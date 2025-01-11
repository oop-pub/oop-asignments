package org.poo.checker;

/**
 * The class contains the minimum of constants needed.
 * You can define your own constants here or create separate files.
 */
public final class CheckerConstants {
    private CheckerConstants() {
    }

    // checker constants
    public static final Integer BIG_TEST_POINTS = 3;
    public static final Integer MAXIMUM_ERROR_CHECKSTYLE = 30;
    public static final Integer CHECKSTYLE_POINTS = 10;

    public static final String CHECKER_PATH = "src/main/resources";
    public static final String TESTS_PATH = "input/";
    public static final String OUT_PATH = "result/out_";
    public static final String REF_PATH = "ref/ref_";
    public static final String RESULT_PATH = "result";
    public static final String OUT_FILE = "out.txt";

    public static final String EMPTY_STR = "";
    public static final String DIGIT_REGEX = "\\D+";
    public static final String DECIMALS_REGEX = "[0-9]+(\\.[0-9]+)? [A-Z]{3}";

    public static final int DECIMAL_POINTS = 2;

    public static final int BASIC_START = 1;
    public static final int BASIC_END = 6;
    public static final int BASIC_POINTS = 2;

    public static final int FUNCTIONAL_START = 7;
    public static final int FUNCTIONAL_END = 10;
    public static final int FUNCTIONAL_POINTS = 3;

    public static final int FLOW_START = 11;
    public static final int FLOW_END = 17;
    public static final int FLOW_POINTS = 5;

    public static final int BIG_START = 18;
    public static final int BIG_END = 20;
    public static final int BIG_POINTS = 7;

    public static final int MAX_POINTS = 100;
    public static final int FIVE_POINTS = 5;
    public static final int ZERO_POINTS = 0;
    public static final int LEN_LONGEST_TEST_NAME = 40;
}