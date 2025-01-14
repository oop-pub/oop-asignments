package org.poo.transactions;

import org.poo.accounts.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.users.Database;

import java.util.HashMap;
import java.util.List;

public final class SplitPayment extends Transaction {
    private final List<Account> accounts;
    private final double amount;
    private final String currency;

    public SplitPayment(
            final List<Account> accounts,
            final int timestamp,
            final double amount,
            final String currency) {
        super(String
                .format("Split payment of %.2f %s", amount, currency)
                .replace(",", "."),
                timestamp
        );
        this.accounts = accounts;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String getType() {
        return "splitPayment";
    }

    private boolean hasError;
    private String errorMessage;

    @Override
    public ObjectNode toJson() {
        var baseTransaction = super.toJson();
        baseTransaction.put("currency", currency);
        baseTransaction.put("amount", amount / accounts.size());
        var involvedAccounts = baseTransaction.putArray("involvedAccounts");
        accounts.forEach(account -> involvedAccounts.add(account.getIban()));

        if (hasError) {
            baseTransaction.put("error", errorMessage);
        }

        return baseTransaction;
    }

    /**
     */
    public void makePayment() {
        var node = new ObjectMapper().createObjectNode();

        var splitAmount = amount / accounts.size();

        HashMap<String, Double> amountMap = new HashMap<>();
        accounts.forEach(account -> amountMap.put(account.getIban(), 0.0));

        this.checkIfAllAccountsAreEligibleForTransfer(amountMap, splitAmount);
        this.accounts.forEach(acc -> acc.getTransactions().add(this));
        if (this.hasError) {
            return;
        }

        accounts.forEach(acc -> acc.withdraw(amountMap.get(acc.getIban())));
    }

    private void checkIfAllAccountsAreEligibleForTransfer(
            final HashMap<String, Double> amountMap,
            final double splitAmount) {
        for (var account : accounts) {
            var exchangeRate = account.getCurrency().equals(currency)
                    ? 1.0
                    : Database.getInstance()
                        .getExchangeRate(this.currency, account.getCurrency());

            amountMap.put(account.getIban(), splitAmount * exchangeRate);

            if (account.getBalance() < amountMap.get(account.getIban())) {
                this.hasError = true;
                this.errorMessage = String.format(
                        "Account %s has insufficient funds for a split payment.",
                        account.getIban()
                );
            }
        }
    }
}
