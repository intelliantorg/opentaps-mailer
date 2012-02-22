package net.intelliant.tests;

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;

public class CommonTests extends MailerTests {
	
	public void testEmailValidation() throws GenericEntityException {
		assertEquals(true, UtilCommon.isValidEmailAddress("email.email@domain.com"));
		assertEquals(true, UtilCommon.isValidEmailAddress("email_email@domain.com"));
		assertEquals(true, UtilCommon.isValidEmailAddress("email@domain.com"));
		assertEquals(true, UtilCommon.isValidEmailAddress("email@domain.co.in"));
		assertEquals(false, UtilCommon.isValidEmailAddress("email@domain."));
		assertEquals(false, UtilCommon.isValidEmailAddress("email@domain"));
		assertEquals(false, UtilCommon.isValidEmailAddress("email@"));
		assertEquals(false, UtilCommon.isValidEmailAddress("email"));
		assertEquals(false, UtilCommon.isValidEmailAddress("@"));
		assertEquals(false, UtilCommon.isValidEmailAddress("@."));
		assertEquals(false, UtilCommon.isValidEmailAddress("."));
		assertEquals(false, UtilCommon.isValidEmailAddress(".email@domain"));
		assertEquals(false, UtilCommon.isValidEmailAddress(""));
	}
	
	public void testContactListRecipientCount() throws GenericEntityException {
		String contactListId = createContactListWithTwoRecipients();
		assertEquals(2, UtilCommon.countContactListRecipients(delegator, contactListId));
		
		GenericValue anyRecipient = EntityUtil.getFirst(delegator.findByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId)));
		anyRecipient.set("validThruDate", UtilDateTime.nowTimestamp());
		anyRecipient.store();
		
		assertEquals(1, UtilCommon.countContactListRecipients(delegator, contactListId));
	}
}
