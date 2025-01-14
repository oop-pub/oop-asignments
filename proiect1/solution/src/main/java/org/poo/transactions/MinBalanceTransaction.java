package org.poo.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.users.Database;

public final class MinBalanceTransaction extends Transaction {
    private final String accountIBAN;
    private final double minimumBalance;
    private final double currentBalance;

    public MinBalanceTransaction(
            final String accountIBAN,
            final double minimumBalance,
            final double currentBalance,
            final int timestamp) {
        super("Minimum balance transaction", timestamp);
        this.accountIBAN = accountIBAN;
        this.minimumBalance = minimumBalance;
        this.currentBalance = currentBalance;
    }

    @Override
    public String getType() {
        return "minBalanceTransaction";
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode objectNode = super.toJson();
        objectNode.put("accountIBAN", accountIBAN);
        objectNode.put("minimumBalance", minimumBalance + " "
                + Database.getInstance().getAccountByIBAN(accountIBAN).getCurrency());
        objectNode.put("currentBalance", currentBalance + " "
                + Database.getInstance().getAccountByIBAN(accountIBAN).getCurrency());
        return objectNode;
    }
}
