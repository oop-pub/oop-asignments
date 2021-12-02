package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

public class View {

    public static int Views(final UserInputData user, final String title) {
        return user.getHistory().get(title);
    }
    /**
     * @param input for input
     * @param action for action
     * @return String
     */
    public static String views(final Input input, final ActionInputData action) {
        var u = input.getUsers();
        for (int i = 0, usersSize = u.size(); i < usersSize; i++) {
            UserInputData user = u.get(i);
            if (!user.getUsername().equals(action.getUsername())) continue;
            var help = new ViewHelp();
            help.addVideoHistory(user, action.getTitle());
            var s = "success -> " + action.getTitle()
                    + " was viewed with total views of " + Views(user, action.getTitle());
            return s;
        }
        return "done";
    }
}
