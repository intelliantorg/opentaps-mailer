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

import org.opentaps.fitnesse.UiElementInterface;
import org.opentaps.fitnesse.UtilElements;

/**
 * Defines the CRMSFA UI Elements.
 */
public enum CrmsfaElements implements UiElementInterface {

    // Main Tabs buttons, we find them by index in the Tab bar
    //  revisit this id the tab ordering changed
    /** My Home tab. */
    MAIN_TAB_HOME(UtilElements.mainTabByIndex(1)),
    /** Leads tab. */
    MAIN_TAB_LEADS(UtilElements.mainTabByIndex(2)),
    /** Contacts tab. */
    MAIN_TAB_CONTACTS(UtilElements.mainTabByIndex(3)),
    /** Accounts tab. */
    MAIN_TAB_ACCOUNTS(UtilElements.mainTabByIndex(4)),
    /** Cases tab. */
    MAIN_TAB_CASES(UtilElements.mainTabByIndex(5)),
    /** Activities tab. */
    MAIN_TAB_ACTIVITIES(UtilElements.mainTabByIndex(6)),
    /** Opportunities tab. */
    MAIN_TAB_OPPORTUNITIES(UtilElements.mainTabByIndex(7)),
    /** Quotes tab. */
    MAIN_TAB_QUOTES(UtilElements.mainTabByIndex(8)),
    /** Orders tab. */
    MAIN_TAB_ORDERS(UtilElements.mainTabByIndex(9)),
    /** Forecasts tab. */
    MAIN_TAB_FORECASTS(UtilElements.mainTabByIndex(10)),
    /** Marketing tab. */
    MAIN_TAB_MARKETING(UtilElements.mainTabByIndex(11)),
    /** Partners tab. */
    MAIN_TAB_PARTNERS(UtilElements.mainTabByIndex(12)),
    /** Teams tab. */
    MAIN_TAB_TEAMS(UtilElements.mainTabByIndex(13)),

    // Shortcuts
    /** My accounts or My Teams' accounts find shortcut. */
    ACCOUNTS_SHORTCUT_MY_ACCOUNTS(UtilElements.leftBarShortcutByIndex(1)),
    /** Create account shortcut. */
    ACCOUNTS_SHORTCUT_CREATE_ACCOUNT(UtilElements.leftBarShortcutByIndex(2)),
    /** Find accounts shortcut. */
    ACCOUNTS_SHORTCUT_FIND_ACCOUNTS(UtilElements.leftBarShortcutByIndex(3)),
    /** Merge accounts shortcut. */
    ACCOUNTS_SHORTCUT_MERGE_ACCOUNTS(UtilElements.leftBarShortcutByIndex(4)),
    
    /** My leads or My Team's find shortcut. */
    LEADS_SHORTCUT_MY_LEADS(UtilElements.leftBarShortcutByIndex(1)),
    /** Create lead shortcut. */
    LEADS_SHORTCUT_CREATE_LEAD(UtilElements.leftBarShortcutByIndex(2)),
    /** Find leads shortcut. */
    LEADS_SHORTCUT_FIND_LEADS(UtilElements.leftBarShortcutByIndex(3)),
    /** Merge leads shortcut. */
    LEADS_SHORTCUT_MERGE_LEADS(UtilElements.leftBarShortcutByIndex(4)),
    /** Request catalog shortcut. */
    LEADS_SHORTCUT_REQUEST_CATALOG(UtilElements.leftBarShortcutByIndex(5)),
    
    /** My contacts shortcut. */
    CONTACTS_SHORTCUT_MY_CONTACTS(UtilElements.leftBarShortcutByIndex(1)),
    /** Create account shortcut. */
    CONTACTS_SHORTCUT_CREATE_CONTACT(UtilElements.leftBarShortcutByIndex(2)),
    /** Find accounts shortcut. */
    CONTACTS_SHORTCUT_FIND_CONTACTS(UtilElements.leftBarShortcutByIndex(3)),
    /** Merge accounts shortcut. */
    CONTACTS_SHORTCUT_MERGE_CONTACTS(UtilElements.leftBarShortcutByIndex(4)),
    
    /** My opportunity shortcut. */
    OPPORTUNITIES_SHORTCUT_MY_OPPORTUNITIES(UtilElements.leftBarShortcutByIndex(1)),
    /** Create opportunity shortcut. */
    OPPORTUNITIES_SHORTCUT_CREATE_OPPORTUNITY(UtilElements.leftBarShortcutByIndex(2)),
    /** Find opportunity shortcut. */
    OPPORTUNITIES_SHORTCUT_FIND_OPPORTUNITIES(UtilElements.leftBarShortcutByIndex(3)),
    /** Create opportunity shortcut. */
    OPPORTUNITIES_SHORTCUT_MERGE_OPPORTUNITIES(UtilElements.leftBarShortcutByIndex(4)),
    
    /** Activities shortcuts. */
    ACTIVITIES_SHORTCUT_MY_CALENDAR(UtilElements.leftBarShortcutByIndex(1)),
    /** Pending e-mails shortcut. */
    ACTIVITIES_SHORTCUT_PENDING_EMAILS(UtilElements.leftBarShortcutByIndex(2)),
    /** Create event shortcut. */
    ACTIVITIES_SHORTCUT_CREATE_EVENT(UtilElements.leftBarShortcutByIndex(3)),
    /** Create task shortcut. */
    ACTIVITIES_SHORTCUT_CREATE_TASK(UtilElements.leftBarShortcutByIndex(4)),
    /** Find activities shortcut. */
    ACTIVITIES_SHORTCUT_FIND_ACTIVITIES(UtilElements.leftBarShortcutByIndex(5)),
    /** Log email shortcut. */
    ACTIVITIES_SHORTCUT_LOG_EMAIL(UtilElements.leftBarShortcutByIndex(6)),
    /** Log call shortcut. */
    ACTIVITIES_SHORTCUT_LOG_CALL(UtilElements.leftBarShortcutByIndex(7)),
    
