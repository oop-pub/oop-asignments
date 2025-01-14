package org.poo.transactions;

public final class ErrorTransaction extends Transaction {
    public ErrorTransaction(
            final String description,
            final int timestamp) {
        super(description, timestamp);
    }

    @Override
    public String getType() {
        return "Error";
    }
}
