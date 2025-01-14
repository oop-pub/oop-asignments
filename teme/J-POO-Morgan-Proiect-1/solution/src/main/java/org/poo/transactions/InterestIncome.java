package org.poo.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class InterestIncome extends Transaction {
    private final double amount;
    private final String currency;

    public InterestIncome(
            final int timestamp,
            final double amount,
            final String currency) {
        super("Interest rate income", timestamp);
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String getType() {
        return "interest";
    }

    @Override
    public ObjectNode toJson() {
        var baseTransaction = super.toJson();
        baseTransaction.put("amount", amount);
        baseTransaction.put("currency", currency);
        return baseTransaction;
    }
}
