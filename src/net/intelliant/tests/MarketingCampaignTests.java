package net.intelliant.tests;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;

public class MarketingCampaignTests extends MailerTests {
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
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);

		Map<?, ?> results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("campaignName"), campaignName);
		assertEquals(results.get("budgetedCost"), budgetedCost);
		assertEquals(results.get("estimatedCost"), estimatedCost);
		assertEquals(results.get("currencyUomId"), currencyUomId);
		assertEquals(results.get("statusId"), "MKTG_CAMP_PLANNED");
		/** Should be planned by default. */

		results = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("templateId"), templateId);

		List<?> contactListsForMMC = delegator.findByAnd("MailerMarketingCampaignAndContactList", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		assertNotEmpty("There must be atleast one active relation between MC and CL", EntityUtil.filterByDate(contactListsForMMC));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEmpty("There must ZERO scheduled campaings", scheduledCampaigns);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
	}

	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaignWithIncorrectTemplateId() throws GeneralException {
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String fromEmailAddress = "email@email.com";
		String templateId = "XYZ";
		String contactListId = createContactList();
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
	
	public void testAddContactListToMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
	}

	public void testUpdateMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);

		currTime = System.currentTimeMillis();
		campaignName = "Campaign_" + currTime;
		templateId = createMergTemplate(null);
		budgetedCost = 12050.0;
		estimatedCost = 11550.50;
		currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		Map<?, ?> results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("campaignName"), campaignName);
		assertEquals(results.get("budgetedCost"), budgetedCost);
		assertEquals(results.get("estimatedCost"), estimatedCost);
		assertEquals(results.get("currencyUomId"), currencyUomId);

		results = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(results);
		assertEquals(results.get("templateId"), templateId);
	}
	
	public void testCancelMarketingCampaignWithNoContactList() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("templateId", templateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull("Thru date must be set when campaign is cancelled.", marketingCampaignGV.getTimestamp("thruDate"));
	}
	
	public void testCancelMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull("Thru date must be set when campaign is cancelled.", marketingCampaignGV.getTimestamp("thruDate"));
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 0 scheduled campaigns", 0, scheduledCampaigns.size());
		
		List<?> cancelledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_CANCELLED"));
		assertEquals("There must 2 scheduled campaigns", 2, cancelledCampaigns.size());
	}
	
	public void testRemoveContactListFromCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		Map results = runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		String campaignListId = (String) results.get("campaignListId"); 
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());

		results = runAndAssertServiceSuccess("mailer.removeContactListFromMarketingCampaign", UtilMisc.toMap("userLogin", admin, "campaignListId", campaignListId));
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 0, scheduledCampaigns.size());
		
		List<?> cancelledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_CANCELLED"));
		assertEquals("There must 2 scheduled campaigns", 2, cancelledCampaigns.size());
	}
	
	public void testUpdateMarketingCampaignByChangingTemplate() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		String newTemplateId = createMergTemplate(UtilMisc.toMap("scheduleAt", "2"));
		inputs.put("templateId", newTemplateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			GenericValue mailerRecipient = scheduledCampaign.getRelatedOne("MailerRecipient");
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertNotEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
	
	public void testUpdateMarketingCampaignNoChangeInTemplate() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("templateId", templateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : scheduledCampaigns) {
			GenericValue mailerRecipient = scheduledCampaign.getRelatedOne("MailerRecipient");
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
}