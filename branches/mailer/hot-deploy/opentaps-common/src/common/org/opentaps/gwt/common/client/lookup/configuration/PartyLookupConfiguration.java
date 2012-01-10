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

package org.opentaps.gwt.common.client.lookup.configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Defines the interface between the server and client for the PartyLookupService
 * Technically not a java interface, but it defines all the constants needed on both sides
 *  which makes the code more robust.
 */
public abstract class PartyLookupConfiguration {

    private PartyLookupConfiguration() { }

    public static final String URL_FIND_CONTACTS = "gwtFindContacts";
    public static final String URL_FIND_ACCOUNTS = "gwtFindAccounts";
    public static final String URL_FIND_LEADS = "gwtFindLeads";
    public static final String URL_FIND_PARTNERS = "gwtFindPartners";
    public static final String URL_FIND_SUPPLIERS = "gwtFindSuppliers";
    public static final String URL_SUGGEST_LEADS = "gwtSuggestLeads";
    public static final String URL_SUGGEST_LEAD_SAVE_SEARCH = "gwtSuggestLeadSaveSearch";
    public static final String URL_SUGGEST_CONTACT_SAVE_SEARCH = "gwtSuggestContactSaveSearch";
    public static final String URL_SUGGEST_ACCOUNT_SAVE_SEARCH = "gwtSuggestAccountSaveSearch";
    
    
    public static final String URL_SUGGEST_LEADS_FOR_OPPORTUNITY = "gwtSuggestLeadsForOpp";
    public static final String URL_SUGGEST_CONTACTS = "gwtSuggestContacts";
    public static final String URL_SUGGEST_ACCOUNTS = "gwtSuggestAccounts";
    public static final String URL_SUGGEST_ACCOUNT_TYPES = "gwtSuggestAccountTypes";
    public static final String URL_SUGGEST_ACCOUNT_OWNERSHIPS = "gwtSuggestAccountOwnerships";
    public static final String URL_SUGGEST_INDUSTRIES = "gwtSuggestIndustries";
    public static final String URL_SUGGEST_MODEL_OF_INTERESTS = "gwtSuggestModelOfInterests";
    public static final String URL_SUGGEST_SALUTATIONS = "gwtSuggestSalutations";
    public static final String URL_SUGGEST_LEAD_STATUS = "gwtSuggestLeadStatus";
    public static final String URL_LEAD_SAVE_SEARCH = "/crmsfa/control/gwtLeadSaveSearch";    
    public static final String URL_CONTACT_SAVE_SEARCH = "/crmsfa/control/gwtContactSaveSearch";    
    public static final String URL_ACCOUNT_SAVE_SEARCH = "/crmsfa/control/gwtAccountSaveSearch";
    
    public static final String URL_RETRIVE_SAVE_SEARCH = "/crmsfa/control/gwtRetriveSaveSearch"; 
    
    public static final String INOUT_SAVEKEY = "saveKey";
    public static final String INOUT_VALUE = "value";
    
    public static final String URL_SUGGEST_SAVE_SEARCH = "";
    
    public static final String IN_RESPONSIBILTY = "MyOrTeamResponsibility";
    public static final String MY_VALUES = "MY_VALUES";
    public static final String TEAM_VALUES = "TEAM_VALUES";
    
    public static final String INOUT_NAME = "name";
    public static final String INOUT_PARTY_ID = "partyId";
    public static final String INOUT_COMPANY_NAME = "companyName";
    public static final String INOUT_GROUP_NAME = "groupName";
    public static final String INOUT_FIRST_NAME = "firstName";
    public static final String INOUT_LAST_NAME = "lastName";
    public static final String INOUT_ADDRESS = "primaryAddress1";
    public static final String INOUT_COUNTRY = "primaryCountryAbbreviation";
    public static final String INOUT_STATE = "primaryStateProvinceAbbreviation";
    public static final String INOUT_STATE1 = "stateProvinceGeoId";
    public static final String INOUT_CITY = "primaryCity";
    public static final String INOUT_POSTAL_CODE = "primaryPostalCode";
    public static final String INOUT_PHONE_COUNTRY_CODE = "primaryCountryCode";
    public static final String INOUT_PHONE_AREA_CODE = "primaryAreaCode";
    public static final String INOUT_PHONE_NUMBER = "primaryContactNumber";
    public static final String INOUT_FORMATED_PHONE_NUMBER = "formatedPrimaryPhone";
    public static final String IN_CLASSIFICATION = "partyClassificationGroupId";
    public static final String OUT_TO_NAME = "primaryToName";
    public static final String OUT_ADDRESS_ID = "primaryPostalAddressId";
    public static final String OUT_ATTENTION_NAME = "primaryAttnName";
    public static final String OUT_ADDRESS_2 = "primaryAddress2";
    public static final String OUT_POSTAL_CODE_EXT = "primaryPostalCodeExt";
    public static final String OUT_PHONE_ID = "primaryTelecomNumberId";
    public static final String INOUT_EMAIL = "primaryEmail";
    public static final String INOUT_EMAIL_ID = "primaryEmailId";
    public static final String INOUT_STATUS = "statusId";
    public static final String INOUT_STATUS_DESC = "description";
    public static final String OUT_LEAD_STATUS = "leadStatus";
    public static final String INOUT_DATA_SOURCE = "dataSourceId";
    public static final String OUT_LEAD_DATA_SOURCE = "leadDataSource";
    
