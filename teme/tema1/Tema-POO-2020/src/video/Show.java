package video;

import actor.Actors;
import entertainment.Genre;
import entertainment.Season;
import fileio.SerialInputData;

import java.util.List;
import java.util.Map;

public class Show extends Video {
    private int numberOfSeasons;
    private final double[] seasonAddedRatings;
    private final int[] seasonNumberRatings;
    private int rating;

    public Show(String title, int release_year, List<String> genres, List<String> cast, int numberOfSeasons, List<Season> seasons) {
        super(title, release_year, genres, cast);
        this.numberOfSeasons = numberOfSeasons;
        seasonNumberRatings = new int[1000];
        seasonAddedRatings = new double[1000];
        rating = 0;
        this.label = "SHOW";
        int count = 0;
        for(Season season : seasons) {
            seasonNumberRatings[count] = season.getRatings().size();
            for(double rating : season.getRatings()) {
                seasonAddedRatings[count] += rating;

            }
            this.rating += seasonAddedRatings[count] / seasonNumberRatings[count];
            this.duration += season.getDuration();
            count += 1;
        }
    }

    public Show(SerialInputData tvshow) {
       this(tvshow.getTitle(), tvshow.getYear(), tvshow.getGenres(), tvshow.getCast(), tvshow.getNumberSeason(), tvshow.getSeasons());
    }

    public void addRating(double rating, int numberOfSeason) {
        double oldRating = this.rating;
        this.rating -= seasonAddedRatings[numberOfSeason] / seasonNumberRatings[numberOfSeason];
        seasonAddedRatings[numberOfSeason] += rating;
        seasonNumberRatings[numberOfSeason] += 1;

        this.rating += seasonAddedRatings[numberOfSeason] / seasonNumberRatings[numberOfSeason];
        modifyActorRating(this.rating, oldRating);
    }


}
