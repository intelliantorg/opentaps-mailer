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
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Auto generated base entity CustomTimePeriod.
 */
@javax.persistence.Entity
@Table(name="CUSTOM_TIME_PERIOD")
public class CustomTimePeriod extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("customTimePeriodId", "CUSTOM_TIME_PERIOD_ID");
        fields.put("parentPeriodId", "PARENT_PERIOD_ID");
        fields.put("periodTypeId", "PERIOD_TYPE_ID");
        fields.put("periodNum", "PERIOD_NUM");
        fields.put("periodName", "PERIOD_NAME");
        fields.put("fromDate", "FROM_DATE");
        fields.put("thruDate", "THRU_DATE");
        fields.put("isClosed", "IS_CLOSED");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
        fields.put("organizationPartyId", "ORGANIZATION_PARTY_ID");
fieldMapColumns.put("CustomTimePeriod", fields);
}
  public static enum Fields implements EntityFieldInterface<CustomTimePeriod> {
    customTimePeriodId("customTimePeriodId"),
    parentPeriodId("parentPeriodId"),
    periodTypeId("periodTypeId"),
    periodNum("periodNum"),
    periodName("periodName"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    isClosed("isClosed"),
    lastUpdatedStamp("lastUpdatedStamp"),
    lastUpdatedTxStamp("lastUpdatedTxStamp"),
    createdStamp("createdStamp"),
    createdTxStamp("createdTxStamp"),
    organizationPartyId("organizationPartyId");
    private final String fieldName;
    private Fields(String name) { fieldName = name; }
    /** {@inheritDoc} */
    public String getName() { return fieldName; }
    /** {@inheritDoc} */
    public String asc() { return fieldName + " ASC"; }
    /** {@inheritDoc} */
    public String desc() { return fieldName + " DESC"; }
  }

    @org.hibernate.annotations.GenericGenerator(name="CustomTimePeriod_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="CustomTimePeriod_GEN")   
    @Id
    
    @Column(name="CUSTOM_TIME_PERIOD_ID")
    private String customTimePeriodId;
    
    @Column(name="PARENT_PERIOD_ID")
    private String parentPeriodId;
    
    @Column(name="PERIOD_TYPE_ID")
    private String periodTypeId;
    
    @Column(name="PERIOD_NUM")
    private Long periodNum;
    
    @Column(name="PERIOD_NAME")
    private String periodName;
    
    @Column(name="FROM_DATE")
    private Date fromDate;
    
    @Column(name="THRU_DATE")
    private Date thruDate;
    
    @Column(name="IS_CLOSED")
    private String isClosed;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    
    @Column(name="ORGANIZATION_PARTY_ID")
    private String organizationPartyId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_PERIOD_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private CustomTimePeriod parentCustomTimePeriod = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PERIOD_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private PeriodType periodType = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ORGANIZATION_PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party organizationParty = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOM_TIME_PERIOD_ID")
    private List<Budget> budgets = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_PERIOD_ID")
    private List<CustomTimePeriod> childCustomTimePeriods = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="customTimePeriod", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="CUSTOM_TIME_PERIOD_ID")
    private List<GlAccountHistory> glAccountHistorys = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOM_TIME_PERIOD_ID")
    private List<SalesForecast> salesForecasts = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOM_TIME_PERIOD_ID")
    private List<SalesForecastHistory> salesForecastHistorys = null;

  /**
   * Default constructor.
   */
  public CustomTimePeriod() {
      super();
      this.baseEntityName = "CustomTimePeriod";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("customTimePeriodId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public CustomTimePeriod(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param customTimePeriodId the customTimePeriodId to set
     */
    public void setCustomTimePeriodId(String customTimePeriodId) {
        this.customTimePeriodId = customTimePeriodId;
    }
    /**
     * Auto generated value setter.
     * @param parentPeriodId the parentPeriodId to set
     */
    public void setParentPeriodId(String parentPeriodId) {
        this.parentPeriodId = parentPeriodId;
    }
    /**
     * Auto generated value setter.
     * @param periodTypeId the periodTypeId to set
     */
    public void setPeriodTypeId(String periodTypeId) {
        this.periodTypeId = periodTypeId;
    }
    /**
     * Auto generated value setter.
     * @param periodNum the periodNum to set
     */
    public void setPeriodNum(Long periodNum) {
        this.periodNum = periodNum;
    }
    /**
     * Auto generated value setter.
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * Auto generated value setter.
     * @param thruDate the thruDate to set
     */
    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }
    /**
     * Auto generated value setter.
     * @param isClosed the isClosed to set
     */
    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
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
     * Auto generated value setter.
     * @param organizationPartyId the organizationPartyId to set
     */
    public void setOrganizationPartyId(String organizationPartyId) {
        this.organizationPartyId = organizationPartyId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomTimePeriodId() {
        return this.customTimePeriodId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getParentPeriodId() {
        return this.parentPeriodId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPeriodTypeId() {
        return this.periodTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getPeriodNum() {
        return this.periodNum;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPeriodName() {
        return this.periodName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Date</code>
     */
    public Date getFromDate() {
        return this.fromDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Date</code>
     */
    public Date getThruDate() {
        return this.thruDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getIsClosed() {
        return this.isClosed;
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
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrganizationPartyId() {
        return this.organizationPartyId;
    }

    /**
     * Auto generated method that gets the related <code>CustomTimePeriod</code> by the relation named <code>ParentCustomTimePeriod</code>.
     * @return the <code>CustomTimePeriod</code>
     * @throws RepositoryException if an error occurs
     */
    public CustomTimePeriod getParentCustomTimePeriod() throws RepositoryException {
        if (this.parentCustomTimePeriod == null) {
            this.parentCustomTimePeriod = getRelatedOne(CustomTimePeriod.class, "ParentCustomTimePeriod");
        }
        return this.parentCustomTimePeriod;
    }
    /**
     * Auto generated method that gets the related <code>PeriodType</code> by the relation named <code>PeriodType</code>.
     * @return the <code>PeriodType</code>
     * @throws RepositoryException if an error occurs
     */
    public PeriodType getPeriodType() throws RepositoryException {
        if (this.periodType == null) {
            this.periodType = getRelatedOne(PeriodType.class, "PeriodType");
        }
        return this.periodType;
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
     * Auto generated method that gets the related <code>Budget</code> by the relation named <code>Budget</code>.
     * @return the list of <code>Budget</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends Budget> getBudgets() throws RepositoryException {
        if (this.budgets == null) {
            this.budgets = getRelated(Budget.class, "Budget");
        }
        return this.budgets;
    }
    /**
     * Auto generated method that gets the related <code>CustomTimePeriod</code> by the relation named <code>ChildCustomTimePeriod</code>.
     * @return the list of <code>CustomTimePeriod</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends CustomTimePeriod> getChildCustomTimePeriods() throws RepositoryException {
        if (this.childCustomTimePeriods == null) {
            this.childCustomTimePeriods = getRelated(CustomTimePeriod.class, "ChildCustomTimePeriod");
        }
        return this.childCustomTimePeriods;
    }
    /**
     * Auto generated method that gets the related <code>GlAccountHistory</code> by the relation named <code>GlAccountHistory</code>.
     * @return the list of <code>GlAccountHistory</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends GlAccountHistory> getGlAccountHistorys() throws RepositoryException {
        if (this.glAccountHistorys == null) {
            this.glAccountHistorys = getRelated(GlAccountHistory.class, "GlAccountHistory");
        }
        return this.glAccountHistorys;
    }
    /**
     * Auto generated method that gets the related <code>SalesForecast</code> by the relation named <code>SalesForecast</code>.
     * @return the list of <code>SalesForecast</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends SalesForecast> getSalesForecasts() throws RepositoryException {
        if (this.salesForecasts == null) {
            this.salesForecasts = getRelated(SalesForecast.class, "SalesForecast");
        }
        return this.salesForecasts;
    }
    /**
     * Auto generated method that gets the related <code>SalesForecastHistory</code> by the relation named <code>SalesForecastHistory</code>.
     * @return the list of <code>SalesForecastHistory</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends SalesForecastHistory> getSalesForecastHistorys() throws RepositoryException {
        if (this.salesForecastHistorys == null) {
            this.salesForecastHistorys = getRelated(SalesForecastHistory.class, "SalesForecastHistory");
        }
        return this.salesForecastHistorys;
    }

    /**
     * Auto generated value setter.
     * @param parentCustomTimePeriod the parentCustomTimePeriod to set
    */
    public void setParentCustomTimePeriod(CustomTimePeriod parentCustomTimePeriod) {
        this.parentCustomTimePeriod = parentCustomTimePeriod;
    }
    /**
     * Auto generated value setter.
     * @param periodType the periodType to set
    */
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
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
     * @param budgets the budgets to set
    */
    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }
    /**
     * Auto generated value setter.
     * @param childCustomTimePeriods the childCustomTimePeriods to set
    */
    public void setChildCustomTimePeriods(List<CustomTimePeriod> childCustomTimePeriods) {
        this.childCustomTimePeriods = childCustomTimePeriods;
    }
    /**
     * Auto generated value setter.
     * @param glAccountHistorys the glAccountHistorys to set
    */
    public void setGlAccountHistorys(List<GlAccountHistory> glAccountHistorys) {
        this.glAccountHistorys = glAccountHistorys;
    }
    /**
     * Auto generated value setter.
     * @param salesForecasts the salesForecasts to set
    */
    public void setSalesForecasts(List<SalesForecast> salesForecasts) {
        this.salesForecasts = salesForecasts;
    }
    /**
     * Auto generated value setter.
     * @param salesForecastHistorys the salesForecastHistorys to set
    */
    public void setSalesForecastHistorys(List<SalesForecastHistory> salesForecastHistorys) {
        this.salesForecastHistorys = salesForecastHistorys;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addGlAccountHistory(GlAccountHistory glAccountHistory) {
        if (this.glAccountHistorys == null) {
            this.glAccountHistorys = new ArrayList<GlAccountHistory>();
        }
        this.glAccountHistorys.add(glAccountHistory);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeGlAccountHistory(GlAccountHistory glAccountHistory) {
        if (this.glAccountHistorys == null) {
            return;
        }
        this.glAccountHistorys.remove(glAccountHistory);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearGlAccountHistory() {
        if (this.glAccountHistorys == null) {
            return;
        }
        this.glAccountHistorys.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setCustomTimePeriodId((String) mapValue.get("customTimePeriodId"));
        setParentPeriodId((String) mapValue.get("parentPeriodId"));
        setPeriodTypeId((String) mapValue.get("periodTypeId"));
        setPeriodNum((Long) mapValue.get("periodNum"));
        setPeriodName((String) mapValue.get("periodName"));
        setFromDate((Date) mapValue.get("fromDate"));
        setThruDate((Date) mapValue.get("thruDate"));
        setIsClosed((String) mapValue.get("isClosed"));
        setLastUpdatedStamp((Timestamp) mapValue.get("lastUpdatedStamp"));
        setLastUpdatedTxStamp((Timestamp) mapValue.get("lastUpdatedTxStamp"));
        setCreatedStamp((Timestamp) mapValue.get("createdStamp"));
        setCreatedTxStamp((Timestamp) mapValue.get("createdTxStamp"));
        setOrganizationPartyId((String) mapValue.get("organizationPartyId"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("customTimePeriodId", getCustomTimePeriodId());
        mapValue.put("parentPeriodId", getParentPeriodId());
        mapValue.put("periodTypeId", getPeriodTypeId());
        mapValue.put("periodNum", getPeriodNum());
        mapValue.put("periodName", getPeriodName());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("isClosed", getIsClosed());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        mapValue.put("organizationPartyId", getOrganizationPartyId());
        return mapValue;
    }


}
