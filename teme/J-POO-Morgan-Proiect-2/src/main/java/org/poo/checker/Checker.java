package org.poo.checker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Main;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Checker {
    private static int gitScore;
    private static int readmeScore;
    private static int totalScore = 0;

    private Checker() {
    }

    private static void calculateScoreGit() {
        System.out.print("GIT commit score: ");

        Path path = Paths.get("git_log.txt");
        if (Files.exists(path)) {
            gitScore = CheckerConstants.FIVE_POINTS;
            System.out.println(gitScore + "/5");
        } else {
            gitScore = CheckerConstants.ZERO_POINTS;
            System.out.println(gitScore + "/5");
        }
    }

    private static void calculateScoreReadme() {
        System.out.println("-----------------------------------------------------");
        System.out.print("README score: ");
        Path path1 = Paths.get("README");
        Path path2 = Paths.get("README.md");
        Path path3 = Paths.get("README.txt");

        if (Files.exists(path1) || Files.exists(path2) || Files.exists(path3)) {
            readmeScore = CheckerConstants.FIVE_POINTS;
            System.out.println(readmeScore + "/5");

        } else {
            readmeScore = CheckerConstants.ZERO_POINTS;
            System.out.println(readmeScore + "/5");
        }
    }

    /**
     * This method is used to calculate total score of the implementation and checkstyle
     */
    public static void calculateScore() throws IOException {
        System.out.println();
        calculateScoreAllTests();
        int checkstyleScore = calculateScoreCheckstyle();
        calculateScoreGit();
        calculateScoreReadme();

        int finalScore = totalScore + gitScore + readmeScore + checkstyleScore;
        System.out.println("-----------------------------------------------------");
        System.out.println("Total: " + finalScore + "/100");

        if (finalScore == CheckerConstants.MAX_POINTS) {
            System.out.println("\nPreafercitul Dani te binecuvanteaza. Acum poti spune ca..."
                    + " https://www.youtube.com/watch?v=rTeObJmb7hQ");
        }
    }

    /**
     * This method is used to calculate the score of checkstyle
     */
    private static int calculateScoreCheckstyle() {
        return Checkstyle.testCheckstyle();
    }

    /**
     * This method is used to calculate score of implementation
     * 18 tests (80 points maximum)
     */
    private static void calculateScoreAllTests() throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        var listFile = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .map(File::getName)
                .toList();
        for (String file : listFile) {
            totalScore += calculateScore(file);
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("Tests score: " + totalScore + "/80");
    }

    /**
     * This method calculates the score of only one single test
     * @return the score of that test
     */
    public static int calculateScore(final String input) {
        if (checkOutput(input)) {
            System.out.print(input + " ");
            for (int i = 1;  i <= CheckerConstants.LEN_LONGEST_TEST_NAME - input.length(); i++) {
                System.out.print("-");
            }
            System.out.println("--------------------------------------------- PASSED (+"
                    + getScoreForTest(input) + ")");
            return getScoreForTest(input);
        } else {
            System.out.print(input + " ");
            for (int i = 1; i <= CheckerConstants.LEN_LONGEST_TEST_NAME - input.length(); i++) {
                System.out.print("-");
            }
            System.out.println("--------------------------------------------- FAILED (+0)");
            return 0;
        }
    }

    /**
     * @param file the test you want to check
     * @return
     *          if the two files are equal or not
     */
    private static boolean checkOutput(final String file) {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Double.class, new DoubleDeserializer());
        mapper.registerModule(simpleModule);

        try {
            JsonNode output = mapper.readTree(new File(CheckerConstants.OUT_PATH + file));
            JsonNode ref = mapper.readTree(new File(CheckerConstants.REF_PATH + file));

            output = roundDecimals(output, CheckerConstants.DECIMAL_POINTS, mapper);
            ref = roundDecimals(ref, CheckerConstants.DECIMAL_POINTS, mapper);

            return output.equals(ref);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static JsonNode roundDecimals(
            final JsonNode node,
            final int precision,
            final ObjectMapper mapper) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<String> fieldNames = objectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                objectNode.set(
                        fieldName,
                        roundDecimals(objectNode.get(fieldName), precision, mapper)
                );
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                arrayNode.set(i, roundDecimals(arrayNode.get(i), precision, mapper));
            }
        } else if (node.isNumber() && node.isFloatingPointNumber()) {
            BigDecimal roundedValue = BigDecimal.valueOf(node.asDouble())
                    .setScale(precision, RoundingMode.HALF_UP);
            return mapper.getNodeFactory().numberNode(roundedValue);
        } else if (node.isTextual() && Pattern.matches(
                CheckerConstants.DECIMALS_REGEX, node.asText())) {
            String[] words = node.asText().split(" ");

            BigDecimal roundedValue = BigDecimal.valueOf(Double.parseDouble(words[0]))
                    .setScale(precision, RoundingMode.HALF_UP);
            String actualValue = roundedValue + " " + words[1];

            return mapper.getNodeFactory().textNode(actualValue);
        }
        return node;
    }

    /**
     * @param input the test you want to calculate score for
     * @return  the score of that test
     */
    private static int getScoreForTest(final String input) {
        int value = Integer.parseInt(
                input.replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR)
                        .substring(0, 2)
        );

        if (value >= CheckerConstants.BASIC_START
                && value <= CheckerConstants.BASIC_END) {
            return CheckerConstants.BASIC_POINTS;
        }

        if (value >= CheckerConstants.FUNCTIONAL_START
                && value <= CheckerConstants.FUNCTIONAL_END) {
            return CheckerConstants.FUNCTIONAL_POINTS;
        }

        if (value >= CheckerConstants.FLOW_START
                && value <= CheckerConstants.FLOW_END) {
            return CheckerConstants.FLOW_POINTS;
        }

        if (value >= CheckerConstants.BIG_START
                && value <= CheckerConstants.BIG_END) {
            return CheckerConstants.BIG_POINTS;
        }

        return CheckerConstants.ZERO_POINTS;
    }
}