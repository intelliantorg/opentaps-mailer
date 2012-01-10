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

import java.util.HashMap;
import java.util.Map;

/**
 * Stores and retrieve variables.
 */
public class VariablesStore {
    private Map<String, String> context = new HashMap<String, String>();

    /**
     * Creates a new <code>VariablesStore</code> instance.
     */
    public VariablesStore() {
        this.context = new HashMap<String, String>();
    }

    /**
     * Stores a variable.
     * @param key the variable name
     * @param value the variable value
     */
    public void put(String key, String value) {
        context.put(key, value);
    }

    /**
     * Translates a key, if it is a variable name (starting with #) gets its value, else returns the key name.
     * This allows passing variable names or normal non variable text value and let the method sort it out.
     * @param key a variable name
     * @return the variable value, or the variable name if it did not start by $
     */
    public String translateValue(String key) {
        if (key.startsWith("#")) {
            return getValue(key);
        } else {
            return key;
        }
    }

    /**
     * Gets a value by key from the store.
     * @param key a <code>String</code> value
     * @return the value associated if the key was found, else the key
     */
    private String getValue(String key) {
        String value = context.get(key);
        if (value == null) {
            return key;
        } else {
            return value;
        }
    }
}
