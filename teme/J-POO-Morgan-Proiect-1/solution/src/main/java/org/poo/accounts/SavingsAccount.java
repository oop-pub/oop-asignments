package org.poo.accounts;

import lombok.Getter;
import lombok.Setter;
import org.poo.reporting.AccountVisitor;
import org.poo.transactions.InterestIncome;
import org.poo.transactions.InterestRateChange;
import org.poo.users.User;

@Getter
@Setter
public final class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(
            final User user,
            final String currency,
            final double interestRate) {
        super(user, currency);
        this.type = "savings";
        this.interestRate = interestRate;
    }

    /**
     * @param timestamp
     */
    public void addInterest(final int timestamp) {
        var amountToIncrease = this.getBalance() * this.interestRate;
        this.setBalance(this.getBalance() + amountToIncrease);
        this.getTransactions().add(new InterestIncome(
                timestamp,
                amountToIncrease,
                this.getCurrency())
        );
    }

    /**
     * @param timestamp
     * @param interestRateUpdate
     */
    public void changeInterestRate(
            final int timestamp,
            final double interestRateUpdate) {
        this.interestRate = interestRateUpdate;
        this.getTransactions().add(new InterestRateChange(
                timestamp,
                interestRateUpdate)
        );
    }

    @Override
    public void accept(final AccountVisitor visitor) {
        visitor.visit(this);
    }
}
