/**
 * 
 */
package org.opentaps.gwt.common.client.suggest;

import org.opentaps.gwt.common.client.lookup.configuration.LeadSourcesLookupConfiguration;

/**
 * @author soham.ray
 * A ComboBox that autocompletes Lead Source.
 */
public class LeadSourcesAutocomplete extends EntityAutocomplete{
	/**
     * Creates a new <code>LeadSourceAutocomplete</code> instance.
     * Unlike other autocompleters, this widget offer no input, just a list of the possible values.
     * @param fieldLabel the field label
     * @param name the field name used in the form
     * @param fieldWidth the field size in pixels
     */
    public LeadSourcesAutocomplete(String fieldLabel, String name, int fieldWidth) {
        super(fieldLabel, name, fieldWidth, LeadSourcesLookupConfiguration.URL_SUGGEST_LEAD_SOURCES, LeadSourcesLookupConfiguration.OUT_SOURCE_ID);
    }
}
