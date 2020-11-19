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
     *
     * @return
     */
    public double getRating() {
        return ratingNum == 0 ? 0 : ratingVal / ratingNum;
    }

    /**
     *
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
     *
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
     *
     * @return
     */
    public int getViews() {
        return noViews;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param rating
     * @param numberOfSeason
     */

    public abstract void addRating(double rating, int numberOfSeason);

    /**
     *
     * @return
     */
    public String getType() {
        return label;
    }

    /**
     *
     * @return
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     *
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
     *
     */
    public void incrementFavorite() {
        favorite += 1;
    }

    /**
     *
     * @return
     */
    public int getFavorite() {
        return favorite;
    }

    /**
     *
     * @return
     */
    public int getDuration() {
        return duration;
    };

    /**
     *
     */
    public void incrementViews() {
        noViews += 1;
    }

    /**
     *
     * @param
     */
    public void incrementViews(final int n) {
        noViews += n;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return title;
    }
}
