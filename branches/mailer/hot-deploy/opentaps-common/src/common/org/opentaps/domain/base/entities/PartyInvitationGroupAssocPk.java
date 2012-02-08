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

import java.io.Serializable;
import javax.persistence.*;

import java.lang.String;

@Embeddable
public class PartyInvitationGroupAssocPk implements Serializable {
    @Column(name="PARTY_INVITATION_ID")
    private String partyInvitationId;
    @Column(name="PARTY_ID_TO")
    private String partyIdTo;
    
    /**
     * Auto generated value setter.
     * @param partyInvitationId the partyInvitationId to set
     */
    public void setPartyInvitationId(String partyInvitationId) {
        this.partyInvitationId = partyInvitationId;
    }
    /**
     * Auto generated value setter.
     * @param partyIdTo the partyIdTo to set
     */
    public void setPartyIdTo(String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getPartyInvitationId() {
        return this.partyInvitationId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */  
    public String getPartyIdTo() {
        return this.partyIdTo;
    }
}