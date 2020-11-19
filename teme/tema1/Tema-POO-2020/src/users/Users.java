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
     *
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
     *
     * @param user
     */
    public void add(final Object user) {
        usernameMap.put(((User) user).getUsername(), (User) user);
        users.add((User) user);

    }

    /**
     *
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
     *
     */
    public void purge() {
        users.clear();
        usernameMap.clear();
    }
}
