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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.Wait;

/**
 * Base fixture class, provides common methods that can be used in all application fixtures.
 */
public abstract class OpentapsCommon implements OpentapsApplicationInterface {

    /** The selenium server instance, used for direct access to the selenium API. */
    private Selenium selenium;
    /** The current context manager instance. */
    private ContextManager contextManager;

    /** The default timeout in millisecond, used for the wait methods. */
    public static final int TIMEOUT = 30000;
    /** The default timeout in millisecond as String, used for the wait methods. @see #TIMEOUT */
    public static final String TIMEOUT_S = new Integer(TIMEOUT).toString();

    /** Number of milliseconds in a second. */
    public static final int MILLISECS = 1000;

    /** The message to display in the debug alert boxes. @see #waitForAlert */
    public static final String DEBUG_CONFIRM_STRING = "Selenium Debug: Click OK to continue.";

    /** The error string to check for when looking for an error. */
    public static final String DEFAULT_CONFIRM_STRING = "Are you sure?";

    /** The error string to check for when looking for an error. */
    public static final String DEFAULT_ERROR_STRING = "The Following Errors Occurred";

    /** The default date format used to interpret or generate date strings. */
    public static final String DEFAULT_DATE_FORMAT = "MM/dd/yy";
    /** The current date format. */
    private DateFormat dateFormat;

    /** The admin user name. */
    public static final String ADMIN_USER = "admin";
    /** The admin password. */
    public static final String ADMIN_PASSWORD = "ofbiz";

    /** The UI elements map. */
    private final Map<String, String> uiElementsMap = new HashMap<String, String>();

    protected final void loadUiElements(UiElementInterface[] elements) {
        for (UiElementInterface element : elements) {
            if (uiElementsMap.containsKey(element.getName())) {
                throw new IllegalArgumentException("An element with the same name was already loaded, when loading UiElement: " + element + " " + element.getName() + " " + element.getLocator());
            }
            uiElementsMap.put(element.getName(), element.getLocator());
        }
    }

    /**
     * Creates a new <code>OpentapsCommon</code> instance.
     * @param selenium the selenium server instance
     * @param contextManager the context manager
     */
    public OpentapsCommon(Selenium selenium, ContextManager contextManager) {
        this.selenium = selenium;
        this.contextManager = contextManager;
        this.dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    }

    //------------------------------
    // OpentapsApplicationInterface
    //------------------------------

    /** {@inheritDoc} */
    public String getDefaultLogin() {
        return ADMIN_USER;
    }

    /** {@inheritDoc} */
    public String getDefaultPassword() {
        return ADMIN_PASSWORD;
    }

    //-----------
    // Accessors
    //-----------

    /**
     * Gets the current date format, defaults to <code>DEFAULT_DATE_FORMAT</code>.
     * @return a <code>DateFormat</code> value
     * @see #DEFAULT_DATE_FORMAT
     * @see #setDateFormat
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * Sets the current date format.
     * @param dateFormat a <code>DateFormat</code> value
     * @see #DEFAULT_DATE_FORMAT
     * @see #getDateFormat
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Gets the current <code>ContextManager</code>.
     * @return a <code>ContextManager</code> value
     */
    public ContextManager getContextManager() {
        return contextManager;
    }

    /**
     * Gets the selenium instance for direct access to the selenium API.
     * @return a <code>Selenium</code> value
     */
    public Selenium getSelenium() {
        return selenium;
    }

    //----------------
    // General methods
    //----------------

    /**
     * Translates the given key according to the current context.
     * @param key a <code>String</code> value, which can be a variable name or a normal string
     * @return if the key is not a variable or a variable not in the context, then it is returned as is, else the variable value is returned
     * @see #save
     */
    public String translate(String key) {
        return getContextManager().translateValue(key);
    }

    /**
     * Saves the given key / value pair to the current context.
     * @param key the variable name
     * @param value the variable value
     * @see #translate
     */
    public void save(String key, String value) {
        getContextManager().putValue(key, value);
    }

    /**
     * Saves the given key / value pair to the current context.
     * @param value the value name
     * @param key the variable name
     * @see #translate
     */
    public void saveValueIn(String value, String key) {
        save(key, valueFor(value));
    }

