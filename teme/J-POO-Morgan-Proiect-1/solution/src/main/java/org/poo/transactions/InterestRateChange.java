package org.poo.transactions;

public final class InterestRateChange extends Transaction {
    public InterestRateChange(
            final int timestamp,
            final double interestRate) {
        super(
           String.format("Interest rate of the account changed to %s", interestRate),
           timestamp
        );
    }

    @Override
    public String getType() {
        return "interestRateChange";
    }
}
