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
 * Auto generated base entity Quote.
 */
@javax.persistence.Entity
@Table(name="QUOTE")
public class Quote extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("quoteId", "QUOTE_ID");
        fields.put("quoteTypeId", "QUOTE_TYPE_ID");
        fields.put("partyId", "PARTY_ID");
        fields.put("issueDate", "ISSUE_DATE");
        fields.put("statusId", "STATUS_ID");
        fields.put("currencyUomId", "CURRENCY_UOM_ID");
        fields.put("productStoreId", "PRODUCT_STORE_ID");
        fields.put("salesChannelEnumId", "SALES_CHANNEL_ENUM_ID");
        fields.put("validFromDate", "VALID_FROM_DATE");
        fields.put("validThruDate", "VALID_THRU_DATE");
        fields.put("quoteName", "QUOTE_NAME");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("Quote", fields);
}
  public static enum Fields implements EntityFieldInterface<Quote> {
    quoteId("quoteId"),
    quoteTypeId("quoteTypeId"),
    partyId("partyId"),
    issueDate("issueDate"),
    statusId("statusId"),
    currencyUomId("currencyUomId"),
    productStoreId("productStoreId"),
    salesChannelEnumId("salesChannelEnumId"),
    validFromDate("validFromDate"),
    validThruDate("validThruDate"),
    quoteName("quoteName"),
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

    @org.hibernate.annotations.GenericGenerator(name="Quote_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="Quote_GEN")   
    @Id
    
    @Column(name="QUOTE_ID")
    private String quoteId;
    
    @Column(name="QUOTE_TYPE_ID")
    private String quoteTypeId;
    
    @Column(name="PARTY_ID")
    private String partyId;
    
    @Column(name="ISSUE_DATE")
    private Timestamp issueDate;
    
    @Column(name="STATUS_ID")
    private String statusId;
    
    @Column(name="CURRENCY_UOM_ID")
    private String currencyUomId;
    
    @Column(name="PRODUCT_STORE_ID")
    private String productStoreId;
    
    @Column(name="SALES_CHANNEL_ENUM_ID")
    private String salesChannelEnumId;
    
    @Column(name="VALID_FROM_DATE")
    private Timestamp validFromDate;
    
    @Column(name="VALID_THRU_DATE")
    private Timestamp validThruDate;
    
    @Column(name="QUOTE_NAME")
    private String quoteName;
    
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
    @JoinColumn(name="QUOTE_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private QuoteType quoteType = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="QUOTE_TYPE_ID")
    private List<QuoteTypeAttr> quoteTypeAttrs = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party party = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private StatusItem statusItem = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CURRENCY_UOM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Uom uom = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_STORE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ProductStore productStore = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="SALES_CHANNEL_ENUM_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Enumeration salesChannelEnumeration = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteAdjustment> quoteAdjustments = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteAttribute> quoteAttributes = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteCoefficient> quoteCoefficients = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteItem> quoteItems = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteRole> quoteRoles = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteTerm> quoteTerms = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<QuoteWorkEffort> quoteWorkEfforts = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="QUOTE_ID")
    private List<SalesOpportunityQuote> salesOpportunityQuotes = null;

  /**
   * Default constructor.
   */
  public Quote() {
      super();
      this.baseEntityName = "Quote";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("quoteId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public Quote(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param quoteId the quoteId to set
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    /**
     * Auto generated value setter.
     * @param quoteTypeId the quoteTypeId to set
     */
    public void setQuoteTypeId(String quoteTypeId) {
        this.quoteTypeId = quoteTypeId;
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
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
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
     * @param productStoreId the productStoreId to set
     */
    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }
    /**
     * Auto generated value setter.
     * @param salesChannelEnumId the salesChannelEnumId to set
     */
    public void setSalesChannelEnumId(String salesChannelEnumId) {
        this.salesChannelEnumId = salesChannelEnumId;
    }
    /**
     * Auto generated value setter.
     * @param validFromDate the validFromDate to set
     */
    public void setValidFromDate(Timestamp validFromDate) {
        this.validFromDate = validFromDate;
    }
    /**
     * Auto generated value setter.
     * @param validThruDate the validThruDate to set
     */
    public void setValidThruDate(Timestamp validThruDate) {
        this.validThruDate = validThruDate;
    }
    /**
     * Auto generated value setter.
     * @param quoteName the quoteName to set
     */
    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
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
    public String getQuoteId() {
        return this.quoteId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getQuoteTypeId() {
        return this.quoteTypeId;
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
     * @return <code>Timestamp</code>
     */
    public Timestamp getIssueDate() {
        return this.issueDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStatusId() {
        return this.statusId;
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
    public String getProductStoreId() {
        return this.productStoreId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getSalesChannelEnumId() {
        return this.salesChannelEnumId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getValidFromDate() {
        return this.validFromDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getValidThruDate() {
        return this.validThruDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getQuoteName() {
        return this.quoteName;
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
     * Auto generated method that gets the related <code>QuoteType</code> by the relation named <code>QuoteType</code>.
     * @return the <code>QuoteType</code>
     * @throws RepositoryException if an error occurs
     */
    public QuoteType getQuoteType() throws RepositoryException {
        if (this.quoteType == null) {
            this.quoteType = getRelatedOne(QuoteType.class, "QuoteType");
        }
        return this.quoteType;
    }
    /**
     * Auto generated method that gets the related <code>QuoteTypeAttr</code> by the relation named <code>QuoteTypeAttr</code>.
     * @return the list of <code>QuoteTypeAttr</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteTypeAttr> getQuoteTypeAttrs() throws RepositoryException {
        if (this.quoteTypeAttrs == null) {
            this.quoteTypeAttrs = getRelated(QuoteTypeAttr.class, "QuoteTypeAttr");
        }
        return this.quoteTypeAttrs;
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
     * Auto generated method that gets the related <code>StatusItem</code> by the relation named <code>StatusItem</code>.
     * @return the <code>StatusItem</code>
     * @throws RepositoryException if an error occurs
     */
    public StatusItem getStatusItem() throws RepositoryException {
        if (this.statusItem == null) {
            this.statusItem = getRelatedOne(StatusItem.class, "StatusItem");
        }
        return this.statusItem;
    }
    /**
     * Auto generated method that gets the related <code>Uom</code> by the relation named <code>Uom</code>.
     * @return the <code>Uom</code>
     * @throws RepositoryException if an error occurs
     */
    public Uom getUom() throws RepositoryException {
        if (this.uom == null) {
            this.uom = getRelatedOne(Uom.class, "Uom");
        }
        return this.uom;
    }
    /**
     * Auto generated method that gets the related <code>ProductStore</code> by the relation named <code>ProductStore</code>.
     * @return the <code>ProductStore</code>
     * @throws RepositoryException if an error occurs
     */
    public ProductStore getProductStore() throws RepositoryException {
        if (this.productStore == null) {
            this.productStore = getRelatedOne(ProductStore.class, "ProductStore");
        }
        return this.productStore;
    }
    /**
     * Auto generated method that gets the related <code>Enumeration</code> by the relation named <code>SalesChannelEnumeration</code>.
     * @return the <code>Enumeration</code>
     * @throws RepositoryException if an error occurs
     */
    public Enumeration getSalesChannelEnumeration() throws RepositoryException {
        if (this.salesChannelEnumeration == null) {
            this.salesChannelEnumeration = getRelatedOne(Enumeration.class, "SalesChannelEnumeration");
        }
        return this.salesChannelEnumeration;
    }
    /**
     * Auto generated method that gets the related <code>QuoteAdjustment</code> by the relation named <code>QuoteAdjustment</code>.
     * @return the list of <code>QuoteAdjustment</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteAdjustment> getQuoteAdjustments() throws RepositoryException {
        if (this.quoteAdjustments == null) {
            this.quoteAdjustments = getRelated(QuoteAdjustment.class, "QuoteAdjustment");
        }
        return this.quoteAdjustments;
    }
    /**
     * Auto generated method that gets the related <code>QuoteAttribute</code> by the relation named <code>QuoteAttribute</code>.
     * @return the list of <code>QuoteAttribute</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteAttribute> getQuoteAttributes() throws RepositoryException {
        if (this.quoteAttributes == null) {
            this.quoteAttributes = getRelated(QuoteAttribute.class, "QuoteAttribute");
        }
        return this.quoteAttributes;
    }
    /**
     * Auto generated method that gets the related <code>QuoteCoefficient</code> by the relation named <code>QuoteCoefficient</code>.
     * @return the list of <code>QuoteCoefficient</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteCoefficient> getQuoteCoefficients() throws RepositoryException {
        if (this.quoteCoefficients == null) {
            this.quoteCoefficients = getRelated(QuoteCoefficient.class, "QuoteCoefficient");
        }
        return this.quoteCoefficients;
    }
    /**
     * Auto generated method that gets the related <code>QuoteItem</code> by the relation named <code>QuoteItem</code>.
     * @return the list of <code>QuoteItem</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteItem> getQuoteItems() throws RepositoryException {
        if (this.quoteItems == null) {
            this.quoteItems = getRelated(QuoteItem.class, "QuoteItem");
        }
        return this.quoteItems;
    }
    /**
     * Auto generated method that gets the related <code>QuoteRole</code> by the relation named <code>QuoteRole</code>.
     * @return the list of <code>QuoteRole</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteRole> getQuoteRoles() throws RepositoryException {
        if (this.quoteRoles == null) {
            this.quoteRoles = getRelated(QuoteRole.class, "QuoteRole");
        }
        return this.quoteRoles;
    }
    /**
     * Auto generated method that gets the related <code>QuoteTerm</code> by the relation named <code>QuoteTerm</code>.
     * @return the list of <code>QuoteTerm</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteTerm> getQuoteTerms() throws RepositoryException {
        if (this.quoteTerms == null) {
            this.quoteTerms = getRelated(QuoteTerm.class, "QuoteTerm");
        }
        return this.quoteTerms;
    }
    /**
     * Auto generated method that gets the related <code>QuoteWorkEffort</code> by the relation named <code>QuoteWorkEffort</code>.
     * @return the list of <code>QuoteWorkEffort</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends QuoteWorkEffort> getQuoteWorkEfforts() throws RepositoryException {
        if (this.quoteWorkEfforts == null) {
            this.quoteWorkEfforts = getRelated(QuoteWorkEffort.class, "QuoteWorkEffort");
        }
        return this.quoteWorkEfforts;
    }
    /**
     * Auto generated method that gets the related <code>SalesOpportunityQuote</code> by the relation named <code>SalesOpportunityQuote</code>.
     * @return the list of <code>SalesOpportunityQuote</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends SalesOpportunityQuote> getSalesOpportunityQuotes() throws RepositoryException {
        if (this.salesOpportunityQuotes == null) {
            this.salesOpportunityQuotes = getRelated(SalesOpportunityQuote.class, "SalesOpportunityQuote");
        }
        return this.salesOpportunityQuotes;
    }

    /**
     * Auto generated value setter.
     * @param quoteType the quoteType to set
    */
    public void setQuoteType(QuoteType quoteType) {
        this.quoteType = quoteType;
    }
    /**
     * Auto generated value setter.
     * @param quoteTypeAttrs the quoteTypeAttrs to set
    */
    public void setQuoteTypeAttrs(List<QuoteTypeAttr> quoteTypeAttrs) {
        this.quoteTypeAttrs = quoteTypeAttrs;
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
     * @param statusItem the statusItem to set
    */
    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }
    /**
     * Auto generated value setter.
     * @param uom the uom to set
    */
    public void setUom(Uom uom) {
        this.uom = uom;
    }
    /**
     * Auto generated value setter.
     * @param productStore the productStore to set
    */
    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }
    /**
     * Auto generated value setter.
     * @param salesChannelEnumeration the salesChannelEnumeration to set
    */
    public void setSalesChannelEnumeration(Enumeration salesChannelEnumeration) {
        this.salesChannelEnumeration = salesChannelEnumeration;
    }
    /**
     * Auto generated value setter.
     * @param quoteAdjustments the quoteAdjustments to set
    */
    public void setQuoteAdjustments(List<QuoteAdjustment> quoteAdjustments) {
        this.quoteAdjustments = quoteAdjustments;
    }
    /**
     * Auto generated value setter.
     * @param quoteAttributes the quoteAttributes to set
    */
    public void setQuoteAttributes(List<QuoteAttribute> quoteAttributes) {
        this.quoteAttributes = quoteAttributes;
    }
    /**
     * Auto generated value setter.
     * @param quoteCoefficients the quoteCoefficients to set
    */
    public void setQuoteCoefficients(List<QuoteCoefficient> quoteCoefficients) {
        this.quoteCoefficients = quoteCoefficients;
    }
    /**
     * Auto generated value setter.
     * @param quoteItems the quoteItems to set
    */
    public void setQuoteItems(List<QuoteItem> quoteItems) {
        this.quoteItems = quoteItems;
    }
    /**
     * Auto generated value setter.
     * @param quoteRoles the quoteRoles to set
    */
    public void setQuoteRoles(List<QuoteRole> quoteRoles) {
        this.quoteRoles = quoteRoles;
    }
    /**
     * Auto generated value setter.
     * @param quoteTerms the quoteTerms to set
    */
    public void setQuoteTerms(List<QuoteTerm> quoteTerms) {
        this.quoteTerms = quoteTerms;
    }
    /**
     * Auto generated value setter.
     * @param quoteWorkEfforts the quoteWorkEfforts to set
    */
    public void setQuoteWorkEfforts(List<QuoteWorkEffort> quoteWorkEfforts) {
        this.quoteWorkEfforts = quoteWorkEfforts;
    }
    /**
     * Auto generated value setter.
     * @param salesOpportunityQuotes the salesOpportunityQuotes to set
    */
    public void setSalesOpportunityQuotes(List<SalesOpportunityQuote> salesOpportunityQuotes) {
        this.salesOpportunityQuotes = salesOpportunityQuotes;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteAttribute(QuoteAttribute quoteAttribute) {
        if (this.quoteAttributes == null) {
            this.quoteAttributes = new ArrayList<QuoteAttribute>();
        }
        this.quoteAttributes.add(quoteAttribute);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteAttribute(QuoteAttribute quoteAttribute) {
        if (this.quoteAttributes == null) {
            return;
        }
        this.quoteAttributes.remove(quoteAttribute);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteAttribute() {
        if (this.quoteAttributes == null) {
            return;
        }
        this.quoteAttributes.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteCoefficient(QuoteCoefficient quoteCoefficient) {
        if (this.quoteCoefficients == null) {
            this.quoteCoefficients = new ArrayList<QuoteCoefficient>();
        }
        this.quoteCoefficients.add(quoteCoefficient);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteCoefficient(QuoteCoefficient quoteCoefficient) {
        if (this.quoteCoefficients == null) {
            return;
        }
        this.quoteCoefficients.remove(quoteCoefficient);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteCoefficient() {
        if (this.quoteCoefficients == null) {
            return;
        }
        this.quoteCoefficients.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteItem(QuoteItem quoteItem) {
        if (this.quoteItems == null) {
            this.quoteItems = new ArrayList<QuoteItem>();
        }
        this.quoteItems.add(quoteItem);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteItem(QuoteItem quoteItem) {
        if (this.quoteItems == null) {
            return;
        }
        this.quoteItems.remove(quoteItem);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteItem() {
        if (this.quoteItems == null) {
            return;
        }
        this.quoteItems.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteRole(QuoteRole quoteRole) {
        if (this.quoteRoles == null) {
            this.quoteRoles = new ArrayList<QuoteRole>();
        }
        this.quoteRoles.add(quoteRole);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteRole(QuoteRole quoteRole) {
        if (this.quoteRoles == null) {
            return;
        }
        this.quoteRoles.remove(quoteRole);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteRole() {
        if (this.quoteRoles == null) {
            return;
        }
        this.quoteRoles.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteTerm(QuoteTerm quoteTerm) {
        if (this.quoteTerms == null) {
            this.quoteTerms = new ArrayList<QuoteTerm>();
        }
        this.quoteTerms.add(quoteTerm);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteTerm(QuoteTerm quoteTerm) {
        if (this.quoteTerms == null) {
            return;
        }
        this.quoteTerms.remove(quoteTerm);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteTerm() {
        if (this.quoteTerms == null) {
            return;
        }
        this.quoteTerms.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addQuoteWorkEffort(QuoteWorkEffort quoteWorkEffort) {
        if (this.quoteWorkEfforts == null) {
            this.quoteWorkEfforts = new ArrayList<QuoteWorkEffort>();
        }
        this.quoteWorkEfforts.add(quoteWorkEffort);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeQuoteWorkEffort(QuoteWorkEffort quoteWorkEffort) {
        if (this.quoteWorkEfforts == null) {
            return;
        }
        this.quoteWorkEfforts.remove(quoteWorkEffort);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearQuoteWorkEffort() {
        if (this.quoteWorkEfforts == null) {
            return;
        }
        this.quoteWorkEfforts.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addSalesOpportunityQuote(SalesOpportunityQuote salesOpportunityQuote) {
        if (this.salesOpportunityQuotes == null) {
            this.salesOpportunityQuotes = new ArrayList<SalesOpportunityQuote>();
        }
        this.salesOpportunityQuotes.add(salesOpportunityQuote);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeSalesOpportunityQuote(SalesOpportunityQuote salesOpportunityQuote) {
        if (this.salesOpportunityQuotes == null) {
            return;
        }
        this.salesOpportunityQuotes.remove(salesOpportunityQuote);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearSalesOpportunityQuote() {
        if (this.salesOpportunityQuotes == null) {
            return;
        }
        this.salesOpportunityQuotes.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setQuoteId((String) mapValue.get("quoteId"));
        setQuoteTypeId((String) mapValue.get("quoteTypeId"));
        setPartyId((String) mapValue.get("partyId"));
        setIssueDate((Timestamp) mapValue.get("issueDate"));
        setStatusId((String) mapValue.get("statusId"));
        setCurrencyUomId((String) mapValue.get("currencyUomId"));
        setProductStoreId((String) mapValue.get("productStoreId"));
        setSalesChannelEnumId((String) mapValue.get("salesChannelEnumId"));
        setValidFromDate((Timestamp) mapValue.get("validFromDate"));
        setValidThruDate((Timestamp) mapValue.get("validThruDate"));
        setQuoteName((String) mapValue.get("quoteName"));
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
        mapValue.put("quoteId", getQuoteId());
        mapValue.put("quoteTypeId", getQuoteTypeId());
        mapValue.put("partyId", getPartyId());
        mapValue.put("issueDate", getIssueDate());
        mapValue.put("statusId", getStatusId());
        mapValue.put("currencyUomId", getCurrencyUomId());
        mapValue.put("productStoreId", getProductStoreId());
        mapValue.put("salesChannelEnumId", getSalesChannelEnumId());
        mapValue.put("validFromDate", getValidFromDate());
        mapValue.put("validThruDate", getValidThruDate());
        mapValue.put("quoteName", getQuoteName());
        mapValue.put("description", getDescription());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}