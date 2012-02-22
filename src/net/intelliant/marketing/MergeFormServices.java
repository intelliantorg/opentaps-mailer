package net.intelliant.marketing;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.location.FlexibleLocation;
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
	public static final String opentapsErrorResource = "OpentapsErrorLabels";
	public static final String errorResource = "ErrorLabels";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> createMergeForm(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		GenericValue mergeForm = null;

		// If template type is email, then email field must be filled with valid
		// email id.
		// Note - UtilValidates in-build method is not up to the mark.
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
		mergeForm = delegator.makeValue("MailerMergeForm", newMergeFormMap);
		mergeForm.setNonPKFields(context);
		mergeForm.remove("headerImageLocation");
		mergeForm.remove("footerImageLocation");

		String fileName = "";
		String dataResourceId = "";
		String previewBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");
		try {
			if (UtilValidate.areEqual(mergeFormTypeId, "PRINT")) {
				String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();
				ByteWrapper binData = (ByteWrapper) context.get("headerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					fileName = (String) context.get("_headerImageLocation_fileName");
					dataResourceId = uploadFile(dctx, filePath, fileName, binData);
					String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
					mergeForm.put("headerImageLocation", previewURL);
				}

				binData = (ByteWrapper) context.get("footerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					fileName = (String) context.get("_footerImageLocation_fileName");
					dataResourceId = uploadFile(dctx, filePath, fileName, binData);
					String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
					mergeForm.put("footerImageLocation", previewURL);
				}
			} else if (UtilValidate.areEqual(mergeFormTypeId, "EMAIL")) {
				mergeForm.put("fromEmailAddress", mergeFormEmailAddress);
			}
		} catch (Exception e) {
			return UtilMessage.createAndLogServiceError(e, module);
		}
		try {
			delegator.create(mergeForm);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_CreateMergeFormFail", locale), module);
		}
		results.put("mergeFormId", mergeFormId);
		return results;
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
		results.put("mergeFormId", mergeFormId);

		String fileName = "";
		String dataResourceId = "";
		String previewBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");
		Map<String, Object> inputs = null;
		try {
			GenericValue mergeForm = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
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
				fileName = (String) context.get("_headerImageLocation_fileName");
				dataResourceId = uploadFile(dctx, filePath, fileName, binData);
				String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
				mergeForm.put("headerImageLocation", previewURL);
			} else if (UtilValidate.areEqual(headerImageLocationRemove, "Y")) {
				mergeForm.put("headerImageLocation", "");
			}

			binData = (ByteWrapper) context.get("footerImageLocation");
			if (binData != null && binData.getLength() > 0) {
				fileName = (String) context.get("_footerImageLocation_fileName");
				dataResourceId = uploadFile(dctx, filePath, fileName, binData);
				String previewURL = previewBasePath + File.separator + dataResourceId + File.separator + fileName;
				mergeForm.put("footerImageLocation", previewURL);
			} else if (UtilValidate.areEqual(footerImageLocationRemove, "Y")) {
				mergeForm.put("footerImageLocation", "");
			}

			delegator.store(mergeForm);

			String oldScheduleAt = mergeForm.getString("scheduleAt");
			if (!UtilValidate.areEqual(oldScheduleAt, scheduleAt)) {
				inputs = UtilMisc.toMap("scheduleAt", scheduleAt);
				inputs.put("userLogin", userLogin);

				List<GenericValue> relatedCampaigns = mergeForm.getRelated("MarketingCampaign");
				if (UtilValidate.isNotEmpty(relatedCampaigns)) {
					for (GenericValue relatedCampaign : relatedCampaigns) {
						inputs.put("marketingCampaignId", relatedCampaign.getString("marketingCampaignId"));
						results = dctx.getDispatcher().runSync("mailer.reScheduleMailers", inputs);
						if (ServiceUtil.isError(results)) {
							return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
						}
					}
				}
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (MalformedURLException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(opentapsErrorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		}
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
