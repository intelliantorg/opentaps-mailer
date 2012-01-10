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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity PartyRelationshipAndSalesOpportunity.
 */
@javax.persistence.Entity
@NamedNativeQuery(name="selectPartyRelationshipAndSalesOpportunitys", query="SELECT PR.PARTY_ID_FROM AS \"partyIdFrom\",PR.ROLE_TYPE_ID_FROM AS \"roleTypeIdFrom\",PR.PARTY_ID_TO AS \"partyIdTo\",PR.PARTY_RELATIONSHIP_TYPE_ID AS \"partyRelationshipTypeId\",PR.FROM_DATE AS \"fromDate\",PR.THRU_DATE AS \"thruDate\",SO.SALES_OPPORTUNITY_ID AS \"salesOpportunityId\",SO.OPPORTUNITY_NAME AS \"opportunityName\",SO.DESCRIPTION AS \"description\",SO.NEXT_STEP AS \"nextStep\",SO.ESTIMATED_AMOUNT AS \"estimatedAmount\",SO.ESTIMATED_PROBABILITY AS \"estimatedProbability\",SO.CURRENCY_UOM_ID AS \"currencyUomId\",SO.MARKETING_CAMPAIGN_ID AS \"marketingCampaignId\",SO.DATA_SOURCE_ID AS \"dataSourceId\",SO.ESTIMATED_CLOSE_DATE AS \"estimatedCloseDate\",SO.OPPORTUNITY_STAGE_ID AS \"opportunityStageId\",SO.TYPE_ENUM_ID AS \"typeEnumId\",SO.CREATED_BY_USER_LOGIN AS \"createdByUserLogin\" FROM PARTY_RELATIONSHIP PR INNER JOIN SALES_OPPORTUNITY_ROLE SOR ON PR.PARTY_ID_FROM = SOR.PARTY_ID INNER JOIN SALES_OPPORTUNITY SO ON SOR.SALES_OPPORTUNITY_ID = SO.SALES_OPPORTUNITY_ID", resultSetMapping="PartyRelationshipAndSalesOpportunityMapping")
@SqlResultSetMapping(name="PartyRelationshipAndSalesOpportunityMapping", entities={
@EntityResult(entityClass=PartyRelationshipAndSalesOpportunity.class, fields = {
@FieldResult(name="partyIdFrom", column="partyIdFrom")
,@FieldResult(name="roleTypeIdFrom", column="roleTypeIdFrom")
,@FieldResult(name="partyIdTo", column="partyIdTo")
,@FieldResult(name="partyRelationshipTypeId", column="partyRelationshipTypeId")
,@FieldResult(name="fromDate", column="fromDate")
,@FieldResult(name="thruDate", column="thruDate")
,@FieldResult(name="salesOpportunityId", column="salesOpportunityId")
,@FieldResult(name="opportunityName", column="opportunityName")
,@FieldResult(name="description", column="description")
,@FieldResult(name="nextStep", column="nextStep")
,@FieldResult(name="estimatedAmount", column="estimatedAmount")
,@FieldResult(name="estimatedProbability", column="estimatedProbability")
,@FieldResult(name="currencyUomId", column="currencyUomId")
,@FieldResult(name="marketingCampaignId", column="marketingCampaignId")
,@FieldResult(name="dataSourceId", column="dataSourceId")
,@FieldResult(name="estimatedCloseDate", column="estimatedCloseDate")
,@FieldResult(name="opportunityStageId", column="opportunityStageId")
,@FieldResult(name="typeEnumId", column="typeEnumId")
,@FieldResult(name="createdByUserLogin", column="createdByUserLogin")
})})
@org.hibernate.annotations.Entity(mutable = false)
@org.hibernate.annotations.AccessType("field")
public class PartyRelationshipAndSalesOpportunity extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("partyIdFrom", "PR.PARTY_ID_FROM");
        fields.put("roleTypeIdFrom", "PR.ROLE_TYPE_ID_FROM");
        fields.put("partyIdTo", "PR.PARTY_ID_TO");
        fields.put("partyRelationshipTypeId", "PR.PARTY_RELATIONSHIP_TYPE_ID");
        fields.put("fromDate", "PR.FROM_DATE");
        fields.put("thruDate", "PR.THRU_DATE");
        fields.put("salesOpportunityId", "SO.SALES_OPPORTUNITY_ID");
        fields.put("opportunityName", "SO.OPPORTUNITY_NAME");
        fields.put("description", "SO.DESCRIPTION");
        fields.put("nextStep", "SO.NEXT_STEP");
        fields.put("estimatedAmount", "SO.ESTIMATED_AMOUNT");
        fields.put("estimatedProbability", "SO.ESTIMATED_PROBABILITY");
        fields.put("currencyUomId", "SO.CURRENCY_UOM_ID");
        fields.put("marketingCampaignId", "SO.MARKETING_CAMPAIGN_ID");
        fields.put("dataSourceId", "SO.DATA_SOURCE_ID");
        fields.put("estimatedCloseDate", "SO.ESTIMATED_CLOSE_DATE");
        fields.put("opportunityStageId", "SO.OPPORTUNITY_STAGE_ID");
        fields.put("typeEnumId", "SO.TYPE_ENUM_ID");
        fields.put("createdByUserLogin", "SO.CREATED_BY_USER_LOGIN");
