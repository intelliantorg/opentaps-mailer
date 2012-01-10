/*
 * Copyright (c) 2006 - 2008 Open Source Strategies, Inc.
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
package org.opentaps.aspect.secas.order;

import org.opentaps.aspect.secas.order.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.order.OrderServices;
import org.opentaps.common.util.UtilCommon;
import org.opentaps.common.util.UtilMessage;

@SuppressWarnings("unchecked")
@Aspect
public class OrderAspects {

    public static final String module = OrderAspects.class.getName();

    @Pointcut("execution(public static Map org.ofbiz.order.order.OrderServices.createOrder(DispatchContext, Map)) && args(dctx, context)")
    void aroundCreateOrder(DispatchContext dctx, Map context){}

    @Around("aroundCreateOrder(dctx, context)")
    public Map aroundCreateOrder(ProceedingJoinPoint jp, DispatchContext dctx, Map context) {
        Map<String, Object> results = null;

        try {

            results = (Map<String, Object>) jp.proceed(UtilCommon.serviceArgsToArray(dctx, context));

            if (!ServiceUtil.isError(results)) {
                Map<String, Object> callResults = OrderServices.setOrderHeaderPartiesFromRoles(dctx, UtilCommon.makeValidSECAContext(dctx, results, true));
                if (ServiceUtil.isError(callResults)) {
                    Map<String, Object> errorResult = ServiceUtil.returnError((String) callResults.get("errorMessage"), (List) callResults.get("errorMessageList"));
                    return errorResult;
                }
            }
        } catch (GenericEntityException gee) {
            return UtilMessage.createAndLogServiceError(gee, (Locale)context.get("locale"), module);
        } catch (Throwable e) {
            return UtilMessage.createAndLogServiceError(e.getLocalizedMessage(), (Locale)context.get("locale"), module);
        }

        return results;
    }

}
