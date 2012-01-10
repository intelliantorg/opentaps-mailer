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

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.opentaps.fitnesse.catalog.OpentapsCatalog;
import org.opentaps.fitnesse.crmsfa.OpentapsCrmsfa;
import org.opentaps.fitnesse.financials.OpentapsFinancials;
import org.opentaps.fitnesse.warehouse.OpentapsWarehouse;
import org.opentaps.fitnesse.webtools.OpentapsWebtools;

/**
 * Manages per Opentaps application contexts and provide base methods to login to each of Opentaps applications.
 * Provides the <code>loginTo[Application]</code> methods which loads application specific fixtures in the context,
 *  each test should start with a <code>login to [Application]</code>.
 * @see OpentapsCommon
 * @see OpentapsCatalog
 * @see OpentapsCrmsfa
 * @see OpentapsFinancials
 * @see OpentapsWarehouse
 * @see OpentapsWebtools
 */
public class OpentapsFixture extends BaseFixture {

    /** The Selenium instance, can be used for direct access to the Selenium API. */
    private static Selenium selenium;

    private static final int SELENIUM_PORT = 4444;
    private static final String DEFAULT_OPENTAPS_PORT = "8100";

    static {
        // initialize the Selenium server
        final String opentapsPort = System.getProperty("opentaps.application-port", DEFAULT_OPENTAPS_PORT);
        selenium = new DefaultSelenium("localhost", SELENIUM_PORT, "*opera", "http://localhost:" + opentapsPort);        
        selenium.start(); 
    }

    private OpentapsCatalog catalog;
    private OpentapsCrmsfa crmsfa;
    private OpentapsWarehouse warehouse;
    private OpentapsFinancials financials;
    private OpentapsWebtools webtools;

    /**
     * Creates a new <code>OpentapsFixture</code> instance an initialize the applications.
     */
    public OpentapsFixture() {
        super();
        catalog = new OpentapsCatalog(selenium, getContextManager());
        crmsfa = new OpentapsCrmsfa(selenium, getContextManager());
        warehouse = new OpentapsWarehouse(selenium, getContextManager());
        financials = new OpentapsFinancials(selenium, getContextManager());
        webtools = new OpentapsWebtools(selenium, getContextManager());
    }

    /**
     * Logs in the given target application with given user and password.
     * @param target the application to log into, eg: crmsfa
     * @param username the user name to use for login
     * @param password the password to use for login
     * @return <code>true</code> if the login succeeded
     */
    public boolean loginToWithUserWithPassword(String target, String username, String password) {
        String loginSuccess = "/" + target + "/control/login";
        String url = "/" + target + "/control/logout";
        selenium.open(url);
        selenium.type("USERNAME", username);
        selenium.type("PASSWORD", password);
        selenium.click("//input[@value='Login']");
        selenium.waitForPageToLoad("100000");
        return (selenium.getLocation().indexOf(loginSuccess) != -1);
    }

    private boolean loginToApplication(OpentapsApplicationInterface application) {
        return loginToWithUserWithPassword(application.getWebappRoot(), application.getDefaultLogin(), application.getDefaultPassword());
    }

    /**
     * Logs out of the current application.
     */
    public void logout() {
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    /**
     * Stops selenium, ends the test session killing the browser.
     */
    public void stop() {
        selenium.stop();
    }

    private void loadCrmsfa() {
        setSystemUnderTest(crmsfa);
    }

    private void loadCatalog() {
        setSystemUnderTest(catalog);
    }

    private void loadFinancials() {
        setSystemUnderTest(financials);
    }

    private void loadWarehouse() {
        setSystemUnderTest(warehouse);
    }

    private void loadWebtools() {
        setSystemUnderTest(webtools);
    }

    /**
     * Logs in CRMSFA.
     */
    public void loginToCrmsfa() {
        loginToApplication(crmsfa);
        loadCrmsfa();
    }

    /**
     * Logs in WebTools.
     */
    public void loginToWebtools() {
        loginToApplication(webtools);
        loadWebtools();
    }

    /**
     * Logs in Catalog.
     */
    public void loginToCatalog() {
        loginToApplication(catalog);
        loadCatalog();
    }

    /**
     * Logs in Financials.
     */
    public void loginToFinancials() {
        loginToApplication(financials);
        loadFinancials();
    }

    /**
     * Logs in Warehouse.
     */
    public void loginToWarehouse() {
        loginToApplication(warehouse);
        loadWarehouse();
    }


}
