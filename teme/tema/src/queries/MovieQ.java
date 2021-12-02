package queries;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieQ {
    private String msg;

    public final String getMessage() {
        return msg;
    }

    /**
     * The method gets the movies searched by rating
     * @param action the action that needs to be done
     * @param movieList the list of movies from database
     */
    public void rateMovies(final ActionInputData action,
                              final List<MovieInputData> movieList) {
        Map<String, Double> searchmovies;
        searchmovies = new HashMap<>();

        var list2 = FilterMovie.verFilter(action, movieList);

        list2.stream().filter(movie -> movie.average() != 0).forEachOrdered(movie
                -> searchmovies.put(movie.getTitle(), movie.average()));

        msg = HelperSort.sorting(action, searchmovies);
    }

    /**
     * The method returns the searched query for movies
     * that appear in the favorite list of users
     * @param action the action that needs to be done
     * @param movieList the list of movies from database
     * @param userList the list of users from database
     */
    public void favMoviesQ(final ActionInputData action,
                                final List<MovieInputData> movieList,
                                final List<UserInputData> userList) {
        Map<String, Double> favoriteMovie;
        favoriteMovie = new HashMap<>();

        var list2 = FilterMovie.verFilter(action, movieList);

        for (int i = 0, userListSize = userList.size(); i < userListSize; i++) {
            UserInputData user = userList.get(i);
            ArrayList<String> movies = user.getFavMovies();
            for (int j = 0, moviesSize = movies.size(); j < moviesSize; j++) {
                String fav = movies.get(j);
                list2.stream().filter(movie -> movie.getTitle().equals(fav)).forEach(movie -> {
                    if (!(favoriteMovie.get(movie.getTitle()) != null)) {
                        favoriteMovie.put(movie.getTitle(), 1.0);
                    } else {
                        double count = favoriteMovie.get(movie.getTitle());
                        favoriteMovie.put(movie.getTitle(), count + 1);

                    }
                });
            }
        }

        msg = HelperSort.sorting(action, favoriteMovie);
    }

    /**
     * Method to get the longest movies
     * @param action the action that needs to be done
     * @param movieList the list of movies from database
     */
    public void longestMovie(final ActionInputData action,
                              final List<MovieInputData> movieList) {
        Map<String, Double> longestMovies;
        longestMovies = new HashMap<>();

        var list = FilterMovie.verFilter(action, movieList);

        list.forEach(movie -> longestMovies.put(movie.getTitle(),
                (double) movie.getDuration()));

        msg = HelperSort.sorting(action, longestMovies);

    }

}
