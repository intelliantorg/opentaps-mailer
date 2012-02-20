package net.intelliant.tests;

import net.intelliant.util.UtilCommon;

import org.ofbiz.entity.GenericEntityException;

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
}
