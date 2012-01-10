/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Honest Public License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Honest Public License for more details.
 *
 * You should have received a copy of the Honest Public License
 * along with this program; if not, write to Funambol,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */
package org.opentaps.domain.party;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opentaps.domain.base.entities.AsteriskUser;
import org.opentaps.domain.base.entities.PartyNoteView;
import org.opentaps.domain.base.entities.PartySummaryCRMView;
import org.opentaps.domain.base.entities.PostalAddress;
import org.opentaps.domain.base.entities.TelecomNumber;
import org.opentaps.domain.base.entities.UserLogin;
import org.opentaps.domain.billing.payment.PaymentMethod;
import org.opentaps.foundation.entity.EntityNotFoundException;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.foundation.infrastructure.User;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;

/**
 * Repository for Parties to handle interaction of Party-related domain with the entity engine (database) and the service engine.
 */
public interface PartyRepositoryInterface extends RepositoryInterface {

    /**
     * Finds the <code>Party</code> with the given ID.
     * @return never null unless the given Party identifier is null
     * @param partyId Party identifier
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>Party</code> is found for the given id
     */
    public Party getPartyById(String partyId) throws RepositoryException, EntityNotFoundException;

    /**
     * Finds the <code>Set</code> of <code>Party</code> with given IDs.
     * @return never null but might be empty
     * @param partyIds set of Party identifiers
     * @throws RepositoryException if an error occurs
     */
    public Set<Party> getPartyByIds(List<String> partyIds) throws RepositoryException;

    /**
     * Finds the <code>Account</code> with the given ID.
     * @return never null unless the given Party identifier is null
     * @param partyId Party identifier
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>Contact</code> is found for the given id
     */
    public Account getAccountById(String partyId) throws RepositoryException, EntityNotFoundException;

    /**
     * Finds the <code>Contact</code> with the given ID.
     * @return never null unless the given Party identifier is null
     * @param partyId Party identifier
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>Contact</code> is found for the given id
     */
    public Contact getContactById(String partyId) throws RepositoryException, EntityNotFoundException;

    /**
     * Finds the <code>Set</code> of accounts of the given <code>Contact</code>.
     * @return null only if the given <code>Contact</code> is null or if no <code>Account</code> is found
     * @param contact a <code>Contact</code>
     * @throws RepositoryException if an error occurs
     */
    public Set<Account> getAccounts(Contact contact) throws RepositoryException;

    /**
     * Finds the list of sub <code>Account</code> for the given parent <code>Account</code>.
     * @return the list of sub <code>Account</code> for the given parent <code>Account</code>
     * @param account the parent <code>Account</code>
     * @throws RepositoryException if an error occurs
     */
    public Set<Account> getSubAccounts(Account account) throws RepositoryException;

    /**
     * Finds the <code>Set</code> of <code>Contact</code> for the given <code>Account</code>.
     * @return null only if the given Account is null or if no Contact is found
     * @param parentAccount the <code>Account</code> the returned contacts belong to
     * @throws RepositoryException if an error occurs
     */
    public Set<Contact> getContacts(Account parentAccount) throws RepositoryException;

    /**
     * Finds the <code>PartySummaryCRMView</code> for the given Party.
     * @return never null unless the given Party identifier is null
     * @param partyId Party identifier
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException if the <code>PartySummaryCRMView</code> is not found
     */
    public PartySummaryCRMView getPartySummaryCRMView(String partyId) throws RepositoryException, EntityNotFoundException;

    /**
     * Finds the list of currently active <code>PostalAddress</code> for the given party and purpose.
     * They are ordered oldest first.
     * @return the list of <code>PostalAddress</code>
     * @param party a <code>Party</code>
     * @param purpose a <code>Party.ContactPurpose</code>
     * @throws RepositoryException if an error occurs
     */
    public List<PostalAddress> getPostalAddresses(Party party, Party.ContactPurpose purpose) throws RepositoryException;

    /**
     * Finds the list of currently active <code>TelecomNumber</code> for the given party.
     * They are ordered oldest first.
     * @return the list of <code>TelecomNumber</code>
     * @param party a <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public List<TelecomNumber> getPhoneNumbers(Party party) throws RepositoryException;

    /**
     * Finds the list of currently active <code>TelecomNumber</code> for the given party and purpose.
     * They are ordered oldest first.
     * @return the list of <code>TelecomNumber</code>
     * @param party a <code>Party</code>
     * @param purpose a <code>Party.ContactPurpose</code>
     * @throws RepositoryException if an error occurs
     */
    public List<TelecomNumber> getPhoneNumbers(Party party, Party.ContactPurpose purpose) throws RepositoryException;

    /**
     * Returns a <code>Map</code> describing the names for the given Party at the given time.
     * The data returned is:
     * - groupName
     * - firstName
     * - middleName
     * - lastName
     * - personalTitle
     * - suffix
     * - fullName
     * TODO: find a better way to handle the result
     * @return the <code>Map</code> describing the names
     * @param party a <code>Party</code>
     * @param date a <code>Timestamp</code>
     * @throws RepositoryException if an error occurs
     */
    public Map<String, Object> getPartyNameForDate(Party party, Timestamp date) throws RepositoryException;

    /**
     * Finds the list of currently active <code>PaymentMethod</code>.
     * They are ordered by type and then oldest first.
     * @return the list of currently active <code>PaymentMethod</code>
     * @param party a <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public List<PaymentMethod> getPaymentMethods(Party party) throws RepositoryException;

    /**
     * Finds the list of <code>Party</code> matching the given phone number.
     * @return the list of <code>Party</code> matching the given phone number
     * @param phoneNumber the phone number to find
     * @throws RepositoryException if an error occurs
     */
    public Set<Party> getPartyByPhoneNumber(String phoneNumber) throws RepositoryException;

    /**
     * Finds the <code>UserLogin</code> matching the given asterisk extension.
     * @return the <code>UserLogin</code> matching the given asterisk extension
     * @param extension the extension number to find
     * @throws RepositoryException if an error occurs
     */
    public UserLogin getUserLoginByExtension(String extension) throws RepositoryException;

    /**
     * Finds the first unexpired <code>AsteriskUser</code> matching the given <code>User</code>.
     * @return the <code>AsteriskUser</code> matching the given <code>User</code>
     * @param user the <code>User</code>
     * @throws RepositoryException if an error occurs
     * @throws InfrastructureException if an error occurs
     */
    public AsteriskUser getAsteriskUserForUser(User user) throws RepositoryException, InfrastructureException;

    /**
     * Finds the list of <code>PartyNoteView</code> related to the given <code>Party</code>.
     * @param party a <code>Party</code> value
     * @return the list of related <code>PartyNoteView</code>
     */
    public List<PartyNoteView> getNotes(Party party) throws RepositoryException;
}
