package solvers;

import fileio.ActionInputData;
import users.User;
import users.Users;
import video.Video;
import video.Videos;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UserQRSolver {

    /**
     *
     * @param action
     * @return
     */
    public static String solve(final ActionInputData action) {

        Stream<User>  unorderedUsers = Users.getInstance().getAll().stream()
                                        .filter(user -> user.isActive());

        List<User> filteredUsers = null;
        if (action.getSortType().compareTo("desc") == 0) {
            filteredUsers = unorderedUsers
                    .sorted(Comparator.comparing(User::getGivenRatingsNum)
                    .thenComparing(User::getUsername).reversed())
                    .limit(action.getNumber())
                    .collect(Collectors.toList());
        } else {
            filteredUsers = unorderedUsers
                    .sorted(Comparator.comparing(User::getGivenRatingsNum)
                    .thenComparing(User::getUsername))
                    .limit(action.getNumber())
                    .collect(Collectors.toList());
        }

        return String.format("Query result: %s", filteredUsers);
    }

    /**
     *
     * @param action
     * @return
     */
    public static String recommend(final ActionInputData action) {

        User  user = Users.getInstance().get(action.getUsername());
        if ("standard".equals(action.getType())) {
            for (Video video : Videos.getInstance().getAll()) {
                if (!user.checkViewed(video.getTitle())) {
                    return "StandardRecommendation result: " + video.getTitle();
                }
            }
            return "StandardRecommendation cannot be applied!";
        } else if ("best_unseen".equals(action.getType())) {
            String result = "";
            double rating = -1;
            for (Video video : Videos.getInstance().getAll()) {
                double videoRating = video.getRating();
                if (!user.checkViewed(video.getTitle()) && videoRating > rating) {
                    rating = videoRating;
                    result = video.getTitle();
                }
            }
            if (result.equals("")) {
                return "BestRatedUnseenRecommendation cannot be applied!";
            }
            return "BestRatedUnseenRecommendation result: " + result;
        } else if ("favorite".equals(action.getType())) {
            if (user.getsubscriptionType().equals("BASIC")) {
                return "FavoriteRecommendation cannot be applied!";
            }
            String result = "";
            double favNum = -1;
            for (Video video : Videos.getInstance().getAll()) {
                if (!user.checkViewed(video.getTitle())) {
                    double videoFavNum = video.getFavorite();
                    if (videoFavNum > favNum) {
                        favNum = videoFavNum;
                        result = video.getTitle();
                    }
                }
            }
            if (result.equals("")) {
                return "FavoriteRecommendation cannot be applied!";
            }
            return "FavoriteRecommendation result: " + result;
        } else if ("search".equals(action.getType())) {
            if (user.getsubscriptionType().equals("BASIC")) {
                return "SearchRecommendation cannot be applied!";
            }
            List<Video> filteredMovies = Videos.getInstance().getAll().stream()
                    .filter(video -> !user.checkViewed(video.getTitle()))
                    .filter(video -> video.hasGenre(action.getGenre()))
                    .sorted(Comparator.comparing(Video::getRating)
                            .thenComparing(Video::getTitle))
                    .collect(Collectors.toList());
            if (filteredMovies.isEmpty()) {
                return "SearchRecommendation cannot be applied!";
            }
            return String.format("SearchRecommendation result: %s", filteredMovies);
        } else if ("popular".equals(action.getType())) {
            if (user.getsubscriptionType().equals("BASIC")) {
                return "PopularRecommendation cannot be applied!";
            }

            List<Map.Entry<String, Integer>> genreEntry = new ArrayList<>(
                    Videos.getInstance().getPopular().entrySet()
            );

            genreEntry.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

            for (Map.Entry<String, Integer> entry : genreEntry) {

                List<Video> filteredVideos = Videos.getInstance().getAll().stream()
                        .filter(video -> !user.checkViewed(video.getTitle()))
                        .filter(video -> video.hasGenre(entry.getKey()))
                        .collect(Collectors.toList());

                if (filteredVideos.isEmpty()) {
                    continue;
                }

                return String.format("PopularRecommendation result: %s", filteredVideos.get(0));
            }
            return "PopularRecommendation cannot be applied!";
        }
        return "";
    }
}
