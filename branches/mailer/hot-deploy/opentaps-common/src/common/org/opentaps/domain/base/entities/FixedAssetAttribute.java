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
 * Auto generated base entity FixedAssetAttribute.
 */
@javax.persistence.Entity
@Table(name="FIXED_ASSET_ATTRIBUTE")
@IdClass(FixedAssetAttributePk.class)
public class FixedAssetAttribute extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("fixedAssetId", "FIXED_ASSET_ID");
        fields.put("attrName", "ATTR_NAME");
        fields.put("attrValue", "ATTR_VALUE");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("FixedAssetAttribute", fields);
}
  public static enum Fields implements EntityFieldInterface<FixedAssetAttribute> {
    fixedAssetId("fixedAssetId"),
    attrName("attrName"),
    attrValue("attrValue"),
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
    
    @Column(name="FIXED_ASSET_ID")
    private String fixedAssetId;
    @Id
    
    @Column(name="ATTR_NAME")
    private String attrName;
    
    @Column(name="ATTR_VALUE")
    private String attrValue;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="FIXED_ASSET_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private FixedAsset fixedAsset = null;
    private transient List<FixedAssetTypeAttr> fixedAssetTypeAttrs = null;

  /**
   * Default constructor.
   */
  public FixedAssetAttribute() {
      super();
      this.baseEntityName = "FixedAssetAttribute";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("fixedAssetId");this.primaryKeyNames.add("attrName");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public FixedAssetAttribute(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param fixedAssetId the fixedAssetId to set
     */
    public void setFixedAssetId(String fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
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
     * @param attrValue the attrValue to set
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
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
    public String getFixedAssetId() {
        return this.fixedAssetId;
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
     * @return <code>String</code>
     */
    public String getAttrValue() {
        return this.attrValue;
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
     * Auto generated method that gets the related <code>FixedAsset</code> by the relation named <code>FixedAsset</code>.
     * @return the <code>FixedAsset</code>
     * @throws RepositoryException if an error occurs
     */
    public FixedAsset getFixedAsset() throws RepositoryException {
        if (this.fixedAsset == null) {
            this.fixedAsset = getRelatedOne(FixedAsset.class, "FixedAsset");
        }
        return this.fixedAsset;
    }
    /**
     * Auto generated method that gets the related <code>FixedAssetTypeAttr</code> by the relation named <code>FixedAssetTypeAttr</code>.
     * @return the list of <code>FixedAssetTypeAttr</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FixedAssetTypeAttr> getFixedAssetTypeAttrs() throws RepositoryException {
        if (this.fixedAssetTypeAttrs == null) {
            this.fixedAssetTypeAttrs = getRelated(FixedAssetTypeAttr.class, "FixedAssetTypeAttr");
        }
        return this.fixedAssetTypeAttrs;
    }

    /**
     * Auto generated value setter.
     * @param fixedAsset the fixedAsset to set
    */
    public void setFixedAsset(FixedAsset fixedAsset) {
        this.fixedAsset = fixedAsset;
    }
    /**
     * Auto generated value setter.
     * @param fixedAssetTypeAttrs the fixedAssetTypeAttrs to set
    */
    public void setFixedAssetTypeAttrs(List<FixedAssetTypeAttr> fixedAssetTypeAttrs) {
        this.fixedAssetTypeAttrs = fixedAssetTypeAttrs;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setFixedAssetId((String) mapValue.get("fixedAssetId"));
        setAttrName((String) mapValue.get("attrName"));
        setAttrValue((String) mapValue.get("attrValue"));
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
        mapValue.put("fixedAssetId", getFixedAssetId());
        mapValue.put("attrName", getAttrName());
        mapValue.put("attrValue", getAttrValue());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
