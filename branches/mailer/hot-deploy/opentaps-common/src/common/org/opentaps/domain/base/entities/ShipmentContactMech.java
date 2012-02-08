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
 * Auto generated base entity ShipmentContactMech.
 */
@javax.persistence.Entity
@Table(name="SHIPMENT_CONTACT_MECH")
@IdClass(ShipmentContactMechPk.class)
public class ShipmentContactMech extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("shipmentId", "SHIPMENT_ID");
        fields.put("shipmentContactMechTypeId", "SHIPMENT_CONTACT_MECH_TYPE_ID");
        fields.put("contactMechId", "CONTACT_MECH_ID");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("ShipmentContactMech", fields);
}
  public static enum Fields implements EntityFieldInterface<ShipmentContactMech> {
    shipmentId("shipmentId"),
    shipmentContactMechTypeId("shipmentContactMechTypeId"),
    contactMechId("contactMechId"),
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
    
    @Column(name="SHIPMENT_ID")
    private String shipmentId;
    @Id
    
    @Column(name="SHIPMENT_CONTACT_MECH_TYPE_ID")
    private String shipmentContactMechTypeId;
    
    @Column(name="CONTACT_MECH_ID")
    private String contactMechId;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="SHIPMENT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Shipment shipment = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="CONTACT_MECH_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ContactMech contactMech = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="SHIPMENT_CONTACT_MECH_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ShipmentContactMechType shipmentContactMechType = null;

  /**
   * Default constructor.
   */
  public ShipmentContactMech() {
      super();
      this.baseEntityName = "ShipmentContactMech";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("shipmentId");this.primaryKeyNames.add("shipmentContactMechTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ShipmentContactMech(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param shipmentId the shipmentId to set
     */
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    /**
     * Auto generated value setter.
     * @param shipmentContactMechTypeId the shipmentContactMechTypeId to set
     */
    public void setShipmentContactMechTypeId(String shipmentContactMechTypeId) {
        this.shipmentContactMechTypeId = shipmentContactMechTypeId;
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
    public String getShipmentId() {
        return this.shipmentId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getShipmentContactMechTypeId() {
        return this.shipmentContactMechTypeId;
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
     * Auto generated method that gets the related <code>Shipment</code> by the relation named <code>Shipment</code>.
     * @return the <code>Shipment</code>
     * @throws RepositoryException if an error occurs
     */
    public Shipment getShipment() throws RepositoryException {
        if (this.shipment == null) {
            this.shipment = getRelatedOne(Shipment.class, "Shipment");
        }
        return this.shipment;
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
     * Auto generated method that gets the related <code>ShipmentContactMechType</code> by the relation named <code>ShipmentContactMechType</code>.
     * @return the <code>ShipmentContactMechType</code>
     * @throws RepositoryException if an error occurs
     */
    public ShipmentContactMechType getShipmentContactMechType() throws RepositoryException {
        if (this.shipmentContactMechType == null) {
            this.shipmentContactMechType = getRelatedOne(ShipmentContactMechType.class, "ShipmentContactMechType");
        }
        return this.shipmentContactMechType;
    }

    /**
     * Auto generated value setter.
     * @param shipment the shipment to set
    */
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
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
     * @param shipmentContactMechType the shipmentContactMechType to set
    */
    public void setShipmentContactMechType(ShipmentContactMechType shipmentContactMechType) {
        this.shipmentContactMechType = shipmentContactMechType;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setShipmentId((String) mapValue.get("shipmentId"));
        setShipmentContactMechTypeId((String) mapValue.get("shipmentContactMechTypeId"));
        setContactMechId((String) mapValue.get("contactMechId"));
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
        mapValue.put("shipmentId", getShipmentId());
        mapValue.put("shipmentContactMechTypeId", getShipmentContactMechTypeId());
        mapValue.put("contactMechId", getContactMechId());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}