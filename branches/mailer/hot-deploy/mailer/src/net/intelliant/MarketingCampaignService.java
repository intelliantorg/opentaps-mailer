package net.intelliant;

import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class MarketingCampaignService {
	public static final String module = MarketingCampaignService.class.getName();
	public static final String errorResource = "ErrorLabels";
	public static final String successResource = "UILabels";
	
    /**
     *  Is a wrapper over the original marketing campaign, custom values will be persisted once the campaign is created. 
     */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess(); 		
		Locale locale = (Locale) context.get("locale");		
		try {
			GenericValue mergeFormGV = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
			if (UtilValidate.isEmpty(mergeFormGV)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), module);
			}
			ModelService service = dctx.getModelService("createMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, "IN");
			LocalDispatcher dispatcher = dctx.getDispatcher();		
			serviceResults = dispatcher.runSync(service.name, inputs);
			if (ServiceUtil.isError(serviceResults)) {
		        return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, module);
		    }
		    String marketingCampaignId = (String)serviceResults.get("marketingCampaignId");
		    inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);	        	
		    inputs.put("fromEmailAddress",context.get("fromEmailAddress"));
		    inputs.put("templateId",context.get("templateId"));
		    inputs.put("contactListId",context.get("contactListId"));
		    GenericValue mailerMarketingCampaign = delegator.makeValue("MailerMarketingCampaign", inputs);
		    mailerMarketingCampaign.create();
		    serviceResults.put("marketingCampaignId", marketingCampaignId);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		}
	    return serviceResults;
	}
}
