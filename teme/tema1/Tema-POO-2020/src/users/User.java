package users;

import actor.Actors;
import fileio.MovieInputData;
import fileio.UserInputData;
import video.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String subscription_type;
    private final List<String> favoriteVideos;
    private final Map<String, Integer> viewedVideos;
    private final Map<String, Rating> ratings;
    private int ratingNum;

    public User(String username, String subscription_type, List<String> favoriteVideos, Map<String, Integer> viewedVideos) {
        this.username = username;
        this.subscription_type = subscription_type;
        this.favoriteVideos = favoriteVideos;
        for(String video : favoriteVideos) {
            Video vid = Videos.getInstance().get(video);
            if(vid != null) {
                vid.incrementFavorite();
            }
        }
        this.viewedVideos = viewedVideos;
        addViewsToVideos();
        ratingNum = 0;
        ratings = new HashMap<String, Rating>();
    }

    public User(UserInputData user) {
        this(user.getUsername(), user.getSubscriptionType(), user.getFavoriteMovies(), user.getHistory());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public void addFavorite(String title) {
        Videos.getInstance().get(title).incrementFavorite();
        favoriteVideos.add(title);
    }

    public int view(String title) {
        Integer num_view = viewedVideos.getOrDefault(title, 0);
        Videos videos = Videos.getInstance();
        Video video = videos.get(title);
        video.incrementViews();
        videos.updateGenre(video);
        viewedVideos.put(title, num_view + 1);
        return num_view + 1;
    }

    public boolean checkRated(String title, int noSeason) {
        return ratings.containsKey(title + "@" + noSeason);
    }

    public boolean checkViewed(String title) {
        return viewedVideos.containsKey(title);
    }

    public boolean checkFavorite(String title) {
        return favoriteVideos.contains(title);
    }

    public void rate(String title, Rating rating) {

        Video video = Videos.getInstance().get(title);
        ratingNum += 1;
        if(video != null) {
            video.addRating(rating.getRating(), rating.getSeason());
            ratings.put(title + "@" + rating.getSeason(), rating);
        }
    }

    public void purge() {
        viewedVideos.clear();
        ratings.clear();
    }

    public Map<String, Integer> getViewed() {
        return viewedVideos;
    }

    public boolean isActive() {
        return ratingNum != 0;
    }
    public int getGivenRatingsNum() {
        return ratingNum;
    }

    public void addViewsToVideos() {
        for (Map.Entry<String, Integer> entry : viewedVideos.entrySet()) {
            Videos.getInstance().get(entry.getKey()).incrementViews(entry.getValue());
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
