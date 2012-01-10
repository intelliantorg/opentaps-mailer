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

/**
 * Manages context variables.
 * Variables can be stored and accessed from the fitnesse tests, those starting with ## are global variables
 *  and stored in the global context, whereas variables starting with # are local.
 */
public class ContextManager {

    private VariablesStore localContext;
    private static VariablesStore globalContext = new VariablesStore();

    /**
     * Creates a new <code>ContextManager</code> instance.
     */
    public ContextManager() {
        this.localContext = new VariablesStore();
    }

    /**
     * Puts a variable into the context, if the variable name starts with ## it is stored in the global context, else in the local context.
     * @param key the variable name
     * @param value the variable value
     */
    public void putValue(String key, String value) {
        if (key.startsWith("##")) {
            globalContext.put(key, value);
        } else {
            localContext.put(key, value);
        }
    }

    /**
     * Translates a key from the appropriate context, if the variable name starts with ## it is retrieved from the global context, else from the local context.
     * If it is a variable name (starting with #) gets its value, else returns the key name.
     * This allows passing variable names or normal non variable text value and let the method sort it out.
     * @param key the variable name
     * @return the variable value, or key if the name does not start with #
     */
    public String translateValue(String key) {
        if (key.startsWith("##")) {
            return globalContext.translateValue(key);
        } else {
            return localContext.translateValue(key);
        }
    }


}
