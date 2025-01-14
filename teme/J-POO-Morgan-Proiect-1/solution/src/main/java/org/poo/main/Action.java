package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.cards.Card;
import org.poo.fileio.CommandInput;
import org.poo.transactions.ErrorTransaction;
import org.poo.transactions.SplitPayment;
import org.poo.transactions.Transaction;
import org.poo.users.Database;
import org.poo.users.User;

import java.util.Comparator;
import java.util.List;

import static org.poo.main.Main.generateOutputEntry;

public final class Action {
    /**
     * To bypass checkstyle
     */
    private Action() { }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void addAccount(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        if (user != null) {
            if (commandInput.getAccountType().equals("savings")) {
                user.createSavingsAccount(
                        commandInput.getCurrency(),
                        commandInput.getInterestRate()
                );
                return;
            }
            user.createAccount(commandInput.getCurrency());
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param users
     * @param output
     */
    public static void printUsers(
            final CommandInput commandInput,
            final List<User> users,
            final ArrayNode output
    ) {
        ArrayNode arrayNode;
        arrayNode = new ObjectMapper().createArrayNode();
        for (User u : users) {
            arrayNode.add(u.toJSON());
        }
        output.add(generateOutputEntry(
                commandInput.getCommand(),
                arrayNode,
                commandInput.getTimestamp())
        );
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void printTransactions(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        ArrayNode arrayNode;
        arrayNode = new ObjectMapper().createArrayNode();
        if (user != null) {
            List<Transaction> orderedTransactions = user.getAccounts().stream()
                    .map(Account::getTransactions)
                    .flatMap(List::stream)
                    .sorted(Comparator.comparing(Transaction::getTimestamp))
                    .toList();

            for (Transaction transaction : orderedTransactions) {
                arrayNode.add(transaction.toJson());
            }
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    arrayNode,
                    commandInput.getTimestamp())
            );
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     */
    public static void createCard(
            final CommandInput commandInput,
            final User user
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account != null && user != null && account.getOwner() == user) {
            account.createCard();
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void payOnline(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        if (user != null) {
            Card card = user.getCardByNumber(commandInput.getCardNumber());
            if (card != null) {
                card.makePayment(
                        commandInput.getAmount(),
                        commandInput.getCurrency(),
                        commandInput.getCommerciant()
                );
            } else {
                output.add(generateOutputEntry(
                        commandInput.getCommand(),
                        new ErrorTransaction(
                                "Card not found",
                                commandInput.getTimestamp()).toJson(),
                        commandInput.getTimestamp()));
            }
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void addFunds(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account != null) {
            account.deposit(commandInput.getAmount());
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Account not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void report(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account != null) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    account.getReport(
                            commandInput.getStartTimestamp(),
                            commandInput.getEndTimestamp()),
                    commandInput.getTimestamp()));
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Account not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void sendMoney(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        Account account2;
        Account account1;
        if (user != null) {
            account1 = Database.getInstance()
                    .getAccountByIBAN(commandInput.getAccount());
            String receiver = commandInput.getReceiver();
            if (user.getAliases().get(receiver) != null) {
                receiver = user.getAliases().get(receiver);
            }
            account2 = Database.getInstance().getAccountByIBAN(receiver);
            if (account1 != null && account2 != null && account1.getOwner() == user) {
                account1.sendMoney(
                        commandInput.getAmount(),
                        account2,
                        commandInput.getDescription()
                );
            }
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void createOneTimeCard(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account != null && user != null && account.getOwner() == user) {
            account.createOneTimeCard();
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void spendingsReport(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance()
                .getAccountByIBAN(commandInput.getAccount());
        if (account != null) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    account.getSpendingReport(
                            commandInput.getStartTimestamp(),
                            commandInput.getEndTimestamp()),
                    commandInput.getTimestamp()));
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Account not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void deleteCard(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        if (user != null) {
            Card card = user.getCardByNumber(commandInput.getCardNumber());
            if (card != null) {
                card.destroy();
            }
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void deleteAccount(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        Account account;
        if (user != null) {
            account = Database.getInstance()
                    .getAccountByIBAN(commandInput.getAccount());
            if (account != null && account.getOwner() == user) {
                output.add(generateOutputEntry(
                        commandInput.getCommand(),
                        account.destroyAccount(),
                        commandInput.getTimestamp()
                ));
            }
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void setAlias(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        if (user != null) {
            user.getAliases().put(commandInput.getAlias(), commandInput.getAccount());
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param user
     * @param output
     */
    public static void setMinBalance(
            final CommandInput commandInput,
            final User user,
            final ArrayNode output
    ) {
        Account account;
        if (user != null) {
            account = Database.getInstance()
                    .getAccountByIBAN(commandInput.getAccount());
            if (account != null && account.getOwner() == user) {
                account.minBalanceTransaction(
                        commandInput.getMinBalance(), account);
            }
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "User not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void checkCardStatus(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Card card = Database.getInstance()
                .getCardByNumber(commandInput.getCardNumber());
        if (card != null) {
            card.checkCardStatus();
        } else {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Card not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
        }
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void addInterest(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account == null) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Account not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
            return;
        }

        if (!account.getType().equals("savings")) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "This is not a savings account",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
            return;
        }

        ((SavingsAccount) account).addInterest(commandInput.getTimestamp());
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     * @param output
     */
    public static void changeInterestRate(
            final CommandInput commandInput,
            final ArrayNode output
    ) {
        Account account;
        account = Database.getInstance().getAccountByIBAN(commandInput.getAccount());
        if (account == null) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "Account not found",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
            return;
        }

        if (!account.getType().equals("savings")) {
            output.add(generateOutputEntry(
                    commandInput.getCommand(),
                    new ErrorTransaction(
                            "This is not a savings account",
                            commandInput.getTimestamp()).toJson(),
                    commandInput.getTimestamp()));
            return;
        }

        ((SavingsAccount) account).changeInterestRate(
                commandInput.getTimestamp(),
                commandInput.getInterestRate()
        );
    }

    /**
     * To bypass checkstyle
     *
     * @param commandInput
     */
    public static void splitPayment(final CommandInput commandInput) {
        var accounts = commandInput.getAccounts().stream()
                .map(Database.getInstance()::getAccountByIBAN)
                .toList();

        var splitPayment = new SplitPayment(
                accounts,
                commandInput.getTimestamp(),
                commandInput.getAmount(),
                commandInput.getCurrency()
        );

        splitPayment.makePayment();
    }
}
