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
 * Auto generated base entity PaymentApplicationSum.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectPaymentApplicationSums", query="SELECT P.PAYMENT_ID AS \"paymentId\",P.PAYMENT_TYPE_ID AS \"paymentTypeId\",P.PAYMENT_METHOD_TYPE_ID AS \"paymentMethodTypeId\",P.PAYMENT_METHOD_ID AS \"paymentMethodId\",P.PARTY_ID_FROM AS \"partyIdFrom\",P.PARTY_ID_TO AS \"partyIdTo\",P.STATUS_ID AS \"statusId\",P.EFFECTIVE_DATE AS \"effectiveDate\",P.PAYMENT_REF_NUM AS \"paymentRefNum\",P.AMOUNT AS \"amount\",P.CURRENCY_UOM_ID AS \"currencyUomId\",PA.AMOUNT_APPLIED AS \"amountApplied\" FROM PAYMENT P LEFT JOIN PAYMENT_APPLICATION PA ON P.PAYMENT_ID = PA.PAYMENT_ID", resultSetMapping="PaymentApplicationSumMapping")
@SqlResultSetMapping(name="PaymentApplicationSumMapping", entities={
@EntityResult(entityClass=PaymentApplicationSum.class, fields = {
@FieldResult(name="paymentId", column="paymentId")
,@FieldResult(name="paymentTypeId", column="paymentTypeId")
,@FieldResult(name="paymentMethodTypeId", column="paymentMethodTypeId")
,@FieldResult(name="paymentMethodId", column="paymentMethodId")
,@FieldResult(name="partyIdFrom", column="partyIdFrom")
,@FieldResult(name="partyIdTo", column="partyIdTo")
,@FieldResult(name="statusId", column="statusId")
,@FieldResult(name="effectiveDate", column="effectiveDate")
,@FieldResult(name="paymentRefNum", column="paymentRefNum")
,@FieldResult(name="amount", column="amount")
,@FieldResult(name="currencyUomId", column="currencyUomId")
,@FieldResult(name="amountApplied", column="amountApplied")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class PaymentApplicationSum extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("paymentId", "P.PAYMENT_ID");
        fields.put("paymentTypeId", "P.PAYMENT_TYPE_ID");
        fields.put("paymentMethodTypeId", "P.PAYMENT_METHOD_TYPE_ID");
        fields.put("paymentMethodId", "P.PAYMENT_METHOD_ID");
        fields.put("partyIdFrom", "P.PARTY_ID_FROM");
        fields.put("partyIdTo", "P.PARTY_ID_TO");
        fields.put("statusId", "P.STATUS_ID");
        fields.put("effectiveDate", "P.EFFECTIVE_DATE");
        fields.put("paymentRefNum", "P.PAYMENT_REF_NUM");
        fields.put("amount", "P.AMOUNT");
        fields.put("currencyUomId", "P.CURRENCY_UOM_ID");
        fields.put("amountApplied", "PA.AMOUNT_APPLIED");
fieldMapColumns.put("PaymentApplicationSum", fields);
}
  public static enum Fields implements EntityFieldInterface<PaymentApplicationSum> {
    paymentId("paymentId"),
    paymentTypeId("paymentTypeId"),
    paymentMethodTypeId("paymentMethodTypeId"),
    paymentMethodId("paymentMethodId"),
    partyIdFrom("partyIdFrom"),
    partyIdTo("partyIdTo"),
    statusId("statusId"),
    effectiveDate("effectiveDate"),
    paymentRefNum("paymentRefNum"),
    amount("amount"),
    currencyUomId("currencyUomId"),
    amountApplied("amountApplied");
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
    
    private String paymentId;
    
    
    private String paymentTypeId;
    
    
    private String paymentMethodTypeId;
    
    
    private String paymentMethodId;
    
    
    private String partyIdFrom;
    
    
    private String partyIdTo;
    
    
    private String statusId;
    
    
    private Timestamp effectiveDate;
    
    
    private String paymentRefNum;
    
    
    private BigDecimal amount;
    
    
    private String currencyUomId;
    
    
    private BigDecimal amountApplied;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PAYMENT_METHOD_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private PaymentMethod paymentMethod = null;

  /**
   * Default constructor.
   */
  public PaymentApplicationSum() {
      super();
      this.baseEntityName = "PaymentApplicationSum";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("paymentId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public PaymentApplicationSum(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param paymentId the paymentId to set
     */
    private void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    /**
     * Auto generated value setter.
     * @param paymentTypeId the paymentTypeId to set
     */
    private void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    /**
     * Auto generated value setter.
     * @param paymentMethodTypeId the paymentMethodTypeId to set
     */
    private void setPaymentMethodTypeId(String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }
    /**
     * Auto generated value setter.
     * @param paymentMethodId the paymentMethodId to set
     */
    private void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    /**
     * Auto generated value setter.
     * @param partyIdFrom the partyIdFrom to set
     */
    private void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    /**
     * Auto generated value setter.
     * @param partyIdTo the partyIdTo to set
     */
    private void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    private void setStatusId(String statusId) {
        this.statusId = statusId;
    }
    /**
     * Auto generated value setter.
     * @param effectiveDate the effectiveDate to set
     */
    private void setEffectiveDate(Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    /**
     * Auto generated value setter.
     * @param paymentRefNum the paymentRefNum to set
     */
    private void setPaymentRefNum(String paymentRefNum) {
        this.paymentRefNum = paymentRefNum;
    }
    /**
     * Auto generated value setter.
     * @param amount the amount to set
     */
    private void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**
     * Auto generated value setter.
     * @param currencyUomId the currencyUomId to set
     */
    private void setCurrencyUomId(String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    /**
     * Auto generated value setter.
     * @param amountApplied the amountApplied to set
     */
    private void setAmountApplied(BigDecimal amountApplied) {
        this.amountApplied = amountApplied;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentId() {
        return this.paymentId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentTypeId() {
        return this.paymentTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentMethodTypeId() {
        return this.paymentMethodTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentMethodId() {
        return this.paymentMethodId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPartyIdFrom() {
        return this.partyIdFrom;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPartyIdTo() {
        return this.partyIdTo;
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
     * @return <code>Timestamp</code>
     */
    public Timestamp getEffectiveDate() {
        return this.effectiveDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPaymentRefNum() {
        return this.paymentRefNum;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getAmount() {
        return this.amount;
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
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getAmountApplied() {
        return this.amountApplied;
    }

    /**
     * Auto generated method that gets the related <code>PaymentMethod</code> by the relation named <code>PaymentMethod</code>.
     * @return the <code>PaymentMethod</code>
     * @throws RepositoryException if an error occurs
     */
    public PaymentMethod getPaymentMethod() throws RepositoryException {
        if (this.paymentMethod == null) {
            this.paymentMethod = getRelatedOne(PaymentMethod.class, "PaymentMethod");
        }
        return this.paymentMethod;
    }

    /**
     * Auto generated value setter.
     * @param paymentMethod the paymentMethod to set
    */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setPaymentId((String) mapValue.get("paymentId"));
        setPaymentTypeId((String) mapValue.get("paymentTypeId"));
        setPaymentMethodTypeId((String) mapValue.get("paymentMethodTypeId"));
        setPaymentMethodId((String) mapValue.get("paymentMethodId"));
        setPartyIdFrom((String) mapValue.get("partyIdFrom"));
        setPartyIdTo((String) mapValue.get("partyIdTo"));
        setStatusId((String) mapValue.get("statusId"));
        setEffectiveDate((Timestamp) mapValue.get("effectiveDate"));
        setPaymentRefNum((String) mapValue.get("paymentRefNum"));
        setAmount(convertToBigDecimal(mapValue.get("amount")));
        setCurrencyUomId((String) mapValue.get("currencyUomId"));
        setAmountApplied(convertToBigDecimal(mapValue.get("amountApplied")));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("paymentId", getPaymentId());
        mapValue.put("paymentTypeId", getPaymentTypeId());
        mapValue.put("paymentMethodTypeId", getPaymentMethodTypeId());
        mapValue.put("paymentMethodId", getPaymentMethodId());
        mapValue.put("partyIdFrom", getPartyIdFrom());
        mapValue.put("partyIdTo", getPartyIdTo());
        mapValue.put("statusId", getStatusId());
        mapValue.put("effectiveDate", getEffectiveDate());
        mapValue.put("paymentRefNum", getPaymentRefNum());
        mapValue.put("amount", getAmount());
        mapValue.put("currencyUomId", getCurrencyUomId());
        mapValue.put("amountApplied", getAmountApplied());
        return mapValue;
    }


}
