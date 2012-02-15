package net.intelliant.marketing;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorCreatingCampaign", locale), MODULE);
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
			GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", context.get("marketingCampaignId")));
			String oldTemplateId = mailerMarketingCampaign.getString("templateId");
			if (UtilValidate.isNotEmpty(templateId)) {
				mergeFormGV = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", context.get("templateId")));
				if (UtilValidate.isEmpty(mergeFormGV)) {
					return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "invalidTemplateId", locale), MODULE);
				}
				mailerMarketingCampaign.set("templateId", templateId);
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
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorUpdatingCampaign", locale), MODULE);
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
				
				ModelService service = dctx.getModelService("mailer.createCampaignLineForListMembers");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("marketingCampaignId", marketingCampaignId);
				inputs.put("contactListId", contactListId);
				dctx.getDispatcher().runSync(service.name, inputs);
			} else {
				return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaignExists", locale), MODULE);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorAddingContactListToCampaign", locale), MODULE);
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
			Map inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.put("marketingCampaignId", marketingCampaignCL.getString("marketingCampaignId"));
			dctx.getDispatcher().runSync("mailer.cancelCreatedMailers", inputs);
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
			GenericValue templateGV = campaignGV.getRelatedOne("MergeForm");
			if (UtilValidate.isNotEmpty(templateGV)) {
//				TODO do some changes on the template
				String emailSubject = templateGV.getString("subject");
				String emailBodyTemplate = templateGV.getString("mergeFormText");
				String emailFromAddress = templateGV.getString("fromEmailAddress");
				emailBodyTemplate = UtilCommon.parseHtmlAndGenerateCompressedImages(emailBodyTemplate);
				if (Debug.infoOn()) {
					Debug.logInfo("This the email subject - " + emailSubject, MODULE);
					Debug.logInfo("This the email template body - " + emailBodyTemplate, MODULE);
					Debug.logInfo("This the email from address - " + emailFromAddress, MODULE);					
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
		} catch (GenericEntityException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (GenericServiceException e) {
			serviceResults = UtilMessage.createAndLogServiceError(e, MODULE);
		}    	
		try {
			GenericValue mailerMarketingCampaignGV = dctx.getDelegator().findByPrimaryKey("MailerCampaignStatus", UtilMisc.toMap("campaignStatusId", campaignStatusId));
	        if (!ServiceUtil.isError(serviceResults)) {
	        	mailerMarketingCampaignGV.setString("statusId", "MAILER_EXECUTED");
	        	mailerMarketingCampaignGV.set("actualExecutionDateTime", UtilDateTime.nowTimestamp());
	        	mailerMarketingCampaignGV.store();
	        } else {
//	        	TODO do something for error.
	        }
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
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
		EntityListIterator iterator = null;
		try {
			List conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "MAILER_CANCELLED"), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
			if (UtilValidate.isNotEmpty(contactListId)) {
				conditionsList.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
			}
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.cancelCreatedMailers] The conditions >> " + conditions, MODULE);
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
			List conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "MAILER_CANCELLED"), new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId));
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.reScheduleMailers] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
	        List mailersToBeReScheduled = FastList.newInstance();
	        GenericValue mailerCampaignStatusGV = null;
	        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
	        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
	        	String dateOfOperationColumn = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");
	        	if (UtilValidate.isEmpty(dateOfOperationColumn)) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnConfigurationMissing", locale), MODULE);
	        	} else if (relatedRecipientGV.containsKey(dateOfOperationColumn) == false) {
	        		return UtilMessage.createAndLogServiceError(UtilProperties.getMessage(errorResource, "errorDateOfOperationColumnIncorrect", locale), MODULE);
	        	}
	        	Timestamp newDate = UtilDateTime.addDaysToTimestamp(new Timestamp(relatedRecipientGV.getDate(dateOfOperationColumn).getTime()), Integer.valueOf(scheduleAt));
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
			List<String> statusIds = UtilMisc.toList("MAILER_SCHEDULED", "MAILER_EXECUTED");
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
		try {
			/** get all approved and non-expired campaigns. */
			List<EntityExpr> conditionsList = UtilMisc.toList(new EntityExpr("statusId", EntityOperator.EQUALS, "MKTG_CAMP_APPROVED"), EntityUtil.getFilterByDateExpr());
            EntityCondition conditions = new EntityConditionList(conditionsList, EntityOperator.AND);
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.checkIfApprovedCampaignsCanBeMarkedInProgress] The conditions >> " + conditions, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignAndMarketingCampaignAppl", conditions, null, null, null, options);
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
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignAndMarketingCampaignAppl", condition, null, null, null, options);
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
		try {
			/** get all in progress campaigns. */
            EntityCondition condition = new EntityExpr("statusId", EntityOperator.EQUALS, "MKTG_CAMP_COMPLETED");
            if (Debug.infoOn()) {
            	Debug.logInfo("[mailer.checkIfCompletedCampaignsCanBeMarkedInProgress] The conditions >> " + condition, MODULE);
            }
	        EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
	        iterator = delegator.findListIteratorByCondition("MailerMarketingCampaignAndMarketingCampaignAppl", condition, null, null, null, options);
	        GenericValue marketingCampaignGV = null;
	        while ((marketingCampaignGV = (GenericValue) iterator.next()) != null) {
	        	boolean canBeMarkedInProgress = false;
	        	String marketingCampaignId = marketingCampaignGV.getString("marketingCampaignId");
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
}