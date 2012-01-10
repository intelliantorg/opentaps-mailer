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
import java.sql.Timestamp;

@Embeddable
public class FacilityGroupRollupPk implements Serializable {
    @Column(name="FACILITY_GROUP_ID")
    private String facilityGroupId;
    @Column(name="PARENT_FACILITY_GROUP_ID")
    private String parentFacilityGroupId;
    @Column(name="FROM_DATE")
    private Timestamp fromDate;
    
    /**
     * Auto generated value setter.
     * @param facilityGroupId the facilityGroupId to set
     */
    public void setFacilityGroupId(String facilityGroupId) {
        this.facilityGroupId = facilityGroupId;
    }
    /**
     * Auto generated value setter.
     * @param parentFacilityGroupId the parentFacilityGroupId to set
     */
    public void setParentFacilityGroupId(String parentFacilityGroupId) {
        this.parentFacilityGroupId = parentFacilityGroupId;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getFacilityGroupId() {
        return this.facilityGroupId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getParentFacilityGroupId() {
        return this.parentFacilityGroupId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */  
    public Timestamp getFromDate() {
        return this.fromDate;
    }
}
