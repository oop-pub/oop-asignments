package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }
    public double average() {
        double sum = 0.0;
        for (int i = 0, seasonsSize = seasons.size(); i < seasonsSize; i++) {
            Season season = seasons.get(i);
            sum = sum + season.average();
        }
        return sum / numberOfSeasons;
    }
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    /**
     * This method computes the duration of
     * a serial by adding all seasons' durations
     * @return the full duration of a serial
     */
    public int serialDuration() {
        int duration = 0;

        for (Season season : getSeasons()) {
            duration = duration+ season.getDuration();
        }

        return duration;
    }
    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
