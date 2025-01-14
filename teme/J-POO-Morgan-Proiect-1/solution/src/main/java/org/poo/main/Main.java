package org.poo.main;

import org.poo.accounts.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.CommerciantInput;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.UserInput;
import org.poo.fileio.ObjectInput;
import org.poo.users.Database;
import org.poo.users.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.poo.main.Action.addAccount;
import static org.poo.main.Action.printUsers;
import static org.poo.main.Action.printTransactions;
import static org.poo.main.Action.createCard;
import static org.poo.main.Action.payOnline;
import static org.poo.main.Action.addFunds;
import static org.poo.main.Action.report;
import static org.poo.main.Action.sendMoney;
import static org.poo.main.Action.createOneTimeCard;
import static org.poo.main.Action.spendingsReport;
import static org.poo.main.Action.deleteCard;
import static org.poo.main.Action.deleteAccount;
import static org.poo.main.Action.setAlias;
import static org.poo.main.Action.setMinBalance;
import static org.poo.main.Action.checkCardStatus;
import static org.poo.main.Action.addInterest;
import static org.poo.main.Action.changeInterestRate;
import static org.poo.main.Action.splitPayment;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @param command
     * @param output
     * @param timestamp
     * @return
     */
    public static ObjectNode generateOutputEntry(
            final String command,
            final ContainerNode output,
            final int timestamp) {
        var node = MAPPER.createObjectNode();
        node.put("command", command);
        node.put("output", output);
        node.put("timestamp", timestamp);

        return node;
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        var sortedFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .toList();

        for (File file : sortedFiles) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(CheckerConstants.TESTS_PATH + filePath1);
        ObjectInput inputData = objectMapper.readValue(file, ObjectInput.class);

        ArrayNode output = objectMapper.createArrayNode();

        /*
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */
        Database.getInstance().init();

        UserInput[] userInputs = inputData.getUsers();
        ExchangeInput[] exchangeInputs = inputData.getExchangeRates();
        CommandInput[] commandInputs = inputData.getCommands();
        CommerciantInput[] commerciantInputs = inputData.getCommerciants();

        List<User> users = Database.getInstance().getUsers();
        for (UserInput userInput : userInputs) {
            users.add(new User(userInput));
        }

        for (ExchangeInput exchangeInput : exchangeInputs) {
            Database.getInstance().addExchangeRate(
                    exchangeInput.getFrom(),
                    exchangeInput.getTo(),
                    exchangeInput.getRate()
            );
        }

        ArrayNode arrayNode = null;
        Account account, account1, account2;

        for (CommandInput commandInput: commandInputs) {
            Database.getInstance().setTimestamp(commandInput.getTimestamp());
            User user = Database.getInstance().getUserByEmail(commandInput.getEmail());
            switch (commandInput.getCommand()) {
                case "addAccount":
                    addAccount(commandInput, user, output);
                    break;
                case "printUsers":
                    printUsers(commandInput, users, output);
                    break;
                case "printTransactions":
                    printTransactions(commandInput, user, output);
                    break;
                case "createCard":
                    createCard(commandInput, user);
                    break;
                case "payOnline":
                    payOnline(commandInput, user, output);
                    break;
                case "addFunds":
                    addFunds(commandInput, output);
                    break;
                case "report":
                    report(commandInput, output);
                    break;
                case "sendMoney":
                    sendMoney(commandInput, user, output);
                    break;
                case "createOneTimeCard":
                    createOneTimeCard(commandInput, user, output);
                    break;
                case "spendingsReport":
                    spendingsReport(commandInput, output);
                    break;
                case "deleteCard":
                    deleteCard(commandInput, user, output);
                    break;
                case "deleteAccount":
                    deleteAccount(commandInput, user, output);
                    break;
                case "setAlias":
                    setAlias(commandInput, user, output);
                    break;
                case "setMinBalance":
                    setMinBalance(commandInput, user, output);
                    break;
                case "checkCardStatus":
                    checkCardStatus(commandInput, output);
                    break;
                case "addInterest":
                    addInterest(commandInput, output);
                    break;
                case "changeInterestRate":
                    changeInterestRate(commandInput, output);
                    break;
                case "splitPayment":
                    splitPayment(commandInput);
                    break;
                default:
                    break;
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    /**
     * Method used for extracting the test number from the file name.
     *
     * @param file the input file
     * @return the extracted numbers
     */
    public static int fileConsumer(final File file) {
        String fileName = file.getName()
                .replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR);
        return Integer.parseInt(fileName.substring(0, 2));
    }
}
