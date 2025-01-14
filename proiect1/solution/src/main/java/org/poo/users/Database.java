package org.poo.users;


import org.poo.accounts.Account;
import org.poo.cards.Card;
import lombok.Getter;
import lombok.Setter;
import org.poo.utils.Pair;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public final class Database {
    private static final Database INSTANCE = new Database();

    @Getter
    private final List<User> users = new ArrayList<>();

    private final HashMap<Pair<String, String>, Double> exchangeRates = new HashMap<>();

    @Getter @Setter
    private int timestamp = 0;


    @Getter @Setter
    private Set<String> commerciantInputs = new TreeSet<>();

    private Database() {
    }

    /**
     * @param iban
     * @return
     */
    public Account getAccountByIBAN(final String iban) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * @param cardNumber
     * @return
     */
    public Card getCardByNumber(final String cardNumber) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param email
     * @return
     */
    public User getUserByEmail(final String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param currency1
     * @param currency2
     * @param rate
     */
    public void addExchangeRate(
            final String currency1,
            final String currency2,
            final double rate) {
        exchangeRates.put(new Pair<>(currency1, currency2), rate);
        exchangeRates.put(new Pair<>(currency2, currency1), 1 / rate);
    }

    /**
     * @param currency1
     * @param currency2
     * @return
     */
    public double getExchangeRate(
            final String currency1,
            final String currency2) {
        if (currency1.equals(currency2)) {
            return 1;
        }
        Double rate = exchangeRates.get(new Pair<>(currency1, currency2));
        if (rate != null) {
            return rate;
        }

        List<String> froms = new ArrayList<>();
        froms.add(currency1);

        for (int i = 0; i < froms.size(); i++) {
            String from = froms.get(i);
            for (String to : exchangeRates.keySet().stream()
                    .filter(pair -> pair.getValue0().equals(from))
                    .map(Pair::getValue1).toList()) {
                exchangeRates.put(new Pair<>(currency1, to),
                        getExchangeRate(currency1, from)
                                * getExchangeRate(from, to));
                if (to.equals(currency2)) {
                    return exchangeRates.get(new Pair<>(currency1, to));
                }
                froms.add(to);
            }
        }
        return exchangeRates.get(new Pair<>(currency1, currency2));
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    /**
     */
    public void init() {
        Utils.resetRandom();
        users.clear();
        exchangeRates.clear();
        commerciantInputs.clear();
    }
}
