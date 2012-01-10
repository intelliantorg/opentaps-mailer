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
 * Defines the interface between the server and client for the OpportunityLookupService
 * Technically not a java interface, but it defines all the constants needed on both sides
 *  which makes the code more robust.
 */

public abstract class OpportunityLookupConfiguration {

    private OpportunityLookupConfiguration() { }

    public static final String URL_FIND_OPPORTUNITIES = "gwtFindOpportunities";
    public static final String URL_SUGGEST_OPPORTUNITY_STAGES = "gwtSuggestOpportunityStages";
    public static final String URL_SUGGEST_OPPORTUNITY_TYPES = "gwtSuggestOpportunityTypes";
    public static final String URL_SUGGEST_EXCHANGE_OLD_CAR = "gwtSuggestExchangeOldCar";
    
    public static final String URL_SUGGEST_OPPORTUNITY_SAVE_SEARCH = "gwtSuggestOpportunitySaveSearch";
    public static final String URL_OPPORTUNITY_SAVE_SEARCH = "/crmsfa/control/gwtOpportunitySaveSearch";
    public static final String INOUT_NAME = "name";
    
    public static final String URL_RETRIVE_SAVE_SEARCH = "/crmsfa/control/gwtRetriveSaveSearch";
    
    public static final String IN_RESPONSIBILTY = "MyOrTeamResponsibility";
    public static final String MY_VALUES = "MY_VALUES";
    public static final String TEAM_VALUES = "TEAM_VALUES";

    public static final String INOUT_OPPORTUNITY_ID = "salesOpportunityId";
    public static final String INOUT_DATA_SOURCE = "dataSourceId";
    public static final String INOUT_DATA_SOURCE_DESC = "dataSourceDescription";
    public static final String OUT_LEAD_DATA_SOURCE = "leadDataSource";
    public static final String INOUT_OPPORTUNITY_NAME = "opportunityName";
    public static final String INOUT_OPPORTUNITY_NUMBER = "opportunityNumber";
    public static final String INOUT_OPPORTUNITY_MODEL_OF_INTEREST = "modelOfInterest";
    public static final String INOUT_OPPORTUNITY_STAGE = "opportunityStageId";
    public static final String INOUT_OPPORTUNITY_SEQUENCE_NUM = "sequenceNum";
    public static final String INOUT_OPPORTUNITY_TYPE = "typeEnumId";
    public static final String INOUT_OPPORTUNITY_AMOUNT = "estimatedAmount";
    public static final String INOUT_OPPORTUNITY_PROBABILITY = "estimatedProbability";
    public static final String INOUT_OPPORTUNITY_DESCRIPTION = "description";
    public static final String INOUT_OPPORTUNITY_NEXT_STEP = "nextStep";
   
    
    public static final String INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE = "estimatedCloseDate";
    public static final String IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_FROM = "estimatedCloseDateFrom";
    public static final String IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_TO = "estimatedCloseDateTo";
    
    public static final String IN_OPPORTUNITY_CREATED_DATE_FROM = "createdDateFrom";
    public static final String IN_OPPORTUNITY_CREATED_DATE_TO = "createdDateTo";
    public static final String OUT_OPPORTUNITY_CREATED_DATE = "createdDate";
    public static final String OUT_OPPORTUNITY_CREATED_STAMP = "createdStamp";
    public static final String INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR = "exchangeOldCar";
    
    public static final String INOUT_OPPORTUNITY_ACCOUNT_NAME = "accountName";
    public static final String INOUT_OPPORTUNITY_ACCOUNT = "accountPartyId";
    public static final String INOUT_OPPORTUNITY_ACCOUNT_TYPE = "ACCOUNT";
    
    public static final String INOUT_OPPORTUNITY_LEAD_NAME = "leadName";
    public static final String INOUT_OPPORTUNITY_LEAD = "leadPartyId";
    public static final String INOUT_OPPORTUNITY_LEAD_TYPE = "PROSPECT";
    
    public static final String INOUT_OPPORTUNITY_CONTACT_NAME = "contactName";
    public static final String INOUT_OPPORTUNITY_CONTACT = "contactPartyId";
    public static final String INOUT_OPPORTUNITY_CONTACT_TYPE = "CONTACT";
    
    public static final String INOUT_PARTY_ID = "partyId";
    public static final String INOUT_PARTY_NAME_DESC = "partyName";
      
    public static final String INOUT_ENUMERATION_ID = "enumId";
    public static final String OUT_ENUMERATION_DESCRIPTION = "description";

    public static final String OUT_OPPORTUNITY_STAGE_DESCRIPTION = "opportunityStageDescription";
    
    public static final String OUT_OPPORTUNITY_MODEL_OF_INTEREST_DESC = "modelOfInterestDesc";
    public static final String OUT_OPPORTUNITY_TYPE_DESC = "typeDesc";
    
   //********************************************************************************************
    public static final String INOUT_CREATED_BY_USER_LOGIN = "createdByUserLogin";
    public static final String INOUT_CREATED_BY_USER_LOGIN_DESC = "createdByUserLoginFullName";
    
    public static final String INOUT_ASSIGNED_TO = "partyIdTo";
    public static final String INOUT_ASSIGNED_TO_DESC = "partyIdToDesc";
    
    public static final String INOUT_ASSIGNED_TEAM_PARTY_ID = "teamPartyIdTo";
    public static final String INOUT_ASSIGNED_TEAM = "groupName";
    public static final String INOUT_ASSIGNED_TEAM_DESC = "assignedTeamDesc";
    public static final String IN_ASSIGNED_TEAM_ID = "teamId";
   //********************************************************************************************
    
    public static final List<String> LIST_OUT_FIELDS = Arrays.asList(
    		INOUT_OPPORTUNITY_ID,
    		INOUT_OPPORTUNITY_NUMBER,
    	    INOUT_OPPORTUNITY_MODEL_OF_INTEREST,
    	    INOUT_OPPORTUNITY_DESCRIPTION,
    	    INOUT_OPPORTUNITY_STAGE,
    	    INOUT_OPPORTUNITY_PROBABILITY,
    	    INOUT_OPPORTUNITY_NEXT_STEP,
    	    INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE,
    	    INOUT_CREATED_BY_USER_LOGIN,
    	    OUT_OPPORTUNITY_CREATED_STAMP,
    	    INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR
    );
}
