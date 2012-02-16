package net.intelliant.marketing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class MergeFormServices {
	private final static String module = MergeFormServices.class.getName();
	public static final String errorResource = "OpentapsErrorLabels";

	public static Map<String, Object> createMergeForm(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Locale locale = (Locale) context.get("locale");

		GenericValue mergeForm = null;

		// If template type is email, then email field must be filled with valid
		// email id.
		// Note - UtilValidates in-build method is not up to the mark.
		String mergeFormTypeId = String.valueOf(context.get("mergeFormTypeId"));
		String mergeFormEmailAddress = String.valueOf(context.get("fromEmailAddress"));
		if (UtilValidate.areEqual(mergeFormTypeId, "EMAIL") && (UtilValidate.areEqual(null, mergeFormEmailAddress) || !UtilValidate.isEmail(mergeFormEmailAddress))) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCampaignTemplateForEmail", locale), module);
		}

		String mergeFormId = delegator.getNextSeqId("MergeForm");
		Map<String, Object> newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
		mergeForm = delegator.makeValue("MergeForm", newMergeFormMap);
		mergeForm.setNonPKFields(context);
		mergeForm.put("headerImageLocation", "");
		mergeForm.put("footerImageLocation", "");
		
		ByteWrapper binData = null;
		String fileName = null;

		String dataResourceId = null;
		String uploadedFilePath = null;
		String urlBasePath = null;
		Map<String, Object> inputs = null;

		try {
			if (UtilValidate.areEqual(mergeFormTypeId, "PRINT")) {
				String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();

				binData = (ByteWrapper) context.get("headerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					fileName = (String) context.get("_headerImageLocation_fileName");

					dataResourceId = delegator.getNextSeqId("DataResource");
					uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
					urlBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");

					mergeForm.put("headerImageLocation", urlBasePath + File.separator + dataResourceId + File.separator + fileName);

					inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
					dispatcher.runSync("createAnonFile", inputs);
				}

				binData = (ByteWrapper) context.get("footerImageLocation");
				if (binData != null && binData.getLength() > 0) {
					fileName = (String) context.get("_footerImageLocation_fileName");

					dataResourceId = delegator.getNextSeqId("DataResource");
					uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
					urlBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");

					mergeForm.put("footerImageLocation", urlBasePath + File.separator + dataResourceId + File.separator + fileName);

					inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
					dispatcher.runSync("createAnonFile", inputs);
				}
			}else{
				mergeForm.put("fromEmailAddress", "");
			}
		} catch (Exception e) {
			return UtilMessage.createAndLogServiceError(e, module);
		}

		try {
			delegator.create(mergeForm);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_CreateMergeFormFail", locale), module);
		}
		results.put("mergeFormId", mergeFormId);
		return results;
	}

	private static String writeToFile(String path, String subFolder, String fileName, ByteWrapper binaryDataOfFile) throws IOException {
		File tmpFile = new File(path + File.separator + subFolder);
		if (!tmpFile.isDirectory()) {
			tmpFile.mkdir();
		}

		tmpFile = new File(tmpFile.getAbsolutePath() + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		fos.write(binaryDataOfFile.getBytes());

		return tmpFile.getAbsolutePath();
	}

	public static Map<String, Object> updateMergeForm(DispatchContext dctx, Map<String, Object> context) {
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		LocalDispatcher dispatcher = dctx.getDispatcher();
		Locale locale = (Locale) context.get("locale");
		String mergeFormTypeId = String.valueOf(context.get("mergeFormTypeId"));
		String scheduleAt = (String) context.get("scheduleAt");
		String mergeFormId = (String) context.get("mergeFormId");
		results.put("mergeFormId", mergeFormId);

		ByteWrapper binData = null;
		String fileName = null;

		String dataResourceId = null;
		String uploadedFilePath = null;
		String urlBasePath = null;

		Map<String, Object> inputs = null;
		try {
			GenericValue mergeForm = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
			mergeForm.setNonPKFields(context);
			mergeForm.put("headerImageLocation", "");
			mergeForm.put("footerImageLocation", "");

			String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();

			if(!UtilValidate.areEqual(mergeFormTypeId, "EMAIL")){
				mergeForm.put("fromEmailAddress", "");
			}

			binData = (ByteWrapper) context.get("headerImageLocation");
			if (binData != null && binData.getLength() > 0) {
				fileName = (String) context.get("_headerImageLocation_fileName");

				dataResourceId = delegator.getNextSeqId("DataResource");
				uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
				urlBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");

				mergeForm.put("headerImageLocation", urlBasePath + File.separator + dataResourceId + File.separator + fileName);

				inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
				dispatcher.runSync("createAnonFile", inputs);
			}else{
				mergeForm.put("headerImageLocation", context.get("headerImageLocationStr"));
			}

			binData = (ByteWrapper) context.get("footerImageLocation");
			if (binData != null && binData.getLength() > 0) {
				fileName = (String) context.get("_footerImageLocation_fileName");

				dataResourceId = delegator.getNextSeqId("DataResource");
				uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
				urlBasePath = UtilProperties.getPropertyValue("mailer", "mailer.imageUploadBaseLocation");

				mergeForm.put("footerImageLocation", urlBasePath + File.separator + dataResourceId + File.separator + fileName);

				inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
				dispatcher.runSync("createAnonFile", inputs);
			}else{
				mergeForm.put("footerImageLocation", context.get("footerImageLocationStr"));
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
							return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
						}
					}
				}
			}
		} catch (GenericEntityException e) {
			Debug.log(e);
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (GenericServiceException e) {
			Debug.log(e);
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (MalformedURLException e) {
			Debug.log(e);
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (IOException e) {
			Debug.log(e);
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		}
		return results;
	}

	public static Map<String, Object> deleteMergeForm(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String mergeFormId = (String) context.get("mergeFormId");
		try {
			delegator.removeByAnd("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_DeleteMergeFormFail", locale), module);
		}
		return results;
	}
}
