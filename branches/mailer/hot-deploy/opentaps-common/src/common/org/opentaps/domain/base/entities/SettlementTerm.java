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
import java.sql.Timestamp;

/**
 * Auto generated base entity SettlementTerm.
 */
@javax.persistence.Entity
@Table(name="SETTLEMENT_TERM")
public class SettlementTerm extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("settlementTermId", "SETTLEMENT_TERM_ID");
        fields.put("termName", "TERM_NAME");
        fields.put("termValue", "TERM_VALUE");
        fields.put("uomId", "UOM_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("SettlementTerm", fields);
}
  public static enum Fields implements EntityFieldInterface<SettlementTerm> {
    settlementTermId("settlementTermId"),
    termName("termName"),
    termValue("termValue"),
    uomId("uomId"),
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

    @org.hibernate.annotations.GenericGenerator(name="SettlementTerm_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="SettlementTerm_GEN")   
    @Id
    
    @Column(name="SETTLEMENT_TERM_ID")
    private String settlementTermId;
    
    @Column(name="TERM_NAME")
    private String termName;
    
    @Column(name="TERM_VALUE")
    private Long termValue;
    
    @Column(name="UOM_ID")
    private String uomId;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="SETTLEMENT_TERM_ID")
    private List<AcctgTransEntry> acctgTransEntrys = null;

  /**
   * Default constructor.
   */
  public SettlementTerm() {
      super();
      this.baseEntityName = "SettlementTerm";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("settlementTermId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public SettlementTerm(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param settlementTermId the settlementTermId to set
     */
    public void setSettlementTermId(String settlementTermId) {
        this.settlementTermId = settlementTermId;
    }
    /**
     * Auto generated value setter.
     * @param termName the termName to set
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }
    /**
     * Auto generated value setter.
     * @param termValue the termValue to set
     */
    public void setTermValue(Long termValue) {
        this.termValue = termValue;
    }
    /**
     * Auto generated value setter.
     * @param uomId the uomId to set
     */
    public void setUomId(String uomId) {
        this.uomId = uomId;
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
    public String getSettlementTermId() {
        return this.settlementTermId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTermName() {
        return this.termName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getTermValue() {
        return this.termValue;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getUomId() {
        return this.uomId;
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
     * Auto generated method that gets the related <code>AcctgTransEntry</code> by the relation named <code>AcctgTransEntry</code>.
     * @return the list of <code>AcctgTransEntry</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends AcctgTransEntry> getAcctgTransEntrys() throws RepositoryException {
        if (this.acctgTransEntrys == null) {
            this.acctgTransEntrys = getRelated(AcctgTransEntry.class, "AcctgTransEntry");
        }
        return this.acctgTransEntrys;
    }

    /**
     * Auto generated value setter.
     * @param acctgTransEntrys the acctgTransEntrys to set
    */
    public void setAcctgTransEntrys(List<AcctgTransEntry> acctgTransEntrys) {
        this.acctgTransEntrys = acctgTransEntrys;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setSettlementTermId((String) mapValue.get("settlementTermId"));
        setTermName((String) mapValue.get("termName"));
        setTermValue((Long) mapValue.get("termValue"));
        setUomId((String) mapValue.get("uomId"));
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
        mapValue.put("settlementTermId", getSettlementTermId());
        mapValue.put("termName", getTermName());
        mapValue.put("termValue", getTermValue());
        mapValue.put("uomId", getUomId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
