package strategies;

public final class GreenDetector {
    public static boolean check(String type) {
        return type.equals("WIND") || type.equals("SOLAR")
                || type.equals("HYDRO");
    }
}
