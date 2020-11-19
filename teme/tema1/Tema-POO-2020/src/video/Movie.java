package video;

import fileio.MovieInputData;

import java.util.List;

public final class Movie extends Video {

    public Movie(final String title, final int releaseYear, final List<String> genres,
                 final int duration, final List<String> cast) {
        super(title, releaseYear, genres, cast);
        this.duration = duration;
        this.label = "MOVIE";

    }

    public Movie(final MovieInputData movie) {
        this(movie.getTitle(), movie.getYear(),
                movie.getGenres(), movie.getDuration(), movie.getCast());
    }

    /**
     *
     * @param rating
     * @param numberOfSeason
     */
    public void addRating(final double rating, final int numberOfSeason) {
        double oldRating = ratingNum == 0 ? 0 : ratingVal / ratingNum;
        ratingVal += rating;
        ratingNum += 1;
        modifyActorRating(ratingVal / ratingNum, oldRating);
    }


}
