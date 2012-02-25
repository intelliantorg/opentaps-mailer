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

	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";
		String description = "Some description for this campaign";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("description", description);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);

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
		assertEquals(results.get("description"), description);

		List<?> contactListsForMMC = delegator.findByAnd("MailerMarketingCampaignAndContactList", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		assertNotEmpty("There must be atleast one active relation between MC and CL", EntityUtil.filterByDate(contactListsForMMC));
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEmpty("There must be ZERO scheduled campaings", campaigns);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		long count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId);
		assertEquals("There must be ZERO scheduled campaigns", 0, count);
		
		count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId);
		assertEquals("There must be 2 'On Hold' campaigns", 2, count);
	}
	
	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaignFailWithFromDateGreaterThanThruDate() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, -10);
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";
		String description = "Some description for this campaign";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("description", description);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		runAndAssertServiceError("mailer.createMarketingCampaign", inputs);
	}

	@SuppressWarnings("unchecked")
	public void testCreateMarketingCampaignFailWithIncorrectTemplateId() throws GeneralException {
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String templateId = "XYZ";
		String contactListId = createContactList(null);
		Double budgetedCost = new Double("12000.00");
		Double estimatedCost = new Double("11500.50");
		String currencyUomId = "INR";

		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);

		runAndAssertServiceError("mailer.createMarketingCampaign", inputs);
	}
	
	@SuppressWarnings("unchecked")
	public void testAddContactListToMarketingCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String scheduleAt = "1";
		String templateId = createMergeTemplate(UtilMisc.toMap("scheduleAt", scheduleAt));
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"), UtilMisc.toList("recipientId"));
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaigns.size());
		
		for (GenericValue scheduledCampaign : campaigns) {
			GenericValue recipient = scheduledCampaign.getRelatedOne("MailerRecipient");
			Date expected = UtilDateTime.addDaysToTimestamp(new Timestamp(recipient.getDate(dateOfOperationColumnName).getTime()), Integer.parseInt(scheduleAt));
			assertEquals(new Date(expected.getTime()), scheduledCampaign.getDate("scheduledForDate"));
		}
	}

	@SuppressWarnings("unchecked")
	public void testUpdateMarketingCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		String description = "Some description for this campaign";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("description", description);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		currTime = System.currentTimeMillis();
		campaignName = "Campaign_" + currTime;
		templateId = createMergeTemplate(null);
		budgetedCost = 12050.0;
		estimatedCost = 11550.50;
		currencyUomId = "INR";
		
		inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		Map<?, ?> campaignGV = delegator.findByPrimaryKey("MailerMarketingCampaignDetailsView", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(campaignGV);
		assertEquals(campaignGV.get("campaignName"), campaignName);
		assertEquals(campaignGV.get("budgetedCost"), budgetedCost);
		assertEquals(campaignGV.get("estimatedCost"), estimatedCost);
		assertEquals(campaignGV.get("currencyUomId"), currencyUomId);
		assertEquals(campaignGV.get("templateId"), templateId);
		assertEquals(campaignGV.get("description"), description);
		
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("marketingCampaignId", marketingCampaignId);
		inputs.put("description", "");
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		campaignGV = delegator.findByPrimaryKey("MailerMarketingCampaignDetailsView", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertNotNull(campaignGV);
		assertEquals(campaignGV.get("campaignName"), campaignName);
		assertEquals(campaignGV.get("budgetedCost"), budgetedCost);
		assertEquals(campaignGV.get("estimatedCost"), estimatedCost);
		assertEquals(campaignGV.get("currencyUomId"), currencyUomId);
		assertEquals(campaignGV.get("templateId"), templateId);
		assertEquals(campaignGV.get("description"), "");
	}
	
	@SuppressWarnings("unchecked")
	public void testCancelMarketingCampaignWithNoContactList() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		long count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId);
		assertEquals("There must be 2 'On Hold' campaigns", 2, count);

		inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("templateId", templateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals("Status must be cancelled.", "MKTG_CAMP_CANCELLED", marketingCampaignGV.getString("statusId"));
	}
	
	@SuppressWarnings("unchecked")
	public void testCancelMarketingCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<?> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		
		GenericValue campaign = EntityUtil.getFirst(campaigns);
		campaign.setString("statusId", "MAILER_EXECUTED");
		campaign.store();
		
		assertEquals("There must 1 ONLY executed campaign line", 1, UtilCommon.countExecutedCampaignLines(delegator, null, marketingCampaignId));

		inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("statusId", "MKTG_CAMP_CANCELLED");
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		GenericValue marketingCampaignGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals("Status must be cancelled.", "MKTG_CAMP_CANCELLED", marketingCampaignGV.getString("statusId"));
		
		long count = UtilCommon.countScheduledCampaignLines(delegator, null, marketingCampaignId);
		assertEquals("There must be ZERO scheduled campaigns", 0, count);
		
		assertEquals("There must be ONLY 1 executed campaign line", 1, UtilCommon.countExecutedCampaignLines(delegator, null, marketingCampaignId));
		
		assertEquals("There must be ONLY 1 cancelled campaign", 1, UtilCommon.countCancelledCampaignLines(delegator, null, marketingCampaignId));
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		assertEquals("There must be ONLY 2 campaign lines", 2, UtilCommon.countAllCampaignLines(delegator, null, marketingCampaignId));
	}
	
	@SuppressWarnings("unchecked")
	public void testRemoveContactListFromCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		Map<String, Object> results = runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		String campaignListId = (String) results.get("campaignListId"); 
		
		long count = UtilCommon.countOnHoldCampaignLines(delegator, null, marketingCampaignId);
		assertEquals("There must be 2 'On Hold' campaigns", 2, count);

		results = runAndAssertServiceSuccess("mailer.removeContactListFromMarketingCampaign", UtilMisc.toMap("userLogin", admin, "campaignListId", campaignListId));
		
		count = UtilCommon.countScheduledCampaignLines(delegator, null, marketingCampaignId);
		assertEquals("There must be 2 scheduled campaigns", 0, count);
		
		List<?> cancelledCampaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_CANCELLED"));
		assertEquals("There must be 2 scheduled campaigns", 2, cancelledCampaigns.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testUpdateMarketingCampaignByChangingTemplate() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must 2 'On Hold' campaigns", 2, campaigns.size());
		Map<String, Object> expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		String newTemplateId = createMergeTemplate(UtilMisc.toMap("scheduleAt", "2"));
		inputs.put("templateId", newTemplateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		Map<String, Object> actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertNotEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testUpdateMarketingCampaignNoChangeInTemplate() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
		Double budgetedCost = Math.random() * 100000;
		Double estimatedCost = budgetedCost > 1000 ? budgetedCost - 900 : budgetedCost - 1;
		String currencyUomId = "INR";
		
		Map<String, Object> inputs = UtilMisc.toMap("campaignName", campaignName);
		inputs.put("userLogin", admin);
		inputs.put("templateId", templateId);
		inputs.put("contactListId", contactListId);
		inputs.put("budgetedCost", budgetedCost);
		inputs.put("currencyUomId", currencyUomId);
		inputs.put("estimatedCost", estimatedCost);
		inputs.put("statusId", "MKTG_CAMP_PLANNED");
		inputs.put("fromDate", fromDate);
		inputs.put("thruDate", thruDate);
		String marketingCampaignId = createMarketingCampaign(inputs);
		
		contactListId = createContactListWithTwoRecipients();
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId));
		
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaigns.size());
		Map<String, Object> expected = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			expected.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}

		inputs = UtilMisc.toMap("marketingCampaignId", marketingCampaignId);
		inputs.put("templateId", templateId);
		inputs.put("userLogin", admin);
		 
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId, "statusId", "MAILER_HOLD"));
		Map<String, Object> actual = FastMap.newInstance();
		for (GenericValue scheduledCampaign : campaigns) {
			actual.put(scheduledCampaign.getString("campaignStatusId"), scheduledCampaign.getString("scheduledForDate"));
		}
		
		assertEquals(expected.size(), actual.size());
		for (Object key : actual.keySet()) {
			assertEquals("Dates must NOT be equal", expected.get(key), actual.get(key));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testAutoTransitionToInProgressFromApproved() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
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
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign2Count);
		
		long campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign3Count);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfApprovedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		
		campaign1Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign1Count);
		
		campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign2Count);
		
		campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign3Count);

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
		assertEquals("There must be ZERO 'On Hold' campaigns", 0, campaign1Count);
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId1);
		assertEquals("There must be 2 'Scheduled' campaigns", 2, campaign1Count);
		GenericValue marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals("Must be in 'In Progress'", "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign2Count);
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals("Must NOT be in 'In Progress'", "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
		
		campaign3Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId3);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign3Count);
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId3));
		assertEquals("Must NOT be in 'In Progress'", "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
	}
	
	@SuppressWarnings("unchecked")
	public void testAutoTransitionToCompleteFromInProgress() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
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
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign2Count);
		
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
		assertEquals("There must be 2 'Scheduled' campaigns", 2, campaign1Count);
		
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
	
	@SuppressWarnings("unchecked")
	public void testAutoTransitionToInProgressFromComplete() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
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
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign1Count);
		
		long campaign2Count = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals("There must be 2 'On Hold' campaigns", 2, campaign2Count);
		
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
		assertEquals(String.format("There must be 2 'Scheduled' campaigns for campaign [%1$s]", marketingCampaignId1), 2, campaign1Count);
		
		runAndAssertServiceSuccess("mailer.addContactListToCampaign", UtilMisc.toMap("userLogin", admin, "marketingCampaignId", marketingCampaignId2, "contactListId", contactListId));
		
		campaign1Count = UtilCommon.countScheduledCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals(String.format("There must be 2 'Scheduled' campaigns for campaign [%1$s]", marketingCampaignId2), 2, campaign1Count);
		
		/** Executing this is to simulate the execution of scheduled service */
		runAndAssertServiceSuccess("mailer.checkIfCompletedCampaignsCanBeMarkedInProgress", UtilMisc.toMap("userLogin", system));
		runAndAssertServiceSuccess("mailer.checkIfInProgressCampaignsCanBeMarkedComplete", UtilMisc.toMap("userLogin", system));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId1));
		assertEquals(String.format("Campaign [%1$s] must be in 'in progress' state", marketingCampaignId1), "MKTG_CAMP_INPROGRESS", marketingCampaingnGV.getString("statusId"));
		
		marketingCampaingnGV = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId2));
		assertEquals(String.format("Campaign [%1$s] must be in appproved state", marketingCampaignId2), "MKTG_CAMP_APPROVED", marketingCampaingnGV.getString("statusId"));
	}
	
	public void testCancelMailersFailWithIncorrectParameters() throws GeneralException {
		runAndAssertServiceError("mailer.cancelCreatedMailers", UtilMisc.toMap("userLogin", admin));		
	}
	
	@SuppressWarnings("unchecked")
	public void testApprovedMarketingCampaignShouldBeMarkedInProgressIfDateWhenApprovedLiesBetweenCampaignDates() throws GeneralException {
		Timestamp fromDate = UtilDateTime.nowTimestamp();
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
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
		
		String marketingCampaignId = createMarketingCampaign(inputs);
		Map<?, ?> results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals(results.get("statusId"), "MKTG_CAMP_PLANNED");
		
		/** Approve Marketing Campaign. */
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("statusId", "MKTG_CAMP_APPROVED");
		inputs.put("marketingCampaignId", marketingCampaignId);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals(results.get("statusId"), "MKTG_CAMP_INPROGRESS");
	}
	
	@SuppressWarnings("unchecked")
	public void testApprovedMarketingCampaignShouldRemainApprovedIfDateWhenApprovedIsNotInBetweenCampaignDates() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		Long currTime = System.currentTimeMillis();
		String campaignName = "Campaign_" + currTime;
		String templateId = createMergeTemplate(null);
		String contactListId = createContactList(null);
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
		
		String marketingCampaignId = createMarketingCampaign(inputs);
		Map<?, ?> results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals(results.get("statusId"), "MKTG_CAMP_PLANNED");
		
		/** Approve Marketing Campaign. */
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("statusId", "MKTG_CAMP_APPROVED");
		inputs.put("marketingCampaignId", marketingCampaignId);
		runAndAssertServiceSuccess("mailer.updateMarketingCampaign", inputs);
		
		results = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		assertEquals(results.get("statusId"), "MKTG_CAMP_APPROVED");
	}
}