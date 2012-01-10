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

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityFieldValue;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.SalesOpportunityStage;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate sales opportunity stage auto complete widgets.
 */

public class OpportunityStageLookupService extends EntityLookupAndSuggestService {

    protected OpportunityStageLookupService(InputProviderInterface provider) {
        super(provider, //Arrays.asList(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_DESCRIPTION));
              Arrays.asList(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_DESCRIPTION));
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     * @throws GenericEntityException 
     */
    public static String suggestOpportunityStages(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, GenericEntityException{
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        OpportunityStageLookupService service = new OpportunityStageLookupService(provider);
        service.suggestOpportunityStages();
        return json.makeSuggestResponse(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE, service);
    }

    /**
     * Gets all sales opportunity stages.
     * @return the list of party classifications <code>GenericValue</code>
     * @throws GenericEntityException 
     */
    @SuppressWarnings("unchecked")
	public List<SalesOpportunityStage> suggestOpportunityStages() throws GenericEntityException {
    	String query = getSuggestQuery();
    	EntityCondition condition = null;
    	if(query != null) {    		
    		List lCond = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_DESCRIPTION)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    		condition = new EntityConditionList(lCond, EntityJoinOperator.AND);
    	}    	    	
    	return findList(SalesOpportunityStage.class,condition);    	
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface oppStage) {
        return oppStage.getString(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_DESCRIPTION);
    }
}
