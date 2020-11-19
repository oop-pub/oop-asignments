package video;
import actor.Actor;
import actor.Actors;
import entertainment.Genre;
import users.User;
import users.Users;

import java.util.List;

public abstract class Video {

    protected String title;
    protected int release_year;
    protected List<String> genres;
    protected double ratingVal;
    protected int ratingNum;
    protected List<String> cast;
    protected int noViews;
    protected int isFav;
    protected String label;
    protected int favorite;
    protected int duration;

    public Video(String title, int release_year, List<String> genres, List<String> cast) {
        this.title = title;
        this.release_year = release_year;
        this.genres = genres;
        ratingVal = 0;
        ratingNum = 0;
        favorite = 0;
        this.cast = cast;
        getDefaultViews();
        noViews = 0;
    }

    public double getRating() {
        return ratingNum == 0 ? 0 : ratingVal / ratingNum;
    }

    protected void modifyActorRating(double rating, double oldRating) {
        for(String actor:cast) {
            if(rating != 0) {
                Actor act = Actors.getInstance().get(actor);
                if(act != null) {
                    act.modifyRating(rating, oldRating);
                }
            }
        }
    }

    private void getDefaultViews() {
        for(String name:cast) {
            User user = Users.getInstance().get(name);
            if(user != null) {
                if(user.getViewed().get(title) != null) {
                    noViews += user.getViewed().get(title);
                };
            }
        }
    }

    public int getViews() {
        return noViews;
    }

    public String getTitle() {
        return title;
    }

    public abstract void addRating(double rating, int numberOfSeason);

    public String getType() {
        return label;
    }

    public int getReleaseYear() {
        return release_year;
    }

    public boolean hasGenre(String genre) {
        for(String gnr : genres) {
            if(genre.compareTo(gnr) == 0) {
                return true;
            }
        }
        return false;
    }

    public void incrementFavorite() {
        favorite += 1;
    }

    public int getFavorite() {
        return favorite;
    }

    public int getDuration() {
        return duration;
    };

    public void incrementViews() {
        noViews += 1;
    }

    public void incrementViews(int n) {
        noViews += n;
    }

    @Override
    public String toString() {
        return title;
    }
}