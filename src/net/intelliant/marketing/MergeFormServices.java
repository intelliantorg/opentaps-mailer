package net.intelliant.marketing;

import java.util.List;
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
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class MergeFormServices {
	private final static String module = MergeFormServices.class.getName();
    public static final String errorResource = "OpentapsErrorLabels";
	
    public static Map<String, Object> createMergeForm(DispatchContext dctx, Map<String, Object> context) {
    	Map<String, Object> results = ServiceUtil.returnSuccess();
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        Boolean privateForm = "Y".equals((String) context.get("privateForm"));

        GenericValue mergeForm = null;
        String mergeFormId = delegator.getNextSeqId("MergeForm");
        Map<String, Object> newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
        mergeForm = delegator.makeValue("MergeForm", newMergeFormMap);
        mergeForm.setNonPKFields(context);

        if (! privateForm) mergeForm.set("partyId", null);

        try {
            delegator.create(mergeForm);
        } catch (GenericEntityException e) {
        	return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_CreateMergeFormFail", locale), module);
        }
        results.put("mergeFormId", mergeFormId);
        return results;
    }

    public static Map<String, Object> updateMergeForm(DispatchContext dctx, Map<String, Object> context) {
    	GenericValue userLogin = (GenericValue) context.get("userLogin");
        Map<String, Object> results = ServiceUtil.returnSuccess();
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        String scheduleAt = (String) context.get("scheduleAt");
        String mergeFormId = (String) context.get("mergeFormId");
        Boolean privateForm = "Y".equals((String) context.get("privateForm"));
        results.put("mergeFormId", mergeFormId);
       
        try {
        	GenericValue mergeForm = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
            mergeForm.setNonPKFields(context);
            if ((! privateForm)) mergeForm.set("partyId", null);
            delegator.store(mergeForm);
            
            String oldScheduleAt = mergeForm.getString("scheduleAt");            
            if (! UtilValidate.areEqual(oldScheduleAt, scheduleAt)) {
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
