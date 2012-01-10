/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
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

package org.opentaps.gwt.common.client.suggest;

import org.opentaps.gwt.common.client.lookup.configuration.ResourceLookupConfiguration;

import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TimeField;

/**
 * A ComboBox that autocompletes Party Classifications.
 */
public class CarAutocomplete extends EntityAutocomplete {

	public CarAutocomplete(String fieldLabel, String name, int fieldWidth) {
	   	super(fieldLabel, name, fieldWidth, ResourceLookupConfiguration.URL_SUGGEST_RES_CARS, ResourceLookupConfiguration.INOUT_PARTY_ID);
	   }
		
	    /**
	     * Creates a new <code>ModelOfInterestAutocomplete</code> instance.
	     * Unlike other autocompleters, /this widget offer no input, just a list of the possible values.
	     * @param fieldLabel the field label
	     * @param name the field name used in the form
	     * @param fieldWidth the field size in pixels
	     */
	    public CarAutocomplete(String fieldLabel, String name, DateField estimatedStartDateField, TimeField estimatedStartTimeField, TextField duration, int fieldWidth) {
	    	 this(fieldLabel, name, fieldWidth);
	    	 linkToDateField(estimatedStartDateField, estimatedStartTimeField, duration, ResourceLookupConfiguration.INOUT_ESTIMATED_START_DATE_FROM, ResourceLookupConfiguration.INOUT_ESTIMATED_START_TIME_FROM, ResourceLookupConfiguration.INOUT_DURATION);
	    }
}
