package org.poo.transactions;

import org.poo.accounts.Account;

public final class AccountCreation extends Transaction {
    private final Account account;
    private final String currency;

    public AccountCreation(
            final Account account,
            final String currency,
            final int timestamp) {
        super("New account created", timestamp);
        this.account = account;
        this.currency = currency;
    }

    @Override
    public String getType() {
        return "AccountCreation";
    }
}
