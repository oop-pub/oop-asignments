package actor;

import container.Container;
import users.Users;

import java.util.Map;
import java.util.List;

import java.util.LinkedList;
import java.util.HashMap;

public final class Actors implements Container {

    private final List<Actor> actors;
    private final Map<String, Actor> actor;
    private static Actors instance = null;

    private Actors() {
        actors = new LinkedList<Actor>();
        actor = new HashMap<String, Actor>();
    }

    /**
     *
     * @return
     */
    public static Actors getInstance() {

        if (instance == null) {
            synchronized (Users.class) {
                if (instance == null) {
                    instance = new Actors();
                }
            }
        }
        return instance;
    }

    public List<Actor> getAll() {
        return actors;
    }

    /**
     *
     * @param newActor
     */
    public void add(final Object newActor) {
        actors.add((Actor) newActor);
        actor.put(((Actor) newActor).getName(), (Actor) newActor);
    }

    /**
     *
     * @param name
     * @return
     */
    public Actor get(final String name) {
        return actor.get(name);
    }

    /**
     *
     */
    public void purge() {
        actor.clear();
        actors.clear();
    }
}
