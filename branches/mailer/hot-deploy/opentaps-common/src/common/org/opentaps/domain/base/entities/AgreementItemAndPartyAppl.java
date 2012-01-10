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
 * Auto generated base entity AgreementItemAndPartyAppl.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectAgreementItemAndPartyAppls", query="SELECT AGI.AGREEMENT_ID AS \"agreementId\",AGI.AGREEMENT_ITEM_SEQ_ID AS \"agreementItemSeqId\",AGI.AGREEMENT_ITEM_TYPE_ID AS \"agreementItemTypeId\",AGI.CURRENCY_UOM_ID AS \"currencyUomId\",AGI.AGREEMENT_TEXT AS \"agreementText\",AGI.AGREEMENT_IMAGE AS \"agreementImage\",AGPA.PARTY_ID AS \"partyId\",AG.PARTY_ID_FROM AS \"partyIdFrom\",AG.PARTY_ID_TO AS \"partyIdTo\",AG.ROLE_TYPE_ID_FROM AS \"roleTypeIdFrom\",AG.ROLE_TYPE_ID_TO AS \"roleTypeIdTo\",AG.AGREEMENT_TYPE_ID AS \"agreementTypeId\",AG.AGREEMENT_DATE AS \"agreementDate\",AG.FROM_DATE AS \"fromDate\",AG.THRU_DATE AS \"thruDate\",AG.DESCRIPTION AS \"description\",AG.TEXT_DATA AS \"textData\",AG.STATUS_ID AS \"statusId\",AG.DEFAULT_CURRENCY_UOM_ID AS \"defaultCurrencyUomId\",AG.FROM_PARTY_CLASS_GROUP_ID AS \"fromPartyClassGroupId\",AG.TO_PARTY_CLASS_GROUP_ID AS \"toPartyClassGroupId\" FROM AGREEMENT_ITEM AGI INNER JOIN AGREEMENT_PARTY_APPLIC AGPA ON AGI.AGREEMENT_ID = AGPA.AGREEMENT_ID AND AGI.AGREEMENT_ITEM_SEQ_ID = AGPA.AGREEMENT_ITEM_SEQ_ID INNER JOIN AGREEMENT AG ON AGI.AGREEMENT_ID = AG.AGREEMENT_ID", resultSetMapping="AgreementItemAndPartyApplMapping")
