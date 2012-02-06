package net.intelliant.marketing;

import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.content.content.ContentServices;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;
import org.opentaps.domain.base.entities.Content;

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
        Map<String, Object> result = ServiceUtil.returnSuccess();
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        String mergeFormId = (String) context.get("mergeFormId");
        Boolean privateForm = "Y".equals((String) context.get("privateForm"));
        Map<String, Object> newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
       
        System.out.println("Context : "+context+"\nMap : "+newMergeFormMap);
        
        GenericValue mergeForm = null;
                      
        try {
            mergeForm = delegator.findByPrimaryKey("MergeForm", newMergeFormMap);
            //delegator.findByAnd(entityName, fields)
            mergeForm.setNonPKFields(context);
            if ((! privateForm)) mergeForm.set("partyId", null);
            delegator.store(mergeForm);
        } catch (GenericEntityException e) {
        	return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale), module);
        }        
        result.put("mergeFormId", mergeFormId);
        return result;
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
