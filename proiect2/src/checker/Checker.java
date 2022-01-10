package checker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Checker {

    private static final int TEST_NUMBER = 30;
    private static final int SMALL_TEST_START = 1;
    private static final int SMALL_TEST_END = 12;
    private static final int SMALL_TEST_SCORE = 1;
    private static final int MEDIUM_TEST_START = 13;
    private static final int MEDIUM_TEST_END = 19;
    private static final int MEDIUM_TEST_SCORE = 2;
    private static final int LARGE_TEST_START = 20;
    private static final int LARGE_TEST_END = 29;
    private static final int LARGE_TEST_SCORE = 3;
    private static final int FINAL_TEST_SCORE = 4;
    protected Checker() {

    }

    /**
     * This method is used to calculate total score of the implementation and checkstyle
     */
    public static void calculateScore() {
        calculateScoreAllTests();
        calculateScoreCheckstyle();
    }

    /**
     * This method is used to calculate the score of checkstyle
     *
     * (5 points maximum)
     */
    private static void calculateScoreCheckstyle() {
        Checkstyle.testCheckstyle();
    }

    /**
     * This method is used to calculate score of implementation
     *
     * 25 tests (60 points maximum)
     */
    private static void calculateScoreAllTests() {
        int totalScore = 0;
        for (int i = 1; i <= TEST_NUMBER; i++) {
            totalScore += calculateScore(i);
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("TESTS = " + totalScore + "/60");
    }

    /**
     * This method calculates the score of only one single test
     *
     * It compares the /output/out_{testNumber}.json file with the /ref/ref_test{testNumber}.json
     *
     * @param testNumber
     *          the testNumber you want to calculate score for
     * @return
     *          the score of that test (1 for tests : 1 -12 )
     *          (2 for tests : 13 - 19) (3 for tests : 20 - 29) (4 for test : 30)
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
            JsonNode output = mapper.readTree(new File("output/out_" + testNumber + ".json"));
            JsonNode ref = mapper.readTree(new File("ref/ref_test" + testNumber + ".json"));

            return output.equals(ref);

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
     *      the score of that test (1 for tests : 1 -12 )
     *      (2 for tests : 13 - 19) (3 for tests : 20 - 29) (4 for test : 30)
     */
    private static int getScoreForTest(final Integer testNumber) {

        if (testNumber >= SMALL_TEST_START && testNumber <= SMALL_TEST_END) {
            return SMALL_TEST_SCORE;
        }
        if (testNumber >= MEDIUM_TEST_START && testNumber <= MEDIUM_TEST_END) {
            return MEDIUM_TEST_SCORE;
        }
        if (testNumber >= LARGE_TEST_START && testNumber <= LARGE_TEST_END) {
            return LARGE_TEST_SCORE;
        }
        return FINAL_TEST_SCORE;
    }
}
