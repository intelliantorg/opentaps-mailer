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
 * Auto generated base entity RateType.
 */
@javax.persistence.Entity
@Table(name="RATE_TYPE")
public class RateType extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("rateTypeId", "RATE_TYPE_ID");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("RateType", fields);
}
  public static enum Fields implements EntityFieldInterface<RateType> {
    rateTypeId("rateTypeId"),
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

    @org.hibernate.annotations.GenericGenerator(name="RateType_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="RateType_GEN")   
    @Id
    
    @Column(name="RATE_TYPE_ID")
    private String rateTypeId;
    
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
    @OneToMany(fetch=FetchType.LAZY, mappedBy="rateType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="RATE_TYPE_ID")
    private List<PartyRate> partyRates = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="RATE_TYPE_ID")
    private List<TimeEntry> timeEntrys = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="rateType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="RATE_TYPE_ID")
    private List<WorkEffortAssignmentRate> workEffortAssignmentRates = null;

  /**
   * Default constructor.
   */
  public RateType() {
      super();
      this.baseEntityName = "RateType";
      this.isView = false;
      this.resourceName = "WorkEffortEntityLabels";
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("rateTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public RateType(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param rateTypeId the rateTypeId to set
     */
    public void setRateTypeId(String rateTypeId) {
        this.rateTypeId = rateTypeId;
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
    public String getRateTypeId() {
        return this.rateTypeId;
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
     * Auto generated method that gets the related <code>PartyRate</code> by the relation named <code>PartyRate</code>.
     * @return the list of <code>PartyRate</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends PartyRate> getPartyRates() throws RepositoryException {
        if (this.partyRates == null) {
            this.partyRates = getRelated(PartyRate.class, "PartyRate");
        }
        return this.partyRates;
    }
    /**
     * Auto generated method that gets the related <code>TimeEntry</code> by the relation named <code>TimeEntry</code>.
     * @return the list of <code>TimeEntry</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends TimeEntry> getTimeEntrys() throws RepositoryException {
        if (this.timeEntrys == null) {
            this.timeEntrys = getRelated(TimeEntry.class, "TimeEntry");
        }
        return this.timeEntrys;
    }
    /**
     * Auto generated method that gets the related <code>WorkEffortAssignmentRate</code> by the relation named <code>WorkEffortAssignmentRate</code>.
     * @return the list of <code>WorkEffortAssignmentRate</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends WorkEffortAssignmentRate> getWorkEffortAssignmentRates() throws RepositoryException {
        if (this.workEffortAssignmentRates == null) {
            this.workEffortAssignmentRates = getRelated(WorkEffortAssignmentRate.class, "WorkEffortAssignmentRate");
        }
        return this.workEffortAssignmentRates;
    }

    /**
     * Auto generated value setter.
     * @param partyRates the partyRates to set
    */
    public void setPartyRates(List<PartyRate> partyRates) {
        this.partyRates = partyRates;
    }
    /**
     * Auto generated value setter.
     * @param timeEntrys the timeEntrys to set
    */
    public void setTimeEntrys(List<TimeEntry> timeEntrys) {
        this.timeEntrys = timeEntrys;
    }
    /**
     * Auto generated value setter.
     * @param workEffortAssignmentRates the workEffortAssignmentRates to set
    */
    public void setWorkEffortAssignmentRates(List<WorkEffortAssignmentRate> workEffortAssignmentRates) {
        this.workEffortAssignmentRates = workEffortAssignmentRates;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addPartyRate(PartyRate partyRate) {
        if (this.partyRates == null) {
            this.partyRates = new ArrayList<PartyRate>();
        }
        this.partyRates.add(partyRate);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removePartyRate(PartyRate partyRate) {
        if (this.partyRates == null) {
            return;
        }
        this.partyRates.remove(partyRate);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearPartyRate() {
        if (this.partyRates == null) {
            return;
        }
        this.partyRates.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addWorkEffortAssignmentRate(WorkEffortAssignmentRate workEffortAssignmentRate) {
        if (this.workEffortAssignmentRates == null) {
            this.workEffortAssignmentRates = new ArrayList<WorkEffortAssignmentRate>();
        }
        this.workEffortAssignmentRates.add(workEffortAssignmentRate);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeWorkEffortAssignmentRate(WorkEffortAssignmentRate workEffortAssignmentRate) {
        if (this.workEffortAssignmentRates == null) {
            return;
        }
        this.workEffortAssignmentRates.remove(workEffortAssignmentRate);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearWorkEffortAssignmentRate() {
        if (this.workEffortAssignmentRates == null) {
            return;
        }
        this.workEffortAssignmentRates.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setRateTypeId((String) mapValue.get("rateTypeId"));
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
        mapValue.put("rateTypeId", getRateTypeId());
        mapValue.put("description", getDescription());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}