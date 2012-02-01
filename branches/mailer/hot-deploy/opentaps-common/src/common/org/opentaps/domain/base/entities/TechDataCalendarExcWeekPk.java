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

import java.io.Serializable;
import javax.persistence.*;

import java.lang.String;
import java.sql.Date;

@Embeddable
public class TechDataCalendarExcWeekPk implements Serializable {
    @Column(name="CALENDAR_ID")
    private String calendarId;
    @Column(name="EXCEPTION_DATE_START")
    private Date exceptionDateStart;
    
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
}