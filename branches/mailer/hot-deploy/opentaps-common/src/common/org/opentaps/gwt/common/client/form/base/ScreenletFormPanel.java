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

package org.opentaps.gwt.common.client.form.base;

import com.gwtext.client.core.Position;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;

import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.form.ServiceSuccessReader;
//import org.opentaps.gwt.crmsfa.accounts.client.form.configuration.QuickNewAccountConfiguration;
//import org.opentaps.gwt.crmsfa.leads.client.form.configuration.QuickNewLeadConfiguration;
//import org.opentaps.gwt.crmsfa.contacts.client.Entry;
//import org.opentaps.gwt.crmsfa.contacts.client.form.configuration.QuickNewContactConfiguration;;
/**
 * Provides utility methods to build a <code>FormPanel</code> to be presented as a screenlet.
 */
public class ScreenletFormPanel extends BaseFormPanel {

	 private Component target;
    /**
     * Constructor giving the <code>FormPanel</code> label position.
     * @param formPosition a <code>Position</code> value
     * @param title a <code>String</code> value
     */
    public ScreenletFormPanel(final Position formPosition, final String title) {
        super(formPosition);
        setTitle(title);
        setBaseCls(UtilUi.SCREENLET_STYLE);
        setTabCls(UtilUi.SCREENLET_HEADER_STYLE);
        setBodyStyle(UtilUi.SCREENLET_BODY_STYLE);
        setCollapsible(true);
        target=this;
    }

    /**
     * Default Constructor, defaults to labels positioned on the left side of the inputs and aligned on the right.
     * @param title a <code>String</code> value
     */
    public ScreenletFormPanel(final String title) {
        this(Position.RIGHT, title);
    }
    
   /* @Override
    public void onActionComplete(Form f, int httpStatus, String responseText) {
    	
    		super.onActionComplete(f, httpStatus, responseText);
   		    	
    	/** 
    	 * JSON response is of the form 
    	 * {"total":1,"response":[{"responseMessage":"success","partyId":"25292"}],"success":true}
    	 **/
    	/*	if(responseText.contains("contactPartyId")){
    			getPartyId(QuickNewContactConfiguration.OUT_CONTACT_PARTY_ID, QuickNewContactConfiguration.HIDDEN_QUICK_CONTACT_PARTY_ID, f ,responseText, "Contact");
    		}else if(responseText.contains("accountPartyId")){
    			//getPartyId(QuickNewAccountConfiguration.OUT_ACCOUNT_PARTY_ID, QuickNewAccountConfiguration.HIDDEN_QUICK_ACCOUNT_PARTY_ID, f ,responseText, "Account");
    		}else if(responseText.contains("leadPartyId")){
    			//getPartyId(QuickNewLeadConfiguration.OUT_LEAD_PARTY_ID, QuickNewLeadConfiguration.HIDDEN_QUICK_LEAD_PARTY_ID, f ,responseText , "Lead");
    		}
    	
    }*/
    
    protected String getShortMarkup(String id, String url) {  
        StringBuffer msg = new StringBuffer("<B><font color=\"#800000\">Created Successfully</font></B> ");
        msg.append("<p></p>");
        msg.append("<a class=\"messageLink\""+ url+"="+id);
		msg.append("\">Click To View </a>"); 
		msg.append("<B><p></B></p>");
		msg.append("<a href=\"#\" onclick=\"this.parentNode.parentNode.previousSibling.remove();this.parentNode.parentNode.remove();\">Close</a>");
		return msg.toString();
    }  
    
    protected void getIdentifierId(String configFormPartyType, String quickHiddenPartyId, Form f,  String responseText, String viewUrl){
    	final ServiceSuccessReader successReader = new ServiceSuccessReader(new  StringFieldDef(configFormPartyType));
    	successReader.readResponse(responseText);
    	String quickIdentifierId = successReader.getAsString(configFormPartyType); 
    	Field field = f.findField(quickHiddenPartyId);
    	if (field != null) {
    		field.setValue(quickIdentifierId);
    		target.getEl().mask(getShortMarkup(quickIdentifierId, viewUrl), false);
    	}
    }
}
