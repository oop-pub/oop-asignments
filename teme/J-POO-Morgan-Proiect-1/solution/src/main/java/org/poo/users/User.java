package org.poo.users;

import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.cards.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public final class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Account> accounts = new ArrayList<>();
    private final List<Account> destroyedAccounts = new ArrayList<>();
    private final HashMap<String, String> aliases = new HashMap<>();

    public User(
            final String firstName,
            final String lastName,
            final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(final UserInput userInput) {
        this.firstName = userInput.getFirstName();
        this.lastName = userInput.getLastName();
        this.email = userInput.getEmail();
    }

    /**
     * @param currency
     */
    public void createAccount(final String currency) {
        accounts.add(new Account(this, currency));
    }

    /**
     * @param currency
     * @param interestRate
     */
    public void createSavingsAccount(
            final String currency,
            final double interestRate) {
        accounts.add(new SavingsAccount(this, currency, interestRate));
    }

    /**
     * @param cardNumber
     * @return
     */
    public Card getCardByNumber(final String cardNumber) {
        for (Account account : accounts) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return card;
                }
            }
        }
        return null;
    }

    /**
     * @return
     */
    public ObjectNode toJSON() {
        ObjectNode userNode = new ObjectMapper().createObjectNode();
        userNode.put("firstName", firstName);
        userNode.put("lastName", lastName);
        userNode.put("email", email);

        ArrayNode accountsNode = new ObjectMapper().createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.toJSON());
        }
        userNode.set("accounts", accountsNode);
        return userNode;
    }
}
