package net.intelliant.tests;

import java.sql.Timestamp;
import java.util.Map;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.opentaps.tests.OpentapsTestCase;

public class MailerTests extends OpentapsTestCase {
	protected GenericValue system;
	protected static final String dateOfOperationColumnName = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");

	@Override
	public void setUp() throws Exception {
		super.setUp();
		system = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	protected String createMarketingCampaign(String campaignName, String templateId, String contactListId, Double budgetedCost, Double estimatedCost, String currencyUomId) {
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		
		return createMarketingCampaign(inputs);
	}
	
	protected String createMarketingCampaign(Map<String, Object> inputs) {
		Map<String, Object> results = runAndAssertServiceSuccess("mailer.createMarketingCampaign", inputs);
		return (String) results.get("marketingCampaignId");
	}	

	protected String createContactList() throws GenericEntityException {
		GenericValue contactListTypeGV = EntityUtil.getFirst(delegator.findAll("ContactListType"));
		Map<String, Object> inputs = UtilMisc.toMap("contactListTypeId", contactListTypeGV.getString("contactListTypeId"));
		inputs.put("userLogin", admin);
		inputs.put("contactListName", "CL " + System.currentTimeMillis());
	
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createContactList", inputs);
		return (String) results.get("contactListId");
	}

	protected String createMergeTemplate(Map overrideDefaults) throws GenericEntityException {
		Map<String, Object> defaultInputs = UtilMisc.toMap("mergeFormName", "mergeFormName_" + System.currentTimeMillis());
		defaultInputs.put("scheduleAt", "1");
		defaultInputs.put("mergeFormText", "Sample text");
		defaultInputs.put("userLogin", admin);
		defaultInputs.put("mergeFormTypeId", "EMAIL");
		defaultInputs.put("fromEmailAddress", "test@email.com");
		if (UtilValidate.isNotEmpty(overrideDefaults)) {
			defaultInputs.putAll(overrideDefaults);
		}
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMergeForm", defaultInputs);
		return (String) results.get("mergeFormId");
	}

	protected String createContactListWithTwoRecipients() throws GenericEntityException {
		String contactListId = createContactList();
		/** Manually recipients and the associate them with contact list. */
		String recipientId = delegator.getNextSeqId("MailerRecipient");
		Map columns = UtilMisc.toMap("recipientId", recipientId);
		columns.put(dateOfOperationColumnName, UtilDateTime.nowDate());
		delegator.create("MailerRecipient", columns);
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("recipientId", recipientId, "contactListId", contactListId));
		
		recipientId = delegator.getNextSeqId("MailerRecipient");
		columns = UtilMisc.toMap("recipientId", recipientId);
		columns.put(dateOfOperationColumnName, UtilDateTime.addDaysToTimestamp(new Timestamp(UtilDateTime.nowDate().getTime()), 1));
		delegator.create("MailerRecipient", columns);
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("recipientId", recipientId, "contactListId", contactListId));
		
		return contactListId;
	}
}
