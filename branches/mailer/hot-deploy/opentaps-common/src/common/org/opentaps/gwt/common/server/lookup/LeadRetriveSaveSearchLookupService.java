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
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.common.event.AjaxEvents;
import org.opentaps.domain.base.entities.SavedSearches;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class LeadRetriveSaveSearchLookupService extends EntityLookupAndSuggestService {

	private static final String MODULE = LeadRetriveSaveSearchLookupService.class.getName();
	
    protected LeadRetriveSaveSearchLookupService(InputProviderInterface provider) {
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
	public static String suggestLeadRetriveSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        String saveName = request.getParameter("saveName");
        // JsonResponse json = new JsonResponse(response);
        LeadRetriveSaveSearchLookupService service = new LeadRetriveSaveSearchLookupService(provider);
       // @SuppressWarnings("unused")
		List list = new ArrayList();
		list = service.suggestRetriveSaveSearch(saveName);
        Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&SIZE=> "+list.size(), MODULE);
        Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&VALUE=> "+list.toString(), MODULE);
        
        Map retriveMap = new HashMap();
        for(int i=0;i<list.size();i++){
        	GenericValue gv = (GenericValue)list.get(0);
        	String saveKey = gv.getString("saveKey");
        	String saveValue = gv.getString("value");
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
	public List<GenericValue> suggestRetriveSaveSearch(String saveName) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	//EntityCondition entityCondition = null;
    	List list = new ArrayList();
    	if(saveName!=null){
	    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	    	HashMap m = new HashMap();
	    	m.put("name", saveName);
	    	list = delegator.findByAnd("SavedSearches", m);
	    	
	    	/*for(int i=0;i<list.size();i++){
	    		GenericValue gv = (GenericValue)list.get(i);
	    		Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&DATA => "+gv.getString("name")+gv.getString("saveKey")+gv.getString("value"));
	    	}*/
	    	//entityCondition  = new EntityConditionList(UtilMisc.toList(new EntityExpr("name",EntityOperator.EQUALS, saveName)),EntityJoinOperator.AND);
    	}
    	// return findList(SavedSearches.class,entityCondition);
    	return list;
    	
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface industry) {
        return industry.getString(PartyLookupConfiguration.INOUT_VALUE);
    }
}
