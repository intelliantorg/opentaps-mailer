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

/**
 * Auto generated base entity StatusValidChangeToDetail.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectStatusValidChangeToDetails", query="SELECT SVC.STATUS_ID AS \"statusId\",SVC.STATUS_ID_TO AS \"statusIdTo\",SVC.CONDITION_EXPRESSION AS \"conditionExpression\",SVC.TRANSITION_NAME AS \"transitionName\",SI.STATUS_TYPE_ID AS \"statusTypeId\",SI.STATUS_CODE AS \"statusCode\",SI.SEQUENCE_ID AS \"sequenceId\",SI.DESCRIPTION AS \"description\" FROM STATUS_VALID_CHANGE SVC INNER JOIN STATUS_ITEM SI ON SVC.STATUS_ID_TO = SI.STATUS_ID", resultSetMapping="StatusValidChangeToDetailMapping")
@SqlResultSetMapping(name="StatusValidChangeToDetailMapping", entities={
@EntityResult(entityClass=StatusValidChangeToDetail.class, fields = {
@FieldResult(name="statusId", column="statusId")
,@FieldResult(name="statusIdTo", column="statusIdTo")
,@FieldResult(name="conditionExpression", column="conditionExpression")
,@FieldResult(name="transitionName", column="transitionName")
,@FieldResult(name="statusTypeId", column="statusTypeId")
,@FieldResult(name="statusCode", column="statusCode")
,@FieldResult(name="sequenceId", column="sequenceId")
,@FieldResult(name="description", column="description")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class StatusValidChangeToDetail extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("statusId", "SVC.STATUS_ID");
        fields.put("statusIdTo", "SVC.STATUS_ID_TO");
        fields.put("conditionExpression", "SVC.CONDITION_EXPRESSION");
        fields.put("transitionName", "SVC.TRANSITION_NAME");
        fields.put("statusTypeId", "SI.STATUS_TYPE_ID");
        fields.put("statusCode", "SI.STATUS_CODE");
        fields.put("sequenceId", "SI.SEQUENCE_ID");
        fields.put("description", "SI.DESCRIPTION");
fieldMapColumns.put("StatusValidChangeToDetail", fields);
}
  public static enum Fields implements EntityFieldInterface<StatusValidChangeToDetail> {
    statusId("statusId"),
    statusIdTo("statusIdTo"),
    conditionExpression("conditionExpression"),
    transitionName("transitionName"),
    statusTypeId("statusTypeId"),
    statusCode("statusCode"),
    sequenceId("sequenceId"),
    description("description");
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
    
    private String statusId;
    
    
    private String statusIdTo;
    
    
    private String conditionExpression;
    
    
    private String transitionName;
    
    
    private String statusTypeId;
    
    
    private String statusCode;
    
    
    private String sequenceId;
    
    
    private String description;
    private transient StatusValidChange statusValidChange = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private StatusItem statusItem = null;

  /**
   * Default constructor.
   */
  public StatusValidChangeToDetail() {
      super();
      this.baseEntityName = "StatusValidChangeToDetail";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("statusId");this.primaryKeyNames.add("statusIdTo");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public StatusValidChangeToDetail(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    private void setStatusId(String statusId) {
        this.statusId = statusId;
    }
    /**
     * Auto generated value setter.
     * @param statusIdTo the statusIdTo to set
     */
    private void setStatusIdTo(String statusIdTo) {
        this.statusIdTo = statusIdTo;
    }
    /**
     * Auto generated value setter.
     * @param conditionExpression the conditionExpression to set
     */
    private void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
    /**
     * Auto generated value setter.
     * @param transitionName the transitionName to set
     */
    private void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }
    /**
     * Auto generated value setter.
     * @param statusTypeId the statusTypeId to set
     */
    private void setStatusTypeId(String statusTypeId) {
        this.statusTypeId = statusTypeId;
    }
    /**
     * Auto generated value setter.
     * @param statusCode the statusCode to set
     */
    private void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * Auto generated value setter.
     * @param sequenceId the sequenceId to set
     */
    private void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }
    /**
     * Auto generated value setter.
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
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
    public String getStatusIdTo() {
        return this.statusIdTo;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getConditionExpression() {
        return this.conditionExpression;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTransitionName() {
        return this.transitionName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStatusTypeId() {
        return this.statusTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStatusCode() {
        return this.statusCode;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getSequenceId() {
        return this.sequenceId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Auto generated method that gets the related <code>StatusValidChange</code> by the relation named <code>StatusValidChange</code>.
     * @return the <code>StatusValidChange</code>
     * @throws RepositoryException if an error occurs
     */
    public StatusValidChange getStatusValidChange() throws RepositoryException {
        if (this.statusValidChange == null) {
            this.statusValidChange = getRelatedOne(StatusValidChange.class, "StatusValidChange");
        }
        return this.statusValidChange;
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
     * Auto generated value setter.
     * @param statusValidChange the statusValidChange to set
    */
    public void setStatusValidChange(StatusValidChange statusValidChange) {
        this.statusValidChange = statusValidChange;
    }
    /**
     * Auto generated value setter.
     * @param statusItem the statusItem to set
    */
    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setStatusId((String) mapValue.get("statusId"));
        setStatusIdTo((String) mapValue.get("statusIdTo"));
        setConditionExpression((String) mapValue.get("conditionExpression"));
        setTransitionName((String) mapValue.get("transitionName"));
        setStatusTypeId((String) mapValue.get("statusTypeId"));
        setStatusCode((String) mapValue.get("statusCode"));
        setSequenceId((String) mapValue.get("sequenceId"));
        setDescription((String) mapValue.get("description"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("statusId", getStatusId());
        mapValue.put("statusIdTo", getStatusIdTo());
        mapValue.put("conditionExpression", getConditionExpression());
        mapValue.put("transitionName", getTransitionName());
        mapValue.put("statusTypeId", getStatusTypeId());
        mapValue.put("statusCode", getStatusCode());
        mapValue.put("sequenceId", getSequenceId());
        mapValue.put("description", getDescription());
        return mapValue;
    }


}
