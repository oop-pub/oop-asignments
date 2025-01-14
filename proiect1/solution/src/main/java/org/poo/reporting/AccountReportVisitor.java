package org.poo.reporting;

import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.transactions.Transaction;

import java.util.List;

public class AccountReportVisitor implements AccountVisitor {
    protected final ObjectMapper mapper = new ObjectMapper();

    @Getter
    protected final ObjectNode root = mapper.createObjectNode();

    protected final int start;
    protected final int end;

    protected ArrayNode transactionsNode;

    public AccountReportVisitor(
            final int start,
            final int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @param account
     */
    @Override
    public void visit(final Account account) {
        root.put("IBAN", account.getIban());
        root.put("balance", account.getBalance());
        root.put("currency", account.getCurrency());

        transactionsNode = root.putArray("transactions");
        this.acceptTransactions(account.getTransactions());
    }

    /**
     * @param transaction
     */
    @Override
    public void visit(final Transaction transaction) {
        transactionsNode.add(transaction.toJson());
    }

    /**
     * @param savingsAccount
     */
    @Override
    public void visit(final SavingsAccount savingsAccount) {
        root.put("IBAN", savingsAccount.getIban());
        root.put("balance", savingsAccount.getBalance());
        root.put("currency", savingsAccount.getCurrency());

        transactionsNode = root.putArray("transactions");

        var compatibleTransactions = List.of("interestRateChange", "interest");
        this.acceptTransactions(savingsAccount.getTransactions()
                .stream().filter(t -> compatibleTransactions.contains(t.getType())).toList());
    }

    /**
     * @param transactions
     */
    public void acceptTransactions(final List<Transaction> transactions) {
        transactions.stream()
                .filter(t -> t.getTimestamp() >= start && t.getTimestamp() <= end)
                .forEach(transaction -> transaction.accept(this));
    }
}
