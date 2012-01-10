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
public class ProductPromoProductPk implements Serializable {
    @Column(name="PRODUCT_PROMO_ID")
    private String productPromoId;
    @Column(name="PRODUCT_PROMO_RULE_ID")
    private String productPromoRuleId;
    @Column(name="PRODUCT_PROMO_ACTION_SEQ_ID")
    private String productPromoActionSeqId;
    @Column(name="PRODUCT_PROMO_COND_SEQ_ID")
    private String productPromoCondSeqId;
    @Column(name="PRODUCT_ID")
    private String productId;
    
    /**
     * Auto generated value setter.
     * @param productPromoId the productPromoId to set
     */
    public void setProductPromoId(String productPromoId) {
        this.productPromoId = productPromoId;
    }
    /**
     * Auto generated value setter.
     * @param productPromoRuleId the productPromoRuleId to set
     */
    public void setProductPromoRuleId(String productPromoRuleId) {
        this.productPromoRuleId = productPromoRuleId;
    }
    /**
     * Auto generated value setter.
     * @param productPromoActionSeqId the productPromoActionSeqId to set
     */
    public void setProductPromoActionSeqId(String productPromoActionSeqId) {
        this.productPromoActionSeqId = productPromoActionSeqId;
    }
    /**
     * Auto generated value setter.
     * @param productPromoCondSeqId the productPromoCondSeqId to set
     */
    public void setProductPromoCondSeqId(String productPromoCondSeqId) {
        this.productPromoCondSeqId = productPromoCondSeqId;
    }
    /**
     * Auto generated value setter.
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductPromoId() {
        return this.productPromoId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductPromoRuleId() {
        return this.productPromoRuleId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductPromoActionSeqId() {
        return this.productPromoActionSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductPromoCondSeqId() {
        return this.productPromoCondSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductId() {
        return this.productId;
    }
}
