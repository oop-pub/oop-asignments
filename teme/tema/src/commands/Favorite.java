package commands;
import fileio.UserInputData;

import java.util.Collections;

public class Favorite {
    /**
     * for coding style
     */
    public Favorite() {
    }
    /**
     * @param user for user
     * @param movie for movie
     * @return String
     */
    public String favAdd(final UserInputData user, final String movie) {
        String result = null;
        String res = "error -> " + movie + " is not seen";
        if (user.getHistory().containsKey(movie)) {
            for (String title : Collections.unmodifiableList(user.getFavMovies())) {
                if (title.equals(movie)) {
                    res = "error -> " + movie + " is already in favourite list";
                    result = res;
                    break;
                }
            }
            if (result != null) {
                return result;
            }
            boolean add = user.getFavMovies().add(movie);
            res = "success -> " + movie + " was added as favourite";
            result = res;
        } else {
            result = res;
        }

        return result;
    }
    }
