/**
 * 
 */
package org.opentaps.gwt.common.client.listviews;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;

import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.StringFieldDef;

/**
 * @author soham.ray
 *
 */
public class OpportunityListView extends EntityListView {
	
	/**
     * Default constructor.
     */
    public OpportunityListView() {
        super();
        // the msg is built in the contructor of EntityListView
        // so we cannot pas the title directly
        setTitle("Pink Card List");
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title label for this list view.
     */
    public OpportunityListView(String title) {
        super(title);
        init();
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the default columns for Activity and extra columns.
     */
    public void init() {
        //init(entityFindUrl, null, workEffortIdLabel, nameColumns);
    	init(OpportunityLookupConfiguration.URL_FIND_OPPORTUNITIES, "/crmsfa/control/viewOpportunity?salesOpportunityId={0}", UtilUi.MSG.crmPinkCard());
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the columns for work effort, as well as a link for a view page.
     * @param entityFindUrl the URL of the request to populate the list
     * @param entityViewUrl the URL linking to the entity view page with a placeholder for the ID. The ID column will use it to provide a link to the view page for each record. For example <code>/crmsfa/control/viewContact?partyId={0}</code>. This is optional, if <code>null</code> then no link will be provided
     * @param workEffortIdLabel the label of the ID column, which depends of the entity that is listed
     */
    private void init(String entityFindUrl, String entityViewUrl, String salesOpportunityIdLabel) {
    	// add work effort id as the first column
        StringFieldDef idDefinition = new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
        if (entityViewUrl != null) {
            makeLinkColumn(salesOpportunityIdLabel, idDefinition, entityViewUrl, true);
        } else {
            makeColumn(salesOpportunityIdLabel, idDefinition);
        }
        
        // add remaining fields
        makeColumn(UtilUi.MSG.crmPinkCardNumber(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NUMBER));
        
        makeColumn("Created By", new StringFieldDef(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC));
        makeColumn(UtilUi.MSG.crmPinkCardCreatedDate(), new StringFieldDef(OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_DATE));
        makeColumn(UtilUi.MSG.crmPinkCardExchangeOldCar(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR));
        makeColumn("Assigned Team", new StringFieldDef(OpportunityLookupConfiguration.INOUT_ASSIGNED_TEAM_DESC));
        makeColumn("Assigned To", new StringFieldDef(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO_DESC));
        makeColumn("Model of Interest", new StringFieldDef(OpportunityLookupConfiguration.OUT_OPPORTUNITY_MODEL_OF_INTEREST_DESC));
        makeColumn(UtilUi.MSG.crmStage(), new StringFieldDef(OpportunityLookupConfiguration.OUT_OPPORTUNITY_STAGE_DESCRIPTION));
        //makeColumn(UtilUi.MSG.crmEstimatedAmount(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_AMOUNT));
        makeColumn(UtilUi.MSG.crmEstimatedProbability(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_PROBABILITY));
        //makeColumn("Lead Source", new StringFieldDef(OpportunityLookupConfiguration.OUT_LEAD_DATA_SOURCE));
        makeColumn(UtilUi.MSG.crmEstimatedCloseDate(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE));
        //makeColumn(UtilUi.MSG.commonType(), new StringFieldDef(OpportunityLookupConfiguration.OUT_OPPORTUNITY_TYPE_DESC));
        makeColumn(UtilUi.MSG.crmNextStep(), new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NEXT_STEP));
        makeColumn("Description", new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_DESCRIPTION));
        makeColumn("Lead", new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_TYPE));
        makeColumn("Contact", new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_TYPE));
        makeColumn("Account", new StringFieldDef(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_TYPE));
        makeColumn("Source", new StringFieldDef(OpportunityLookupConfiguration.INOUT_DATA_SOURCE_DESC)); 
        
        configure(entityFindUrl, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID, SortDir.ASC);
        setColumnHidden(OpportunityLookupConfiguration.INOUT_ASSIGNED_TEAM_DESC, true);
    }

    /**
     * Filters the records of the list by showing only those belonging to the user making the request.
     * @param viewPref a <code>Boolean</code> value
     */
    public void filterMyOrTeamOpportunities(String viewPref) {
        setFilter(PartyLookupConfiguration.IN_RESPONSIBILTY, viewPref);
    }
    
    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOpportunityId(String opportunityId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID, opportunityId);
    }
    
    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOpportunityName(String opportunityName) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NAME, opportunityName);
    }
    
    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOpportunityNumber(String opportunityNumber) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NUMBER, opportunityNumber);
    }

    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOpportunityNextStep(String opportunityNextStep) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NEXT_STEP, opportunityNextStep);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOpportunityStage(String opportunityStageId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE, opportunityStageId);
    }

    public void filterByType(String typeEnumId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_TYPE, typeEnumId);
    }

    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByAmount(String estimatedAmount) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_AMOUNT, estimatedAmount);
    }
    
    public void filterBySequenceNum(String sequenceNum) {
    	setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_SEQUENCE_NUM, sequenceNum);
    }
    
    public void filterByProbability(String estimatedProbability) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_PROBABILITY, estimatedProbability);
    }

    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByNextStep(String nextStep) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NEXT_STEP, nextStep);
    }
    
    public void filterByEstimatedCloseDateFrom(String estimatedCloseDateFrom) {
        setFilter(OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_FROM, estimatedCloseDateFrom);
    }

    public void filterByEstimatedCloseDateTo(String estimatedCloseDateTo) {
        setFilter(OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_TO, estimatedCloseDateTo);
    }
    
    public void filterByCreatedDateFrom(String createdDateFrom) {
        setFilter(OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_FROM, createdDateFrom);
    }

    public void filterByCreatedDateTo(String createdDateTo) {
        setFilter(OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_TO, createdDateTo);
    }
    
    public void filterByExchangeOldCar(String exchangeOldCar) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR, exchangeOldCar);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByAssignedTo(String assignedTo) {
        setFilter(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO, assignedTo);
    }
    
    public void filterByAssignedTeam(String assignedTeam) {
        setFilter(OpportunityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID, assignedTeam);
    }
    
    public void filterByCreatedBy(String createdBy) {
        setFilter(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN, createdBy);
    }
    
    public void filterByLeadSource(String dataSourceId) {
        setFilter(OpportunityLookupConfiguration.INOUT_DATA_SOURCE, dataSourceId);
    }
    
//    /**
//     * Filters the records of the list by work effort status matching the given classification.
//     * @param classification a <code>String</code> value
//     */
//    public void filterByLead(String leadPartyId) {
//        setFilter(OpportunityLookupConfiguration.INOUT_PARTY_ID, leadPartyId);
//    }
//    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByAccount(String accountPartyId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT, accountPartyId);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByOppLead(String leadPartyId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD, leadPartyId);
    }
    
    public void filterByContact(String contactPartyId) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT, contactPartyId);
    }
    
    public void filterByMoi(String moi) {
        setFilter(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST, moi);
    }
}
