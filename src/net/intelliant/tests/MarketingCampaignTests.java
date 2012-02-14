package net.intelliant.tests;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;
import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
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
		String templateId = createMergeTemplate(null);
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
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEmpty("There must ZERO scheduled campaings", campaigns);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 0 scheduled campaigns", 0, campaigns.size());
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
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
		String scheduleAt = "1";
		String templateId = createMergeTemplate(UtilMisc.toMap("scheduleAt", scheduleAt));
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"), UtilMisc.toList("recipientId"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		
		for (GenericValue scheduledCampaign : campaigns) {
			GenericValue recipient = scheduledCampaign.getRelatedOne("MailerRecipient");
			Date expected = UtilDateTime.addDaysToTimestamp(new Timestamp(recipient.getDate(dateOfOperationColumnName).getTime()), Integer.parseInt(scheduleAt));
			assertEquals(new Date(expected.getTime()), scheduledCampaign.getDate("scheduledForDate"));
		}
	}

	public void testUpdateMarketingCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);

		currTime = System.currentTimeMillis();
		campaignName = "Campaign_" + currTime;
		templateId = createMergeTemplate(null);
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
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());

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
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull("Thru date must be set when campaign is cancelled.", marketingCampaignGV.getTimestamp("thruDate"));
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 0 scheduled campaigns", 0, campaigns.size());
		
		List<?> cancelledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_CANCELLED"));
		assertEquals("There must 2 scheduled campaigns", 2, cancelledCampaigns.size());
	}
	
	public void testRemoveContactListFromCampaign() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		Map results = runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		String campaignListId = (String) results.get("campaignListId"); 
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());

		results = runAndAssertServiceSuccess("mailer.removeContactListFromMarketingCampaign", UtilMisc.toMap("userLogin", admin, "campaignListId", campaignListId));
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_SCHEDULED"));
		assertEquals("There must 2 scheduled campaigns", 0, campaigns.size());
		
		List<?> cancelledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_CANCELLED"));
		assertEquals("There must 2 scheduled campaigns", 2, cancelledCampaigns.size());
	}
	
	public void testUpdateMarketingCampaignByChangingTemplate() throws GeneralException {
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		String newTemplateId = createMergeTemplate(UtilMisc.toMap("scheduleAt", "2"));
		inputs.put("templateId", newTemplateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
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
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		String marketingCampaignId = createMarketingCampaign(campaignName, templateId, contactListId, budgetedCost, estimatedCost, currencyUomId);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		Map expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		Map<String, Object> inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("templateId", templateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		Map actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			GenericValue mailerRecipient = scheduledCampaign.getRelatedOne("MailerRecipient");
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
	
	public void testAutoInProgressFromApproved() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName, "templateId", templateId, "contactListId", contactListId);
		inputs.put("userLogin", admin);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		
		String marketingCampaignId1 = createMarketingCampaign(inputs);
		
		fromDate = UtilDateTime.addDaysToTimestamp(fromDate, -1);
		thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId2= createMarketingCampaign(inputs);
		
		fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId3= createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId1, "contactListId", contactListId));
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId2, "contactListId", contactListId));
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId3, "contactListId", contactListId));
		
		long campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign2Count);
		
		long campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign3Count);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		
		campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign1Count);
		
		campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign2Count);
		
		campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign3Count);

		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("statusId", "MKTG_CAMP_APPROVED");
		inputs.put("marketingCampaignId", marketingCampaignId1);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		inputs.put("marketingCampaignId", marketingCampaignId2);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		inputs.put("marketingCampaignId", marketingCampaignId3);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		
		campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 0 'On Hold' campaigns", 0, campaign1Count);
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'Scheduled' campaigns", 2, campaign1Count);
		GenericValue marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals("Must be in 'In Progress'", "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign2Count);
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals("Must NOT be in 'In Progress'", "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
		
		campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign3Count);
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId3));
		assertEquals("Must NOT be in 'In Progress'", "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
	}
	
	public void testAutoCompleteFromInProgress() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName, "templateId", templateId, "contactListId", contactListId);
		inputs.put("userLogin", admin);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		
		String marketingCampaignId1 = createMarketingCampaign(inputs);
		
		fromDate = UtilDateTime.addDaysToTimestamp(fromDate, -1);
		thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId2= createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId1, "contactListId", contactListId));
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId2, "contactListId", contactListId));
		
		long campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign2Count);
		
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("statusId", "MKTG_CAMP_APPROVED");
		inputs.put("marketingCampaignId", marketingCampaignId1);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		inputs.put("marketingCampaignId", marketingCampaignId2);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));

		GenericValue marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals("Must be in 'In Progress'", "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'Scheduled' campaigns", 2, campaign1Count);
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		for (GenericValue scheduledCampaign : campaigns) {
			scheduledCampaign.put("statusId", "MAILER_EXECUTED");
			scheduledCampaign.store();
		}
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfInProgressCampaignsCanBeMarkedComplete", UtilMisc.toMap("userLogin", system));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals(String.format("Campaign [%1$s] must be in completed state", marketingCampaignId1), "MKTG_CAMP_COMPLETED", marketingCampaingnGV.getString("statusId"));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals(String.format("Campaign [%1$s] must be in appproved state", marketingCampaignId2), "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
	}
	
	public void testAutoInProgressFromComplete() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList();
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName, "templateId", templateId, "contactListId", contactListId);
		inputs.put("userLogin", admin);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		
		String marketingCampaignId1 = createMarketingCampaign(inputs);
		
		fromDate = UtilDateTime.addDaysToTimestamp(fromDate, -1);
		thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId2= createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId1, "contactListId", contactListId));
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId2, "contactListId", contactListId));
		
		long campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must 2 'On Hold' campaigns", 2, campaign2Count);
		
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("statusId", "MKTG_CAMP_APPROVED");
		inputs.put("marketingCampaignId", marketingCampaignId1);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		inputs.put("marketingCampaignId", marketingCampaignId2);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));

		GenericValue marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals("Must be in 'In Progress'", "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must 2 'Scheduled' campaigns", 2, campaign1Count);
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		for (GenericValue scheduledCampaign : campaigns) {
			scheduledCampaign.put("statusId", "MAILER_EXECUTED");
			scheduledCampaign.store();
		}
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfInProgressCampaignsCanBeMarkedComplete", UtilMisc.toMap("userLogin", system));
		runAndAssertServiceSuccess("mailer.checkIfCompletedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals(String.format("Campaign [%1$s] must be in completed state", marketingCampaignId1), "MKTG_CAMP_COMPLETED", marketingCampaingnGV.getString("statusId"));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals(String.format("Campaign [%1$s] must be in appproved state", marketingCampaignId2), "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
		
		/** create new contact list and attach it to campaign 1. */
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId1, "contactListId", contactListId));
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals(String.format("There must 2 'Scheduled' campaigns for campaign [%1$s]", marketingCampaignId1), 2, campaign1Count);
		
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId2, "contactListId", contactListId));
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals(String.format("There must 2 'Scheduled' campaigns for campaign [%1$s]", marketingCampaignId2), 2, campaign1Count);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfCompletedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		runAndAssertServiceSuccess("mailer.checkIfInProgressCampaignsCanBeMarkedComplete", UtilMisc.toMap("userLogin", system));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals(String.format("Campaign [%1$s] must be in 'in progress' state", marketingCampaignId1), "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals(String.format("Campaign [%1$s] must be in appproved state", marketingCampaignId2), "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));

	}
}