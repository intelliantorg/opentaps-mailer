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
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity OrderDeliverySchedule.
 */
@javax.persistence.Entity
@Table(name="ORDER_DELIVERY_SCHEDULE")
@IdClass(OrderDeliverySchedulePk.class)
public class OrderDeliverySchedule extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("orderId", "ORDER_ID");
        fields.put("orderItemSeqId", "ORDER_ITEM_SEQ_ID");
        fields.put("estimatedReadyDate", "ESTIMATED_READY_DATE");
        fields.put("cartons", "CARTONS");
        fields.put("skidsPallets", "SKIDS_PALLETS");
        fields.put("unitsPieces", "UNITS_PIECES");
        fields.put("totalCubicSize", "TOTAL_CUBIC_SIZE");
        fields.put("totalCubicUomId", "TOTAL_CUBIC_UOM_ID");
        fields.put("totalWeight", "TOTAL_WEIGHT");
        fields.put("totalWeightUomId", "TOTAL_WEIGHT_UOM_ID");
        fields.put("statusId", "STATUS_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("OrderDeliverySchedule", fields);
}
  public static enum Fields implements EntityFieldInterface<OrderDeliverySchedule> {
    orderId("orderId"),
    orderItemSeqId("orderItemSeqId"),
    estimatedReadyDate("estimatedReadyDate"),
    cartons("cartons"),
    skidsPallets("skidsPallets"),
    unitsPieces("unitsPieces"),
    totalCubicSize("totalCubicSize"),
    totalCubicUomId("totalCubicUomId"),
    totalWeight("totalWeight"),
    totalWeightUomId("totalWeightUomId"),
    statusId("statusId"),
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
    
    @Column(name="ORDER_ID")
    private String orderId;
    @Id
    
    @Column(name="ORDER_ITEM_SEQ_ID")
    private String orderItemSeqId;
    
    @Column(name="ESTIMATED_READY_DATE")
    private Timestamp estimatedReadyDate;
    
    @Column(name="CARTONS")
    private Long cartons;
    
    @Column(name="SKIDS_PALLETS")
    private Long skidsPallets;
    
    @Column(name="UNITS_PIECES")
    private BigDecimal unitsPieces;
    
    @Column(name="TOTAL_CUBIC_SIZE")
    private BigDecimal totalCubicSize;
    
    @Column(name="TOTAL_CUBIC_UOM_ID")
    private String totalCubicUomId;
    
    @Column(name="TOTAL_WEIGHT")
    private BigDecimal totalWeight;
    
    @Column(name="TOTAL_WEIGHT_UOM_ID")
    private String totalWeightUomId;
    
    @Column(name="STATUS_ID")
    private String statusId;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ORDER_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private OrderHeader orderHeader = null;
    private transient OrderItem orderItem = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="TOTAL_CUBIC_UOM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Uom totalCubicUom = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="TOTAL_WEIGHT_UOM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Uom totalWeightUom = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private StatusItem statusItem = null;

  /**
   * Default constructor.
   */
  public OrderDeliverySchedule() {
      super();
      this.baseEntityName = "OrderDeliverySchedule";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("orderId");this.primaryKeyNames.add("orderItemSeqId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public OrderDeliverySchedule(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    /**
     * Auto generated value setter.
     * @param orderItemSeqId the orderItemSeqId to set
     */
    public void setOrderItemSeqId(String orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param estimatedReadyDate the estimatedReadyDate to set
     */
    public void setEstimatedReadyDate(Timestamp estimatedReadyDate) {
        this.estimatedReadyDate = estimatedReadyDate;
    }
    /**
     * Auto generated value setter.
     * @param cartons the cartons to set
     */
    public void setCartons(Long cartons) {
        this.cartons = cartons;
    }
    /**
     * Auto generated value setter.
     * @param skidsPallets the skidsPallets to set
     */
    public void setSkidsPallets(Long skidsPallets) {
        this.skidsPallets = skidsPallets;
    }
    /**
     * Auto generated value setter.
     * @param unitsPieces the unitsPieces to set
     */
    public void setUnitsPieces(BigDecimal unitsPieces) {
        this.unitsPieces = unitsPieces;
    }
    /**
     * Auto generated value setter.
     * @param totalCubicSize the totalCubicSize to set
     */
    public void setTotalCubicSize(BigDecimal totalCubicSize) {
        this.totalCubicSize = totalCubicSize;
    }
    /**
     * Auto generated value setter.
     * @param totalCubicUomId the totalCubicUomId to set
     */
    public void setTotalCubicUomId(String totalCubicUomId) {
        this.totalCubicUomId = totalCubicUomId;
    }
    /**
     * Auto generated value setter.
     * @param totalWeight the totalWeight to set
     */
    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }
    /**
     * Auto generated value setter.
     * @param totalWeightUomId the totalWeightUomId to set
     */
    public void setTotalWeightUomId(String totalWeightUomId) {
        this.totalWeightUomId = totalWeightUomId;
    }
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
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
    public String getOrderId() {
        return this.orderId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrderItemSeqId() {
        return this.orderItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEstimatedReadyDate() {
        return this.estimatedReadyDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getCartons() {
        return this.cartons;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getSkidsPallets() {
        return this.skidsPallets;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getUnitsPieces() {
        return this.unitsPieces;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getTotalCubicSize() {
        return this.totalCubicSize;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTotalCubicUomId() {
        return this.totalCubicUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getTotalWeight() {
        return this.totalWeight;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTotalWeightUomId() {
        return this.totalWeightUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStatusId() {
        return this.statusId;
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
     * Auto generated method that gets the related <code>OrderHeader</code> by the relation named <code>OrderHeader</code>.
     * @return the <code>OrderHeader</code>
     * @throws RepositoryException if an error occurs
     */
    public OrderHeader getOrderHeader() throws RepositoryException {
        if (this.orderHeader == null) {
            this.orderHeader = getRelatedOne(OrderHeader.class, "OrderHeader");
        }
        return this.orderHeader;
    }
    /**
     * Auto generated method that gets the related <code>OrderItem</code> by the relation named <code>OrderItem</code>.
     * @return the <code>OrderItem</code>
     * @throws RepositoryException if an error occurs
     */
    public OrderItem getOrderItem() throws RepositoryException {
        if (this.orderItem == null) {
            this.orderItem = getRelatedOne(OrderItem.class, "OrderItem");
        }
        return this.orderItem;
    }
    /**
     * Auto generated method that gets the related <code>Uom</code> by the relation named <code>TotalCubicUom</code>.
     * @return the <code>Uom</code>
     * @throws RepositoryException if an error occurs
     */
    public Uom getTotalCubicUom() throws RepositoryException {
        if (this.totalCubicUom == null) {
            this.totalCubicUom = getRelatedOne(Uom.class, "TotalCubicUom");
        }
        return this.totalCubicUom;
    }
    /**
     * Auto generated method that gets the related <code>Uom</code> by the relation named <code>TotalWeightUom</code>.
     * @return the <code>Uom</code>
     * @throws RepositoryException if an error occurs
     */
    public Uom getTotalWeightUom() throws RepositoryException {
        if (this.totalWeightUom == null) {
            this.totalWeightUom = getRelatedOne(Uom.class, "TotalWeightUom");
        }
        return this.totalWeightUom;
    }
    /**
     * Auto generated method that gets the related <code>StatusItem</code> by the relation named <code>StatusItem</code>.
     * @return the <code>StatusItem</code>
     * @throws RepositoryException if an error occurs
     */
    public StatusItem getStatusItem() throws RepositoryException {
        if (this.statusItem == null) {
            this.statusItem = getRelatedOne(StatusItem.class, "StatusItem");
        }
        return this.statusItem;
    }

    /**
     * Auto generated value setter.
     * @param orderHeader the orderHeader to set
    */
    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }
    /**
     * Auto generated value setter.
     * @param orderItem the orderItem to set
    */
    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
    /**
     * Auto generated value setter.
     * @param totalCubicUom the totalCubicUom to set
    */
    public void setTotalCubicUom(Uom totalCubicUom) {
        this.totalCubicUom = totalCubicUom;
    }
    /**
     * Auto generated value setter.
     * @param totalWeightUom the totalWeightUom to set
    */
    public void setTotalWeightUom(Uom totalWeightUom) {
        this.totalWeightUom = totalWeightUom;
    }
    /**
     * Auto generated value setter.
     * @param statusItem the statusItem to set
    */
    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setOrderId((String) mapValue.get("orderId"));
        setOrderItemSeqId((String) mapValue.get("orderItemSeqId"));
        setEstimatedReadyDate((Timestamp) mapValue.get("estimatedReadyDate"));
        setCartons((Long) mapValue.get("cartons"));
        setSkidsPallets((Long) mapValue.get("skidsPallets"));
        setUnitsPieces(convertToBigDecimal(mapValue.get("unitsPieces")));
        setTotalCubicSize(convertToBigDecimal(mapValue.get("totalCubicSize")));
        setTotalCubicUomId((String) mapValue.get("totalCubicUomId"));
        setTotalWeight(convertToBigDecimal(mapValue.get("totalWeight")));
        setTotalWeightUomId((String) mapValue.get("totalWeightUomId"));
        setStatusId((String) mapValue.get("statusId"));
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
        mapValue.put("orderId", getOrderId());
        mapValue.put("orderItemSeqId", getOrderItemSeqId());
        mapValue.put("estimatedReadyDate", getEstimatedReadyDate());
        mapValue.put("cartons", getCartons());
        mapValue.put("skidsPallets", getSkidsPallets());
        mapValue.put("unitsPieces", getUnitsPieces());
        mapValue.put("totalCubicSize", getTotalCubicSize());
        mapValue.put("totalCubicUomId", getTotalCubicUomId());
        mapValue.put("totalWeight", getTotalWeight());
        mapValue.put("totalWeightUomId", getTotalWeightUomId());
        mapValue.put("statusId", getStatusId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
