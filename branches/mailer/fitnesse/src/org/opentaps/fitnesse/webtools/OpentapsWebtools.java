/*
 * Copyright (c) 2009 Open Source Strategies, Inc.
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

package org.opentaps.fitnesse.webtools;

import com.thoughtworks.selenium.Selenium;

import org.opentaps.fitnesse.ContextManager;
import org.opentaps.fitnesse.OpentapsCommon;

/**
 * Fixtures for the Webtools application.
 */
public class OpentapsWebtools extends OpentapsCommon {

    /**
     * Creates a new <code>OpentapsWebTools</code> instance.
     * @param selenium a <code>Selenium</code> value
     * @param contextManager a <code>ContextManager</code> value
     */
    public OpentapsWebtools(Selenium selenium, ContextManager contextManager) {
        super(selenium, contextManager);
        loadUiElements(WebtoolsElements.values());
    }

    /** {@inheritDoc} */
    public String getWebappRoot() {
        return "webtools";
    }
}
