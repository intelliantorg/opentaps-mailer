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

package org.opentaps.aspect.profiling.testtools;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

import org.ofbiz.base.util.Debug;

public aspect TestRunContainerProfiling {

    public static final String module = TestRunContainerProfiling.class.getName();
    private static final EtmMonitor etmMonitor = EtmManager.getEtmMonitor();

    pointcut testRunContainerStart() : execution(public boolean org.ofbiz.testtools.TestRunContainer.start());

    boolean around(): testRunContainerStart() {
        boolean ret = false;

        // load jetm to monitor time
        Debug.logInfo("Start JETM monitoring", module);
        BasicEtmConfigurator.configure();
        etmMonitor.start();

        ret = proceed();

        //visualize results
        Debug.log("[JETM] ------------------------------------------------------------------ [JETM]", module);
        etmMonitor.render(new SimpleTextRenderer(Debug.getPrintWriter()));
        Debug.log("[JETM] ------------------------------------------------------------------ [JETM]", module);

        etmMonitor.stop();

        return ret;
    }

}
