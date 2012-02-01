package net.intelliant.marketing;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class MarketingCampaignServices {
	public static final String module = MarketingCampaignServices.class.getName();
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
		try {			
			GenericValue mergeFormGV = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
			if (UtilValidate.isEmpty(mergeFormGV)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), module);
			}
			ModelService service = dctx.getModelService("createMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("statusId", "MKTG_CAMP_PLANNED");
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

			service = dctx.getModelService("mailer.addContactListToCampaign");
			inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("marketingCampaignId", marketingCampaignId);
			dctx.getDispatcher().runSync(service.name, inputs);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), module);
		}
		return serviceResults;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> updateMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		try {
			GenericValue mergeFormGV = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
			if (UtilValidate.isEmpty(mergeFormGV)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), module);
			}
			
			ModelService service = dctx.getModelService("updateMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			LocalDispatcher dispatcher = dctx.getDispatcher();
			serviceResults = dispatcher.runSync(service.name, inputs);
			if (ServiceUtil.isError(serviceResults)) {
				return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, module);
			}
			
			GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign",UtilMisc.toMap("marketingCampaignId", context.get("marketingCampaignId")));
			mailerMarketingCampaign.set("fromEmailAddress", context.get("fromEmailAddress"));
			mailerMarketingCampaign.set("templateId", context.get("templateId"));
			mailerMarketingCampaign.store();

			return serviceResults;
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), module);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), module);
		}
	}

	/** Add contact list to campaign. */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> addContactListToCampaign(DispatchContext dctx, Map<String, Object> context) {
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
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
				String campaignListId = dctx.getDelegator().getNextSeqId("MailerMarketingCampaignAndContactList");
				Map<String, Object> inputs = UtilMisc.toMap("campaignListId", campaignListId, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId);
				inputs.put("fromDate", UtilDateTime.nowTimestamp());
				inputs.put("createdByUserLogin", userLogin.getString("userLoginId"));
				GenericValue mailerMarketingCampaignContactList = dctx.getDelegator().makeValue("MailerMarketingCampaignAndContactList", inputs);
				mailerMarketingCampaignContactList.create();
				
				ModelService service = dctx.getModelService("mailer.scheduleCampaignsForListMembers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("marketingCampaignId", marketingCampaignId);
				inputs.put("contactListId", contactListId);
				dctx.getDispatcher().runSync(service.name, inputs);
			} else {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaingExists", locale), module);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), module);
		}
		return serviceResults;
	}
	
	/**
	 * Removes a contact list from a Marketing campaign with its tracking code
	 * by expiring those entities - expire the MarketingCampaignContactList
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> removeContactListFromMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		String campaignListId = (String) context.get("campaignListId");
		try {
			GenericValue marketingCampaignContactList = delegator.findByPrimaryKey("MailerMarketingCampaignAndContactList", UtilMisc.toMap("campaignListId", campaignListId));
			if (UtilValidate.isEmpty(marketingCampaignContactList)) {
				return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignContactListNotFound", UtilMisc.toMap("campaignListId", campaignListId), locale, module);
			}
			marketingCampaignContactList.set("thruDate", UtilDateTime.nowTimestamp());
			marketingCampaignContactList.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
			delegator.store(marketingCampaignContactList);
		} catch (GenericEntityException e2) {
			return UtilMessage.createAndLogServiceError(e2, module);
		}
		return ServiceUtil.returnSuccess();
	}
}