package video;

import actor.Actor;
import actor.Actors;
import entertainment.Genre;
import fileio.MovieInputData;

import java.util.List;

public class Movie extends Video {

    private int ratingNum;

    public Movie(String title, int release_year, List<String> genres, int duration, List<String> cast, double rating) {
        super(title, release_year, genres, cast);
        this.duration = duration;
        this.label = "MOVIE";
        ratingNum  = 0;

    }

    public Movie(MovieInputData movie) {
        this(movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getDuration(), movie.getCast(), 0);
    }

    public void addRating(double rating, int numberOfSeason) {
        double aux = this.rating * ratingNum;
        double oldRating = this.rating;
        ratingNum += 1;
        this.rating = (aux + rating) / ratingNum;
        modifyActorRating(this.rating, oldRating);
    }


}
