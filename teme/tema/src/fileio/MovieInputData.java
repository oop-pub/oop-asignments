package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    private final ArrayList<Double> rating = new ArrayList<>();
    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }
    public ArrayList<Double> getRating() {
        return rating;
    }

    /**
     *
     * @return
     */
    public double average() {
        double sum = 0.0;

        if (getRating().size() == 0) {
            return 0.0;
        }
        for (int i = 0; i < getRating().size(); ++i) {
            sum = sum + getRating().get(i);
        }

        return sum / getRating().size();
    }
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
