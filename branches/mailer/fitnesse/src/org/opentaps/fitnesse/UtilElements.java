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
 * An utility class with methods for defining some common selenium locators.
 */
public final class UtilElements {

    private UtilElements() { }

    /**
     * Gets the locator for the application top tab bar, for the given tab index.
     * @param index an <code>int</code> value
     * @return a selenium locator
     */
    public static String mainTabByIndex(int index) {
        return "//ul[@class=\"sectionTabBar\"]/li[" + index + "]/span/a";
    }

    /**
     * Gets the locator for the left bar shortcuts, for the given shortcut index.
     * @param index an <code>int</code> value
     * @return a selenium locator
     */
    public static String leftBarShortcutByIndex(int index) {
        return "//ul[@class=\"shortcuts\"]/li[" + index + "]/a";
    }

    /**
     * Gets the for input locator for the given form and input name.
     * @param form the form locator, eg: <code>//form[@name="createAccountForm"]</code>
     * @param inputName the input name
     * @return a selenium locator
     */
    public static String formInput(UiElementInterface form, String inputName) {
        return formInput(form, inputName, "input");
    }

    /**
     * Gets the for input locator for the given form and input name.
     * @param form the form locator, eg: <code>//form[@name="createAccountForm"]</code>
     * @param inputName the input name
     * @param inputTag the input tag, eg: <code>input</code> or <code>select</code> ...
     * @return a selenium locator
     */
    public static String formInput(UiElementInterface form, String inputName, String inputTag) {
        return form.getLocator() + "//" + inputTag + "[@name=\"" + inputName + "\"]";
    }

    /**
     * Gets the for input locator for the given form submit button.
     * @param form the form locator, eg: <code>//form[@name="createAccountForm"]</code>
     * @return a selenium locator
     */
    public static String formSubmit(UiElementInterface form) {
        return form.getLocator() + "//input[@type=\"submit\"]";
    }

}
