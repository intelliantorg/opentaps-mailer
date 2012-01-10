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
 * Auto generated base entity AcctgTransTypeAttr.
 */
@javax.persistence.Entity
@Table(name="ACCTG_TRANS_TYPE_ATTR")
@IdClass(AcctgTransTypeAttrPk.class)
public class AcctgTransTypeAttr extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("acctgTransTypeId", "ACCTG_TRANS_TYPE_ID");
        fields.put("attrName", "ATTR_NAME");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("AcctgTransTypeAttr", fields);
}
  public static enum Fields implements EntityFieldInterface<AcctgTransTypeAttr> {
    acctgTransTypeId("acctgTransTypeId"),
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
    
    @Column(name="ACCTG_TRANS_TYPE_ID")
    private String acctgTransTypeId;
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
    @JoinColumn(name="ACCTG_TRANS_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private AcctgTransType acctgTransType = null;
    private transient List<AcctgTransAttribute> acctgTransAttributes = null;
    private transient List<AcctgTrans> acctgTranses = null;

  /**
   * Default constructor.
   */
  public AcctgTransTypeAttr() {
      super();
      this.baseEntityName = "AcctgTransTypeAttr";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("acctgTransTypeId");this.primaryKeyNames.add("attrName");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public AcctgTransTypeAttr(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param acctgTransTypeId the acctgTransTypeId to set
     */
    public void setAcctgTransTypeId(String acctgTransTypeId) {
        this.acctgTransTypeId = acctgTransTypeId;
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
    public String getAcctgTransTypeId() {
        return this.acctgTransTypeId;
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
     * Auto generated method that gets the related <code>AcctgTransType</code> by the relation named <code>AcctgTransType</code>.
     * @return the <code>AcctgTransType</code>
     * @throws RepositoryException if an error occurs
     */
    public AcctgTransType getAcctgTransType() throws RepositoryException {
        if (this.acctgTransType == null) {
            this.acctgTransType = getRelatedOne(AcctgTransType.class, "AcctgTransType");
        }
        return this.acctgTransType;
    }
    /**
     * Auto generated method that gets the related <code>AcctgTransAttribute</code> by the relation named <code>AcctgTransAttribute</code>.
     * @return the list of <code>AcctgTransAttribute</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends AcctgTransAttribute> getAcctgTransAttributes() throws RepositoryException {
        if (this.acctgTransAttributes == null) {
            this.acctgTransAttributes = getRelated(AcctgTransAttribute.class, "AcctgTransAttribute");
        }
        return this.acctgTransAttributes;
    }
    /**
     * Auto generated method that gets the related <code>AcctgTrans</code> by the relation named <code>AcctgTrans</code>.
     * @return the list of <code>AcctgTrans</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends AcctgTrans> getAcctgTranses() throws RepositoryException {
        if (this.acctgTranses == null) {
            this.acctgTranses = getRelated(AcctgTrans.class, "AcctgTrans");
        }
        return this.acctgTranses;
    }

    /**
     * Auto generated value setter.
     * @param acctgTransType the acctgTransType to set
    */
    public void setAcctgTransType(AcctgTransType acctgTransType) {
        this.acctgTransType = acctgTransType;
    }
    /**
     * Auto generated value setter.
     * @param acctgTransAttributes the acctgTransAttributes to set
    */
    public void setAcctgTransAttributes(List<AcctgTransAttribute> acctgTransAttributes) {
        this.acctgTransAttributes = acctgTransAttributes;
    }
    /**
     * Auto generated value setter.
     * @param acctgTranses the acctgTranses to set
    */
    public void setAcctgTranses(List<AcctgTrans> acctgTranses) {
        this.acctgTranses = acctgTranses;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setAcctgTransTypeId((String) mapValue.get("acctgTransTypeId"));
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
        mapValue.put("acctgTransTypeId", getAcctgTransTypeId());
        mapValue.put("attrName", getAttrName());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
