package org.poo.transactions;

import org.poo.cards.Card;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CardDestroy extends Transaction {
    private final Card card;

    public CardDestroy(
            final Card card,
            final int timestamp) {
        super("The card has been destroyed", timestamp);
        this.card = card;
    }

    @Override
    public String getType() {
        return "CardCreation";
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode objectNode = super.toJson();
        objectNode.put("card", card.getCardNumber());
        objectNode.put("cardHolder", card.getCardHolder().getEmail());
        objectNode.put("account", card.getAccount().getIban());
        return objectNode;
    }
}
