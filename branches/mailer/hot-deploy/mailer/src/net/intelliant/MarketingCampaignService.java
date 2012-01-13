package net.intelliant;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
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
	 * Is a wrapper over the original marketing campaign, custom values will be
	 * persisted once the campaign is created.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		Locale locale = (Locale) context.get("locale");
		String contactListId = (String) context.get("contactListId");
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
			String marketingCampaignId = (String) serviceResults.get("marketingCampaignId");
			serviceResults.put("marketingCampaignId", marketingCampaignId);
			
			inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
			inputs.put("fromEmailAddress", context.get("fromEmailAddress"));
			inputs.put("templateId", context.get("templateId"));
			GenericValue mailerMarketingCampaign = delegator.makeValue("MailerMarketingCampaign", inputs);
			mailerMarketingCampaign.create();
			
			addContactListToCampaign(delegator, contactListId, marketingCampaignId, null);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		}
		return serviceResults;
	}

	@SuppressWarnings("unchecked")
	private static void addContactListToCampaign(GenericDelegator delegator, String contactListId, String marketingCampaignId, Timestamp now) throws GenericEntityException {
		if (now == null) {
			now = UtilDateTime.nowTimestamp();
		}
		String campaignListId = delegator.getNextSeqId("MailerMarketingCampaignAndContactList");
		Map<String, Object> inputs = UtilMisc.toMap("campaignListId", campaignListId, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId, "fromDate", now);
		GenericValue mailerMarketingCampaignContactList = delegator.makeValue("MailerMarketingCampaignAndContactList", inputs);
		mailerMarketingCampaignContactList.create();
	}
	
	/** Add contact list to campaign. */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> addContactListToCampaign(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		Locale locale = (Locale) context.get("locale");
		String contactListId = (String) context.get("contactListId");
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		List andConditions = UtilMisc.toList(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
		andConditions.add(EntityUtil.getFilterByDateExpr());
		andConditions.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
		EntityConditionList conditions = new EntityConditionList(andConditions, EntityOperator.AND);
		try {
			List marketingCampaignContactLists = dctx.getDelegator().findByCondition("MailerMarketingCampaignAndContactList", conditions, null, UtilMisc.toList("fromDate"));
			if (UtilValidate.isEmpty(marketingCampaignContactLists)) {
				addContactListToCampaign(dctx.getDelegator(), contactListId, marketingCampaignId, null);
			} else {
				if (Debug.infoOn()) {
					Debug.logInfo("Contact List - " + contactListId + " already associated with campaign - " + marketingCampaignId, module);
				}
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), module);
		}
		return serviceResults;
	}
	
	/**
     * Removes a contact list from a Marketing campaign with its tracking code by expiring those entities
     * - expire the MarketingCampaignContactList
     */
	@SuppressWarnings("unchecked")
    public static Map<String, Object> removeContactListFromMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        String campaignListId = (String) context.get("campaignListId");
        Timestamp now = UtilDateTime.nowTimestamp();

        try {
            // get the MarketingCampaignContactList
            GenericValue marketingCampaignContactList = delegator.findByPrimaryKey("MailerMarketingCampaignAndContactList", UtilMisc.toMap("campaignListId", campaignListId));
            if (UtilValidate.isEmpty(marketingCampaignContactList)) {
                return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignContactListNotFound", UtilMisc.toMap("campaignListId", campaignListId), locale, module);
            }
            // expire it
            if (UtilValidate.isEmpty(marketingCampaignContactList.get("thruDate"))) {
                marketingCampaignContactList.set("thruDate", now);
                delegator.store(marketingCampaignContactList);
            }

            return ServiceUtil.returnSuccess();
        } catch (GenericEntityException e2) {
            return UtilMessage.createAndLogServiceError(e2, module);
        }
    }
}