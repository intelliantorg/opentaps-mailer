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
 * Auto generated base entity CustRequestStatus.
 */
@javax.persistence.Entity
@Table(name="CUST_REQUEST_STATUS")
public class CustRequestStatus extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("custRequestStatusId", "CUST_REQUEST_STATUS_ID");
        fields.put("statusId", "STATUS_ID");
        fields.put("custRequestId", "CUST_REQUEST_ID");
        fields.put("custRequestItemSeqId", "CUST_REQUEST_ITEM_SEQ_ID");
        fields.put("statusDatetime", "STATUS_DATETIME");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("CustRequestStatus", fields);
}
  public static enum Fields implements EntityFieldInterface<CustRequestStatus> {
    custRequestStatusId("custRequestStatusId"),
    statusId("statusId"),
    custRequestId("custRequestId"),
    custRequestItemSeqId("custRequestItemSeqId"),
    statusDatetime("statusDatetime"),
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

    @org.hibernate.annotations.GenericGenerator(name="CustRequestStatus_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="CustRequestStatus_GEN")   
    @Id
    
    @Column(name="CUST_REQUEST_STATUS_ID")
    private String custRequestStatusId;
    
    @Column(name="STATUS_ID")
    private String statusId;
    
    @Column(name="CUST_REQUEST_ID")
    private String custRequestId;
    
    @Column(name="CUST_REQUEST_ITEM_SEQ_ID")
    private String custRequestItemSeqId;
    
    @Column(name="STATUS_DATETIME")
    private Timestamp statusDatetime;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private StatusItem statusItem = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CUST_REQUEST_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private CustRequest custRequest = null;
    private transient CustRequestItem custRequestItem = null;

  /**
   * Default constructor.
   */
  public CustRequestStatus() {
      super();
      this.baseEntityName = "CustRequestStatus";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("custRequestStatusId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public CustRequestStatus(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param custRequestStatusId the custRequestStatusId to set
     */
    public void setCustRequestStatusId(String custRequestStatusId) {
        this.custRequestStatusId = custRequestStatusId;
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
     * @param custRequestId the custRequestId to set
     */
    public void setCustRequestId(String custRequestId) {
        this.custRequestId = custRequestId;
    }
    /**
     * Auto generated value setter.
     * @param custRequestItemSeqId the custRequestItemSeqId to set
     */
    public void setCustRequestItemSeqId(String custRequestItemSeqId) {
        this.custRequestItemSeqId = custRequestItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param statusDatetime the statusDatetime to set
     */
    public void setStatusDatetime(Timestamp statusDatetime) {
        this.statusDatetime = statusDatetime;
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
    public String getCustRequestStatusId() {
        return this.custRequestStatusId;
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
    public String getCustRequestId() {
        return this.custRequestId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustRequestItemSeqId() {
        return this.custRequestItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getStatusDatetime() {
        return this.statusDatetime;
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
     * Auto generated method that gets the related <code>CustRequest</code> by the relation named <code>CustRequest</code>.
     * @return the <code>CustRequest</code>
     * @throws RepositoryException if an error occurs
     */
    public CustRequest getCustRequest() throws RepositoryException {
        if (this.custRequest == null) {
            this.custRequest = getRelatedOne(CustRequest.class, "CustRequest");
        }
        return this.custRequest;
    }
    /**
     * Auto generated method that gets the related <code>CustRequestItem</code> by the relation named <code>CustRequestItem</code>.
     * @return the <code>CustRequestItem</code>
     * @throws RepositoryException if an error occurs
     */
    public CustRequestItem getCustRequestItem() throws RepositoryException {
        if (this.custRequestItem == null) {
            this.custRequestItem = getRelatedOne(CustRequestItem.class, "CustRequestItem");
        }
        return this.custRequestItem;
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
     * @param custRequest the custRequest to set
    */
    public void setCustRequest(CustRequest custRequest) {
        this.custRequest = custRequest;
    }
    /**
     * Auto generated value setter.
     * @param custRequestItem the custRequestItem to set
    */
    public void setCustRequestItem(CustRequestItem custRequestItem) {
        this.custRequestItem = custRequestItem;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setCustRequestStatusId((String) mapValue.get("custRequestStatusId"));
        setStatusId((String) mapValue.get("statusId"));
        setCustRequestId((String) mapValue.get("custRequestId"));
        setCustRequestItemSeqId((String) mapValue.get("custRequestItemSeqId"));
        setStatusDatetime((Timestamp) mapValue.get("statusDatetime"));
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
        mapValue.put("custRequestStatusId", getCustRequestStatusId());
        mapValue.put("statusId", getStatusId());
        mapValue.put("custRequestId", getCustRequestId());
        mapValue.put("custRequestItemSeqId", getCustRequestItemSeqId());
        mapValue.put("statusDatetime", getStatusDatetime());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}