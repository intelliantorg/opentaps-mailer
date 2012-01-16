package net.intelliant.tests;

import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.opentaps.tests.OpentapsTestCase;

public class MarketingMergedFormsTests extends OpentapsTestCase {
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
	public void testCreateMergeForm() throws GeneralException {
		Long currTime = System.currentTimeMillis();

		String mergeFormId;
		String partyId = "10083";
		String mergeFormName = "Test form name - " + currTime;
		String subject = "Test subject - " + currTime;
		String description = "Test description - " + currTime;
		String mergeFormText = "Test merge form text - " + currTime;
		String showInSelect = (Math.random() > .5) ? "Y" : "N";

		Map<String, Object> inputs = UtilMisc.toMap("userLogin", admin);
		inputs.put("partyId", partyId);
		inputs.put("mergeFormName", mergeFormName);
		inputs.put("subject", subject);
		inputs.put("description", description);
		inputs.put("mergeFormText", mergeFormText);
		inputs.put("showInSelect", showInSelect);

		Map<String, Object> results = runAndAssertServiceSuccess("mailer.createMergeForm", inputs);
		System.out.println(module+" - RESULT : "+results);
		mergeFormId = (String) results.get("mergeFormId");

		results = delegator.findByPrimaryKey("MergeForm", UtilMisc.toMap("mergeFormId", mergeFormId));

		assertNotNull(results);
		assertEquals(results.get("mergeFormName"), mergeFormName);
		assertEquals(results.get("subject"), subject);
		assertEquals(results.get("description"), description);
		assertEquals(results.get("mergeFormText"), mergeFormText);
		assertEquals(results.get("showInSelect"), showInSelect);

		partyId = "10083";
		subject = "Test subject - " + currTime;
		description = "Test description - " + currTime;
		showInSelect = (Math.random() > .5) ? "Y" : "N";
		
		inputs.clear();
		inputs = UtilMisc.toMap("userLogin", admin);
		inputs.put("partyId", partyId);
		inputs.put("subject", subject);
		inputs.put("description", description);
		inputs.put("showInSelect", showInSelect);
		
		runAndAssertServiceFailure("mailer.createMergeForm", inputs);
	}
}
