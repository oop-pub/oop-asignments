package users;

import container.Container;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Users implements Container {
    private final Map<String, User> usernameMap;
    private final List<User> users;
    private static Users instance = null;


    private Users() {
        usernameMap = new HashMap<String, User>();
        users = new LinkedList<User>();
    }

    /**
     * Returns the instance of the class
     * @return
     */
    public static Users getInstance() {

        if (instance == null) {
            synchronized (Users.class) {
                if (instance == null) {
                    instance = new Users();
                }
            }
        }
        return instance;
    }


    /**
     * Adds a new user to the database
     * @param user
     */
    public void add(final Object user) {
        usernameMap.put(((User) user).getUsername(), (User) user);
        users.add((User) user);

    }

    /**
     * Gets a user by its username
     * @param username
     * @return
     */
    public User get(final String username) {
        return usernameMap.get(username);
    }

    public List<User> getAll() {
        return users;
    }

    /**
     * Clears the data
     */
    public void purge() {
        users.clear();
        usernameMap.clear();
    }
}
