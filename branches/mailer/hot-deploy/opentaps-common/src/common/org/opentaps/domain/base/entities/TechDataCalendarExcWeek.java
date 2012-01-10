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
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Auto generated base entity TechDataCalendarExcWeek.
 */
@javax.persistence.Entity
@Table(name="TECH_DATA_CALENDAR_EXC_WEEK")
@IdClass(TechDataCalendarExcWeekPk.class)
public class TechDataCalendarExcWeek extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("calendarId", "CALENDAR_ID");
        fields.put("exceptionDateStart", "EXCEPTION_DATE_START");
        fields.put("calendarWeekId", "CALENDAR_WEEK_ID");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("TechDataCalendarExcWeek", fields);
}
  public static enum Fields implements EntityFieldInterface<TechDataCalendarExcWeek> {
    calendarId("calendarId"),
    exceptionDateStart("exceptionDateStart"),
    calendarWeekId("calendarWeekId"),
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

    @Id
    
    @Column(name="CALENDAR_ID")
    private String calendarId;
    @Id
    
    @Column(name="EXCEPTION_DATE_START")
    private Date exceptionDateStart;
    
    @Column(name="CALENDAR_WEEK_ID")
    private String calendarWeekId;
    
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
    @JoinColumn(name="CALENDAR_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private TechDataCalendar techDataCalendar = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CALENDAR_WEEK_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private TechDataCalendarWeek techDataCalendarWeek = null;

  /**
   * Default constructor.
   */
  public TechDataCalendarExcWeek() {
      super();
      this.baseEntityName = "TechDataCalendarExcWeek";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("calendarId");this.primaryKeyNames.add("exceptionDateStart");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public TechDataCalendarExcWeek(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param calendarId the calendarId to set
     */
    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }
    /**
     * Auto generated value setter.
     * @param exceptionDateStart the exceptionDateStart to set
     */
    public void setExceptionDateStart(Date exceptionDateStart) {
        this.exceptionDateStart = exceptionDateStart;
    }
    /**
     * Auto generated value setter.
     * @param calendarWeekId the calendarWeekId to set
     */
    public void setCalendarWeekId(String calendarWeekId) {
        this.calendarWeekId = calendarWeekId;
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
    public String getCalendarId() {
        return this.calendarId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Date</code>
     */
    public Date getExceptionDateStart() {
        return this.exceptionDateStart;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCalendarWeekId() {
        return this.calendarWeekId;
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
     * Auto generated method that gets the related <code>TechDataCalendar</code> by the relation named <code>TechDataCalendar</code>.
     * @return the <code>TechDataCalendar</code>
     * @throws RepositoryException if an error occurs
     */
    public TechDataCalendar getTechDataCalendar() throws RepositoryException {
        if (this.techDataCalendar == null) {
            this.techDataCalendar = getRelatedOne(TechDataCalendar.class, "TechDataCalendar");
        }
        return this.techDataCalendar;
    }
    /**
     * Auto generated method that gets the related <code>TechDataCalendarWeek</code> by the relation named <code>TechDataCalendarWeek</code>.
     * @return the <code>TechDataCalendarWeek</code>
     * @throws RepositoryException if an error occurs
     */
    public TechDataCalendarWeek getTechDataCalendarWeek() throws RepositoryException {
        if (this.techDataCalendarWeek == null) {
            this.techDataCalendarWeek = getRelatedOne(TechDataCalendarWeek.class, "TechDataCalendarWeek");
        }
        return this.techDataCalendarWeek;
    }

    /**
     * Auto generated value setter.
     * @param techDataCalendar the techDataCalendar to set
    */
    public void setTechDataCalendar(TechDataCalendar techDataCalendar) {
        this.techDataCalendar = techDataCalendar;
    }
    /**
     * Auto generated value setter.
     * @param techDataCalendarWeek the techDataCalendarWeek to set
    */
    public void setTechDataCalendarWeek(TechDataCalendarWeek techDataCalendarWeek) {
        this.techDataCalendarWeek = techDataCalendarWeek;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setCalendarId((String) mapValue.get("calendarId"));
        setExceptionDateStart((Date) mapValue.get("exceptionDateStart"));
        setCalendarWeekId((String) mapValue.get("calendarWeekId"));
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
        mapValue.put("calendarId", getCalendarId());
        mapValue.put("exceptionDateStart", getExceptionDateStart());
        mapValue.put("calendarWeekId", getCalendarWeekId());
        mapValue.put("description", getDescription());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
