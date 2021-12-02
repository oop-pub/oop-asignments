package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.List;

public class Rating {
    /**
     * @param u, input, action for addRating
     * @return String
     */
    public String addRating(final Input inp, final UserInputData u,
                            final ActionInputData action) {
        List<UserInputData> users;
        List<SerialInputData> serial = inp.getSerials();
        users = inp.getUsers();
        for (int i = 0, usersSize = users.size(); i < usersSize; i++) {
            UserInputData user = users.get(i);
            if (user.getUsername().equals(action.getUsername())
                    && !user.getHistory().containsKey(action.getTitle())) {
                return "error -> " + action.getTitle() + " is not seen";
            }
        }
        var s = "success -> " + action.getTitle() + " was rated with "
                + action.getGrade() + " by " + action.getUsername();
        List<MovieInputData> movies = inp.getMovies();
        for (int i = 0, moviesSize = movies.size(); i < moviesSize; i++) {
            MovieInputData movie = movies.get(i);
            if (!action.getTitle().equals(movie.getTitle())) {
                if (u.getRatedMovies().contains(action.getTitle()))
                    return "error -> " + action.getTitle() + " has been already rated";
            } else {
                movie.getRating().add(action.getGrade());
                u.getRatedMovies().add(action.getTitle());
                return s;
            }
        }
        for (int i = 0; i < serial.size(); i++) {
            SerialInputData series = serial.get(i);
            if (!series.getTitle().equals(action.getTitle())) {
                continue;
            }
            series.getSeasons().get(action.getSeasonNumber() - 1).
                    getRatings().add(action.getGrade());
            u.getRatedMovies().add(action.getTitle() + action.getSeasonNumber());
            return s;
        }
        return "ok";}
}
