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
 * Auto generated base entity TelecomNumber.
 */
@javax.persistence.Entity
@Table(name="TELECOM_NUMBER")
public class TelecomNumber extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("contactMechId", "CONTACT_MECH_ID");
        fields.put("countryCode", "COUNTRY_CODE");
        fields.put("areaCode", "AREA_CODE");
        fields.put("contactNumber", "CONTACT_NUMBER");
        fields.put("askForName", "ASK_FOR_NAME");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("TelecomNumber", fields);
}
  public static enum Fields implements EntityFieldInterface<TelecomNumber> {
    contactMechId("contactMechId"),
    countryCode("countryCode"),
    areaCode("areaCode"),
    contactNumber("contactNumber"),
    askForName("askForName"),
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

    @org.hibernate.annotations.GenericGenerator(name="TelecomNumber_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="TelecomNumber_GEN")   
    @Id
    
    @Column(name="CONTACT_MECH_ID")
    private String contactMechId;
    
    @Column(name="COUNTRY_CODE")
    private String countryCode;
    
    @Column(name="AREA_CODE")
    private String areaCode;
    
    @Column(name="CONTACT_NUMBER")
    private String contactNumber;
    
    @Column(name="ASK_FOR_NAME")
    private String askForName;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CONTACT_MECH_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ContactMech contactMech = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="telecomNumber", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="CONTACT_MECH_ID")
    private List<FacilityContactMech> facilityContactMeches = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="TELECOM_CONTACT_MECH_ID")
    private List<OrderItemShipGroup> telecomOrderItemShipGroups = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="telecomNumber", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="CONTACT_MECH_ID")
    private List<PartyContactMech> partyContactMeches = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="telecomNumber", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="CONTACT_MECH_ID")
    private List<PartyContactMechPurpose> partyContactMechPurposes = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGIN_PHONE_CONTACT_MECH_ID")
    private List<ReturnHeader> originReturnHeaders = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGIN_TELECOM_NUMBER_ID")
    private List<Shipment> originShipments = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="DESTINATION_TELECOM_NUMBER_ID")
    private List<Shipment> destinationShipments = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="ORIGIN_TELECOM_NUMBER_ID")
    private List<ShipmentRouteSegment> originShipmentRouteSegments = null;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="DEST_TELECOM_NUMBER_ID")
    private List<ShipmentRouteSegment> destShipmentRouteSegments = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="telecomNumber", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="CONTACT_MECH_ID")
    private List<WorkEffortContactMech> workEffortContactMeches = null;

  /**
   * Default constructor.
   */
  public TelecomNumber() {
      super();
      this.baseEntityName = "TelecomNumber";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("contactMechId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public TelecomNumber(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param contactMechId the contactMechId to set
     */
    public void setContactMechId(String contactMechId) {
        this.contactMechId = contactMechId;
    }
    /**
     * Auto generated value setter.
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * Auto generated value setter.
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    /**
     * Auto generated value setter.
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    /**
     * Auto generated value setter.
     * @param askForName the askForName to set
     */
    public void setAskForName(String askForName) {
        this.askForName = askForName;
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
    public String getContactMechId() {
        return this.contactMechId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCountryCode() {
        return this.countryCode;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAreaCode() {
        return this.areaCode;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getContactNumber() {
        return this.contactNumber;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAskForName() {
        return this.askForName;
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
     * Auto generated method that gets the related <code>ContactMech</code> by the relation named <code>ContactMech</code>.
     * @return the <code>ContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public ContactMech getContactMech() throws RepositoryException {
        if (this.contactMech == null) {
            this.contactMech = getRelatedOne(ContactMech.class, "ContactMech");
        }
        return this.contactMech;
    }
    /**
     * Auto generated method that gets the related <code>FacilityContactMech</code> by the relation named <code>FacilityContactMech</code>.
     * @return the list of <code>FacilityContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends FacilityContactMech> getFacilityContactMeches() throws RepositoryException {
        if (this.facilityContactMeches == null) {
            this.facilityContactMeches = getRelated(FacilityContactMech.class, "FacilityContactMech");
        }
        return this.facilityContactMeches;
    }
    /**
     * Auto generated method that gets the related <code>OrderItemShipGroup</code> by the relation named <code>TelecomOrderItemShipGroup</code>.
     * @return the list of <code>OrderItemShipGroup</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends OrderItemShipGroup> getTelecomOrderItemShipGroups() throws RepositoryException {
        if (this.telecomOrderItemShipGroups == null) {
            this.telecomOrderItemShipGroups = getRelated(OrderItemShipGroup.class, "TelecomOrderItemShipGroup");
        }
        return this.telecomOrderItemShipGroups;
    }
    /**
     * Auto generated method that gets the related <code>PartyContactMech</code> by the relation named <code>PartyContactMech</code>.
     * @return the list of <code>PartyContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends PartyContactMech> getPartyContactMeches() throws RepositoryException {
        if (this.partyContactMeches == null) {
            this.partyContactMeches = getRelated(PartyContactMech.class, "PartyContactMech");
        }
        return this.partyContactMeches;
    }
    /**
     * Auto generated method that gets the related <code>PartyContactMechPurpose</code> by the relation named <code>PartyContactMechPurpose</code>.
     * @return the list of <code>PartyContactMechPurpose</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends PartyContactMechPurpose> getPartyContactMechPurposes() throws RepositoryException {
        if (this.partyContactMechPurposes == null) {
            this.partyContactMechPurposes = getRelated(PartyContactMechPurpose.class, "PartyContactMechPurpose");
        }
        return this.partyContactMechPurposes;
    }
    /**
     * Auto generated method that gets the related <code>ReturnHeader</code> by the relation named <code>OriginReturnHeader</code>.
     * @return the list of <code>ReturnHeader</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ReturnHeader> getOriginReturnHeaders() throws RepositoryException {
        if (this.originReturnHeaders == null) {
            this.originReturnHeaders = getRelated(ReturnHeader.class, "OriginReturnHeader");
        }
        return this.originReturnHeaders;
    }
    /**
     * Auto generated method that gets the related <code>Shipment</code> by the relation named <code>OriginShipment</code>.
     * @return the list of <code>Shipment</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends Shipment> getOriginShipments() throws RepositoryException {
        if (this.originShipments == null) {
            this.originShipments = getRelated(Shipment.class, "OriginShipment");
        }
        return this.originShipments;
    }
    /**
     * Auto generated method that gets the related <code>Shipment</code> by the relation named <code>DestinationShipment</code>.
     * @return the list of <code>Shipment</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends Shipment> getDestinationShipments() throws RepositoryException {
        if (this.destinationShipments == null) {
            this.destinationShipments = getRelated(Shipment.class, "DestinationShipment");
        }
        return this.destinationShipments;
    }
    /**
     * Auto generated method that gets the related <code>ShipmentRouteSegment</code> by the relation named <code>OriginShipmentRouteSegment</code>.
     * @return the list of <code>ShipmentRouteSegment</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ShipmentRouteSegment> getOriginShipmentRouteSegments() throws RepositoryException {
        if (this.originShipmentRouteSegments == null) {
            this.originShipmentRouteSegments = getRelated(ShipmentRouteSegment.class, "OriginShipmentRouteSegment");
        }
        return this.originShipmentRouteSegments;
    }
    /**
     * Auto generated method that gets the related <code>ShipmentRouteSegment</code> by the relation named <code>DestShipmentRouteSegment</code>.
     * @return the list of <code>ShipmentRouteSegment</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ShipmentRouteSegment> getDestShipmentRouteSegments() throws RepositoryException {
        if (this.destShipmentRouteSegments == null) {
            this.destShipmentRouteSegments = getRelated(ShipmentRouteSegment.class, "DestShipmentRouteSegment");
        }
        return this.destShipmentRouteSegments;
    }
    /**
     * Auto generated method that gets the related <code>WorkEffortContactMech</code> by the relation named <code>WorkEffortContactMech</code>.
     * @return the list of <code>WorkEffortContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends WorkEffortContactMech> getWorkEffortContactMeches() throws RepositoryException {
        if (this.workEffortContactMeches == null) {
            this.workEffortContactMeches = getRelated(WorkEffortContactMech.class, "WorkEffortContactMech");
        }
        return this.workEffortContactMeches;
    }

    /**
     * Auto generated value setter.
     * @param contactMech the contactMech to set
    */
    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }
    /**
     * Auto generated value setter.
     * @param facilityContactMeches the facilityContactMeches to set
    */
    public void setFacilityContactMeches(List<FacilityContactMech> facilityContactMeches) {
        this.facilityContactMeches = facilityContactMeches;
    }
    /**
     * Auto generated value setter.
     * @param telecomOrderItemShipGroups the telecomOrderItemShipGroups to set
    */
    public void setTelecomOrderItemShipGroups(List<OrderItemShipGroup> telecomOrderItemShipGroups) {
        this.telecomOrderItemShipGroups = telecomOrderItemShipGroups;
    }
    /**
     * Auto generated value setter.
     * @param partyContactMeches the partyContactMeches to set
    */
    public void setPartyContactMeches(List<PartyContactMech> partyContactMeches) {
        this.partyContactMeches = partyContactMeches;
    }
    /**
     * Auto generated value setter.
     * @param partyContactMechPurposes the partyContactMechPurposes to set
    */
    public void setPartyContactMechPurposes(List<PartyContactMechPurpose> partyContactMechPurposes) {
        this.partyContactMechPurposes = partyContactMechPurposes;
    }
    /**
     * Auto generated value setter.
     * @param originReturnHeaders the originReturnHeaders to set
    */
    public void setOriginReturnHeaders(List<ReturnHeader> originReturnHeaders) {
        this.originReturnHeaders = originReturnHeaders;
    }
    /**
     * Auto generated value setter.
     * @param originShipments the originShipments to set
    */
    public void setOriginShipments(List<Shipment> originShipments) {
        this.originShipments = originShipments;
    }
    /**
     * Auto generated value setter.
     * @param destinationShipments the destinationShipments to set
    */
    public void setDestinationShipments(List<Shipment> destinationShipments) {
        this.destinationShipments = destinationShipments;
    }
    /**
     * Auto generated value setter.
     * @param originShipmentRouteSegments the originShipmentRouteSegments to set
    */
    public void setOriginShipmentRouteSegments(List<ShipmentRouteSegment> originShipmentRouteSegments) {
        this.originShipmentRouteSegments = originShipmentRouteSegments;
    }
    /**
     * Auto generated value setter.
     * @param destShipmentRouteSegments the destShipmentRouteSegments to set
    */
    public void setDestShipmentRouteSegments(List<ShipmentRouteSegment> destShipmentRouteSegments) {
        this.destShipmentRouteSegments = destShipmentRouteSegments;
    }
    /**
     * Auto generated value setter.
     * @param workEffortContactMeches the workEffortContactMeches to set
    */
    public void setWorkEffortContactMeches(List<WorkEffortContactMech> workEffortContactMeches) {
        this.workEffortContactMeches = workEffortContactMeches;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addFacilityContactMeche(FacilityContactMech facilityContactMeche) {
        if (this.facilityContactMeches == null) {
            this.facilityContactMeches = new ArrayList<FacilityContactMech>();
        }
        this.facilityContactMeches.add(facilityContactMeche);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeFacilityContactMeche(FacilityContactMech facilityContactMeche) {
        if (this.facilityContactMeches == null) {
            return;
        }
        this.facilityContactMeches.remove(facilityContactMeche);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearFacilityContactMeche() {
        if (this.facilityContactMeches == null) {
            return;
        }
        this.facilityContactMeches.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addPartyContactMeche(PartyContactMech partyContactMeche) {
        if (this.partyContactMeches == null) {
            this.partyContactMeches = new ArrayList<PartyContactMech>();
        }
        this.partyContactMeches.add(partyContactMeche);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removePartyContactMeche(PartyContactMech partyContactMeche) {
        if (this.partyContactMeches == null) {
            return;
        }
        this.partyContactMeches.remove(partyContactMeche);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearPartyContactMeche() {
        if (this.partyContactMeches == null) {
            return;
        }
        this.partyContactMeches.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addPartyContactMechPurpose(PartyContactMechPurpose partyContactMechPurpose) {
        if (this.partyContactMechPurposes == null) {
            this.partyContactMechPurposes = new ArrayList<PartyContactMechPurpose>();
        }
        this.partyContactMechPurposes.add(partyContactMechPurpose);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removePartyContactMechPurpose(PartyContactMechPurpose partyContactMechPurpose) {
        if (this.partyContactMechPurposes == null) {
            return;
        }
        this.partyContactMechPurposes.remove(partyContactMechPurpose);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearPartyContactMechPurpose() {
        if (this.partyContactMechPurposes == null) {
            return;
        }
        this.partyContactMechPurposes.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addWorkEffortContactMeche(WorkEffortContactMech workEffortContactMeche) {
        if (this.workEffortContactMeches == null) {
            this.workEffortContactMeches = new ArrayList<WorkEffortContactMech>();
        }
        this.workEffortContactMeches.add(workEffortContactMeche);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeWorkEffortContactMeche(WorkEffortContactMech workEffortContactMeche) {
        if (this.workEffortContactMeches == null) {
            return;
        }
        this.workEffortContactMeches.remove(workEffortContactMeche);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearWorkEffortContactMeche() {
        if (this.workEffortContactMeches == null) {
            return;
        }
        this.workEffortContactMeches.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setContactMechId((String) mapValue.get("contactMechId"));
        setCountryCode((String) mapValue.get("countryCode"));
        setAreaCode((String) mapValue.get("areaCode"));
        setContactNumber((String) mapValue.get("contactNumber"));
        setAskForName((String) mapValue.get("askForName"));
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
        mapValue.put("contactMechId", getContactMechId());
        mapValue.put("countryCode", getCountryCode());
        mapValue.put("areaCode", getAreaCode());
        mapValue.put("contactNumber", getContactNumber());
        mapValue.put("askForName", getAskForName());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}