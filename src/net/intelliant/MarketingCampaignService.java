package net.intelliant;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map createMarketingCampaign(DispatchContext dctx, Map context) throws GenericServiceException, GenericEntityException{
		GenericDelegator delegator = dctx.getDelegator();
		
		Locale locale = (Locale) context.get("locale");
		String module = (String) context.get("module");
		
		GenericValue checkResults = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId",context.get("templateId")));
		if(checkResults == null){
			return UtilMessage.createAndLogServiceError("Proved template id is not valid", module);
		}
		
		checkResults = delegator.findByPrimaryKey("ContactList", UtilMisc.toMap("contactListId",context.get("contactListId")));
		if(checkResults == null){
			return UtilMessage.createAndLogServiceError("Proved contact list id is not valid", module);
		}
		
		
		//* Context will contain several parameters, keep the parameters acceptable to the service being called.
		ModelService service = dctx.getModelService("createMarketingCampaign");
		Map input = service.makeValid(context, "IN");
		
		LocalDispatcher dispatcher = dctx.getDispatcher();		
		Map serviceResults = dispatcher.runSync(service.name, input);
		
		if (ServiceUtil.isError(serviceResults)) {
	        return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, module);
	    }
		
	    String marketingCampaignId = (String) serviceResults.get("marketingCampaignId");
	    //* Try with Map.putAll()
	    Map parameters = new HashMap();	        	
	    parameters.put("marketingCampaignId", marketingCampaignId);
	    parameters.put("fromEmailAddress",context.get("fromEmailAddress"));
	    parameters.put("templateId",context.get("templateId"));
	    parameters.put("contactListId",context.get("contactListId"));
	    
	    GenericValue mailerMarketingCampaign = delegator.makeValue("MailerMarketingCampaign", parameters);
	    mailerMarketingCampaign.create();
	    
	    serviceResults = ServiceUtil.returnSuccess();
	    serviceResults.put("marketingCampaignId", marketingCampaignId);
	    return serviceResults;
	    //UtilMessage.createServiceSuccess("Operation successful! '"+context.get("campaignName")+"' added.", locale, serviceResults);//  serviceResults;
	}
}
