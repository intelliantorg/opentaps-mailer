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
 * Auto generated base entity FixedAssetStdCostType.
 */
@javax.persistence.Entity
@Table(name="FIXED_ASSET_STD_COST_TYPE")
public class FixedAssetStdCostType extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("fixedAssetStdCostTypeId", "FIXED_ASSET_STD_COST_TYPE_ID");
        fields.put("parentTypeId", "PARENT_TYPE_ID");
        fields.put("hasTable", "HAS_TABLE");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("FixedAssetStdCostType", fields);
}
  public static enum Fields implements EntityFieldInterface<FixedAssetStdCostType> {
    fixedAssetStdCostTypeId("fixedAssetStdCostTypeId"),
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

    @org.hibernate.annotations.GenericGenerator(name="FixedAssetStdCostType_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="FixedAssetStdCostType_GEN")   
    @Id
    
    @Column(name="FIXED_ASSET_STD_COST_TYPE_ID")
    private String fixedAssetStdCostTypeId;
    
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
    private FixedAssetStdCostType parentFixedAssetStdCostType = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="fixedAssetStdCostType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="FIXED_ASSET_STD_COST_TYPE_ID")
    private List<FixedAssetStdCost> fixedAssetStdCosts = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_TYPE_ID")
    private List<FixedAssetStdCostType> childFixedAssetStdCostTypes = null;

  /**
   * Default constructor.
   */
  public FixedAssetStdCostType() {
      super();
      this.baseEntityName = "FixedAssetStdCostType";
      this.isView = false;
      this.resourceName = "AccountingEntityLabels";
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("fixedAssetStdCostTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public FixedAssetStdCostType(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param fixedAssetStdCostTypeId the fixedAssetStdCostTypeId to set
     */
    public void setFixedAssetStdCostTypeId(String fixedAssetStdCostTypeId) {
        this.fixedAssetStdCostTypeId = fixedAssetStdCostTypeId;
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
    public String getFixedAssetStdCostTypeId() {
        return this.fixedAssetStdCostTypeId;
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
     * Auto generated method that gets the related <code>FixedAssetStdCostType</code> by the relation named <code>ParentFixedAssetStdCostType</code>.
     * @return the <code>FixedAssetStdCostType</code>
     * @throws RepositoryException if an error occurs
     */
    public FixedAssetStdCostType getParentFixedAssetStdCostType() throws RepositoryException {
        if (this.parentFixedAssetStdCostType == null) {
            this.parentFixedAssetStdCostType = getRelatedOne(FixedAssetStdCostType.class, "ParentFixedAssetStdCostType");
        }
        return this.parentFixedAssetStdCostType;
    }
    /**
     * Auto generated method that gets the related <code>FixedAssetStdCost</code> by the relation named <code>FixedAssetStdCost</code>.
     * @return the list of <code>FixedAssetStdCost</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FixedAssetStdCost> getFixedAssetStdCosts() throws RepositoryException {
        if (this.fixedAssetStdCosts == null) {
            this.fixedAssetStdCosts = getRelated(FixedAssetStdCost.class, "FixedAssetStdCost");
        }
        return this.fixedAssetStdCosts;
    }
    /**
     * Auto generated method that gets the related <code>FixedAssetStdCostType</code> by the relation named <code>ChildFixedAssetStdCostType</code>.
     * @return the list of <code>FixedAssetStdCostType</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FixedAssetStdCostType> getChildFixedAssetStdCostTypes() throws RepositoryException {
        if (this.childFixedAssetStdCostTypes == null) {
            this.childFixedAssetStdCostTypes = getRelated(FixedAssetStdCostType.class, "ChildFixedAssetStdCostType");
        }
        return this.childFixedAssetStdCostTypes;
    }

    /**
     * Auto generated value setter.
     * @param parentFixedAssetStdCostType the parentFixedAssetStdCostType to set
    */
    public void setParentFixedAssetStdCostType(FixedAssetStdCostType parentFixedAssetStdCostType) {
        this.parentFixedAssetStdCostType = parentFixedAssetStdCostType;
    }
    /**
     * Auto generated value setter.
     * @param fixedAssetStdCosts the fixedAssetStdCosts to set
    */
    public void setFixedAssetStdCosts(List<FixedAssetStdCost> fixedAssetStdCosts) {
        this.fixedAssetStdCosts = fixedAssetStdCosts;
    }
    /**
     * Auto generated value setter.
     * @param childFixedAssetStdCostTypes the childFixedAssetStdCostTypes to set
    */
    public void setChildFixedAssetStdCostTypes(List<FixedAssetStdCostType> childFixedAssetStdCostTypes) {
        this.childFixedAssetStdCostTypes = childFixedAssetStdCostTypes;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addFixedAssetStdCost(FixedAssetStdCost fixedAssetStdCost) {
        if (this.fixedAssetStdCosts == null) {
            this.fixedAssetStdCosts = new ArrayList<FixedAssetStdCost>();
        }
        this.fixedAssetStdCosts.add(fixedAssetStdCost);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeFixedAssetStdCost(FixedAssetStdCost fixedAssetStdCost) {
        if (this.fixedAssetStdCosts == null) {
            return;
        }
        this.fixedAssetStdCosts.remove(fixedAssetStdCost);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearFixedAssetStdCost() {
        if (this.fixedAssetStdCosts == null) {
            return;
        }
        this.fixedAssetStdCosts.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setFixedAssetStdCostTypeId((String) mapValue.get("fixedAssetStdCostTypeId"));
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
        mapValue.put("fixedAssetStdCostTypeId", getFixedAssetStdCostTypeId());
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
