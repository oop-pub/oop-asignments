package actor;

import container.Container;
import fileio.ActorInputData;
import users.Users;

import java.util.*;

public final class Actors implements Container {

    private List<Actor> actors;
    private Map<String, Actor> actor;
    private static Actors instance = null;

    private Actors() {
        actors = new LinkedList<Actor>();
        actor = new HashMap<String, Actor>();
    }

    public static Actors getInstance() {

        if(instance == null) {
            synchronized (Users.class) {
                if(instance == null) {
                    instance = new Actors();
                }
            }
        }
        return instance;
    }

    public List<Actor> getAll() {
        return actors;
    }

    public void add(Object actor) {
        actors.add((Actor)actor);
        this.actor.put(((Actor)actor).getName(), (Actor)actor);
    }

    public Actor get(String name) {
        return actor.get(name);
    }

    public void purge() {
        actor.clear();
        actors.clear();
    }
}
