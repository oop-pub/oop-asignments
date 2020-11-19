package solvers;

import fileio.ActionInputData;
import users.User;
import users.Users;
import video.Video;
import video.Videos;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UserQRSolver {
    public static String solve(ActionInputData action) {

        Stream<User>  unorderedUsers = Users.getInstance().getAll().stream()
                                        .filter(user -> user.isActive());

        List<User> filteredUsers = null;
        if(action.getSortType().compareTo("desc") == 0) {
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

    public static String recommend(ActionInputData action) {

        User  user = Users.getInstance().get(action.getUsername());
        switch (action.getType()) {
            case "standard":
                for (Video video : Videos.getInstance().getAll()) {
                    if (!user.checkViewed(video.getTitle())) {
                        return "StandardRecommendation result: " + video.getTitle();
                    }
                }
                return "StandardRecommendation cannot be applied!";
            case "best_unseen": {
                String result = "";
                double rating = -1;
                for (Video video : Videos.getInstance().getAll()) {
                    double videoRating = video.getRating();
                    if (!user.checkViewed(video.getTitle()) && videoRating > rating) {
                        rating = videoRating;
                        result = video.getTitle();
                    }
                }
                if(result.equals("")) {
                    return "BestRatedUnseenRecommendation cannot be applied!";
                }
                return "BestRatedUnseenRecommendation result: " + result;
            }
            case "favorite": {
                if (user.getSubscription_type().equals("BASIC")) {
                    return "FavoriteRecommendation cannot be applied!";
                }
                String result = "";
                double favNum = -1;
                for (Video video : Videos.getInstance().getAll()) {
                    if(!user.checkViewed(video.getTitle())) {
                        double videoFavNum = video.getFavorite();
                        if (videoFavNum > favNum) {
                            favNum = videoFavNum;
                            result = video.getTitle();
                        }
                    }
                }
                if(result.equals("")) {
                    return "FavoriteRecommendation cannot be applied!";
                }
                return "FavoriteRecommendation result: " + result;
            }
            case "search":
                if (user.getSubscription_type().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                }
                List<Video> filteredMovies = Videos.getInstance().getAll().stream()
                        .filter(video -> !user.checkViewed(video.getTitle()))
                        .filter(video -> video.hasGenre(action.getGenre()))
                        .sorted(Comparator.comparing(Video::getRating)
                                .thenComparing(Video::getTitle))
                        .collect(Collectors.toList());
                if(filteredMovies.isEmpty()) {
                    return "SearchRecommendation cannot be applied!";
                }
                return String.format("SearchRecommendation result: %s", filteredMovies);
            case "popular":
                if (user.getSubscription_type().equals("BASIC")) {
                    return "PopularRecommendation cannot be applied!";
                }
                for(Map.Entry<String, Integer> entry : Videos.getInstance().getPopular().entrySet()) {
                    List<Video> filteredVideos = Videos.getInstance().getAll().stream()
                                        .filter(video -> video.hasGenre(entry.getKey()))
                                        .filter(video -> !user.checkViewed(video.getTitle()))
                                        .collect(Collectors.toList());
                    if(filteredVideos.isEmpty()) {
                        continue;
                    }
                    return String.format("PopularRecommendation result: %s", filteredVideos.get(0));
                }
                return "PopularRecommendation cannot be applied!";
        }
        return "";
    }
}
