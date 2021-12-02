package commands;
import fileio.UserInputData;
public class ViewHelp {
    public ViewHelp() {

    }
    /**
     * @param user for acces user
     * @param title for title
     */
    public void addVideoHistory(final UserInputData user, final String title) {
        if (!user.getHistory().containsKey(title)) {
            user.getHistory().put(title, 1);
        } else {
            user.getHistory().put(title, user.getHistory().get(title) + 1);
        }
    }

}