   //************************************************************************************* 
    public static final String INOUT_ASSIGNED_TO = "partyIdTo";
    public static final String INOUT_ASSIGNED_TO_DESC = "partyIdToDesc";
    
    public static final String INOUT_CREATED_BY_USER_LOGIN = "createdByUserLogin";
    public static final String INOUT_CREATED_BY_USER_LOGIN_DESC = "createdByUserLoginFullName";
    
    public static final String INOUT_ASSIGNED_TEAM_PARTY_ID = "teamPartyIdTo";
    public static final String INOUT_ASSIGNED_TEAM = "groupName";
    public static final String INOUT_ASSIGNED_TEAM_DESC = "assignedTeamDesc";
  //*************************************************************************************
    
    public static final String INOUT_PARENT_ACCOUNT = "parentPartyId";
    public static final String INOUT_INITIAL_ACCOUNT_FOR_CONTACT = "accountPartyId";
    public static final String OUT_NUMBER_OF_ACCOUNTS_FOR_CONTACT = "numAccForContact";
    
    public static final String INOUT_ACCOUNT_TYPE = "accountType";
    public static final String INOUT_ACCOUNT_OWNERSHIP = "ownershipEnumId";
    public static final String INOUT_ACCOUNT_INDUSTRY = "industryEnumId";    
    //public static final String INOUT_ACCOUNT_WEBSITE = "officeSiteName";
    //public static final String INOUT_ACCOUNT_ANNUAL_REVENEU = "annualRevenue";
    //public static final String INOUT_ACCOUstateProvinceGeoIdNT_SIC_CODE = "sicCode";
    public static final String INOUT_ACCOUNT_RATING = "rating";
    //public static final String INOUT_ACCOUNT_NUMBER_OF_EMP = "numberEmployees";
    //public static final String INOUT_ACCOUNT_TICKER_SYMBOL = "tickerSymbol";
    
    public static final String INOUT_ENUMERATION_ID = "enumId";
    public static final String OUT_ENUMERATION_DESCRIPTION = "description";
    public static final String INOUT_ENUM_TYPE_ID = "enumTypeId";
    
    public static final String OUT_ACCOUNT_INDUSTRY_DESC = "industryDesc";
    public static final String OUT_ACCOUNT_OWNERSHIP_DESC = "accOwnershipDesc";
    public static final String OUT_ACCOUNT_TYPE_DESC = "accTypeDesc";
    

    public static final String INOUT_STATE_NAME = "primaryStateProvinceName";
    public static final String INOUT_COUNTRY_NAME = "geoName"; 
    
    public static final List<String> LIST_OUT_FIELDS = Arrays.asList(
        INOUT_PARTY_ID,
        INOUT_COMPANY_NAME,
        INOUT_GROUP_NAME,
        INOUT_FIRST_NAME,
        INOUT_LAST_NAME,
        INOUT_ADDRESS,
        INOUT_COUNTRY,
       INOUT_STATE,
        //INOUT_STATE1,
        INOUT_CITY,
        INOUT_POSTAL_CODE,
        INOUT_PHONE_COUNTRY_CODE,
        INOUT_PHONE_AREA_CODE,
        INOUT_PHONE_NUMBER,
        INOUT_EMAIL,
//        INOUT_EMAIL_ID,
        INOUT_STATUS,
//        INOUT_DATA_SOURCE,
        OUT_TO_NAME,
        OUT_ATTENTION_NAME,
        OUT_ADDRESS_2,
        OUT_POSTAL_CODE_EXT,        
        OUT_PHONE_ID,
        OUT_ADDRESS_ID,
        INOUT_PARENT_ACCOUNT,
        //INOUT_INITIAL_ACCOUNT,      
        
        INOUT_ACCOUNT_TYPE,
        INOUT_ACCOUNT_OWNERSHIP,
        INOUT_ACCOUNT_INDUSTRY,
//        INOUT_ACCOUNT_WEBSITE,
//        INOUT_ACCOUNT_ANNUAL_REVENEU,
//        INOUT_ACCOUNT_SIC_CODE,
        INOUT_ACCOUNT_RATING,
        INOUT_CREATED_BY_USER_LOGIN,
        INOUT_ASSIGNED_TO
        //INOUT_ASSIGNED_TEAM_PARTY_ID,
//        INOUT_ACCOUNT_NUMBER_OF_EMP,
//        INOUT_ACCOUNT_TICKER_SYMBOL
    );	

}
