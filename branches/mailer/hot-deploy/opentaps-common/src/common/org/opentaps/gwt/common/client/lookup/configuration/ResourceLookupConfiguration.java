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


/**
 * Defines the interface between the server and client for the ActivityLookupService
 * Technically not a java interface, but it defines all the constants needed on both sides
 *  which makes the code more robust.
 */
public abstract class ResourceLookupConfiguration {

    private ResourceLookupConfiguration() { }

    public static final String URL_FIND_RESOURCES = "gwtFindResources";
    public static final String URL_SUGGEST_RES_CARS = "gwtSuggestResCars";
    public static final String URL_SUGGEST_RES_DRIVERS = "gwtSuggestResDrivers";

    public static final String IN_RESPONSIBILTY = "MyOrTeamResponsibility";
    public static final String MY_VALUES = "MY_VALUES";
    public static final String TEAM_VALUES = "TEAM_VALUES";

    public static final String INOUT_PARTY_ID = "partyId";
    //public static final String INOUT_PARTY_NAME = "partyName";
    public static final String INOUT_ROLE_TYPE_ID = "releTypeId";
    public static final String INOUT_WORK_EFFORT_ID = "workEffortId";
    public static final String INOUT_FROM_DATE = "fromDate";
    public static final String INOUT_THRU_DATE = "thruDate";
    public static final String INOUT_STATUS_ID = "statusId";
    public static final String INOUT_STATUS_DATE_TIME = "statusDateTime";
    public static final String INOUT_AVAILABILITY_STATUS_ID = "availabilityStatusId";
    
    public static final String INOUT_ESTIMATED_START_DATE = "estimatedStartDate";
    
    public static final String INOUT_ESTIMATED_START_DATE_FROM = "estimatedStartDateFrom";
    public static final String INOUT_ESTIMATED_START_TIME_FROM = "estimatedStartTimeFrom";
    
    public static final String INOUT_ESTIMATED_CLOSE_DATE_FROM = "estimatedCloseDateFrom";
    
    
    
    public static final String INOUT_DURATION = "duration";
    	
//    public static final List<String> LIST_OUT_FIELDS = Arrays.asList(
//    	INOUT_CUST_REQT_ID,
//    	INOUT_CUST_REQT_NAME,
//    	INOUT_CASE_STATUS_ID,
//    	INOUT_CASE_CONTACT,
//    	INOUT_CASE_PRIORITY,
//    	INOUT_CASE_TYPE
//    );
}
