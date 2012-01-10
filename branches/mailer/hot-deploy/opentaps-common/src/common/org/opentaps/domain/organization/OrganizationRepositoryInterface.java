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
package org.opentaps.domain.organization;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.opentaps.domain.base.entities.CustomTimePeriod;
import org.opentaps.domain.base.entities.PaymentMethod;
import org.opentaps.domain.base.entities.TermType;
import org.opentaps.foundation.entity.EntityNotFoundException;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;

/**
 * Organization repository.
 */
public interface OrganizationRepositoryInterface extends RepositoryInterface {

    /**
     * Finds an <code>Organization</code> by ID from the database.
     * @param organizationPartyId the party ID for the organization
     * @return the <code>Organization</code> found
     * @throws RepositoryException if an error occurs
     * @throws EntityNotFoundException no <code>Organization</code> is found for the given id
     */
    public Organization getOrganizationById(String organizationPartyId) throws RepositoryException, EntityNotFoundException;

    /**
     * Get all time periods for the organization, including those which are closed or in the past or future.
     * @param organizationPartyId the party ID for the organization
     * @return the list of <code>CustomTimePeriod</code> for the organization
     * @throws RepositoryException if an error occurs
     */
    public List<CustomTimePeriod> getAllFiscalTimePeriods(String organizationPartyId) throws RepositoryException;

    /**
     * Finds the open time periods for an organization.
     * This method select only those time periods which are relevant to the fiscal
     * operations of a company.
     * @param organizationPartyId the party ID for the organization
     * @return the list of <code>CustomTimePeriod</code> found
     * @throws RepositoryException if an error occurs
     */
    public List<CustomTimePeriod> getOpenFiscalTimePeriods(String organizationPartyId) throws RepositoryException;

    /**
     * Finds the open time periods for an organization that the given date falls in.
     * This method select only those time periods which are relevant to the fiscal
     * operations of a company.
     * @param organizationPartyId the party ID for the organization
     * @param asOfDate the date for which to get the open fiscal periods
     * @return the list of <code>CustomTimePeriod</code> found
     * @throws RepositoryException if an error occurs
     */
    public List<CustomTimePeriod> getOpenFiscalTimePeriods(String organizationPartyId, Timestamp asOfDate) throws RepositoryException;

    /**
     * Finds the default <code>PaymentMethod</code> for the given organization.
     * @param organizationPartyId the party ID for the organization
     * @return the default <code>PaymentMethod</code>
     * @throws RepositoryException if an error occurs
     */
    public PaymentMethod getDefaultPaymentMethod(String organizationPartyId) throws RepositoryException;

    /**
     * Gets the conversion factor from the given organization base currency into the given currency.
     * @param organizationPartyId the party ID for the organization
     * @param currencyUomId a <code>String</code> value
     * @return a <code>BigDecimal</code> value
     * @exception RepositoryException if an error occurs
     */
    public BigDecimal determineUomConversionFactor(String organizationPartyId, String currencyUomId) throws RepositoryException;

    /**
     * Gets the conversion factor from the given organization base currency into the given currency taking the conversion rate as of the given date.
     * @param organizationPartyId the party ID for the organization
     * @param currencyUomId a <code>String</code> value
     * @param asOfDate a <code>Timestamp</code> value
     * @return a <code>BigDecimal</code> value
     * @exception RepositoryException if an error occurs
     */
    public BigDecimal determineUomConversionFactor(String organizationPartyId, String currencyUomId, Timestamp asOfDate) throws RepositoryException;

    /**
     * Gets the configured (non-null) Tag Types for the given organization, as a <code>Map</code> of
     * {index value: configured <code>enumTypeId</code>}.  For example: {1=DIVISION_TAG, 2=DEPARTMENT_TAG, 3=ACTIVITY_TAG}
     * @param organizationPartyId the party ID for the organization
     * @param accountingTagUsageTypeId  the tag usage, for example "FINANCIALS_REPORTS", "PRCH_ORDER_ITEMS" ...
     * @return  a <code>Map</code> of tagIndex: enumtypeId
     * @throws RepositoryException if an error occurs
     */
    public Map<Integer, String> getAccountingTagTypes(String organizationPartyId, String accountingTagUsageTypeId) throws RepositoryException;

    /**
     * Gets the configuration for the given organization and usage type.
     * @param organizationPartyId the party ID for the organization
     * @param accountingTagUsageTypeId the tag usage, for example "FINANCIALS_REPORTS", "PRCH_ORDER_ITEMS" ...
     * @return a list of <code>AccountingTagConfigurationForOrganizationAndUsage</code>, each one representing the tag type and available tag values for each tag index
     * @throws RepositoryException if an error occurs
     */
    public List<AccountingTagConfigurationForOrganizationAndUsage> getAccountingTagConfiguration(String organizationPartyId, String accountingTagUsageTypeId) throws RepositoryException;

    /**
     * Gets the list of term type IDs that can be used for the given document type ID.
     * @param documentTypeId the document type to get the agreement term types for, eg: "SALES_INVOICE", "PURCHASE_ORDER", ...
     * @return a list of agreement term type IDs
     * @throws RepositoryException if an error occurs
     */
    public List<String> getValidTermTypeIds(String documentTypeId) throws RepositoryException;

    /**
     * Gets the list of term types that can be used for the given document type ID.
     * @param documentTypeId the document type to get the agreement term types for, eg: "SALES_INVOICE", "PURCHASE_ORDER", ...
     * @return a list of agreement term types
     * @throws RepositoryException if an error occurs
     */
    public List<TermType> getValidTermTypes(String documentTypeId) throws RepositoryException;

    /**
     * Gets all the organization party from the PartyRole = INTERNAL_ORGANIZATIO.
     * @return a list of organization party
     * @throws RepositoryException if an error occurs
     */
    public List<Organization> getAllValidOrganizations() throws RepositoryException;

    /**
     * validate the required tags if input for Pojo Service.
     * @param tags a <code>Map<String, String></code> value
     * @param organizationPartyId the party ID for the organization
     * @param accountingTagUsageTypeId  the tag usage, for example "FINANCIALS_REPORTS", "PRCH_ORDER_ITEMS" ...
     * @param prefix a <code>String</code> value
     * @return a <code>String</code> value
     * @throws RepositoryException if an error occurs
     */
    public String validateTagParameters(Map<String, String> tags, String organizationPartyId, String accountingTagUsageTypeId, String prefix) throws RepositoryException;
}
