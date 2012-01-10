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
 * Auto generated base entity GlAccountGroup.
 */
@javax.persistence.Entity
@Table(name="GL_ACCOUNT_GROUP")
public class GlAccountGroup extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("glAccountGroupId", "GL_ACCOUNT_GROUP_ID");
        fields.put("glAccountGroupTypeId", "GL_ACCOUNT_GROUP_TYPE_ID");
        fields.put("description", "DESCRIPTION");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("GlAccountGroup", fields);
}
  public static enum Fields implements EntityFieldInterface<GlAccountGroup> {
    glAccountGroupId("glAccountGroupId"),
    glAccountGroupTypeId("glAccountGroupTypeId"),
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

    @org.hibernate.annotations.GenericGenerator(name="GlAccountGroup_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="GlAccountGroup_GEN")   
    @Id
    
    @Column(name="GL_ACCOUNT_GROUP_ID")
    private String glAccountGroupId;
    
    @Column(name="GL_ACCOUNT_GROUP_TYPE_ID")
    private String glAccountGroupTypeId;
    
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
    @JoinColumn(name="GL_ACCOUNT_GROUP_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private GlAccountGroupType glAccountGroupType = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="GL_ACCOUNT_GROUP_ID")
    private List<GlAccountGroupMember> glAccountGroupMembers = null;

  /**
   * Default constructor.
   */
  public GlAccountGroup() {
      super();
      this.baseEntityName = "GlAccountGroup";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("glAccountGroupId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public GlAccountGroup(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param glAccountGroupId the glAccountGroupId to set
     */
    public void setGlAccountGroupId(String glAccountGroupId) {
        this.glAccountGroupId = glAccountGroupId;
    }
    /**
     * Auto generated value setter.
     * @param glAccountGroupTypeId the glAccountGroupTypeId to set
     */
    public void setGlAccountGroupTypeId(String glAccountGroupTypeId) {
        this.glAccountGroupTypeId = glAccountGroupTypeId;
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
    public String getGlAccountGroupId() {
        return this.glAccountGroupId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getGlAccountGroupTypeId() {
        return this.glAccountGroupTypeId;
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
     * Auto generated method that gets the related <code>GlAccountGroupType</code> by the relation named <code>GlAccountGroupType</code>.
     * @return the <code>GlAccountGroupType</code>
     * @throws RepositoryException if an error occurs
     */
    public GlAccountGroupType getGlAccountGroupType() throws RepositoryException {
        if (this.glAccountGroupType == null) {
            this.glAccountGroupType = getRelatedOne(GlAccountGroupType.class, "GlAccountGroupType");
        }
        return this.glAccountGroupType;
    }
    /**
     * Auto generated method that gets the related <code>GlAccountGroupMember</code> by the relation named <code>GlAccountGroupMember</code>.
     * @return the list of <code>GlAccountGroupMember</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends GlAccountGroupMember> getGlAccountGroupMembers() throws RepositoryException {
        if (this.glAccountGroupMembers == null) {
            this.glAccountGroupMembers = getRelated(GlAccountGroupMember.class, "GlAccountGroupMember");
        }
        return this.glAccountGroupMembers;
    }

    /**
     * Auto generated value setter.
     * @param glAccountGroupType the glAccountGroupType to set
    */
    public void setGlAccountGroupType(GlAccountGroupType glAccountGroupType) {
        this.glAccountGroupType = glAccountGroupType;
    }
    /**
     * Auto generated value setter.
     * @param glAccountGroupMembers the glAccountGroupMembers to set
    */
    public void setGlAccountGroupMembers(List<GlAccountGroupMember> glAccountGroupMembers) {
        this.glAccountGroupMembers = glAccountGroupMembers;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setGlAccountGroupId((String) mapValue.get("glAccountGroupId"));
        setGlAccountGroupTypeId((String) mapValue.get("glAccountGroupTypeId"));
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
        mapValue.put("glAccountGroupId", getGlAccountGroupId());
        mapValue.put("glAccountGroupTypeId", getGlAccountGroupTypeId());
        mapValue.put("description", getDescription());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
