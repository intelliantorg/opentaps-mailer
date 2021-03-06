/*
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
 */
package net.intelliant.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
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
import org.ofbiz.entity.util.EntityUtil;

public final class UtilCommon {
	private static final Pattern emailPattern = Pattern.compile("^(?!(?:(?:\\x22?\\x5C[\\x00-\\x7E]\\x22?)|(?:\\x22?[^\\x5C\\x22]\\x22?)){255,})(?!(?:(?:\\x22?\\x5C[\\x00-\\x7E]\\x22?)|(?:\\x22?[^\\x5C\\x22]\\x22?)){65,}@)(?:(?:[\\x21\\x23-\\x27\\x2A\\x2B\\x2D\\x2F-\\x39\\x3D\\x3F\\x5E-\\x7E]+)|(?:\\x22(?:[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x21\\x23-\\x5B\\x5D-\\x7F]|(?:\\x5C[\\x00-\\x7F]))*\\x22))(?:\\.(?:(?:[\\x21\\x23-\\x27\\x2A\\x2B\\x2D\\x2F-\\x39\\x3D\\x3F\\x5E-\\x7E]+)|(?:\\x22(?:[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x21\\x23-\\x5B\\x5D-\\x7F]|(?:\\x5C[\\x00-\\x7F]))*\\x22)))*@(?:(?:(?!.*[^.]{64,})(?:(?:(?:xn--)?[a-z0-9]+(?:-[a-z0-9]+)*\\.){1,126}){1,}(?:(?:[a-z][a-z0-9]*)|(?:(?:xn--)[a-z0-9]+))(?:-[a-z0-9]+)*)|(?:\\[(?:(?:IPv6:(?:(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){7})|(?:(?!(?:.*[a-f0-9][:\\]]){7,})(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,5})?::(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,5})?)))|(?:(?:IPv6:(?:(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){5}:)|(?:(?!(?:.*[a-f0-9]:){5,})(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,3})?::(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,3}:)?)))?(?:(?:25[0-5])|(?:2[0-4][0-9])|(?:1[0-9]{2})|(?:[1-9]?[0-9]))(?:\\.(?:(?:25[0-5])|(?:2[0-4][0-9])|(?:1[0-9]{2})|(?:[1-9]?[0-9]))){3}))\\]))$", Pattern.CASE_INSENSITIVE);
	
	private static final String module = UtilCommon.class.getName();
	private static final String campaignBaseURL = UtilProperties.getPropertyValue("mailer", "mailer.campaignURL");
	/** must start and end with slash '/' */
	private static final String imageUploadWebApp = StringUtil.cleanUpPathPrefix(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation")) + "/";
	private static final String imageCompressionQuality = UtilProperties.getPropertyValue("mailer", "mailer.imageCompressionQuality", "0.25f");
	private static String imageUploadLocation;
	static {
		try {
			imageUploadLocation = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();
		} catch (MalformedURLException e) {}
		if (Debug.infoOn()) {
			Debug.logInfo("[UtilCommon] campaignBaseURL >> " + campaignBaseURL, module);
			Debug.logInfo("[UtilCommon] imageUploadLocation >> " + imageUploadLocation, module);
			Debug.logInfo("[UtilCommon] imageUploadWebApp >> " + imageUploadWebApp, module);
			Debug.logInfo("[UtilCommon] imageCompressionQuality >> " + imageCompressionQuality, module);
		}
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
							
							/* Handling spaces in file-name to make url friendly. */
							String outputFileName = StringEscapeUtils.escapeHtml(originalFileName);
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

	public static String getModifiedHtmlWithAbsoluteImagePath(String html){
		if (UtilValidate.isEmpty(html)) {
			return html;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements images = doc.select("img[src~=(?i)\\.(jpg|jpeg|png|gif)]");
		
		if (images != null && images.size() > 0) {
			String srcAttributeValue = "";
			StringBuilder finalLocation = new StringBuilder();
			Set<String> imageSrc = new HashSet<String>();
			
			for (Element image : images) {
				srcAttributeValue = image.attr("src");
				
				if(!imageSrc.contains(srcAttributeValue)){
					int separatorIndex = srcAttributeValue.lastIndexOf("/");
					if (separatorIndex == -1) {
						separatorIndex = srcAttributeValue.lastIndexOf("\\"); /** just in case some one plays with html source. */
					}
					String outputFileName = null;
					if (separatorIndex != -1) {
						String originalFileName = srcAttributeValue.substring(separatorIndex + 1);
						outputFileName = originalFileName;
					}
					finalLocation = new StringBuilder(imageUploadLocation);
					finalLocation = finalLocation.append(outputFileName);
					
					imageSrc.add(srcAttributeValue);
					html = StringUtil.replaceString(html, srcAttributeValue, finalLocation.toString());	
				}
			}
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
	
	public static long countContactListRecipients(GenericDelegator delegator, String contactListId) throws GenericEntityException {
		List<EntityCondition> conditions = UtilMisc.toList(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
		conditions.add(EntityUtil.getFilterByDateExpr("validFromDate", "validThruDate"));

		EntityCondition whereConditions = new EntityConditionList(conditions, EntityOperator.AND);
		return delegator.findCountByCondition("MailerRecipientContactList", whereConditions, null);
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
		Matcher matcher = emailPattern.matcher(emailAddress);
		return matcher.matches();
	}
	/**
	 * UtilDateTime.addDaysToTimestamp(Timestamp start, Double days) type-casts input into integer resulting in loss.
	 * @return a <code>Timestamp</code> value
	 */
	public static Timestamp addDaysToTimestamp(Timestamp start, Double days) {
	    return new Timestamp(start.getTime() + ((long) (24*60*60*1000*days.longValue())));
	}	
}
