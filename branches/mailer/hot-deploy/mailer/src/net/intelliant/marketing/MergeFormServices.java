package net.intelliant.marketing;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.intelliant.util.UtilCommon;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.ofbiz.base.location.FlexibleLocation;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.ByteWrapper;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class MergeFormServices {
	private final static String module = MergeFormServices.class.getName();
	private static final String opentapsErrorResource = "OpentapsErrorLabels";
	private static final String errorResource = "ErrorLabels";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> createMergeForm(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");

		String mergeFormTypeId = String.valueOf(context.get("mergeFormTypeId"));
		String mergeFormEmailAddress = String.valueOf(context.get("fromEmailAddress"));
		String subject = String.valueOf(context.get("subject"));

		if (UtilValidate.areEqual(mergeFormTypeId, "EMAIL")) {
			if (UtilValidate.areEqual(null, mergeFormEmailAddress) || !UtilCommon.isValidEmailAddress(mergeFormEmailAddress)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "ErrorMergeFormValidEmail", locale), module);
			}
			if (UtilValidate.isEmpty(subject)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "ErrorMergeFormSubjectNotNullForEmailTypeTemplate", locale), module);
			}
		}
		String mergeFormId = delegator.getNextSeqId("MailerMergeForm");
		Map<String, Object> newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
		GenericValue mergeForm = delegator.makeValue("MailerMergeForm", newMergeFormMap);
		mergeForm.setNonPKFields(context);
		mergeForm.remove("headerImageLocation");
		mergeForm.remove("footerImageLocation");

		String fileName = "";
		String dataResourceId = "";
		String previewBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");
		try {
			if (UtilValidate.areEqual(mergeFormTypeId, "PRINT")) {
				String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();
				double topMargin = -1;
				ByteWrapper binData = (ByteWrapper) context.get("headerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					topMargin = computeImageHeightInInches(binData);
					fileName = (String) context.get("_headerImageLocation_fileName");
					dataResourceId = uploadFile(dctx, filePath, fileName, binData);
					String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
					mergeForm.put("headerImageLocation", previewURL);
				}
				mergeForm.put("topMargin", topMargin);

				double bottomMargin = -1;
				binData = (ByteWrapper) context.get("footerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					bottomMargin = computeImageHeightInInches(binData);
					fileName = (String) context.get("_footerImageLocation_fileName");
					dataResourceId = uploadFile(dctx, filePath, fileName, binData);
					String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
					mergeForm.put("footerImageLocation", previewURL);
				}
				mergeForm.put("bottomMargin", bottomMargin);
			} else if (UtilValidate.areEqual(mergeFormTypeId, "EMAIL")) {
				mergeForm.put("fromEmailAddress", mergeFormEmailAddress);
			}
			delegator.create(mergeForm);
			results.put("mergeFormId", mergeFormId);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_CreateMergeFormFail", locale), locale, module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_CreateMergeFormFail", locale), locale, module);
		} catch (MalformedURLException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_CreateMergeFormFail", locale), locale, module);
		}
		return results;
	}

	private static double computeImageHeightInInches(ByteWrapper binData) {
		double heigthInInches = -1;
		try {
			ImageInfo info = Sanselan.getImageInfo(binData.getBytes());
			if (info != null) {
				printImageInfo(info);
				heigthInInches = info.getPhysicalHeightInch();
			}
		} catch (ImageReadException e) {
			Debug.logError(e, "Encountered errors reading image info.", module);
		} catch (IOException e) {
			Debug.logError(e, "Encountered errors reading image info.", module);
		}
		return heigthInInches;
	}
	
	private static void printImageInfo(ImageInfo info) {
		if (Debug.infoOn()) {
			Debug.logInfo("[mailer.createMergeForm] Sanselan bpp >> " + info.getBitsPerPixel(), module);
			Debug.logInfo("[mailer.createMergeForm] Sanselan height in px >> " + info.getHeight(), module);
			Debug.logInfo("[mailer.createMergeForm] Sanselan height in inches >> " + info.getPhysicalHeightInch(), module);
			Debug.logInfo("[mailer.createMergeForm] Sanselan width in inches >> " + info.getPhysicalWidthInch(), module);
			Debug.logInfo("[mailer.createMergeForm] Sanselan height dpi >> " + info.getPhysicalHeightDpi(), module);
			Debug.logInfo("[mailer.createMergeForm] Sanselan width dpi >> " + info.getPhysicalWidthDpi(), module);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static String uploadFile(DispatchContext dctx, String parentPath, String uploadedFileName, ByteWrapper binaryDataOfFile) throws GenericServiceException {
		String dataResourceId = dctx.getDelegator().getNextSeqId("DataResource");
		File tmpFile = new File(parentPath + File.separator + dataResourceId);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		String uploadedFilePath = tmpFile.getAbsolutePath() + File.separator + uploadedFileName;
		Map<String, Object> inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binaryDataOfFile, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
		dctx.getDispatcher().runSync("createAnonFile", inputs);
		return dataResourceId;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> updateMergeForm(DispatchContext dctx, Map<String, Object> context) {
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String mergeFormTypeId = String.valueOf(context.get("mergeFormTypeId"));
		String scheduleAt = (String) context.get("scheduleAt");
		String mergeFormId = (String) context.get("mergeFormId");
		String headerImageLocationRemove = String.valueOf(context.get("headerImageLocationRemove"));
		String footerImageLocationRemove = String.valueOf(context.get("footerImageLocationRemove"));

		String fileName = "";
		String dataResourceId = "";
		String previewBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");
		try {
			GenericValue mergeForm = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
			String oldScheduleAt = mergeForm.getString("scheduleAt");
			mergeForm.setNonPKFields(context);
			mergeForm.remove("headerImageLocation");
			mergeForm.remove("footerImageLocation");
			
			String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();

			if (UtilValidate.areEqual(mergeFormTypeId, "EMAIL")) {
				mergeForm.set("fromEmailAddress", context.get("fromEmailAddress"));
			} else {
				mergeForm.put("fromEmailAddress", "");
			}
			ByteWrapper binData = (ByteWrapper) context.get("headerImageLocation");
			if (binData != null && binData.getLength() > 0) {
				double topMargin = computeImageHeightInInches(binData);
				fileName = (String) context.get("_headerImageLocation_fileName");
				dataResourceId = uploadFile(dctx, filePath, fileName, binData);
				String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
				mergeForm.put("headerImageLocation", previewURL);
				mergeForm.put("topMargin", topMargin);
			} else if (UtilValidate.areEqual(headerImageLocationRemove, "Y")) {
				mergeForm.put("headerImageLocation", "");
			}

			binData = (ByteWrapper) context.get("footerImageLocation");
			if (binData != null && binData.getLength() > 0) {
				double bottomMargin = computeImageHeightInInches(binData);
				fileName = (String) context.get("_footerImageLocation_fileName");
				dataResourceId = uploadFile(dctx, filePath, fileName, binData);
				String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
				mergeForm.put("footerImageLocation", previewURL);
				mergeForm.put("bottomMargin", bottomMargin);
			} else if (UtilValidate.areEqual(footerImageLocationRemove, "Y")) {
				mergeForm.put("footerImageLocation", "");
			}

			delegator.store(mergeForm);

			if (!UtilValidate.areEqual(oldScheduleAt, scheduleAt)) {
				Map<String, Object> inputs = UtilMisc.toMap("scheduleAt", scheduleAt);
				inputs.put("userLogin", userLogin);

				List<GenericValue> relatedCampaigns = mergeForm.getRelated("MailerMarketingCampaign");
				if (UtilValidate.isNotEmpty(relatedCampaigns)) {
					for (GenericValue relatedCampaign : relatedCampaigns) {
						inputs.put("marketingCampaignId", relatedCampaign.getString("marketingCampaignId"));
						if (Debug.infoOn()) {
							Debug.logInfo("[mailer.updateMergeForm] calling mailer.reScheduleMailers with inputs >> " + inputs, module);	
						}
						results = dctx.getDispatcher().runSync("mailer.reScheduleMailers", inputs);
						if (ServiceUtil.isError(results)) {
							return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
						}
					}
				}
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), locale, module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), locale, module);
		} catch (MalformedURLException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), locale, module);
		}
		results.put("mergeFormId", mergeFormId);
		return results;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> deleteMergeForm(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String mergeFormId = (String) context.get("mergeFormId");
		try {
			delegator.removeByAnd("MailerMergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_DeleteMergeFormFail", locale), module);
		}
		return results;
	}
}
