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

import org.opentaps.fitnesse.UiElementInterface;

/**
 * Defines the Webtools UI Elements.
 */
public enum WebtoolsElements implements UiElementInterface {

    // Main Tabs buttons
    /** Main tab. */
    MAIN_TAB_HOME("main tab", "link=Main");

    private final String name;
    private final String locator;

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public String getLocator() {
        return locator;
    }

    private WebtoolsElements(String name, String locator) {
        this.name = name;
        this.locator = locator;
    }

    private WebtoolsElements(UiElementInterface element) {
        this.name = element.getName();
        this.locator = element.getLocator();
    }

}
