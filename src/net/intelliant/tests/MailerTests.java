/*
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 */
package net.intelliant.tests;

import java.sql.Timestamp;
import java.util.Map;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.opentaps.tests.OpentapsTestCase;

public class MailerTests extends OpentapsTestCase {
	protected GenericValue system;
	protected static final String dateOfOperationColumnName = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");

	@Override
	public void setUp() throws Exception {
		super.setUp();
		system = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected String createMarketingCampaign(Map<String, Object> inputs) {
		Map<String, Object> results = runAndAssertServiceSuccess("mailer.createMarketingCampaign", inputs);
		return (String) results.get("marketingCampaignId");
	}	

	protected String createContactList(Map overrideDefaults) throws GenericEntityException {
		GenericValue contactListTypeGV = EntityUtil.getFirst(delegator.findAll("ContactListType"));
		Map<String, Object> defaultInputs = UtilMisc.toMap("contactListTypeId", contactListTypeGV.getString("contactListTypeId"));
		defaultInputs.put("userLogin", admin);
		defaultInputs.put("contactListName", "CL " + System.currentTimeMillis());
		if (UtilValidate.isNotEmpty(overrideDefaults)) {
			defaultInputs.putAll(overrideDefaults);
		}	
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createContactList", defaultInputs);
		return (String) results.get("contactListId");
	}

	protected String createMergeTemplate(Map overrideDefaults) throws GenericEntityException {
		Map<String, Object> defaultInputs = UtilMisc.toMap("mergeFormName", "mergeFormName_" + System.currentTimeMillis());
		defaultInputs.put("scheduleAt", "1");
		defaultInputs.put("mergeFormText", "Sample text");
		defaultInputs.put("userLogin", admin);
		defaultInputs.put("mergeFormTypeId", "EMAIL");
		defaultInputs.put("fromEmailAddress", "test@email.com");
		if (UtilValidate.isNotEmpty(overrideDefaults)) {
			defaultInputs.putAll(overrideDefaults);
		}
		Map<?, ?> results = runAndAssertServiceSuccess("mailer.createMergeForm", defaultInputs);
		return (String) results.get("mergeFormId");
	}

	@SuppressWarnings("unchecked")
	protected String createContactListWithTwoRecipients() throws GenericEntityException {
		String contactListId = createContactList(null);
		/** Manually recipients and the associate them with contact list. */
		String recipientId = delegator.getNextSeqId("MailerRecipient");
		Map<String, Object> columns = UtilMisc.toMap("recipientId", recipientId);
		columns.put(dateOfOperationColumnName, UtilDateTime.nowDate());
		delegator.create("MailerRecipient", columns);
		String recipientContactListId = delegator.getNextSeqId("MailerRecipientContactList");
		columns = UtilMisc.toMap("recipientListId", recipientContactListId, "recipientId", recipientId, "contactListId", contactListId);
		columns.put("validFromDate", UtilDateTime.nowTimestamp());
		delegator.create("MailerRecipientContactList", columns);
		
		recipientId = delegator.getNextSeqId("MailerRecipient");
		columns = UtilMisc.toMap("recipientId", recipientId);
		columns.put(dateOfOperationColumnName, UtilDateTime.addDaysToTimestamp(new Timestamp(UtilDateTime.nowDate().getTime()), 1));
		delegator.create("MailerRecipient", columns);
		recipientContactListId = delegator.getNextSeqId("MailerRecipientContactList");
		columns = UtilMisc.toMap("recipientListId", recipientContactListId, "recipientId", recipientId, "contactListId", contactListId);
		columns.put("validFromDate", UtilDateTime.nowTimestamp());
		delegator.create("MailerRecipientContactList", columns);
		
		return contactListId;
	}
}
