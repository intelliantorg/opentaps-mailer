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
 * Methods for locating and login to Opentaps applications.
 */
public interface OpentapsApplicationInterface {

    /**
     * Gets the application web root as defined the component configuration.
     * This corresponds to the first element of the URLs eg: "crmsfa" in <code>/crmsfa/control/login</code>.
     * @return the URL root
     */
    public String getWebappRoot();

    /**
     * Gets the default user login to use when login into the application.
     * @return the user login
     * @see #getDefaultPassword
     */
    public String getDefaultLogin();

    /**
     * Gets the default password to use when login into the application.
     * @return the user password
     * @see #getDefaultLogin
     */
    public String getDefaultPassword();

}
