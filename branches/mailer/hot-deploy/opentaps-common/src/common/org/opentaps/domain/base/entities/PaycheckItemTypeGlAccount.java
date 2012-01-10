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
 * Auto generated base entity PaycheckItemTypeGlAccount.
 */
@javax.persistence.Entity
@Table(name="PAYCHECK_ITEM_TYPE_GL_ACCOUNT")
@IdClass(PaycheckItemTypeGlAccountPk.class)
public class PaycheckItemTypeGlAccount extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("paycheckItemTypeId", "PAYCHECK_ITEM_TYPE_ID");
        fields.put("organizationPartyId", "ORGANIZATION_PARTY_ID");
        fields.put("debitGlAccountId", "DEBIT_GL_ACCOUNT_ID");
        fields.put("creditGlAccountId", "CREDIT_GL_ACCOUNT_ID");
        fields.put("defaultDueToPartyId", "DEFAULT_DUE_TO_PARTY_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("PaycheckItemTypeGlAccount", fields);
}
  public static enum Fields implements EntityFieldInterface<PaycheckItemTypeGlAccount> {
    paycheckItemTypeId("paycheckItemTypeId"),
    organizationPartyId("organizationPartyId"),
    debitGlAccountId("debitGlAccountId"),
    creditGlAccountId("creditGlAccountId"),
    defaultDueToPartyId("defaultDueToPartyId"),
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
    
    @Column(name="PAYCHECK_ITEM_TYPE_ID")
    private String paycheckItemTypeId;
    @Id
    
    @Column(name="ORGANIZATION_PARTY_ID")
    private String organizationPartyId;
    
    @Column(name="DEBIT_GL_ACCOUNT_ID")
    private String debitGlAccountId;
    
    @Column(name="CREDIT_GL_ACCOUNT_ID")
    private String creditGlAccountId;
    
    @Column(name="DEFAULT_DUE_TO_PARTY_ID")
    private String defaultDueToPartyId;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ORGANIZATION_PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party organizationParty = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="DEBIT_GL_ACCOUNT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private GlAccount debitGlAccount = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CREDIT_GL_ACCOUNT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private GlAccount creditGlAccount = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="DEFAULT_DUE_TO_PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party defaultDueToParty = null;

  /**
   * Default constructor.
   */
  public PaycheckItemTypeGlAccount() {
      super();
      this.baseEntityName = "PaycheckItemTypeGlAccount";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("paycheckItemTypeId");this.primaryKeyNames.add("organizationPartyId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public PaycheckItemTypeGlAccount(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param paycheckItemTypeId the paycheckItemTypeId to set
     */
    public void setPaycheckItemTypeId(String paycheckItemTypeId) {
        this.paycheckItemTypeId = paycheckItemTypeId;
    }
    /**
     * Auto generated value setter.
     * @param organizationPartyId the organizationPartyId to set
     */
    public void setOrganizationPartyId(String organizationPartyId) {
        this.organizationPartyId = organizationPartyId;
    }
    /**
     * Auto generated value setter.
     * @param debitGlAccountId the debitGlAccountId to set
     */
    public void setDebitGlAccountId(String debitGlAccountId) {
        this.debitGlAccountId = debitGlAccountId;
    }
    /**
     * Auto generated value setter.
     * @param creditGlAccountId the creditGlAccountId to set
     */
    public void setCreditGlAccountId(String creditGlAccountId) {
        this.creditGlAccountId = creditGlAccountId;
    }
    /**
     * Auto generated value setter.
     * @param defaultDueToPartyId the defaultDueToPartyId to set
     */
    public void setDefaultDueToPartyId(String defaultDueToPartyId) {
        this.defaultDueToPartyId = defaultDueToPartyId;
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
    public String getPaycheckItemTypeId() {
        return this.paycheckItemTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrganizationPartyId() {
        return this.organizationPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDebitGlAccountId() {
        return this.debitGlAccountId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCreditGlAccountId() {
        return this.creditGlAccountId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDefaultDueToPartyId() {
        return this.defaultDueToPartyId;
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
     * Auto generated method that gets the related <code>Party</code> by the relation named <code>OrganizationParty</code>.
     * @return the <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public Party getOrganizationParty() throws RepositoryException {
        if (this.organizationParty == null) {
            this.organizationParty = getRelatedOne(Party.class, "OrganizationParty");
        }
        return this.organizationParty;
    }
    /**
     * Auto generated method that gets the related <code>GlAccount</code> by the relation named <code>DebitGlAccount</code>.
     * @return the <code>GlAccount</code>
     * @throws RepositoryException if an error occurs
     */
    public GlAccount getDebitGlAccount() throws RepositoryException {
        if (this.debitGlAccount == null) {
            this.debitGlAccount = getRelatedOne(GlAccount.class, "DebitGlAccount");
        }
        return this.debitGlAccount;
    }
    /**
     * Auto generated method that gets the related <code>GlAccount</code> by the relation named <code>CreditGlAccount</code>.
     * @return the <code>GlAccount</code>
     * @throws RepositoryException if an error occurs
     */
    public GlAccount getCreditGlAccount() throws RepositoryException {
        if (this.creditGlAccount == null) {
            this.creditGlAccount = getRelatedOne(GlAccount.class, "CreditGlAccount");
        }
        return this.creditGlAccount;
    }
    /**
     * Auto generated method that gets the related <code>Party</code> by the relation named <code>DefaultDueToParty</code>.
     * @return the <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public Party getDefaultDueToParty() throws RepositoryException {
        if (this.defaultDueToParty == null) {
            this.defaultDueToParty = getRelatedOne(Party.class, "DefaultDueToParty");
        }
        return this.defaultDueToParty;
    }

    /**
     * Auto generated value setter.
     * @param organizationParty the organizationParty to set
    */
    public void setOrganizationParty(Party organizationParty) {
        this.organizationParty = organizationParty;
    }
    /**
     * Auto generated value setter.
     * @param debitGlAccount the debitGlAccount to set
    */
    public void setDebitGlAccount(GlAccount debitGlAccount) {
        this.debitGlAccount = debitGlAccount;
    }
    /**
     * Auto generated value setter.
     * @param creditGlAccount the creditGlAccount to set
    */
    public void setCreditGlAccount(GlAccount creditGlAccount) {
        this.creditGlAccount = creditGlAccount;
    }
    /**
     * Auto generated value setter.
     * @param defaultDueToParty the defaultDueToParty to set
    */
    public void setDefaultDueToParty(Party defaultDueToParty) {
        this.defaultDueToParty = defaultDueToParty;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setPaycheckItemTypeId((String) mapValue.get("paycheckItemTypeId"));
        setOrganizationPartyId((String) mapValue.get("organizationPartyId"));
        setDebitGlAccountId((String) mapValue.get("debitGlAccountId"));
        setCreditGlAccountId((String) mapValue.get("creditGlAccountId"));
        setDefaultDueToPartyId((String) mapValue.get("defaultDueToPartyId"));
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
        mapValue.put("paycheckItemTypeId", getPaycheckItemTypeId());
        mapValue.put("organizationPartyId", getOrganizationPartyId());
        mapValue.put("debitGlAccountId", getDebitGlAccountId());
        mapValue.put("creditGlAccountId", getCreditGlAccountId());
        mapValue.put("defaultDueToPartyId", getDefaultDueToPartyId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
