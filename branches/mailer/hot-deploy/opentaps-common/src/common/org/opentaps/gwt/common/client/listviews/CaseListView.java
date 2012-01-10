/**
 * 
 */
package org.opentaps.gwt.common.client.listviews;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.lookup.configuration.CaseLookupConfiguration;

import com.gwtext.client.core.SortDir;
import com.gwtext.client.data.StringFieldDef;

/**
 * @author soham.ray
 *
 */
public class CaseListView extends EntityListView {
	
	/**
     * Default constructor.
     */
    public CaseListView() {
        super();
        // the msg is built in the contructor of EntityListView
        // so we cannot pas the title directly
        setTitle(UtilUi.MSG.caseList());
    }

    /**
     * Constructor giving a title for this list view, which is displayed in the UI.
     * @param title the title label for this list view.
     */
    public CaseListView(String title) {
        super(title);
        init();
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the default columns for Activity and extra columns.
     */
    public void init() {
        //init(entityFindUrl, null, workEffortIdLabel, nameColumns);
    	init(CaseLookupConfiguration.URL_FIND_CASES, "/crmsfa/control/viewCase?custRequestId={0}", UtilUi.MSG.crmCase());
    }

    /**
     * Configures the list columns and interaction with the server request that populates it.
     * Constructs the column model and JSON reader for the list with the columns for work effort, as well as a link for a view page.
     * @param entityFindUrl the URL of the request to populate the list
     * @param entityViewUrl the URL linking to the entity view page with a placeholder for the ID. The ID column will use it to provide a link to the view page for each record. For example <code>/crmsfa/control/viewContact?partyId={0}</code>. This is optional, if <code>null</code> then no link will be provided
     * @param caseIdLabel the label of the ID column, which depends of the entity that is listed
     */
    private void init(String entityFindUrl, String entityViewUrl, String caseIdLabel) {
        // add work effort id as the first column
        StringFieldDef idDefinition = new StringFieldDef(CaseLookupConfiguration.INOUT_CUST_REQT_ID);
        if (entityViewUrl != null) {
            makeLinkColumn(caseIdLabel, idDefinition, entityViewUrl, true);
        } else {
            makeColumn(caseIdLabel, idDefinition);
        }

        // add remaining fields
        makeColumn(UtilUi.MSG.partySubject(), new StringFieldDef(CaseLookupConfiguration.INOUT_CUST_REQT_NAME));
        makeColumn(UtilUi.MSG.commonStatus(), new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_STATUS_DESC));
        makeColumn(UtilUi.MSG.commonType(), new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_TYPE_DESC));
        makeColumn(UtilUi.MSG.commonPriority(), new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_PRIORITY_DESC));
        /*makeColumn(UtilUi.MSG.account(), new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_ACCOUNT));
        makeColumn(UtilUi.MSG.contactId(), new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_CONTACT));*/
        makeColumn("Party", new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_PARTY_NAME));
        makeColumn("Assigned To", new StringFieldDef(CaseLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN));
        makeColumn("Created Date", new StringFieldDef(CaseLookupConfiguration.INOUT_CASE_DATE));
        
        configure(entityFindUrl, CaseLookupConfiguration.INOUT_CUST_REQT_ID, SortDir.ASC);  
    }

    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByCaseId(String caseId) {
        setFilter(CaseLookupConfiguration.INOUT_CUST_REQT_ID, caseId);
    }
    
    /**
     * Filters the records of the list by work effort name matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByCaseSubject(String caseSubject) {
        setFilter(CaseLookupConfiguration.INOUT_CUST_REQT_NAME, caseSubject);
    }

    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByCaseStatus(String caseStatus) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_STATUS_ID, caseStatus);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByCasePriority(String casePriority) {
    	if(casePriority.equals("01")){
    		casePriority = "1";
    	}
    	if(casePriority.equals("03")){
    		casePriority = "3";
    	}
    	if(casePriority.equals("05")){
    		casePriority = "5";
    	}
    	if(casePriority.equals("07")){
    		casePriority = "7";
    	}
    	if(casePriority.equals("09")){
    		casePriority = "9";
    	}
        setFilter(CaseLookupConfiguration.INOUT_CASE_PRIORITY, casePriority);
    }
    
    /**
     * Filters the records of the list by work effort status matching the given classification.
     * @param classification a <code>String</code> value
     */
    public void filterByCaseType(String caseType) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_TYPE, caseType);
    }
    
    /**
     * Filters the records of the list by case account.
     * @param caseStatus
     */   
    public void filterByCaseAccount(String caseAccount) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_ACCOUNT, caseAccount);
    }
    
    /**
     * Filters the records of the list by case account.
     * @param caseStatus
     */   
    public void filterByCaseContact(String caseContact) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_CONTACT, caseContact);
    }
    
    /**
     * Filters the records of the list by case assigned to.
     * @param caseAccount
     */
    public void filterByCaseAssignedTo(String caseAssignedTo) {
        setFilter(CaseLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN, caseAssignedTo);
    }
    
    public void filterByCreatedFromDate(String createdFromDate) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_DATE_FROM, createdFromDate);
    }
    
    public void filterByCreatedToDate(String createdToDate) {
        setFilter(CaseLookupConfiguration.INOUT_CASE_DATE_TO, createdToDate);
    }

}
