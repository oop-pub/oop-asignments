package checker;

/**
 * The class contains the minimum of constants needed.
 *
 * You can define your own constants here or create separate files.
 */
public final class Constants {



    private Constants() {
    }

    // checker constants
    public static final Integer BIG_TEST_POINTS = 3;
    public static final Integer MAXIMUM_ERROR_CHECKSTYLE = 30;
    public static final Integer CHECKSTYLE_POINTS = 10;
    public static final String TESTS_PATH = "input/";
    public static final String OUT_PATH = "result/out_";
    public static final String REF_PATH = "ref/ref_";
    public static final String RESULT_PATH = "result";
    public static final String OUT_FILE = "out.txt";
    public static final String GAME_START = "game_start";
    public static final String MULTIPLE_GAMES_VALID = "multiple_games_valid";
    public static final String MULTIPLE_GAMES_INVALID = "multiple_games_invalid";
    public static final String BIG_GAME = "big_game";
    public static final Integer GAME_START_POINTS = 3;
    public static final Integer SINGLE_GAME_POINTS = 4;
    public static final Integer MULTIPLE_GAMES_VALID_POINTS = 5;
    public static final Integer MULTIPLE_GAMES_INVALID_POINTS = 6;
    public static final Integer BIG_GAME_POINTS = 10;


    // add any constants you think you may use
    public static final String TANK_ERR = "Another minion must be attacked";
    public static final String FREEZE_ERR = "The minion is frozen";
    public static final String WRONG_COORDINATES = "Invalid coordinates";
    public static final String WRONG_MINION_ERR = "Minion not owned by the right player";
    public static final String ALREADY_USED_ERR = "Ability or attack already used in turn";
    public static final String MANA_ERR = "Not enough mana";
    public static final String FRIEND_ERR = "Can't use ability on a friend";
    public static final String ENEMY_ERR = "Can't use ability on an enemy";
    public static final String NO_ABILITY_ERR = "Minion doesn't have special ability";
    public static final String FULL_LINE_ERR = "Row is full";
    public static final String IS_ENV_CARD_ERR = "Bad type of card";
    public static final String CARD_NOT_IN_HAND = "Card not in hand";
    public static final String BAD_USE_OF_ENV = "Can't place environment in your own field";
}
