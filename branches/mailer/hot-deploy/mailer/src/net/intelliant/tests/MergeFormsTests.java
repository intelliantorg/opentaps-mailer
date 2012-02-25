package net.intelliant.tests;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.ModelService;

public class MergeFormsTests extends MailerTests {
	private final static String module = MarketingCampaignTests.class.getName();

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressWarnings("unchecked")
	public void testCreateCampaignTemplate() throws GeneralException {
		Long currTime = System.currentTimeMillis();

		String mergeFormName = "Test form name - " + currTime;
		String subject = "Test subject - " + currTime;
		String scheduleAt = String.valueOf((Math.random() * 10000));
		String description = "Test description - " + currTime;
		String mergeFormText = "Test merge form text - " + currTime;
		String showInSelect = (Math.random() > .5) ? "Y" : "N";
		String mergeFormTypeId = "EMAIL";
		String fromEmailAddress = "e" + currTime + "@email.com";

		Map<String, Object> inputs = UtilMisc.toMap("userLogin", admin);
		inputs.put("mergeFormName", mergeFormName);
		inputs.put("subject", subject);
		inputs.put("scheduleAt", scheduleAt);
		inputs.put("description", description);
		inputs.put("mergeFormText", mergeFormText);
		inputs.put("showInSelect", showInSelect);
		inputs.put("mergeFormTypeId", mergeFormTypeId);
		inputs.put("fromEmailAddress", fromEmailAddress);

		Map<String, Object> results = runAndAssertServiceSuccess("mailer.createMergeForm", inputs);
		String mergeFormId = (String) results.get("mergeFormId");

		results = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));

		assertNotNull(results);
		assertEquals(mergeFormName, results.get("mergeFormName"));
		assertEquals(subject, results.get("subject"));
		assertEquals(description, results.get("description"));
		assertEquals(mergeFormText, results.get("mergeFormText"));
		assertEquals(showInSelect, results.get("showInSelect"));
		assertEquals(mergeFormTypeId, results.get("mergeFormTypeId"));
		assertEquals(fromEmailAddress, results.get("fromEmailAddress"));
	}

	public void testUpdateCampaignTemplateWithScheduleAtChanged() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		long currTime = System.currentTimeMillis();
		String templateId = createMergeTemplate(null);
		String contactListId1 = createContactListWithTwoRecipients();
		String contactListId2 = createContactListWithTwoRecipients();

		String campaignName = "Campaign_" + currTime;
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";

		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId1);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId1 = createMarketingCampaign(inputs);
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		inputs.put("contactListId", contactListId2);
		String marketingCampaignId2 = createMarketingCampaign(inputs);
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		inputs = delegator.findByPrimaryKey("MailerMergeForm", UtilMisc.toMap("mergeFormId", templateId));
		ModelService service = dispatcher.getDispatchContext().getModelService("mailer.updateMergeForm");
		inputs = service.makeValid(inputs, ModelService.IN_PARAM);
		inputs.put("scheduleAt", "2");
		inputs.put("userLogin", admin);
		Map<String, Object> results = runAndAssertServiceSuccess("mailer.updateMergeForm", inputs);
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1, "statusId", "MAILER_HOLD"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2, "statusId", "MAILER_HOLD"));
		for (GenericValue scheduledCampaign : campaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertNotEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
}