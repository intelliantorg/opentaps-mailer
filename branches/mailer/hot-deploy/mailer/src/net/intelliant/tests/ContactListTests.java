/*
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
 */
package net.intelliant.tests;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;

public class ContactListTests extends MailerTests {
	private final static String module = ContactListTests.class.getName();
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressWarnings("unchecked")
	public void testCreateContactListWithMailerCampaignInInput() throws GeneralException {
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
		
		inputs.clear();
		inputs.put("userLogin", admin);
		inputs.put("marketingCampaignId", marketingCampaignId);
		contactListId = createContactList(inputs);
		
		List<GenericValue> relations = delegator.findByAnd("MailerMarketingCampaignAndContactList", UtilMisc.toMap("contactListId", contactListId));
		assertEquals(1, relations.size());
		GenericValue relation = EntityUtil.getFirst(relations);
		assertEquals(marketingCampaignId, relation.getString("marketingCampaignId"));
	}

	@SuppressWarnings("unchecked")
	public void testRemoveRecipientFromContactListWithNoMailerCampaign() throws GeneralException {
		String contactListId = createContactListWithTwoRecipients();
		
		List<GenericValue> relations = delegator.findByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId));
		assertEquals(2, relations.size());
		GenericValue relation = EntityUtil.getFirst(relations);
		String recipientListId = relation.getString("recipientListId");
		
		runAndAssertServiceSuccess("mailer.removeRecipientFromContactList", UtilMisc.toMap("userLogin", admin, "recipientListId", recipientListId, "contactListId", contactListId));
		long activeRecipients = UtilCommon.countContactListRecipients(delegator, contactListId);
		assertEquals(1, activeRecipients);
	}
	
	@SuppressWarnings("unchecked")
	public void testRemoveRecipientFromContactListWithMailerCampaign() throws GeneralException {
		Timestamp fromDate = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
		Timestamp thruDate = UtilDateTime.addDaysToTimestamp(fromDate, 1);
		String contactListId = createContactListWithTwoRecipients();		
		String campaignName = "Campaign_" + System.currentTimeMillis();
		String templateId = createMergeTemplate(null);
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
		
		/** execute all campaigns */
		List<GenericValue> campaigns = delegator.findByAnd("MailerCampaignStatus", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		for (GenericValue scheduledCampaign : campaigns) {
			scheduledCampaign.put("statusId", "MAILER_EXECUTED");
			scheduledCampaign.store();
		}
		
		/** remove one of the recipients */
		List<GenericValue> relations = delegator.findByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId));
		assertEquals(2, relations.size());
		GenericValue relation = EntityUtil.getFirst(relations);
		String recipientListId = relation.getString("recipientListId");
		
		runAndAssertServiceSuccess("mailer.removeRecipientFromContactList", UtilMisc.toMap("userLogin", admin, "recipientListId", recipientListId, "contactListId", contactListId));
		long activeRecipients = UtilCommon.countContactListRecipients(delegator, contactListId);
		assertEquals(1, activeRecipients);
		
		long cancelledCount = UtilCommon.countCancelledCampaignLines(delegator, contactListId, marketingCampaignId);
		assertEquals(0, cancelledCount);
		
		/** create another campaign. */
		String marketingCampaignId2 = createMarketingCampaign(inputs);
		long onHoldCount = UtilCommon.countOnHoldCampaignLines(delegator, contactListId, marketingCampaignId2);
		long totalCount = UtilCommon.countAllCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals(1, onHoldCount);
		assertEquals(1, totalCount);
		
		EntityCondition conditions = new EntityConditionList( 
    		UtilMisc.toList(
				new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId),
                EntityUtil.getFilterByDateExpr("validFromDate", "validThruDate")
    		), EntityOperator.AND
    	);
		/** find active recipients. */
		relations = delegator.findByCondition("MailerRecipientContactList", conditions, null, null);
		assertEquals(1, relations.size());
		relation = EntityUtil.getFirst(relations);
		recipientListId = relation.getString("recipientListId");
		
		runAndAssertServiceSuccess("mailer.removeRecipientFromContactList", UtilMisc.toMap("userLogin", admin, "recipientListId", recipientListId));
		activeRecipients = UtilCommon.countContactListRecipients(delegator, contactListId);
		assertEquals(0, activeRecipients);
		
		cancelledCount = UtilCommon.countCancelledCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals(1, cancelledCount);
		
		totalCount = UtilCommon.countAllCampaignLines(delegator, contactListId, marketingCampaignId2);
		assertEquals(1, totalCount);
	}
}