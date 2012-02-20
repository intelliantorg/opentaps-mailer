package net.intelliant.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.EmailValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ofbiz.base.location.FlexibleLocation;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;

public final class UtilCommon {
	private static final String module = UtilCommon.class.getName();
	private static final String campaignBaseURL = UtilProperties.getPropertyValue("mailer", "mailer.campaignURL");
	/** must start and end with slash '/' */
	private static final String imageUploadWebApp = StringUtil.cleanUpPathPrefix(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation")) + "/";
	private static final String imageCompressionQuality = UtilProperties.getPropertyValue("mailer", "mailer.imageCompressionQuality", "0.25f");
	private static String imageUploadLocation;
	static {
		try {
			imageUploadLocation = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();
			if (Debug.infoOn()) {
				Debug.logInfo("[parseHtmlAndGenerateCompressedImages] imageUploadLocation >> " + imageUploadLocation, module);
				Debug.logInfo("[parseHtmlAndGenerateCompressedImages] campaignBaseURL >> " + campaignBaseURL, module);
				Debug.logInfo("[parseHtmlAndGenerateCompressedImages] imageUploadWebApp >> " + imageUploadWebApp, module);
				Debug.logInfo("[parseHtmlAndGenerateCompressedImages] imageCompressionQuality >> " + imageCompressionQuality, module);
			}
		} catch (MalformedURLException e) {}
	}
	
	private UtilCommon() {}
	
	/**
	 * 1. Compressed JPEG images.
	 * 2. Prefixes (if required) image server URL to image src locations. 
	 * 
	 * @return a <code>String</code> value
	 */
	public static String parseHtmlAndGenerateCompressedImages(String html) throws IOException {
		if (UtilValidate.isEmpty(html)) {
			return html;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements images = doc.select("img[src~=(?i)\\.(jpg|jpeg|png|gif)]");
		if (images != null && images.size() > 0) {
			Set<String> imageLocations = new HashSet<String>();
			for (Element image : images) {
				String srcAttributeValue = image.attr("src");
				if (!(imageLocations.contains(srcAttributeValue))) {
					if (Debug.infoOn()) {
						Debug.logInfo("[parseHtmlAndGenerateCompressedImages] originalSource >> " + srcAttributeValue, module);
					}
					if (!UtilValidate.isUrl(srcAttributeValue)) {
						int separatorIndex = srcAttributeValue.lastIndexOf("/");
						if (separatorIndex == -1) {
							separatorIndex = srcAttributeValue.lastIndexOf("\\"); /** just in case some one plays with html source. */
						}
						if (separatorIndex != -1) {
							String originalFileName = srcAttributeValue.substring(separatorIndex + 1);
							String outputFileName = originalFileName;
							/** Compression works for jpeg's only. 
							if (originalFileName.endsWith("jpg") || originalFileName.endsWith("jpeg")) {
								try {
									outputFileName = generateCompressedImageForInputFile(imageUploadLocation, originalFileName);
								} catch (NoSuchAlgorithmException e) {
									Debug.logError(e, module);
									return html;
								}
							}
							*/
							StringBuilder finalLocation = new StringBuilder(campaignBaseURL);
							finalLocation.append(imageUploadWebApp).append(outputFileName);
							html = StringUtil.replaceString(html, srcAttributeValue, finalLocation.toString());
							imageLocations.add(srcAttributeValue);
						}
					} else {
						Debug.logWarning("[parseHtmlAndGenerateCompressedImages] ignoring encountered HTML URL..", module);
					}
				}
			}
		} else {
			if (Debug.infoOn()) {
				Debug.logInfo("[parseHtmlAndGenerateCompressedImages] No jpeg images, doing nothing..", module);
			}
		}
		if (Debug.infoOn()) {
			Debug.logInfo("[parseHtmlAndGenerateCompressedImages] returning html >> " + html, module);
		}
		return html;
	}

	private static String generateCompressedImageForInputFile(String locationInFileSystem, String srcFileName) throws NoSuchAlgorithmException, IOException {
		String outputFileName = srcFileName;
		File srcFile = new File(locationInFileSystem, srcFileName);
		File compressedFile = null;
		FileImageOutputStream outputStream = null;
		try {
			if (srcFile.exists()) {
				String md5sum = getMD5SumForFile(srcFile.getAbsolutePath());
				int extentionIndex = srcFileName.lastIndexOf("."); // find index of extension.
				if (extentionIndex != -1) {
					outputFileName = outputFileName.replaceFirst("\\.", "_" + md5sum + ".");
				}
				compressedFile = new File(locationInFileSystem, outputFileName);
				if (Debug.infoOn()) {
					Debug.logInfo("[generateCompressedImageFor] sourceFile >> " + srcFile.getAbsolutePath(), module);
					Debug.logInfo("[generateCompressedImageFor] md5sum >> " + md5sum, module);
					Debug.logInfo("[generateCompressedImageFor] compressedFile >> " + compressedFile.getAbsolutePath(), module);
				}
				if (!compressedFile.exists()) {
					if (Debug.infoOn()) {
						Debug.logInfo("[generateCompressedImageFor] compressed file does NOT exist..", module);
					}
					Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
					ImageWriter imageWriter = (ImageWriter) iter.next();
					ImageWriteParam iwp = imageWriter.getDefaultWriteParam();
					iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
					iwp.setCompressionQuality(Float.parseFloat(imageCompressionQuality));   // an integer between 0 and 1, 1 specifies minimum compression and maximum quality
					BufferedImage bufferedImage = ImageIO.read(srcFile);
					outputStream = new FileImageOutputStream(compressedFile);
					imageWriter.setOutput(outputStream);
					IIOImage image = new IIOImage(bufferedImage, null, null);
					imageWriter.write(null, image, iwp);
				} else {
					if (Debug.infoOn()) {
						Debug.logInfo("[generateCompressedImageFor] compressed file exists, not compressing again..", module);
					}
				}
			} else {
				Debug.logWarning(String.format("Source image file does NOT exist..", srcFile), module);
			}
		} finally {
			if (outputStream != null) {
				outputStream.close();
				outputStream = null;
			}
		}
		return outputFileName;
	}

	private static byte[] createMD5SumForFile(String fileName) throws NoSuchAlgorithmException, IOException {
		InputStream inputStream = new FileInputStream(fileName);
		MessageDigest complete = MessageDigest.getInstance("MD5");
		try {
			byte[] buffer = new byte[1024];
			int numRead;
			do {
				numRead = inputStream.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
		} finally {
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
		}
		return complete.digest();
	}

	/** TODO use instead commons-codec-1.6.jar, it has org.apache.commons.codec.digest.DigestUtils.md5Hex(inputStream). */
	private static String getMD5SumForFile(String fileName) throws NoSuchAlgorithmException, IOException {
		byte[] digest = createMD5SumForFile(fileName);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			result.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}

	/**
	 * Gets the path for uploaded files.
	 * @return a <code>String</code> value
	 */
	public static String getUploadPath() {
		return System.getProperty("user.dir") + File.separatorChar + "runtime" + File.separatorChar + "data" + File.separatorChar;
	}
	
	@SuppressWarnings("unchecked")
	public static long countCampaignLines(GenericDelegator delegator, String statusId, String contactListId, String marketingCampaignId) throws GenericEntityException {
		if (Debug.infoOn()) {
			Debug.logInfo("[countCampaignLines] statusId >> " + statusId, module);
			Debug.logInfo("[countCampaignLines] contactListId >> " + contactListId, module);
			Debug.logInfo("[countCampaignLines] marketingCampaignId >> " + marketingCampaignId, module);
		}
		Map<String, Object> conditions = UtilMisc.toMap("1", "1");
		if (UtilValidate.isNotEmpty(contactListId)) {
			conditions.put("contactListId", contactListId);
		}
		if (UtilValidate.isNotEmpty(marketingCampaignId)) {
			conditions.put("marketingCampaignId", marketingCampaignId);
		}
		if (UtilValidate.isNotEmpty(statusId)) {
			conditions.put("statusId", statusId);
		}
		return delegator.findCountByAnd("MailerCampaignStatus", conditions);
	}
	
	public static long countScheduledCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_SCHEDULED", contactListId, marketingCampaignId);
	}
	
	public static long countCancelledCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_CANCELLED", contactListId, marketingCampaignId);
	}
	
	public static long countExecutedCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_EXECUTED", contactListId, marketingCampaignId);
	}
	
