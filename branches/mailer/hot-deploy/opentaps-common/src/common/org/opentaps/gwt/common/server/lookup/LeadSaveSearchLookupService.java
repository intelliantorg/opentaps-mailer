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

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityFieldValue;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.SavedSearches;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class LeadSaveSearchLookupService extends EntityLookupAndSuggestService {
	
	
	
    protected LeadSaveSearchLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(PartyLookupConfiguration.INOUT_NAME));
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs   
     * @throws GenericEntityException
     * @throws SecurityException 
     */
    public static String suggestLeadSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        LeadSaveSearchLookupService service = new LeadSaveSearchLookupService(provider);
        service.suggestLeadSaveSearch("PROSPECT");
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
    }

    /**
     * Gets all saved searches
     * @return List<SavedSearches>
     * @throws GenericEntityException 
     */
    @SuppressWarnings("unchecked")
	public List<SavedSearches> suggestLeadSaveSearch(String entityType) throws GenericEntityException {	
    	String query = this.getProvider().getParameter("query");    	
    	List lCond1 = UtilMisc.toList(new EntityExpr("type", EntityOperator.EQUALS, entityType));
    	if(query != null) {    		
    		List lCond2 = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue("name")), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		lCond1.addAll(lCond2);
    	}
    	EntityCondition entityCondition = new EntityConditionList(lCond1, EntityJoinOperator.AND);
    	
    	return findList(SavedSearches.class,entityCondition);    	 
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs   
     * @throws GenericEntityException
     * @throws SecurityException 
     */
    public static String suggestAccountSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        LeadSaveSearchLookupService service = new LeadSaveSearchLookupService(provider);
        service.suggestLeadSaveSearch("ACCOUNT");
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
    }
    
    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs   
     * @throws GenericEntityException
     * @throws SecurityException 
     */
    public static String suggestActivitySaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        LeadSaveSearchLookupService service = new LeadSaveSearchLookupService(provider);
        service.suggestLeadSaveSearch("ACTIVITY");
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
    }
    
    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs   
     * @throws GenericEntityException
     * @throws SecurityException 
     */
    public static String suggestContactSaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        LeadSaveSearchLookupService service = new LeadSaveSearchLookupService(provider);
        service.suggestLeadSaveSearch("CONTACT");
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
    }
    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs   
     * @throws GenericEntityException
     * @throws SecurityException 
     */
    public static String suggestOpportunitySaveSearch(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        LeadSaveSearchLookupService service = new LeadSaveSearchLookupService(provider);
        service.suggestLeadSaveSearch("OPPORTUNITY");
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_NAME, service);
    }
    @Override
    public String makeSuggestDisplayedText(EntityInterface industry) {
        return industry.getString(PartyLookupConfiguration.INOUT_NAME);
    }
}
