package org.poo.reporting;

import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.transactions.Transaction;

public interface AccountVisitor {
    /**
     * @param account
     */
    void visit(Account account);

    /**
     * @param transaction
     */
    void visit(Transaction transaction);

    /**
     * @param savingsAccount
     */
    void visit(SavingsAccount savingsAccount);
}
