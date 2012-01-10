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
 * Auto generated base entity FinAccount.
 */
@javax.persistence.Entity
@Table(name="FIN_ACCOUNT")
public class FinAccount extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("finAccountId", "FIN_ACCOUNT_ID");
        fields.put("finAccountTypeId", "FIN_ACCOUNT_TYPE_ID");
        fields.put("finAccountName", "FIN_ACCOUNT_NAME");
        fields.put("finAccountCode", "FIN_ACCOUNT_CODE");
        fields.put("finAccountPin", "FIN_ACCOUNT_PIN");
        fields.put("currencyUomId", "CURRENCY_UOM_ID");
        fields.put("organizationPartyId", "ORGANIZATION_PARTY_ID");
        fields.put("ownerPartyId", "OWNER_PARTY_ID");
        fields.put("postToGlAccountId", "POST_TO_GL_ACCOUNT_ID");
        fields.put("fromDate", "FROM_DATE");
        fields.put("thruDate", "THRU_DATE");
        fields.put("isFrozen", "IS_FROZEN");
        fields.put("isRefundable", "IS_REFUNDABLE");
        fields.put("replenishPaymentId", "REPLENISH_PAYMENT_ID");
        fields.put("replenishLevel", "REPLENISH_LEVEL");
        fields.put("actualBalance", "ACTUAL_BALANCE");
        fields.put("availableBalance", "AVAILABLE_BALANCE");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("FinAccount", fields);
}
  public static enum Fields implements EntityFieldInterface<FinAccount> {
    finAccountId("finAccountId"),
    finAccountTypeId("finAccountTypeId"),
    finAccountName("finAccountName"),
    finAccountCode("finAccountCode"),
    finAccountPin("finAccountPin"),
    currencyUomId("currencyUomId"),
    organizationPartyId("organizationPartyId"),
    ownerPartyId("ownerPartyId"),
    postToGlAccountId("postToGlAccountId"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    isFrozen("isFrozen"),
    isRefundable("isRefundable"),
    replenishPaymentId("replenishPaymentId"),
    replenishLevel("replenishLevel"),
    actualBalance("actualBalance"),
    availableBalance("availableBalance"),
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

    @org.hibernate.annotations.GenericGenerator(name="FinAccount_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="FinAccount_GEN")   
    @Id
    
    @Column(name="FIN_ACCOUNT_ID")
    private String finAccountId;
    
    @Column(name="FIN_ACCOUNT_TYPE_ID")
    private String finAccountTypeId;
    
    @Column(name="FIN_ACCOUNT_NAME")
    private String finAccountName;
    
    @Column(name="FIN_ACCOUNT_CODE")
    private String finAccountCode;
    
    @Column(name="FIN_ACCOUNT_PIN")
    private String finAccountPin;
    
    @Column(name="CURRENCY_UOM_ID")
    private String currencyUomId;
    
    @Column(name="ORGANIZATION_PARTY_ID")
    private String organizationPartyId;
    
    @Column(name="OWNER_PARTY_ID")
    private String ownerPartyId;
    
    @Column(name="POST_TO_GL_ACCOUNT_ID")
    private String postToGlAccountId;
    
    @Column(name="FROM_DATE")
    private Timestamp fromDate;
    
    @Column(name="THRU_DATE")
    private Timestamp thruDate;
    
    @Column(name="IS_FROZEN")
    private String isFrozen;
    
    @Column(name="IS_REFUNDABLE")
    private String isRefundable;
    
    @Column(name="REPLENISH_PAYMENT_ID")
    private String replenishPaymentId;
    
    @Column(name="REPLENISH_LEVEL")
    private BigDecimal replenishLevel;
    
    @Column(name="ACTUAL_BALANCE")
    private BigDecimal actualBalance;
    
    @Column(name="AVAILABLE_BALANCE")
    private BigDecimal availableBalance;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="FIN_ACCOUNT_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private FinAccountType finAccountType = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CURRENCY_UOM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Uom currencyUom = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ORGANIZATION_PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party organizationParty = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="OWNER_PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party ownerParty = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="POST_TO_GL_ACCOUNT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private GlAccount postToGlAccount = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="REPLENISH_PAYMENT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private PaymentMethod replenishPaymentMethod = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="FIN_ACCOUNT_TYPE_ID")
    private List<FinAccountTypeAttr> finAccountTypeAttrs = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="finAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="FIN_ACCOUNT_ID")
    private List<FinAccountAttribute> finAccountAttributes = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="FIN_ACCOUNT_ID")
    private List<FinAccountAuth> finAccountAuths = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="finAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="FIN_ACCOUNT_ID")
    private List<FinAccountRole> finAccountRoles = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="FIN_ACCOUNT_ID")
    private List<FinAccountTrans> finAccountTranses = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="FIN_ACCOUNT_ID")
    private List<ReturnHeader> returnHeaders = null;

  /**
   * Default constructor.
   */
  public FinAccount() {
      super();
      this.baseEntityName = "FinAccount";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("finAccountId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public FinAccount(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param finAccountId the finAccountId to set
     */
    public void setFinAccountId(String finAccountId) {
        this.finAccountId = finAccountId;
    }
    /**
     * Auto generated value setter.
     * @param finAccountTypeId the finAccountTypeId to set
     */
    public void setFinAccountTypeId(String finAccountTypeId) {
        this.finAccountTypeId = finAccountTypeId;
    }
    /**
     * Auto generated value setter.
     * @param finAccountName the finAccountName to set
     */
    public void setFinAccountName(String finAccountName) {
        this.finAccountName = finAccountName;
    }
    /**
     * Auto generated value setter.
     * @param finAccountCode the finAccountCode to set
     */
    public void setFinAccountCode(String finAccountCode) {
        this.finAccountCode = finAccountCode;
    }
    /**
     * Auto generated value setter.
     * @param finAccountPin the finAccountPin to set
     */
    public void setFinAccountPin(String finAccountPin) {
        this.finAccountPin = finAccountPin;
    }
    /**
     * Auto generated value setter.
     * @param currencyUomId the currencyUomId to set
     */
    public void setCurrencyUomId(String currencyUomId) {
        this.currencyUomId = currencyUomId;
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
     * @param ownerPartyId the ownerPartyId to set
     */
    public void setOwnerPartyId(String ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }
    /**
     * Auto generated value setter.
     * @param postToGlAccountId the postToGlAccountId to set
     */
    public void setPostToGlAccountId(String postToGlAccountId) {
        this.postToGlAccountId = postToGlAccountId;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * Auto generated value setter.
     * @param thruDate the thruDate to set
     */
    public void setThruDate(Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    /**
     * Auto generated value setter.
     * @param isFrozen the isFrozen to set
     */
    public void setIsFrozen(String isFrozen) {
        this.isFrozen = isFrozen;
    }
    /**
     * Auto generated value setter.
     * @param isRefundable the isRefundable to set
     */
    public void setIsRefundable(String isRefundable) {
        this.isRefundable = isRefundable;
    }
    /**
     * Auto generated value setter.
     * @param replenishPaymentId the replenishPaymentId to set
     */
    public void setReplenishPaymentId(String replenishPaymentId) {
        this.replenishPaymentId = replenishPaymentId;
    }
    /**
     * Auto generated value setter.
     * @param replenishLevel the replenishLevel to set
     */
    public void setReplenishLevel(BigDecimal replenishLevel) {
        this.replenishLevel = replenishLevel;
    }
    /**
     * Auto generated value setter.
     * @param actualBalance the actualBalance to set
     */
    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }
    /**
     * Auto generated value setter.
     * @param availableBalance the availableBalance to set
     */
    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
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
    public String getFinAccountId() {
        return this.finAccountId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFinAccountTypeId() {
        return this.finAccountTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFinAccountName() {
        return this.finAccountName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFinAccountCode() {
        return this.finAccountCode;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFinAccountPin() {
        return this.finAccountPin;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCurrencyUomId() {
        return this.currencyUomId;
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
    public String getOwnerPartyId() {
        return this.ownerPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPostToGlAccountId() {
        return this.postToGlAccountId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getFromDate() {
        return this.fromDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getThruDate() {
        return this.thruDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getIsFrozen() {
        return this.isFrozen;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getIsRefundable() {
        return this.isRefundable;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getReplenishPaymentId() {
        return this.replenishPaymentId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getReplenishLevel() {
        return this.replenishLevel;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualBalance() {
        return this.actualBalance;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getAvailableBalance() {
        return this.availableBalance;
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
     * Auto generated method that gets the related <code>FinAccountType</code> by the relation named <code>FinAccountType</code>.
     * @return the <code>FinAccountType</code>
     * @throws RepositoryException if an error occurs
     */
    public FinAccountType getFinAccountType() throws RepositoryException {
        if (this.finAccountType == null) {
            this.finAccountType = getRelatedOne(FinAccountType.class, "FinAccountType");
        }
        return this.finAccountType;
    }
    /**
     * Auto generated method that gets the related <code>Uom</code> by the relation named <code>CurrencyUom</code>.
     * @return the <code>Uom</code>
     * @throws RepositoryException if an error occurs
     */
    public Uom getCurrencyUom() throws RepositoryException {
        if (this.currencyUom == null) {
            this.currencyUom = getRelatedOne(Uom.class, "CurrencyUom");
        }
        return this.currencyUom;
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
     * Auto generated method that gets the related <code>Party</code> by the relation named <code>OwnerParty</code>.
     * @return the <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public Party getOwnerParty() throws RepositoryException {
        if (this.ownerParty == null) {
            this.ownerParty = getRelatedOne(Party.class, "OwnerParty");
        }
        return this.ownerParty;
    }
    /**
     * Auto generated method that gets the related <code>GlAccount</code> by the relation named <code>PostToGlAccount</code>.
     * @return the <code>GlAccount</code>
     * @throws RepositoryException if an error occurs
     */
    public GlAccount getPostToGlAccount() throws RepositoryException {
        if (this.postToGlAccount == null) {
            this.postToGlAccount = getRelatedOne(GlAccount.class, "PostToGlAccount");
        }
        return this.postToGlAccount;
    }
    /**
     * Auto generated method that gets the related <code>PaymentMethod</code> by the relation named <code>ReplenishPaymentMethod</code>.
     * @return the <code>PaymentMethod</code>
     * @throws RepositoryException if an error occurs
     */
    public PaymentMethod getReplenishPaymentMethod() throws RepositoryException {
        if (this.replenishPaymentMethod == null) {
            this.replenishPaymentMethod = getRelatedOne(PaymentMethod.class, "ReplenishPaymentMethod");
        }
        return this.replenishPaymentMethod;
    }
    /**
     * Auto generated method that gets the related <code>FinAccountTypeAttr</code> by the relation named <code>FinAccountTypeAttr</code>.
     * @return the list of <code>FinAccountTypeAttr</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FinAccountTypeAttr> getFinAccountTypeAttrs() throws RepositoryException {
        if (this.finAccountTypeAttrs == null) {
            this.finAccountTypeAttrs = getRelated(FinAccountTypeAttr.class, "FinAccountTypeAttr");
        }
        return this.finAccountTypeAttrs;
    }
    /**
     * Auto generated method that gets the related <code>FinAccountAttribute</code> by the relation named <code>FinAccountAttribute</code>.
     * @return the list of <code>FinAccountAttribute</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FinAccountAttribute> getFinAccountAttributes() throws RepositoryException {
        if (this.finAccountAttributes == null) {
            this.finAccountAttributes = getRelated(FinAccountAttribute.class, "FinAccountAttribute");
        }
        return this.finAccountAttributes;
    }
    /**
     * Auto generated method that gets the related <code>FinAccountAuth</code> by the relation named <code>FinAccountAuth</code>.
     * @return the list of <code>FinAccountAuth</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FinAccountAuth> getFinAccountAuths() throws RepositoryException {
        if (this.finAccountAuths == null) {
            this.finAccountAuths = getRelated(FinAccountAuth.class, "FinAccountAuth");
        }
        return this.finAccountAuths;
    }
    /**
     * Auto generated method that gets the related <code>FinAccountRole</code> by the relation named <code>FinAccountRole</code>.
     * @return the list of <code>FinAccountRole</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FinAccountRole> getFinAccountRoles() throws RepositoryException {
        if (this.finAccountRoles == null) {
            this.finAccountRoles = getRelated(FinAccountRole.class, "FinAccountRole");
        }
        return this.finAccountRoles;
    }
    /**
     * Auto generated method that gets the related <code>FinAccountTrans</code> by the relation named <code>FinAccountTrans</code>.
     * @return the list of <code>FinAccountTrans</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FinAccountTrans> getFinAccountTranses() throws RepositoryException {
        if (this.finAccountTranses == null) {
            this.finAccountTranses = getRelated(FinAccountTrans.class, "FinAccountTrans");
        }
        return this.finAccountTranses;
    }
    /**
     * Auto generated method that gets the related <code>ReturnHeader</code> by the relation named <code>ReturnHeader</code>.
     * @return the list of <code>ReturnHeader</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ReturnHeader> getReturnHeaders() throws RepositoryException {
        if (this.returnHeaders == null) {
            this.returnHeaders = getRelated(ReturnHeader.class, "ReturnHeader");
        }
        return this.returnHeaders;
    }

    /**
     * Auto generated value setter.
     * @param finAccountType the finAccountType to set
    */
    public void setFinAccountType(FinAccountType finAccountType) {
        this.finAccountType = finAccountType;
    }
    /**
     * Auto generated value setter.
     * @param currencyUom the currencyUom to set
    */
    public void setCurrencyUom(Uom currencyUom) {
        this.currencyUom = currencyUom;
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
     * @param ownerParty the ownerParty to set
    */
    public void setOwnerParty(Party ownerParty) {
        this.ownerParty = ownerParty;
    }
    /**
     * Auto generated value setter.
     * @param postToGlAccount the postToGlAccount to set
    */
    public void setPostToGlAccount(GlAccount postToGlAccount) {
        this.postToGlAccount = postToGlAccount;
    }
    /**
     * Auto generated value setter.
     * @param replenishPaymentMethod the replenishPaymentMethod to set
    */
    public void setReplenishPaymentMethod(PaymentMethod replenishPaymentMethod) {
        this.replenishPaymentMethod = replenishPaymentMethod;
    }
    /**
     * Auto generated value setter.
     * @param finAccountTypeAttrs the finAccountTypeAttrs to set
    */
    public void setFinAccountTypeAttrs(List<FinAccountTypeAttr> finAccountTypeAttrs) {
        this.finAccountTypeAttrs = finAccountTypeAttrs;
    }
    /**
     * Auto generated value setter.
     * @param finAccountAttributes the finAccountAttributes to set
    */
    public void setFinAccountAttributes(List<FinAccountAttribute> finAccountAttributes) {
        this.finAccountAttributes = finAccountAttributes;
    }
    /**
     * Auto generated value setter.
     * @param finAccountAuths the finAccountAuths to set
    */
    public void setFinAccountAuths(List<FinAccountAuth> finAccountAuths) {
        this.finAccountAuths = finAccountAuths;
    }
    /**
     * Auto generated value setter.
     * @param finAccountRoles the finAccountRoles to set
    */
    public void setFinAccountRoles(List<FinAccountRole> finAccountRoles) {
        this.finAccountRoles = finAccountRoles;
    }
    /**
     * Auto generated value setter.
     * @param finAccountTranses the finAccountTranses to set
    */
    public void setFinAccountTranses(List<FinAccountTrans> finAccountTranses) {
        this.finAccountTranses = finAccountTranses;
    }
    /**
     * Auto generated value setter.
     * @param returnHeaders the returnHeaders to set
    */
    public void setReturnHeaders(List<ReturnHeader> returnHeaders) {
        this.returnHeaders = returnHeaders;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addFinAccountAttribute(FinAccountAttribute finAccountAttribute) {
        if (this.finAccountAttributes == null) {
            this.finAccountAttributes = new ArrayList<FinAccountAttribute>();
        }
        this.finAccountAttributes.add(finAccountAttribute);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeFinAccountAttribute(FinAccountAttribute finAccountAttribute) {
        if (this.finAccountAttributes == null) {
            return;
        }
        this.finAccountAttributes.remove(finAccountAttribute);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearFinAccountAttribute() {
        if (this.finAccountAttributes == null) {
            return;
        }
        this.finAccountAttributes.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addFinAccountRole(FinAccountRole finAccountRole) {
        if (this.finAccountRoles == null) {
            this.finAccountRoles = new ArrayList<FinAccountRole>();
        }
        this.finAccountRoles.add(finAccountRole);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeFinAccountRole(FinAccountRole finAccountRole) {
        if (this.finAccountRoles == null) {
            return;
        }
        this.finAccountRoles.remove(finAccountRole);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearFinAccountRole() {
        if (this.finAccountRoles == null) {
            return;
        }
        this.finAccountRoles.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setFinAccountId((String) mapValue.get("finAccountId"));
        setFinAccountTypeId((String) mapValue.get("finAccountTypeId"));
        setFinAccountName((String) mapValue.get("finAccountName"));
        setFinAccountCode((String) mapValue.get("finAccountCode"));
        setFinAccountPin((String) mapValue.get("finAccountPin"));
        setCurrencyUomId((String) mapValue.get("currencyUomId"));
        setOrganizationPartyId((String) mapValue.get("organizationPartyId"));
        setOwnerPartyId((String) mapValue.get("ownerPartyId"));
        setPostToGlAccountId((String) mapValue.get("postToGlAccountId"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        setIsFrozen((String) mapValue.get("isFrozen"));
        setIsRefundable((String) mapValue.get("isRefundable"));
        setReplenishPaymentId((String) mapValue.get("replenishPaymentId"));
        setReplenishLevel(convertToBigDecimal(mapValue.get("replenishLevel")));
        setActualBalance(convertToBigDecimal(mapValue.get("actualBalance")));
        setAvailableBalance(convertToBigDecimal(mapValue.get("availableBalance")));
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
        mapValue.put("finAccountId", getFinAccountId());
        mapValue.put("finAccountTypeId", getFinAccountTypeId());
        mapValue.put("finAccountName", getFinAccountName());
        mapValue.put("finAccountCode", getFinAccountCode());
        mapValue.put("finAccountPin", getFinAccountPin());
        mapValue.put("currencyUomId", getCurrencyUomId());
        mapValue.put("organizationPartyId", getOrganizationPartyId());
        mapValue.put("ownerPartyId", getOwnerPartyId());
        mapValue.put("postToGlAccountId", getPostToGlAccountId());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("isFrozen", getIsFrozen());
        mapValue.put("isRefundable", getIsRefundable());
        mapValue.put("replenishPaymentId", getReplenishPaymentId());
        mapValue.put("replenishLevel", getReplenishLevel());
        mapValue.put("actualBalance", getActualBalance());
        mapValue.put("availableBalance", getAvailableBalance());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
