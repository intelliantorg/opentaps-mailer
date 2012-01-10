/*
 * Copyright (c) 2009 - 2009 Open Source Strategies, Inc.
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

package org.ofbiz.testtools;

import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.AssertionFailedError;
import org.ofbiz.base.util.Debug;

/**
 * Extends the Junit <code>TestSuite</code> to provide debugging and timing information.
 */
public class DebugTestListener implements TestListener {

    private static final String MODULE = DebugTestListener.class.getName();

    private int counter = 0;
    private int total;
    private long startTime;
    private long endTime;

    /**
     * Creates a new <code>DebugTestListener</code> instance.
     */
    public DebugTestListener(int total) {
        super();
        this.total = total;
    }

    public void startTest(Test test) {
        Debug.log("[JUNIT:Test] ------------------------------------------------------------------", MODULE);
        Debug.log("[JUNIT:Test] Starting test " + counter++ + "/" + total + " : " + test, MODULE);
        startTime = System.currentTimeMillis();
    }

    public void endTest(Test test) {
        endTime = System.currentTimeMillis();
        Debug.log("[JUNIT:Test] Finished test: " + test + " in " + (endTime - startTime) + " ms.", MODULE);
    }

    public void addError(Test test, Throwable t) {
        Debug.logError(t, "[JUNIT:Test:ERROR] " + test + ": " + t.getMessage(), MODULE);
    }

    public void addFailure(Test test, AssertionFailedError t) {
        Debug.logError(t, "[JUNIT:Test:FAILURE] " + test + ": " + t.getMessage(), MODULE);
    }
}
