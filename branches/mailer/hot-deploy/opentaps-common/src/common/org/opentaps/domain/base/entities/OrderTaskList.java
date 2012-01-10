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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity OrderTaskList.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectOrderTaskLists", query="SELECT OH.ORDER_ID AS \"orderId\",OH.ORDER_TYPE_ID AS \"orderTypeId\",OH.ORDER_DATE AS \"orderDate\",OH.ENTRY_DATE AS \"entryDate\",OH.GRAND_TOTAL AS \"grandTotal\",OHR.ROLE_TYPE_ID AS \"roleTypeId\",OHR.PARTY_ID AS \"partyId\",PS.FIRST_NAME AS \"firstName\",PS.LAST_NAME AS \"lastName\",WE.WORK_EFFORT_ID AS \"workEffortId\",WE.WORK_EFFORT_TYPE_ID AS \"workEffortTypeId\",WE.CURRENT_STATUS_ID AS \"currentStatusId\",WE.LAST_STATUS_UPDATE AS \"lastStatusUpdate\",WE.PRIORITY AS \"priority\",WE.WORK_EFFORT_NAME AS \"workEffortName\",WE.DESCRIPTION AS \"description\",WE.CREATED_DATE AS \"createdDate\",WE.CREATED_BY_USER_LOGIN AS \"createdByUserLogin\",WE.LAST_MODIFIED_DATE AS \"lastModifiedDate\",WE.LAST_MODIFIED_BY_USER_LOGIN AS \"lastModifiedByUserLogin\",WE.ESTIMATED_START_DATE AS \"estimatedStartDate\",WE.ESTIMATED_COMPLETION_DATE AS \"estimatedCompletionDate\",WE.ACTUAL_START_DATE AS \"actualStartDate\",WE.ACTUAL_COMPLETION_DATE AS \"actualCompletionDate\",WE.INFO_URL AS \"infoUrl\",WEPA.PARTY_ID AS \"partyId\",WEPA.ROLE_TYPE_ID AS \"roleTypeId\",WEPA.FROM_DATE AS \"fromDate\",WEPA.THRU_DATE AS \"thruDate\",WEPA.STATUS_ID AS \"statusId\",WEPA.STATUS_DATE_TIME AS \"statusDateTime\" FROM ORDER_HEADER OH LEFT JOIN ORDER_ROLE OHR ON OH.ORDER_ID = OHR.ORDER_ID LEFT JOIN PERSON PS ON OHR.PARTY_ID = PS.PARTY_ID INNER JOIN WORK_EFFORT WE ON OH.ORDER_ID = WE.SOURCE_REFERENCE_ID INNER JOIN WORK_EFFORT_PARTY_ASSIGNMENT WEPA ON WE.WORK_EFFORT_ID = WEPA.WORK_EFFORT_ID", resultSetMapping="OrderTaskListMapping")
@SqlResultSetMapping(name="OrderTaskListMapping", entities={
@EntityResult(entityClass=OrderTaskList.class, fields = {
@FieldResult(name="orderId", column="orderId")
,@FieldResult(name="orderTypeId", column="orderTypeId")
,@FieldResult(name="orderDate", column="orderDate")
,@FieldResult(name="entryDate", column="entryDate")
,@FieldResult(name="grandTotal", column="grandTotal")
,@FieldResult(name="orderRoleTypeId", column="orderRoleTypeId")
,@FieldResult(name="customerPartyId", column="customerPartyId")
,@FieldResult(name="customerFirstName", column="customerFirstName")
,@FieldResult(name="customerLastName", column="customerLastName")
,@FieldResult(name="workEffortId", column="workEffortId")
,@FieldResult(name="workEffortTypeId", column="workEffortTypeId")
,@FieldResult(name="currentStatusId", column="currentStatusId")
,@FieldResult(name="lastStatusUpdate", column="lastStatusUpdate")
,@FieldResult(name="priority", column="priority")
,@FieldResult(name="workEffortName", column="workEffortName")
,@FieldResult(name="description", column="description")
,@FieldResult(name="createdDate", column="createdDate")
,@FieldResult(name="createdByUserLogin", column="createdByUserLogin")
,@FieldResult(name="lastModifiedDate", column="lastModifiedDate")
,@FieldResult(name="lastModifiedByUserLogin", column="lastModifiedByUserLogin")
,@FieldResult(name="estimatedStartDate", column="estimatedStartDate")
,@FieldResult(name="estimatedCompletionDate", column="estimatedCompletionDate")
,@FieldResult(name="actualStartDate", column="actualStartDate")
,@FieldResult(name="actualCompletionDate", column="actualCompletionDate")
,@FieldResult(name="infoUrl", column="infoUrl")
,@FieldResult(name="wepaPartyId", column="wepaPartyId")
,@FieldResult(name="roleTypeId", column="roleTypeId")
,@FieldResult(name="fromDate", column="fromDate")
,@FieldResult(name="thruDate", column="thruDate")
,@FieldResult(name="statusId", column="statusId")
,@FieldResult(name="statusDateTime", column="statusDateTime")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class OrderTaskList extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("orderId", "OH.ORDER_ID");
        fields.put("orderTypeId", "OH.ORDER_TYPE_ID");
        fields.put("orderDate", "OH.ORDER_DATE");
        fields.put("entryDate", "OH.ENTRY_DATE");
        fields.put("grandTotal", "OH.GRAND_TOTAL");
        fields.put("orderRoleTypeId", "OHR.ROLE_TYPE_ID");
        fields.put("customerPartyId", "OHR.PARTY_ID");
        fields.put("customerFirstName", "PS.FIRST_NAME");
        fields.put("customerLastName", "PS.LAST_NAME");
        fields.put("workEffortId", "WE.WORK_EFFORT_ID");
        fields.put("workEffortTypeId", "WE.WORK_EFFORT_TYPE_ID");
        fields.put("currentStatusId", "WE.CURRENT_STATUS_ID");
        fields.put("lastStatusUpdate", "WE.LAST_STATUS_UPDATE");
        fields.put("priority", "WE.PRIORITY");
        fields.put("workEffortName", "WE.WORK_EFFORT_NAME");
        fields.put("description", "WE.DESCRIPTION");
        fields.put("createdDate", "WE.CREATED_DATE");
        fields.put("createdByUserLogin", "WE.CREATED_BY_USER_LOGIN");
        fields.put("lastModifiedDate", "WE.LAST_MODIFIED_DATE");
        fields.put("lastModifiedByUserLogin", "WE.LAST_MODIFIED_BY_USER_LOGIN");
        fields.put("estimatedStartDate", "WE.ESTIMATED_START_DATE");
        fields.put("estimatedCompletionDate", "WE.ESTIMATED_COMPLETION_DATE");
        fields.put("actualStartDate", "WE.ACTUAL_START_DATE");
        fields.put("actualCompletionDate", "WE.ACTUAL_COMPLETION_DATE");
        fields.put("infoUrl", "WE.INFO_URL");
        fields.put("wepaPartyId", "WEPA.PARTY_ID");
        fields.put("roleTypeId", "WEPA.ROLE_TYPE_ID");
        fields.put("fromDate", "WEPA.FROM_DATE");
        fields.put("thruDate", "WEPA.THRU_DATE");
        fields.put("statusId", "WEPA.STATUS_ID");
        fields.put("statusDateTime", "WEPA.STATUS_DATE_TIME");
fieldMapColumns.put("OrderTaskList", fields);
}
  public static enum Fields implements EntityFieldInterface<OrderTaskList> {
    orderId("orderId"),
    orderTypeId("orderTypeId"),
    orderDate("orderDate"),
    entryDate("entryDate"),
    grandTotal("grandTotal"),
    orderRoleTypeId("orderRoleTypeId"),
    customerPartyId("customerPartyId"),
    customerFirstName("customerFirstName"),
    customerLastName("customerLastName"),
    workEffortId("workEffortId"),
    workEffortTypeId("workEffortTypeId"),
    currentStatusId("currentStatusId"),
    lastStatusUpdate("lastStatusUpdate"),
    priority("priority"),
    workEffortName("workEffortName"),
    description("description"),
    createdDate("createdDate"),
    createdByUserLogin("createdByUserLogin"),
    lastModifiedDate("lastModifiedDate"),
    lastModifiedByUserLogin("lastModifiedByUserLogin"),
    estimatedStartDate("estimatedStartDate"),
    estimatedCompletionDate("estimatedCompletionDate"),
    actualStartDate("actualStartDate"),
    actualCompletionDate("actualCompletionDate"),
    infoUrl("infoUrl"),
    wepaPartyId("wepaPartyId"),
    roleTypeId("roleTypeId"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    statusId("statusId"),
    statusDateTime("statusDateTime");
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
    
    private String orderId;
    
    
    private String orderTypeId;
    
    
    private Timestamp orderDate;
    
    
    private Timestamp entryDate;
    
    
    private BigDecimal grandTotal;
    
    
    private String orderRoleTypeId;
    
    
    private String customerPartyId;
    
    
    private String customerFirstName;
    
    
    private String customerLastName;
    
    
    private String workEffortId;
    
    
    private String workEffortTypeId;
    
    
    private String currentStatusId;
    
    
    private Timestamp lastStatusUpdate;
    
    
    private Long priority;
    
    
    private String workEffortName;
    
    
    private String description;
    
    
    private Timestamp createdDate;
    
    
    private String createdByUserLogin;
    
    
    private Timestamp lastModifiedDate;
    
    
    private String lastModifiedByUserLogin;
    
    
    private Timestamp estimatedStartDate;
    
    
    private Timestamp estimatedCompletionDate;
    
    
    private Timestamp actualStartDate;
    
    
    private Timestamp actualCompletionDate;
    
    
    private String infoUrl;
    
    
    private String wepaPartyId;
    
    
    private String roleTypeId;
    
    
    private Timestamp fromDate;
    
    
    private Timestamp thruDate;
    
    
    private String statusId;
    
    
    private Timestamp statusDateTime;

  /**
   * Default constructor.
   */
  public OrderTaskList() {
      super();
      this.baseEntityName = "OrderTaskList";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("orderId");this.primaryKeyNames.add("orderRoleTypeId");this.primaryKeyNames.add("customerPartyId");this.primaryKeyNames.add("workEffortId");this.primaryKeyNames.add("wepaPartyId");this.primaryKeyNames.add("roleTypeId");this.primaryKeyNames.add("fromDate");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public OrderTaskList(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param orderId the orderId to set
     */
    private void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    /**
     * Auto generated value setter.
     * @param orderTypeId the orderTypeId to set
     */
    private void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
    /**
     * Auto generated value setter.
     * @param orderDate the orderDate to set
     */
    private void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    /**
     * Auto generated value setter.
     * @param entryDate the entryDate to set
     */
    private void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }
    /**
     * Auto generated value setter.
     * @param grandTotal the grandTotal to set
     */
    private void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
    /**
     * Auto generated value setter.
     * @param orderRoleTypeId the orderRoleTypeId to set
     */
    private void setOrderRoleTypeId(String orderRoleTypeId) {
        this.orderRoleTypeId = orderRoleTypeId;
    }
    /**
     * Auto generated value setter.
     * @param customerPartyId the customerPartyId to set
     */
    private void setCustomerPartyId(String customerPartyId) {
        this.customerPartyId = customerPartyId;
    }
    /**
     * Auto generated value setter.
     * @param customerFirstName the customerFirstName to set
     */
    private void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
    /**
     * Auto generated value setter.
     * @param customerLastName the customerLastName to set
     */
    private void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    /**
     * Auto generated value setter.
     * @param workEffortId the workEffortId to set
     */
    private void setWorkEffortId(String workEffortId) {
        this.workEffortId = workEffortId;
    }
    /**
     * Auto generated value setter.
     * @param workEffortTypeId the workEffortTypeId to set
     */
    private void setWorkEffortTypeId(String workEffortTypeId) {
        this.workEffortTypeId = workEffortTypeId;
    }
    /**
     * Auto generated value setter.
     * @param currentStatusId the currentStatusId to set
     */
    private void setCurrentStatusId(String currentStatusId) {
        this.currentStatusId = currentStatusId;
    }
    /**
     * Auto generated value setter.
     * @param lastStatusUpdate the lastStatusUpdate to set
     */
    private void setLastStatusUpdate(Timestamp lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }
    /**
     * Auto generated value setter.
     * @param priority the priority to set
     */
    private void setPriority(Long priority) {
        this.priority = priority;
    }
    /**
     * Auto generated value setter.
     * @param workEffortName the workEffortName to set
     */
    private void setWorkEffortName(String workEffortName) {
        this.workEffortName = workEffortName;
    }
    /**
     * Auto generated value setter.
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
    }
    /**
     * Auto generated value setter.
     * @param createdDate the createdDate to set
     */
    private void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * Auto generated value setter.
     * @param createdByUserLogin the createdByUserLogin to set
     */
    private void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }
    /**
     * Auto generated value setter.
     * @param lastModifiedDate the lastModifiedDate to set
     */
    private void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    /**
     * Auto generated value setter.
     * @param lastModifiedByUserLogin the lastModifiedByUserLogin to set
     */
    private void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }
    /**
     * Auto generated value setter.
     * @param estimatedStartDate the estimatedStartDate to set
     */
    private void setEstimatedStartDate(Timestamp estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }
    /**
     * Auto generated value setter.
     * @param estimatedCompletionDate the estimatedCompletionDate to set
     */
    private void setEstimatedCompletionDate(Timestamp estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }
    /**
     * Auto generated value setter.
     * @param actualStartDate the actualStartDate to set
     */
    private void setActualStartDate(Timestamp actualStartDate) {
        this.actualStartDate = actualStartDate;
    }
    /**
     * Auto generated value setter.
     * @param actualCompletionDate the actualCompletionDate to set
     */
    private void setActualCompletionDate(Timestamp actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }
    /**
     * Auto generated value setter.
     * @param infoUrl the infoUrl to set
     */
    private void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
    /**
     * Auto generated value setter.
     * @param wepaPartyId the wepaPartyId to set
     */
    private void setWepaPartyId(String wepaPartyId) {
        this.wepaPartyId = wepaPartyId;
    }
    /**
     * Auto generated value setter.
     * @param roleTypeId the roleTypeId to set
     */
    private void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    /**
     * Auto generated value setter.
     * @param fromDate the fromDate to set
     */
    private void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * Auto generated value setter.
     * @param thruDate the thruDate to set
     */
    private void setThruDate(Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    private void setStatusId(String statusId) {
        this.statusId = statusId;
    }
    /**
     * Auto generated value setter.
     * @param statusDateTime the statusDateTime to set
     */
    private void setStatusDateTime(Timestamp statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrderId() {
        return this.orderId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrderTypeId() {
        return this.orderTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getOrderDate() {
        return this.orderDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEntryDate() {
        return this.entryDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getGrandTotal() {
        return this.grandTotal;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOrderRoleTypeId() {
        return this.orderRoleTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomerPartyId() {
        return this.customerPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomerFirstName() {
        return this.customerFirstName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomerLastName() {
        return this.customerLastName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWorkEffortId() {
        return this.workEffortId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWorkEffortTypeId() {
        return this.workEffortTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCurrentStatusId() {
        return this.currentStatusId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastStatusUpdate() {
        return this.lastStatusUpdate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getPriority() {
        return this.priority;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWorkEffortName() {
        return this.workEffortName;
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
    public Timestamp getCreatedDate() {
        return this.createdDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getLastModifiedByUserLogin() {
        return this.lastModifiedByUserLogin;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEstimatedStartDate() {
        return this.estimatedStartDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEstimatedCompletionDate() {
        return this.estimatedCompletionDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getActualStartDate() {
        return this.actualStartDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getActualCompletionDate() {
        return this.actualCompletionDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getInfoUrl() {
        return this.infoUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWepaPartyId() {
        return this.wepaPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getRoleTypeId() {
        return this.roleTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getFromDate() {
        return this.fromDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getThruDate() {
        return this.thruDate;
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
     * @return <code>Timestamp</code>
     */
    public Timestamp getStatusDateTime() {
        return this.statusDateTime;
    }




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setOrderId((String) mapValue.get("orderId"));
        setOrderTypeId((String) mapValue.get("orderTypeId"));
        setOrderDate((Timestamp) mapValue.get("orderDate"));
        setEntryDate((Timestamp) mapValue.get("entryDate"));
        setGrandTotal(convertToBigDecimal(mapValue.get("grandTotal")));
        setOrderRoleTypeId((String) mapValue.get("orderRoleTypeId"));
        setCustomerPartyId((String) mapValue.get("customerPartyId"));
        setCustomerFirstName((String) mapValue.get("customerFirstName"));
        setCustomerLastName((String) mapValue.get("customerLastName"));
        setWorkEffortId((String) mapValue.get("workEffortId"));
        setWorkEffortTypeId((String) mapValue.get("workEffortTypeId"));
        setCurrentStatusId((String) mapValue.get("currentStatusId"));
        setLastStatusUpdate((Timestamp) mapValue.get("lastStatusUpdate"));
        setPriority((Long) mapValue.get("priority"));
        setWorkEffortName((String) mapValue.get("workEffortName"));
        setDescription((String) mapValue.get("description"));
        setCreatedDate((Timestamp) mapValue.get("createdDate"));
        setCreatedByUserLogin((String) mapValue.get("createdByUserLogin"));
        setLastModifiedDate((Timestamp) mapValue.get("lastModifiedDate"));
        setLastModifiedByUserLogin((String) mapValue.get("lastModifiedByUserLogin"));
        setEstimatedStartDate((Timestamp) mapValue.get("estimatedStartDate"));
        setEstimatedCompletionDate((Timestamp) mapValue.get("estimatedCompletionDate"));
        setActualStartDate((Timestamp) mapValue.get("actualStartDate"));
        setActualCompletionDate((Timestamp) mapValue.get("actualCompletionDate"));
        setInfoUrl((String) mapValue.get("infoUrl"));
        setWepaPartyId((String) mapValue.get("wepaPartyId"));
        setRoleTypeId((String) mapValue.get("roleTypeId"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        setStatusId((String) mapValue.get("statusId"));
        setStatusDateTime((Timestamp) mapValue.get("statusDateTime"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("orderId", getOrderId());
        mapValue.put("orderTypeId", getOrderTypeId());
        mapValue.put("orderDate", getOrderDate());
        mapValue.put("entryDate", getEntryDate());
        mapValue.put("grandTotal", getGrandTotal());
        mapValue.put("orderRoleTypeId", getOrderRoleTypeId());
        mapValue.put("customerPartyId", getCustomerPartyId());
        mapValue.put("customerFirstName", getCustomerFirstName());
        mapValue.put("customerLastName", getCustomerLastName());
        mapValue.put("workEffortId", getWorkEffortId());
        mapValue.put("workEffortTypeId", getWorkEffortTypeId());
        mapValue.put("currentStatusId", getCurrentStatusId());
        mapValue.put("lastStatusUpdate", getLastStatusUpdate());
        mapValue.put("priority", getPriority());
        mapValue.put("workEffortName", getWorkEffortName());
        mapValue.put("description", getDescription());
        mapValue.put("createdDate", getCreatedDate());
        mapValue.put("createdByUserLogin", getCreatedByUserLogin());
        mapValue.put("lastModifiedDate", getLastModifiedDate());
        mapValue.put("lastModifiedByUserLogin", getLastModifiedByUserLogin());
        mapValue.put("estimatedStartDate", getEstimatedStartDate());
        mapValue.put("estimatedCompletionDate", getEstimatedCompletionDate());
        mapValue.put("actualStartDate", getActualStartDate());
        mapValue.put("actualCompletionDate", getActualCompletionDate());
        mapValue.put("infoUrl", getInfoUrl());
        mapValue.put("wepaPartyId", getWepaPartyId());
        mapValue.put("roleTypeId", getRoleTypeId());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("statusId", getStatusId());
        mapValue.put("statusDateTime", getStatusDateTime());
        return mapValue;
    }


}
