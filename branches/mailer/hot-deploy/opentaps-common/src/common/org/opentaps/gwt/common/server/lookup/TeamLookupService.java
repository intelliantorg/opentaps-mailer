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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
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
import org.ofbiz.entity.condition.EntityFieldValue;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.PartyGroup;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.LeadSourcesLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.TeamLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate Team Members autocompleters widgets.
 */
public class TeamLookupService extends EntityLookupAndSuggestService {
	
	private static final String MODULE = TeamLookupService.class.getName();

    protected TeamLookupService(InputProviderInterface provider) {
        super(provider,
              Arrays.asList(TeamLookupConfiguration.INOUT_TEAM_NAME,
            		  		TeamLookupConfiguration.INOUT_TEAM_PARTY_ID
            		  		));
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
    public static String suggestTeam(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        TeamLookupService service = new TeamLookupService(provider);
        Debug.logInfo("==============================================>TEAM PARTY ID=> "+(String)provider.getUser().getOfbizUserLogin().get("partyId"), MODULE);
        service.suggestTeam((String)provider.getUser().getOfbizUserLogin().get("partyId"));
        return json.makeSuggestResponse(TeamLookupConfiguration.INOUT_TEAM_PARTY_ID, service);
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
	public List<PartyGroup> suggestTeam(String partyId) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	Class teamHelper = Class.forName("com.opensourcestrategies.crmsfa.teams.TeamHelper");
    	Method getTeamsForPartyId = teamHelper.getMethod("getTeamsForPartyId", new Class[]{String.class,GenericDelegator.class});
    	Collection<String> teamPartyIds = (Collection<String>) getTeamsForPartyId.invoke(teamHelper,new Object[]{partyId,delegator});	
    	//Debug.logInfo("==============================================>TEAM teamPartyIds=> "+teamPartyIds, MODULE);
    	
    	String query = this.getProvider().getParameter("query");
    	List lCond1 = UtilMisc.toList(new EntityExpr(TeamLookupConfiguration.INOUT_TEAM_PARTY_ID , EntityOperator.IN , teamPartyIds));
    	//EntityCondition condition = new EntityExpr("partyId" , EntityOperator.IN , teamPartyIds );
    	//return findList(PartyRole.class,condition);
    	if(query != null) {    		
    		List lCond2 = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(TeamLookupConfiguration.INOUT_TEAM_NAME)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		lCond1.addAll(lCond2);
    	}
    	EntityCondition entityCondition = new EntityConditionList(lCond1, EntityJoinOperator.AND); 
    	return findList(PartyGroup.class, entityCondition);
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface team) {    	    	
    	StringBuffer teamSuggestionDetails = new StringBuffer();
    	teamSuggestionDetails.append(team.getString(TeamLookupConfiguration.INOUT_TEAM_NAME));
    	teamSuggestionDetails.append(" (");
    	teamSuggestionDetails.append(team.getString(TeamLookupConfiguration.INOUT_TEAM_PARTY_ID));
    	teamSuggestionDetails.append(")");
        return teamSuggestionDetails.toString();
    	
    }
}
