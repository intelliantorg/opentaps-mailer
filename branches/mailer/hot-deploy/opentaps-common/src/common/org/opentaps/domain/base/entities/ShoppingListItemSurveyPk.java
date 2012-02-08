package org.opentaps.domain.base.entities;

/*
* Copyright (c) 2008 - 2009 Open Source Strategies, Inc.
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

// DO NOT EDIT THIS FILE!  THIS IS AUTO GENERATED AND WILL GET WRITTEN OVER PERIODICALLY WHEN THE DATA MODEL CHANGES
// EXTEND THIS CLASS INSTEAD.

import java.io.Serializable;
import javax.persistence.*;

import java.lang.String;

@Embeddable
public class ShoppingListItemSurveyPk implements Serializable {
    @Column(name="SHOPPING_LIST_ID")
    private String shoppingListId;
    @Column(name="SHOPPING_LIST_ITEM_SEQ_ID")
    private String shoppingListItemSeqId;
    @Column(name="SURVEY_RESPONSE_ID")
    private String surveyResponseId;
    
    /**
     * Auto generated value setter.
     * @param shoppingListId the shoppingListId to set
     */
    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
    /**
     * Auto generated value setter.
     * @param shoppingListItemSeqId the shoppingListItemSeqId to set
     */
    public void setShoppingListItemSeqId(String shoppingListItemSeqId) {
        this.shoppingListItemSeqId = shoppingListItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param surveyResponseId the surveyResponseId to set
     */
    public void setSurveyResponseId(String surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getShoppingListId() {
        return this.shoppingListId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getShoppingListItemSeqId() {
        return this.shoppingListItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getSurveyResponseId() {
        return this.surveyResponseId;
    }
}