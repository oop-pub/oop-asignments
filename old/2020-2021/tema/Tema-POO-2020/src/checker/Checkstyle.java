package checker;

import common.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Checker to verify the coding style
 */
public final class Checkstyle {
    /**
     * DO NOT MODIFY
     */
    public void testCheckstyle() {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar",
                Constants.JAR_PATH, "-c", Constants.XML_PATH, "./");

        processBuilder.redirectErrorStream(true);
        File log = new File(Constants.CHECKSTYLE_FILE);
        processBuilder.redirectOutput(log);

        try {
            Process process = processBuilder.start();
            process.waitFor();

            Path path = Paths.get(Constants.CHECKSTYLE_FILE);
            long lineCount = Files.lines(path).count();

            long errors = 0;
            if (lineCount > Constants.MIN_LINES) {
                errors = lineCount - Constants.NUM_CHECK_INFO;
            }
            System.out.println("-----------------------------");
            System.out.println("Checkstyle: "
                    + ((errors <= Constants.MIN_CHECKSTYLE_ERR) ? "Ok" : "Failed"));
            System.out.println("Checkstyle errors: " + errors);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
