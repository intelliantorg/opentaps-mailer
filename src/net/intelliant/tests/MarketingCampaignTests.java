package net.intelliant.tests;

import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.util.EntityUtil;
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

	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaign() throws GeneralException {
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String fromEmailAddress = "email@email.com";
		String templateId = "10000";
		String contactListId = "10000";
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

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

		results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("campaignName"), campaignName);
		assertEquals(results.get("budgetedCost"), budgetedCost);
		assertEquals(results.get("estimatedCost"), estimatedCost);
		assertEquals(results.get("currencyUomId"), currencyUomId);
		assertEquals(results.get("statusId"), "MKTG_CAMP_PLANNED"); /** Should be planned by default. */

		results = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("fromEmailAddress"), fromEmailAddress);
		assertEquals(results.get("templateId"), templateId);
		
		List<?> contactListsForMMC = delegator.findByAnd("MailerMarketingCampaignAndContactList", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		assertNotEmpty("There must be atleast one active relation between MC and CL", EntityUtil.filterByDate(contactListsForMMC));
	}

	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaignWithIncorrectTemplateId() throws GeneralException {
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String fromEmailAddress = "email@email.com";
		String templateId = "XYZ";
		String contactListId = "10000";
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("fromEmailAddress", fromEmailAddress);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);

		runAndAssertServiceError("mailer.createMarketingCampaign", inputs);
	}
}
