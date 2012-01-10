/*
 * Copyright (c) 2009 Open Source Strategies, Inc.
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

package org.opentaps.fitnesse.crmsfa;

import com.thoughtworks.selenium.Selenium;

import org.opentaps.fitnesse.ContextManager;
import org.opentaps.fitnesse.OpentapsCommon;

/**
 * Fixtures for the CRMSFA application.
 */
public class OpentapsCrmsfa extends OpentapsCommon {

    private static final String DEFAULT_STATE = "TX";
    private static final String DEFAULT_ZIP_CODE = "123456";
    private static final String DEFAULT_CITY = "Test City";

    /**
     * Creates a new <code>OpentapsCrmsfa</code> instance.
     * @param selenium a <code>Selenium</code> value
     * @param contextManager a <code>ContextManager</code> value
     */
    public OpentapsCrmsfa(Selenium selenium, ContextManager contextManager) {
        super(selenium, contextManager);
        loadUiElements(CrmsfaElements.values());
    }

    /** {@inheritDoc} */
    public String getWebappRoot() {
        return "crmsfa";
    }

    //---------------------
    // Some action methods
    //---------------------

    /**
     * Goes to the view page for the given Account.
     * @param key the variable name where the account ID is stored
     */
    public void viewAccount(String key) {
        goToPage(getIndexedUiElement(CrmsfaElements.VIEW_ACCOUNT_PAGE, translate(key)));
    }
    
    /**
     * Goes to the view page for the given Lead.
     * @param key the variable name where the lead ID is stored
     */
    public void viewLead(String key) {
        goToPage(getIndexedUiElement(CrmsfaElements.VIEW_LEAD_PAGE, translate(key)));
    }
    
    /**
     * Goes to the view page for the given Case.
     * @param key the variable name where the case ID is stored
     */
    public void viewCase(String key) {
        goToPage(getIndexedUiElement(CrmsfaElements.VIEW_CASE_PAGE, translate(key)));
    }
    
    /**
     * Goes to the view page for the given Event.
     * @param key the variable name where the work effort ID is stored
     */
    public void viewEvent(String key) {
        goToPage(getIndexedUiElement(CrmsfaElements.VIEW_EVENT_PAGE, translate(key)));
    }
    
    /**
     * Goes to the view page for the given Task.
     * @param key the variable name where the work effort ID is stored
     */
    public void viewTask(String key) {
        goToPage(getIndexedUiElement(CrmsfaElements.VIEW_TASK_PAGE, translate(key)));
    }

    /**
     * Creates an Account and saves the created account id into the given variable.
     * @param key the variable name where to store the account id
     * @param name the account name
     */
    public void createAccountWithName(String key, String name) {
        createAccountWithNameWithState(key, name, DEFAULT_STATE);
    }

    /**
     * Creates an Account and saves the created account id into the given variable.
     * @param key the variable name where to store the account id
     * @param name the account name
     * @param state the account state (in the address)
     */
    public void createAccountWithNameWithState(String key, String name, String state) {
        createAccountWithNameWithCityWithStateWithZipCode(key, name, DEFAULT_CITY, state, DEFAULT_ZIP_CODE);
    }

    /**
     * Creates an Account and saves the created account id into the given variable.
     * @param key the variable name where to store the account id
     * @param name the account name
     * @param zipCode the account zip code
     * @param state the account state (in the address)
     */
    public void createAccountWithNameWithZipCodeWithState(String key, String name, String zipCode, String state) {
        createAccountWithNameWithCityWithStateWithZipCode(key, name, DEFAULT_CITY, zipCode, state);
    }

