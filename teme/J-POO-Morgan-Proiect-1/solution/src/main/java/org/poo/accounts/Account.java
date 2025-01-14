package org.poo.accounts;

import org.poo.cards.Card;
import org.poo.cards.OneTimeCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.ErrorTransaction;
import org.poo.transactions.MinBalanceTransaction;
import org.poo.transactions.MoneyTransfer;
import org.poo.transactions.AccountCreation;
import org.poo.transactions.Transaction;
import org.poo.utils.Utils;
import org.poo.reporting.AccountElement;
import org.poo.reporting.AccountReportVisitor;
import org.poo.reporting.AccountSpendingVisitor;
import org.poo.reporting.AccountVisitor;
import org.poo.users.Database;
import org.poo.users.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Account implements AccountElement {
    protected String iban;
    protected double balance;
    protected double minBalance;
    protected User owner;
    protected String currency;
    protected List<Transaction> transactions = new ArrayList<>();
    protected List<Card> cards = new ArrayList<>();
    protected String type;

    public Account(final User user, final String currency) {
        this.owner = user;
        this.currency = currency;
        this.iban = Utils.generateIBAN();
        this.transactions.add(
                new AccountCreation(
                        this,
                        this.currency,
                        Database.getInstance().getTimestamp()
                )
        );
        this.type = "classic";
    }

    /**
     * @param amount
     */
    public void deposit(final double amount) {
        balance += amount;
    }

    /**
     * @param amount
     */
    public void withdraw(final double amount) {
        balance -= amount;
    }

    /**
     */
    public void createCard() {
        cards.add(new Card(
                Utils.generateCardNumber(),
                owner,
                this
            )
        );
    }

    /**
     */
    public void createOneTimeCard() {
        cards.add(new OneTimeCard(
                Utils.generateCardNumber(),
                "12/24",
                owner,
                this)
        );
    }

    /**
     * @param amount
     * @param account
     * @param description
     */
    public void sendMoney(
            final double amount,
            final Account account,
            final String description) {
        if (amount > this.balance) {
            this.transactions.add(new ErrorTransaction(
                    "Insufficient funds",
                    Database.getInstance().getTimestamp())
            );
            return;
        } else if (account.getMinBalance() + amount >= this.balance
                && account.getMinBalance() > 0) {
            this.transactions.add(new ErrorTransaction(
                    "Cannot perform payment due to a minimum balance being set",
                    Database.getInstance().getTimestamp())
            );
            return;
        }
        double amountReceived = amount * Database.getInstance()
                .getExchangeRate(this.currency, account.currency);

        account.transactions.add(new MoneyTransfer(
                this.iban,
                account.iban,
                amountReceived,
                account.balance + amountReceived,
                description,
                Database.getInstance().getTimestamp(),
                "received")
        );

        this.transactions.add(new MoneyTransfer(
                this.iban,
                account.iban,
                amount,
                this.balance - amount,
                description,
                Database.getInstance().getTimestamp(),
                "sent")
        );

        this.balance -= amount;
        account.balance += amountReceived;
    }

    /**
     * @param minBalanceValue
     * @param account
     */
    public void minBalanceTransaction(
            final double minBalanceValue,
            final Account account) {
        account.setMinBalance(minBalanceValue);
        account.getTransactions().add(new MinBalanceTransaction(
                account.getIban(),
                minBalanceValue,
                account.getBalance(),
                Database.getInstance().getTimestamp())
        );
    }

    /**
     * @param start
     * @param end
     * @return
     */
    public ObjectNode getReport(final int start, final int end) {
        AccountReportVisitor accountReportVisitor = new AccountReportVisitor(start, end);
        accountReportVisitor.visit(this);
        return accountReportVisitor.getRoot();
    }

    /**
     * @param start
     * @param end
     * @return
     */
    public ObjectNode getSpendingReport(final int start, final int end) {
        AccountSpendingVisitor accountSpendingVisitor = new AccountSpendingVisitor(start, end);
        accountSpendingVisitor.visit(this);
        return accountSpendingVisitor.getRoot();
    }

    /**
     * @return
     */
    public ObjectNode destroyAccount() {
        if (this.balance != 0) {
            this.transactions.add(new ErrorTransaction(
                    "Account couldn't be deleted - there are funds remaining",
                    Database.getInstance().getTimestamp())
            );
            return new ObjectMapper().createObjectNode()
                    .put(
                            "error",
                            "Account couldn't be deleted - see org.poo.transactions for details"
                    )
                    .put("timestamp", Database.getInstance().getTimestamp());
        }

        for (Card card : this.cards) {
            card.destroy();
        }

        this.owner.getAccounts().remove(this);

        return new ObjectMapper().createObjectNode()
                .put("success", "Account deleted")
                .put("timestamp", Database.getInstance().getTimestamp());
    }

    /**
     * @param visitor
     */
    @Override
    public void accept(final AccountVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return
     */
    public ObjectNode toJSON() {
        ObjectNode accountNode = new ObjectMapper().createObjectNode();
        accountNode.put("IBAN", iban);
        accountNode.put("balance", balance);
        accountNode.put("currency", currency);
        accountNode.put("type", type);

        ArrayNode cardsNode = new ObjectMapper().createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.toJSON());
        }
        accountNode.set("cards", cardsNode);
        return accountNode;
    }
}
