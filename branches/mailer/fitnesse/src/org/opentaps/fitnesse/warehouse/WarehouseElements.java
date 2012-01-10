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

package org.opentaps.fitnesse.warehouse;

import org.opentaps.fitnesse.UiElementInterface;

/**
 * Defines the Warehouse UI Elements.
 */
public enum WarehouseElements implements UiElementInterface {

    // Main Tabs buttons
    /** My Home tab. */
    MAIN_TAB_HOME("home tab", "link=My Home");

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

    private WarehouseElements(String name, String locator) {
        this.name = name;
        this.locator = locator;
    }

    private WarehouseElements(UiElementInterface element) {
        this.name = element.getName();
        this.locator = element.getLocator();
    }

}
