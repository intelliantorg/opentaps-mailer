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
import org.opentaps.domain.base.entities.WorkEffortPurposeType;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.ActivityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class ActivityPurposeTypeLookupService extends EntityLookupAndSuggestService {

    protected ActivityPurposeTypeLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID,ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_DESC));
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
    public static String suggestActivityPurposeType(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, SecurityException, IllegalArgumentException, GenericEntityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        ActivityPurposeTypeLookupService service = new ActivityPurposeTypeLookupService(provider);
        service.suggestActivityPurposeType();
        return json.makeSuggestResponse(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID, service);
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
	public List<WorkEffortPurposeType> suggestActivityPurposeType() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, GenericEntityException {
    	String query = this.getProvider().getParameter("query");
    	List lCond = new ArrayList();
    	if(query != null) {    		
    		lCond = UtilMisc.toList(new EntityExpr(new EntityFunction.UPPER(new EntityFieldValue(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_DESC)), EntityOperator.LIKE, new EntityFunction.UPPER("%" + query + "%")));
    	}
    	EntityCondition andCondition = new EntityConditionList(lCond, EntityJoinOperator.AND);    	
    	EntityCondition orCondition = new EntityConditionList(
                UtilMisc.toList(
                        new EntityExpr("workEffortPurposeTypeId", EntityOperator.LIKE, "%_MEETING_%"),
                        new EntityExpr("workEffortPurposeTypeId", EntityOperator.LIKE, "%_TASK%")
                ), EntityOperator.OR
        );
    	EntityCondition condition = null;
    	if(query != null) {
    		condition = new EntityConditionList(UtilMisc.toList(orCondition, andCondition), EntityJoinOperator.AND);
    	}
    	else
    		condition = orCondition;
    	return findList(WorkEffortPurposeType.class, condition);
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface industry) {
        return industry.getString(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_DESC);
    }
}
