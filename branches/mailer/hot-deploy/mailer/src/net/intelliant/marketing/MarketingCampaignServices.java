package net.intelliant.marketing;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastList;

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
		LocalDispatcher dispatcher = dctx.getDispatcher();
		GenericDelegator delegator = dctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String statusId = (String) context.get("statusId");
		String templateId = (String) context.get("templateId");
		Map<String, Object> serviceResults = ServiceUtil.returnSuccess();
		GenericValue mergeFormGV = null;
		try {
			if (UtilValidate.isNotEmpty(templateId)) {
				mergeFormGV = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
				if (UtilValidate.isEmpty(mergeFormGV)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), module);
				}
			}
			ModelService service = dctx.getModelService("updateMarketingCampaign");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_CANCELLED")) {
				inputs.put("thruDate", UtilDateTime.nowTimestamp());
			} 
			serviceResults = dispatcher.runSync(service.name, inputs);
			if (ServiceUtil.isError(serviceResults)) {
				return UtilMessage.createAndLogServiceError(serviceResults, service.name, locale, module);
			}
			
			GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", context.get("marketingCampaignId")));
			String oldTemplateId = mailerMarketingCampaign.getString("templateId");
			mailerMarketingCampaign.set("templateId", templateId);
			mailerMarketingCampaign.store();

			if (UtilValidate.isNotEmpty(statusId) && statusId.equals("MKTG_CAMP_CANCELLED")) {
				service = dctx.getModelService("mailer.cancelScheduledMailers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), module);
				}
			} else if (UtilValidate.isNotEmpty(templateId) && !UtilValidate.areEqual(oldTemplateId, templateId)) {
				/** No point executing this campaign was cancelled. */
				/** Check if user is actually update template Id as well. */				
				service = dctx.getModelService("mailer.reScheduleMailers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("scheduleAt", mergeFormGV.getString("scheduleAt"));
				serviceResults = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(serviceResults)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), module);
				}
			}
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
				serviceResults.put("campaignListId", campaignListId);
				
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
				return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignContactListNotFound", UtilMisc.toMap("campaignListId", campaignListId), locale, module);
			}
			marketingCampaignCL.set("thruDate", UtilDateTime.nowTimestamp());
			marketingCampaignCL.set("lastModifiedByUserLogin", userLogin.getString("userLoginId"));
			delegator.store(marketingCampaignCL);
			ModelService service = dctx.getModelService("mailer.cancelScheduledMailers");
			Map inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("marketingCampaignId", marketingCampaignCL.getString("marketingCampaignId"));
			dctx.getDispatcher().runSync("mailer.cancelScheduledMailers", inputs);
		} catch (GenericEntityException gee) {
			return UtilMessage.createAndLogServiceError(gee, module);
		} catch (GenericServiceException gse) {
			return UtilMessage.createAndLogServiceError(gse, module);
		}
		return ServiceUtil.returnSuccess();
	}

	/**
	 * Will be used to schedule e-mails.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> executeEmailMailers(DispatchContext dctx, Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		EntityListIterator iterator = null;
		try {
			GenericValue campaignGV = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
//			find out the template
			GenericValue templateGV = campaignGV.getRelatedOne("MergeForm");
			if (UtilValidate.isNotEmpty(templateGV)) {
//				TODO do some changes on the template
				String emailSubject = templateGV.getString("subject");
				String emailBodyTemplate = templateGV.getString("mergeFormText");
				String emailFromAddress = templateGV.getString("fromEmailAddress");
				if (Debug.infoOn()) {
					Debug.logInfo("This the email subject - " + emailSubject, module);
					Debug.logInfo("This the email template body - " + emailBodyTemplate, module);
					Debug.logInfo("This the email from address - " + emailFromAddress, module);					
				}
	            EntityCondition conditions = new EntityConditionList( 
            		UtilMisc.toList(
        				new EntityExpr("emailStatusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"),
        				new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId),
                        new EntityExpr("scheduledForDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp())
            		), EntityOperator.AND);
	            if (Debug.infoOn()) {
	            	Debug.logInfo("The campaign status conditions >> " + conditions, module);
	            }
		        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
		        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
		        GenericValue mailerCampaignStatusGV = null;
//				Iterate over each scheduled mailer,
		        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
		        	String campaignStatusId = mailerCampaignStatusGV.getString("campaignStatusId");
//		        	TODO use a view instead of finding related one.
		        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
		        	String sendToEmailColumn = UtilProperties.getPropertyValue("mailer", "mailer.sendToEMailColumn");
		        	if (UtilValidate.isEmpty(sendToEmailColumn)) {
		        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorSendToEmailColumnConfigurationMissing", locale), module);
		        	} else if (relatedRecipientGV.containsKey(sendToEmailColumn) == false) {
		        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorSendToEmailColumnIncorrect", locale), module);
		        	}
		        	StringWriter writer = new StringWriter();
//		        	and prepare email content,
		        	FreemarkerUtil.renderTemplateWithTags("relatedRecipientGV#" + relatedRecipientGV.getString("recipientId"), emailBodyTemplate, relatedRecipientGV.getAllFields(), writer, false, false);
		            String emailBodyContent = writer.toString();
//		        	prepare Comm. Event, and send email.
					ModelService service = dctx.getModelService("createCommunicationEvent");
	                Map<String, Object> serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	                serviceInputs.put("entryDate", UtilDateTime.nowTimestamp());
	                serviceInputs.put("communicationEventTypeId", "EMAIL_COMMUNICATION");
	                serviceInputs.put("subject", emailSubject);
	                serviceInputs.put("contentMimeTypeId", "text/html");
	                serviceInputs.put("content", emailBodyContent);
	                serviceInputs.put("fromString", emailFromAddress);
//	              	USE recipient email address.
	                serviceInputs.put("toString", relatedRecipientGV.getString(sendToEmailColumn));
	                serviceInputs.put("partyIdFrom", "_NA_");
	                Map<String, Object> serviceResults = dctx.getDispatcher().runSync("createCommunicationEvent", serviceInputs);
	                if (ServiceUtil.isError(serviceResults)) {
	                    return UtilMessage.createAndLogServiceError(serviceResults, module);
	                }
	                String communicationEventId = (String) serviceResults.get("communicationEventId");
	                service = dctx.getModelService("mailer.sendEmailMailer");
	                serviceInputs = service.makeValid(context, ModelService.IN_PARAM);
	                serviceInputs.put("communicationEventId", communicationEventId);
	                serviceInputs.put("campaignStatusId", campaignStatusId);
	                dctx.getDispatcher().runAsync(service.name, serviceInputs);
	            }
			} else {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorNoTemplateWithCampaign", locale), module);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, module);
		} catch (TemplateException e) {
			return UtilMessage.createAndLogServiceError(e, module);
		} catch (IOException e) {
			return UtilMessage.createAndLogServiceError(e, module);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, module);
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
				Debug.logInfo("Executing sendMail with following parameters - " + serviceInputs, module);
			}
	        serviceResults = dctx.getDispatcher().runSync(service.name, serviceInputs);
		} catch (GenericEntityException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, module);
		} catch (GenericServiceException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, module);
		}    	
		try {
			GenericValue mailerMarketingCampaignGV = dctx.getDelegator().findByPrimaryKey("MailerCampaignStatus", UtilMisc.toMap("campaignStatusId", campaignStatusId));
	        if (!ServiceUtil.isError(serviceResults)) {
	        	mailerMarketingCampaignGV.setString("statusId", "MAILER_EXECUTED");
	        	mailerMarketingCampaignGV.store();
	        } else {
//	        	TODO do something for error.
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, module);
		}
		return ServiceUtil.returnSuccess();
	}
	
	/**
	 * Will be used to cancel scheduled mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> cancelScheduledMailers(DispatchContext dctx, Map<String, Object> context) {
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		String contactListId = (String) context.get("contactListId");
		EntityListIterator iterator = null;
		try {
			List conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
			if (UtilValidate.isNotEmpty(contactListId)) {
				conditionsList.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
			}
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("The conditions >> " + conditions, module);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List mailersToBeCancelled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	mailerCampaignStatusGV.set("statusId", "MAILER_CANCELLED");
	        	mailersToBeCancelled.add(mailerCampaignStatusGV);
            }
	        if (UtilValidate.isNotEmpty(mailersToBeCancelled)) {
	        	if (Debug.infoOn()) {
	        		Debug.logInfo("About to cancel " + mailersToBeCancelled.size() + " mailers.", module);
	        	}
	        	delegator.storeAll(mailersToBeCancelled);
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, module);
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
	 * Will be used to cancel scheduled mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> reScheduleMailers(DispatchContext dctx, Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		GenericDelegator delegator = dctx.getDelegator();
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		String scheduleAt = (String) context.get("scheduleAt");
		EntityListIterator iterator = null;
		try {
			List conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("The conditions >> " + conditions, module);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List mailersToBeReScheduled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
	        	String dateOfOperationColumn = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");
	        	if (UtilValidate.isEmpty(dateOfOperationColumn)) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnConfigurationMissing", locale), module);
	        	} else if (relatedRecipientGV.containsKey(dateOfOperationColumn) == false) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnIncorrect", locale), module);
	        	}
	        	Timestamp newDate = UtilDateTime.addDaysToTimestamp(new Timestamp(relatedRecipientGV.getDate(dateOfOperationColumn).getTime()), Integer.valueOf(scheduleAt));
	        	mailerCampaignStatusGV.set("scheduledForDate", newDate);	        	
	        	mailersToBeReScheduled.add(mailerCampaignStatusGV);
            }
	        if (UtilValidate.isNotEmpty(mailersToBeReScheduled)) {
	        	if (Debug.infoOn()) {
	        		Debug.logInfo("About to re-schedule " + mailersToBeReScheduled.size() + " mailers.", module);
	        	}
	        	delegator.storeAll(mailersToBeReScheduled);
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, module);
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
}