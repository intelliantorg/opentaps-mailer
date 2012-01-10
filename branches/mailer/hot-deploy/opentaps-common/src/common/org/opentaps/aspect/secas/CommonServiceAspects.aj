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
package org.opentaps.aspect.secas;

import java.util.Map;

import javolution.util.FastMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.base.util.Debug;
import org.opentaps.common.util.UtilCommon;

@Aspect
public class CommonServiceAspects {

    public static final String module = CommonServiceAspects.class.getName();

    /*
     * Pointcut echoServiceAroundDemo is example to demonstrate call flow for around advice. 
     */
    @Pointcut("execution(public static Map org.ofbiz.common.CommonServices.echoService(DispatchContext, Map)) && args(dctx, context)")
    void echoServiceAroundDemo(DispatchContext dctx, Map context){}

    @Around("echoServiceAroundDemo(dctx, context)")
    public Map firstEchoServiceAroundDemo(ProceedingJoinPoint jp, DispatchContext dctx, Map context) {
        Debug.logInfo("Around Advise Demo: enter into firstEchoServiceAroundDemo", module);
        
        
        // Calls next advice since it exists
        Map results = (Map) jp.proceed(UtilCommon.serviceArgsToArray(dctx, context)); 

        Debug.logInfo("Around Advise Demo: leave firstEchoServiceAroundDemo", module);
        
        // returns !!echoService!! results. Invoking code believe in this.
        return results;
    }

    @Around("echoServiceAroundDemo(dctx, context)")
    public Map nextEchoServiceAroundDemo(ProceedingJoinPoint jp, DispatchContext dctx, Map context) {
        
        Debug.logInfo("Around Advise Demo: enter into nextEchoServiceAroundDemo", module);
        
        // call CommonServices.echoService() since no more joined advices
        Debug.logInfo("Around Advise Demo: Call echoService", module);
        Map results = (Map) jp.proceed(UtilCommon.serviceArgsToArray(dctx, context)); 

        Debug.logInfo("Around Advise Demo: leave nextEchoServiceAroundDemo", module);

        return results;
    }
}
