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
public class EmployeePaycheckTypePk implements Serializable {
    @Column(name="ORGANIZATION_PARTY_ID")
    private String organizationPartyId;
    @Column(name="EMPLOYEE_PARTY_ID")
    private String employeePartyId;
    @Column(name="PAYMENT_TYPE_ID")
    private String paymentTypeId;
    @Column(name="FROM_DATE")
    private Timestamp fromDate;
    
    /**
     * Auto generated value setter.
     * @param organizationPartyId the organizationPartyId to set
     */
    public void setOrganizationPartyId(String organizationPartyId) {
        this.organizationPartyId = organizationPartyId;
    }
    /**
     * Auto generated value setter.
     * @param employeePartyId the employeePartyId to set
     */
    public void setEmployeePartyId(String employeePartyId) {
        this.employeePartyId = employeePartyId;
    }
    /**
     * Auto generated value setter.
     * @param paymentTypeId the paymentTypeId to set
     */
    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
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
    public String getOrganizationPartyId() {
        return this.organizationPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getEmployeePartyId() {
        return this.employeePartyId;
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
     * @return <code>Timestamp</code>
     */  
    public Timestamp getFromDate() {
        return this.fromDate;
    }
}