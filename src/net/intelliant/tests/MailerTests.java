package net.intelliant.tests;

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
	
	static final String dateOfOperationColumnName = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	protected String createMarketingCampaign(String campaignName, String fromEmailAddress, String templateId, String contactListId, Double budgetedCost, Double estimatedCost, String currencyUomId) {
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("fromEmailAddress", fromEmailAddress);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
	
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMarketingCampaign", inputs);
		String marketingCampaignId = (String) results.get("marketingCampaignId");
		return marketingCampaignId;
	}

	protected String createContactList() throws GenericEntityException {
		GenericValue contactListTypeGV = EntityUtil.getFirst(delegator.findAll("ContactListType"));
		Map<String, Object> inputs = UtilMisc.toMap("contactListTypeId", contactListTypeGV.getString("contactListTypeId"));
		inputs.put("userLogin", admin);
		inputs.put("contactListName", "CL " + System.currentTimeMillis());
	
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createContactList", inputs);
		return (String) results.get("contactListId");
	}

	protected String createMergTemplate(Map overriddingInputs) throws GenericEntityException {
		Map<String, Object> inputs = UtilMisc.toMap("mergeFormName", "mergeFormName_" + System.currentTimeMillis());
		inputs.put("scheduleAt", "1");
		inputs.put("mergeFormText", "Sample text");
		inputs.put("userLogin", admin);
		if (UtilValidate.isNotEmpty(overriddingInputs)) {
			inputs.putAll(overriddingInputs);
		}
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMergeForm", inputs);
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
		columns.put(dateOfOperationColumnName, UtilDateTime.nowDate());
		delegator.create("MailerRecipient", columns);
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("recipientId", recipientId, "contactListId", contactListId));
		
		return contactListId;
	}
}
