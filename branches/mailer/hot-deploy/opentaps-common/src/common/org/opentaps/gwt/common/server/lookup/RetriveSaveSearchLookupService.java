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

package org.opentaps.gwt.common.server.lookup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.opentaps.common.event.AjaxEvents;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class RetriveSaveSearchLookupService extends EntityLookupAndSuggestService {
	
	private static final String MODULE = RetriveSaveSearchLookupService.class.getName();

    protected RetriveSaveSearchLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(PartyLookupConfiguration.INOUT_SAVEKEY,PartyLookupConfiguration.INOUT_VALUE));
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws ClassNotFoundException 
     * @throws GenericEntityException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    @SuppressWarnings("unchecked")
	public static String suggestRetriveSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        String saveName = request.getParameter("saveName");
        String saveType = request.getParameter("saveType");
        RetriveSaveSearchLookupService service = new RetriveSaveSearchLookupService(provider);
        List list = new ArrayList();
		list = service.suggestRetriveSaveSearchGetDate(saveName);
        Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&SIZE=> "+list.size(), MODULE);
        Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&VALUE=> "+list.toString(), MODULE);
        Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&saveType=> "+saveType, MODULE);
        
        Map retriveMap = new HashMap();
        for(int i=0;i<list.size();i++){
        	GenericValue gv = (GenericValue)list.get(i);
        	@SuppressWarnings("unused")
			String duplicateSaveKey = null;
        	String saveKey = gv.getString("saveKey");
        	String saveValue = gv.getString("value");
        	
       //******************************** FOR LEAD************************************************
        	if(saveType.equals("PROSPECT")){
        		if(saveKey.equals("advLeadStatusInput")){
	        		duplicateSaveKey = saveKey+"1";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("description", saveValue.trim());
	    	    	m1.put("statusTypeId", "PARTY_LEAD_STATUS");
	    	    	keyList = delegator.findByAnd("StatusItem", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("statusId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("advLeadSourceInput")){
	        		duplicateSaveKey = saveKey+"1";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("description", saveValue.trim());
	    	    	m1.put("dataSourceTypeId", "LEAD_GENERATION");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("dataSourceId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("countryInput")){
	        		duplicateSaveKey = "Country";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "COUNTRY");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("stateInput")){
	        		duplicateSaveKey = "State";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "STATE");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
        	}
        	
     //******************************** FOR CONTACT************************************************
        	if(saveType.equals("CONTACT")){
        		if(saveKey.equals("stateInput")){
	        		duplicateSaveKey = "State";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "STATE");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("countryInput")){
	        		duplicateSaveKey = "Country";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "COUNTRY");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
        	}	
        	
    //******************************** FOR ACCOUNT************************************************
        	if(saveType.equals("ACCOUNT")){
        		if(saveKey.equals("stateInput")){
	        		duplicateSaveKey = "State";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "STATE");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("countryInput")){
	        		duplicateSaveKey = "Country";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("geoName", saveValue.trim());
	    	    	m1.put("geoTypeId", "COUNTRY");
	    	    	keyList = delegator.findByAnd("Geo", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("geoId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("accType")){
	        		duplicateSaveKey = "AccountType";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("description", saveValue.trim());
	    	    	m1.put("enumTypeId", "PARTY_ACCOUNT_TYPE");
	    	    	keyList = delegator.findByAnd("Enumeration", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("enumId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("ownerShip")){
	        		duplicateSaveKey = "OwnType";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("description", saveValue.trim());
	    	    	m1.put("enumTypeId", "PARTY_OWNERSHIP");
	    	    	keyList = delegator.findByAnd("Enumeration", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("enumId");
	    	    		
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        		
	        	}
	        	if(saveKey.equals("industry")){
	        		duplicateSaveKey = "IndusName";
	        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	        		@SuppressWarnings("unused")
					List keyList = new ArrayList();
	    	    	HashMap m1 = new HashMap();
	    	    	m1.put("description", saveValue.trim());
	    	    	m1.put("enumTypeId", "PARTY_INDUSTRY");
	    	    	keyList = delegator.findByAnd("Enumeration", m1);
	    	    	if(keyList.size()>0){
	    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
	    	    		String saveKeyValue = gv1.getString("enumId");
	    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
	    	    	}
	        	}
        	} 	
	        	
	 //******************************** FOR OPPORTUNITY ************************************************
	        	if(saveType.equals("OPPORTUNITY")){
	        		if(saveKey.equals("advOppStageInput")){
		        		duplicateSaveKey = "OpportunityStage";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	keyList = delegator.findByAnd("SalesOpportunityStage", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("opportunityStageId");
		    	    		Debug.logInfo("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!opportunityStageId=> "+saveKeyValue, MODULE);
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        	}
		        	if(saveKey.equals("moi")){
		        		duplicateSaveKey = "Model";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	m1.put("enumTypeId", "HONDA_CAR_MODEL");
		    	    	keyList = delegator.findByAnd("Enumeration", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("enumId");
		    	    		Debug.logInfo("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!enumId=> "+saveKeyValue, MODULE);
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
		        	if(saveKey.equals("leadSourceInput")){
		        		duplicateSaveKey = "DataSource";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	m1.put("dataSourceTypeId", "LEAD_GENERATION");
		    	    	keyList = delegator.findByAnd("DataSource", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("dataSourceId");
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
	        	}	
	        	
	   //******************************** FOR CASE*****************************************************
	        	if(saveType.equals("CASE")){
	        		if(saveKey.equals("advCaseStatusInput")){
		        		duplicateSaveKey = "StatusType";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	m1.put("statusTypeId", "CUSTREQ_STTS");
		    	    	keyList = delegator.findByAnd("StatusItem", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("statusId");
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
		        	if(saveKey.equals("advCaseTypeInput")){
		        		duplicateSaveKey = "TypeOfCase";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	keyList = delegator.findByAnd("CustRequestType", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("custRequestTypeId");
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
		        	if(saveKey.equals("advCasePriorityInput")){
		        		duplicateSaveKey = "TypeOfPriority";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	Debug.logInfo("=====================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>description=> "+saveValue, MODULE);
		    	    	m1.put("description", saveValue.trim());
		    	    	//m1.put("enumTypeId", "PRIORITY_LEV");
		    	    	keyList = delegator.findByAnd("Enumeration", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("sequenceId");
		    	    		Debug.logInfo("=====================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>enumCode=> "+saveKeyValue, MODULE);
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
	        	}	
	        	
        	
	  	//******************************** FOR ACTIVITY *****************************************************
	        	if(saveType.equals("ACTIVITY")){
	        		if(saveKey.equals("advActivityStatusInput")){
		        		duplicateSaveKey = "StatusType";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	//m1.put("statusTypeId", "TASK_STATUS");
		    	    	keyList = delegator.findByAnd("StatusItem", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("statusId");
		    	    		//String finalstring = saveKeyValue.substring(3, saveKeyValue.length()+1);
		    	    		//Debug.logInfo("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!finalstring=> "+finalstring);
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
		        	if(saveKey.equals("advActivityPurposeInput")){
		        		duplicateSaveKey = "PurposeType";
		        		GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		        		@SuppressWarnings("unused")
						List keyList = new ArrayList();
		    	    	HashMap m1 = new HashMap();
		    	    	m1.put("description", saveValue.trim());
		    	    	keyList = delegator.findByAnd("WorkEffortPurposeType", m1);
		    	    	if(keyList.size()>0){
		    	    		GenericValue gv1 = (GenericValue)keyList.get(0);
		    	    		String saveKeyValue = gv1.getString("workEffortPurposeTypeId");
		    	    		retriveMap.put(duplicateSaveKey,saveKeyValue);
		    	    	}
		        		
		        	}
	        	}	
        	
        		
        	
        	if(saveKey!=null && saveValue!=null){
        		retriveMap.put(saveKey,saveValue);
        	}
        }
       
        return AjaxEvents.doJSONResponse(response, JSONObject.fromMap(retriveMap));
    }

    /**
     * Gets all party classifications.
     * @return the list of party classifications <code>GenericValue</code>
     * @throws ClassNotFoundException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws GenericEntityException 
     */
    @SuppressWarnings("unchecked")
	public List<GenericValue> suggestRetriveSaveSearchGetDate(String saveName) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	//EntityCondition entityCondition = null;
    	List list = new ArrayList();
    	if(saveName!=null){
	    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	    	HashMap m = new HashMap();
	    	m.put("name", saveName);
	    	list = delegator.findByAnd("SavedSearches", m);
	    	
    	}
    return list;
    	
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface industry) {
        return industry.getString(PartyLookupConfiguration.INOUT_VALUE);
    }
}