	public static long countOnHoldCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_HOLD", contactListId, marketingCampaignId);
	}
	
	public static long countAllCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, null, contactListId, marketingCampaignId);
	}
	
	public static long countAllCampaignLinesPendingTillDate(GenericDelegator delegator, String marketingCampaignId, String contactListId) throws GenericEntityException {
		List<EntityCondition> conditions = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"));
		conditions.add(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
		conditions.add(new EntityExpr("scheduledForDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp()));
		if (UtilValidate.isNotEmpty(contactListId)) {
			conditions.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
		}

		EntityCondition whereConditions = new EntityConditionList(conditions, EntityOperator.AND);
		return delegator.findCountByCondition("MailerCampaignStatus", whereConditions, null);
	}
	
	private static String getTemplateType(GenericValue templateGV) {
		if (UtilValidate.isEmpty(templateGV)) {
			return null;
		} else {
			return templateGV.getString("mergeFormTypeId");
		}
	}
	
	public static boolean isEmailTemplate(GenericValue templateGV) {
		if (UtilValidate.isEmpty(templateGV)) {
			return false;
		} else {
			String templateType = getTemplateType(templateGV);
			if (templateType.equals("EMAIL")) {
				return true;
			}
			return false;
		}
	}
	
	public static boolean isPrintTemplate(GenericValue templateGV) {
		if (UtilValidate.isEmpty(templateGV)) {
			return false;
		} else {
			String templateType = getTemplateType(templateGV);
			if (templateType.equals("PRINT")) {
				return true;
			}
			return false;
		}
	}
	
	public static boolean isEmailTemplate(GenericDelegator delegator, String templateId) throws GenericEntityException {
		GenericValue templateGV = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", templateId));
		return isEmailTemplate(templateGV);
	}
	
	public static void addErrorMessage(HttpServletRequest request, String resource, String label) {
		addMessage(request, "_ERROR_MESSAGE_", resource, label);
	}
	
	public static void addEventMessage(HttpServletRequest request, String resource, String label) {
		addMessage(request, "_EVENT_MESSAGE_", resource, label);
	}
	
	private static void addMessage(HttpServletRequest request, String messageType, String resource, String label) {
		String message = UtilProperties.getMessage(resource, label, UtilHttp.getLocale(request));
		request.setAttribute(messageType, message);
	}
	
	public static boolean isValidEmailAddress(String emailAddress) {
		if (UtilValidate.isEmpty(emailAddress)) {
			return false;
		}
		return EmailValidator.getInstance().isValid(emailAddress);
	}
		
	public static final String getModifiedMessage(String baseString, String [][]replacer){
		Debug.log(baseString);
		
		for(String []replace : replacer){
			Debug.log(replace[0]+":"+replace[1]);
			baseString = baseString.replace(replace[0], replace[1]);
		}
		Debug.log(baseString);
		return baseString;
	}
}
