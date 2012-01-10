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

import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;

/**
 * A ComboBox that autocompletes Party Classifications.
 */

public class ModelOfInterestAutocomplete extends EntityAutocomplete {

    /**
     * Creates a new <code>ModelOfInterestAutocomplete</code> instance.
     * Unlike other autocompleters, this widget offer no input, just a list of the possible values.
     * @param fieldLabel the field label
     * @param name the field name used in the form
     * @param fieldWidth the field size in pixels
     */
    public ModelOfInterestAutocomplete(String fieldLabel, String name, int fieldWidth) {
    	super(fieldLabel, name, fieldWidth, PartyLookupConfiguration.URL_SUGGEST_MODEL_OF_INTERESTS, PartyLookupConfiguration.OUT_ENUMERATION_DESCRIPTION);
    }
    
    public ModelOfInterestAutocomplete(String fieldLabel, String name, String url, int fieldWidth) {
    	super(fieldLabel, name, fieldWidth, url, PartyLookupConfiguration.OUT_ENUMERATION_DESCRIPTION);
    	this.makeStatic();
    }
}
