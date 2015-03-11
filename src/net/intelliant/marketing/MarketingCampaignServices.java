/*
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 */
package net.intelliant.marketing;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javolution.util.FastList;

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.template.freemarker.FreemarkerUtil;
import org.opentaps.common.util.UtilMessage;

import freemarker.template.TemplateException;

public class MarketingCampaignServices {
	private static final String MODULE = MarketingCampaignServices.class.getName();
	private static final String errorResource = "ErrorLabels";
	private static final String successResource = "UILabels";

	@SuppressWarnings("unchecked")
	private static Map<String, Object> checkDates(Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		TimeZone timeZone = (TimeZone) context.get("timeZone");				
		Timestamp fromDate = (Timestamp) context.get("fromDate");
		Timestamp thruDate = (Timestamp) context.get("thruDate");
		if (thruDate == null || fromDate == null) {
			return null;
		}
		if (thruDate.before(fromDate)) {
			Map<String, Object> messageMap = UtilMisc.toMap("thruDate", UtilDateTime.timeStampToString(thruDate, timeZone, locale));
			messageMap.put("fromDate", UtilDateTime.timeStampToString(fromDate, timeZone, locale));
			return UtilMessage.createServiceError(UtilProperties.getMessage(errorResource, "errorCreatingOrUpdatingCampaignThruDateMustBeGreaterThanFromDate", messageMap, locale), locale);
		}
		return null;
	}

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
			serviceResults = checkDates(context);
			if (ServiceUtil.isError(serviceResults)) {
				return serviceResults;
			}
			GenericValue mergeFormGV = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
			if (UtilValidate.isEmpty(mergeFormGV)) {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), MODULE);
			}
			ModelService service = dctx.getModelService("createMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("statusId", "MKTG_CAMP_PLANNED");
			LocalDispatcher dispatcher = dctx.getDispatcher();
			serviceResults = dispatcher.runSync(service.name, inputs);
			if (ServiceUtil.isError(serviceResults)) {
				return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, MODULE);
			}
			String marketingCampaignId = (String) serviceResults.get("marketingCampaignId");

			inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
			inputs.put("templateId", context.get("templateId"));
			inputs.put("description", context.get("description"));
			GenericValue mailerMarketingCampaign = delegator.makeValue("MailerMarketingCampaign", inputs);
			mailerMarketingCampaign.create();

			service = dctx.getModelService("mailer.addContactListToCampaign");
			inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("marketingCampaignId", marketingCampaignId);
			dctx.getDispatcher().runSync(service.name, inputs);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), locale, MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), locale, MODULE);
		}
		return serviceResults;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> updateMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		LocalDispatcher dispatcher = dctx.getDispatcher();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String statusId = (String) context.get("statusId");
		String templateId = (String) context.get("templateId");
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		GenericValue mergeFormGV = null;
		try {
			serviceResults = checkDates(context);
			if (ServiceUtil.isError(serviceResults)) {
				return serviceResults;
			}
			GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", context.get("marketingCampaignId")));
			String oldTemplateId = mailerMarketingCampaign.getString("templateId");
			if (UtilValidate.isNotEmpty(templateId)) {
				mergeFormGV = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
				if (UtilValidate.isEmpty(mergeFormGV)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), MODULE);
				}
				mailerMarketingCampaign.set("templateId", templateId);
			}
			if (context.containsKey("description")) {
				mailerMarketingCampaign.put("description", context.get("description"));
			}
			mailerMarketingCampaign.store();
			
			GenericValue marketingCampaign = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", context.get("marketingCampaignId")));
			String oldStatusId = marketingCampaign.getString("statusId");

			ModelService service = dctx.getModelService("updateMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			serviceResults = dispatcher.runSync(service.name, inputs);
			if (ServiceUtil.isError(serviceResults)) {
				return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, MODULE);
			}
			
			boolean isCampaignCancelled = false;
			if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_CANCELLED")) {
				isCampaignCancelled = true;
				service = dctx.getModelService("mailer.cancelCreatedMailers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
				}
			} else if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_INPROGRESS")) {
				service = dctx.getModelService("mailer.scheduleAllMailers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
				}
			} else if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_APPROVED")) {
				service = dctx.getModelService("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
				}
			} else if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_COMPLETED")) {
				service = dctx.getModelService("mailer.checkIfCompletedCampaignsCanBeMarkedInProgress");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
				}
			}
			/** No point executing this campaign was cancelled. */
			if (UtilValidate.isNotEmpty(templateId) && !UtilValidate.areEqual(oldTemplateId, templateId) && !isCampaignCancelled) {
				/** Check if user is actually update template Id as well. */				
				service = dctx.getModelService("mailer.reScheduleMailers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("scheduleAt", mergeFormGV.getString("scheduleAt"));
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
				}
			}
			if (Debug.infoOn()) {
				Debug.logInfo("[mailer.updateMarketingCampaign] oldStatusId >> " + oldStatusId, MODULE);
				Debug.logInfo("[mailer.updateMarketingCampaign] statusId >> " + statusId, MODULE);
			}
			if (UtilValidate.isNotEmpty(statusId) && !UtilValidate.areEqual(oldStatusId, statusId)) {
				GenericValue statusVC = delegator.findByPrimaryKey("StatusValidChange", UtilMisc.toMap("statusId", oldStatusId, "statusIdTo", statusId));
				if (UtilValidate.isNotEmpty(statusVC) && UtilValidate.isNotEmpty(statusVC.getString("postChangeMessage"))) {
					serviceResults.put(ModelService.SUCCESS_MESSAGE, UtilProperties.getMessage(successResource, statusVC.getString("postChangeMessage"), locale));
					if (Debug.infoOn()) {
						Debug.logInfo("[mailer.updateMarketingCampaign] postChangeMessage >> " + UtilProperties.getMessage(successResource, statusVC.getString("postChangeMessage"), locale), MODULE);
					}
				}
			}
			return serviceResults;
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), locale, MODULE);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), locale, MODULE);
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
		List<EntityCondition> andConditions = UtilMisc.toList(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
		andConditions.add(EntityUtil.getFilterByDateExpr());
		andConditions.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
		EntityConditionList conditions = new EntityConditionList(andConditions, EntityOperator.AND);
		try {
			List<GenericValue> marketingCampaignContactLists = dctx.getDelegator().findByCondition("MailerMarketingCampaignAndContactList", conditions, null, UtilMisc.toList("fromDate"));
			if (UtilValidate.isEmpty(marketingCampaignContactLists)) {
				String campaignListId = dctx.getDelegator().getNextSeqId("MailerMarketingCampaignAndContactList");
				Map<String, Object> inputs = UtilMisc.toMap("campaignListId", campaignListId, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId);
				inputs.put("fromDate", UtilDateTime.nowTimestamp());
				inputs.put("createdByUserLogin", userLogin.getString("userLoginId"));
				GenericValue mailerMarketingCampaignContactList = dctx.getDelegator().makeValue("MailerMarketingCampaignAndContactList", inputs);
				mailerMarketingCampaignContactList.create();
				serviceResults.put("campaignListId", campaignListId);
				
				ModelService service = dctx.getModelService("mailer.createCampaignLineForListMembers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("marketingCampaignId", marketingCampaignId);
				inputs.put("contactListId", contactListId);
				dctx.getDispatcher().runSync(service.name, inputs);
			} else {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaignExists", locale), MODULE);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), locale, MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), locale, MODULE);
		}
		return serviceResults;
	}
	
	/**
	 * Removes a contact list from a Marketing campaign
	 * by expiring those entities - expire the MailerMarketingCampaignAndContactList
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> removeContactListFromMarketingCampaign(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");
		String campaignListId = (String) context.get("campaignListId");
		try {
			GenericValue marketingCampaignCL = delegator.findByPrimaryKey("MailerMarketingCampaignAndContactList", UtilMisc.toMap("campaignListId", campaignListId));
			if (UtilValidate.isEmpty(marketingCampaignCL)) {
				return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignContactListNotFound", UtilMisc.toMap("campaignListId", campaignListId), locale, MODULE);
			}
			marketingCampaignCL.set("thruDate", UtilDateTime.nowTimestamp());
			marketingCampaignCL.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
			delegator.store(marketingCampaignCL);
			ModelService service = dctx.getModelService("mailer.cancelCreatedMailers");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("marketingCampaignId", marketingCampaignCL.getString("marketingCampaignId"));
			dctx.getDispatcher().runSync(service.name, inputs);
		} catch (GenericEntityException gee) {
			return UtilMessage.createAndLogServiceError(gee, MODULE);
		} catch (GenericServiceException gse) {
			return UtilMessage.createAndLogServiceError(gse, MODULE);
		}
		return ServiceUtil.returnSuccess();
	}

	/**
	 * Will be used to schedule e-mails.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> executeEmailMailers(DispatchContext dctx, Map<String, Object> context) {
        int scheduledEmailsCount = 0;
		Locale locale = (Locale) context.get("locale");
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		EntityListIterator iterator = null;
		try {
			GenericValue campaignGV = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
//			find out the template
			GenericValue templateGV = campaignGV.getRelatedOne("MailerMergeForm");
			if (UtilValidate.isNotEmpty(templateGV)) {
//				do some changes on the template
				String emailSubject = templateGV.getString("subject");
				String emailBodyTemplate = templateGV.getString("mergeFormText");
				String emailFromAddress = templateGV.getString("fromEmailAddress");
				emailBodyTemplate = UtilCommon.parseHtmlAndGenerateCompressedImages(emailBodyTemplate);
				if (Debug.infoOn()) {
					Debug.logInfo("[mailer.executeEmailMailers] This the email subject - " + emailSubject, MODULE);
					Debug.logInfo("[mailer.executeEmailMailers] This the email template body - " + emailBodyTemplate, MODULE);
					Debug.logInfo("[mailer.executeEmailMailers] This the email from address - " + emailFromAddress, MODULE);					
				}
	            EntityCondition conditions = new EntityConditionList( 
            		UtilMisc.toList(
        				new EntityExpr("statusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"),
        				new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId),
                        new EntityExpr("scheduledForDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp())
            		), EntityOperator.AND);
	            if (Debug.infoOn()) {
	            	Debug.logInfo("[mailer.executeEmailMailers] The conditions >> " + conditions, MODULE);
	            }
		        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
		        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
		        GenericValue mailerCampaignStatusGV = null;
//				Iterate over each scheduled mailer,
		        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
		        	scheduledEmailsCount++;
		        	String campaignStatusId = mailerCampaignStatusGV.getString("campaignStatusId");
//		        	TODO use a view instead of finding related one.
		        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
		        	String sendToEmailColumn = UtilProperties.getPropertyValue("mailer", "mailer.sendToEMailColumn");
		        	if (UtilValidate.isEmpty(sendToEmailColumn)) {
		        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorSendToEmailColumnConfigurationMissing", locale), MODULE);
		        	} else if (relatedRecipientGV.containsKey(sendToEmailColumn) == false) {
		        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorSendToEmailColumnIncorrect", locale), MODULE);
		        	}
		        	StringWriter emailBodyContent = new StringWriter();
		        	FreemarkerUtil.renderTemplateWithTags("relatedRecipientGV#" + relatedRecipientGV.getString("recipientId"), emailBodyTemplate, relatedRecipientGV.getAllFields(), emailBodyContent, false, false);
//		        	prepare Comm. Event, and send email.
					ModelService service = dctx.getModelService("createCommunicationEvent");
	                Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	                serviceInputs.put("entryDate", UtilDateTime.nowTimestamp());
	                serviceInputs.put("communicationEventTypeId", "EMAIL_COMMUNICATION");
	                serviceInputs.put("subject", emailSubject);
	                serviceInputs.put("contentMimeTypeId", "text/html");
	                serviceInputs.put("content", emailBodyContent.toString());
	                serviceInputs.put("fromString", emailFromAddress);
//	              	USE recipient email address.
	                serviceInputs.put("toString", relatedRecipientGV.getString(sendToEmailColumn));
	                serviceInputs.put("partyIdFrom", "_NA_");
	                Map<String, Object> serviceResults = dctx.getDispatcher().runSync("createCommunicationEvent", serviceInputs);
	                if (ServiceUtil.isError(serviceResults)) {
	                    return UtilMessage.createAndLogServiceError(serviceResults, MODULE);
	                }
	                String communicationEventId = (String) serviceResults.get("communicationEventId");
	                service = dctx.getModelService("mailer.sendEmailMailer");
	                serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	                serviceInputs.put("communicationEventId", communicationEventId);
	                serviceInputs.put("campaignStatusId", campaignStatusId);
	                dctx.getDispatcher().runAsync(service.name, serviceInputs);
	    			if (Debug.infoOn()) {
	    				Debug.logInfo("[mailer.executeEmailMailers] Executing sendEmailMailer with following parameters - " + serviceInputs, MODULE);
	    			}
	            }
			} else {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorNoTemplateWithCampaign", locale), MODULE);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (TemplateException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (IOException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		Map<String, Object> messageMap = UtilMisc.toMap("scheduledEmailsCount", scheduledEmailsCount);
		String successMessage = UtilProperties.getMessage(successResource, "successEmailsScheduled", messageMap, locale);
		return ServiceUtil.returnSuccess(successMessage);
	}
	
	/**
	 * Will be used to send e-mails.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendEmailMailer(DispatchContext dctx, Map<String, Object> context) {
		Map<String,Object> serviceResults = null; 
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String communicationEventId = (String) context.get("communicationEventId");
		String campaignStatusId = (String) context.get("campaignStatusId");
		GenericValue commEventGV;
		try {
			GenericValue scheduledEmailOnly = EntityUtil.getFirst(dctx.getDelegator().findByAnd("MailerCampaignStatus", UtilMisc.toMap("campaignStatusId", campaignStatusId, "statusId", "MAILER_SCHEDULED")));
			if (UtilValidate.isEmpty(scheduledEmailOnly)) {
				Debug.logWarning("[mailer.sendEmailMailer] This mailer may have already been executed, not executing again.", MODULE);
				return ServiceUtil.returnSuccess();
			}
			commEventGV = dctx.getDelegator().findByPrimaryKey("CommunicationEvent", UtilMisc.toMap("communicationEventId", communicationEventId));
			ModelService service = dctx.getModelService("sendMail");
			Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	        serviceInputs.put("partyId", "_NA_");
	        serviceInputs.put("sendFrom", commEventGV.getString("fromString"));
	        serviceInputs.put("subject", commEventGV.getString("subject"));
	        serviceInputs.put("body", commEventGV.getString("content"));
	        serviceInputs.put("contentType", commEventGV.getString("contentMimeTypeId"));
	        serviceInputs.put("sendTo", commEventGV.getString("toString"));
	        serviceInputs.put("userLogin", userLogin);
			if (Debug.infoOn()) {
				Debug.logInfo("[mailer.sendEmailMailer] Executing sendMail with following parameters - " + serviceInputs, MODULE);
			}
	        serviceResults = dctx.getDispatcher().runSync(service.name, serviceInputs);
	        if (!ServiceUtil.isError(serviceResults)) {
	        	dctx.getDispatcher().runSync("mailer.markMailersAsExecuted", UtilMisc.toMap("campaignStatusIds", UtilMisc.toList(campaignStatusId)));
	        } else {
	        	Debug.logError("[mailer.sendEmailMailer] Encountered errors while sending email - " + serviceResults, MODULE);
	        }
		} catch (GenericEntityException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (GenericServiceException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, MODULE);
		}    	
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to cancel scheduled mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> cancelCreatedMailers(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		String contactListId = (String) context.get("contactListId");
		String recipientId = (String) context.get("recipientId");
		List<String> validStatuses = UtilMisc.toList("MAILER_EXECUTED", "MAILER_CANCELLED");
		EntityListIterator iterator = null;
		boolean atleastOnePresent = false;
		try {
			List<EntityCondition> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.NOT_IN, validStatuses));
			if (UtilValidate.isNotEmpty(marketingCampaignId)) {
				conditionsList.add(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
				atleastOnePresent = true;
			}
			if (UtilValidate.isNotEmpty(contactListId)) {
				conditionsList.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
				atleastOnePresent = true;
			}
			if (UtilValidate.isNotEmpty(recipientId)) {
				conditionsList.add(new EntityExpr("recipientId", EntityOperator.EQUALS, recipientId));
				atleastOnePresent = true;
			}
			if (!atleastOnePresent) {
				return ServiceUtil.returnError("Atleast one parameter out of marketingCampaignId, contactListId, recipientId must be specified.");
			}
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.cancelCreatedMailers] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List<GenericValue> mailersToBeCancelled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	mailerCampaignStatusGV.set("statusId", "MAILER_CANCELLED");
	        	mailersToBeCancelled.add(mailerCampaignStatusGV);
            }
	        if (UtilValidate.isNotEmpty(mailersToBeCancelled)) {
	        	if (Debug.infoOn()) {
	        		Debug.logInfo("[mailer.cancelCreatedMailers] About to cancel " + mailersToBeCancelled.size() + " mailers.", MODULE);
	        	}
	        	delegator.storeAll(mailersToBeCancelled);
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to re-schedule mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> reScheduleMailers(DispatchContext dctx, Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		String scheduleAt = (String) context.get("scheduleAt");
		EntityListIterator iterator = null;
		try {
			List<EntityCondition> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "MAILER_CANCELLED"), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.reScheduleMailers] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List<GenericValue> mailersToBeReScheduled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
	        	String dateOfOperationColumn = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");
	        	if (UtilValidate.isEmpty(dateOfOperationColumn)) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnConfigurationMissing", locale), MODULE);
	        	} else if (relatedRecipientGV.containsKey(dateOfOperationColumn) == false) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnIncorrect", locale), MODULE);
	        	}
	        	Timestamp newDate = UtilCommon.addDaysToTimestamp(UtilDateTime.getTimestamp(relatedRecipientGV.getDate(dateOfOperationColumn).getTime()), Double.parseDouble(scheduleAt));
	        	mailerCampaignStatusGV.set("scheduledForDate", newDate);	        	
	        	mailersToBeReScheduled.add(mailerCampaignStatusGV);
            }
	        if (UtilValidate.isNotEmpty(mailersToBeReScheduled)) {
	        	if (Debug.infoOn()) {
	        		Debug.logInfo("[mailer.reScheduleMailers] About to re-schedule " + mailersToBeReScheduled.size() + " mailers.", MODULE);
	        	}
	        	delegator.storeAll(mailersToBeReScheduled);
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to schedule all mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> scheduleAllMailers(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		EntityListIterator iterator = null;
		try {
			/** No need to change status if mailer exists in below mentioned status, hence these filters. */ 
			List<String> statusIds = UtilMisc.toList("MAILER_SCHEDULED", "MAILER_EXECUTED", "MAILER_CANCELLED");
			List<EntityExpr> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.NOT_IN, statusIds), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.scheduleAllMailers] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List<GenericValue> mailersToBeScheduled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	mailerCampaignStatusGV.set("statusId", "MAILER_SCHEDULED");
	        	mailersToBeScheduled.add(mailerCampaignStatusGV);
            }
	        if (UtilValidate.isNotEmpty(mailersToBeScheduled)) {
	        	if (Debug.infoOn()) {
	        		Debug.logInfo("[mailer.scheduleAllMailers] About to schedule " + mailersToBeScheduled.size() + " mailers.", MODULE);
	        	}
	        	delegator.storeAll(mailersToBeScheduled);
	        } else {
	            if (Debug.infoOn()) {
	            	Debug.logInfo("[mailer.scheduleAllMailers] No Mailers will be scheduled..", MODULE);
	            }
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to check if campaign can be set to "in progress".
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> checkIfApprovedCampaignsCanBeMarkedInProgress(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		EntityListIterator iterator = null;
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		try {
			/** get all approved and non-expired campaigns. */
			List<EntityExpr> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MKTG_CAMP_APPROVED"), EntityUtil.getFilterByDateExpr());
			/** Use this to check for a particular marketing campaign. */
			if (UtilValidate.isNotEmpty(marketingCampaignId)) {
				conditionsList.add(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
			}
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.checkIfApprovedCampaignsCanBeMarkedInProgress] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignDetailsView", conditions, null, null, null, options);
	        GenericValue marketingCampaignGV = null;
	        while ((marketingCampaignGV = (GenericValue) iterator.next()) != null) {
	        	Debug.logWarning(String.format("[mailer.checkIfApprovedCampaignsCanBeMarkedInProgress] Setting campaign [%1$s] to in progress..", marketingCampaignGV.getString("marketingCampaignId")), MODULE);
				try {
					ModelService service = dctx.getModelService("mailer.updateMarketingCampaign");
					Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
			        serviceInputs.put("statusId", "MKTG_CAMP_INPROGRESS");
			        serviceInputs.put("marketingCampaignId", marketingCampaignGV.getString("marketingCampaignId"));
					if (Debug.infoOn()) {
						Debug.logInfo("[mailer.checkIfApprovedCampaignsCanBeMarkedInProgress] Executing mailer.updateMarketingCampaign with following parameters - " + serviceInputs, MODULE);
					}
			        dctx.getDispatcher().runSync(service.name, serviceInputs);
				} catch (GenericServiceException e) {
					return UtilMessage.createAndLogServiceError(e, MODULE);
				}
            }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to check if campaign can be set to "in progress".
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> checkIfInProgressCampaignsCanBeMarkedComplete(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		EntityListIterator iterator = null;
		try {
			/** get all in progress campaigns. */
            EntityCondition condition = new EntityExpr("statusId", EntityOperator.EQUALS, "MKTG_CAMP_INPROGRESS");
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.checkIfInProgressCampaignsCanBeMarkedComplete] The conditions >> " + condition, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignDetailsView", condition, null, null, null, options);
	        GenericValue marketingCampaignGV = null;
	        while ((marketingCampaignGV = (GenericValue) iterator.next()) != null) {
	        	boolean canBeMarkedComplete = false;
	        	String marketingCampaignId = marketingCampaignGV.getString("marketingCampaignId");
	        	Timestamp thruDate = marketingCampaignGV.getTimestamp("thruDate");
	        	Timestamp now = UtilDateTime.nowTimestamp();
	        	if (thruDate != null && (now.after(thruDate) || thruDate.equals(now))) {
	        		canBeMarkedComplete = true;
	        	} else {
	        		long count = UtilCommon.countScheduledCampaignLines(delegator, null, marketingCampaignId);
	        		if (count == 0) {
	        			canBeMarkedComplete = true;
	        		} else {
	        			if (Debug.infoOn()) {
	        				Debug.logInfo(String.format("[mailer.checkIfInProgressCampaignsCanBeMarkedComplete] campaign [%1$s] still has [%2$s] scheduled, will NOT be marked complete..", marketingCampaignId, count), MODULE);
	        			}
	        		}
	        	}
	        	if (canBeMarkedComplete) {
	        		Debug.logWarning(String.format("[mailer.checkIfInProgressCampaignsCanBeMarkedComplete] Setting campaign [%1$s] to complete..", marketingCampaignGV.getString("marketingCampaignId")), MODULE);
	        		try {
	        			ModelService service = dctx.getModelService("mailer.updateMarketingCampaign");
	        			Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	        			serviceInputs.put("statusId", "MKTG_CAMP_COMPLETED");
	        			serviceInputs.put("marketingCampaignId", marketingCampaignId);
	        			if (Debug.infoOn()) {
	        				Debug.logInfo("[mailer.checkIfInProgressCampaignsCanBeMarkedComplete] Executing mailer.updateMarketingCampaign with following parameters - " + serviceInputs, MODULE);
	        			}
	        			dctx.getDispatcher().runSync(service.name, serviceInputs);
	        		} catch (GenericServiceException e) {
	        			return UtilMessage.createAndLogServiceError(e, MODULE);
	        		}
	        	}
            }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}	
	
	/**
	 * Will be used to check if completed campaign can be set to in progress.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> checkIfCompletedCampaignsCanBeMarkedInProgress(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		EntityListIterator iterator = null;
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		try {
			/** get all in completed campaigns. */
			List<EntityExpr> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MKTG_CAMP_COMPLETED"), EntityUtil.getFilterByDateExpr());
			/** Use this to check for a particular marketing campaign. */
			if (UtilValidate.isNotEmpty(marketingCampaignId)) {
				conditionsList.add(new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
			}
			EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.checkIfCompletedCampaignsCanBeMarkedInProgress] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignDetailsView", conditions, null, null, null, options);
	        GenericValue marketingCampaignGV = null;
	        while ((marketingCampaignGV = (GenericValue) iterator.next()) != null) {
	        	boolean canBeMarkedInProgress = false;
	        	marketingCampaignId = marketingCampaignGV.getString("marketingCampaignId");
	        	Timestamp thruDate = marketingCampaignGV.getTimestamp("thruDate");
	        	Timestamp now = UtilDateTime.nowTimestamp();
        		long count = UtilCommon.countScheduledCampaignLines(delegator, null, marketingCampaignId);
	        	if (thruDate != null && (now.before(thruDate) || thruDate.equals(now)) && count > 0) {
	        		canBeMarkedInProgress = true;
        			if (Debug.infoOn()) {
        				Debug.logInfo(String.format("[mailer.checkIfCompletedCampaignsCanBeMarkedInProgress] campaign [%1$s] still has [%2$s] scheduled, will NOT be marked in progress..", marketingCampaignId, count), MODULE);
        			}
	        	}
	        	if (canBeMarkedInProgress) {
	        		Debug.logWarning(String.format("[mailer.checkIfCompletedCampaignsCanBeMarkedInProgress] Setting campaign [%1$s] to in progress..", marketingCampaignGV.getString("marketingCampaignId")), MODULE);
	        		try {
	        			ModelService service = dctx.getModelService("mailer.updateMarketingCampaign");
	        			Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	        			serviceInputs.put("statusId", "MKTG_CAMP_INPROGRESS");
	        			serviceInputs.put("marketingCampaignId", marketingCampaignId);
	        			if (Debug.infoOn()) {
	        				Debug.logInfo("[mailer.checkIfCompletedCampaignsCanBeMarkedInProgress] Executing mailer.updateMarketingCampaign with following parameters - " + serviceInputs, MODULE);
	        			}
	        			dctx.getDispatcher().runSync(service.name, serviceInputs);
	        		} catch (GenericServiceException e) {
	        			return UtilMessage.createAndLogServiceError(e, MODULE);
	        		}
	        	}
            }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} finally {
			if (iterator != null) {
				try {
					iterator.close();
				} catch (GenericEntityException e) {
					iterator = null;
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to mark mailer as executed.	 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> markMailersAsExecuted(DispatchContext dctx, Map<String, Object> context) {
		List campaignStatusIds = (List) context.get("campaignStatusIds");
		if (Debug.infoOn()) {
			Debug.logInfo("[mailer.markMailersAsExecuted] campaignStatusIds >> " + campaignStatusIds, MODULE);
		}
		if (UtilValidate.isNotEmpty(campaignStatusIds)) {
			for (Object campaignStatusId : campaignStatusIds) {
				try {
					GenericValue mailerMarketingCampaignGV = dctx.getDelegator().findByPrimaryKey("MailerCampaignStatus", UtilMisc.toMap("campaignStatusId", campaignStatusId));
			    	mailerMarketingCampaignGV.setString("statusId", "MAILER_EXECUTED");
			    	mailerMarketingCampaignGV.set("actualExecutionDateTime", UtilDateTime.nowTimestamp());
			    	mailerMarketingCampaignGV.store();
				} catch (GenericEntityException e) {
					return UtilMessage.createAndLogServiceError(e, MODULE);
				}
			}
		}
		return ServiceUtil.returnSuccess();
	}
}