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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity TaxInvoiceItemFact.
 */
@javax.persistence.Entity
@Table(name="TAX_INVOICE_ITEM_FACT")
@IdClass(TaxInvoiceItemFactPk.class)
public class TaxInvoiceItemFact extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("dateDimId", "DATE_DIM_ID");
        fields.put("storeDimId", "STORE_DIM_ID");
        fields.put("taxAuthorityDimId", "TAX_AUTHORITY_DIM_ID");
        fields.put("currencyDimId", "CURRENCY_DIM_ID");
        fields.put("invoiceId", "INVOICE_ID");
        fields.put("invoiceItemSeqId", "INVOICE_ITEM_SEQ_ID");
        fields.put("grossAmount", "GROSS_AMOUNT");
        fields.put("discounts", "DISCOUNTS");
        fields.put("refunds", "REFUNDS");
        fields.put("netAmount", "NET_AMOUNT");
        fields.put("taxDue", "TAX_DUE");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("TaxInvoiceItemFact", fields);
}
  public static enum Fields implements EntityFieldInterface<TaxInvoiceItemFact> {
    dateDimId("dateDimId"),
    storeDimId("storeDimId"),
    taxAuthorityDimId("taxAuthorityDimId"),
    currencyDimId("currencyDimId"),
    invoiceId("invoiceId"),
    invoiceItemSeqId("invoiceItemSeqId"),
    grossAmount("grossAmount"),
    discounts("discounts"),
    refunds("refunds"),
    netAmount("netAmount"),
    taxDue("taxDue"),
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
    
    @Column(name="DATE_DIM_ID")
    private Long dateDimId;
    @Id
    
    @Column(name="STORE_DIM_ID")
    private Long storeDimId;
    @Id
    
    @Column(name="TAX_AUTHORITY_DIM_ID")
    private Long taxAuthorityDimId;
    @Id
    
    @Column(name="CURRENCY_DIM_ID")
    private Long currencyDimId;
    @Id
    
    @Column(name="INVOICE_ID")
    private String invoiceId;
    @Id
    
    @Column(name="INVOICE_ITEM_SEQ_ID")
    private String invoiceItemSeqId;
    
    @Column(name="GROSS_AMOUNT")
    private BigDecimal grossAmount;
    
    @Column(name="DISCOUNTS")
    private BigDecimal discounts;
    
    @Column(name="REFUNDS")
    private BigDecimal refunds;
    
    @Column(name="NET_AMOUNT")
    private BigDecimal netAmount;
    
    @Column(name="TAX_DUE")
    private BigDecimal taxDue;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;

  /**
   * Default constructor.
   */
  public TaxInvoiceItemFact() {
      super();
      this.baseEntityName = "TaxInvoiceItemFact";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("dateDimId");this.primaryKeyNames.add("storeDimId");this.primaryKeyNames.add("taxAuthorityDimId");this.primaryKeyNames.add("currencyDimId");this.primaryKeyNames.add("invoiceId");this.primaryKeyNames.add("invoiceItemSeqId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public TaxInvoiceItemFact(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param dateDimId the dateDimId to set
     */
    public void setDateDimId(Long dateDimId) {
        this.dateDimId = dateDimId;
    }
    /**
     * Auto generated value setter.
     * @param storeDimId the storeDimId to set
     */
    public void setStoreDimId(Long storeDimId) {
        this.storeDimId = storeDimId;
    }
    /**
     * Auto generated value setter.
     * @param taxAuthorityDimId the taxAuthorityDimId to set
     */
    public void setTaxAuthorityDimId(Long taxAuthorityDimId) {
        this.taxAuthorityDimId = taxAuthorityDimId;
    }
    /**
     * Auto generated value setter.
     * @param currencyDimId the currencyDimId to set
     */
    public void setCurrencyDimId(Long currencyDimId) {
        this.currencyDimId = currencyDimId;
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
     * @param invoiceItemSeqId the invoiceItemSeqId to set
     */
    public void setInvoiceItemSeqId(String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param grossAmount the grossAmount to set
     */
    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }
    /**
     * Auto generated value setter.
     * @param discounts the discounts to set
     */
    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }
    /**
     * Auto generated value setter.
     * @param refunds the refunds to set
     */
    public void setRefunds(BigDecimal refunds) {
        this.refunds = refunds;
    }
    /**
     * Auto generated value setter.
     * @param netAmount the netAmount to set
     */
    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }
    /**
     * Auto generated value setter.
     * @param taxDue the taxDue to set
     */
    public void setTaxDue(BigDecimal taxDue) {
        this.taxDue = taxDue;
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
     * @return <code>Long</code>
     */
    public Long getDateDimId() {
        return this.dateDimId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getStoreDimId() {
        return this.storeDimId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getTaxAuthorityDimId() {
        return this.taxAuthorityDimId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getCurrencyDimId() {
        return this.currencyDimId;
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
    public String getInvoiceItemSeqId() {
        return this.invoiceItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getGrossAmount() {
        return this.grossAmount;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getDiscounts() {
        return this.discounts;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getRefunds() {
        return this.refunds;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getNetAmount() {
        return this.netAmount;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getTaxDue() {
        return this.taxDue;
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




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setDateDimId((Long) mapValue.get("dateDimId"));
        setStoreDimId((Long) mapValue.get("storeDimId"));
        setTaxAuthorityDimId((Long) mapValue.get("taxAuthorityDimId"));
        setCurrencyDimId((Long) mapValue.get("currencyDimId"));
        setInvoiceId((String) mapValue.get("invoiceId"));
        setInvoiceItemSeqId((String) mapValue.get("invoiceItemSeqId"));
        setGrossAmount(convertToBigDecimal(mapValue.get("grossAmount")));
        setDiscounts(convertToBigDecimal(mapValue.get("discounts")));
        setRefunds(convertToBigDecimal(mapValue.get("refunds")));
        setNetAmount(convertToBigDecimal(mapValue.get("netAmount")));
        setTaxDue(convertToBigDecimal(mapValue.get("taxDue")));
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
        mapValue.put("dateDimId", getDateDimId());
        mapValue.put("storeDimId", getStoreDimId());
        mapValue.put("taxAuthorityDimId", getTaxAuthorityDimId());
        mapValue.put("currencyDimId", getCurrencyDimId());
        mapValue.put("invoiceId", getInvoiceId());
        mapValue.put("invoiceItemSeqId", getInvoiceItemSeqId());
        mapValue.put("grossAmount", getGrossAmount());
        mapValue.put("discounts", getDiscounts());
        mapValue.put("refunds", getRefunds());
        mapValue.put("netAmount", getNetAmount());
        mapValue.put("taxDue", getTaxDue());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
