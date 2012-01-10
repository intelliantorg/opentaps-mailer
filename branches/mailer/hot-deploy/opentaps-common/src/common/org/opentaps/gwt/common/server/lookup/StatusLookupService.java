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

import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.opentaps.domain.base.entities.StatusItem;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.StatusLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate ActivityStatus autocompleters widgets.
 */
public class StatusLookupService extends EntityLookupAndSuggestService {

	private static String statusType;
	
    protected StatusLookupService(InputProviderInterface provider) {
        super(provider,
              Arrays.asList(StatusLookupConfiguration.OUT_STATUS_ID,
            		  		StatusLookupConfiguration.OUT_DESCRIPTION));
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String suggestStatus(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        StatusLookupService service = new StatusLookupService(provider);
        service.suggestStatus();
        return json.makeSuggestResponse(StatusLookupConfiguration.OUT_STATUS_ID, service);
    }

    /**
     * Gets all party classifications.
     * @return the list of party classifications <code>GenericValue</code>
     */
    public List<StatusItem> suggestStatus() {
    	EntityCondition condition = null;
//    	StatusLookupService.statusType = StatusLookupConfiguration.STATUS_TYPE_CASE;
    	
//    	if(StatusLookupService.statusType.equals(StatusLookupConfiguration.STATUS_TYPE_CASE)){
//    		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
//    		condition = new EntityExpr( "statusTypeId" , EntityOperator.EQUALS , "CUSTREQ_STTS" );
//    			new EntityConditionList(
//	                Arrays.asList(                        
//	                        new EntityExpr("statusTypeId", EntityOperator.EQUALS, "CUSTREQ_STTS")
//	                ),
//	                EntityOperator.AND
//	        );
//    	}else if(StatusLookupService.statusType.equals(StatusLookupConfiguration.STATUS_TYPE_ACTIVITY)){
//	    	condition = new EntityConditionList(
//	                Arrays.asList(                        
//	                        new EntityExpr("statusTypeId", EntityOperator.EQUALS, "EVENT_STATUS"),
//	                        new EntityExpr("statusTypeId", EntityOperator.EQUALS, "TASK_STATUS")
//	                ),
//	                EntityOperator.OR
//	        );
//    	}
//    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+condition);
        return findList(StatusItem.class,condition);
    }
    
    public static String getStatusType() {
		return statusType;
	}

	public static void setStatusType(String statusType) {
		StatusLookupService.statusType = statusType;
	}

	@Override
    public String makeSuggestDisplayedText(EntityInterface activityStatus) {
        return activityStatus.getString(StatusLookupConfiguration.OUT_DESCRIPTION);
    }
}
