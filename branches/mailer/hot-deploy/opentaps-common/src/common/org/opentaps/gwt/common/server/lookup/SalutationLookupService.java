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
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityFieldValue;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.Enumeration;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.LeadSourcesLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate salutation autocompleters widgets.
 */
public class SalutationLookupService extends EntityLookupAndSuggestService {

    protected SalutationLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(PartyLookupConfiguration.INOUT_ENUMERATION_ID,PartyLookupConfiguration.OUT_ENUMERATION_DESCRIPTION));
    }

    /**
     * AJAX event to suggest Salutation.
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
    public static String suggestSalutations(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        SalutationLookupService service = new SalutationLookupService(provider);
        service.suggestSalutations();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_ENUMERATION_ID, service);
    }

    /**
     * Gets all salutations.
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
	public List<Enumeration> suggestSalutations() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	String query = this.getProvider().getParameter("query");
    	List lCond1 = UtilMisc.toList(new EntityExpr( "enumTypeId" , EntityOperator.EQUALS , "SALUTATION"));
    	if(query != null) {    		
    		List lCond2 = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(LeadSourcesLookupConfiguration.OUT_SOURCE_DESCRIPTION)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		lCond1.addAll(lCond2);
    	}
    	//EntityCondition condition = new EntityExpr( "enumTypeId" , EntityOperator.EQUALS , "SALUTATION" );
    	EntityCondition entityCondition = new EntityConditionList(lCond1, EntityJoinOperator.AND);
    	return findList(Enumeration.class, entityCondition);
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface salutation) {
        return salutation.getString(PartyLookupConfiguration.OUT_ENUMERATION_DESCRIPTION);
    }
}
