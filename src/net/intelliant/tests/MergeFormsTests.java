package net.intelliant.tests;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.ModelParam;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

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

		Map<String, Object> inputs = UtilMisc.toMap("userLogin", admin);
		inputs.put("mergeFormName", mergeFormName);
		inputs.put("subject", subject);
		inputs.put("scheduleAt", scheduleAt);
		inputs.put("description", description);
		inputs.put("mergeFormText", mergeFormText);
		inputs.put("showInSelect", showInSelect);

		Map<String, Object> results = runAndAssertServiceSuccess("mailer.createMergeForm", inputs);
		String mergeFormId = (String) results.get("mergeFormId");

		results = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));

		assertNotNull(results);
		assertEquals(results.get("mergeFormName"), mergeFormName);
		assertEquals(results.get("subject"), subject);
		assertEquals(results.get("description"), description);
		assertEquals(results.get("mergeFormText"), mergeFormText);
		assertEquals(results.get("showInSelect"), showInSelect);
	}
	
	public void testUpdateCampaignTemplateWithScheduleAtChanged() throws GeneralException {
		long currTime = System.currentTimeMillis();
		String templateId = createMergTemplate(null);
		String contactListId1 = createContactListWithTwoRecipients();
		String contactListId2 = createContactListWithTwoRecipients();
		
		String campaignName = "Campaign_" + currTime;
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId1 = createMarketingCampaign(campaignName, templateId, contactListId1, budgetedCost, estimatedCost, currencyUomId);
		List<GenericValue> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		String marketingCampaignId2 = createMarketingCampaign(campaignName, templateId, contactListId2, budgetedCost, estimatedCost, currencyUomId);
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		Map<String, Object> inputs = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", templateId));
		ModelService service = dispatcher.getDispatchContext().getModelService("mailer.updateMergeForm");
		inputs = service.makeValid(inputs, ModelService.IN_PARAM);
		inputs.put("scheduleAt", "2");
		inputs.put("userLogin", admin);
		Map<String, Object> results = runAndAssertServiceSuccess("mailer.updateMergeForm", inputs);
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1, "statusId", "MAILER_SCHEDULED"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2, "statusId", "MAILER_SCHEDULED"));
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
}