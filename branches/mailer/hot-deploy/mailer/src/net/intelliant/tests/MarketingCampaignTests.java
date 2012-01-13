package net.intelliant.tests;

import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.opentaps.tests.OpentapsTestCase;

public class MarketingCampaignTests extends OpentapsTestCase {
	private final static String module = MarketingCampaignTests.class.getName();

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateMarketingCampaign() throws GeneralException {
		String campaignName = "Sample test campaign";
		String fromEmailAddress = "Sample test address";
		String templateId = "10000";
		String contractListId = "10000";
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		Map<String, Object> inputs = UtilMisc.toMap("campaignName", "Sample test campaign");
		inputs.put("userLogin", admin);
		inputs.put("campaignName", campaignName);
		inputs.put("fromEmailAddress", fromEmailAddress);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contractListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMarketingCampaign", inputs);
		String marketingCampaignId = (String) results.get("marketingCampaignId");

		results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("campaignName"), campaignName);
		assertEquals(results.get("budgetedCost"), budgetedCost);
		assertEquals(results.get("estimatedCost"), estimatedCost);
		assertEquals(results.get("currencyUomId"), currencyUomId);

		results = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("fromEmailAddress"), fromEmailAddress);
		assertEquals(results.get("templateId"), templateId);
		assertEquals(results.get("contactListId"), contractListId);
	}

	public void testCreateMarketingCampaignNoTemplateId() throws GeneralException {
		String campaignName = "Sample test campaign";
		String fromEmailAddress = "Sample test address";
		String contractListId = "10000";
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		Map<String, Object> inputs = UtilMisc.toMap("campaignName", "Sample test campaign");
		inputs.put("userLogin", admin);
		inputs.put("campaignName", campaignName);
		inputs.put("fromEmailAddress", fromEmailAddress);
		inputs.put("contactListId", contractListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);

		runAndAssertServiceFailure("mailer.createMarketingCampaign", inputs);
	}
}