fieldMapColumns.put("PartyRelationshipAndSalesOpportunity", fields);
}
  public static enum Fields implements EntityFieldInterface<PartyRelationshipAndSalesOpportunity> {
    partyIdFrom("partyIdFrom"),
    roleTypeIdFrom("roleTypeIdFrom"),
    partyIdTo("partyIdTo"),
    partyRelationshipTypeId("partyRelationshipTypeId"),
    fromDate("fromDate"),
    thruDate("thruDate"),
    salesOpportunityId("salesOpportunityId"),
    opportunityName("opportunityName"),
    description("description"),
    nextStep("nextStep"),
    estimatedAmount("estimatedAmount"),
    estimatedProbability("estimatedProbability"),
    currencyUomId("currencyUomId"),
    marketingCampaignId("marketingCampaignId"),
    dataSourceId("dataSourceId"),
    estimatedCloseDate("estimatedCloseDate"),
    opportunityStageId("opportunityStageId"),
    typeEnumId("typeEnumId"),
    createdByUserLogin("createdByUserLogin");
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
    
    private String partyIdFrom;
    
    
    private String roleTypeIdFrom;
    
    
    private String partyIdTo;
    
    
    private String partyRelationshipTypeId;
    
    
    private Timestamp fromDate;
    
    
    private Timestamp thruDate;
    
    
    private String salesOpportunityId;
    
    
    private String opportunityName;
    
    
    private String description;
    
    
    private String nextStep;
    
    
    private BigDecimal estimatedAmount;
    
    
    private BigDecimal estimatedProbability;
    
    
    private String currencyUomId;
    
    
    private String marketingCampaignId;
    
    
    private String dataSourceId;
    
    
    private Timestamp estimatedCloseDate;
    
    
    private String opportunityStageId;
    
    
    private String typeEnumId;
    
    
    private String createdByUserLogin;
    private transient List<SalesOpportunityQuote> salesOpportunityQuotes = null;

  /**
   * Default constructor.
   */
  public PartyRelationshipAndSalesOpportunity() {
      super();
      this.baseEntityName = "PartyRelationshipAndSalesOpportunity";
      this.isView = true;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("partyIdFrom");this.primaryKeyNames.add("roleTypeIdFrom");this.primaryKeyNames.add("partyIdTo");this.primaryKeyNames.add("fromDate");this.primaryKeyNames.add("salesOpportunityId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public PartyRelationshipAndSalesOpportunity(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * This is a view-entity, so the setter methods will be private to this class and for use in its fromMap constructor only
     */
    /**
     * Auto generated value setter.
     * @param partyIdFrom the partyIdFrom to set
     */
    private void setPartyIdFrom(String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
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
     * @param partyIdTo the partyIdTo to set
     */
    private void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    /**
     * Auto generated value setter.
     * @param partyRelationshipTypeId the partyRelationshipTypeId to set
     */
    private void setPartyRelationshipTypeId(String partyRelationshipTypeId) {
        this.partyRelationshipTypeId = partyRelationshipTypeId;
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
     * @param salesOpportunityId the salesOpportunityId to set
     */
    private void setSalesOpportunityId(String salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }
    /**
     * Auto generated value setter.
     * @param opportunityName the opportunityName to set
     */
    private void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
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
     * @param nextStep the nextStep to set
     */
    private void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }
    /**
     * Auto generated value setter.
     * @param estimatedAmount the estimatedAmount to set
     */
    private void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }
    /**
     * Auto generated value setter.
     * @param estimatedProbability the estimatedProbability to set
     */
    private void setEstimatedProbability(BigDecimal estimatedProbability) {
        this.estimatedProbability = estimatedProbability;
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
     * @param marketingCampaignId the marketingCampaignId to set
     */
    private void setMarketingCampaignId(String marketingCampaignId) {
        this.marketingCampaignId = marketingCampaignId;
    }
    /**
     * Auto generated value setter.
     * @param dataSourceId the dataSourceId to set
     */
    private void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    /**
     * Auto generated value setter.
     * @param estimatedCloseDate the estimatedCloseDate to set
     */
    private void setEstimatedCloseDate(Timestamp estimatedCloseDate) {
        this.estimatedCloseDate = estimatedCloseDate;
    }
    /**
     * Auto generated value setter.
     * @param opportunityStageId the opportunityStageId to set
     */
    private void setOpportunityStageId(String opportunityStageId) {
        this.opportunityStageId = opportunityStageId;
    }
    /**
     * Auto generated value setter.
     * @param typeEnumId the typeEnumId to set
     */
    private void setTypeEnumId(String typeEnumId) {
        this.typeEnumId = typeEnumId;
    }
    /**
     * Auto generated value setter.
     * @param createdByUserLogin the createdByUserLogin to set
     */
    private void setCreatedByUserLogin(String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
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
    public String getRoleTypeIdFrom() {
        return this.roleTypeIdFrom;
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
    public String getPartyRelationshipTypeId() {
        return this.partyRelationshipTypeId;
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
    public String getSalesOpportunityId() {
        return this.salesOpportunityId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOpportunityName() {
        return this.opportunityName;
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
    public String getNextStep() {
        return this.nextStep;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getEstimatedAmount() {
        return this.estimatedAmount;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getEstimatedProbability() {
        return this.estimatedProbability;
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
    public String getMarketingCampaignId() {
        return this.marketingCampaignId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDataSourceId() {
        return this.dataSourceId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getEstimatedCloseDate() {
        return this.estimatedCloseDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getOpportunityStageId() {
        return this.opportunityStageId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTypeEnumId() {
        return this.typeEnumId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCreatedByUserLogin() {
        return this.createdByUserLogin;
    }

    /**
     * Auto generated method that gets the related <code>SalesOpportunityQuote</code> by the relation named <code>SalesOpportunityQuote</code>.
     * @return the list of <code>SalesOpportunityQuote</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends SalesOpportunityQuote> getSalesOpportunityQuotes() throws RepositoryException {
        if (this.salesOpportunityQuotes == null) {
            this.salesOpportunityQuotes = getRelated(SalesOpportunityQuote.class, "SalesOpportunityQuote");
        }
        return this.salesOpportunityQuotes;
    }

    /**
     * Auto generated value setter.
     * @param salesOpportunityQuotes the salesOpportunityQuotes to set
    */
    public void setSalesOpportunityQuotes(List<SalesOpportunityQuote> salesOpportunityQuotes) {
        this.salesOpportunityQuotes = salesOpportunityQuotes;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setPartyIdFrom((String) mapValue.get("partyIdFrom"));
        setRoleTypeIdFrom((String) mapValue.get("roleTypeIdFrom"));
        setPartyIdTo((String) mapValue.get("partyIdTo"));
        setPartyRelationshipTypeId((String) mapValue.get("partyRelationshipTypeId"));
        setFromDate((Timestamp) mapValue.get("fromDate"));
        setThruDate((Timestamp) mapValue.get("thruDate"));
        setSalesOpportunityId((String) mapValue.get("salesOpportunityId"));
        setOpportunityName((String) mapValue.get("opportunityName"));
        setDescription((String) mapValue.get("description"));
        setNextStep((String) mapValue.get("nextStep"));
        setEstimatedAmount(convertToBigDecimal(mapValue.get("estimatedAmount")));
        setEstimatedProbability(convertToBigDecimal(mapValue.get("estimatedProbability")));
        setCurrencyUomId((String) mapValue.get("currencyUomId"));
        setMarketingCampaignId((String) mapValue.get("marketingCampaignId"));
        setDataSourceId((String) mapValue.get("dataSourceId"));
        setEstimatedCloseDate((Timestamp) mapValue.get("estimatedCloseDate"));
        setOpportunityStageId((String) mapValue.get("opportunityStageId"));
        setTypeEnumId((String) mapValue.get("typeEnumId"));
        setCreatedByUserLogin((String) mapValue.get("createdByUserLogin"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("partyIdFrom", getPartyIdFrom());
        mapValue.put("roleTypeIdFrom", getRoleTypeIdFrom());
        mapValue.put("partyIdTo", getPartyIdTo());
        mapValue.put("partyRelationshipTypeId", getPartyRelationshipTypeId());
        mapValue.put("fromDate", getFromDate());
        mapValue.put("thruDate", getThruDate());
        mapValue.put("salesOpportunityId", getSalesOpportunityId());
        mapValue.put("opportunityName", getOpportunityName());
        mapValue.put("description", getDescription());
        mapValue.put("nextStep", getNextStep());
        mapValue.put("estimatedAmount", getEstimatedAmount());
        mapValue.put("estimatedProbability", getEstimatedProbability());
        mapValue.put("currencyUomId", getCurrencyUomId());
        mapValue.put("marketingCampaignId", getMarketingCampaignId());
        mapValue.put("dataSourceId", getDataSourceId());
        mapValue.put("estimatedCloseDate", getEstimatedCloseDate());
        mapValue.put("opportunityStageId", getOpportunityStageId());
        mapValue.put("typeEnumId", getTypeEnumId());
        mapValue.put("createdByUserLogin", getCreatedByUserLogin());
        return mapValue;
    }


}
