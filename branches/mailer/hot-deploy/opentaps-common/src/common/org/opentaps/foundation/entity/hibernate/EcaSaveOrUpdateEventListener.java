package org.opentaps.foundation.entity.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.opentaps.foundation.entity.Entity;

/**
 * This is an implementation of the DefaultSaveOrUpdateEventListener for support eca with hibernate.
 *
 */
public class EcaSaveOrUpdateEventListener extends
        DefaultSaveOrUpdateEventListener {
    private static final String MODULE = EcaSaveOrUpdateEventListener.class
            .getName();
    private GenericDelegator delegator;

    /**
     * EcaSaveOrUpdateEventListener constructor.
     *
     * @param delegator
     *            <code>GenericDelegator</code> object.
     */
    public EcaSaveOrUpdateEventListener(GenericDelegator delegator) {
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
            Debug.logInfo("Execute save/update operation for entity [" + entity.getBaseEntityName() + "]" + " sucessed.", MODULE);
        } catch (GenericEntityException e) {
            String errMsg = "Failure in save/update operation for entity ["
                    + entity.getBaseEntityName() + "]: " + e.toString() + ".";
            Debug.logError(e, errMsg, MODULE);
            throw new HibernateException(e.getMessage());
        }
    }

}
