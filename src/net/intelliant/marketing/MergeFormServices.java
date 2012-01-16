package net.intelliant.marketing;

import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

public class MergeFormServices {
	private final static String module = MergeFormServices.class.getName();
    public static final String errorResource = "OpentapsErrorLabels";
	
    public static Map createMergeForm(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        Boolean privateForm = "Y".equals((String) context.get("privateForm"));

        GenericValue mergeForm = null;
        String mergeFormId = delegator.getNextSeqId("MergeForm");
        Map newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
        mergeForm = delegator.makeValue("MergeForm", newMergeFormMap);
        mergeForm.setNonPKFields(context);
        
        if (! privateForm) mergeForm.set("partyId", null);
        
        try {
            delegator.create(mergeForm);
        } catch (GenericEntityException e) {
            String errorMessage = UtilProperties.getMessage(errorResource, "OpentapsError_CreateMergeFormFail", locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnError(errorMessage);
        }

        return UtilMisc.toMap("mergeFormId", mergeFormId);
    }

    public static Map updateMergeForm(DispatchContext dctx, Map context) {
        Map result = ServiceUtil.returnSuccess();
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        String mergeFormId = (String) context.get("mergeFormId");
        Boolean privateForm = "Y".equals((String) context.get("privateForm"));

        GenericValue mergeForm = null;
        try {
            Map newMergeFormMap = UtilMisc.toMap("mergeFormId", mergeFormId);
            mergeForm = delegator.findByPrimaryKey("MergeForm", newMergeFormMap);
            mergeForm.setNonPKFields(context);
            if ((! privateForm)) mergeForm.set("partyId", null);
            delegator.store(mergeForm);
        } catch (GenericEntityException e) {
            String errorMessage = UtilProperties.getMessage(errorResource, "OpentapsError_UpdateMergeFormFail", locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnError(errorMessage);
        }
        
        result.put("mergeFormId", mergeFormId);
        return result;
    }
    
    public static Map deleteMergeForm(DispatchContext dctx, Map context) {
        Map result = ServiceUtil.returnSuccess();
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        String mergeFormId = (String) context.get("mergeFormId");

        try {
            delegator.removeByAnd("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));
        } catch (GenericEntityException e) {
            String errorMessage = UtilProperties.getMessage(errorResource, "OpentapsError_DeleteMergeFormFail", locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnError(errorMessage);
        }

        return result;
    }

}
