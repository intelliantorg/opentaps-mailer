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
 * Auto generated base entity WorkflowActivityLoop.
 */
@javax.persistence.Entity
@Table(name="WORKFLOW_ACTIVITY_LOOP")
@IdClass(WorkflowActivityLoopPk.class)
public class WorkflowActivityLoop extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("packageId", "PACKAGE_ID");
        fields.put("packageVersion", "PACKAGE_VERSION");
        fields.put("processId", "PROCESS_ID");
        fields.put("processVersion", "PROCESS_VERSION");
        fields.put("activityId", "ACTIVITY_ID");
        fields.put("conditionExpr", "CONDITION_EXPR");
        fields.put("loopKindEnumId", "LOOP_KIND_ENUM_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("WorkflowActivityLoop", fields);
}
  public static enum Fields implements EntityFieldInterface<WorkflowActivityLoop> {
    packageId("packageId"),
    packageVersion("packageVersion"),
    processId("processId"),
    processVersion("processVersion"),
    activityId("activityId"),
    conditionExpr("conditionExpr"),
    loopKindEnumId("loopKindEnumId"),
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
    
    @Column(name="PACKAGE_ID")
    private String packageId;
    @Id
    
    @Column(name="PACKAGE_VERSION")
    private String packageVersion;
    @Id
    
    @Column(name="PROCESS_ID")
    private String processId;
    @Id
    
    @Column(name="PROCESS_VERSION")
    private String processVersion;
    @Id
    
    @Column(name="ACTIVITY_ID")
    private String activityId;
    
    @Column(name="CONDITION_EXPR")
    private String conditionExpr;
    
    @Column(name="LOOP_KIND_ENUM_ID")
    private String loopKindEnumId;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    private transient WorkflowActivity workflowActivity = null;

  /**
   * Default constructor.
   */
  public WorkflowActivityLoop() {
      super();
      this.baseEntityName = "WorkflowActivityLoop";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("packageId");this.primaryKeyNames.add("packageVersion");this.primaryKeyNames.add("processId");this.primaryKeyNames.add("processVersion");this.primaryKeyNames.add("activityId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public WorkflowActivityLoop(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

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
     * @param conditionExpr the conditionExpr to set
     */
    public void setConditionExpr(String conditionExpr) {
        this.conditionExpr = conditionExpr;
    }
    /**
     * Auto generated value setter.
     * @param loopKindEnumId the loopKindEnumId to set
     */
    public void setLoopKindEnumId(String loopKindEnumId) {
        this.loopKindEnumId = loopKindEnumId;
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
    public String getConditionExpr() {
        return this.conditionExpr;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getLoopKindEnumId() {
        return this.loopKindEnumId;
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
     * Auto generated method that gets the related <code>WorkflowActivity</code> by the relation named <code>WorkflowActivity</code>.
     * @return the <code>WorkflowActivity</code>
     * @throws RepositoryException if an error occurs
     */
    public WorkflowActivity getWorkflowActivity() throws RepositoryException {
        if (this.workflowActivity == null) {
            this.workflowActivity = getRelatedOne(WorkflowActivity.class, "WorkflowActivity");
        }
        return this.workflowActivity;
    }

    /**
     * Auto generated value setter.
     * @param workflowActivity the workflowActivity to set
    */
    public void setWorkflowActivity(WorkflowActivity workflowActivity) {
        this.workflowActivity = workflowActivity;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setPackageId((String) mapValue.get("packageId"));
        setPackageVersion((String) mapValue.get("packageVersion"));
        setProcessId((String) mapValue.get("processId"));
        setProcessVersion((String) mapValue.get("processVersion"));
        setActivityId((String) mapValue.get("activityId"));
        setConditionExpr((String) mapValue.get("conditionExpr"));
        setLoopKindEnumId((String) mapValue.get("loopKindEnumId"));
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
        mapValue.put("packageId", getPackageId());
        mapValue.put("packageVersion", getPackageVersion());
        mapValue.put("processId", getProcessId());
        mapValue.put("processVersion", getProcessVersion());
        mapValue.put("activityId", getActivityId());
        mapValue.put("conditionExpr", getConditionExpr());
        mapValue.put("loopKindEnumId", getLoopKindEnumId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
