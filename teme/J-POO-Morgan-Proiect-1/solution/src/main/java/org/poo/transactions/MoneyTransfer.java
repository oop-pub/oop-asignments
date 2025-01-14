package org.poo.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.users.Database;

public final class MoneyTransfer extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private double newBalance;
    private String description;

    @Setter @Getter
    private String type;

    public MoneyTransfer(
            final String senderIBAN,
            final String receiverIBAN,
            final double amount,
            final double newBalance,
            final String description,
            final int timestamp,
            final String type) {
        super(description, timestamp);
        this.receiverIBAN = receiverIBAN;
        this.senderIBAN = senderIBAN;
        this.amount = amount;
        this.newBalance = newBalance;
        this.type = type;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode objectNode = super.toJson();
        objectNode.put("senderIBAN", senderIBAN);
        objectNode.put("receiverIBAN", receiverIBAN);

        if (type.equals("sent")) {
            objectNode.put("amount", amount + " "
                    + Database.getInstance()
                    .getAccountByIBAN(senderIBAN).getCurrency());
        } else {
            objectNode.put("amount", amount + " "
                    + Database.getInstance()
                    .getAccountByIBAN(receiverIBAN).getCurrency());
        }

        objectNode.put("transferType", type);
        return objectNode;
    }
}
