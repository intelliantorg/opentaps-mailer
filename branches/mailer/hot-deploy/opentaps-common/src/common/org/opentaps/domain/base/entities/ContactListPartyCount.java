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
import java.sql.Timestamp;

/**
 * Auto generated base entity ContactListPartyCount.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectContactListPartyCounts", query="SELECT CLP.CONTACT_LIST_ID AS \"contactListId\",CLP.PARTY_ID AS \"partyId\",CLP.FROM_DATE AS \"fromDate\",CLP.THRU_DATE AS \"thruDate\",CLP.STATUS_ID AS \"statusId\",PA.COUNTRY_GEO_ID AS \"countryGeoId\",PCM.FROM_DATE AS \"fromDate\",PCM.THRU_DATE AS \"thruDate\" FROM CONTACT_LIST_PARTY CLP LEFT JOIN POSTAL_ADDRESS PA ON CLP.PREFERRED_CONTACT_MECH_ID = PA.CONTACT_MECH_ID LEFT JOIN PARTY_CONTACT_MECH PCM ON CLP.PARTY_ID = PCM.PARTY_ID AND CLP.PREFERRED_CONTACT_MECH_ID = PCM.CONTACT_MECH_ID", resultSetMapping="ContactListPartyCountMapping")
@SqlResultSetMapping(name="ContactListPartyCountMapping", entities={
@EntityResult(entityClass=ContactListPartyCount.class, fields = {
@FieldResult(name="contactListId", column="contactListId")
,@FieldResult(name="partyId", column="partyId")
,@FieldResult(name="contactMechFromDate", column="contactMechFromDate")
,@FieldResult(name="contactMechThruDate", column="contactMechThruDate")
,@FieldResult(name="statusId", column="statusId")
,@FieldResult(name="countryGeoId", column="countryGeoId")
,@FieldResult(name="fromDate", column="fromDate")
,@FieldResult(name="thruDate", column="thruDate")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class ContactListPartyCount extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("contactListId", "CLP.CONTACT_LIST_ID");
        fields.put("partyId", "CLP.PARTY_ID");
        fields.put("contactMechFromDate", "CLP.FROM_DATE");
        fields.put("contactMechThruDate", "CLP.THRU_DATE");
        fields.put("statusId", "CLP.STATUS_ID");
        fields.put("countryGeoId", "PA.COUNTRY_GEO_ID");
        fields.put("fromDate", "PCM.FROM_DATE");
        fields.put("thruDate", "PCM.THRU_DATE");
fieldMapColumns.put("ContactListPartyCount", fields);
}
  public static enum Fields implements EntityFieldInterface<ContactListPartyCount> {
    contactListId("contactListId"),
    partyId("partyId"),
    contactMechFromDate("contactMechFromDate"),
    contactMechThruDate("contactMechThruDate"),
    statusId("statusId"),
    countryGeoId("countryGeoId"),
    fromDate("fromDate"),
    thruDate("thruDate");
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
    
    private String contactListId;
    
    
    private Long partyId;
    
    
    private Timestamp contactMechFromDate;
    
    
    private Timestamp contactMechThruDate;
    
    
    private String statusId;
    
    
    private String countryGeoId;
    
    
    private Timestamp fromDate;
    
    
    private Timestamp thruDate;

  /**
   * Default constructor.
   */
  public ContactListPartyCount() {
      super();
      this.baseEntityName = "ContactListPartyCount";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("contactListId");this.primaryKeyNames.add("partyId");this.primaryKeyNames.add("contactMechFromDate");this.primaryKeyNames.add("fromDate");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ContactListPartyCount(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param contactListId the contactListId to set
     */
    private void setContactListId(String contactListId) {
        this.contactListId = contactListId;
    }
    /**
     * Auto generated value setter.
     * @param partyId the partyId to set
     */
    private void setPartyId(Long partyId) {
        this.partyId = partyId;
    }
    /**
     * Auto generated value setter.
     * @param contactMechFromDate the contactMechFromDate to set
     */
    private void setContactMechFromDate(Timestamp contactMechFromDate) {
        this.contactMechFromDate = contactMechFromDate;
    }
    /**
     * Auto generated value setter.
     * @param contactMechThruDate the contactMechThruDate to set
     */
    private void setContactMechThruDate(Timestamp contactMechThruDate) {
        this.contactMechThruDate = contactMechThruDate;
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
     * @param countryGeoId the countryGeoId to set
     */
    private void setCountryGeoId(String countryGeoId) {
        this.countryGeoId = countryGeoId;
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
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getContactListId() {
        return this.contactListId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getPartyId() {
        return this.partyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getContactMechFromDate() {
        return this.contactMechFromDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getContactMechThruDate() {
        return this.contactMechThruDate;
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
    public String getCountryGeoId() {
        return this.countryGeoId;
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




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setContactListId((String) mapValue.get("contactListId"));
        setPartyId((Long) mapValue.get("partyId"));
        setContactMechFromDate((Timestamp) mapValue.get("contactMechFromDate"));
        setContactMechThruDate((Timestamp) mapValue.get("contactMechThruDate"));
        setStatusId((String) mapValue.get("statusId"));
        setCountryGeoId((String) mapValue.get("countryGeoId"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("contactListId", getContactListId());
        mapValue.put("partyId", getPartyId());
        mapValue.put("contactMechFromDate", getContactMechFromDate());
        mapValue.put("contactMechThruDate", getContactMechThruDate());
        mapValue.put("statusId", getStatusId());
        mapValue.put("countryGeoId", getCountryGeoId());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        return mapValue;
    }


}
