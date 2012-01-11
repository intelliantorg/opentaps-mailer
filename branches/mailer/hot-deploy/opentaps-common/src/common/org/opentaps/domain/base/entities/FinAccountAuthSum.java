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
 * Auto generated base entity FinAccountAuthSum.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectFinAccountAuthSums", query="SELECT FAA.FIN_ACCOUNT_ID AS \"finAccountId\",FAA.AUTHORIZATION_DATE AS \"authorizationDate\",FAA.FROM_DATE AS \"fromDate\",FAA.THRU_DATE AS \"thruDate\",FAA.AMOUNT AS \"amount\" FROM FIN_ACCOUNT_AUTH FAA", resultSetMapping="FinAccountAuthSumMapping")
@SqlResultSetMapping(name="FinAccountAuthSumMapping", entities={
@EntityResult(entityClass=FinAccountAuthSum.class, fields = {
@FieldResult(name="finAccountId", column="finAccountId")
,@FieldResult(name="authorizationDate", column="authorizationDate")
,@FieldResult(name="fromDate", column="fromDate")
,@FieldResult(name="thruDate", column="thruDate")
,@FieldResult(name="amount", column="amount")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class FinAccountAuthSum extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("finAccountId", "FAA.FIN_ACCOUNT_ID");
        fields.put("authorizationDate", "FAA.AUTHORIZATION_DATE");
        fields.put("fromDate", "FAA.FROM_DATE");
        fields.put("thruDate", "FAA.THRU_DATE");
        fields.put("amount", "FAA.AMOUNT");
fieldMapColumns.put("FinAccountAuthSum", fields);
}
  public static enum Fields implements EntityFieldInterface<FinAccountAuthSum> {
    finAccountId("finAccountId"),
    authorizationDate("authorizationDate"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    amount("amount");
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
    
    private String finAccountId;
    
    
    private Timestamp authorizationDate;
    
    
    private Timestamp fromDate;
    
    
    private Timestamp thruDate;
    
    
    private BigDecimal amount;

  /**
   * Default constructor.
   */
  public FinAccountAuthSum() {
      super();
      this.baseEntityName = "FinAccountAuthSum";
      this.isView = true;
      
      
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public FinAccountAuthSum(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param finAccountId the finAccountId to set
     */
    private void setFinAccountId(String finAccountId) {
        this.finAccountId = finAccountId;
    }
    /**
     * Auto generated value setter.
     * @param authorizationDate the authorizationDate to set
     */
    private void setAuthorizationDate(Timestamp authorizationDate) {
        this.authorizationDate = authorizationDate;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    private void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * Auto generated value setter.
     * @param thruDate the thruDate to set
     */
    private void setThruDate(Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    /**
     * Auto generated value setter.
     * @param amount the amount to set
     */
    private void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * @return <code>Timestamp</code>
     */
    public Timestamp getAuthorizationDate() {
        return this.authorizationDate;
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
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getAmount() {
        return this.amount;
    }




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setFinAccountId((String) mapValue.get("finAccountId"));
        setAuthorizationDate((Timestamp) mapValue.get("authorizationDate"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        setAmount(convertToBigDecimal(mapValue.get("amount")));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("finAccountId", getFinAccountId());
        mapValue.put("authorizationDate", getAuthorizationDate());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("amount", getAmount());
        return mapValue;
    }


}