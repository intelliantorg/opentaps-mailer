/**
 * 
 */
package org.opentaps.gwt.common.client.listviews;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.lookup.configuration.ActivityLookupConfiguration;

import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.StringFieldDef;

/**
 * @author soham.ray
 *
 */
public class ActivityListView extends EntityListView {
	
	/**
     * Default constructor.
     */
    public ActivityListView() {
        super();
        // the msg is built in the contructor of EntityListView
        // so we cannot pas the title directly
        setTitle(UtilUi.MSG.activityList());
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title label for this list view.
     */
    public ActivityListView(String title) {
        super(title);
        init();
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the default columns for Activity and extra columns.
     */
    public void init() {
        //init(entityFindUrl, null, workEffortIdLabel, nameColumns);
    	init(ActivityLookupConfiguration.URL_FIND_ACTIVITIES, "/crmsfa/control/viewActivity?workEffortId={0}", UtilUi.MSG.workEffort());
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the columns for work effort, as well as a link for a view page.
     * @param entityFindUrl the URL of the request to populate the list
     * @param entityViewUrl the URL linking to the entity view page with a placeholder for the ID. The ID column will use it to provide a link to the view page for each record. For example <code>/crmsfa/control/viewContact?partyId={0}</code>. This is optional, if <code>null</code> then no link will be provided
     * @param workEffortIdLabel the label of the ID column, which depends of the entity that is listed
     */
    private void init(String entityFindUrl, String entityViewUrl, String workEffortIdLabel) {
    	// add work effort id as the first column
        StringFieldDef idDefinition = new StringFieldDef(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
        if (entityViewUrl != null) {
            makeLinkColumn("Activity", idDefinition, entityViewUrl, true);
        } else {
            makeColumn("Activity", idDefinition);
        }

        // add remaining fields
        makeColumn(UtilUi.MSG.commonType(), new StringFieldDef(ActivityLookupConfiguration.INOUT_WORKEFFORT_TYPE_ID));
        makeColumn(UtilUi.MSG.commonName(), new StringFieldDef(ActivityLookupConfiguration.INOUT_WORKEFFORT_NAME));
        makeColumn(UtilUi.MSG.workEffortStatus(), new StringFieldDef(ActivityLookupConfiguration.INOUT_ACTIVITY_STATUS));
        makeColumn("Purpose", new StringFieldDef(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_DESC));
        
        makeColumn("Created By", new StringFieldDef(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC));
        //makeColumn("Party", new StringFieldDef(ActivityLookupConfiguration.INOUT_PARTY_FULL_NAME));
//        makeColumn("Assigned Team", new StringFieldDef(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_DESC));
        
        makeColumn("Assigned To", new StringFieldDef(ActivityLookupConfiguration.INOUT_ASSIGNED_TO_DESC));
        
        //makeColumn("Customer", new StringFieldDef(ActivityLookupConfiguration.INOUT_CUSTOMER_FULL_NAME));
        makeColumn("Internal Party", new StringFieldDef(ActivityLookupConfiguration.INOUT_INTERNAL_PARTY_FULL_NAME));
        makeColumn("Car", new StringFieldDef(ActivityLookupConfiguration.INOUT_CAR_FULL_NAME));
        makeColumn("Driver", new StringFieldDef(ActivityLookupConfiguration.INOUT_DRIVER_FULL_NAME));
        makeColumn("Lead", new StringFieldDef(ActivityLookupConfiguration.INOUT_LEAD_FULL_NAME));
        makeColumn("Contact", new StringFieldDef(ActivityLookupConfiguration.INOUT_CONTACT_FULL_NAME));
        makeColumn("Account", new StringFieldDef(ActivityLookupConfiguration.INOUT_ACCOUNT_FULL_NAME));
        
        
        makeColumn("Estimated Start Date", new StringFieldDef(ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE));
        makeColumn("Estimated Completion Date", new StringFieldDef(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE));
        makeColumn("Actual Start Date", new StringFieldDef(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE));
        makeColumn("Actual Completion Date", new StringFieldDef(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE));
        
        configure(entityFindUrl, ActivityLookupConfiguration.INOUT_WORKEFFORT_ID, SortDir.ASC);
        

        //setColumnHidden(ActivityLookupConfiguration.INOUT_CUSTOMER_FULL_NAME, true);
        //setColumnHidden(ActivityLookupConfiguration.INOUT_ASSIGNED_TO_DESC, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_INTERNAL_PARTY_FULL_NAME, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_CAR_FULL_NAME, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_DRIVER_FULL_NAME, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_LEAD_FULL_NAME, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_CONTACT_FULL_NAME, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_ACCOUNT_FULL_NAME, true);
//        setColumnHidden(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_DESC, true);
        
        setColumnHidden(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE, true);
        setColumnHidden(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE, true);
    }

    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityName(String activityName) {
        setFilter(ActivityLookupConfiguration.INOUT_WORKEFFORT_NAME, activityName);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityStatus(String activityStatus) {
    	activityStatus = activityStatus;
    	setFilter(ActivityLookupConfiguration.INOUT_CURRENT_STATUS_ID, activityStatus);
    }
    
    //*************************************Adding Search conditions*********************************************************
    
    /**
     * Filters the records of the list by work effort Id matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityId(String activityId) {
        setFilter(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID, activityId);
    }
    
    
    /**
     * Filters the records of the list by work effort Purpose Type Id matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityType(String activityType) {
        setFilter(ActivityLookupConfiguration.INOUT_WORKEFFORT_TYPE_ID, activityType);
    }
    
    /**
     * Filters the records of the list by work effort Purpose Type Id matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityPurpose(String activityPurposeId) {
        setFilter(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID, activityPurposeId);
    }
    
    /**
     * Filters the records of the list by Assigned To matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityCreatedBy(String createdBy) {
        setFilter(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN, createdBy);
    }
    
    public void filterByActivityAssignedTo(String activityAssignedTo) {
        setFilter(ActivityLookupConfiguration.INOUT_ASSIGNED_TO, activityAssignedTo);
    }
    
    public void filterByActivityAssignedTeam(String activityAssignedTeam) {
        setFilter(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID, activityAssignedTeam);
    }
    
    /**
     * Filters the records of the list by Contact matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityLead(String leadPartyId) {
    	if(leadPartyId != null){
    		setFilter(ActivityLookupConfiguration.INOUT_LEAD_PARTY_ID, leadPartyId);
    	}
    }
    
    
    /**
     * Filters the records of the list by Account matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityAccount(String accountPartyId) {
    	//UtilUi.debug("accountPartyId "+accountPartyId);
    	if(accountPartyId != null){
    		setFilter(ActivityLookupConfiguration.INOUT_ACCOUNT_PARTY_ID, accountPartyId);
    	}
    }
    
    /**
     * Filters the records of the list by Contact matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByActivityContact(String advContactPartyId) {
    	if(advContactPartyId != null){
    		setFilter(ActivityLookupConfiguration.INOUT_CONTACT_PARTY_ID, advContactPartyId);
    	}
    }
    
    
  //*********************************** Date Fields*****************************************  
    public void filterByActivityEstStartDateFrom(String estStartFrom) {
    	if(estStartFrom != null){
    		setFilter(ActivityLookupConfiguration.IN_ESTIMATED_START_DATE_FROM, estStartFrom);
    	}
    }
    
    public void filterByActivityEstStartDateTo(String estStartTo) {
    	if(estStartTo != null){
    		setFilter(ActivityLookupConfiguration.IN_ESTIMATED_START_DATE_TO, estStartTo);
    	}
    }
    
    public void filterByActivityEstCloseDateFrom(String estCloseFrom) {
    	if(estCloseFrom != null){
    		setFilter(ActivityLookupConfiguration.IN_ESTIMATED_COMPLETION_DATE_FROM, estCloseFrom);
    	}
    }
    
    public void filterByActivityEstCloseDateTo(String estCloseTo) {
    	if(estCloseTo != null){
    		setFilter(ActivityLookupConfiguration.IN_ESTIMATED_COMPLETION_DATE_TO, estCloseTo);
    	}
    }
    
  //**************************Actual Dates******************************************************  
    public void filterByActivityActStartDateFrom(String actStartFrom) {
    	if(actStartFrom != null){
    		setFilter(ActivityLookupConfiguration.IN_ACTUAL_START_DATE_FROM, actStartFrom);
    	}
    }
    
    public void filterByActivityActStartDateTo(String actStartTo) {
    	if(actStartTo != null){
    		setFilter(ActivityLookupConfiguration.IN_ACTUAL_START_DATE_TO, actStartTo);
    	}
    }
    
    public void filterByActivityActCloseDateFrom(String actCloseFrom) {
    	if(actCloseFrom != null){
    		setFilter(ActivityLookupConfiguration.IN_ACTUAL_COMPLETION_DATE_FROM, actCloseFrom);
    	}
    }
    
    public void filterByActivityActCloseDateTo(String actCloseTo) {
    	if(actCloseTo != null){
    		setFilter(ActivityLookupConfiguration.IN_ACTUAL_COMPLETION_DATE_TO, actCloseTo);
    	}
    }
    
}
