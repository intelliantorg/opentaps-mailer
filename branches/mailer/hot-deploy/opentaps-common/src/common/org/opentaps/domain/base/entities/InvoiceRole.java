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
 * Auto generated base entity InvoiceRole.
 */
@javax.persistence.Entity
@Table(name="INVOICE_ROLE")
@IdClass(InvoiceRolePk.class)
public class InvoiceRole extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("invoiceId", "INVOICE_ID");
        fields.put("partyId", "PARTY_ID");
        fields.put("roleTypeId", "ROLE_TYPE_ID");
        fields.put("datetimePerformed", "DATETIME_PERFORMED");
        fields.put("percentage", "PERCENTAGE");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("InvoiceRole", fields);
}
  public static enum Fields implements EntityFieldInterface<InvoiceRole> {
    invoiceId("invoiceId"),
    partyId("partyId"),
    roleTypeId("roleTypeId"),
    datetimePerformed("datetimePerformed"),
    percentage("percentage"),
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
    
    @Column(name="INVOICE_ID")
    private String invoiceId;
    @Id
    
    @Column(name="PARTY_ID")
    private String partyId;
    @Id
    
    @Column(name="ROLE_TYPE_ID")
    private String roleTypeId;
    
    @Column(name="DATETIME_PERFORMED")
    private Timestamp datetimePerformed;
    
    @Column(name="PERCENTAGE")
    private BigDecimal percentage;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="INVOICE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Invoice invoice = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party party = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ROLE_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private RoleType roleType = null;
    private transient PartyRole partyRole = null;

  /**
   * Default constructor.
   */
  public InvoiceRole() {
      super();
      this.baseEntityName = "InvoiceRole";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("invoiceId");this.primaryKeyNames.add("partyId");this.primaryKeyNames.add("roleTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public InvoiceRole(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    /**
     * Auto generated value setter.
     * @param partyId the partyId to set
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    /**
     * Auto generated value setter.
     * @param roleTypeId the roleTypeId to set
     */
    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    /**
     * Auto generated value setter.
     * @param datetimePerformed the datetimePerformed to set
     */
    public void setDatetimePerformed(Timestamp datetimePerformed) {
        this.datetimePerformed = datetimePerformed;
    }
    /**
     * Auto generated value setter.
     * @param percentage the percentage to set
     */
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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
    public String getInvoiceId() {
        return this.invoiceId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPartyId() {
        return this.partyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getRoleTypeId() {
        return this.roleTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getDatetimePerformed() {
        return this.datetimePerformed;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getPercentage() {
        return this.percentage;
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
     * Auto generated method that gets the related <code>Invoice</code> by the relation named <code>Invoice</code>.
     * @return the <code>Invoice</code>
     * @throws RepositoryException if an error occurs
     */
    public Invoice getInvoice() throws RepositoryException {
        if (this.invoice == null) {
            this.invoice = getRelatedOne(Invoice.class, "Invoice");
        }
        return this.invoice;
    }
    /**
     * Auto generated method that gets the related <code>Party</code> by the relation named <code>Party</code>.
     * @return the <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public Party getParty() throws RepositoryException {
        if (this.party == null) {
            this.party = getRelatedOne(Party.class, "Party");
        }
        return this.party;
    }
    /**
     * Auto generated method that gets the related <code>RoleType</code> by the relation named <code>RoleType</code>.
     * @return the <code>RoleType</code>
     * @throws RepositoryException if an error occurs
     */
    public RoleType getRoleType() throws RepositoryException {
        if (this.roleType == null) {
            this.roleType = getRelatedOne(RoleType.class, "RoleType");
        }
        return this.roleType;
    }
    /**
     * Auto generated method that gets the related <code>PartyRole</code> by the relation named <code>PartyRole</code>.
     * @return the <code>PartyRole</code>
     * @throws RepositoryException if an error occurs
     */
    public PartyRole getPartyRole() throws RepositoryException {
        if (this.partyRole == null) {
            this.partyRole = getRelatedOne(PartyRole.class, "PartyRole");
        }
        return this.partyRole;
    }

    /**
     * Auto generated value setter.
     * @param invoice the invoice to set
    */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    /**
     * Auto generated value setter.
     * @param party the party to set
    */
    public void setParty(Party party) {
        this.party = party;
    }
    /**
     * Auto generated value setter.
     * @param roleType the roleType to set
    */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    /**
     * Auto generated value setter.
     * @param partyRole the partyRole to set
    */
    public void setPartyRole(PartyRole partyRole) {
        this.partyRole = partyRole;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setInvoiceId((String) mapValue.get("invoiceId"));
        setPartyId((String) mapValue.get("partyId"));
        setRoleTypeId((String) mapValue.get("roleTypeId"));
        setDatetimePerformed((Timestamp) mapValue.get("datetimePerformed"));
        setPercentage(convertToBigDecimal(mapValue.get("percentage")));
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
        mapValue.put("invoiceId", getInvoiceId());
        mapValue.put("partyId", getPartyId());
        mapValue.put("roleTypeId", getRoleTypeId());
        mapValue.put("datetimePerformed", getDatetimePerformed());
        mapValue.put("percentage", getPercentage());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
