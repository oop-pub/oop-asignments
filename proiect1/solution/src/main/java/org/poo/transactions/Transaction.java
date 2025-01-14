package org.poo.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.reporting.AccountElement;
import org.poo.reporting.AccountVisitor;

public abstract class Transaction implements AccountElement {
    @Getter
    protected int timestamp;
    protected String description;

    /**
     * @return
     */
    public abstract String getType();

    public Transaction(
            final String description,
            final int timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public Transaction(final int timestamp) {
        this.description = "No description";
        this.timestamp = timestamp;
    }

    /**
     * @return
     */
    public ObjectNode toJson() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("timestamp", timestamp);
        objectNode.put("description", description);
        return objectNode;
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(final AccountVisitor visitor) {
        visitor.visit(this);
    }
}
