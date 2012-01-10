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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity InventoryEventPlanned.
 */
@javax.persistence.Entity
@Table(name="INVENTORY_EVENT_PLANNED")
@IdClass(InventoryEventPlannedPk.class)
public class InventoryEventPlanned extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("productId", "PRODUCT_ID");
        fields.put("eventDate", "EVENT_DATE");
        fields.put("inventoryEventPlanTypeId", "INVENTORY_EVENT_PLAN_TYPE_ID");
        fields.put("eventQuantity", "EVENT_QUANTITY");
        fields.put("facilityId", "FACILITY_ID");
        fields.put("eventName", "EVENT_NAME");
        fields.put("isLate", "IS_LATE");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("InventoryEventPlanned", fields);
}
  public static enum Fields implements EntityFieldInterface<InventoryEventPlanned> {
    productId("productId"),
    eventDate("eventDate"),
    inventoryEventPlanTypeId("inventoryEventPlanTypeId"),
    eventQuantity("eventQuantity"),
    facilityId("facilityId"),
    eventName("eventName"),
    isLate("isLate"),
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
    
    @Column(name="PRODUCT_ID")
    private String productId;
    @Id
    
    @Column(name="EVENT_DATE")
    private Timestamp eventDate;
    @Id
    
    @Column(name="INVENTORY_EVENT_PLAN_TYPE_ID")
    private String inventoryEventPlanTypeId;
    
    @Column(name="EVENT_QUANTITY")
    private BigDecimal eventQuantity;
    
    @Column(name="FACILITY_ID")
    private String facilityId;
    
    @Column(name="EVENT_NAME")
    private String eventName;
    
    @Column(name="IS_LATE")
    private String isLate;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Product product = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="INVENTORY_EVENT_PLAN_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private InventoryEventPlannedType inventoryEventPlannedType = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="FACILITY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Facility facility = null;

  /**
   * Default constructor.
   */
  public InventoryEventPlanned() {
      super();
      this.baseEntityName = "InventoryEventPlanned";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("productId");this.primaryKeyNames.add("eventDate");this.primaryKeyNames.add("inventoryEventPlanTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public InventoryEventPlanned(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * Auto generated value setter.
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }
    /**
     * Auto generated value setter.
     * @param inventoryEventPlanTypeId the inventoryEventPlanTypeId to set
     */
    public void setInventoryEventPlanTypeId(String inventoryEventPlanTypeId) {
        this.inventoryEventPlanTypeId = inventoryEventPlanTypeId;
    }
    /**
     * Auto generated value setter.
     * @param eventQuantity the eventQuantity to set
     */
    public void setEventQuantity(BigDecimal eventQuantity) {
        this.eventQuantity = eventQuantity;
    }
    /**
     * Auto generated value setter.
     * @param facilityId the facilityId to set
     */
    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
    /**
     * Auto generated value setter.
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    /**
     * Auto generated value setter.
     * @param isLate the isLate to set
     */
    public void setIsLate(String isLate) {
        this.isLate = isLate;
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
    public String getProductId() {
        return this.productId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEventDate() {
        return this.eventDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getInventoryEventPlanTypeId() {
        return this.inventoryEventPlanTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getEventQuantity() {
        return this.eventQuantity;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFacilityId() {
        return this.facilityId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getEventName() {
        return this.eventName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getIsLate() {
        return this.isLate;
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
     * Auto generated method that gets the related <code>Product</code> by the relation named <code>Product</code>.
     * @return the <code>Product</code>
     * @throws RepositoryException if an error occurs
     */
    public Product getProduct() throws RepositoryException {
        if (this.product == null) {
            this.product = getRelatedOne(Product.class, "Product");
        }
        return this.product;
    }
    /**
     * Auto generated method that gets the related <code>InventoryEventPlannedType</code> by the relation named <code>InventoryEventPlannedType</code>.
     * @return the <code>InventoryEventPlannedType</code>
     * @throws RepositoryException if an error occurs
     */
    public InventoryEventPlannedType getInventoryEventPlannedType() throws RepositoryException {
        if (this.inventoryEventPlannedType == null) {
            this.inventoryEventPlannedType = getRelatedOne(InventoryEventPlannedType.class, "InventoryEventPlannedType");
        }
        return this.inventoryEventPlannedType;
    }
    /**
     * Auto generated method that gets the related <code>Facility</code> by the relation named <code>Facility</code>.
     * @return the <code>Facility</code>
     * @throws RepositoryException if an error occurs
     */
    public Facility getFacility() throws RepositoryException {
        if (this.facility == null) {
            this.facility = getRelatedOne(Facility.class, "Facility");
        }
        return this.facility;
    }

    /**
     * Auto generated value setter.
     * @param product the product to set
    */
    public void setProduct(Product product) {
        this.product = product;
    }
    /**
     * Auto generated value setter.
     * @param inventoryEventPlannedType the inventoryEventPlannedType to set
    */
    public void setInventoryEventPlannedType(InventoryEventPlannedType inventoryEventPlannedType) {
        this.inventoryEventPlannedType = inventoryEventPlannedType;
    }
    /**
     * Auto generated value setter.
     * @param facility the facility to set
    */
    public void setFacility(Facility facility) {
        this.facility = facility;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setProductId((String) mapValue.get("productId"));
        setEventDate((Timestamp) mapValue.get("eventDate"));
        setInventoryEventPlanTypeId((String) mapValue.get("inventoryEventPlanTypeId"));
        setEventQuantity(convertToBigDecimal(mapValue.get("eventQuantity")));
        setFacilityId((String) mapValue.get("facilityId"));
        setEventName((String) mapValue.get("eventName"));
        setIsLate((String) mapValue.get("isLate"));
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
        mapValue.put("productId", getProductId());
        mapValue.put("eventDate", getEventDate());
        mapValue.put("inventoryEventPlanTypeId", getInventoryEventPlanTypeId());
        mapValue.put("eventQuantity", getEventQuantity());
        mapValue.put("facilityId", getFacilityId());
        mapValue.put("eventName", getEventName());
        mapValue.put("isLate", getIsLate());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
