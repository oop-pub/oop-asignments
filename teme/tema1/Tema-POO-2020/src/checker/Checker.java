package checker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Checker to verify the correctness of the tests.
 */
public final class Checker {
    /**.
     * DO NOT MODIFY
     * @param directory The name of the output directory.
     */
    public void deleteFiles(final File[] directory) {
        if (directory != null) {
            for (File file : directory) {
                if (!file.delete()) {
                    System.out.println("nu s-a sters");
                }
            }
        }
    }

    /**
     *
     * @param file The file from which the data will be read
     * @return A list of queryTest objects
     * @throws IOException in case of exceptions to reading / writing
     */
    public List<QueryTest> createQueries(final File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<List<QueryTest>>() {
        });
    }

    /**
     *
     * @param queryTests1 first query test
     * @param queryTests2 second query test
     * @return true if the queries have the same values
     */
    public boolean compareQueries(final List<QueryTest> queryTests1,
                                  final List<QueryTest> queryTests2) {
        if (queryTests1.size() != queryTests2.size()) {
            return false;
        }

        for (int i = 0; i < queryTests1.size(); i++) {
            if (!queryTests1.get(i).equals(queryTests2.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Displays the score obtained after running the tests
     * @param dirOutPath output directory path
     * @param dirRefPath references directory path
     * @param inputPath input directory path
     * @throws IOException in case of exceptions to reading / writing
     */
    public void iterateFiles(final String dirOutPath, final String dirRefPath,
                             final String inputPath) throws IOException {
        int score = 0;
        int length;

        File dirOut = new File(dirOutPath);
        File dirRef = new File(dirRefPath);
        File input = new File(inputPath);

        File[] directoryList = dirOut.listFiles();
        File[] directoryRef = dirRef.listFiles();
        File[] inputDir = input.listFiles();

        List<List<QueryTest>> listRefs = new ArrayList<>();
        List<List<QueryTest>> listOut = new ArrayList<>();

        if (directoryList != null) {
            Arrays.sort(directoryList);
            for (File file : directoryList) {
                listOut.add(createQueries(file));
            }
        }

        if (directoryRef != null) {
            Arrays.sort(directoryRef);
            for (File file : directoryRef) {
                listRefs.add(createQueries(file));
            }
        }

        if (listOut.size() == listRefs.size() && inputDir != null) {
            Arrays.sort(inputDir);
            for (int i = 0; i < listOut.size(); i++) {

                length = Constants.MAX_LENGTH - inputDir[i].getName().length();
                if (compareQueries(listRefs.get(i), listOut.get(i))) {

                    char[] chars = new char[length];
                    Arrays.fill(chars, ' ');
                    String s = new String(chars);

                    if (inputDir[i].getName().contains(Constants.LARGE)
                            || inputDir[i].getName().contains(Constants.NO_VALUES)) {
                        System.out.println(inputDir[i].getName()
                                + s
                                + "PASSED (+"
                                + Constants.LARGE_TEST
                                + ")");
                        score += Constants.LARGE_TEST;
                    } else {
                        System.out.println(inputDir[i].getName()
                                + s
                                + "PASSED (+"
                                + Constants.SINGLE_TEST + ")");
                        score += Constants.SINGLE_TEST;
                    }
                } else {
                    char[] chars = new char[length];
                    Arrays.fill(chars, ' ');
                    String s = new String(chars);
                    System.out.println(inputDir[i].getName()
                            + s
                            + "FAILED (+0)");
                }
            }
        }

        System.out.println("-----------------------------");
        System.out.println("TOTAL = " + score + "/80");
    }


}
