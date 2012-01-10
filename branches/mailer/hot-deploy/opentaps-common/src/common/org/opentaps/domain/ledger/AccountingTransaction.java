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
package org.opentaps.domain.ledger;

import java.math.BigDecimal;
import java.util.List;

import org.ofbiz.base.util.UtilNumber;
import org.opentaps.domain.base.entities.AcctgTransEntry;
import org.opentaps.foundation.repository.RepositoryException;

/**
 * Accounting Transaction Entry.
 */
public class AccountingTransaction extends org.opentaps.domain.base.entities.AcctgTrans {

    private int roundingMode = -1;
    private int decimals = -1;

    private List<AcctgTransEntry> transactionEntries;
    private BigDecimal creditTotal;
    private BigDecimal debitTotal;

    /**
     * Checks if this transaction is posted to the ledger.
     * @return a <code>boolean</code> value
     */
    public boolean isPosted() {
        return getSpecification().isPosted(this);
    }

    /**
     * Gets the rounding mode.
     * TODO: After thinking about this, I think these should be in an interface RoundingInterface and perhaps use a convention such as opentaps.entity.AccountingTransaction.rounding
     * @return an <code>int</code> value
     */
    public int getRoundingMode() {
        if (roundingMode == -1) {
            roundingMode = UtilNumber.getBigDecimalRoundingMode("invoice.rounding");
        }
        return roundingMode;
    }

    /**
     * Gets the number of decimals used for rounding.
     * @return an <code>int</code> value
     */
    public int getDecimals() {
        if (decimals == -1) {
            decimals = UtilNumber.getBigDecimalScale("invoice.decimals");
        }
        return decimals;
    }

    /**
     * Get the transaction entries.  Lazy loads the entries so subsequent calls do not hit database again.
     * @return the <code>List</code> of <code>AcctgTransEntry</code> for this transaction.
     * @exception RepositoryException if an error occurs
     */
    public List<AcctgTransEntry> getTransactionEntries() throws RepositoryException {
        if (transactionEntries == null) {
            transactionEntries = getRepository().getTransactionEntries(getAcctgTransId());
        }
        return transactionEntries;
    }

    /**
     * Checks if this transaction can be posted.
     * @return a <code>boolean</code> value
     * @throws LedgerException if an error occurs
     * @throws RepositoryException if an error occurs
     */
    public boolean canPost() throws LedgerException, RepositoryException {
        return getDebitTotal().compareTo(getCreditTotal()) == 0;
    }

    private void updateCreditDebitTotals() throws LedgerException, RepositoryException {
        LedgerSpecificationInterface spec = getSpecification();
        creditTotal = BigDecimal.ZERO;
        debitTotal = BigDecimal.ZERO;
        // sum up the debit and credit entries, rounding each time we add
        for (AcctgTransEntry entry : getTransactionEntries()) {
            if (spec.isDebit(entry)) {
                debitTotal = debitTotal.add(entry.getAmount()).setScale(getDecimals(), getRoundingMode());
            } else if (spec.isCredit(entry)) {
                creditTotal = creditTotal.add(entry.getAmount()).setScale(getDecimals(), getRoundingMode());
            } else {
                throw new LedgerException("FinancialsError_BadDebitCreditFlag", entry.toMap());
            }
        }
    }

    /**
     * Gets the sum of all credit entries.
     * @return a <code>BigDecimal</code> value
     * @exception LedgerException if an error occurs
     * @exception RepositoryException if an error occurs
     */
    public BigDecimal getCreditTotal() throws LedgerException, RepositoryException {
        if (creditTotal == null) {
            updateCreditDebitTotals();
        }
        return creditTotal;
    }

    /**
     * Gets the sum of all debit entries.
     * @return a <code>BigDecimal</code> value
     * @exception LedgerException if an error occurs
     * @exception RepositoryException if an error occurs
     */
    public BigDecimal getDebitTotal() throws LedgerException, RepositoryException {
        if (debitTotal == null) {
            updateCreditDebitTotals();
        }
        return debitTotal;
    }

    /**
     * Calculates the trial balance for this transaction which is the sums of all credit entries minus the sum of all debit entries.
     * @return a <code>BigDecimal</code> value
     * @exception LedgerException if an error occurs
     * @exception RepositoryException if an error occurs
     */
    public BigDecimal getTrialBalance() throws LedgerException, RepositoryException {
        // return debits minus credits
        return getDebitTotal().subtract(getCreditTotal()).setScale(getDecimals(), getRoundingMode());
    }

    /**
     * Gets the repository.
     * @return a <code>LedgerRepositoryInterface</code> value
     */
    public LedgerRepositoryInterface getRepository() {
        return (LedgerRepositoryInterface) repository;
    }

    /**
     * Gets the ledger specification interface.
     * @return a <code>LedgerSpecificationInterface</code> value
     */
    public LedgerSpecificationInterface getSpecification() {
        return getRepository().getSpecification();
    }

}
