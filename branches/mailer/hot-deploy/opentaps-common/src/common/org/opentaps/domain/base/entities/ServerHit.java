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
 * Auto generated base entity ServerHit.
 */
@javax.persistence.Entity
@Table(name="SERVER_HIT")
@IdClass(ServerHitPk.class)
public class ServerHit extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("visitId", "VISIT_ID");
        fields.put("contentId", "CONTENT_ID");
        fields.put("hitStartDateTime", "HIT_START_DATE_TIME");
        fields.put("hitTypeId", "HIT_TYPE_ID");
        fields.put("numOfBytes", "NUM_OF_BYTES");
        fields.put("runningTimeMillis", "RUNNING_TIME_MILLIS");
        fields.put("userLoginId", "USER_LOGIN_ID");
        fields.put("statusId", "STATUS_ID");
        fields.put("requestUrl", "REQUEST_URL");
        fields.put("referrerUrl", "REFERRER_URL");
        fields.put("serverIpAddress", "SERVER_IP_ADDRESS");
        fields.put("serverHostName", "SERVER_HOST_NAME");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
        fields.put("internalContentId", "INTERNAL_CONTENT_ID");
        fields.put("partyId", "PARTY_ID");
        fields.put("idByIpContactMechId", "ID_BY_IP_CONTACT_MECH_ID");
        fields.put("refByWebContactMechId", "REF_BY_WEB_CONTACT_MECH_ID");
