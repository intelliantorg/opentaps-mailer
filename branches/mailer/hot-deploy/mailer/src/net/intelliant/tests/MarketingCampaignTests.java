package net.intelliant.tests;

import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
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

	public void testCreateMarketingCampaign() throws GeneralException {
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String fromEmailAddress = "email@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
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
		assertEquals(results.get("fromEmailAddress"), fromEmailAddress);
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

	private String createMarketingCampaign(String campaignName, String fromEmailAddress, String templateId, String contactListId, Double budgetedCost, Double estimatedCost, String currencyUomId) {
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
		String fromEmailAddress = "email_" + currTime + "@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());
	}

	public void testUpdateMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String fromEmailAddress = "email_" + currTime + "@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);

		currTime = System.currentTimeMillis();
		campaignName = "Campaign_" + currTime;
		fromEmailAddress = "email_" + currTime + "@email.com";
		templateId = createMergTemplate();;
		budgetedCost = 12050.0;
		estimatedCost = 11550.50;
		currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("fromEmailAddress", fromEmailAddress);
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
		assertEquals(results.get("fromEmailAddress"), fromEmailAddress);
		assertEquals(results.get("templateId"), templateId);
	}
	
	private String createContactList() throws GenericEntityException {
		GenericValue contactListTypeGV = EntityUtil.getFirst(delegator.findAll("ContactListType"));
		Map<String, Object> inputs = UtilMisc.toMap("contactListTypeId", contactListTypeGV.getString("contactListTypeId"));
		inputs.put("userLogin", admin);
		inputs.put("contactListName", "CL " + System.currentTimeMillis());

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createContactList", inputs);
		return (String) results.get("contactListId");
	}
	
	private String createMergTemplate() throws GenericEntityException {
		Map<String, Object> inputs = UtilMisc.toMap("mergeFormName", "mergeFormName" + System.currentTimeMillis());
		inputs.put("scheduleAt", "1");
		inputs.put("mergeFormText", "Sample text");
		inputs.put("userLogin", admin);
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMergeForm", inputs);
		return (String) results.get("mergeFormId");
	}
	
	private String createContactListWithTwoRecipients() throws GenericEntityException {
		String contactListId = createContactList();
		/** Manually recipients and the associate them with contact list. */
		String recipientId = delegator.getNextSeqId("MailerRecipient");
		delegator.create("MailerRecipient", UtilMisc.toMap("recipientId", recipientId));
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("recipientId", recipientId, "contactListId", contactListId));
		
		recipientId = delegator.getNextSeqId("MailerRecipient");
		delegator.create("MailerRecipient", UtilMisc.toMap("recipientId", recipientId));
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("recipientId", recipientId, "contactListId", contactListId));
		
		return contactListId;
	}
	
	public void testCancelMarketingCampaignWithNoContactList() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String fromEmailAddress = "email_" + currTime + "@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("templateId", templateId);
		inputs.put("fromEmailAddress", fromEmailAddress);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull("Thru date must be set when campaign is cancelled.", marketingCampaignGV.getTimestamp("thruDate"));
	}
	
	public void testCancelMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String fromEmailAddress = "email_" + currTime + "@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> scheduledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 2, scheduledCampaigns.size());

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("fromEmailAddress", fromEmailAddress);
		 
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
		String fromEmailAddress = "email_" + currTime + "@email.com";
		String templateId = createMergTemplate();;
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, fromEmailAddress, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
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
}
