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
import java.math.BigDecimal;
import java.sql.Timestamp;

@Embeddable
public class SimpleSalesTaxLookupPk implements Serializable {
    @Column(name="PRODUCT_STORE_ID")
    private String productStoreId;
    @Column(name="COUNTRY_GEO_ID")
    private String countryGeoId;
    @Column(name="STATE_PROVINCE_GEO_ID")
    private String stateProvinceGeoId;
    @Column(name="TAX_CATEGORY")
    private String taxCategory;
    @Column(name="MIN_ITEM_PRICE")
    private BigDecimal minItemPrice;
    @Column(name="MIN_PURCHASE")
    private BigDecimal minPurchase;
    @Column(name="FROM_DATE")
    private Timestamp fromDate;
    
    /**
     * Auto generated value setter.
     * @param productStoreId the productStoreId to set
     */
    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }
    /**
     * Auto generated value setter.
     * @param countryGeoId the countryGeoId to set
     */
    public void setCountryGeoId(String countryGeoId) {
        this.countryGeoId = countryGeoId;
    }
    /**
     * Auto generated value setter.
     * @param stateProvinceGeoId the stateProvinceGeoId to set
     */
    public void setStateProvinceGeoId(String stateProvinceGeoId) {
        this.stateProvinceGeoId = stateProvinceGeoId;
    }
    /**
     * Auto generated value setter.
     * @param taxCategory the taxCategory to set
     */
    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }
    /**
     * Auto generated value setter.
     * @param minItemPrice the minItemPrice to set
     */
    public void setMinItemPrice(BigDecimal minItemPrice) {
        this.minItemPrice = minItemPrice;
    }
    /**
     * Auto generated value setter.
     * @param minPurchase the minPurchase to set
     */
    public void setMinPurchase(BigDecimal minPurchase) {
        this.minPurchase = minPurchase;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProductStoreId() {
        return this.productStoreId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getCountryGeoId() {
        return this.countryGeoId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getStateProvinceGeoId() {
        return this.stateProvinceGeoId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getTaxCategory() {
        return this.taxCategory;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */  
    public BigDecimal getMinItemPrice() {
        return this.minItemPrice;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */  
    public BigDecimal getMinPurchase() {
        return this.minPurchase;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */  
    public Timestamp getFromDate() {
        return this.fromDate;
    }
}
