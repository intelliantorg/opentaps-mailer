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
 * Auto generated base entity CostComponentTypeAttr.
 */
@javax.persistence.Entity
@Table(name="COST_COMPONENT_TYPE_ATTR")
@IdClass(CostComponentTypeAttrPk.class)
public class CostComponentTypeAttr extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("costComponentTypeId", "COST_COMPONENT_TYPE_ID");
        fields.put("attrName", "ATTR_NAME");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("CostComponentTypeAttr", fields);
}
  public static enum Fields implements EntityFieldInterface<CostComponentTypeAttr> {
    costComponentTypeId("costComponentTypeId"),
    attrName("attrName"),
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
    
    @Column(name="COST_COMPONENT_TYPE_ID")
    private String costComponentTypeId;
    @Id
    
    @Column(name="ATTR_NAME")
    private String attrName;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="COST_COMPONENT_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private CostComponentType costComponentType = null;
    private transient List<CostComponentAttribute> costComponentAttributes = null;
    private transient List<CostComponent> costComponents = null;

  /**
   * Default constructor.
   */
  public CostComponentTypeAttr() {
      super();
      this.baseEntityName = "CostComponentTypeAttr";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("costComponentTypeId");this.primaryKeyNames.add("attrName");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public CostComponentTypeAttr(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param costComponentTypeId the costComponentTypeId to set
     */
    public void setCostComponentTypeId(String costComponentTypeId) {
        this.costComponentTypeId = costComponentTypeId;
    }
    /**
     * Auto generated value setter.
     * @param attrName the attrName to set
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
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
    public String getCostComponentTypeId() {
        return this.costComponentTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAttrName() {
        return this.attrName;
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
     * Auto generated method that gets the related <code>CostComponentType</code> by the relation named <code>CostComponentType</code>.
     * @return the <code>CostComponentType</code>
     * @throws RepositoryException if an error occurs
     */
    public CostComponentType getCostComponentType() throws RepositoryException {
        if (this.costComponentType == null) {
            this.costComponentType = getRelatedOne(CostComponentType.class, "CostComponentType");
        }
        return this.costComponentType;
    }
    /**
     * Auto generated method that gets the related <code>CostComponentAttribute</code> by the relation named <code>CostComponentAttribute</code>.
     * @return the list of <code>CostComponentAttribute</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends CostComponentAttribute> getCostComponentAttributes() throws RepositoryException {
        if (this.costComponentAttributes == null) {
            this.costComponentAttributes = getRelated(CostComponentAttribute.class, "CostComponentAttribute");
        }
        return this.costComponentAttributes;
    }
    /**
     * Auto generated method that gets the related <code>CostComponent</code> by the relation named <code>CostComponent</code>.
     * @return the list of <code>CostComponent</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends CostComponent> getCostComponents() throws RepositoryException {
        if (this.costComponents == null) {
            this.costComponents = getRelated(CostComponent.class, "CostComponent");
        }
        return this.costComponents;
    }

    /**
     * Auto generated value setter.
     * @param costComponentType the costComponentType to set
    */
    public void setCostComponentType(CostComponentType costComponentType) {
        this.costComponentType = costComponentType;
    }
    /**
     * Auto generated value setter.
     * @param costComponentAttributes the costComponentAttributes to set
    */
    public void setCostComponentAttributes(List<CostComponentAttribute> costComponentAttributes) {
        this.costComponentAttributes = costComponentAttributes;
    }
    /**
     * Auto generated value setter.
     * @param costComponents the costComponents to set
    */
    public void setCostComponents(List<CostComponent> costComponents) {
        this.costComponents = costComponents;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setCostComponentTypeId((String) mapValue.get("costComponentTypeId"));
        setAttrName((String) mapValue.get("attrName"));
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
        mapValue.put("costComponentTypeId", getCostComponentTypeId());
        mapValue.put("attrName", getAttrName());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
