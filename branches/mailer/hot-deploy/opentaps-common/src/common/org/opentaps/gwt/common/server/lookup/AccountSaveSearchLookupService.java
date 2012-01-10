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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.SavedSearches;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class AccountSaveSearchLookupService extends EntityLookupAndSuggestService {

    protected AccountSaveSearchLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(PartyLookupConfiguration.INOUT_NAME,PartyLookupConfiguration.INOUT_NAME));
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
    public static String suggestAccountSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);        
        JsonResponse json = new JsonResponse(response);
        Map m = provider.getParameterMap();
        Set mSet = m.keySet();
        Iterator i = mSet.iterator();
        while(i.hasNext()) {
        	String str = (String)i.next();
        	Debug.logError("Sudipta>>>"+str + " " +provider.getParameter(str), "");
        }
        AccountSaveSearchLookupService service = new AccountSaveSearchLookupService(provider);
        service.suggestAccountSaveSearch();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
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
	public List<SavedSearches> suggestAccountSaveSearch() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	//EntityCondition condition = new EntityExpr( "statusId" , EntityOperator.LIKE , "EVENT_%");
    	List saveList = new ArrayList();
    	List saveFieldsToSelect = new ArrayList();
    	saveFieldsToSelect.add("name");
    	List orderBy = new ArrayList();
    	orderBy.add("name");
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	
    	 EntityCondition entityCondition = new EntityConditionList(UtilMisc.toList(new EntityExpr("type",EntityOperator.EQUALS,"ACCOUNT")),EntityJoinOperator.AND);
    	 //EntityFindOptions findOptions = new EntityFindOptions(false, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
    	 //saveList = delegator.findByCondition("SavedSearches", entityCondition, null, saveFieldsToSelect, orderBy, findOptions);
    	 //return saveList;
    	 
    	 return findList(SavedSearches.class,entityCondition);
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface industry) {
        return industry.getString(PartyLookupConfiguration.INOUT_NAME);
    }
}
