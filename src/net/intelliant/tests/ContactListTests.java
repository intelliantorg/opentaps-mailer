package net.intelliant.tests;

import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
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

	public void testCreateContactListWithMailerCampaignInInput() throws GeneralException {
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
}
