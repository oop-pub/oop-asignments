package checker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Checker to verify the coding style
 */
public final class Checkstyle {
    public static final String JAR_PATH = "src/checker/checkstyle-8.36.2-all.jar";
    public static final String XML_PATH = "src/checker/poo_checks.xml";
    public static final String CHECKSTYLE_FILE = "checkstyle.txt";
    public static final int MIN_LINES = 2;
    public static final int NUM_CHECK_INFO = 3;
    public static final int MIN_CHECKSTYLE_ERR = 30;

    private Checkstyle() {
    }

    /**
     * DO NOT MODIFY
     */
    public static boolean testCheckstyle() {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar",
                JAR_PATH, "-c", XML_PATH, "./");

        processBuilder.redirectErrorStream(true);
        File log = new File(CHECKSTYLE_FILE);
        processBuilder.redirectOutput(log);

        try {
            Process process = processBuilder.start();
            process.waitFor();

            Path path = Paths.get(CHECKSTYLE_FILE);
            long lineCount = Files.lines(path).count();

            long errors = 0;
            if (lineCount > MIN_LINES) {
                errors = lineCount - NUM_CHECK_INFO;
            }

            boolean checkstylePassed = errors <= MIN_CHECKSTYLE_ERR;

            System.out.println("-----------------------------");
            System.out.println("Checkstyle: "
                    + (checkstylePassed ? "Ok" : "Failed"));
            System.out.println("Checkstyle errors: " + errors);
            return checkstylePassed;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}