    /**
     * Creates an Account and saves the created account id into the given variable.
     * @param key the variable name where to store the account id
     * @param name the account name
     * @param city the account city
     * @param state the account state (in the address)
     * @param zipCode the account zip code
     */
    public void createAccountWithNameWithCityWithStateWithZipCode(String key, String name, String city, String state, String zipCode) {
        goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACCOUNTS);
        clickButton(CrmsfaElements.ACCOUNTS_SHORTCUT_CREATE_ACCOUNT);
        typeTextInValue(CrmsfaElements.CREATE_ACCOUNT_NAME, name);
        typeTextInValue(CrmsfaElements.CREATE_ACCOUNT_TO_NAME, "test");
        typeTextInValue(CrmsfaElements.CREATE_ACCOUNT_ADDRESS, "100 Park Avenue");
        typeTextInValue(CrmsfaElements.CREATE_ACCOUNT_CITY, city);
        typeTextInValue(CrmsfaElements.CREATE_ACCOUNT_POSTAL_CODE, zipCode);
        selectFromValue(CrmsfaElements.CREATE_ACCOUNT_STATE, state);
        clickButton(CrmsfaElements.CREATE_ACCOUNT_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_ACCOUNT_ID, key);
    }
    
    /**
     * Creates a Lead and saves the created lead id into the given variable.
     * @param key the variable name where to store the lead id
     * @param companyName the lead company name
     * @param firstName the lead first name
     * @param lastName the lead last name
     * @param toName the lead to name
     * @param address the lead address
     * @param city the lead city
     * @param zipCode the lead zip code
     * @param country the lead country
     */
    public void createLeadWithCompanyNameWithFirstNameWithLastNameWithToNameWithAddressWithCityWithZipCodeWithCountry(String key, String companyName, String firstName, String lastName, String toName, String address, String city, String zipCode, String country) {
        goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_LEADS);
        clickButton(CrmsfaElements.LEADS_SHORTCUT_CREATE_LEAD);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_COMPANY_NAME, companyName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_FIRST_NAME, firstName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_LAST_NAME, lastName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_TO_NAME, toName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_ADDRESS, address);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_CITY, city);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_POSTAL_CODE, zipCode);
        selectFromValue(CrmsfaElements.CREATE_LEAD_COUNTRY, country);
        clickButton(CrmsfaElements.CREATE_LEAD_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_LEAD_ID, key);
    }
    
    /**
     * Creates a Lead and saves the created lead id into the given variable.
     * @param key the variable name where to store the lead id
     * @param companyName the lead company name
     * @param firstName the lead first name
     * @param lastName the lead last name
     * @param toName the lead to name
     * @param address the lead address
     * @param city the lead city
     * @param state the lead state (in the address) 
     * @param zipCode the lead zip code
     * @param country the lead country
     */
    public void createLeadWithCompanyNameWithFirstNameWithLastNameWithToNameWithAddressWithCityWithStateWithZipCodeWithCountry(String key, String companyName, String firstName, String lastName, String toName, String address, String city, String state, String zipCode, String country) {
        goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_LEADS);
        clickButton(CrmsfaElements.LEADS_SHORTCUT_CREATE_LEAD);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_COMPANY_NAME, companyName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_FIRST_NAME, firstName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_LAST_NAME, lastName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_TO_NAME, toName);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_ADDRESS, address);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_CITY, city);
        typeTextInValue(CrmsfaElements.CREATE_LEAD_POSTAL_CODE, zipCode);
        selectFromValue(CrmsfaElements.CREATE_LEAD_STATE, state);
        selectFromValue(CrmsfaElements.CREATE_LEAD_COUNTRY, country);
        clickButton(CrmsfaElements.CREATE_LEAD_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_LEAD_ID, key);
    }
    
    /**
     * Merges Leads
     * @param key
     * @param fromLead
     * @param toLead
     */
    public void mergeLeadsWithFromLeadWithToLead(String key, String fromLead, String toLead){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_LEADS);
        clickButton(CrmsfaElements.LEADS_SHORTCUT_MERGE_LEADS);
        typeTextInValue(CrmsfaElements.MERGE_LEADS_PARTY_ID_FROM, fromLead);
        typeTextInValue(CrmsfaElements.MERGE_LEADS_PARTY_ID_TO, toLead);
        clickButton(CrmsfaElements.MERGE_LEADS_SUBMIT_BUTTON);
        clickButton(CrmsfaElements.CONFIRM_MERGE_LEADS_SUBMIT_BUTTON);
    }
    
    /**
     * Merges Contacts
     * @param key
     * @param fromContact
     * @param toContact
     */
    public void mergeContactsWithFromContactWithToContact(String key, String fromContact, String toContact){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_CONTACTS);
        clickButton(CrmsfaElements.CONTACTS_SHORTCUT_MERGE_CONTACTS);
        typeTextInValue(CrmsfaElements.MERGE_CONTACTS_PARTY_ID_FROM, fromContact);
        typeTextInValue(CrmsfaElements.MERGE_CONTACTS_PARTY_ID_TO, toContact);
        clickButton(CrmsfaElements.MERGE_CONTACTS_SUBMIT_BUTTON);
        clickButton(CrmsfaElements.CONFIRM_MERGE_CONTACTS_SUBMIT_BUTTON);
    }
    
    /**
     * Merges Accounts
     * @param key
     * @param fromAccount
     * @param toAccount
     */
    public void mergeAccountsWithFromAccountWithToAccount(String key, String fromAccount, String toAccount){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACCOUNTS);
        clickButton(CrmsfaElements.ACCOUNTS_SHORTCUT_MERGE_ACCOUNTS);
        typeTextInValue(CrmsfaElements.MERGE_ACCOUNTS_PARTY_ID_FROM, fromAccount);
        typeTextInValue(CrmsfaElements.MERGE_ACCOUNTS_PARTY_ID_TO, toAccount);
        clickButton(CrmsfaElements.MERGE_ACCOUNTS_SUBMIT_BUTTON);
        clickButton(CrmsfaElements.CONFIRM_MERGE_ACCOUNTS_SUBMIT_BUTTON);
    }
    
    /**
     * Creates a Case and saves the created case id into the given variable.
     * @param key
     * @param accountId
     * @param contactId
     * @param priority
     * @param type
     * @param reason
     * @param subject
     * @param description
     * @param note
     */
    public void createCaseWithAccountIdWithContactIdWithPriorityWithTypeWithReasonWithSubjectWithDescriptionWithNote(String key, String accountId, String contactId, String priority, String type, String reason, String subject, String description, String note){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_CASES);
        clickButton(CrmsfaElements.CASES_SHORTCUT_CREATE_CASE);
        typeTextInValue(CrmsfaElements.CREATE_CASE_ACCOUNT_PARTY_ID, accountId);
        typeTextInValue(CrmsfaElements.CREATE_CASE_CONTACT_PARTY_ID, contactId);
        typeTextInValue(CrmsfaElements.CREATE_CASE_PRIORITY, priority);
        typeTextInValue(CrmsfaElements.CREATE_CASE_TYPE, type);
        typeTextInValue(CrmsfaElements.CREATE_CASE_REASON, reason);
        typeTextInValue(CrmsfaElements.CREATE_CASE_SUBJECT, subject);
        typeTextInValue(CrmsfaElements.CREATE_CASE_DESCRIPTION, description);
        typeTextInValue(CrmsfaElements.CREATE_CASE_INTERNAL_NOTE, note);                
        clickButton(CrmsfaElements.CREATE_CASE_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_CASE_CUST_REQUEST_ID, key);
    }
    
    /**
     * Creates an Event and saves the created work effort id into the given variable.
     * @param key
     * @param name
     * @param security
     * @param relatedPartyId
     * @param opportunityId
     * @param caseId
     * @param scheduledStartDate
     * @param endDateTime
     * @param duration
     * @param location
     * @param description
     * @param availability
     * @param ignoreSchedulingConflicts
     */
    public void createEventWithNameWithSecurityWithRelatedPartyIdWithOpportunityIdWithCaseIdWithScheduledStartDateWithEndDateTimeWithDurationWithLocationWithDescriptionWithAvailabilityWithIgnoreSchedulingConflicts(String key, String name, String security, String relatedPartyId, String opportunityId, String caseId, String scheduledStartDate, String endDateTime, String duration, String location, String description, String availability, String ignoreSchedulingConflicts){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACTIVITIES);
        clickButton(CrmsfaElements.ACTIVITIES_SHORTCUT_CREATE_EVENT);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_NAME, name);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_SECURITY, security);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_RELATED_PARTY_ID, relatedPartyId);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_OPPORTUNITY_ID, opportunityId);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_CASE_ID, caseId);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_SCHEDULED_START_DATE, scheduledStartDate);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_END_DATE_TIME, endDateTime);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_DURATION, duration);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_LOCATION, location);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_DESCRIPTION, description);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_AVAILABILITY, availability);
        typeTextInValue(CrmsfaElements.CREATE_EVENT_IGNORE_SCHEDULING_CONFLICTS, ignoreSchedulingConflicts);
        clickButton(CrmsfaElements.CREATE_EVENT_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_EVENT_WORK_EFFORT_ID, key);
    }
    
    /**
     * Creates a Task and saves the created work effort id into the given variable.
     * @param key
     * @param subject
     * @param security
     * @param relatedPartyId
     * @param opportunityId
     * @param caseId
     * @param purpose
     * @param startDateTime
     * @param endDateTime
     * @param duration
     * @param description
     * @param availability
     * @param ignoreSchedulingConflicts
     */
    public void createTaskWithSubjectWithSecurityWithRelatedPartyIdWithOpportunityIdWithCaseIdWithPurposeWithStartDateTimeWithEndDateTimeWithDurationWithDescriptionWithAvailabilityWithIgnoreSchedulingConflicts(String key, String subject, String security, String relatedPartyId, String opportunityId, String caseId, String purpose, String startDateTime, String endDateTime, String duration, String description, String availability, String ignoreSchedulingConflicts){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACTIVITIES);
        clickButton(CrmsfaElements.ACTIVITIES_SHORTCUT_CREATE_TASK);
        typeTextInValue(CrmsfaElements.CREATE_TASK_SUBJECT, subject);
        typeTextInValue(CrmsfaElements.CREATE_TASK_SECURITY, security);
        typeTextInValue(CrmsfaElements.CREATE_TASK_RELATED_PARTY_ID, relatedPartyId);
        typeTextInValue(CrmsfaElements.CREATE_TASK_OPPORTUNITY_ID, opportunityId);
        typeTextInValue(CrmsfaElements.CREATE_TASK_CASE_ID, caseId);
        typeTextInValue(CrmsfaElements.CREATE_TASK_START_DATE_TIME, startDateTime);
        typeTextInValue(CrmsfaElements.CREATE_TASK_END_DATE_TIME, endDateTime);
        typeTextInValue(CrmsfaElements.CREATE_TASK_DURATION, duration);
        typeTextInValue(CrmsfaElements.CREATE_TASK_DESCRIPTION, description);
        typeTextInValue(CrmsfaElements.CREATE_TASK_AVAILABILITY, availability);
        typeTextInValue(CrmsfaElements.CREATE_TASK_IGNORE_SCHEDULING_CONFLICTS, ignoreSchedulingConflicts);
        clickButton(CrmsfaElements.CREATE_TASK_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.CREATED_TASK_WORK_EFFORT_ID, key);
    }
    
    /**
     * Logs an email Task and saves the created work effort id into the given variable.
     * @param key
     * @param inboundOutbound
     * @param internalPartyId
     * @param externalPartyId
     * @param opportunityId
     * @param caseId
     * @param subject
     * @param message
     * @param startDate
     * @param duration
     */
    public void logEmailTaskWithOutboundWithInternalPartyIdWithExternalPartyIdWithOpportunityIdWithCaseIdWithSubjectWithMessageWithStartDateWithDuration(String key, String outbound, String internalPartyId, String externalPartyId, String opportunityId, String caseId, String subject, String message, String startDate, String duration){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACTIVITIES);
        clickButton(CrmsfaElements.ACTIVITIES_SHORTCUT_LOG_EMAIL);
        typeTextInValue(CrmsfaElements.LOG_TASK_INBOUND_OUTBOUND, outbound);
        typeTextInValue(CrmsfaElements.LOG_TASK_INTERNAL_PARTY_ID, internalPartyId);
        typeTextInValue(CrmsfaElements.LOG_TASK_EXTERNAL_PARTY_ID, externalPartyId);
        typeTextInValue(CrmsfaElements.LOG_TASK_OPPORTUNITY_ID, opportunityId);
        typeTextInValue(CrmsfaElements.LOG_TASK_CASE_ID, caseId);
        typeTextInValue(CrmsfaElements.LOG_TASK_SUBJECT, subject);
        typeTextInValue(CrmsfaElements.LOG_TASK_MESSAGE, message);
        typeTextInValue(CrmsfaElements.LOG_TASK_START_DATE, startDate);
        typeTextInValue(CrmsfaElements.LOG_TASK_DURATION, duration);
        clickButton(CrmsfaElements.LOG_TASK_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.LOGGED_TASK_WORK_EFFORT_ID, key);
    }
    
    /**
     * Logs a call Task and saves the created work effort id into the given variable.
     * @param key
     * @param inboundOutbound
     * @param internalPartyId
     * @param externalPartyId
     * @param opportunityId
     * @param caseId
     * @param subject
     * @param message
     * @param startDate
     * @param duration
     */
    public void logCallTaskWithOutboundWithInternalPartyIdWithExternalPartyIdWithOpportunityIdWithCaseIdWithSubjectWithMessageWithStartDateWithDuration(String key, String outbound, String internalPartyId, String externalPartyId, String opportunityId, String caseId, String subject, String message, String startDate, String duration){
    	goToMainPage();
        clickButton(CrmsfaElements.MAIN_TAB_ACTIVITIES);
        clickButton(CrmsfaElements.ACTIVITIES_SHORTCUT_LOG_CALL);
        typeTextInValue(CrmsfaElements.LOG_TASK_INBOUND_OUTBOUND, outbound);
        typeTextInValue(CrmsfaElements.LOG_TASK_INTERNAL_PARTY_ID, internalPartyId);
        typeTextInValue(CrmsfaElements.LOG_TASK_EXTERNAL_PARTY_ID, externalPartyId);
        typeTextInValue(CrmsfaElements.LOG_TASK_OPPORTUNITY_ID, opportunityId);
        typeTextInValue(CrmsfaElements.LOG_TASK_CASE_ID, caseId);
        typeTextInValue(CrmsfaElements.LOG_TASK_SUBJECT, subject);
        typeTextInValue(CrmsfaElements.LOG_TASK_MESSAGE, message);
        typeTextInValue(CrmsfaElements.LOG_TASK_START_DATE, startDate);
        typeTextInValue(CrmsfaElements.LOG_TASK_DURATION, duration);
        clickButton(CrmsfaElements.LOG_TASK_SUBMIT_BUTTON);
        saveValueIn(CrmsfaElements.LOGGED_TASK_WORK_EFFORT_ID, key);
    }
}
