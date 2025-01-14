package org.poo.cards;
import org.poo.accounts.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.CardCreation;
import org.poo.transactions.CardDestroy;
import org.poo.transactions.CardPayment;
import org.poo.transactions.ErrorTransaction;
import org.poo.users.Database;
import org.poo.users.User;
import org.poo.utils.Constants;

@Getter
@Setter
public class Card {
    protected String cardNumber;
    protected String status;
    protected User cardHolder;
    protected Account account;

    /**
     * @return
     */
    public String getType() {
        return "Card";
    }

    public Card(
            final String cardNumber,
            final User cardHolder,
            final Account account) {
        this.cardNumber = cardNumber;
        this.status = "active";
        this.cardHolder = cardHolder;
        this.account = account;
        account.getTransactions().add(new CardCreation(
                this,
                Database.getInstance().getTimestamp())
        );
    }

    /**
     * @param amount
     * @param currency
     * @param commerciant
     */
    public void makePayment(
            final double amount,
            final String currency,
            final String commerciant) {
        double cardAmount = amount * Database.getInstance()
                .getExchangeRate(currency, account.getCurrency());
        if (this.status.equals("frozen")) {
            account.getTransactions().add(new ErrorTransaction(
                    "The card is frozen",
                    Database.getInstance().getTimestamp())
            );
            return;
        }

        if (account.getBalance() < cardAmount) {
            account.getTransactions().add(new ErrorTransaction(
                    "Insufficient funds",
                    Database.getInstance().getTimestamp())
            );
            return;
        }

        account.withdraw(cardAmount);
        account.getTransactions().add(new CardPayment(
                this,
                cardAmount,
                commerciant,
                Database.getInstance().getTimestamp())
        );
        Database.getInstance().getCommerciantInputs().add(commerciant);
    }

    /**
     *
     */
    public void destroy() {
        account.getCards().remove(this);
        account.getTransactions().add(new CardDestroy(
                this,
                Database.getInstance().getTimestamp())
        );
    }

    /**
     *
     */
    public void checkCardStatus() {
        double absoluteValue = Math.abs(
                this.account.getBalance()
                        - this.account.getMinBalance());

        if (account.getMinBalance() >= account.getBalance()) {
            this.setStatus("frozen");
            account.getTransactions().add(new ErrorTransaction(
                    "You have reached the minimum amount of funds, the card will be frozen",
                    Database.getInstance().getTimestamp())
            );
        } else if (absoluteValue <= Constants.MIN_BALANCE_WARNING_THRESHOLD) {
            account.getTransactions().add(new ErrorTransaction(
                    "Warning, reaching minimum amount of funds",
                    Database.getInstance().getTimestamp())
            );
        }
    }

    /**
     * @return
     */
    public ObjectNode toJSON() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("cardNumber", this.cardNumber);
        objectNode.put("status", this.status);
        return objectNode;
    }
}
