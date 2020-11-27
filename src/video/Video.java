package video;
import actor.Actor;
import actor.Actors;
import users.User;
import users.Users;

import java.util.List;

public abstract class Video {

    protected String title;
    protected int releaseYear;
    protected List<String> genres;
    protected double ratingVal;
    protected int ratingNum;
    protected List<String> cast;
    protected int noViews;
    protected int isFav;
    protected String label;
    protected int favorite;
    protected int duration;

    public Video(final String title, final int releaseYear,
                 final List<String> genres, final List<String> cast) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genres = genres;
        ratingVal = 0;
        ratingNum = 0;
        favorite = 0;
        this.cast = cast;
        getDefaultViews();
        noViews = 0;
    }

    /**
     * Returns the rating of a video
     * @return
     */
    public double getRating() {
        return ratingNum == 0 ? 0 : ratingVal / ratingNum;
    }

    /**
     * Modifies the rating of the videos actors
     * @param rating
     * @param oldRating
     */
    protected void modifyActorRating(final double rating, final double oldRating) {
        for (String actor:cast) {
            if (rating != 0) {
                Actor act = Actors.getInstance().get(actor);
                if (act != null) {
                    act.modifyRating(rating, oldRating);
                }
            }
        }
    }

    /**
     * Calculates the default number of views
     */
    private void getDefaultViews() {
        for (String name:cast) {
            User user = Users.getInstance().get(name);
            if (user != null) {
                if (user.getViewed().get(title) != null) {
                    noViews += user.getViewed().get(title);
                }
            }
        }
    }

    /**
     * Returns the numbers of views of the video
     * @return
     */
    public int getViews() {
        return noViews;
    }

    /**
     * Returns the title of the video
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Adds a new rating to a video. If the video is a movie, numberOfSeason is 0.
     * @param rating
     * @param numberOfSeason
     */

    public abstract void addRating(double rating, int numberOfSeason);

    /**
     * Returns the type of the video (movie or show).
     * @return
     */
    public String getType() {
        return label;
    }

    /**
     * Returns the release year
     * @return
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Checks wether the video contains the given genre
     * @param genre
     * @return
     */

    public boolean hasGenre(final String genre) {
        for (String gnr : genres) {
            if (genre.compareTo(gnr) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increments the number of appearances in users favorite lists
     */
    public void incrementFavorite() {
        favorite += 1;
    }

    /**
     * Get the number of appearances in users favorite lists
     * @return
     */
    public int getFavorite() {
        return favorite;
    }

    /**
     * Returns the duration of a video
     * @return
     */
    public int getDuration() {
        return duration;
    };

    /**
     * Increments the number of total views by 1
     */
    public void incrementViews() {
        noViews += 1;
    }

    /**
     * Increments the number of total views by n
     * @param
     */
    public void incrementViews(final int n) {
        noViews += n;
    }

    /**
     * Returns the title of the video
     * @return
     */
    @Override
    public String toString() {
        return title;
    }
}