@SqlResultSetMapping(name="AgreementItemAndPartyApplMapping", entities={
@EntityResult(entityClass=AgreementItemAndPartyAppl.class, fields = {
@FieldResult(name="agreementId", column="agreementId")
,@FieldResult(name="agreementItemSeqId", column="agreementItemSeqId")
,@FieldResult(name="agreementItemTypeId", column="agreementItemTypeId")
,@FieldResult(name="currencyUomId", column="currencyUomId")
,@FieldResult(name="agreementText", column="agreementText")
,@FieldResult(name="agreementImage", column="agreementImage")
,@FieldResult(name="partyId", column="partyId")
,@FieldResult(name="partyIdFrom", column="partyIdFrom")
,@FieldResult(name="partyIdTo", column="partyIdTo")
,@FieldResult(name="roleTypeIdFrom", column="roleTypeIdFrom")
,@FieldResult(name="roleTypeIdTo", column="roleTypeIdTo")
,@FieldResult(name="agreementTypeId", column="agreementTypeId")
,@FieldResult(name="agreementDate", column="agreementDate")
,@FieldResult(name="fromDate", column="fromDate")
,@FieldResult(name="thruDate", column="thruDate")
,@FieldResult(name="description", column="description")
,@FieldResult(name="textData", column="textData")
,@FieldResult(name="statusId", column="statusId")
,@FieldResult(name="defaultCurrencyUomId", column="defaultCurrencyUomId")
,@FieldResult(name="fromPartyClassGroupId", column="fromPartyClassGroupId")
,@FieldResult(name="toPartyClassGroupId", column="toPartyClassGroupId")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class AgreementItemAndPartyAppl extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("agreementId", "AGI.AGREEMENT_ID");
        fields.put("agreementItemSeqId", "AGI.AGREEMENT_ITEM_SEQ_ID");
        fields.put("agreementItemTypeId", "AGI.AGREEMENT_ITEM_TYPE_ID");
        fields.put("currencyUomId", "AGI.CURRENCY_UOM_ID");
        fields.put("agreementText", "AGI.AGREEMENT_TEXT");
        fields.put("agreementImage", "AGI.AGREEMENT_IMAGE");
        fields.put("partyId", "AGPA.PARTY_ID");
        fields.put("partyIdFrom", "AG.PARTY_ID_FROM");
        fields.put("partyIdTo", "AG.PARTY_ID_TO");
        fields.put("roleTypeIdFrom", "AG.ROLE_TYPE_ID_FROM");
        fields.put("roleTypeIdTo", "AG.ROLE_TYPE_ID_TO");
        fields.put("agreementTypeId", "AG.AGREEMENT_TYPE_ID");
        fields.put("agreementDate", "AG.AGREEMENT_DATE");
        fields.put("fromDate", "AG.FROM_DATE");
        fields.put("thruDate", "AG.THRU_DATE");
        fields.put("description", "AG.DESCRIPTION");
        fields.put("textData", "AG.TEXT_DATA");
        fields.put("statusId", "AG.STATUS_ID");
        fields.put("defaultCurrencyUomId", "AG.DEFAULT_CURRENCY_UOM_ID");
        fields.put("fromPartyClassGroupId", "AG.FROM_PARTY_CLASS_GROUP_ID");
        fields.put("toPartyClassGroupId", "AG.TO_PARTY_CLASS_GROUP_ID");
fieldMapColumns.put("AgreementItemAndPartyAppl", fields);
}
  public static enum Fields implements EntityFieldInterface<AgreementItemAndPartyAppl> {
    agreementId("agreementId"),
    agreementItemSeqId("agreementItemSeqId"),
    agreementItemTypeId("agreementItemTypeId"),
    currencyUomId("currencyUomId"),
    agreementText("agreementText"),
    agreementImage("agreementImage"),
    partyId("partyId"),
    partyIdFrom("partyIdFrom"),
    partyIdTo("partyIdTo"),
    roleTypeIdFrom("roleTypeIdFrom"),
    roleTypeIdTo("roleTypeIdTo"),
    agreementTypeId("agreementTypeId"),
    agreementDate("agreementDate"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    description("description"),
    textData("textData"),
    statusId("statusId"),
    defaultCurrencyUomId("defaultCurrencyUomId"),
    fromPartyClassGroupId("fromPartyClassGroupId"),
    toPartyClassGroupId("toPartyClassGroupId");
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
    
    private String agreementId;
    
    
    private String agreementItemSeqId;
    
    
    private String agreementItemTypeId;
    
    
    private String currencyUomId;
    
    
    private String agreementText;
    
    
    private byte[] agreementImage;
    
    
    private String partyId;
    
    
    private String partyIdFrom;
    
    
    private String partyIdTo;
    
    
    private String roleTypeIdFrom;
    
    
    private String roleTypeIdTo;
    
    
    private String agreementTypeId;
    
    
    private Timestamp agreementDate;
    
    
    private Timestamp fromDate;
    
    
    private Timestamp thruDate;
    
    
    private String description;
    
    
    private String textData;
    
    
    private String statusId;
    
    
    private String defaultCurrencyUomId;
    
    
    private String fromPartyClassGroupId;
    
    
    private String toPartyClassGroupId;

  /**
   * Default constructor.
   */
  public AgreementItemAndPartyAppl() {
      super();
      this.baseEntityName = "AgreementItemAndPartyAppl";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("agreementId");this.primaryKeyNames.add("agreementItemSeqId");this.primaryKeyNames.add("partyId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public AgreementItemAndPartyAppl(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param agreementId the agreementId to set
     */
    private void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }
    /**
     * Auto generated value setter.
     * @param agreementItemSeqId the agreementItemSeqId to set
     */
    private void setAgreementItemSeqId(String agreementItemSeqId) {
        this.agreementItemSeqId = agreementItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param agreementItemTypeId the agreementItemTypeId to set
     */
    private void setAgreementItemTypeId(String agreementItemTypeId) {
        this.agreementItemTypeId = agreementItemTypeId;
    }
    /**
     * Auto generated value setter.
     * @param currencyUomId the currencyUomId to set
     */
    private void setCurrencyUomId(String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    /**
     * Auto generated value setter.
     * @param agreementText the agreementText to set
     */
    private void setAgreementText(String agreementText) {
        this.agreementText = agreementText;
    }
    /**
     * Auto generated value setter.
     * @param agreementImage the agreementImage to set
     */
    private void setAgreementImage(byte[] agreementImage) {
        this.agreementImage = agreementImage;
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
     * @param partyIdFrom the partyIdFrom to set
     */
    private void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    /**
     * Auto generated value setter.
     * @param partyIdTo the partyIdTo to set
     */
    private void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    /**
     * Auto generated value setter.
     * @param roleTypeIdFrom the roleTypeIdFrom to set
     */
    private void setRoleTypeIdFrom(String roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }
    /**
     * Auto generated value setter.
     * @param roleTypeIdTo the roleTypeIdTo to set
     */
    private void setRoleTypeIdTo(String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }
    /**
     * Auto generated value setter.
     * @param agreementTypeId the agreementTypeId to set
     */
    private void setAgreementTypeId(String agreementTypeId) {
        this.agreementTypeId = agreementTypeId;
    }
    /**
     * Auto generated value setter.
     * @param agreementDate the agreementDate to set
     */
    private void setAgreementDate(Timestamp agreementDate) {
        this.agreementDate = agreementDate;
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
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
    }
    /**
     * Auto generated value setter.
     * @param textData the textData to set
     */
    private void setTextData(String textData) {
        this.textData = textData;
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
     * @param defaultCurrencyUomId the defaultCurrencyUomId to set
     */
    private void setDefaultCurrencyUomId(String defaultCurrencyUomId) {
        this.defaultCurrencyUomId = defaultCurrencyUomId;
    }
    /**
     * Auto generated value setter.
     * @param fromPartyClassGroupId the fromPartyClassGroupId to set
     */
    private void setFromPartyClassGroupId(String fromPartyClassGroupId) {
        this.fromPartyClassGroupId = fromPartyClassGroupId;
    }
    /**
     * Auto generated value setter.
     * @param toPartyClassGroupId the toPartyClassGroupId to set
     */
    private void setToPartyClassGroupId(String toPartyClassGroupId) {
        this.toPartyClassGroupId = toPartyClassGroupId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAgreementId() {
        return this.agreementId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAgreementItemSeqId() {
        return this.agreementItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAgreementItemTypeId() {
        return this.agreementItemTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCurrencyUomId() {
        return this.currencyUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAgreementText() {
        return this.agreementText;
    }
    /**
     * Auto generated value accessor.
     * @return <code>byte[]</code>
     */
    public byte[] getAgreementImage() {
        return this.agreementImage;
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
    public String getPartyIdFrom() {
        return this.partyIdFrom;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPartyIdTo() {
        return this.partyIdTo;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getRoleTypeIdFrom() {
        return this.roleTypeIdFrom;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getRoleTypeIdTo() {
        return this.roleTypeIdTo;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getAgreementTypeId() {
        return this.agreementTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getAgreementDate() {
        return this.agreementDate;
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
    public String getDescription() {
        return this.description;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTextData() {
        return this.textData;
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
    public String getDefaultCurrencyUomId() {
        return this.defaultCurrencyUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getFromPartyClassGroupId() {
        return this.fromPartyClassGroupId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getToPartyClassGroupId() {
        return this.toPartyClassGroupId;
    }




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setAgreementId((String) mapValue.get("agreementId"));
        setAgreementItemSeqId((String) mapValue.get("agreementItemSeqId"));
        setAgreementItemTypeId((String) mapValue.get("agreementItemTypeId"));
        setCurrencyUomId((String) mapValue.get("currencyUomId"));
        setAgreementText((String) mapValue.get("agreementText"));
        setAgreementImage((byte[]) mapValue.get("agreementImage"));
        setPartyId((String) mapValue.get("partyId"));
        setPartyIdFrom((String) mapValue.get("partyIdFrom"));
        setPartyIdTo((String) mapValue.get("partyIdTo"));
        setRoleTypeIdFrom((String) mapValue.get("roleTypeIdFrom"));
        setRoleTypeIdTo((String) mapValue.get("roleTypeIdTo"));
        setAgreementTypeId((String) mapValue.get("agreementTypeId"));
        setAgreementDate((Timestamp) mapValue.get("agreementDate"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        setDescription((String) mapValue.get("description"));
        setTextData((String) mapValue.get("textData"));
        setStatusId((String) mapValue.get("statusId"));
        setDefaultCurrencyUomId((String) mapValue.get("defaultCurrencyUomId"));
        setFromPartyClassGroupId((String) mapValue.get("fromPartyClassGroupId"));
        setToPartyClassGroupId((String) mapValue.get("toPartyClassGroupId"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("agreementId", getAgreementId());
        mapValue.put("agreementItemSeqId", getAgreementItemSeqId());
        mapValue.put("agreementItemTypeId", getAgreementItemTypeId());
        mapValue.put("currencyUomId", getCurrencyUomId());
        mapValue.put("agreementText", getAgreementText());
        mapValue.put("agreementImage", getAgreementImage());
        mapValue.put("partyId", getPartyId());
        mapValue.put("partyIdFrom", getPartyIdFrom());
        mapValue.put("partyIdTo", getPartyIdTo());
        mapValue.put("roleTypeIdFrom", getRoleTypeIdFrom());
        mapValue.put("roleTypeIdTo", getRoleTypeIdTo());
        mapValue.put("agreementTypeId", getAgreementTypeId());
        mapValue.put("agreementDate", getAgreementDate());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("description", getDescription());
        mapValue.put("textData", getTextData());
        mapValue.put("statusId", getStatusId());
        mapValue.put("defaultCurrencyUomId", getDefaultCurrencyUomId());
        mapValue.put("fromPartyClassGroupId", getFromPartyClassGroupId());
        mapValue.put("toPartyClassGroupId", getToPartyClassGroupId());
        return mapValue;
    }


}
