package net.intelliant.marketing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		try {
			String filePath = FlexibleLocation.resolveLocation(UtilProperties.getPropertyValue("mailer", "mailer.imageUploadLocation")).getPath();
			
			ByteWrapper binData = (ByteWrapper) context.get("headerImageLocation");			
			String fileName = (String) context.get("_headerImageLocation_fileName");
			String contentType = (String) context.get("_headerImageLocation_contentType");
			
			String dataResourceId = delegator.getNextSeqId("DataResource");
			String uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
			
			Map<String, Object> inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
			dispatcher.runSync("createAnonFile", inputs);
			
			binData = (ByteWrapper) context.get("footerImageLocation");
			fileName = (String) context.get("_footerImageLocation_fileName");
			contentType = (String) context.get("_footerImageLocation_contentType");
			
			dataResourceId = delegator.getNextSeqId("DataResource");
			uploadedFilePath = writeToFile(filePath, dataResourceId, fileName, binData);
			inputs = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", binData, "dataResourceTypeId", "LOCAL_FILE", "objectInfo", uploadedFilePath);
			dispatcher.runSync("createAnonFile", inputs);
			
		} catch (Exception e) {
			return UtilMessage.createAndLogServiceError(e, module);
		}
		
		if (true) {
			return UtilMessage.createServiceError("Testing", locale);
		}

		String mergeFormId = delegator.getNextSeqId("MergeForm");
		Map<String, Object> newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
		mergeForm = delegator.makeValue("MergeForm", newMergeFormMap);
		mergeForm.setNonPKFields(context);

		try {
			delegator.create(mergeForm);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_CreateMergeFormFail", locale), module);
		}
		results.put("mergeFormId", mergeFormId);
		return results;
	}
	private static String writeToFile(String path, String subFolder, String fileName, ByteWrapper binaryDataOfFile) throws IOException{
		File tmpFile = new File(path+File.separator+subFolder);
		if(!tmpFile.isDirectory()){
			tmpFile.mkdir();
		}

		tmpFile = new File(tmpFile.getAbsolutePath()+File.separator+fileName);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		fos.write(binaryDataOfFile.getBytes());
		
		return tmpFile.getAbsolutePath();
	}

	public static Map<String, Object> updateMergeForm(DispatchContext dctx, Map<String, Object> context) {
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Map<String, Object> results = ServiceUtil.returnSuccess();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String scheduleAt = (String) context.get("scheduleAt");
		String mergeFormId = (String) context.get("mergeFormId");
		results.put("mergeFormId", mergeFormId);

		try {
			GenericValue mergeForm = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
			mergeForm.setNonPKFields(context);
			delegator.store(mergeForm);

			String oldScheduleAt = mergeForm.getString("scheduleAt");
			if (!UtilValidate.areEqual(oldScheduleAt, scheduleAt)) {
				Map inputs = UtilMisc.toMap("scheduleAt", scheduleAt);
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
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
		} catch (GenericServiceException e) {
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
