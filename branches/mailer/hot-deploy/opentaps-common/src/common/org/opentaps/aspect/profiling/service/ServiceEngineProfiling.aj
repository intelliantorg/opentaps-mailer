/*
 * Copyright (c) 2006 - 2007 Open Source Strategies, Inc.
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

package org.opentaps.aspect.profiling.service;

import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

import java.util.*;

import org.ofbiz.base.util.Debug;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericRequester;
import org.ofbiz.service.ModelService;

public aspect ServiceEngineProfiling {

    public static final String module = ServiceEngineProfiling.class.getName();
    private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

    pointcut serviceEcaRuleEval() : execution(public void org.ofbiz.service.eca.ServiceEcaRule.eval(String, DispatchContext, Map, Map, boolean, boolean, Set));

    void around(): serviceEcaRuleEval() {
        EtmPoint point = etmMonitor.createPoint( "ServiceEcaRuleEval");
        proceed();
        point.collect();
    }

    pointcut serviceEcaUtilEvalRules() : execution(public static void org.ofbiz.service.eca.ServiceEcaUtil.evalRules(String, Map, String, DispatchContext, Map, Map, boolean, boolean));

    void around(): serviceEcaUtilEvalRules() {
        EtmPoint point = etmMonitor.createPoint( "ServiceEcaUtilEvalRules");
        proceed();
        point.collect();
    }

    pointcut modelServiceValidate(Map test, String mode, Locale locale) : execution(public void org.ofbiz.service.ModelService.validate(Map, String, Locale)) && args(test, mode, locale);

    void around(Map test, String mode, Locale locale): modelServiceValidate(test, mode, locale) {
        EtmPoint point = null;
        if (ModelService.IN_PARAM.equals(mode)) {
            point = etmMonitor.createPoint( "ModelServiceValidate.Input");
        } else if (ModelService.OUT_PARAM.equals(mode)) {
            point = etmMonitor.createPoint( "ModelServiceValidate.Output");
        } else {
            point = etmMonitor.createPoint( "ModelServiceValidate");
        }
        proceed(test, mode, locale);
        point.collect();
    }

    pointcut genericEngineSendCallbacks() : execution(public void org.ofbiz.service.engine.GenericEngine.sendCallbacks(ModelService, Map, Object, int));

    void around(): genericEngineSendCallbacks() {
        EtmPoint point = etmMonitor.createPoint( "GenericEngineSendCallbacks");
        proceed();
        point.collect();
    }

    pointcut genericEngineRunSyncIgnore(String localName, ModelService modelService, Map context) : execution(public void org.ofbiz.service.engine.GenericEngine.runSyncIgnore(String, ModelService, Map)) && args(localName, modelService, context);

    void around(String localName, ModelService modelService, Map context): genericEngineRunSyncIgnore(localName, modelService, context) {
        EtmPoint point = etmMonitor.createPoint( "GenericEngineRunSync[" + modelService.name + "]");
        proceed(localName, modelService, context);
        point.collect();
    }

    pointcut genericEngineRunSync(String localName, ModelService modelService, Map context) : execution(public Map org.ofbiz.service.engine.GenericEngine.runSync(String, ModelService, Map)) && args(localName, modelService, context);

    Map around(String localName, ModelService modelService, Map context): genericEngineRunSync(localName, modelService, context) {
        Map ret = null;
        EtmPoint point = etmMonitor.createPoint( "GenericEngineRunSync[" + modelService.name + "]");
        ret = proceed(localName, modelService, context);
        point.collect();
        return ret;
    }

    pointcut genericEngineRunAsyncIgnore(String localName, ModelService modelService, Map context, boolean persist) : call(public void org.ofbiz.service.engine.GenericEngine.runAsync(String, ModelService, Map, boolean)) && args(localName, modelService, context, persist);

    void around(String localName, ModelService modelService, Map context, boolean persist): genericEngineRunAsyncIgnore(localName, modelService, context, persist) {
        EtmPoint point = etmMonitor.createPoint( "GenericEngineRunAsync[" + modelService.name + "]");
        proceed(localName, modelService, context, persist);
        point.collect();
    }

    pointcut genericEngineRunAsync(String localName, ModelService modelService, Map context, GenericRequester requester, boolean persist) : call(public void org.ofbiz.service.engine.GenericEngine.runAsync(String, ModelService, Map, GenericRequester, boolean)) && args(localName, modelService, context, requester, persist);

    void around(String localName, ModelService modelService, Map context, GenericRequester requester, boolean persist): genericEngineRunAsync(localName, modelService, context, requester, persist) {
        EtmPoint point = etmMonitor.createPoint( "GenericEngineRunAsync[" + modelService.name + "]");
        proceed(localName, modelService, context, requester, persist);
        point.collect();
    }

}
