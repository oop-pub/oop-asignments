package strategies;

public final class GreenDetector {
    private GreenDetector() { }

    /**
     * Checks is the given argument is
     * a type of green energy
     * @param type
     * @return
     */
    public static boolean check(String type) {
        return type.equals("WIND") || type.equals("SOLAR")
                || type.equals("HYDRO");
    }
}
