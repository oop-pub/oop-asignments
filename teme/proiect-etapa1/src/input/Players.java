package input;

import java.util.*;

public class Players<T> {
    private List<T> members;
    private Map<Integer, T> memberMap;

    public Players() {
        members = new LinkedList<>();
        memberMap = new TreeMap<>();
    }

    public List<T> getAll() {
        return members;
    }

    public T getById(int id) {
        return memberMap.get(id);
    }

    public void add(T member) {
        members.add(member);
        memberMap.put(((Player)member).getId(), member);
    }

    public void addAll(List<T> members) {
        for(T member : members) {
            this.add(member);
        }
    }

    public void eliminate(T member) {
        members.remove(member);
    }

    public Map<Integer, T> getMap() {
        return memberMap;
    }
}