fieldMapColumns.put("ServerHit", fields);
}
  public static enum Fields implements EntityFieldInterface<ServerHit> {
    visitId("visitId"),
    contentId("contentId"),
    hitStartDateTime("hitStartDateTime"),
    hitTypeId("hitTypeId"),
    numOfBytes("numOfBytes"),
    runningTimeMillis("runningTimeMillis"),
    userLoginId("userLoginId"),
    statusId("statusId"),
    requestUrl("requestUrl"),
    referrerUrl("referrerUrl"),
    serverIpAddress("serverIpAddress"),
    serverHostName("serverHostName"),
    lastUpdatedStamp("lastUpdatedStamp"),
    lastUpdatedTxStamp("lastUpdatedTxStamp"),
    createdStamp("createdStamp"),
    createdTxStamp("createdTxStamp"),
    internalContentId("internalContentId"),
    partyId("partyId"),
    idByIpContactMechId("idByIpContactMechId"),
    refByWebContactMechId("refByWebContactMechId");
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
    
    @Column(name="VISIT_ID")
    private String visitId;
    @Id
    
    @Column(name="CONTENT_ID")
    private String contentId;
    @Id
    
    @Column(name="HIT_START_DATE_TIME")
    private Timestamp hitStartDateTime;
    @Id
    
    @Column(name="HIT_TYPE_ID")
    private String hitTypeId;
    
    @Column(name="NUM_OF_BYTES")
    private Long numOfBytes;
    
    @Column(name="RUNNING_TIME_MILLIS")
    private Long runningTimeMillis;
    
    @Column(name="USER_LOGIN_ID")
    private String userLoginId;
    
    @Column(name="STATUS_ID")
    private String statusId;
    
    @Column(name="REQUEST_URL")
    private String requestUrl;
    
    @Column(name="REFERRER_URL")
    private String referrerUrl;
    
    @Column(name="SERVER_IP_ADDRESS")
    private String serverIpAddress;
    
    @Column(name="SERVER_HOST_NAME")
    private String serverHostName;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    
    @Column(name="INTERNAL_CONTENT_ID")
    private String internalContentId;
    
    @Column(name="PARTY_ID")
    private String partyId;
    
    @Column(name="ID_BY_IP_CONTACT_MECH_ID")
    private String idByIpContactMechId;
    
    @Column(name="REF_BY_WEB_CONTACT_MECH_ID")
    private String refByWebContactMechId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="HIT_TYPE_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ServerHitType serverHitType = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="VISIT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Visit visit = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="STATUS_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private StatusItem statusItem = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="USER_LOGIN_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private UserLogin userLogin = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PARTY_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Party party = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="ID_BY_IP_CONTACT_MECH_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ContactMech idByIpContactMech = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="REF_BY_WEB_CONTACT_MECH_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ContactMech refByWebContactMech = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="INTERNAL_CONTENT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Content content = null;

  /**
   * Default constructor.
   */
  public ServerHit() {
      super();
      this.baseEntityName = "ServerHit";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("visitId");this.primaryKeyNames.add("contentId");this.primaryKeyNames.add("hitStartDateTime");this.primaryKeyNames.add("hitTypeId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ServerHit(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param visitId the visitId to set
     */
    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
    /**
     * Auto generated value setter.
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    /**
     * Auto generated value setter.
     * @param hitStartDateTime the hitStartDateTime to set
     */
    public void setHitStartDateTime(Timestamp hitStartDateTime) {
        this.hitStartDateTime = hitStartDateTime;
    }
    /**
     * Auto generated value setter.
     * @param hitTypeId the hitTypeId to set
     */
    public void setHitTypeId(String hitTypeId) {
        this.hitTypeId = hitTypeId;
    }
    /**
     * Auto generated value setter.
     * @param numOfBytes the numOfBytes to set
     */
    public void setNumOfBytes(Long numOfBytes) {
        this.numOfBytes = numOfBytes;
    }
    /**
     * Auto generated value setter.
     * @param runningTimeMillis the runningTimeMillis to set
     */
    public void setRunningTimeMillis(Long runningTimeMillis) {
        this.runningTimeMillis = runningTimeMillis;
    }
    /**
     * Auto generated value setter.
     * @param userLoginId the userLoginId to set
     */
    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }
    /**
     * Auto generated value setter.
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
    /**
     * Auto generated value setter.
     * @param requestUrl the requestUrl to set
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    /**
     * Auto generated value setter.
     * @param referrerUrl the referrerUrl to set
     */
    public void setReferrerUrl(String referrerUrl) {
        this.referrerUrl = referrerUrl;
    }
    /**
     * Auto generated value setter.
     * @param serverIpAddress the serverIpAddress to set
     */
    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }
    /**
     * Auto generated value setter.
     * @param serverHostName the serverHostName to set
     */
    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
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
     * Auto generated value setter.
     * @param internalContentId the internalContentId to set
     */
    public void setInternalContentId(String internalContentId) {
        this.internalContentId = internalContentId;
    }
    /**
     * Auto generated value setter.
     * @param partyId the partyId to set
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    /**
     * Auto generated value setter.
     * @param idByIpContactMechId the idByIpContactMechId to set
     */
    public void setIdByIpContactMechId(String idByIpContactMechId) {
        this.idByIpContactMechId = idByIpContactMechId;
    }
    /**
     * Auto generated value setter.
     * @param refByWebContactMechId the refByWebContactMechId to set
     */
    public void setRefByWebContactMechId(String refByWebContactMechId) {
        this.refByWebContactMechId = refByWebContactMechId;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getVisitId() {
        return this.visitId;
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
     * @return <code>Timestamp</code>
     */
    public Timestamp getHitStartDateTime() {
        return this.hitStartDateTime;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getHitTypeId() {
        return this.hitTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getNumOfBytes() {
        return this.numOfBytes;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Long</code>
     */
    public Long getRunningTimeMillis() {
        return this.runningTimeMillis;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getUserLoginId() {
        return this.userLoginId;
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
    public String getRequestUrl() {
        return this.requestUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getReferrerUrl() {
        return this.referrerUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getServerIpAddress() {
        return this.serverIpAddress;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getServerHostName() {
        return this.serverHostName;
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
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getInternalContentId() {
        return this.internalContentId;
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
    public String getIdByIpContactMechId() {
        return this.idByIpContactMechId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getRefByWebContactMechId() {
        return this.refByWebContactMechId;
    }

    /**
     * Auto generated method that gets the related <code>ServerHitType</code> by the relation named <code>ServerHitType</code>.
     * @return the <code>ServerHitType</code>
     * @throws RepositoryException if an error occurs
     */
    public ServerHitType getServerHitType() throws RepositoryException {
        if (this.serverHitType == null) {
            this.serverHitType = getRelatedOne(ServerHitType.class, "ServerHitType");
        }
        return this.serverHitType;
    }
    /**
     * Auto generated method that gets the related <code>Visit</code> by the relation named <code>Visit</code>.
     * @return the <code>Visit</code>
     * @throws RepositoryException if an error occurs
     */
    public Visit getVisit() throws RepositoryException {
        if (this.visit == null) {
            this.visit = getRelatedOne(Visit.class, "Visit");
        }
        return this.visit;
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
     * Auto generated method that gets the related <code>UserLogin</code> by the relation named <code>UserLogin</code>.
     * @return the <code>UserLogin</code>
     * @throws RepositoryException if an error occurs
     */
    public UserLogin getUserLogin() throws RepositoryException {
        if (this.userLogin == null) {
            this.userLogin = getRelatedOne(UserLogin.class, "UserLogin");
        }
        return this.userLogin;
    }
    /**
     * Auto generated method that gets the related <code>Party</code> by the relation named <code>Party</code>.
     * @return the <code>Party</code>
     * @throws RepositoryException if an error occurs
     */
    public Party getParty() throws RepositoryException {
        if (this.party == null) {
            this.party = getRelatedOne(Party.class, "Party");
        }
        return this.party;
    }
    /**
     * Auto generated method that gets the related <code>ContactMech</code> by the relation named <code>IdByIpContactMech</code>.
     * @return the <code>ContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public ContactMech getIdByIpContactMech() throws RepositoryException {
        if (this.idByIpContactMech == null) {
            this.idByIpContactMech = getRelatedOne(ContactMech.class, "IdByIpContactMech");
        }
        return this.idByIpContactMech;
    }
    /**
     * Auto generated method that gets the related <code>ContactMech</code> by the relation named <code>RefByWebContactMech</code>.
     * @return the <code>ContactMech</code>
     * @throws RepositoryException if an error occurs
     */
    public ContactMech getRefByWebContactMech() throws RepositoryException {
        if (this.refByWebContactMech == null) {
            this.refByWebContactMech = getRelatedOne(ContactMech.class, "RefByWebContactMech");
        }
        return this.refByWebContactMech;
    }
    /**
     * Auto generated method that gets the related <code>Content</code> by the relation named <code>Content</code>.
     * @return the <code>Content</code>
     * @throws RepositoryException if an error occurs
     */
    public Content getContent() throws RepositoryException {
        if (this.content == null) {
            this.content = getRelatedOne(Content.class, "Content");
        }
        return this.content;
    }

    /**
     * Auto generated value setter.
     * @param serverHitType the serverHitType to set
    */
    public void setServerHitType(ServerHitType serverHitType) {
        this.serverHitType = serverHitType;
    }
    /**
     * Auto generated value setter.
     * @param visit the visit to set
    */
    public void setVisit(Visit visit) {
        this.visit = visit;
    }
    /**
     * Auto generated value setter.
     * @param statusItem the statusItem to set
    */
    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }
    /**
     * Auto generated value setter.
     * @param userLogin the userLogin to set
    */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }
    /**
     * Auto generated value setter.
     * @param party the party to set
    */
    public void setParty(Party party) {
        this.party = party;
    }
    /**
     * Auto generated value setter.
     * @param idByIpContactMech the idByIpContactMech to set
    */
    public void setIdByIpContactMech(ContactMech idByIpContactMech) {
        this.idByIpContactMech = idByIpContactMech;
    }
    /**
     * Auto generated value setter.
     * @param refByWebContactMech the refByWebContactMech to set
    */
    public void setRefByWebContactMech(ContactMech refByWebContactMech) {
        this.refByWebContactMech = refByWebContactMech;
    }
    /**
     * Auto generated value setter.
     * @param content the content to set
    */
    public void setContent(Content content) {
        this.content = content;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setVisitId((String) mapValue.get("visitId"));
        setContentId((String) mapValue.get("contentId"));
        setHitStartDateTime((Timestamp) mapValue.get("hitStartDateTime"));
        setHitTypeId((String) mapValue.get("hitTypeId"));
        setNumOfBytes((Long) mapValue.get("numOfBytes"));
        setRunningTimeMillis((Long) mapValue.get("runningTimeMillis"));
        setUserLoginId((String) mapValue.get("userLoginId"));
        setStatusId((String) mapValue.get("statusId"));
        setRequestUrl((String) mapValue.get("requestUrl"));
        setReferrerUrl((String) mapValue.get("referrerUrl"));
        setServerIpAddress((String) mapValue.get("serverIpAddress"));
        setServerHostName((String) mapValue.get("serverHostName"));
        setLastUpdatedStamp((Timestamp) mapValue.get("lastUpdatedStamp"));
        setLastUpdatedTxStamp((Timestamp) mapValue.get("lastUpdatedTxStamp"));
        setCreatedStamp((Timestamp) mapValue.get("createdStamp"));
        setCreatedTxStamp((Timestamp) mapValue.get("createdTxStamp"));
        setInternalContentId((String) mapValue.get("internalContentId"));
        setPartyId((String) mapValue.get("partyId"));
        setIdByIpContactMechId((String) mapValue.get("idByIpContactMechId"));
        setRefByWebContactMechId((String) mapValue.get("refByWebContactMechId"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("visitId", getVisitId());
        mapValue.put("contentId", getContentId());
        mapValue.put("hitStartDateTime", getHitStartDateTime());
        mapValue.put("hitTypeId", getHitTypeId());
        mapValue.put("numOfBytes", getNumOfBytes());
        mapValue.put("runningTimeMillis", getRunningTimeMillis());
        mapValue.put("userLoginId", getUserLoginId());
        mapValue.put("statusId", getStatusId());
        mapValue.put("requestUrl", getRequestUrl());
        mapValue.put("referrerUrl", getReferrerUrl());
        mapValue.put("serverIpAddress", getServerIpAddress());
        mapValue.put("serverHostName", getServerHostName());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        mapValue.put("internalContentId", getInternalContentId());
        mapValue.put("partyId", getPartyId());
        mapValue.put("idByIpContactMechId", getIdByIpContactMechId());
        mapValue.put("refByWebContactMechId", getRefByWebContactMechId());
        return mapValue;
    }


}
