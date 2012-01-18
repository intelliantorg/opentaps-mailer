package net.intelliant.imports;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;


public class ImportServices {
	public static final String module = ImportServices.class.getName();
	public static final String errorResource = "ErrorLabels";
	public static final String successResource = "UILabels";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> configureImportMapping(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		Locale locale = (Locale) context.get("locale");
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String importMapperName = (String) context.get("importMapperName");
		String description = (String) context.get("description");
		String entityName = (String) context.get("entityName");
		String contentId = (String) context.get("contentId");
		String importMapperId = dctx.getDelegator().getNextSeqId("MailerImportMapper");
		Map<String, Object> inputs = UtilMisc.toMap("importMapperId", importMapperId, "importMapperName", importMapperName, "description", description);
		inputs.put("entityName", entityName);
		inputs.put("contentId", contentId);
		inputs.put("createdByUserLogin", userLogin.getString("userLoginId"));
		try {
			dctx.getDelegator().makeValue("MailerImportMapper", inputs).create();
			ModelService service = dctx.getModelService("mailer.configureImportColumnsMapping");
			inputs = service.makeValid(context, "IN");
			inputs.put("importMapperId", importMapperId);
			dctx.getDispatcher().runSync(service.name, inputs);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		}
		serviceResults.put("importMapperId", importMapperId);
		return serviceResults;	
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> configureImportColumnsMapping(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		Locale locale = (Locale) context.get("locale");
		String importMapperId = (String) context.get("importMapperId");
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Map<String, Object> entityColNames = (Map) context.get("entityColName");
		Map<String, Object> importFileColIdx = (Map) context.get("importFileColIdx");
		Set<String> keys = entityColNames.keySet();
		for (String key : keys) {
			String importColumnMapperId = dctx.getDelegator().getNextSeqId("MailerImportColumnMapper");
			Map<String, Object> inputs = UtilMisc.toMap("importColumnMapperId", importColumnMapperId);
			String importFileColIdxValue = importFileColIdx.get(key).toString();
			if (UtilValidate.isNotEmpty(importFileColIdxValue)) {
				inputs.put("importMapperId", importMapperId);				
				inputs.put("entityColName", entityColNames.get(key));
				inputs.put("importFileColIdx", importFileColIdxValue);
				inputs.put("createdByUserLogin", userLogin.getString("userLoginId"));
				try {
					dctx.getDelegator().makeValue("MailerImportColumnMapper", inputs).create();
				} catch (GenericEntityException e) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
				}	
			}
		}
		return serviceResults;	
	}
}