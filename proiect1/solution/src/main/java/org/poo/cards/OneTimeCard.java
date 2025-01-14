package org.poo.cards;

import org.poo.accounts.Account;
import org.poo.transactions.ErrorTransaction;
import org.poo.users.Database;
import org.poo.users.User;

public final class OneTimeCard extends Card {
    private boolean isUsed = false;
    public OneTimeCard(
            final String cardNumber,
            final String expiryDate,
            final User cardHolder,
            final Account account) {
        super(cardNumber, cardHolder, account);
    }

    @Override
    public String getType() {
        return "One Time Card";
    }

    @Override
    public void makePayment(
            final double amount,
            final String currency,
            final String commerciant) {
        if (isUsed) {
            account.getTransactions().add(new ErrorTransaction(
                    "Card has already been used",
                    Database.getInstance().getTimestamp())
            );
            return;
        }

        if (this.status.equals("frozen")) {
            account.getTransactions().add(new ErrorTransaction(
                    "The card is frozen",
                    Database.getInstance().getTimestamp())
            );
            return;
        }

        double cardAmount = amount * Database.getInstance()
                .getExchangeRate(currency, account.getCurrency());
        if (account.getBalance() < cardAmount) {
            account.getTransactions().add(new ErrorTransaction(
                    "Insufficient funds",
                    Database.getInstance().getTimestamp())
            );
            return;
        }
        super.makePayment(cardAmount, account.getCurrency(), commerciant);
        isUsed = true;
        destroy();
        account.createOneTimeCard();
    }
}
