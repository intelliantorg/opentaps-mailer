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
import java.util.ArrayList;
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
import org.ofbiz.entity.util.EntityUtil;
import org.opentaps.domain.base.entities.PartyToSummaryByRelationship;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.TeamMembersLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;
import java.util.Iterator;
/**
 * The RPC service used to populate Team Members autocompleters widgets.
 */
public class TeamMembersLookupService extends EntityLookupAndSuggestService {

    protected TeamMembersLookupService(InputProviderInterface provider) {
        super(provider,
              Arrays.asList(TeamMembersLookupConfiguration.OUT_PARTY_ID,
            		  		TeamMembersLookupConfiguration.OUT_PARTY_FIRST_NAME,
            		  		TeamMembersLookupConfiguration.OUT_PARTY_LAST_NAME));
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
    public static String suggestTeamMembers(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        TeamMembersLookupService service = new TeamMembersLookupService(provider);
        service.suggestTeamMembers((String)provider.getUser().getOfbizUserLogin().get("partyId"));
        return json.makeSuggestResponse(TeamMembersLookupConfiguration.OUT_PARTY_ID, service);
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
	public List<PartyToSummaryByRelationship> suggestTeamMembers(String partyId) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	String query = this.getProvider().getParameter("query"); 
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		Class teamHelper = Class.forName("com.opensourcestrategies.crmsfa.teams.TeamHelper");
    	Method getTeamsForPartyId = teamHelper.getMethod("getTeamsForPartyId", new Class[]{String.class,GenericDelegator.class});
    	Collection<String> teamPartyIds = (Collection<String>) getTeamsForPartyId.invoke(teamHelper,new Object[]{partyId,delegator});
    	List lCond1 = new ArrayList();
    	if(query != null) {    		
    		lCond1 = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(TeamMembersLookupConfiguration.OUT_PARTY_FIRST_NAME)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		List lCond2 = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(TeamMembersLookupConfiguration.OUT_PARTY_LAST_NAME)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		lCond1.addAll(lCond2);
    	}
    	EntityCondition queryCondition = new EntityConditionList(lCond1, EntityJoinOperator.OR);
    	EntityCondition orConditions =  new EntityConditionList( UtilMisc.toList(
                new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_MANAGER"),
                new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_REP"),
                new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_REP_LIMITED"),
                new EntityExpr("securityGroupId", EntityOperator.EQUALS, "CSR")
                ), EntityOperator.OR);
    	EntityCondition conditions = null;
    	if(query != null) {
    		conditions = new EntityConditionList( UtilMisc.toList(
                new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "ACCOUNT_TEAM"),
                new EntityExpr("partyIdFrom", EntityOperator.IN, teamPartyIds),
                new EntityExpr("partyRelationshipTypeId", EntityOperator.EQUALS, "ASSIGNED_TO"),
                orConditions,
                // new EntityExpr("securityGroupId", EntityOperator.IN, TEAM_SECURITY_GROUPS),  XXX TODO: found bug in mysql: this is not equivalent to using the or condition!
                queryCondition,                
                EntityUtil.getFilterByDateExpr()
                ), EntityOperator.AND);
    	}
    	else
    		conditions = new EntityConditionList( UtilMisc.toList(
                    new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "ACCOUNT_TEAM"),
                    new EntityExpr("partyIdFrom", EntityOperator.IN, teamPartyIds),
                    new EntityExpr("partyRelationshipTypeId", EntityOperator.EQUALS, "ASSIGNED_TO"),
                    orConditions,
                    // new EntityExpr("securityGroupId", EntityOperator.IN, TEAM_SECURITY_GROUPS),  XXX TODO: found bug in mysql: this is not equivalent to using the or condition!                                  
                    EntityUtil.getFilterByDateExpr()
                    ), EntityOperator.AND);
    	return findList(PartyToSummaryByRelationship.class,conditions);    	
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface teamMember) {
        StringBuffer teamMemberDetails = new StringBuffer();
        teamMemberDetails.append(teamMember.getString(TeamMembersLookupConfiguration.OUT_PARTY_FIRST_NAME));
        teamMemberDetails.append(" ");
        teamMemberDetails.append(teamMember.getString(TeamMembersLookupConfiguration.OUT_PARTY_LAST_NAME));
        teamMemberDetails.append(" (");
        teamMemberDetails.append(teamMember.getString(TeamMembersLookupConfiguration.OUT_PARTY_ID));
        teamMemberDetails.append(")");
        return teamMemberDetails.toString();
    }
}
