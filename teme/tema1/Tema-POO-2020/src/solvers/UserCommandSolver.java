package solvers;

import fileio.ActionInputData;
import users.User;
import users.Users;
import video.*;

public final class UserCommandSolver {

    public static String solve(ActionInputData action) {
        User user = Users.getInstance()
                .get(action.getUsername());

        String result = "";

        if(user != null) {
            switch (action.getType()) {
                case "favorite":
                    if(user.checkViewed(action.getTitle())) {
                        if(!user.checkFavorite(action.getTitle())) {
                            user.addFavorite(action.getTitle());
                            result = String.format("success -> %s was added as favourite", action.getTitle());
                        } else {
                            result = String.format("error -> %s is already in favourite list", action.getTitle());
                        }
                    } else {
                        result = String.format("error -> %s is not seen", action.getTitle());
                    }
                    break;
                case "view":
                    int views = user.view(action.getTitle());
                    result = String.format("success -> %s was viewed with total views of %d", action.getTitle(), views);
                    break;
                case "rating":

                    if (user.checkViewed(action.getTitle())) {
                        if (!user.checkRated(action.getTitle(), action.getSeasonNumber())) {
                            user.rate(action.getTitle(), new Rating(
                                    action.getGrade(), action.getSeasonNumber()
                            ));
                            result = String.format("success -> %s was rated with %.1f by %s", action.getTitle(),
                                    action.getGrade(), user.getUsername()
                            );
                        } else {
                            result = String.format("error -> %s has been already rated", action.getTitle());
                        }
                    } else {
                        result = String.format("error -> %s is not seen", action.getTitle());
                    }

                    break;
            }

        }
        return result;
    }
}
