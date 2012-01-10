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
 * Defines the interface between the server and client for the ActivityLookupService
 * Technically not a java interface, but it defines all the constants needed on both sides
 *  which makes the code more robust.
 */
public abstract class ActivityLookupConfiguration {

    private ActivityLookupConfiguration() { }

    public static final String URL_FIND_ACTIVITIES = "gwtFindActivities";

    public static final String IN_RESPONSIBILTY = "MyOrTeamResponsibility";
    public static final String MY_VALUES = "MY_VALUES";
    public static final String TEAM_VALUES = "TEAM_VALUES";
    
    public static final String URL_SUGGEST_ACTIVITY_PURPOSE_TYPE = "gwtSuggestActivityPurposeType";
    public static final String URL_SUGGEST_ACTIVITY_STATUS = "gwtSuggestActivityStatus";
    
    public static final String URL_SUGGEST_ACTIVITY_SAVE_SEARCH = "gwtSuggestActivitySaveSearch";
    public static final String URL_ACTIVITY_SAVE_SEARCH = "/crmsfa/control/gwtActivitySaveSearch";
    public static final String INOUT_NAME = "name";

    public static final String INOUT_WORKEFFORT_ID = "workEffortId";
    public static final String INOUT_WORKEFFORT_NAME = "workEffortName";
    public static final String INOUT_WORKEFFORT_TYPE_ID = "workEffortTypeId";
    public static final String INOUT_WORKEFFORT_PURPOSE_TYPE_ID = "workEffortPurposeTypeId";
    public static final String INOUT_WORKEFFORT_PURPOSE_TYPE_DESC = "description";
    public static final String INOUT_PARTY_ID = "partyId";
    public static final String INOUT_PARTY_FULL_NAME = "fullName";
    
    public static final String INOUT_CAR_FULL_NAME = "carName";
    public static final String INOUT_INTERNAL_PARTY_FULL_NAME = "partyFullName";
    public static final String INOUT_DRIVER_FULL_NAME = "driverName";
    public static final String INOUT_CUSTOMER_FULL_NAME = "customerName";
    public static final String INOUT_LEAD_FULL_NAME = "leadName";
    public static final String INOUT_CONTACT_FULL_NAME = "contactName";
    public static final String INOUT_ACCOUNT_FULL_NAME = "accountName";
    
    
    //public static final String IN_CONTACT_NAME = "contactName";
    public static  String INOUT_CURRENT_STATUS_ID = "currentStatusId";
    public static final String INOUT_ACTIVITY_STATUS = "activityStatus";
    
    public static final String INOUT_STATUS_ID = "statusId";
    public static final String INOUT_CURRENT_STATUS_DESC = "description";
    
    public static final String INOUT_FROM_DATE = "fromDate";
    public static final String INOUT_THRU_DATE = "thruDate";
    //public static final String INOUT_ASSIGNED_TO = "assignedTo";
    
    public static final String INOUT_LEAD_PARTY_ID = "leadPartyId";
    public static final String INOUT_ACCOUNT_PARTY_ID = "accountPartyId";
    public static final String INOUT_CONTACT_PARTY_ID = "contactPartyId";
    
    public static final String INOUT_ESTIMATED_START_DATE = "estimatedStartDate";
    public static final String INOUT_ESTIMATED_COMPLETION_DATE = "estimatedCompletionDate";
    public static final String INOUT_ACTUAL_START_DATE = "actualStartDate";
    public static final String INOUT_ACTUAL_COMPLETION_DATE = "actualCompletionDate";
   
   //*************************************************************************************** 
    public static final String INOUT_CREATED_BY_USER_LOGIN = "createdByUserLogin";
    public static final String INOUT_CREATED_BY_USER_LOGIN_DESC = "createdByUserLoginFullName";
    
    public static final String INOUT_ASSIGNED_TO = "partyIdTo";
    public static final String INOUT_ASSIGNED_TO_DESC = "partyIdToDesc";
    
    public static final String INOUT_ASSIGNED_TEAM_PARTY_ID = "teamPartyIdTo";
    public static final String INOUT_ASSIGNED_TEAM = "groupName";
    public static final String INOUT_ASSIGNED_TEAM_DESC = "assignedTeamDesc";
   //***************************************************************************************  
    
    public static final String IN_ESTIMATED_START_DATE_FROM = "estimatedStartDateFrom";
    public static final String IN_ESTIMATED_START_DATE_TO = "estimatedStartDateTo";
    public static final String IN_ESTIMATED_COMPLETION_DATE_FROM = "estimatedCloseDateFrom";
    public static final String IN_ESTIMATED_COMPLETION_DATE_TO = "estimatedCloseDateTo";
    
    public static final String IN_ACTUAL_START_DATE_FROM = "actualStartDateFrom";
    public static final String IN_ACTUAL_START_DATE_TO = "actualStartDateTo";
    public static final String IN_ACTUAL_COMPLETION_DATE_FROM = "actualCloseDateFrom";
    public static final String IN_ACTUAL_COMPLETION_DATE_TO = "actualCloseDateTo";
    
    
    public static final List<String> LIST_OUT_FIELDS = Arrays.asList(
    	INOUT_WORKEFFORT_ID,
    	INOUT_WORKEFFORT_NAME,
    	INOUT_WORKEFFORT_TYPE_ID,
    	INOUT_WORKEFFORT_PURPOSE_TYPE_ID,
    	//INOUT_ASSIGNED_TO,
    	INOUT_CREATED_BY_USER_LOGIN,
    	//INOUT_ASSIGNED_TEAM_PARTY_ID,
    	//INOUT_PARTY_ID,
    	//IN_CONTACT_NAME,
    	INOUT_CURRENT_STATUS_ID,
    	INOUT_ESTIMATED_START_DATE,
    	INOUT_ESTIMATED_COMPLETION_DATE,
    	INOUT_ACTUAL_START_DATE,
    	INOUT_ACTUAL_COMPLETION_DATE
    	//INOUT_FROM_DATE,
    	//INOUT_THRU_DATE
    	//INOUT_ASSIGNED_TO
    );
}