    /** My Cases or My Team's find shortcut. */
    CASES_SHORTCUT_MY_CASES(UtilElements.leftBarShortcutByIndex(1)),
    /** Create case shortcut. */
    CASES_SHORTCUT_CREATE_CASE(UtilElements.leftBarShortcutByIndex(2)),
    /** Find leads shortcut. */
    CASES_SHORTCUT_FIND_CASES(UtilElements.leftBarShortcutByIndex(3)),
    
    // Create Account Form
    /** The create account form. */
    CREATE_ACCOUNT_FORM("//form[@name=\"createAccountForm\"]"),
    /** The account name field for the account. */
    CREATE_ACCOUNT_NAME(UtilElements.formInput(CREATE_ACCOUNT_FORM, "accountName")),
    /** The to name field for the account. */
    CREATE_ACCOUNT_TO_NAME(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalToName")),
    /** The address field for the account. */
    CREATE_ACCOUNT_ADDRESS(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalAddress1")),
    /** The city field for the account. */
    CREATE_ACCOUNT_CITY(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalCity")),
    /** The postal code field for the account. */
    CREATE_ACCOUNT_POSTAL_CODE(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalPostalCode")),
    /** The postal code field for the account. */
    CREATE_ACCOUNT_COUNTRY(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalCountryGeoId", "select")),
    /** The postal code field for the account. */
    CREATE_ACCOUNT_STATE(UtilElements.formInput(CREATE_ACCOUNT_FORM, "generalStateProvinceGeoId", "select")),
    /** Submit the create account form. */
    CREATE_ACCOUNT_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_ACCOUNT_FORM)),
    /** The created account ID after the create account form has been submitted. Note: here we get it from the <code>assignContactToAccountForm</code>. */
    CREATED_ACCOUNT_ID("accountPartyId"),
    
    // Create Lead Form
    /** The create lead form. */
    CREATE_LEAD_FORM("//form[@name=\"createLeadForm\"]"),
    /** The company name field for the lead. */
    CREATE_LEAD_COMPANY_NAME(UtilElements.formInput(CREATE_LEAD_FORM, "companyName")),
    /** The first name field for the lead. */
    CREATE_LEAD_FIRST_NAME(UtilElements.formInput(CREATE_LEAD_FORM, "firstName")),
    /** The last name field for the lead. */
    CREATE_LEAD_LAST_NAME(UtilElements.formInput(CREATE_LEAD_FORM, "lastName")),
    /** The to name field for the lead. */
    CREATE_LEAD_TO_NAME(UtilElements.formInput(CREATE_LEAD_FORM, "generalToName")),
    /** The address field for the lead. */
    CREATE_LEAD_ADDRESS(UtilElements.formInput(CREATE_LEAD_FORM, "generalAddress1")),
    /** The city field for the lead. */
    CREATE_LEAD_CITY(UtilElements.formInput(CREATE_LEAD_FORM, "generalCity")),
    /** The postal code field for the lead. */
    CREATE_LEAD_POSTAL_CODE(UtilElements.formInput(CREATE_LEAD_FORM, "generalPostalCode")),
    /** The postal code field for the lead. */
    CREATE_LEAD_COUNTRY(UtilElements.formInput(CREATE_LEAD_FORM, "generalCountryGeoId", "select")),
    /** The postal code field for the lead. */
    CREATE_LEAD_STATE(UtilElements.formInput(CREATE_LEAD_FORM, "generalStateProvinceGeoId", "select")),
    /** Submit the create lead form. */
    CREATE_LEAD_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_LEAD_FORM)),
    /** The created lead ID after the create lead form has been submitted. Note: here we get it from the <code>convertLeadForm</code>. */
    CREATED_LEAD_ID("leadPartyId"),
    
    // Create Contact Form
    /** The create contact form. */
    CREATE_CONTACT_FORM("//form[@name=\"createContactForm\"]"),
    /** The first name field for the contact. */
    CREATE_CONTACT_FIRST_NAME(UtilElements.formInput(CREATE_CONTACT_FORM, "firstName")),
    /** The last name field for the contact. */
    CREATE_CONTACT_LAST_NAME(UtilElements.formInput(CREATE_CONTACT_FORM, "lastName")),
    /** The first name (Local) field for the contact. */
    CREATE_CONTACT_FIRST_NAME_LOCAL(UtilElements.formInput(CREATE_CONTACT_FORM, "firstNameLocal")),
    /** The last name (Local) field for the contact. */
    CREATE_CONTACT_LAST_NAME_LOCAL(UtilElements.formInput(CREATE_CONTACT_FORM, "lastNameLocal")),
    /** The salutation field for the contact. */
    CREATE_CONTACT_PERSONAL_TITLE(UtilElements.formInput(CREATE_CONTACT_FORM, "personalTitle")),
    /** The birth date field for the contact. */
    CREATE_CONTACT_BIRTH_DATE(UtilElements.formInput(CREATE_CONTACT_FORM, "birthDate")),
    /** The title field for the contact. */
    CREATE_CONTACT_GENERAL_PROF_TITLE(UtilElements.formInput(CREATE_CONTACT_FORM, "generalProfTitle")),
    /** The department field for the contact. */
    CREATE_CONTACT_DEPARTMENT(UtilElements.formInput(CREATE_CONTACT_FORM, "departmentName")),
    /** The preferred currency uom id field for the contact. */
    CREATE_CONTACT_PREFERRED_CURRENCY_UOM_ID(UtilElements.formInput(CREATE_CONTACT_FORM, "preferredCurrencyUomId", "select")),
    /** The  field for the contact. */
    CREATE_CONTACT_ACCOUNT_PARTY_ID(UtilElements.formInput(CREATE_CONTACT_FORM, "accountPartyId")),
    /** The description  field for the contact. */
    CREATE_CONTACT_DESCRIPTION(UtilElements.formInput(CREATE_CONTACT_FORM, "description", "textarea")),
    /** The important note field for the contact. */
    CREATE_CONTACT_IMPORTANT_NOTE(UtilElements.formInput(CREATE_CONTACT_FORM, "importantNote", "textarea")),
    /** The primary phone country code field for the contact. */
    CREATE_CONTACT_PRIMARY_PHONE_COUNTRY_CODE(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryPhoneCountryCode")),
    /** The primary phone area code field for the contact. */
    CREATE_CONTACT_PRIMARY_PHONE_AREA_CODE(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryPhoneAreaCode")),
    /** The primary phone number field for the contact. */
    CREATE_CONTACT_PRIMARY_PHONE_NUMBER(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryPhoneNumber")),
    /** The primary phone extension field for the contact. */
    CREATE_CONTACT_PRIMARY_PHONE_EXTENSION(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryPhoneExtension")),
    /** The primary phone ask for name field for the contact. */
    CREATE_CONTACT_PRIMARY_PHONE_ASK_FOR_NAME(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryPhoneAskForName")),
    /** The primary email field for the contact. */
    CREATE_CONTACT_PRIMARY_EMAIL(UtilElements.formInput(CREATE_CONTACT_FORM, "primaryEmail")),
    /** The general to name field for the contact. */
    CREATE_CONTACT_GENERAL_TO_NAME(UtilElements.formInput(CREATE_CONTACT_FORM, "generalToName")),
    /** The general attention name field for the contact. */
    CREATE_CONTACT_GENERAL_ATTN_NAME(UtilElements.formInput(CREATE_CONTACT_FORM, "generalAttnName")),
    /** The general address one field for the contact. */
    CREATE_CONTACT_GENERAL_ADDRESS_ONE(UtilElements.formInput(CREATE_CONTACT_FORM, "generalAddress1")),
    /** The general address two field for the contact. */
    CREATE_CONTACT_GENERAL_ADDRESS_TWO(UtilElements.formInput(CREATE_CONTACT_FORM, "generalAddress2")),
    /** The general city field for the contact. */
    CREATE_CONTACT_GENERAL_CITY(UtilElements.formInput(CREATE_CONTACT_FORM, "generalCity")),
    /** The general state province geo id field for the contact. */
    CREATE_CONTACT_GENERAL_STATE_PROVINCE_GEO_ID(UtilElements.formInput(CREATE_CONTACT_FORM, "generalStateProvinceGeoId", "select")),
    /** The general postal code field for the contact. */
    CREATE_CONTACT_GENERAL_POSTAL_CODE(UtilElements.formInput(CREATE_CONTACT_FORM, "generalPostalCode")),
    /** The general country geo id field for the contact. */
    CREATE_CONTACT_GENERAL_COUNTRY_GEO_ID(UtilElements.formInput(CREATE_CONTACT_FORM, "generalCountryGeoId", "select")),
    /** The general postal code ext field for the contact. */
    CREATE_CONTACT_GENERAL_POSTAL_CODE_EXT(UtilElements.formInput(CREATE_CONTACT_FORM, "generalPostalCodeExt")),
    /** Submit the create contact form. */
    CREATE_CONTACT_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_CONTACT_FORM)),
    /** The created lead ID after the create lead form has been submitted. Note: here we get it from the <code>reassignToForm</code>. */
    CREATED_CONTACT_ID("contactPartyId"),
    
    // Create Opportunity Form
    /** The create opportunity form. */
    CREATE_OPPORTUNITY_FORM("//form[@name=\"createOpportunityForm\"]"),
    /** The opportunity name field for the opportunity. */
    CREATE_OPPORTUNITY_NAME(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "opportunityName")),
    /** The account party field for the opportunity. */
    CREATE_OPPORTUNITY_ACCOUNT_NAME(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "accountPartyId")),
    /** The lead field for the opportunity. */
    CREATE_OPPORTUNITY_LEAD_NAME(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "leadPartyId")),
    /** The contact field for the opportunity. */
    CREATE_OPPORTUNITY_CONTACT_NAME(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "contactPartyId")),
    /** The stage field for the opportunity. */
    CREATE_OPPORTUNITY_STAGE(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "opportunityStageId", "select")),
    /** The type field for the opportunity. */
    CREATE_OPPORTUNITY_TYPE(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "typeEnumId", "select")),
    /** The estimated amount field for the opportunity. */
    CREATE_OPPORTUNITY_ESTIMATED_AMOUNT(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "estimatedAmount")),
    /** The currency uom id field for the opportunity. */
    CREATE_OPPORTUNITY_CURRENCY_UOM_ID(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "currencyUomId", "select")),
    /** The marketing campaign field for the opportunity. */
    CREATE_OPPORTUNITY_MARKETING_CAMPAIGN_ID(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "marketingCampaignId", "select")),
    /** The data source field for the opportunity. */
    CREATE_OPPORTUNITY_DATA_SOURCE_ID(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "dataSourceId", "select")),
    /** The estimated close date for the opportunity. */
    CREATE_OPPORTUNITY_ESTIMATED_CLOSE_DATE(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "estimatedCloseDate")),
    /** The description field for the opportunity. */
    CREATE_OPPORTUNITY_DESCRIPTION(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "description", "textarea")),
    /** The next step field for the opportunity. */
    CREATE_OPPORTUNITY_NEXT_STEP(UtilElements.formInput(CREATE_OPPORTUNITY_FORM, "nextStep", "textarea")),
    /** Submit the create opportunity form. */
    CREATE_OPPORTUNITY_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_OPPORTUNITY_FORM)),
    /** The created sales opportunity ID after the create opportunity form has been submitted. Note: here we get it from the <code>updateOpportunityForm</code>. */
    CREATED_SALES_OPPORTUNITY_ID("salesOpportunityId"),

    // Create Case Form
    /** The create lead form. */
    CREATE_CASE_FORM("//form[@name=\"createCaseForm\"]"),
    /** The account party id field for the case. */
    CREATE_CASE_ACCOUNT_PARTY_ID(UtilElements.formInput(CREATE_CASE_FORM, "accountPartyId")),
    /** The contact party id field for the case. */
    CREATE_CASE_CONTACT_PARTY_ID(UtilElements.formInput(CREATE_CASE_FORM, "contactPartyId")),
    /** The priority field for the case. */
    CREATE_CASE_PRIORITY(UtilElements.formInput(CREATE_CASE_FORM, "priority", "select")),
    /** The type field for the case. */
    CREATE_CASE_TYPE(UtilElements.formInput(CREATE_CASE_FORM, "custRequestTypeId", "select")),
    /** The reason field for the case. */
    CREATE_CASE_REASON(UtilElements.formInput(CREATE_CASE_FORM, "custRequestCategoryId", "select")),
    /** The subject field for the case. */
    CREATE_CASE_SUBJECT(UtilElements.formInput(CREATE_CASE_FORM, "custRequestName")),
    /** The description field for the case. */
    CREATE_CASE_DESCRIPTION(UtilElements.formInput(CREATE_CASE_FORM, "description", "textarea")),
    /** The internal note field for the case. */
    CREATE_CASE_INTERNAL_NOTE(UtilElements.formInput(CREATE_CASE_FORM, "note", "textarea")),
    /** Submit the create case form. */
    CREATE_CASE_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_CASE_FORM)),
    /** The created cust request ID after the create case form has been submitted. Note: here we get it from the <code>updateCaseForm</code>. */
    CREATED_CASE_CUST_REQUEST_ID("custRequestId"),
    
    // Create Event Form
    /** The create event form. */
    CREATE_EVENT_FORM("//form[@name=\"createEventForm\"]"),
    /** The name field for the event. */
    CREATE_EVENT_NAME(UtilElements.formInput(CREATE_EVENT_FORM, "workEffortName")),
    /** The security field for the event. */
    CREATE_EVENT_SECURITY(UtilElements.formInput(CREATE_EVENT_FORM, "scopeEnumId", "select")),
    /** The related party id field for the event. */
    CREATE_EVENT_RELATED_PARTY_ID(UtilElements.formInput(CREATE_EVENT_FORM, "internalPartyId")),
    /** The opportunity id field for the event. */
    CREATE_EVENT_OPPORTUNITY_ID(UtilElements.formInput(CREATE_EVENT_FORM, "salesOpportunityId")),
    /** The case id field for the event. */
    CREATE_EVENT_CASE_ID(UtilElements.formInput(CREATE_EVENT_FORM, "custRequestId")),
    /** The scheduled start date field for the event. */
    CREATE_EVENT_SCHEDULED_START_DATE(UtilElements.formInput(CREATE_EVENT_FORM, "estimatedStartDate_c_date")),
    /** The end date and time field for the event. */
    CREATE_EVENT_END_DATE_TIME(UtilElements.formInput(CREATE_EVENT_FORM, "estimatedCompletionDate_c_date")),
    /** The duration field for the event. */
    CREATE_EVENT_DURATION(UtilElements.formInput(CREATE_EVENT_FORM, "duration")),
    /** The location field for the event. */
    CREATE_EVENT_LOCATION(UtilElements.formInput(CREATE_EVENT_FORM, "locationDesc")),
    /** The description field for the event. */
    CREATE_EVENT_DESCRIPTION(UtilElements.formInput(CREATE_EVENT_FORM, "description", "textarea")),
    /** The availability field for the case. */
    CREATE_EVENT_AVAILABILITY(UtilElements.formInput(CREATE_EVENT_FORM, "availabilityStatusId", "select")),
    /** The scheduling conflicts field for the case. */
    CREATE_EVENT_IGNORE_SCHEDULING_CONFLICTS(UtilElements.formInput(CREATE_EVENT_FORM, "forceIfConflicts", "select")),
    /** Submit the create event form. */
    CREATE_EVENT_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_EVENT_FORM)),
    /** The created work effort ID after the create event form has been submitted. Note: here we get it from the <code>updateEventForm</code>. */
    CREATED_EVENT_WORK_EFFORT_ID("workEffortId"),
    
    // Create Task Form
    /** The create task form. */
    CREATE_TASK_FORM("//form[@name=\"createTaskForm\"]"),
    /** The name field for the task. */
    CREATE_TASK_SUBJECT(UtilElements.formInput(CREATE_TASK_FORM, "workEffortName")),
    /** The security field for the task. */
    CREATE_TASK_SECURITY(UtilElements.formInput(CREATE_TASK_FORM, "scopeEnumId", "select")),
    /** The related party id field for the task. */
    CREATE_TASK_RELATED_PARTY_ID(UtilElements.formInput(CREATE_TASK_FORM, "internalPartyId")),
    /** The opportunity id field for the task. */
    CREATE_TASK_OPPORTUNITY_ID(UtilElements.formInput(CREATE_TASK_FORM, "salesOpportunityId")),
    /** The case id field for the task. */
    CREATE_TASK_CASE_ID(UtilElements.formInput(CREATE_TASK_FORM, "custRequestId")),
    /** The purpose field for the task. */
    CREATE_TASK_PURPOSE(UtilElements.formInput(CREATE_TASK_FORM, "workEffortPurposeTypeId", "select")),
    /** The scheduled start date field for the task. */
    CREATE_TASK_START_DATE_TIME(UtilElements.formInput(CREATE_TASK_FORM, "estimatedStartDate_c_date")),
    /** The end date and time field for the task. */
    CREATE_TASK_END_DATE_TIME(UtilElements.formInput(CREATE_TASK_FORM, "estimatedCompletionDate_c_date")),
    /** The duration field for the task. */
    CREATE_TASK_DURATION(UtilElements.formInput(CREATE_TASK_FORM, "duration")),
    /** The description field for the task. */
    CREATE_TASK_DESCRIPTION(UtilElements.formInput(CREATE_TASK_FORM, "description", "textarea")),
    /** The availability field for the case. */
    CREATE_TASK_AVAILABILITY(UtilElements.formInput(CREATE_TASK_FORM, "availabilityStatusId", "select")),
    /** The scheduling conflicts field for the case. */
    CREATE_TASK_IGNORE_SCHEDULING_CONFLICTS(UtilElements.formInput(CREATE_TASK_FORM, "forceIfConflicts", "select")),
    /** Submit the create task form. */
    CREATE_TASK_SUBMIT_BUTTON(UtilElements.formSubmit(CREATE_TASK_FORM)),
    /** The created work effort ID after the create task form has been submitted. Note: here we get it from the <code>updateTaskForm</code>. */
    CREATED_TASK_WORK_EFFORT_ID("workEffortId"),
    
    // Log Task Form
    /** The log task form. */
    LOG_TASK_FORM("//form[@name=\"logTaskForm\"]"),
    /** The inbound/outbound field for the task. */
    LOG_TASK_INBOUND_OUTBOUND(UtilElements.formInput(LOG_TASK_FORM, "outbound", "select")),
    /** The internal party id field for the task. */
    LOG_TASK_INTERNAL_PARTY_ID(UtilElements.formInput(LOG_TASK_FORM, "fromPartyId")),
    /** The external party id field for the task. */
    LOG_TASK_EXTERNAL_PARTY_ID(UtilElements.formInput(LOG_TASK_FORM, "internalPartyId")),
    /** The opportunity id field for the task. */
    LOG_TASK_OPPORTUNITY_ID(UtilElements.formInput(LOG_TASK_FORM, "salesOpportunityId")),
    /** The case id field for the task. */
    LOG_TASK_CASE_ID(UtilElements.formInput(LOG_TASK_FORM, "custRequestId")),
    /** The name field for the task. */
    LOG_TASK_SUBJECT(UtilElements.formInput(LOG_TASK_FORM, "workEffortName")),
    /** The message field for the task. */
    LOG_TASK_MESSAGE(UtilElements.formInput(LOG_TASK_FORM, "content", "textarea")),
    /** The scheduled start date field for the task. */
    LOG_TASK_START_DATE(UtilElements.formInput(LOG_TASK_FORM, "actualStartDate_c_date")),
    /** The duration field for the task. */
    LOG_TASK_DURATION(UtilElements.formInput(LOG_TASK_FORM, "duration")),
    /** Submit the log task form. */
    LOG_TASK_SUBMIT_BUTTON(UtilElements.formSubmit(LOG_TASK_FORM)),
    /** The created work effort ID after the log task form has been submitted. Note: here we get it from the <code>updateTaskForm</code>. */
    LOGGED_TASK_WORK_EFFORT_ID("workEffortId"),
    
    // GWT Find accounts widget
    /** Find accounts name tab. */
    GWT_FIND_ACCOUNTS("//div[@id=\"findAccounts\"]"),
    /** Find accounts name tab. */
    GWT_FIND_ACCOUNTS_FORM(GWT_FIND_ACCOUNTS, "//form"),
    /** Find accounts name tab. */
    GWT_FIND_ACCOUNTS_LIST(GWT_FIND_ACCOUNTS, "//div[@class=\"x-grid3-body\"]"),
    /** Find accounts name tab. */
    FIND_ACCOUNTS_NAME_TAB(GWT_FIND_ACCOUNTS_FORM, "//ul/li[1]"),
    /** Find accounts id tab. */
    FIND_ACCOUNTS_ID_TAB(GWT_FIND_ACCOUNTS_FORM, "//ul/li[2]"),
    /** Find accounts phone tab. */
    FIND_ACCOUNTS_PHONE_TAB(GWT_FIND_ACCOUNTS_FORM, "//ul/li[3]"),
    /** Find accounts advanced tab. */
    FIND_ACCOUNTS_ADVANCED_TAB(GWT_FIND_ACCOUNTS_FORM, "//ul/li[4]"),
    /** Find accounts ID input. */
    FIND_ACCOUNTS_BY_ID(GWT_FIND_ACCOUNTS_FORM, "//input[@name=\"id\"]"),
    /** Find accounts submit button. */
    FIND_ACCOUNTS_SUBMIT_BUTTON(GWT_FIND_ACCOUNTS, "//button"),
    /** The ID cell in the accounts list, the index is the row number. */
    FOUND_ACCOUNT_ID(GWT_FIND_ACCOUNTS_LIST, "/div[%1$d]//td[1]"),
    /** The name cell in the accounts list, the index is the row number. */
    FOUND_ACCOUNT_NAME(GWT_FIND_ACCOUNTS_LIST, "/div[%1$d]//td[2]"),
    
    // GWT Find leads widget
    /** Find leads name tab. */
    GWT_FIND_LEADS("//div[@id=\"findLeads\"]"),
    /** Find leads name tab. */
    GWT_FIND_LEADS_FORM(GWT_FIND_LEADS, "//form"),
    /** Find leads name tab. */
    GWT_FIND_LEADS_LIST(GWT_FIND_LEADS, "//div[@class=\"x-grid3-body\"]"),
    /** Find leads name tab. */
    FIND_LEADS_NAME_TAB(GWT_FIND_LEADS_FORM, "//ul/li[1]"),
    /** Find leads id tab. */
    FIND_LEADS_ID_TAB(GWT_FIND_LEADS_FORM, "//ul/li[2]"),
    /** Find leads phone tab. */
    FIND_LEADS_PHONE_TAB(GWT_FIND_LEADS_FORM, "//ul/li[3]"),
    /** Find leads advanced tab. */
    FIND_LEADS_ADVANCED_TAB(GWT_FIND_LEADS_FORM, "//ul/li[4]"),
    /** Find leads ID input. */
    FIND_LEADS_BY_ID(GWT_FIND_LEADS_FORM, "//input[@name=\"id\"]"),
    /** Find leads submit button. */
    FIND_LEADS_SUBMIT_BUTTON(GWT_FIND_LEADS, "//button"),
    /** The ID cell in the leads list, the index is the row number. */
    FOUND_LEAD_ID(GWT_FIND_LEADS_LIST, "/div[%1$d]//td[1]"),
    /** The first name cell in the leads list, the index is the row number. */
    FOUND_LEAD_FIRST_NAME(GWT_FIND_LEADS_LIST, "/div[%1$d]//td[2]"),
    /** The last name cell in the leads list, the index is the row number. */
    FOUND_LEAD_LAST_NAME(GWT_FIND_LEADS_LIST, "/div[%1$d]//td[3]"),
    /** The company name cell in the leads list, the index is the row number. */
    FOUND_LEAD_COMPANY_NAME(GWT_FIND_LEADS_LIST, "/div[%1$d]//td[4]"),
    
    // GWT Find contacts widget
    /** Find contacts name tab. */
    GWT_FIND_CONTACTS("//div[@id=\"findContacts\"]"),
    /** Find contacts name tab. */
    GWT_FIND_CONTACTS_FORM(GWT_FIND_CONTACTS, "//form"),
    /** Find contacts name tab. */
    GWT_FIND_CONTACTS_LIST(GWT_FIND_CONTACTS, "//div[@class=\"x-grid3-body\"]"),
    /** Find contacts name tab. */
    FIND_CONTACTS_NAME_TAB(GWT_FIND_CONTACTS_FORM, "//ul/li[1]"),
    /** Find contacts id tab. */
    FIND_CONTACTS_ID_TAB(GWT_FIND_CONTACTS_FORM, "//ul/li[2]"),
    /** Find contacts phone tab. */
    FIND_CONTACTS_PHONE_TAB(GWT_FIND_CONTACTS_FORM, "//ul/li[3]"),
    /** Find contacts advanced tab. */
    FIND_CONTACTS_ADVANCED_TAB(GWT_FIND_CONTACTS_FORM, "//ul/li[4]"),
    /** Find contacts ID input. */
    FIND_CONTACTS_BY_ID(GWT_FIND_CONTACTS_FORM, "//input[@name=\"id\"]"),
    /** Find contacts submit button. */
    FIND_CONTACTS_SUBMIT_BUTTON(GWT_FIND_CONTACTS, "//button"),
    /** The ID cell in the contacts list, the index is the row number. */
    FOUND_CONTACT_ID(GWT_FIND_CONTACTS_LIST, "/div[%1$d]//td[1]"),
    /** The first name cell in the contacts list, the index is the row number. */
    FOUND_CONTACT_FIRST_NAME(GWT_FIND_CONTACTS_LIST, "/div[%1$d]//td[2]"),
    /** The last name cell in the contacts list, the index is the row number. */
    FOUND_CONTACT_LAST_NAME(GWT_FIND_CONTACTS_LIST, "/div[%1$d]//td[3]"),
    /** The company name cell in the contacts list, the index is the row number. */
    FOUND_CONTACT_COMPANY_NAME(GWT_FIND_CONTACTS_LIST, "/div[%1$d]//td[4]"),
    
 // GWT Find activities widget
    /** Find activities name tab. */
    GWT_FIND_ACTIVITIES("//div[@id=\"findActivities\"]"),
    /** Find activities name tab. */
    GWT_FIND_ACTIVITIES_FORM(GWT_FIND_ACTIVITIES, "//form"),
    /** Find activities name tab. */
    GWT_FIND_ACTIVITIES_LIST(GWT_FIND_ACTIVITIES, "//div[@class=\"x-grid3-body\"]"),
    /** Find activities name tab. */
    FIND_ACTIVITIES_NAME_TAB(GWT_FIND_ACTIVITIES_FORM, "//ul/li[1]"),
    /** Find activities id tab. */
    FIND_ACTIVITIES_ID_TAB(GWT_FIND_ACTIVITIES_FORM, "//ul/li[2]"),
    /** Find activities phone tab. */
    FIND_ACTIVITIES_PHONE_TAB(GWT_FIND_ACTIVITIES_FORM, "//ul/li[3]"),
    /** Find activities advanced tab. */
    FIND_ACTIVITIES_ADVANCED_TAB(GWT_FIND_ACTIVITIES_FORM, "//ul/li[4]"),
    /** Find activities ID input. */
    FIND_ACTIVITIES_BY_ID(GWT_FIND_ACTIVITIES_FORM, "//input[@name=\"id\"]"),
    /** Find activities submit button. */
    FIND_ACTIVITIES_SUBMIT_BUTTON(GWT_FIND_ACTIVITIES, "//button"),
    /** The ID cell in the activities list, the index is the row number. */
    FOUND_ACTIVITY_ID(GWT_FIND_ACTIVITIES_LIST, "/div[%1$d]//td[1]"),
    /** The first name cell in the activities list, the index is the row number. */
    FOUND_ACTIVITY_FIRST_NAME(GWT_FIND_ACTIVITIES_LIST, "/div[%1$d]//td[2]"),
    /** The last name cell in the activities list, the index is the row number. */
    FOUND_ACTIVITY_LAST_NAME(GWT_FIND_ACTIVITIES_LIST, "/div[%1$d]//td[3]"),
    /** The company name cell in the activities list, the index is the row number. */
    FOUND_ACTIVITY_COMPANY_NAME(GWT_FIND_ACTIVITIES_LIST, "/div[%1$d]//td[4]"),
    
    // GWT Find Opportunities widget
    /** Find opportunities name tab. */
    GWT_FIND_OPPORTUNITIES("//div[@id=\"findOpportunities\"]"),
    /** Find opportunities name tab. */
    GWT_FIND_OPPORTUNITIES_FORM(GWT_FIND_OPPORTUNITIES, "//form"),
    /** Find opportunities name tab. */
    GWT_FIND_OPPORTUNITIES_LIST(GWT_FIND_OPPORTUNITIES, "//div[@class=\"x-grid3-body\"]"),
    /** Find opportunities name tab. */
    FIND_OPPORTUNITIES_NAME_TAB(GWT_FIND_OPPORTUNITIES_FORM, "//ul/li[1]"),
    /** Find opportunities id tab. */
    FIND_OPPORTUNITIES_ID_TAB(GWT_FIND_OPPORTUNITIES_FORM, "//ul/li[2]"),
    /** Find opportunities phone tab. */
    FIND_OPPORTUNITIES_PHONE_TAB(GWT_FIND_OPPORTUNITIES_FORM, "//ul/li[3]"),
    /** Find opportunities advanced tab. */
    FIND_OPPORTUNITIES_ADVANCED_TAB(GWT_FIND_OPPORTUNITIES_FORM, "//ul/li[4]"),
    /** Find opportunities ID input. */
    FIND_OPPORTUNITIES_BY_ID(GWT_FIND_OPPORTUNITIES_FORM, "//input[@name=\"id\"]"),
    /** Find opportunities submit button. */
    FIND_OPPORTUNITIES_SUBMIT_BUTTON(GWT_FIND_OPPORTUNITIES, "//button"),
    /** The ID cell in the opportunities list, the index is the row number. */
    FOUND_SALES_OPPORTUNITY_ID(GWT_FIND_OPPORTUNITIES_LIST, "/div[%1$d]//td[1]"),
    /** The name cell in the opportunities list, the index is the row number. */
    FOUND_SALES_OPPORTUNITY_NAME(GWT_FIND_OPPORTUNITIES_LIST, "/div[%1$d]//td[2]"),
    

    // View account
    /** The view account page, the index is the account ID. */
    VIEW_ACCOUNT_PAGE("viewAccount?partyId=%1$s"),
    /** The view account main section. */
    VIEW_ACCOUNT_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view account name. */
    VIEW_ACCOUNT_NAME(VIEW_ACCOUNT_MAIN_SECTION, "//tr[1]/td[2]"),
    /** The view account local name. */
    VIEW_ACCOUNT_LOCAL_NAME(VIEW_ACCOUNT_MAIN_SECTION, "//tr[2]/td[2]"),
    /** The view account Contact information section. */
    VIEW_ACCOUNT_CONTACT_INFORMATION_SECTION("//div[@id=\"center-content-column\"]//div[@class=\"subSectionBlock\"][3]//table"),
    
    // View lead
    /** The view lead page, the index is the lead ID. */
    VIEW_LEAD_PAGE("viewLead?partyId=%1$s"),
    /** The view lead main section. */
    VIEW_LEAD_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view lead company name. */
    VIEW_LEAD_COMPANY_NAME(VIEW_LEAD_MAIN_SECTION, "//tr[1]/td[2]"),
    /** The view lead first name. */
    VIEW_LEAD_FIRST_NAME(VIEW_LEAD_MAIN_SECTION, "//tr[2]/td[2]"),
    /** The view lead last name. */
    VIEW_LEAD_LAST_NAME(VIEW_LEAD_MAIN_SECTION, "//tr[2]/td[4]"),
    /** The view lead status name. */
    VIEW_LEAD_STATUS_NAME(VIEW_LEAD_MAIN_SECTION, "//tr[3]/td[2]"),
    /** The view lead Contact information section. */
    VIEW_LEAD_CONTACT_INFORMATION_SECTION("//div[@id=\"center-content-column\"]//div[@class=\"subSectionBlock\"][2]//table"),
    /** The qualify lead link. */
    VIEW_LEAD_QUALIFY_LEAD_LINK("//div[@id=\"center-content-column\"]//div[@class=\"subMenuBar\"][1]//a[@class=\"subMenuButton\"][2]"),
    /** The convert lead link. */
    VIEW_LEAD_CONVERT_LEAD_LINK("//div[@id=\"center-content-column\"]//div[@class=\"subMenuBar\"][1]//a[@class=\"subMenuButton\"][2]"),
    /** The convert lead form. */
    CONVERT_LEAD_FORM("//form[@name=\"convertLeadForm\"]"),
    /** Submit the convert lead form. */
    CONVERT_LEAD_SUBMIT_BUTTON(UtilElements.formSubmit(CONVERT_LEAD_FORM)),
    
    // View contact
    /** The view contact page, the index is the contact ID. */
    VIEW_CONTACT_PAGE("viewContact?partyId=%1$s"),
    /** The view contact main section. */
    VIEW_CONTACT_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view contact first name. */
    VIEW_CONTACT_FIRST_NAME(VIEW_CONTACT_MAIN_SECTION, "//tr[1]/td[2]"),
    /** The view contact last name. */
    VIEW_CONTACT_LAST_NAME(VIEW_CONTACT_MAIN_SECTION, "//tr[1]/td[4]"),
    /** The view contact first name local. */
    VIEW_CONTACT_FIRST_NAME_LOCAL(VIEW_CONTACT_MAIN_SECTION, "//tr[2]/td[2]"),
    /** The view contact last name local. */
    VIEW_CONTACT_LAST_NAME_LOCAL(VIEW_CONTACT_MAIN_SECTION, "//tr[2]/td[4]"),
    /** The view contact salutation name. */
    VIEW_CONTACT_PERSONAL_TITLE(VIEW_CONTACT_MAIN_SECTION, "//tr[3]/td[2]"),
    /** The view contact salutation name. */
    VIEW_CONTACT_GENERAL_PROF_TITLE(VIEW_CONTACT_MAIN_SECTION, "//tr[4]/td[2]"),
    /** The view contact salutation name. */
    VIEW_CONTACT_DEPARTMENT(VIEW_CONTACT_MAIN_SECTION, "//tr[4]/td[4]"),
    
    
    // View Opportunity
    /** The view opportunity page, the index is the opportunity ID. */
    VIEW_OPPORTUNITY_PAGE("viewOpportunity?salesOpportunityId=%1$s"),
    /** The view opportunity main section. */
    VIEW_OPPORTUNITY_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view opportunity company name. */
    VIEW_OPPORTUNITY_OPPORTUNITY_NAME(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[1]/td[2]"),
    /** The view opportunity account name. */
    VIEW_OPPORTUNITY_ACCOUNT_NAME(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[2]/td[2]"),
    /** The view opportunity status name. */
    VIEW_OPPORTUNITY_STAGE(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[3]/td[2]"),
    /** The view opportunity type. */
    VIEW_OPPORTUNITY_TYPE(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[3]/td[4]"),
    /** The view opportunity estimated account. */
    VIEW_OPPORTUNITY_ESTIMATED_AMOUNT(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[4]/td[2]"),
    /** The view opportunity estimated probability. */
    VIEW_OPPORTUNITY_ESTIMATED_PROBABLITY(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[5]/td[2]"),
    /** The view opportunity marketing campaign. */
    VIEW_OPPORTUNITY_MARKETING_CAMPAIGN(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[6]/td[2]"),
    /** The view opportunity source. */
    VIEW_OPPORTUNITY_SOURCE(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[6]/td[4]"),
    /** The view opportunity estimated close date. */
    VIEW_OPPORTUNITY_ESTIMATED_CLOSE_DATE(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[7]/td[2]"),
    /** The view opportunity description. */
    VIEW_OPPORTUNITY_DESCRIPTION(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[8]/td[2]"),
    /** The view opportunity next step. */
    VIEW_OPPORTUNITY_NEXT_STEP(VIEW_OPPORTUNITY_MAIN_SECTION, "//tr[9]/td[2]"),
    
    // View case
    /** The view case page, the index is the lead ID. */
    VIEW_CASE_PAGE("viewCase?custRequestId=%1$s"),
    /** The view case main section. */
    VIEW_CASE_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view lead subject. */
    VIEW_CASE_SUBJECT(VIEW_CASE_MAIN_SECTION, "//tr[1]/td[2]"),
    
    // View event
    /** The view event page, the index is the work effort ID. */
    VIEW_EVENT_PAGE("viewActivity?workEffortId=%1$s"),
    /** The view event main section. */
    VIEW_EVENT_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view event activity name. */
    VIEW_EVENT_ACTIVITY_NAME(VIEW_EVENT_MAIN_SECTION, "//tr[1]/td[2]"),
    
    // View task
    /** The view task page, the index is the work effort ID. */
    VIEW_TASK_PAGE("viewActivity?workEffortId=%1$s"),
    /** The view task main section. */
    VIEW_TASK_MAIN_SECTION("//div[@id=\"center-content-column\"]//table"),
    /** The view task activity subject. */
    VIEW_TASK_ACTIVITY_SUBJECT(VIEW_TASK_MAIN_SECTION, "//tr[1]/td[2]"),
                
    // Merge Leads
    /** The merge leads form. */
    MERGE_LEADS_FORM("//form[@name=\"MergePartyForm\"]"),
    /** The party id from field for the lead. */
    MERGE_LEADS_PARTY_ID_FROM(UtilElements.formInput(MERGE_LEADS_FORM, "partyIdFrom")),
    /** The party id to field for the lead. */
    MERGE_LEADS_PARTY_ID_TO(UtilElements.formInput(MERGE_LEADS_FORM, "partyIdTo")),
    /** Submit the merge leads form. */
    MERGE_LEADS_SUBMIT_BUTTON(UtilElements.formSubmit(MERGE_LEADS_FORM)),
    /** The confirm merge leads form. */
    CONFIRM_MERGE_LEADS_FORM("//form[@name=\"ConfirmMergePartyForm\"]"),
    /** Submit the confirm merge leads form. */
    CONFIRM_MERGE_LEADS_SUBMIT_BUTTON(UtilElements.formSubmit(CONFIRM_MERGE_LEADS_FORM)),
    
    // Merge Contacts
    /** The merge contacts form. */
    MERGE_CONTACTS_FORM("//form[@name=\"MergePartyForm\"]"),
    /** The party id from field for the contact. */
    MERGE_CONTACTS_PARTY_ID_FROM(UtilElements.formInput(MERGE_CONTACTS_FORM, "partyIdFrom")),
    /** The party id to field for the contact. */
    MERGE_CONTACTS_PARTY_ID_TO(UtilElements.formInput(MERGE_CONTACTS_FORM, "partyIdTo")),
    /** Submit the merge contacts form. */
    MERGE_CONTACTS_SUBMIT_BUTTON(UtilElements.formSubmit(MERGE_CONTACTS_FORM)),
    /** The confirm merge contacts form. */
    CONFIRM_MERGE_CONTACTS_FORM("//form[@name=\"ConfirmMergePartyForm\"]"),
    /** Submit the confirm merge contacts form. */
    CONFIRM_MERGE_CONTACTS_SUBMIT_BUTTON(UtilElements.formSubmit(CONFIRM_MERGE_CONTACTS_FORM)),
    
    // Merge Accounts
    /** The merge accounts form. */
    MERGE_ACCOUNTS_FORM("//form[@name=\"MergePartyForm\"]"),
    /** The party id from field for the account. */
    MERGE_ACCOUNTS_PARTY_ID_FROM(UtilElements.formInput(MERGE_ACCOUNTS_FORM, "partyIdFrom")),
    /** The party id to field for the account. */
    MERGE_ACCOUNTS_PARTY_ID_TO(UtilElements.formInput(MERGE_ACCOUNTS_FORM, "partyIdTo")),
    /** Submit the merge accounts form. */
    MERGE_ACCOUNTS_SUBMIT_BUTTON(UtilElements.formSubmit(MERGE_ACCOUNTS_FORM)),
    /** The confirm merge accounts form. */
    CONFIRM_MERGE_ACCOUNTS_FORM("//form[@name=\"ConfirmMergePartyForm\"]"),
    /** Submit the confirm merge accounts form. */
    CONFIRM_MERGE_ACCOUNTS_SUBMIT_BUTTON(UtilElements.formSubmit(CONFIRM_MERGE_ACCOUNTS_FORM));
    
    private final String name;
    private final String locator;

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public String getLocator() {
        return locator;
    }

    private CrmsfaElements(String locator) {
        this.name = name().toLowerCase().replaceAll("_", " ");
        this.locator = locator;
    }

    private CrmsfaElements(String name, String locator) {
        this.name = name;
        this.locator = locator;
    }

    private CrmsfaElements(UiElementInterface element) {
        this(element.getName(), element.getLocator());
    }

    private CrmsfaElements(UiElementInterface element, String locator) {
        this(element.getLocator() + locator);
    }

    private CrmsfaElements(String name, UiElementInterface element, String locator) {
        this(name, element.getLocator() + locator);
    }

}
