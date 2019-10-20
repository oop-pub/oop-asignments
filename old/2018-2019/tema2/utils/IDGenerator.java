package utils;

/**
 * This class generates a unique id for new files, directories and commits.
 *
 */
public final class IDGenerator {
    private static int currentFileIDInUse = 0;
    private static int currentCommitIDInUse = 1;

    private IDGenerator() {
    }

    /**
     * Generates a new file id.
     *
     * @return a new file id
     */
    public static int generateFileID() {
        IDGenerator.currentFileIDInUse += 2;
        return IDGenerator.currentFileIDInUse;
    }

    /**
     * Generates a new commit id.
     *
     * @return a new commit id
     */
    public static int generateCommitID() {
        IDGenerator.currentCommitIDInUse += 2;
        return IDGenerator.currentCommitIDInUse;
    }
}
