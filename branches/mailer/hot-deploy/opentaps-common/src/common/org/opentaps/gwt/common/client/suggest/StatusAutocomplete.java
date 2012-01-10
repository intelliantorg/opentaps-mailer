/**
 * 
 */
package org.opentaps.gwt.common.client.suggest;

import org.opentaps.gwt.common.client.lookup.configuration.StatusLookupConfiguration;

/**
 * @author soham.ray
 * A ComboBox that autocompletes Activity Status.
 */
public class StatusAutocomplete extends EntityStaticAutocomplete {
	/**
     * Creates a new <code>StatusAutocomplete</code> instance.
     * Unlike other autocompleters, this widget offer no input, just a list of the possible values.
     * @param fieldLabel the field label
     * @param name the field name used in the form
     * @param fieldWidth the field size in pixels
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
     */
    public StatusAutocomplete(String fieldLabel, String name, int fieldWidth){    	
        super(fieldLabel, name, fieldWidth, StatusLookupConfiguration.URL_SUGGEST_ACTIVITY_STATUS , StatusLookupConfiguration.OUT_DESCRIPTION);
    }
}
