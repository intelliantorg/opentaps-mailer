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
 * Auto generated base entity InventoryEventPlannedType.
 */
@javax.persistence.Entity
@Table(name="INVENTORY_EVENT_PLANNED_TYPE")
public class InventoryEventPlannedType extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("inventoryEventPlanTypeId", "INVENTORY_EVENT_PLAN_TYPE_ID");
        fields.put("description", "DESCRIPTION");
        fields.put("inOut", "IN_OUT");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("InventoryEventPlannedType", fields);
}
  public static enum Fields implements EntityFieldInterface<InventoryEventPlannedType> {
    inventoryEventPlanTypeId("inventoryEventPlanTypeId"),
    description("description"),
    inOut("inOut"),
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

    @org.hibernate.annotations.GenericGenerator(name="InventoryEventPlannedType_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
    @GeneratedValue(generator="InventoryEventPlannedType_GEN")   
    @Id
    
    @Column(name="INVENTORY_EVENT_PLAN_TYPE_ID")
    private String inventoryEventPlanTypeId;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="IN_OUT")
    private String inOut;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="inventoryEventPlannedType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="INVENTORY_EVENT_PLAN_TYPE_ID")
    private List<InventoryEventPlanned> inventoryEventPlanneds = null;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="inventoryEventPlannedType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="INVENTORY_EVENT_PLAN_TYPE_ID")
    private List<MrpInventoryEvent> mrpInventoryEvents = null;

  /**
   * Default constructor.
   */
  public InventoryEventPlannedType() {
      super();
      this.baseEntityName = "InventoryEventPlannedType";
      this.isView = false;
      this.resourceName = "ManufacturingEntityLabels";
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("inventoryEventPlanTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public InventoryEventPlannedType(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param inventoryEventPlanTypeId the inventoryEventPlanTypeId to set
     */
    public void setInventoryEventPlanTypeId(String inventoryEventPlanTypeId) {
        this.inventoryEventPlanTypeId = inventoryEventPlanTypeId;
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
     * @param inOut the inOut to set
     */
    public void setInOut(String inOut) {
        this.inOut = inOut;
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
    public String getInventoryEventPlanTypeId() {
        return this.inventoryEventPlanTypeId;
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
     * @return <code>String</code>
     */
    public String getInOut() {
        return this.inOut;
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
     * Auto generated method that gets the related <code>InventoryEventPlanned</code> by the relation named <code>InventoryEventPlanned</code>.
     * @return the list of <code>InventoryEventPlanned</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends InventoryEventPlanned> getInventoryEventPlanneds() throws RepositoryException {
        if (this.inventoryEventPlanneds == null) {
            this.inventoryEventPlanneds = getRelated(InventoryEventPlanned.class, "InventoryEventPlanned");
        }
        return this.inventoryEventPlanneds;
    }
    /**
     * Auto generated method that gets the related <code>MrpInventoryEvent</code> by the relation named <code>MrpInventoryEvent</code>.
     * @return the list of <code>MrpInventoryEvent</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends MrpInventoryEvent> getMrpInventoryEvents() throws RepositoryException {
        if (this.mrpInventoryEvents == null) {
            this.mrpInventoryEvents = getRelated(MrpInventoryEvent.class, "MrpInventoryEvent");
        }
        return this.mrpInventoryEvents;
    }

    /**
     * Auto generated value setter.
     * @param inventoryEventPlanneds the inventoryEventPlanneds to set
    */
    public void setInventoryEventPlanneds(List<InventoryEventPlanned> inventoryEventPlanneds) {
        this.inventoryEventPlanneds = inventoryEventPlanneds;
    }
    /**
     * Auto generated value setter.
     * @param mrpInventoryEvents the mrpInventoryEvents to set
    */
    public void setMrpInventoryEvents(List<MrpInventoryEvent> mrpInventoryEvents) {
        this.mrpInventoryEvents = mrpInventoryEvents;
    }

    /**
     * Auto generated method that add item to collection.
     */
    public void addInventoryEventPlanned(InventoryEventPlanned inventoryEventPlanned) {
        if (this.inventoryEventPlanneds == null) {
            this.inventoryEventPlanneds = new ArrayList<InventoryEventPlanned>();
        }
        this.inventoryEventPlanneds.add(inventoryEventPlanned);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeInventoryEventPlanned(InventoryEventPlanned inventoryEventPlanned) {
        if (this.inventoryEventPlanneds == null) {
            return;
        }
        this.inventoryEventPlanneds.remove(inventoryEventPlanned);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearInventoryEventPlanned() {
        if (this.inventoryEventPlanneds == null) {
            return;
        }
        this.inventoryEventPlanneds.clear();
    }
    /**
     * Auto generated method that add item to collection.
     */
    public void addMrpInventoryEvent(MrpInventoryEvent mrpInventoryEvent) {
        if (this.mrpInventoryEvents == null) {
            this.mrpInventoryEvents = new ArrayList<MrpInventoryEvent>();
        }
        this.mrpInventoryEvents.add(mrpInventoryEvent);
    }
    /**
     * Auto generated method that remove item from collection.
     */
    public void removeMrpInventoryEvent(MrpInventoryEvent mrpInventoryEvent) {
        if (this.mrpInventoryEvents == null) {
            return;
        }
        this.mrpInventoryEvents.remove(mrpInventoryEvent);
    }
    /**
     * Auto generated method that clear items from collection.
     */
    public void clearMrpInventoryEvent() {
        if (this.mrpInventoryEvents == null) {
            return;
        }
        this.mrpInventoryEvents.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setInventoryEventPlanTypeId((String) mapValue.get("inventoryEventPlanTypeId"));
        setDescription((String) mapValue.get("description"));
        setInOut((String) mapValue.get("inOut"));
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
        mapValue.put("inventoryEventPlanTypeId", getInventoryEventPlanTypeId());
        mapValue.put("description", getDescription());
        mapValue.put("inOut", getInOut());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}