package users;

import fileio.UserInputData;
import video.Video;
import video.Videos;
import video.Rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class User {
    private String username;
    private String subscriptionType;
    private final List<String> favoriteVideos;
    private final Map<String, Integer> viewedVideos;
    private final Map<String, Rating> ratings;
    private int ratingNum;

    public User(final String username, final String subscriptionType,
                final List<String> favoriteVideos,
                final Map<String, Integer> viewedVideos) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteVideos = favoriteVideos;
        for (String video : favoriteVideos) {
            Video vid = Videos.getInstance().get(video);
            if (vid != null) {
                vid.incrementFavorite();
            }
        }
        this.viewedVideos = viewedVideos;
        addViewsToVideos();
        ratingNum = 0;
        ratings = new HashMap<String, Rating>();
    }

    public User(final UserInputData user) {
        this(user.getUsername(), user.getSubscriptionType(),
                user.getFavoriteMovies(), user.getHistory());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the type of subscription of a user
     * @return
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Sets the type of subscription for a user
     * @param subscription
     */
    public void setSubscriptionType(final String subscription) {
        subscriptionType = subscription;
    }

    /**
     * Adds a video to the favorite list
     * @param title
     */
    public void addFavorite(final String title) {
        Videos.getInstance().get(title).incrementFavorite();
        favoriteVideos.add(title);
    }

    /**
     * Views a video. Returns the number of times the user viewed the given video
     * @param title
     * @return
     */
    public int view(final String title) {
        Integer numView = viewedVideos.getOrDefault(title, 0);
        Videos videos = Videos.getInstance();
        Video video = videos.get(title);
        video.incrementViews();
        videos.updateGenre(video);
        viewedVideos.put(title, numView + 1);
        return numView + 1;
    }

    /**
     * Checks if a video is already rated by the user
     * @param title
     * @param noSeason
     * @return
     */
    public boolean checkRated(final String title, final int noSeason) {
        return ratings.containsKey(title + "@" + noSeason);
    }

    /**
     * Check is a video is already seen by the user
     * @param title
     * @return
     */
    public boolean checkViewed(final String title) {
        return viewedVideos.containsKey(title);
    }

    /**
     * Check if a video is already in the favorite list
     * @param title
     * @return
     */
    public boolean checkFavorite(final String title) {
        return favoriteVideos.contains(title);
    }

    /**
     * Rates a video
     * @param title
     * @param rating
     */
    public void rate(final String title, final Rating rating) {

        Video video = Videos.getInstance().get(title);
        ratingNum += 1;
        if (video != null) {
            video.addRating(rating.getRating(), rating.getSeason());
            ratings.put(title + "@" + rating.getSeason(), rating);
        }
    }

    /**
     * Clears the data retained by a user
     */
    public void purge() {
        viewedVideos.clear();
        ratings.clear();
    }

    /**
     * Returns the videos that were viewed by the user
     * @return
     */
    public Map<String, Integer> getViewed() {
        return viewedVideos;
    }

    /**
     * Checks whether the user is active
     * @return
     */
    public boolean isActive() {
        return ratingNum != 0;
    }

    /**
     * Returns the number of the given ratings
     * @return
     */
    public int getGivenRatingsNum() {
        return ratingNum;
    }

    /**
     * Increments the number of views of the default viewed videos
     */
    public void addViewsToVideos() {
        for (Map.Entry<String, Integer> entry : viewedVideos.entrySet()) {
            Videos.getInstance().get(entry.getKey()).incrementViews(entry.getValue());
        }
    }

    /**
     * Returns the username
     * @return
     */
    @Override
    public String toString() {
        return username;
    }
}
