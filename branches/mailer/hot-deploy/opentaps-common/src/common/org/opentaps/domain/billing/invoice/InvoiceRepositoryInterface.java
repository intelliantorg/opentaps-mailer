/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
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
package org.opentaps.domain.billing.invoice;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.opentaps.domain.base.entities.InvoiceAdjustment;
import org.opentaps.domain.base.entities.InvoiceAndInvoiceItem;
import org.opentaps.domain.base.entities.InvoiceItemType;
import org.opentaps.domain.base.entities.PaymentAndApplication;
import org.opentaps.domain.base.entities.PostalAddress;
import org.opentaps.domain.product.Product;
import org.opentaps.foundation.entity.EntityNotFoundException;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;

/**
 * Repository for Invoices to handle interaction of Invoice-related domain with the entity engine (database) and the service engine.
 */
public interface InvoiceRepositoryInterface extends RepositoryInterface {

    /**
     * Gets a specific implementation of an invoice specification.
     * Use this method to set a new billing domain's specification.
     * @return the <code>InvoiceSpecificationInterface</code>
     */
    public InvoiceSpecificationInterface getInvoiceSpecification();

    /**
     * Finds an <code>Invoice</code> by ID from the database.
     * @param invoiceId the invoice ID
     * @return the <code>Invoice</code> found
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>Invoice</code> is found for the given ID
     */
    public Invoice getInvoiceById(String invoiceId) throws RepositoryException, EntityNotFoundException;

    /**
     * Finds the list of <code>Invoice</code> for the given collection of IDs from the database.
     * @param invoiceIds the collection of invoice IDs
     * @return the list of <code>Invoice</code> found
     * @throws RepositoryException if an error occurs
     */
    public List<Invoice> getInvoicesByIds(Collection<String> invoiceIds) throws RepositoryException;

    /**
     * Finds the finance charges already assessed for the given <code>Invoice</code>.
     * @param invoice an <code>Invoice</code>
     * @return list of <code>InvoiceAndInvoiceItem</code>
     * @throws RepositoryException if an error occurs
     */
    public List<InvoiceAndInvoiceItem> getRelatedInterestInvoiceItems(Invoice invoice) throws RepositoryException;

    /**
     * Lookup payment applications which took place before the asOfDateTime for this invoice.  The payments must
     * be considered paid (e.g., received, confirmed, sent).
     * @param invoice an <code>Invoice</code>
     * @param asOfDateTime the date before which to consider the payment applications
     * @return list of <code>PaymentAndApplication</code>
     * @throws RepositoryException if an error occurs
     */
    public List<PaymentAndApplication> getPaymentsApplied(Invoice invoice, Timestamp asOfDateTime) throws RepositoryException;


    /**
     * Lookup pending payment applications which took place before the asOfDateTime for this invoice.  The payments must
     * be considered pending
     * @param invoice an <code>Invoice</code>
     * @param asOfDateTime the date before which to consider the payment applications
     * @return list of <code>PaymentAndApplication</code>
     * @throws RepositoryException if an error occurs
     */
    public List<PaymentAndApplication> getPendingPaymentsApplied(Invoice invoice, Timestamp asOfDateTime) throws RepositoryException;

    /**
     * Finds an <code>InvoiceAdjustment</code> by ID from the database.
     * @param invoiceAdjustmentId the invoice adjustment ID
     * @return the <code>InvoiceAdjustment</code> found
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>InvoiceAdjustment</code> is found for the given ID
     */
    public InvoiceAdjustment getInvoiceAdjustmentById(String invoiceAdjustmentId) throws EntityNotFoundException, RepositoryException;

    /**
     * Gets the adjustments applied to the invoice as of a certain date.
     * @param invoice an <code>Invoice</code>
     * @param asOfDateTime a <code>Timestamp</code> value
     * @return the list of <code>InvoiceAdjustment</code> as of the given date
     * @throws RepositoryException if an error occurs
     */
    public List<InvoiceAdjustment> getAdjustmentsApplied(Invoice invoice, Timestamp asOfDateTime) throws RepositoryException;

    /**
     * Gets the list of <code>InvoiceItemType</code> that are applicable for the given invoice type and organization.
     * Results are ordered by <code>defaultSequenceNum</code> and <code>invoiceItemTypeId</code>.
     * @param invoiceTypeId the invoice type to get the applicable <code>InvoiceItemType</code> for
     * @param organizationPartyId the organization to get the applicable <code>InvoiceItemType</code> for
     * @return list of applicable <code>InvoiceItemType</code>
     * @throws RepositoryException if an error occurs
     */
    public List<InvoiceItemType> getApplicableInvoiceItemTypes(String invoiceTypeId, String organizationPartyId) throws RepositoryException;

    /**
     * Gets the list of <code>InvoiceItemType</code> that are applicable for the given <code>Invoice</code>.
     * Results are ordered by <code>defaultSequenceNum</code> and <code>invoiceItemTypeId</code>.
     * @param invoice the <code>Invoice</code> to get the applicable <code>InvoiceItemType</code> for
     * @return list of applicable <code>InvoiceItemType</code>
     * @throws RepositoryException if an error occurs
     */
    public List<InvoiceItemType> getApplicableInvoiceItemTypes(Invoice invoice) throws RepositoryException;

    /**
     * Gets the shipping address of the invoice, or <code>null</code> if it cannot find one.  In the case of opentaps 1.0,
     * this will look up InvoiceContactMech for purpose SHIPPING_LOCATION.  Ensure that all processes
     * that create invoices populates this table when applicable.
     *
     * @param invoice an <code>Invoice</code>
     * @return the shipping <code>PostalAddress</code>, or <code>null</code> if none was found
     * @throws RepositoryException if an error occurs
     */
    public PostalAddress getShippingAddress(Invoice invoice) throws RepositoryException;

    /**
     * Gets the billing address of the invoice. This is either the BILLING_LOCATION from InvoiceContactMech,
     * billing party BILLING_LOCATION or billing party GENERAL_LOCATION, whichever is found first.
     *
     * @param invoice an <code>Invoice</code>
     * @return the billing <code>PostalAddress</code>, or <code>null</code> if none was found
     * @throws RepositoryException if an error occurs
     */
    public PostalAddress getBillingAddress(Invoice invoice) throws RepositoryException;

    /**
     * Sets the billing address of the invoice to the given <code>PostalAddress</code>.
     *
     * @param invoice an <code>Invoice</code>
     * @param billingAddress a <code>PostalAddress</code>
     * @throws RepositoryException if an error occurs
     */
    public void setBillingAddress(Invoice invoice, PostalAddress billingAddress) throws RepositoryException;

    /**
     * Sets the invoice as PAID.
     * This method does not do any checking.  If you want to check whether the invoice should be set to PAID,
     * do it in the service calling this method.
     * @param invoice an <code>Invoice</code>
     * @throws RepositoryException if an error occurs
     */
    public void setPaid(Invoice invoice) throws RepositoryException;
    
    /**
     * get id of InvoiceItemType by the given <code>Product</code>.
     *
     * @param invoice an <code>Invoice</code> value
     * @param product an <code>Product</code> value
     * @throws RepositoryException if an error occurs
     */
    public String getInvoiceItemTypeIdForProduct(Invoice invoice, Product product) throws RepositoryException;

}
