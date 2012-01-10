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

package org.opentaps.fitnesse;

import fitlibrary.DoFixture;

/**
 * Base class extending <code>DoFixture</code> and implementing a <code>ContextManager</code>.
 */
public class BaseFixture extends DoFixture {

    private ContextManager contextManager;

    /**
     * Creates a new <code>BaseFixture</code> instance.
     */
    public BaseFixture() {
        contextManager = new ContextManager();
        registerParseDelegate(String.class, this);
    }

    //TODO This will not work when there are multiple test threads running, since parse uses the state in ContextManager. Revisit if need arises
    /**
     * Parse method for registering a delegate object that will handle parsing of values like #myProduct
     * when they are used in fit table cells. See method registerParseDelegate() in constructor.
     *
     * @param stringToParse string to parse
     * @return value
     */
    public String parse(String stringToParse) {
        return contextManager.translateValue(stringToParse);
    }

    /**
     * Gets the context manager.
     * @return a <code>ContextManager</code> value
     */
    public ContextManager getContextManager() {
        return contextManager;
    }

}
