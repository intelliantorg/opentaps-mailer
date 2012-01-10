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

/**
 * Auto generated base entity MaxContentApprovalView.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectMaxContentApprovalViews", query="SELECT C.CONTENT_TYPE_ID AS \"contentTypeId\",CA.CONTENT_ID AS \"contentId\",CA.PARTY_ID AS \"partyId\",CA.ROLE_TYPE_ID AS \"roleTypeId\",CA.SEQUENCE_NUM AS \"sequenceNum\",CA.CONTENT_REVISION_SEQ_ID AS \"contentRevisionSeqId\",CA.CONTENT_REVISION_SEQ_ID AS \"contentRevisionSeqId\" FROM CONTENT C INNER JOIN CONTENT_APPROVAL CA ON C.CONTENT_ID = CA.CONTENT_ID", resultSetMapping="MaxContentApprovalViewMapping")
@SqlResultSetMapping(name="MaxContentApprovalViewMapping", entities={
@EntityResult(entityClass=MaxContentApprovalView.class, fields = {
@FieldResult(name="contentTypeId", column="contentTypeId")
,@FieldResult(name="contentId", column="contentId")
,@FieldResult(name="partyId", column="partyId")
,@FieldResult(name="roleTypeId", column="roleTypeId")
,@FieldResult(name="sequenceNum", column="sequenceNum")
,@FieldResult(name="contentRevisionSeqId", column="contentRevisionSeqId")
,@FieldResult(name="maxContentRevisionSeqId", column="maxContentRevisionSeqId")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class MaxContentApprovalView extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("contentTypeId", "C.CONTENT_TYPE_ID");
        fields.put("contentId", "CA.CONTENT_ID");
        fields.put("partyId", "CA.PARTY_ID");
        fields.put("roleTypeId", "CA.ROLE_TYPE_ID");
        fields.put("sequenceNum", "CA.SEQUENCE_NUM");
        fields.put("contentRevisionSeqId", "CA.CONTENT_REVISION_SEQ_ID");
        fields.put("maxContentRevisionSeqId", "CA.CONTENT_REVISION_SEQ_ID");
fieldMapColumns.put("MaxContentApprovalView", fields);
}
  public static enum Fields implements EntityFieldInterface<MaxContentApprovalView> {
    contentTypeId("contentTypeId"),
    contentId("contentId"),
    partyId("partyId"),
    roleTypeId("roleTypeId"),
    sequenceNum("sequenceNum"),
    contentRevisionSeqId("contentRevisionSeqId"),
    maxContentRevisionSeqId("maxContentRevisionSeqId");
    private final String fieldName;
    private Fields(String name) { fieldName = name; }
    /** {@inheritDoc} */
    public String getName() { return fieldName; }
    /** {@inheritDoc} */
    public String asc() { return fieldName + " ASC"; }
    /** {@inheritDoc} */
    public String desc() { return fieldName + " DESC"; }
  }

    
    
    private String contentTypeId;
    @Id
    
    private String contentId;
    
    
    private String partyId;
    
    
    private String roleTypeId;
    
    
    private Long sequenceNum;
    
    
    private String contentRevisionSeqId;
    
    
    private String maxContentRevisionSeqId;

  /**
   * Default constructor.
   */
  public MaxContentApprovalView() {
      super();
      this.baseEntityName = "MaxContentApprovalView";
      this.isView = true;
      
      
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public MaxContentApprovalView(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param contentTypeId the contentTypeId to set
     */
    private void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }
    /**
     * Auto generated value setter.
     * @param contentId the contentId to set
     */
    private void setContentId(String contentId) {
        this.contentId = contentId;
    }
    /**
     * Auto generated value setter.
     * @param partyId the partyId to set
     */
    private void setPartyId(String partyId) {
        this.partyId = partyId;
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
     * @param sequenceNum the sequenceNum to set
     */
    private void setSequenceNum(Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }
    /**
     * Auto generated value setter.
     * @param contentRevisionSeqId the contentRevisionSeqId to set
     */
    private void setContentRevisionSeqId(String contentRevisionSeqId) {
        this.contentRevisionSeqId = contentRevisionSeqId;
    }
    /**
     * Auto generated value setter.
     * @param maxContentRevisionSeqId the maxContentRevisionSeqId to set
     */
    private void setMaxContentRevisionSeqId(String maxContentRevisionSeqId) {
        this.maxContentRevisionSeqId = maxContentRevisionSeqId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getContentTypeId() {
        return this.contentTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getContentId() {
        return this.contentId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPartyId() {
        return this.partyId;
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
     * @return <code>Long</code>
     */
    public Long getSequenceNum() {
        return this.sequenceNum;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getContentRevisionSeqId() {
        return this.contentRevisionSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getMaxContentRevisionSeqId() {
        return this.maxContentRevisionSeqId;
    }




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setContentTypeId((String) mapValue.get("contentTypeId"));
        setContentId((String) mapValue.get("contentId"));
        setPartyId((String) mapValue.get("partyId"));
        setRoleTypeId((String) mapValue.get("roleTypeId"));
        setSequenceNum((Long) mapValue.get("sequenceNum"));
        setContentRevisionSeqId((String) mapValue.get("contentRevisionSeqId"));
        setMaxContentRevisionSeqId((String) mapValue.get("maxContentRevisionSeqId"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("contentTypeId", getContentTypeId());
        mapValue.put("contentId", getContentId());
        mapValue.put("partyId", getPartyId());
        mapValue.put("roleTypeId", getRoleTypeId());
        mapValue.put("sequenceNum", getSequenceNum());
        mapValue.put("contentRevisionSeqId", getContentRevisionSeqId());
        mapValue.put("maxContentRevisionSeqId", getMaxContentRevisionSeqId());
        return mapValue;
    }


}
