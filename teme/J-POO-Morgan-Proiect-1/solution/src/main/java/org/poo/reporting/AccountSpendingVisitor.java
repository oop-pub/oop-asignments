package org.poo.reporting;

import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.transactions.CardPayment;
import org.poo.transactions.Transaction;
import org.poo.users.Database;

import java.util.List;

public final class AccountSpendingVisitor extends AccountReportVisitor {
    public AccountSpendingVisitor(
            final int start,
            final int end) {
        super(start, end);
    }

    @Override
    public void visit(final Account account) {
        if (account.getType().equals("savings")) {
            this.visit((SavingsAccount) account);
            return;
        }

        super.visit(account);

        ArrayNode commerciantTransactions = root.putArray("commerciants");

        var transactions = account.getTransactions().stream()
                .filter(t -> t.getTimestamp() >= start
                        && t.getTimestamp() <= end
                        && t.getType().equals("cardPayment"))
                .toList();

        for (String commerciant : Database.getInstance().getCommerciantInputs()) {
            var selectedTransactionsForComm = transactions.stream()
                    .filter(transaction -> ((CardPayment) transaction)
                            .getCommerciant().equals(commerciant))
                    .toList();

            if (!selectedTransactionsForComm.isEmpty()) {
                double total = selectedTransactionsForComm.stream().mapToDouble(
                        transaction -> ((CardPayment) transaction).getAmount()).sum();
                ObjectNode commerciantNode = new ObjectMapper().createObjectNode();
                commerciantNode.put("commerciant", commerciant);
                commerciantNode.put("total", total);
                commerciantTransactions.add(commerciantNode);
            }
        }
    }

    @Override
    public void visit(final SavingsAccount savingsAccount) {
        root.put("error", "This kind of report is not supported for a saving account");
    }

    @Override
    public void acceptTransactions(final List<Transaction> transactions) {
        transactions.stream()
                .filter(t -> t.getTimestamp() >= start
                        && t.getTimestamp() <= end
                        && t.getType().equals("cardPayment"))
                .forEach(t -> t.accept(this));
    }
}
