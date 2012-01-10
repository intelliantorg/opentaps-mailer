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
 * Auto generated base entity ProductMaintType.
 */
@javax.persistence.Entity
@Table(name="PRODUCT_MAINT_TYPE")
public class ProductMaintType extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("productMaintTypeId", "PRODUCT_MAINT_TYPE_ID");
        fields.put("description", "DESCRIPTION");
        fields.put("parentTypeId", "PARENT_TYPE_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("ProductMaintType", fields);
}
  public static enum Fields implements EntityFieldInterface<ProductMaintType> {
    productMaintTypeId("productMaintTypeId"),
    description("description"),
    parentTypeId("parentTypeId"),
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

    @org.hibernate.annotations.GenericGenerator(name="ProductMaintType_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="ProductMaintType_GEN")   
    @Id
    
    @Column(name="PRODUCT_MAINT_TYPE_ID")
    private String productMaintTypeId;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="PARENT_TYPE_ID")
    private String parentTypeId;
    
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
    private ProductMaintType parentProductMaintType = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_MAINT_TYPE_ID")
    private List<FixedAssetMaint> fixedAssetMaints = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_MAINT_TYPE_ID")
    private List<ProductMaint> productMaints = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_TYPE_ID")
    private List<ProductMaintType> childProductMaintTypes = null;

  /**
   * Default constructor.
   */
  public ProductMaintType() {
      super();
      this.baseEntityName = "ProductMaintType";
      this.isView = false;
      this.resourceName = "ProductEntityLabels";
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("productMaintTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ProductMaintType(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param productMaintTypeId the productMaintTypeId to set
     */
    public void setProductMaintTypeId(String productMaintTypeId) {
        this.productMaintTypeId = productMaintTypeId;
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
     * @param parentTypeId the parentTypeId to set
     */
    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
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
    public String getProductMaintTypeId() {
        return this.productMaintTypeId;
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
     * @return <code>String</code>
     */
    public String getParentTypeId() {
        return this.parentTypeId;
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
     * Auto generated method that gets the related <code>ProductMaintType</code> by the relation named <code>ParentProductMaintType</code>.
     * @return the <code>ProductMaintType</code>
     * @throws RepositoryException if an error occurs
     */
    public ProductMaintType getParentProductMaintType() throws RepositoryException {
        if (this.parentProductMaintType == null) {
            this.parentProductMaintType = getRelatedOne(ProductMaintType.class, "ParentProductMaintType");
        }
        return this.parentProductMaintType;
    }
    /**
     * Auto generated method that gets the related <code>FixedAssetMaint</code> by the relation named <code>FixedAssetMaint</code>.
     * @return the list of <code>FixedAssetMaint</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FixedAssetMaint> getFixedAssetMaints() throws RepositoryException {
        if (this.fixedAssetMaints == null) {
            this.fixedAssetMaints = getRelated(FixedAssetMaint.class, "FixedAssetMaint");
        }
        return this.fixedAssetMaints;
    }
    /**
     * Auto generated method that gets the related <code>ProductMaint</code> by the relation named <code>ProductMaint</code>.
     * @return the list of <code>ProductMaint</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ProductMaint> getProductMaints() throws RepositoryException {
        if (this.productMaints == null) {
            this.productMaints = getRelated(ProductMaint.class, "ProductMaint");
        }
        return this.productMaints;
    }
    /**
     * Auto generated method that gets the related <code>ProductMaintType</code> by the relation named <code>ChildProductMaintType</code>.
     * @return the list of <code>ProductMaintType</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ProductMaintType> getChildProductMaintTypes() throws RepositoryException {
        if (this.childProductMaintTypes == null) {
            this.childProductMaintTypes = getRelated(ProductMaintType.class, "ChildProductMaintType");
        }
        return this.childProductMaintTypes;
    }

    /**
     * Auto generated value setter.
     * @param parentProductMaintType the parentProductMaintType to set
    */
    public void setParentProductMaintType(ProductMaintType parentProductMaintType) {
        this.parentProductMaintType = parentProductMaintType;
    }
    /**
     * Auto generated value setter.
     * @param fixedAssetMaints the fixedAssetMaints to set
    */
    public void setFixedAssetMaints(List<FixedAssetMaint> fixedAssetMaints) {
        this.fixedAssetMaints = fixedAssetMaints;
    }
    /**
     * Auto generated value setter.
     * @param productMaints the productMaints to set
    */
    public void setProductMaints(List<ProductMaint> productMaints) {
        this.productMaints = productMaints;
    }
    /**
     * Auto generated value setter.
     * @param childProductMaintTypes the childProductMaintTypes to set
    */
    public void setChildProductMaintTypes(List<ProductMaintType> childProductMaintTypes) {
        this.childProductMaintTypes = childProductMaintTypes;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setProductMaintTypeId((String) mapValue.get("productMaintTypeId"));
        setDescription((String) mapValue.get("description"));
        setParentTypeId((String) mapValue.get("parentTypeId"));
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
        mapValue.put("productMaintTypeId", getProductMaintTypeId());
        mapValue.put("description", getDescription());
        mapValue.put("parentTypeId", getParentTypeId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
