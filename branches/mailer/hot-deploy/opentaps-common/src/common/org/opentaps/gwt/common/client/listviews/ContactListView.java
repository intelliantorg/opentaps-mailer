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

package org.opentaps.gwt.common.client.listviews;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;

/**
 * A list of Contacts.
 */
public class ContactListView extends PartyListView {

    /**
     * Default constructor.
     * Set the title to the default contact list title.
     */
    public ContactListView() {
        super();
        // the msg is built in the contructor of EntityListView
        // so we cannot pass the title directly
        setTitle(UtilUi.MSG.contactList());
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title of the list
     */
    public ContactListView(String title) {
        super(title);
        init();
    }

    @Override
    public void init() {
        init(PartyLookupConfiguration.URL_FIND_CONTACTS, "/crmsfa/control/viewContact?partyId={0}", UtilUi.MSG.contactId(), new String[]{
                PartyLookupConfiguration.INOUT_FIRST_NAME, UtilUi.MSG.firstName(),
                PartyLookupConfiguration.INOUT_LAST_NAME, UtilUi.MSG.lastName(),
                //PartyLookupConfiguration.INOUT_PARENT_ACCOUNT, "Parent Account",
                PartyLookupConfiguration.OUT_NUMBER_OF_ACCOUNTS_FOR_CONTACT, "Number of Accounts",
                //PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC, "Assigned To"
            });
    }

    /**
     * Filters the records of the list by first name of the contact matching the given sub string.
     * @param firstName a <code>String</code> value
     */
    public void filterByFirstName(String firstName) {
        setFilter(PartyLookupConfiguration.INOUT_FIRST_NAME, firstName);
    }

    /**
     * Filters the records of the list by last name of the contact matching the given sub string.
     * @param lastName a <code>String</code> value
     */
    public void filterByLastName(String lastName) {
        setFilter(PartyLookupConfiguration.INOUT_LAST_NAME, lastName);
    }
    
    public void filterByCreatedBy(String createdBy) {
        setFilter(PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN, createdBy);
    }
    
    public void filterByAssignedTo(String assignedTo) {
        setFilter(PartyLookupConfiguration.INOUT_ASSIGNED_TO, assignedTo);
    }
    
    public void filterByAssignedTeam(String assignedTeam) {
        setFilter(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID, assignedTeam);
    }
    
    public void filterByEmail(String primaryEmail) {
        setFilter(PartyLookupConfiguration.INOUT_EMAIL, primaryEmail);
    }
        
    public void filterByParentAccount(String parentPartyId) {
        setFilter(PartyLookupConfiguration.INOUT_PARENT_ACCOUNT, parentPartyId);
    }
    
    public void filterByInitialAccount(String accountPartyId) {
        setFilter(PartyLookupConfiguration.INOUT_INITIAL_ACCOUNT_FOR_CONTACT, accountPartyId);
    }
}
