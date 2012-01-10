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

@Embeddable
public class WorkflowActivityToolPk implements Serializable {
    @Column(name="PACKAGE_ID")
    private String packageId;
    @Column(name="PACKAGE_VERSION")
    private String packageVersion;
    @Column(name="PROCESS_ID")
    private String processId;
    @Column(name="PROCESS_VERSION")
    private String processVersion;
    @Column(name="ACTIVITY_ID")
    private String activityId;
    @Column(name="TOOL_ID")
    private String toolId;
    
    /**
     * Auto generated value setter.
     * @param packageId the packageId to set
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    /**
     * Auto generated value setter.
     * @param packageVersion the packageVersion to set
     */
    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }
    /**
     * Auto generated value setter.
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    /**
     * Auto generated value setter.
     * @param processVersion the processVersion to set
     */
    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }
    /**
     * Auto generated value setter.
     * @param activityId the activityId to set
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    /**
     * Auto generated value setter.
     * @param toolId the toolId to set
     */
    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getPackageId() {
        return this.packageId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getPackageVersion() {
        return this.packageVersion;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProcessId() {
        return this.processId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getProcessVersion() {
        return this.processVersion;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getActivityId() {
        return this.activityId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getToolId() {
        return this.toolId;
    }
}
