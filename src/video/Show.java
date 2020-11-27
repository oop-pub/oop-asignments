package video;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.List;

public final class Show extends Video {
    private int numberOfSeasons;
    private final double[] seasonAddedRatings;
    private final int[] seasonNumberRatings;
    private final int maxSeason = 10000;

    public Show(final String title, final int releaseYear, final List<String> genres,
                final  List<String> cast, final int numberOfSeasons, final List<Season> seasons) {
        super(title, releaseYear, genres, cast);
        this.numberOfSeasons = numberOfSeasons;
        seasonNumberRatings = new int[maxSeason];
        seasonAddedRatings = new double[maxSeason];
        ratingNum = numberOfSeasons;
        this.label = "SHOW";
        int count = 0;
        for (Season season : seasons) {
            this.duration += season.getDuration();
            count += 1;
        }
    }

    public Show(final SerialInputData tvshow) {
       this(tvshow.getTitle(), tvshow.getYear(), tvshow.getGenres(),
               tvshow.getCast(), tvshow.getNumberSeason(), tvshow.getSeasons());
    }

    /**
     * Adds rating to a show type video
     * @param newRating
     * @param numberOfSeason
     */
    public void addRating(final double newRating, final int numberOfSeason) {

        double oldRating = ratingNum == 0 ? 0 : ratingVal / ratingNum;

        if (seasonNumberRatings[numberOfSeason] != 0) {
            ratingVal -= seasonAddedRatings[numberOfSeason] / seasonNumberRatings[numberOfSeason];
        }

        seasonAddedRatings[numberOfSeason] += newRating;
        seasonNumberRatings[numberOfSeason] += 1;

        ratingVal += seasonAddedRatings[numberOfSeason] / seasonNumberRatings[numberOfSeason];
        modifyActorRating(ratingVal / ratingNum, oldRating);
    }


}
