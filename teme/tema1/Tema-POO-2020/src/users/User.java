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
     *
     * @return
     */
    public String getsubscriptionType() {
        return subscriptionType;
    }

    /**
     *
     * @param subscription
     */
    public void setsubscriptionType(final String subscription) {
        subscriptionType = subscription;
    }

    /**
     *
     * @param title
     */
    public void addFavorite(final String title) {
        Videos.getInstance().get(title).incrementFavorite();
        favoriteVideos.add(title);
    }

    /**
     *
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
     *
     * @param title
     * @param noSeason
     * @return
     */
    public boolean checkRated(final String title, final int noSeason) {
        return ratings.containsKey(title + "@" + noSeason);
    }

    /**
     *
     * @param title
     * @return
     */
    public boolean checkViewed(final String title) {
        return viewedVideos.containsKey(title);
    }

    /**
     *
     * @param title
     * @return
     */
    public boolean checkFavorite(final String title) {
        return favoriteVideos.contains(title);
    }

    /**
     *
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
     *
     */
    public void purge() {
        viewedVideos.clear();
        ratings.clear();
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> getViewed() {
        return viewedVideos;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return ratingNum != 0;
    }

    /**
     *
     * @return
     */
    public int getGivenRatingsNum() {
        return ratingNum;
    }

    /**
     *
     */
    public void addViewsToVideos() {
        for (Map.Entry<String, Integer> entry : viewedVideos.entrySet()) {
            Videos.getInstance().get(entry.getKey()).incrementViews(entry.getValue());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return username;
    }
}
