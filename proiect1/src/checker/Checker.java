package checker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;

import java.io.File;
import java.io.IOException;

public final class Checker {

    private Checker() {
        //constructor for checkstyle
    }
    /**
     * This method is used to calculate total score of the implementation and checkstyle
     */
    public static void calculateScore() {

        System.out.println("TOTAL : "
                + (calculateScoreAllTests() + calculateScoreCheckstyle()) + "/70");
        System.out.println("-----------------------------------------------------");
    }

    /**
     * This method is used to calculate the score of checkstyle
     *
     * (5 points maximum)
     */
    private static int calculateScoreCheckstyle() {
        return Checkstyle.testCheckstyle();
    }

    /**
     * This method is used to calculate score of implementation
     *
     * 25 tests (60 points maximum)
     */
    private static int calculateScoreAllTests() {
        int totalScore = 0;
        for (int i = 1; i <= Constants.TESTS_NUMBER; i++) {
            totalScore += calculateScore(i);
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("TESTS = " + totalScore + "/60");
        return totalScore;
    }

    /**
     * This method calculates the score of only one single test
     *
     * It compares the /output/out_{testNumber}.json file with the /ref/ref_test{testNumber}.json
     *
     * @param testNumber
     *          the testNumber you want to calculate score for
     * @return
     *          the score of that test (2 for tests : 1-15) (3 for tests : 16 - 25)
     */
    public static int calculateScore(final Integer testNumber) {
        if (checkOutput(testNumber)) {
            System.out.println("test" + testNumber
                    + ".json ----------------------------- PASSED (+"
                    + getScoreForTest(testNumber) + ")");
            return getScoreForTest(testNumber);
        } else {
            System.out.println("test" + testNumber
                    + ".json  ----------------------------- FAILED (+0)");
            return 0;
        }
    }

    /**
     * It compares the /output/out_{testNumber}.json file with the /ref/ref_test{testNumber}.json
     *
     * @param testNumber
     *          the testNumber you want to calculate score for
     * @return
     *          if the two files are equal or not
     */
    private static boolean checkOutput(final Integer testNumber) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File outputFile = new File(Constants.OUTPUT_PATH + testNumber
                    + Constants.FILE_EXTENSION);
            if (outputFile.exists()) {
                JsonNode output = mapper.readTree(outputFile);
                JsonNode ref = mapper
                        .readTree(new File(Constants.REF_PATH + testNumber
                                + Constants.FILE_EXTENSION));

                return output.equals(ref);
            } else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param testNumber
     *      the testNumber you want to calculate score for
     * @return
     *      the score of that test (2 for tests : 1-15) (3 for tests : 16 - 25)
     */
    private static int getScoreForTest(final Integer testNumber) {
        return (testNumber >= 1 && testNumber <= Constants.TESTS_NUMBER_SMALL)
                ? Constants.SMALL_TEST_POINTS : Constants.BIG_TEST_POINTS;
    }
}
