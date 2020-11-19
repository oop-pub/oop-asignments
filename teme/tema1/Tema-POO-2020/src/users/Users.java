package users;

import container.Container;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Users implements Container {
    private final Map<String, User> username_map;
    private final List<User> users;
    private static Users instance = null;


    private Users() {
        username_map = new HashMap<String, User>();
        users = new LinkedList<User>();
    }

    public static Users getInstance() {

        if(instance == null) {
            synchronized (Users.class) {
                if(instance == null) {
                    instance = new Users();
                }
            }
        }
        return instance;
    }



    public void add(Object user) {
        username_map.put(((User)user).getUsername(), (User)user);
        users.add((User)user);

    }

    public User get(String username) {
        return username_map.get(username);
    }

    public List<User> getAll() {
        return users;
    }

    public void purge() {
        users.clear();
        username_map.clear();
    }
}
