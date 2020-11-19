package video;

public final class Rating {

    private final double rating;
    private final int season;

    public Rating(final double rating, final int season) {
        this.rating = rating;
        this.season = season;
    }

    public double getRating() {
        return rating;
    }

    public int getSeason() {
        return season;
    }
}
