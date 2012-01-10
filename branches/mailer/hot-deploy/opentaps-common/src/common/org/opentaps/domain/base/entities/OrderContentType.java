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
 * Auto generated base entity OrderContentType.
 */
@javax.persistence.Entity
@Table(name="ORDER_CONTENT_TYPE")
public class OrderContentType extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("orderContentTypeId", "ORDER_CONTENT_TYPE_ID");
        fields.put("parentTypeId", "PARENT_TYPE_ID");
        fields.put("hasTable", "HAS_TABLE");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("OrderContentType", fields);
}
  public static enum Fields implements EntityFieldInterface<OrderContentType> {
    orderContentTypeId("orderContentTypeId"),
    parentTypeId("parentTypeId"),
    hasTable("hasTable"),
    description("description"),
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

    @org.hibernate.annotations.GenericGenerator(name="OrderContentType_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="OrderContentType_GEN")   
    @Id
    
    @Column(name="ORDER_CONTENT_TYPE_ID")
    private String orderContentTypeId;
    
    @Column(name="PARENT_TYPE_ID")
    private String parentTypeId;
    
    @Column(name="HAS_TABLE")
    private String hasTable;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private OrderContentType parentOrderContentType = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="orderContentType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="ORDER_CONTENT_TYPE_ID")
    private List<OrderContent> orderContents = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_TYPE_ID")
    private List<OrderContentType> childOrderContentTypes = null;

  /**
   * Default constructor.
   */
  public OrderContentType() {
      super();
      this.baseEntityName = "OrderContentType";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("orderContentTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public OrderContentType(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param orderContentTypeId the orderContentTypeId to set
     */
    public void setOrderContentTypeId(String orderContentTypeId) {
        this.orderContentTypeId = orderContentTypeId;
    }
    /**
     * Auto generated value setter.
     * @param parentTypeId the parentTypeId to set
     */
    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
    /**
     * Auto generated value setter.
     * @param hasTable the hasTable to set
     */
    public void setHasTable(String hasTable) {
        this.hasTable = hasTable;
    }
    /**
     * Auto generated value setter.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
    public String getOrderContentTypeId() {
        return this.orderContentTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getParentTypeId() {
        return this.parentTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getHasTable() {
        return this.hasTable;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDescription() {
        return this.description;
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
     * Auto generated method that gets the related <code>OrderContentType</code> by the relation named <code>ParentOrderContentType</code>.
     * @return the <code>OrderContentType</code>
     * @throws RepositoryException if an error occurs
     */
    public OrderContentType getParentOrderContentType() throws RepositoryException {
        if (this.parentOrderContentType == null) {
            this.parentOrderContentType = getRelatedOne(OrderContentType.class, "ParentOrderContentType");
        }
        return this.parentOrderContentType;
    }
    /**
     * Auto generated method that gets the related <code>OrderContent</code> by the relation named <code>OrderContent</code>.
     * @return the list of <code>OrderContent</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends OrderContent> getOrderContents() throws RepositoryException {
        if (this.orderContents == null) {
            this.orderContents = getRelated(OrderContent.class, "OrderContent");
        }
        return this.orderContents;
    }
    /**
     * Auto generated method that gets the related <code>OrderContentType</code> by the relation named <code>ChildOrderContentType</code>.
     * @return the list of <code>OrderContentType</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends OrderContentType> getChildOrderContentTypes() throws RepositoryException {
        if (this.childOrderContentTypes == null) {
            this.childOrderContentTypes = getRelated(OrderContentType.class, "ChildOrderContentType");
        }
        return this.childOrderContentTypes;
    }

    /**
     * Auto generated value setter.
     * @param parentOrderContentType the parentOrderContentType to set
    */
    public void setParentOrderContentType(OrderContentType parentOrderContentType) {
        this.parentOrderContentType = parentOrderContentType;
    }
    /**
     * Auto generated value setter.
     * @param orderContents the orderContents to set
    */
    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }
    /**
     * Auto generated value setter.
     * @param childOrderContentTypes the childOrderContentTypes to set
    */
    public void setChildOrderContentTypes(List<OrderContentType> childOrderContentTypes) {
        this.childOrderContentTypes = childOrderContentTypes;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addOrderContent(OrderContent orderContent) {
        if (this.orderContents == null) {
            this.orderContents = new ArrayList<OrderContent>();
        }
        this.orderContents.add(orderContent);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeOrderContent(OrderContent orderContent) {
        if (this.orderContents == null) {
            return;
        }
        this.orderContents.remove(orderContent);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearOrderContent() {
        if (this.orderContents == null) {
            return;
        }
        this.orderContents.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setOrderContentTypeId((String) mapValue.get("orderContentTypeId"));
        setParentTypeId((String) mapValue.get("parentTypeId"));
        setHasTable((String) mapValue.get("hasTable"));
        setDescription((String) mapValue.get("description"));
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
        mapValue.put("orderContentTypeId", getOrderContentTypeId());
        mapValue.put("parentTypeId", getParentTypeId());
        mapValue.put("hasTable", getHasTable());
        mapValue.put("description", getDescription());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
