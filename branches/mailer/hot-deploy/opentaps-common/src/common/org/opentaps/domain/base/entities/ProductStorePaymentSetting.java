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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javolution.util.FastMap;

import org.opentaps.foundation.entity.Entity;
import org.opentaps.foundation.entity.EntityFieldInterface;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;
import javax.persistence.*;
import java.lang.String;
import java.sql.Timestamp;

/**
 * Auto generated base entity ProductStorePaymentSetting.
 */
@javax.persistence.Entity
@Table(name="PRODUCT_STORE_PAYMENT_SETTING")
@IdClass(ProductStorePaymentSettingPk.class)
public class ProductStorePaymentSetting extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("productStoreId", "PRODUCT_STORE_ID");
        fields.put("paymentMethodTypeId", "PAYMENT_METHOD_TYPE_ID");
        fields.put("paymentServiceTypeEnumId", "PAYMENT_SERVICE_TYPE_ENUM_ID");
        fields.put("paymentService", "PAYMENT_SERVICE");
        fields.put("paymentPropertiesPath", "PAYMENT_PROPERTIES_PATH");
        fields.put("applyToAllProducts", "APPLY_TO_ALL_PRODUCTS");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("ProductStorePaymentSetting", fields);
}
  public static enum Fields implements EntityFieldInterface<ProductStorePaymentSetting> {
    productStoreId("productStoreId"),
    paymentMethodTypeId("paymentMethodTypeId"),
    paymentServiceTypeEnumId("paymentServiceTypeEnumId"),
    paymentService("paymentService"),
    paymentPropertiesPath("paymentPropertiesPath"),
    applyToAllProducts("applyToAllProducts"),
    lastUpdatedStamp("lastUpdatedStamp"),
    lastUpdatedTxStamp("lastUpdatedTxStamp"),
    createdStamp("createdStamp"),
    createdTxStamp("createdTxStamp");
    private final String fieldName;
    private Fields(String name) { fieldName = name; }
    /** {@inheritDoc} */
    public String getName() { return fieldName; }
    /** {@inheritDoc} */
    public String asc() { return fieldName + " ASC"; }
    /** {@inheritDoc} */
    public String desc() { return fieldName + " DESC"; }
  }

    @Id
    
    @Column(name="PRODUCT_STORE_ID")
    private String productStoreId;
    @Id
    
    @Column(name="PAYMENT_METHOD_TYPE_ID")
    private String paymentMethodTypeId;
    @Id
    
    @Column(name="PAYMENT_SERVICE_TYPE_ENUM_ID")
    private String paymentServiceTypeEnumId;
    
    @Column(name="PAYMENT_SERVICE")
    private String paymentService;
    
    @Column(name="PAYMENT_PROPERTIES_PATH")
    private String paymentPropertiesPath;
    
    @Column(name="APPLY_TO_ALL_PRODUCTS")
    private String applyToAllProducts;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_STORE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ProductStore productStore = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PAYMENT_METHOD_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private PaymentMethodType paymentMethodType = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PAYMENT_SERVICE_TYPE_ENUM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Enumeration enumeration = null;

  /**
   * Default constructor.
   */
  public ProductStorePaymentSetting() {
      super();
      this.baseEntityName = "ProductStorePaymentSetting";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("productStoreId");this.primaryKeyNames.add("paymentMethodTypeId");this.primaryKeyNames.add("paymentServiceTypeEnumId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ProductStorePaymentSetting(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param productStoreId the productStoreId to set
     */
    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }
    /**
     * Auto generated value setter.
     * @param paymentMethodTypeId the paymentMethodTypeId to set
     */
    public void setPaymentMethodTypeId(String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }
    /**
     * Auto generated value setter.
     * @param paymentServiceTypeEnumId the paymentServiceTypeEnumId to set
     */
    public void setPaymentServiceTypeEnumId(String paymentServiceTypeEnumId) {
        this.paymentServiceTypeEnumId = paymentServiceTypeEnumId;
    }
    /**
     * Auto generated value setter.
     * @param paymentService the paymentService to set
     */
    public void setPaymentService(String paymentService) {
        this.paymentService = paymentService;
    }
    /**
     * Auto generated value setter.
     * @param paymentPropertiesPath the paymentPropertiesPath to set
     */
    public void setPaymentPropertiesPath(String paymentPropertiesPath) {
        this.paymentPropertiesPath = paymentPropertiesPath;
    }
    /**
     * Auto generated value setter.
     * @param applyToAllProducts the applyToAllProducts to set
     */
    public void setApplyToAllProducts(String applyToAllProducts) {
        this.applyToAllProducts = applyToAllProducts;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedStamp the lastUpdatedStamp to set
     */
    public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedTxStamp the lastUpdatedTxStamp to set
     */
    public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdStamp the createdStamp to set
     */
    public void setCreatedStamp(Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdTxStamp the createdTxStamp to set
     */
    public void setCreatedTxStamp(Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
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
    public String getPaymentMethodTypeId() {
        return this.paymentMethodTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentServiceTypeEnumId() {
        return this.paymentServiceTypeEnumId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentService() {
        return this.paymentService;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentPropertiesPath() {
        return this.paymentPropertiesPath;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getApplyToAllProducts() {
        return this.applyToAllProducts;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedStamp() {
        return this.lastUpdatedStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedTxStamp() {
        return this.lastUpdatedTxStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedStamp() {
        return this.createdStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedTxStamp() {
        return this.createdTxStamp;
    }

    /**
     * Auto generated method that gets the related <code>ProductStore</code> by the relation named <code>ProductStore</code>.
     * @return the <code>ProductStore</code>
     * @throws RepositoryException if an error occurs
     */
    public ProductStore getProductStore() throws RepositoryException {
        if (this.productStore == null) {
            this.productStore = getRelatedOne(ProductStore.class, "ProductStore");
        }
        return this.productStore;
    }
    /**
     * Auto generated method that gets the related <code>PaymentMethodType</code> by the relation named <code>PaymentMethodType</code>.
     * @return the <code>PaymentMethodType</code>
     * @throws RepositoryException if an error occurs
     */
    public PaymentMethodType getPaymentMethodType() throws RepositoryException {
        if (this.paymentMethodType == null) {
            this.paymentMethodType = getRelatedOne(PaymentMethodType.class, "PaymentMethodType");
        }
        return this.paymentMethodType;
    }
    /**
     * Auto generated method that gets the related <code>Enumeration</code> by the relation named <code>Enumeration</code>.
     * @return the <code>Enumeration</code>
     * @throws RepositoryException if an error occurs
     */
    public Enumeration getEnumeration() throws RepositoryException {
        if (this.enumeration == null) {
            this.enumeration = getRelatedOne(Enumeration.class, "Enumeration");
        }
        return this.enumeration;
    }

    /**
     * Auto generated value setter.
     * @param productStore the productStore to set
    */
    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }
    /**
     * Auto generated value setter.
     * @param paymentMethodType the paymentMethodType to set
    */
    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }
    /**
     * Auto generated value setter.
     * @param enumeration the enumeration to set
    */
    public void setEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setProductStoreId((String) mapValue.get("productStoreId"));
        setPaymentMethodTypeId((String) mapValue.get("paymentMethodTypeId"));
        setPaymentServiceTypeEnumId((String) mapValue.get("paymentServiceTypeEnumId"));
        setPaymentService((String) mapValue.get("paymentService"));
        setPaymentPropertiesPath((String) mapValue.get("paymentPropertiesPath"));
        setApplyToAllProducts((String) mapValue.get("applyToAllProducts"));
        setLastUpdatedStamp((Timestamp) mapValue.get("lastUpdatedStamp"));
        setLastUpdatedTxStamp((Timestamp) mapValue.get("lastUpdatedTxStamp"));
        setCreatedStamp((Timestamp) mapValue.get("createdStamp"));
        setCreatedTxStamp((Timestamp) mapValue.get("createdTxStamp"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("productStoreId", getProductStoreId());
        mapValue.put("paymentMethodTypeId", getPaymentMethodTypeId());
        mapValue.put("paymentServiceTypeEnumId", getPaymentServiceTypeEnumId());
        mapValue.put("paymentService", getPaymentService());
        mapValue.put("paymentPropertiesPath", getPaymentPropertiesPath());
        mapValue.put("applyToAllProducts", getApplyToAllProducts());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
