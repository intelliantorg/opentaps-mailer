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

package org.opentaps.gwt.common.client.form;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.form.base.SubFormPanel;
import org.opentaps.gwt.common.client.listviews.PartyListView;
import org.opentaps.gwt.common.client.suggest.CountryAutocomplete;
import org.opentaps.gwt.common.client.suggest.PartyClassificationAutocomplete;
import org.opentaps.gwt.common.client.suggest.StateAutocomplete;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.TextField;


/**
 * Base class for the common Find party form + list view pattern.
 *
 * @see org.opentaps.gwt.crmsfa.accounts.client.form.FindAccountsForm
 * @see org.opentaps.gwt.crmsfa.contacts.client.form.FindContactsForm
 * @see org.opentaps.gwt.crmsfa.leads.client.form.FindLeadsForm
 * @see org.opentaps.gwt.crmsfa.partners.client.form.FindPartnersForm
 * @see org.opentaps.gwt.purchasing.suppliers.client.form.FindSuppliersForm
 */
public abstract class FindPartyForm extends FindEntityForm<PartyListView> {

    //private final SubFormPanel filterByIdTab;
    //private final TextField idInput;

    //private final SubFormPanel filterByNameTab;

    //private final SubFormPanel filterByPhoneTab;
    //private final PhoneNumberField phoneInput;

	protected final SubFormPanel filterByBasicTab;
    
    protected final SubFormPanel filterByAdvancedTab;
    protected final PartyClassificationAutocomplete classificationInput;
    protected final TextField addressInput;
    protected final TextField cityInput;
    protected final CountryAutocomplete countryInput;
    protected final StateAutocomplete stateInput;
    protected final TextField postalCodeInput;

    /**
     * Constructor.  The order in which the tab widgets are created reflects the
     * order in which they appear from left to right.
     *
     * @param idLabel the label for the main column of the list view, the column listing the party identifiers
     * @param findButtonLabel the label for the find button of the filter form
     */
    public FindPartyForm(String idLabel, String findButtonLabel) {
        super(findButtonLabel);

        // Filter by Name
        //filterByNameTab = getMainForm().addTab(UtilUi.MSG.findByName());
        //buildFilterByNameTab(filterByNameTab);

        // Filter by ID
        //filterByIdTab = getMainForm().addTab(UtilUi.MSG.findById());
        //idInput = new TextField(idLabel, "id", getInputLength());
        //filterByIdTab.addField(idInput);

        // Filter by Phone Number
        //filterByPhoneTab = getMainForm().addTab(UtilUi.MSG.findByPhone());
        //phoneInput = new PhoneNumberField(UtilUi.MSG.phoneNumber(), getLabelLength(), getInputLength());
        //filterByPhoneTab.addField(phoneInput);

        // Filter by Basic
        filterByBasicTab = getMainForm().addTab(UtilUi.MSG.findByBasic());
        buildFilterByBasicTab(filterByBasicTab);
        
        classificationInput = new PartyClassificationAutocomplete(UtilUi.MSG.classification(), "classification", getInputLength());
        addressInput = new TextField(UtilUi.MSG.address(), "address", getInputLength());
        cityInput = new TextField(UtilUi.MSG.city(), "city", getInputLength());
        postalCodeInput = new TextField(UtilUi.MSG.postalCode(), "postalCode", getInputLength());
        countryInput = new CountryAutocomplete(UtilUi.MSG.country(), "country", getInputLength());
        stateInput = new StateAutocomplete(UtilUi.MSG.stateOrProvince(), "state", countryInput, getInputLength());

        // Build the filter by advanced tab
        filterByAdvancedTab = getMainForm().addTab(UtilUi.MSG.findByAdvanced());
        buildFilterByAdvancedTab(filterByAdvancedTab);        
    }

//    /**
//     * Builds the tab in the filter form used to filter by name.
//     * Since different party types use different fields for names, the implementation will be
//     * defined in the sub classes.
//     * @param p the tab <code>SubFormPanel</code> that this method should populate
//     */
//    protected abstract void buildFilterByNameTab(SubFormPanel p);
//
//    /**
//     * Filters by the name fields.
//     * Since different party types use different fields for names, the implementation will be
//     * defined in the sub classes.
//     */
//    protected abstract void filterByNames();
    
    protected abstract void buildFilterByBasicTab(SubFormPanel p);

    /**
     * Basic advanced tab contains all the filters created in constructor.
     * TODO this isn't ideal but gets the job done.
     * @param p the tab <code>SubFormPanel</code> that this method should populate
     */
    protected void buildFilterByAdvancedTab(SubFormPanel p) {
        filterByAdvancedTab.addField(classificationInput);
        filterByAdvancedTab.addField(addressInput);
        filterByAdvancedTab.addField(cityInput);
        filterByAdvancedTab.addField(countryInput);
        filterByAdvancedTab.addField(stateInput);
        filterByAdvancedTab.addField(postalCodeInput);
    }

//    protected void filterById() {
//        getListView().filterByPartyId(idInput.getText());
//    }
//
//    protected void filterByPhoneNumber() {
//        getListView().filterByPhoneCountryCode(phoneInput.getCountryCode());
//        getListView().filterByPhoneAreaCode(phoneInput.getAreaCode());
//        getListView().filterByPhoneNumber(phoneInput.getNumber());
//    }
    
    protected abstract void filterByBasic();
    
    protected void filterByAdvanced() {
        getListView().filterByClassification(classificationInput.getText());
        getListView().filterByAddress(addressInput.getText());
        getListView().filterByCity(cityInput.getText());
        getListView().filterByCountry(countryInput.getText());
        getListView().filterByStateProvince(stateInput.getText());
        getListView().filterByPostalCode(postalCodeInput.getText());
    }

    @Override protected void filter() {
        getListView().clearFilters();
        Panel p = getMainForm().getTabPanel().getActiveTab();
//        if (p == filterByIdTab) {
//            filterById();
//        } else if (p == filterByNameTab) {
//            filterByNames();
//        } else if (p == filterByPhoneTab) {
//            filterByPhoneNumber();
//        }
        if (p == filterByBasicTab) {
          filterByBasic();
        } else if (p == filterByAdvancedTab) {
            filterByAdvanced();
        }
        getListView().applyFilters();
    }

}
