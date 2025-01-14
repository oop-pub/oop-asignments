package org.poo.transactions;

import org.poo.cards.Card;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

@Getter
public final class CardPayment extends Transaction {
    private final double amount;
    private final Card card;
    private final String commerciant;

    @Override
    public String getType() {
        return "cardPayment";
    }

    public CardPayment(
            final Card card,
            final double amount,
            final String commerciant,
            final int timestamp) {
        super("Card payment", timestamp);
        this.card = card;
        this.amount = amount;
        this.commerciant = commerciant;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode objectNode = super.toJson();
        objectNode.put("amount", amount);
        objectNode.put("commerciant", commerciant);
        return objectNode;
    }
}
