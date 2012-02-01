/*
 * Copyright (c) 2007 - 2009 Open Source Strategies, Inc.
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
package org.opentaps.gwt.common.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
/**
 * Defines UncaughtExceptionHandler in GWT Base Entry.
 */
public abstract class BaseEntry implements EntryPoint {
    static {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable throwable) {
              String text = "Uncaught exception: ";
              while (throwable != null) {
                StackTraceElement[] stackTraceElements = throwable.getStackTrace();
                text += throwable.toString() + "\n";
                for (int i = 0; i < stackTraceElements.length; i++) {
                  text += "    at " + stackTraceElements[i] + "\n";
                }
                throwable = throwable.getCause();
                if (throwable != null) {
                  text += "Caused by: " + throwable.getCause();
                }
              }
              UtilUi.errorMessage(text);
            }
          });
        }
}