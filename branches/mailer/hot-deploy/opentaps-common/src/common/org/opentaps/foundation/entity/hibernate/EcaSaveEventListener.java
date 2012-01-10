/*
 * Copyright (c) 2007 - 2009 Open Source Strategies, Inc.
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
package org.opentaps.foundation.entity.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.opentaps.foundation.entity.Entity;
/**
 * This is an implementation of the DefaultSaveEventListener for support eca with hibernate.
 *
 */
public class EcaSaveEventListener extends DefaultSaveEventListener {
    private static final String MODULE = EcaSaveOrUpdateEventListener.class
            .getName();
    private GenericDelegator delegator;

    /**
     * EcaSaveEventListener constructor.
     * 
     * @param delegator
     *            <code>GenericDelegator</code> object.
     */
    public EcaSaveEventListener(GenericDelegator delegator) {
        this.delegator = delegator;
    }

    /**
     * Handle the given update event.
     * 
     * @param event
     *            The update event to be handled.
     */
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
        Entity entity = (Entity) event.getObject();
        try {
            // run eecas of before save event
            EcaCommonEvent.beforeSave(entity, delegator);
            // call super method to persist object
            super.onSaveOrUpdate(event);
            // run eecas of after save event
            EcaCommonEvent.afterSave(entity, delegator);
            Debug.logInfo("Execute save/update operation for entity ["
                    + entity.getBaseEntityName() + "]" + " sucessed.", MODULE);
        } catch (GenericEntityException e) {
            String errMsg = "Failure in save/update operation for entity ["
                    + entity.getBaseEntityName() + "]: " + e.toString() + ".";
            Debug.logError(e, errMsg, MODULE);
            throw new HibernateException(e.getMessage());
        }
    }

}
