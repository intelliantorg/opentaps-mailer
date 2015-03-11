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

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.opentaps.common.util.UtilDate;

public class CommonTests extends MailerTests {
	
	public void testEmailValidation() throws GenericEntityException {
		assertTrue(UtilCommon.isValidEmailAddress("email.email@domain.com"));
		assertTrue(UtilCommon.isValidEmailAddress("email_email@domain.com"));
		assertTrue(UtilCommon.isValidEmailAddress("email@domain.com"));
		assertTrue(UtilCommon.isValidEmailAddress("email@domain.co.in"));
		assertTrue(UtilCommon.isValidEmailAddress("e4_sd_0@sd.com"));
		assertTrue(UtilCommon.isValidEmailAddress("E4_SD_0@SD.com"));
		assertFalse(UtilCommon.isValidEmailAddress("email@domain."));
		assertFalse(UtilCommon.isValidEmailAddress("email@domain"));
		assertFalse(UtilCommon.isValidEmailAddress("email@"));
		assertFalse(UtilCommon.isValidEmailAddress("email"));
		assertFalse(UtilCommon.isValidEmailAddress("@"));
		assertFalse(UtilCommon.isValidEmailAddress("@."));
		assertFalse(UtilCommon.isValidEmailAddress("."));
		assertFalse(UtilCommon.isValidEmailAddress(".email@domain"));
		assertFalse(UtilCommon.isValidEmailAddress(""));
		assertFalse(UtilCommon.isValidEmailAddress("ho.security@allahabadbank.com/www.allahabadbank.in"));
	}
	
	public void testContactListRecipientCount() throws GenericEntityException {
		String contactListId = createContactListWithTwoRecipients();
		assertEquals(2, UtilCommon.countContactListRecipients(delegator, contactListId));
		
		GenericValue anyRecipient = EntityUtil.getFirst(delegator.findByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId)));
		anyRecipient.set("validThruDate", UtilDateTime.nowTimestamp());
		anyRecipient.store();
		
		assertEquals(1, UtilCommon.countContactListRecipients(delegator, contactListId));
	}
	
	public void testAddDaysToTimestamp() throws GenericEntityException {
		int daysToAdd = 2;
		Timestamp startTS = UtilDateTime.nowTimestamp();
		Timestamp newTS = UtilCommon.addDaysToTimestamp(startTS, Double.valueOf(daysToAdd));
		int daysAfterDiff = UtilDate.dateDifference(newTS, startTS);
		assertEquals(daysToAdd, daysAfterDiff);
		
		daysToAdd = 2000;
		newTS = UtilCommon.addDaysToTimestamp(startTS, Double.valueOf(daysToAdd));
		daysAfterDiff = UtilDate.dateDifference(newTS, startTS);
		assertEquals(daysToAdd, daysAfterDiff);
	}
}
