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
package org.opentaps.domain.shipping;

import org.opentaps.foundation.domain.DomainInterface;
import org.opentaps.foundation.repository.RepositoryException;

/**
 * This is the interface of the Shipping domain.
 */
public interface ShippingDomainInterface extends DomainInterface {

    /**
     * Returns the shipping repository instance.
     * @return a <code>ShippingRepositoryInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public ShippingRepositoryInterface getShippingRepository() throws RepositoryException;

}
