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
 * A list of Accounts.
 */
public class AccountListView extends PartyListView {

    /**
     * Default constructor.
     * Set the title to the default account list title.
     */
    public AccountListView() {
        super();
        // the msg is built in the contructor of EntityListView
        // so we cannot pas the title directly
        setTitle(UtilUi.MSG.accountList());
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title of the list
     */
    public AccountListView(String title) {
        super(title);
        init();
    }

    @Override
    public void init() {

        init(PartyLookupConfiguration.URL_FIND_ACCOUNTS, "/crmsfa/control/viewAccount?partyId={0}", UtilUi.MSG.accountId(), new String[]{
                PartyLookupConfiguration.INOUT_GROUP_NAME, UtilUi.MSG.accountName(),
                //PartyLookupConfiguration.INOUT_ACCOUNT_WEBSITE, UtilUi.MSG.crmSiteName(),
                //PartyLookupConfiguration.INOUT_ACCOUNT_ANNUAL_REVENEU, UtilUi.MSG.crmAnnualRevenue(),
                //PartyLookupConfiguration.INOUT_ACCOUNT_SIC_CODE, UtilUi.MSG.crmSICCode(),
                PartyLookupConfiguration.INOUT_ACCOUNT_RATING, "Rating",
                //PartyLookupConfiguration.INOUT_ACCOUNT_TICKER_SYMBOL, UtilUi.MSG.crmTickerSymbol(),
                PartyLookupConfiguration.OUT_ACCOUNT_INDUSTRY_DESC, UtilUi.MSG.crmIndustry(),
                //PartyLookupConfiguration.INOUT_ACCOUNT_NUMBER_OF_EMP, UtilUi.MSG.crmNumberOfEmployees(),
                PartyLookupConfiguration.OUT_ACCOUNT_OWNERSHIP_DESC, UtilUi.MSG.crmOwnership(),
                PartyLookupConfiguration.OUT_ACCOUNT_TYPE_DESC, "Account Type",
                //PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC, "Assigned To"
            });
    }

    /**
     * Filters the records of the list by name of the account matching the given sub string.
     * @param accountName a <code>String</code> value
     */
    public void filterByAccountName(String accountName) {
        setFilter(PartyLookupConfiguration.INOUT_GROUP_NAME, accountName);
    }
    
    public void filterByEmail(String email) {
        setFilter(PartyLookupConfiguration.INOUT_EMAIL, email);
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
    
//    public void filterByWebsite(String website) {
//        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_WEBSITE, website);
//    }
    
//    public void filterByAnnualRevenue(String annualRev) {
//        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_ANNUAL_REVENEU, annualRev);
//    }
//    
//    public void filterBySICCode(String sicCode) {
//        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_SIC_CODE, sicCode);
//    }
    
    public void filterByRating(String rating) {
        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_RATING, rating);
    }
    
//    public void filterByNumberOfEmp(String numOfEmp) {
//        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_NUMBER_OF_EMP, numOfEmp);
//    }
//    
//    public void filterByTickerSymbol(String tickerSymbol) {
//        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_TICKER_SYMBOL, tickerSymbol);
//    }
    
    public void filterByOwnership(String ownership) {
        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_OWNERSHIP, ownership);
    }
    
    public void filterByAccountType(String accountType) {
        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_TYPE, accountType);
    }
    
    public void filterByIndustry(String industry) {
        setFilter(PartyLookupConfiguration.INOUT_ACCOUNT_INDUSTRY, industry);
    }
}
