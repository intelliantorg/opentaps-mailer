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
public abstract class CaseLookupConfiguration {

    private CaseLookupConfiguration() { }

    public static final String URL_FIND_CASES = "gwtFindCases";
    public static final String URL_SUGGEST_CASE_TYPES = "gwtSuggestCaseTypes";
    public static final String URL_SUGGEST_CASE_STATUS = "gwtSuggestCaseStatus";
    public static final String URL_SUGGEST_CASE_PRIORITY = "gwtSuggestCasePriority";
    
    public static final String URL_SUGGEST_CASE_SAVE_SEARCH = "gwtSuggestCaseSaveSearch";
    public static final String URL_CASE_SAVE_SEARCH = "/crmsfa/control/gwtCaseSaveSearch";
    public static final String INOUT_NAME = "name";

    public static final String IN_RESPONSIBILTY = "MyOrTeamResponsibility";
    public static final String MY_VALUES = "MY_VALUES";
    public static final String TEAM_VALUES = "TEAM_VALUES";

    public static final String INOUT_CUST_REQT_ID = "custRequestId";
    public static final String INOUT_CUST_REQT_NAME = "custRequestName";
    public static final String INOUT_CASE_STATUS_ID = "statusId";
    public static final String INOUT_CASE_ACCOUNT = "accountPartyId";
    public static final String INOUT_CASE_CONTACT = "fromPartyId";
    public static final String INOUT_CASE_PARTY_NAME = "partyName";
    public static final String INOUT_CREATED_BY_USER_LOGIN = "lastModifiedByUserLogin";
    public static final String INOUT_CASE_PRIORITY = "priority";
    public static final String INOUT_CASE_PRIORITY_DESC = "priorityDesc";
    public static final String INOUT_CASE_TYPE = "custRequestTypeId";
    
    public static final String INOUT_CASE_STATUS_DESC = "caseStatusDesc";
    public static final String INOUT_CASE_TYPE_DESC = "caseTypeDesc";
    
    public static final String INOUT_DESCRIPTION = "description";
    
    public static final String INOUT_CASE_PRIORITY_ID = "enumCode";
    
    public static final String INOUT_CASE_DATE = "createdDate";
    
    public static final String INOUT_CASE_DATE_FROM = "createdDateFrom";
    public static final String INOUT_CASE_DATE_TO = "createdDateTo";
    
    public static final List<String> LIST_OUT_FIELDS = Arrays.asList(
    	INOUT_CUST_REQT_ID,
    	INOUT_CUST_REQT_NAME,
    	INOUT_CASE_STATUS_ID,
    	INOUT_CASE_CONTACT,
    	INOUT_CASE_PRIORITY,
    	INOUT_CASE_TYPE,
    	INOUT_CREATED_BY_USER_LOGIN,
    	INOUT_CASE_DATE
    );
}
