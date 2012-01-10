/**
 * 
 */
package org.opentaps.gwt.common.client.suggest;

import org.opentaps.gwt.common.client.lookup.configuration.TeamMembersLookupConfiguration;

/**
 * @author soham.ray
 * A ComboBox that autocompletes Activity Status.
 */
public class TeamMembersAutocomplete extends EntityAutocomplete {
	/**
     * Creates a new <code>AssignedToAutocomplete</code> instance.
     * Unlike other autocompleters, this widget offer no input, just a list of the possible values.
     * @param fieldLabel the field label
     * @param name the field name used in the form
     * @param fieldWidth the field size in pixels
     */	
    public TeamMembersAutocomplete(String fieldLabel, String name, int fieldWidth) {
        super(fieldLabel, name, fieldWidth, TeamMembersLookupConfiguration.URL_SUGGEST_TEAM_MEMBERS, TeamMembersLookupConfiguration.OUT_PARTY_ID);
    }
}
