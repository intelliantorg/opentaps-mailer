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
 * Auto generated base entity PosTerminalState.
 */
@javax.persistence.Entity
@Table(name="POS_TERMINAL_STATE")
@IdClass(PosTerminalStatePk.class)
public class PosTerminalState extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("posTerminalId", "POS_TERMINAL_ID");
        fields.put("openedDate", "OPENED_DATE");
        fields.put("closedDate", "CLOSED_DATE");
        fields.put("startingTxId", "STARTING_TX_ID");
        fields.put("endingTxId", "ENDING_TX_ID");
        fields.put("openedByUserLoginId", "OPENED_BY_USER_LOGIN_ID");
        fields.put("closedByUserLoginId", "CLOSED_BY_USER_LOGIN_ID");
        fields.put("startingDrawerAmount", "STARTING_DRAWER_AMOUNT");
        fields.put("actualEndingCash", "ACTUAL_ENDING_CASH");
        fields.put("actualEndingCheck", "ACTUAL_ENDING_CHECK");
        fields.put("actualEndingCc", "ACTUAL_ENDING_CC");
        fields.put("actualEndingGc", "ACTUAL_ENDING_GC");
        fields.put("actualEndingOther", "ACTUAL_ENDING_OTHER");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("PosTerminalState", fields);
}
  public static enum Fields implements EntityFieldInterface<PosTerminalState> {
    posTerminalId("posTerminalId"),
    openedDate("openedDate"),
    closedDate("closedDate"),
    startingTxId("startingTxId"),
    endingTxId("endingTxId"),
    openedByUserLoginId("openedByUserLoginId"),
    closedByUserLoginId("closedByUserLoginId"),
    startingDrawerAmount("startingDrawerAmount"),
    actualEndingCash("actualEndingCash"),
    actualEndingCheck("actualEndingCheck"),
    actualEndingCc("actualEndingCc"),
    actualEndingGc("actualEndingGc"),
    actualEndingOther("actualEndingOther"),
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
    
    @Column(name="POS_TERMINAL_ID")
    private String posTerminalId;
    @Id
    
    @Column(name="OPENED_DATE")
    private Timestamp openedDate;
    
    @Column(name="CLOSED_DATE")
    private Timestamp closedDate;
    
    @Column(name="STARTING_TX_ID")
    private String startingTxId;
    
    @Column(name="ENDING_TX_ID")
    private String endingTxId;
    
    @Column(name="OPENED_BY_USER_LOGIN_ID")
    private String openedByUserLoginId;
    
    @Column(name="CLOSED_BY_USER_LOGIN_ID")
    private String closedByUserLoginId;
    
    @Column(name="STARTING_DRAWER_AMOUNT")
    private BigDecimal startingDrawerAmount;
    
    @Column(name="ACTUAL_ENDING_CASH")
    private BigDecimal actualEndingCash;
    
    @Column(name="ACTUAL_ENDING_CHECK")
    private BigDecimal actualEndingCheck;
    
    @Column(name="ACTUAL_ENDING_CC")
    private BigDecimal actualEndingCc;
    
    @Column(name="ACTUAL_ENDING_GC")
    private BigDecimal actualEndingGc;
    
    @Column(name="ACTUAL_ENDING_OTHER")
    private BigDecimal actualEndingOther;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="POS_TERMINAL_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private PosTerminal posTerminal = null;

  /**
   * Default constructor.
   */
  public PosTerminalState() {
      super();
      this.baseEntityName = "PosTerminalState";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("posTerminalId");this.primaryKeyNames.add("openedDate");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public PosTerminalState(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param posTerminalId the posTerminalId to set
     */
    public void setPosTerminalId(String posTerminalId) {
        this.posTerminalId = posTerminalId;
    }
    /**
     * Auto generated value setter.
     * @param openedDate the openedDate to set
     */
    public void setOpenedDate(Timestamp openedDate) {
        this.openedDate = openedDate;
    }
    /**
     * Auto generated value setter.
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Timestamp closedDate) {
        this.closedDate = closedDate;
    }
    /**
     * Auto generated value setter.
     * @param startingTxId the startingTxId to set
     */
    public void setStartingTxId(String startingTxId) {
        this.startingTxId = startingTxId;
    }
    /**
     * Auto generated value setter.
     * @param endingTxId the endingTxId to set
     */
    public void setEndingTxId(String endingTxId) {
        this.endingTxId = endingTxId;
    }
    /**
     * Auto generated value setter.
     * @param openedByUserLoginId the openedByUserLoginId to set
     */
    public void setOpenedByUserLoginId(String openedByUserLoginId) {
        this.openedByUserLoginId = openedByUserLoginId;
    }
    /**
     * Auto generated value setter.
     * @param closedByUserLoginId the closedByUserLoginId to set
     */
    public void setClosedByUserLoginId(String closedByUserLoginId) {
        this.closedByUserLoginId = closedByUserLoginId;
    }
    /**
     * Auto generated value setter.
     * @param startingDrawerAmount the startingDrawerAmount to set
     */
    public void setStartingDrawerAmount(BigDecimal startingDrawerAmount) {
        this.startingDrawerAmount = startingDrawerAmount;
    }
    /**
     * Auto generated value setter.
     * @param actualEndingCash the actualEndingCash to set
     */
    public void setActualEndingCash(BigDecimal actualEndingCash) {
        this.actualEndingCash = actualEndingCash;
    }
    /**
     * Auto generated value setter.
     * @param actualEndingCheck the actualEndingCheck to set
     */
    public void setActualEndingCheck(BigDecimal actualEndingCheck) {
        this.actualEndingCheck = actualEndingCheck;
    }
    /**
     * Auto generated value setter.
     * @param actualEndingCc the actualEndingCc to set
     */
    public void setActualEndingCc(BigDecimal actualEndingCc) {
        this.actualEndingCc = actualEndingCc;
    }
    /**
     * Auto generated value setter.
     * @param actualEndingGc the actualEndingGc to set
     */
    public void setActualEndingGc(BigDecimal actualEndingGc) {
        this.actualEndingGc = actualEndingGc;
    }
    /**
     * Auto generated value setter.
     * @param actualEndingOther the actualEndingOther to set
     */
    public void setActualEndingOther(BigDecimal actualEndingOther) {
        this.actualEndingOther = actualEndingOther;
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
    public String getPosTerminalId() {
        return this.posTerminalId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getOpenedDate() {
        return this.openedDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getClosedDate() {
        return this.closedDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStartingTxId() {
        return this.startingTxId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getEndingTxId() {
        return this.endingTxId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOpenedByUserLoginId() {
        return this.openedByUserLoginId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getClosedByUserLoginId() {
        return this.closedByUserLoginId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getStartingDrawerAmount() {
        return this.startingDrawerAmount;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualEndingCash() {
        return this.actualEndingCash;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualEndingCheck() {
        return this.actualEndingCheck;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualEndingCc() {
        return this.actualEndingCc;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualEndingGc() {
        return this.actualEndingGc;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getActualEndingOther() {
        return this.actualEndingOther;
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
     * Auto generated method that gets the related <code>PosTerminal</code> by the relation named <code>PosTerminal</code>.
     * @return the <code>PosTerminal</code>
     * @throws RepositoryException if an error occurs
     */
    public PosTerminal getPosTerminal() throws RepositoryException {
        if (this.posTerminal == null) {
            this.posTerminal = getRelatedOne(PosTerminal.class, "PosTerminal");
        }
        return this.posTerminal;
    }

    /**
     * Auto generated value setter.
     * @param posTerminal the posTerminal to set
    */
    public void setPosTerminal(PosTerminal posTerminal) {
        this.posTerminal = posTerminal;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setPosTerminalId((String) mapValue.get("posTerminalId"));
        setOpenedDate((Timestamp) mapValue.get("openedDate"));
        setClosedDate((Timestamp) mapValue.get("closedDate"));
        setStartingTxId((String) mapValue.get("startingTxId"));
        setEndingTxId((String) mapValue.get("endingTxId"));
        setOpenedByUserLoginId((String) mapValue.get("openedByUserLoginId"));
        setClosedByUserLoginId((String) mapValue.get("closedByUserLoginId"));
        setStartingDrawerAmount(convertToBigDecimal(mapValue.get("startingDrawerAmount")));
        setActualEndingCash(convertToBigDecimal(mapValue.get("actualEndingCash")));
        setActualEndingCheck(convertToBigDecimal(mapValue.get("actualEndingCheck")));
        setActualEndingCc(convertToBigDecimal(mapValue.get("actualEndingCc")));
        setActualEndingGc(convertToBigDecimal(mapValue.get("actualEndingGc")));
        setActualEndingOther(convertToBigDecimal(mapValue.get("actualEndingOther")));
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
        mapValue.put("posTerminalId", getPosTerminalId());
        mapValue.put("openedDate", getOpenedDate());
        mapValue.put("closedDate", getClosedDate());
        mapValue.put("startingTxId", getStartingTxId());
        mapValue.put("endingTxId", getEndingTxId());
        mapValue.put("openedByUserLoginId", getOpenedByUserLoginId());
        mapValue.put("closedByUserLoginId", getClosedByUserLoginId());
        mapValue.put("startingDrawerAmount", getStartingDrawerAmount());
        mapValue.put("actualEndingCash", getActualEndingCash());
        mapValue.put("actualEndingCheck", getActualEndingCheck());
        mapValue.put("actualEndingCc", getActualEndingCc());
        mapValue.put("actualEndingGc", getActualEndingGc());
        mapValue.put("actualEndingOther", getActualEndingOther());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