    /**
     * Saves the given key / value pair to the current context.
     * @param element an <code>UiElementInterface</code> value
     * @param key the variable name
     * @see #translate
     */
    protected void saveValueIn(UiElementInterface element, String key) {
        save(key, valueFor(element));
    }

    /**
     * Goes to the given URL.
     * @param url the URL to go to, eg: "/someApplication/control/somePage"
     */
    public void goToURL(String url) {
        selenium.open(url);
    }

    /**
     * Goes to the application main page.
     */
    public void goToMainPage() {
        goToURL(getWebappRoot() + "/control/main");
    }

    /**
     * Goes to the given application page.
     * @param page the page name, which is the last part of the URL
     */
    public void goToPage(String page) {
        goToURL(getWebappRoot() + "/control/" + page);
    }

    /**
     * Generates a unique ID.
     * This should be used when filling ID inputs to ensure the given ID is unique.
     * @return a unique ID based on the current time
     */
    public String generateId() {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * Selects a window by ID (javascript ID) or title as the target for further selenium commands.
     * @param name a window javascript ID, name or title, or <code>null</code> / "main" for the main window
     */
    public void selectWindow(String name) {
        if ("main".equals(name)) {
            selenium.selectWindow(null);
            return;
        }
        selenium.selectWindow(name);
    }

    /**
     * Sleeps for 1 second.
     * @exception InterruptedException if an error occurs
     */
    public void sleepOneSecond() throws InterruptedException {
        sleepForSeconds(1);
    }

    /**
     * Sleeps for the given number of seconds.
     * @param timeInSecs number of seconds to sleep
     * @exception InterruptedException if an error occurs
     */
    public void sleepForSeconds(int timeInSecs) throws InterruptedException {
        Thread.sleep(MILLISECS * timeInSecs);
    }

    /**
     * Lookup an element locator from the UI map.
     * @param key the UI element name
     * @return a selenium locator <code>String</code>
     * @see #uiElement
     */
    public String uiElementFor(String key) {
         return uiElementsMap.get(key);
    }

    /**
     * Returns a selenium locator <code>String</code> for a given UI name.
     * This method allows a non existing element name for input which it will return as is.
     * @param key the UI element name
     * @return a selenium locator <code>String</code> from the uiElementFor or the input if nothing was found
     * @see #uiElementFor
     */
    protected String uiElement(String key) {
        String uiElement = uiElementFor(key);
        if (uiElement == null) {
            return key;
        }
        return uiElement;
    }

    /**
     * Returns the selenium locator associated to the given UI Element.
     * @param element an <code>UiElementInterface</code> value
     * @return a selenium locator <code>String</code>
     */
    protected final String uiElement(UiElementInterface element) {
        return element.getLocator();
    }

    /**
     * Insert an index into an uiElement locator, this works by replacing the <code>String.Format</code> placeholder in the locator if present.
     * @param uiElement the base UI element name
     * @param index the index number that will be inserted into the locator
     * @return the indexed locator
     */
    protected String getIndexedUiElement(UiElementInterface uiElement, int index) {
        return String.format(uiElement(uiElement), index);
    }

    /**
     * Insert an index into an uiElement locator, this works by replacing the <code>String.Format</code> placeholder in the locator if present.
     * @param uiElement the base UI element name
     * @param index the index number that will be inserted into the locator
     * @return the indexed locator
     */
    protected String getIndexedUiElement(String uiElement, int index) {
        return String.format(uiElement(uiElement), index);
    }

    /**
     * Insert an index into an uiElement locator, this works by replacing the <code>String.Format</code> placeholder in the locator if present.
     * @param uiElement the base UI element name
     * @param index the index string that will be inserted into the locator
     * @return the indexed locator
     */
    protected String getIndexedUiElement(UiElementInterface uiElement, String index) {
        return String.format(uiElement(uiElement), index);
    }

    /**
     * Insert an index into an uiElement locator, this works by replacing the <code>String.Format</code> placeholder in the locator if present.
     * @param uiElement the base UI element name
     * @param index the index string that will be inserted into the locator
     * @return the indexed locator
     */
    protected String getIndexedUiElement(String uiElement, String index) {
        return String.format(uiElement(uiElement), index);
    }

    /**
     * Gets tomorrow's date as <code>String</code> formatted according to the current date format.
     * @return a <code>String</code> value
     * @see #getDateFormat
     * @see #getRelativeDateStr
     */
    public String getTomorrowsDateStr() {
        return getRelativeDateStr(1);
    }

    /**
     * Gets today's date as <code>String</code> formatted according to the current date format.
     * @return a <code>String</code> value
     * @see #getDateFormat
     * @see #getRelativeDateStr
     */
    public String getTodaysDateStr() {
        return getRelativeDateStr(0);
    }

    /**
     * Gets today' date offset by the given number of days as <code>String</code> formatted according to the current date format.
     * @param daysOffset number of days to add to today's date
     * @return a <code>String</code> value
     * @see #getDateFormat
     * @see #getTodaysDateStr
     * @see #getTomorrowsDateStr
     */
    public String getRelativeDateStr(int daysOffset) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, daysOffset);
        return dateFormat.format(cal.getTime());
    }

    //----------------------------
    // Type, select etc in a form
    //----------------------------

    // Click methods

    /**
     * Clicks on the given element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param elementName either a named element or a selenium locator <code>String</code>
     * @see #clickButton
     */
    public void clickOn(String elementName) {
        selenium.click(uiElement(elementName));
    }

    /**
     * Clicks on the given element.
     * @param element an <code>UiElementInterface</code> value
     * @see #clickButton
     */
    protected void clickOn(UiElementInterface element) {
        selenium.click(uiElement(element));
    }

    /**
     * Clicks the given button and waits for the page to load.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param buttonName either a named element or a selenium locator <code>String</code>
     * @see #clickOn
     * @see #waitForPageToLoad
     */
    public void clickButton(String buttonName) {
        clickOn(buttonName);
        waitForPageToLoad();
    }

    /**
     * Clicks the given button and waits for the page to load.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element an <code>UiElementInterface</code> value
     * @see #clickOn
     * @see #waitForPageToLoad
     */
    protected void clickButton(UiElementInterface element) {
        clickOn(element);
        waitForPageToLoad();
    }

    /**
     * Clicks on the named link and waits for the page to load.
     * @param linkName the link name as in the selenium locator string "link={linkName}" or a variable name
     * @see #clickButton
     * @see #clickItemLink
     * @see #waitForPageToLoad
     */
    public void clickLink(String linkName) {
        selenium.click("link=" + translate(linkName));
        waitForPageToLoad();
    }

    /**
     * Clicks on a specially formatted item link and waits for the page to load.
     * The item link is expected to have the label "({linkName})".
     * @param linkName the link name as in the selenium locator string "link=({linkName})" or a variable name
     * @see #clickButton
     * @see #clickLink
     * @see #waitForPageToLoad
     */
    public void clickItemLink(String linkName) {
        selenium.click("link=(" + translate(linkName) + ")");
        waitForPageToLoad();
    }

    // Combo Box methods

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param value the value to select in the combo box, as a selenium option locator <code>String</code>
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromLabel
     * @see #selectFromValue
     * @see #waitForPageToLoad
     */
    public void selectAndWaitFromValue(String combobox, String value) {
        selectFromValue(combobox, value);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param value the value to select in the combo box, as a selenium option locator <code>String</code>
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromLabel
     * @see #selectFromValue
     * @see #waitForPageToLoad
     */
    protected void selectAndWaitFromValue(UiElementInterface combobox, String value) {
        selectFromValue(combobox, value);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param label the value to select in the combo box, which is the option displayed label
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromValue
     * @see #selectFromLabel
     * @see #waitForPageToLoad
     */
    public void selectAndWaitFromLabel(String combobox, String label) {
        selectFromLabel(combobox, label);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param label the value to select in the combo box, which is the option displayed label
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromValue
     * @see #selectFromLabel
     * @see #waitForPageToLoad
     */
    protected void selectAndWaitFromLabel(UiElementInterface combobox, String label) {
        selectFromLabel(combobox, label);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param id the value to select in the combo box, which is the option ID
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromLabel
     * @see #selectAndWaitFromValue
     * @see #selectFromId
     * @see #waitForPageToLoad
     */
    public void selectAndWaitFromId(String combobox, String id) {
        selectFromId(combobox, id);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param id the value to select in the combo box, which is the option ID
     * @see #selectAndWaitFromIndex
     * @see #selectAndWaitFromLabel
     * @see #selectAndWaitFromValue
     * @see #selectFromId
     * @see #waitForPageToLoad
     */
    protected void selectAndWaitFromId(UiElementInterface combobox, String id) {
        selectFromId(combobox, id);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param itemIndex the value to select in the combo box, which is the option index (order in which it appears in the combo box)
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromLabel
     * @see #selectAndWaitFromValue
     * @see #selectFromIndex
     * @see #waitForPageToLoad
     */
    public void selectAndWaitFromIndex(String combobox, int itemIndex) {
        selectFromIndex(combobox, itemIndex);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box and waits for the page to load.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param itemIndex the value to select in the combo box, which is the option index (order in which it appears in the combo box)
     * @see #selectAndWaitFromId
     * @see #selectAndWaitFromLabel
     * @see #selectAndWaitFromValue
     * @see #selectFromIndex
     * @see #waitForPageToLoad
     */
    protected void selectAndWaitFromIndex(UiElementInterface combobox, int itemIndex) {
        selectFromIndex(combobox, itemIndex);
        waitForPageToLoad();
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param option the value to select in the combo box, as a selenium option locator <code>String</code>
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    public void selectFromOption(String combobox, String option) {
        selenium.select(uiElement(combobox), option);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param option the value to select in the combo box, as a selenium option locator <code>String</code>
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    protected void selectFromOption(UiElementInterface combobox, String option) {
        selenium.select(uiElement(combobox), option);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param value the value to select in the combo box, which is the option value
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    public void selectFromValue(String combobox, String value) {
        selectFromOption(combobox, "value=" + value);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param value the value to select in the combo box, which is the option value
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    protected void selectFromValue(UiElementInterface combobox, String value) {
        selectFromOption(combobox, "value=" +  value);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param label the value to select in the combo box, which is the option displayed label
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    public void selectFromLabel(String combobox, String label) {
        selectFromValue(combobox, "label=" + label);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param label the value to select in the combo box, which is the option displayed label
     * @see #selectFromId
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexValue
     */
    protected void selectFromLabel(UiElementInterface combobox, String label) {
        selectFromValue(combobox, "label=" + label);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param id the value to select in the combo box, which is the option ID
     * @see #selectFromIndex
     * @see #selectFromValue
     * @see #selectFromWithIndexId
     */
    public void selectFromId(String combobox, String id) {
        selectFromValue(combobox, "id=" + id);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param id the value to select in the combo box, which is the option ID
     * @see #selectFromIndex
     * @see #selectFromValue
     * @see #selectFromWithIndexId
     */
    protected void selectFromId(UiElementInterface combobox, String id) {
        selectFromValue(combobox, "id=" + id);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param itemIndex the value to select in the combo box, which is the option index (order in which it appears in the combo box)
     * @see #selectFromId
     * @see #selectFromValue
     * @see #selectFromWithIndexValue
     */
    public void selectFromIndex(String combobox, int itemIndex) {
        selectFromValue(combobox, "index=" + itemIndex);
    }

    /**
     * Selects a value in the given combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox UI element
     * @param itemIndex the value to select in the combo box, which is the option index (order in which it appears in the combo box)
     * @see #selectFromId
     * @see #selectFromValue
     * @see #selectFromWithIndexValue
     */
    protected void selectFromIndex(UiElementInterface combobox, int itemIndex) {
        selectFromValue(combobox, "index=" + itemIndex);
    }

    /**
     * Selects a value in the given indexed combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox base UI element name
     * @param index the index of the UI element
     * @param value the value to select in the combo box, as a selenium option locator <code>String</code>
     * @see #selectFromValue
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexIndex
     * @see #selectFromWithIndexLabel
     */
    public void selectFromWithIndexValue(String combobox, int index, String value) {
        selenium.select(getIndexedUiElement(combobox, index), value);
    }

    /**
     * Selects a value in the given indexed combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox base UI element name
     * @param index the index of the UI element
     * @param label the value to select in the combo box, which is the option displayed label
     * @see #selectFromLabel
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexIndex
     * @see #selectFromWithIndexValue
     */
    public void selectFromWithIndexLabel(String combobox, int index, String label) {
        selenium.select(getIndexedUiElement(combobox, index), "label=" + label);
    }

    /**
     * Selects a value in the given indexed combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox base UI element name
     * @param index the index of the UI element
     * @param id the value to select in the combo box, which is the option ID
     * @see #selectFromId
     * @see #selectFromWithIndexIndex
     * @see #selectFromWithIndexLabel
     * @see #selectFromWithIndexValue
     */
    public void selectFromWithIndexId(String combobox, int index, String id) {
        selenium.select(getIndexedUiElement(combobox, index), "id=" + id);
    }

    /**
     * Selects a value in the given indexed combo box.
     * The combo box is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param combobox the combobox base UI element name
     * @param index the index of the UI element
     * @param itemIndex the value to select in the combo box, which is the option index (order in which it appears in the combo box)
     * @see #selectFromIndex
     * @see #selectFromWithIndexId
     * @see #selectFromWithIndexLabel
     * @see #selectFromWithIndexValue
     */
    public void selectFromWithIndexIndex(String combobox, int index, int itemIndex) {
        selenium.select(getIndexedUiElement(combobox, index), "index=" + itemIndex);
    }

    /**
     * Selects an option in a multi-select input.
     * @param element the multi-select UI element name
     * @param value the value to select as
     * @see #addToMultiSelectLabel
     */
    public void addToMultiSelectValue(String element, String value) {
        selenium.addSelection(uiElement(element), value);
    }

    /**
     * Selects an option in a multi-select input.
     * @param element the multi-select UI element name
     * @param label the value to select as, which is the option label displayed
     * @see #addToMultiSelectValue
     */
    public void addToMultiSelectLabel(String element, String label) {
        selenium.addSelection(uiElement(element), "label=" + label);
    }

    // Type methods

    /**
     * Types the given value in the given element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @param value the value to enter as text or variable name
     * @see #typeTextInWithIndexValue
     */
    public void typeTextInValue(String element, String value) {
        selenium.type(uiElement(element), translate(value));
    }

    /**
     * Types the given value in the given element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @param value the value to enter as text or variable name
     * @see #typeTextInWithIndexValue
     */
    protected void typeTextInValue(UiElementInterface element, String value) {
        selenium.type(uiElement(element), translate(value));
    }

    /**
     * Types the given value in the given indexed element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element base name
     * @param index the index of the UI element
     * @param value the value to enter as text or variable name
     * @see #typeTextInValue
     */
    public void typeTextInWithIndexValue(String element, int index, String value) {
        selenium.type(getIndexedUiElement(element, index), translate(value));
    }

    /**
     * Types today's date in the given element, and save the typed date in the local variable <code>#defaultDate</code>.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @see #typeTodaysDateInWithKey
     * @see #typeTomorrowsDateIn
     */
    public void typeTodaysDateIn(String element) {
        typeTodaysDateInWithKey(element, "#defaultDate");
    }

    /**
     * Types tomorrow's date in the given element, and save the typed date in the local variable <code>#defaultDate</code>.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @see #typeTomorrowsDateInWithKey
     * @see #typeTodaysDateIn
     */
    public void typeTomorrowsDateIn(String element) {
        typeTomorrowsDateInWithKey(element, "#defaultDate");
    }

    /**
     * Types today's date in the given element, and save the typed date in variable.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @param key the variable name to save the date into
     * @see #typeTodaysDateIn
     * @see #typeTomorrowsDateInWithKey
     */
    public void typeTodaysDateInWithKey(String element, String key) {
        typeTextInValue(element, getRelativeDateStr(0));
        contextManager.putValue(key, getRelativeDateStr(0));
    }

    /**
     * Types tomorrow's date in the given element, and save the typed date in variable.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the input element name
     * @param key the variable name to save the date into
     * @see #typeTodaysDateInWithKey
     * @see #typeTomorrowsDateIn
     */
    public void typeTomorrowsDateInWithKey(String element, String key) {
        typeTextInValue(element, getTomorrowsDateStr());
        contextManager.putValue(key, getTomorrowsDateStr());
    }

    // Radio and check box methods

    /**
     * Selects a check box or a radio button UI element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box or radio element name
     * @see #selectCheckboxOrRadioWithIndex
     */
    public void selectCheckboxOrRadio(String element) {
        selenium.check(uiElement(element));
    }

    /**
     * Selects an indexed check box or a radio button UI element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box or radio element name
     * @param index the index of the UI element
     * @see #selectCheckboxOrRadio
     * @see #unselectCheckboxWithIndex
     */
    public void selectCheckboxOrRadioWithIndex(String element, int index) {
        selenium.check(getIndexedUiElement(element, index));
    }

    /**
     * Unselects a check box UI element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box element name
     * @see #selectCheckboxOrRadio
     */
    public void unselectCheckbox(String element) {
        selenium.uncheck(uiElement(element));
    }

    /**
     * Unselects an indexed check box UI element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box element name
     * @param index the index of the UI element
     * @see #selectCheckboxOrRadioWithIndex
     */
    public void unselectCheckboxWithIndex(String element, int index) {
        selenium.uncheck(getIndexedUiElement(element, index));
    }

    //-------------
    // Read values
    //-------------

    /**
     * Gets the selected option label in the given combo box.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the combo box element name
     * @return the selected label
     */
    public String selectedValueFor(String element) {
        return selenium.getSelectedLabel(uiElement(element));
    }

    /**
     * Gets the selected option ID in the given combo box.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the combo box element name
     * @return the selected ID
     */
    public String selectedIdFor(String element) {
        return selenium.getSelectedId(uiElement(element));
    }

    /**
     * Gets the selected option index in the given combo box.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the combo box element name
     * @return the selected index
     */
    public String selectedIndexFor(String element) {
        return selenium.getSelectedIndex(uiElement(element));
    }

    /**
     * Gets the value in the given input element (an element with a "value" attribute).
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return the content of the element value attribute
     */
    public String valueFor(String element) {
        return selenium.getValue(uiElement(element));
    }

    /**
     * Gets the value in the given input element (an element with a "value" attribute).
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element an <code>UiElementInterface</code> value
     * @return the content of the element value attribute
     */
    protected String valueFor(UiElementInterface element) {
        return selenium.getValue(uiElement(element));
    }

    /**
     * Gets the value in the given indexed input element (an element with a "value" attribute).
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @param index the index of the UI element
     * @return the content of the element value attribute
     */
    public String valueForWithIndex(String element, int index) {
        return valueFor(getIndexedUiElement(element, index));
    }

    /**
     * Gets the text in the given element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return the element text
     */
    public String textFor(String element) {
        return selenium.getText(uiElement(element));
    }

    /**
     * Gets the text in the given indexed input element.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @param index the index of the UI element
     * @return the element text
     */
    public String textForWithIndex(String element, int index) {
        return textFor(getIndexedUiElement(element, index));
    }

    //--------------------------------------------------------------------------------------------------------------
    // Check stuff, this the core testing methods, returns true and the test pass, returns false and the test fails
    //--------------------------------------------------------------------------------------------------------------


    /**
     * Checks the given element is not on the page.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementAbsent(String element) {
        return !checkElementPresent(uiElement(element));
    }

    /**
     * Checks the given indexed element is not on the page.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexAbsent(String uiElement, int index) {
        return !checkElementWithIndexPresent(uiElement, index);
    }

    /**
     * Checks the given element exists on the page.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementPresent(String element) {
        return selenium.isElementPresent(uiElement(element));
    }

    /**
     * Checks the given indexed element exists on the page.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexPresent(String uiElement, int index) {
        return checkElementPresent(getIndexedUiElement(uiElement, index));
    }

    /**
     * Checks the given element is enabled and can be edited.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementEnabled(String element) {
        return selenium.isEditable(uiElement(element));
    }

    /**
     * Checks the given element is enabled and can be edited.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexEnabled(String uiElement, int index) {
        return selenium.isEditable(getIndexedUiElement(uiElement, index));
    }

    /**
     * Checks the given element is disabled and cannot be edited.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementDisabled(String element) {
        return !checkElementEnabled(element);
    }

    /**
     * Checks the given element is disabled and cannot be edited.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexDisabled(String uiElement, int index) {
        return !checkElementWithIndexEnabled(uiElement, index);
    }

    /**
     * Checks the given input element has a value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasValue(String element) {
        String elementText = valueFor(element);
        return (elementText != null && elementText.trim().length() > 0);
    }

    /**
     * Checks the given input element has a value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexHasValue(String uiElement, int index) {
        return checkElementHasValue(getIndexedUiElement(uiElement, index));
    }

    /**
     * Checks the given input element has the given value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasValue(String element, String value) {
        String elementText = valueFor(element);
        String textValue = translate(value);
        return elementText.equals(textValue);
    }

    /**
     * Checks the given input element has the given value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexHasValue(String uiElement, int index, String value) {
        return checkElementHasValue(getIndexedUiElement(uiElement, index), value);
    }

    /**
     * Checks the given input element contains the given value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementContainsValue(String element, String value) {
        String elementText = valueFor(element);
        String textValue = translate(value);
        return elementText.contains(textValue);
    }

    /**
     * Checks the given input element contains the given value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexContainsValue(String uiElement, int index, String value) {
        return checkElementContainsValue(getIndexedUiElement(uiElement, index), value);
    }

    /**
     * Checks the given element has a text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasText(String element) {
        String elementText = textFor(element);
        return (elementText != null && elementText.trim().length() > 0);
    }

    /**
     * Checks the given element has the given text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasText(String element, String value) {
        String elementText = textFor(element);
        String textValue = translate(value);
        return elementText.equals(textValue);
    }

    /**
     * Checks the given element has the given text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexHasText(String uiElement, int index, String value) {
        return checkElementHasText(getIndexedUiElement(uiElement, index), value);
    }

    /**
     * Checks the given element contains the given text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementContainsText(String element, String value) {
        String elementText = textFor(element);
        String textValue = translate(value);
        return elementText.contains(textValue);
    }

    /**
     * Checks the given element contains the given text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param uiElement the element base name
     * @param index the index of the UI element
     * @param value the value to test for as text or variable name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementWithIndexContainsText(String uiElement, int index, String value) {
        return checkElementContainsText(getIndexedUiElement(uiElement, index), value);
    }

    /**
     * Checks the given element has tomorrow's date as text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasTomorrowsDate(String element) {
        String value = textFor(element);
        return value.equals(getTomorrowsDateStr());
    }

    /**
     * Checks the given element has today's date as text value.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * The value can be either a text string or a variable.
     * @param element the element name
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasTodaysDate(String element) {
        String value = textFor(element);
        return value.equals(getTodaysDateStr());
    }

    /**
     * Checks the given text or variable is present on the page.
     * @param value a text string or a variable
     * @return a <code>boolean</code> value
     */
    public boolean checkTextPresent(String value) {
        return selenium.isTextPresent(translate(value));
    }

    /**
     * Checks the given text or variable is absent on the page.
     * @param value a text string or a variable
     * @return a <code>boolean</code> value
     */
    public boolean checkTextAbsent(String value) {
        return !checkTextPresent(value);
    }

    /**
     * Checks that there is no error message displayed on the page.
     * @return a <code>boolean</code> value
     */
    public boolean checkErrorAbsent() {
        return !checkErrorPresent();
    }

    /**
     * Checks that there is an error message displayed on the page.
     * @return a <code>boolean</code> value
     */
    public boolean checkErrorPresent() {
        return selenium.isTextPresent(DEFAULT_ERROR_STRING);
    }

    /**
     * Checks the given select element has the given option in its list of available options.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the combo box element name
     * @param optionLabel an option label to check for
     * @return a <code>boolean</code> value
     */
    public boolean checkSelectHasOption(String element, String optionLabel) {
        String[] options = selenium.getSelectOptions(uiElement(element));
        return Arrays.asList(options).contains(optionLabel);
    }

    /**
     * Checks the given select element does not have the given option in its list of available options.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the combo box element name
     * @param optionLabel an option label to check for
     * @return a <code>boolean</code> value
     */
    public boolean checkSelectDoesNotHaveOption(String element, String optionLabel) {
        return !checkSelectHasOption(element, optionLabel);
    }

    /**
     * Checks the given check box or radio is checked.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box or radio element name
     * @return a <code>boolean</code> value
     */
    public boolean checkCheckboxOrRadioIsChecked(String element) {
        return selenium.isChecked(uiElement(element));
    }

    /**
     * Checks the given check box or radio is not checked.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the check box or radio element name
     * @return a <code>boolean</code> value
     */
    public boolean checkCheckboxOrRadioIsNotChecked(String element) {
        return !checkCheckboxOrRadioIsChecked(element);
    }

    /**
     * Checks the given multi-select or combo box has the given option ID selected.
     * @param element the multi-select or combo box UI element name
     * @param id the value id to check if selected
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasSelectedId(String element, String id) {
        String[] values = selenium.getSelectedIds(element);
        return Arrays.asList(values).contains(id);
    }

    /**
     * Checks the given multi-select or combo box has the given option label selected.
     * @param element the multi-select or combo box UI element name
     * @param label the value to check if selected, which is the option label displayed
     * @return a <code>boolean</code> value
     */
    public boolean checkElementHasSelectedLabel(String element, String label) {
        String[] values = selenium.getSelectedLabels(element);
        return Arrays.asList(values).contains(label);
    }

    /**
     * Checks the default confirmation pop up appeared, and confirms it.
     * @return a <code>boolean</code> value
     * @see #DEFAULT_CONFIRM_STRING
     * @see #confirm(String)
     */
    public boolean confirm() {
        return confirm(DEFAULT_CONFIRM_STRING);
    }

    /**
     * Checks a confirmation pop up appeared with the given text, and confirms it.
     * @param text the text of the confirm pop up
     * @return a <code>boolean</code> value
     * @see #confirm()
     */
    public boolean confirm(String text) {
        String confirmation = selenium.getConfirmation();
        return text.equals(confirmation);
    }

    //--------------------------------------------------------------------------------------------
    // Wait methods, useful when AJAX is involved such as to wait for the paginated lists to load
    //--------------------------------------------------------------------------------------------

    /**
     * Waits for the page to load, throws an exception in selenium if waiting more than <code>TIMEOUT</code>.
     * @see #TIMEOUT
     */
    public void waitForPageToLoad() {
        selenium.waitForPageToLoad(TIMEOUT_S);
    }

    /**
     * Waits for the given pop up to load, throws an exception in selenium if waiting more than <code>TIMEOUT</code>.
     * @param popupName the pop up name which is the javascript name.
     * @see #TIMEOUT
     */
    public void waitForPopUp(String popupName) {
        selenium.waitForPopUp(popupName, TIMEOUT_S);
    }

    /**
     * Waits for the given element to be visible, throws an exception in selenium if waiting more than <code>TIMEOUT</code>.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @exception InterruptedException if an error occurs
     * @see #TIMEOUT
     */
    public void waitForElementToBeVisible(final String element) throws InterruptedException {
        waitForElementToBeVisibleWithoutLookup(uiElement(element));
    }

    /**
     * Waits for the given indexed element to be visible, throws an exception in selenium if waiting more than <code>TIMEOUT</code>.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @param index the index of the UI element
     * @exception InterruptedException if an error occurs
     * @see #TIMEOUT
     */
    public void waitForElementToBeVisibleWithIndex(final String element, final int index) throws InterruptedException {
        waitForElementToBeVisibleWithoutLookup(getIndexedUiElement(element, index));
    }

    private void waitForElementToBeVisibleWithoutLookup(final String elementName) throws InterruptedException {
        Wait waitForDropdown = new Wait() {
            @Override public boolean until() {
                return selenium.isElementPresent(elementName) && selenium.isVisible(elementName);
            }
        };
        waitForDropdown.wait(elementName + "not found", TIMEOUT);
    }

    /**
     * Waits for the given element to be have the given value, throws an exception in selenium if waiting more than <code>TIMEOUT</code>.
     * The element is looked up via {@link #uiElement} and can be either a named element or a selenium locator <code>String</code>.
     * @param element the element name
     * @param value the value to wait for as text or variable name
     * @exception InterruptedException if an error occurs
     * @see #TIMEOUT
     */
    public void waitForElementToHaveValue(final String element, final String value) throws InterruptedException {
        final String elementName = uiElement(element);
        final String valueToFind = translate(value);
        Wait waitForDropdown = new Wait() {
            @Override public boolean until() {
                return selenium.getValue(elementName).equals(valueToFind);
            }
        };
        waitForDropdown.wait(elementName + " does not have value " + valueToFind, TIMEOUT);
    }

    /**
     * Displays a pop up and waits for the user to confirm it, used only for debugging.
     */
    public void waitForAlert() {
        selenium.getEval("alert('" + DEBUG_CONFIRM_STRING + "')");
    }
}
