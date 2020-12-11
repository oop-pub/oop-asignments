package input;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public final class Players<T> {
    private List<T> members;
    private Map<Integer, T> memberMap;

    public Players() {
        members = new LinkedList<>();
        memberMap = new TreeMap<>();
    }

    public List<T> getAll() {
        return members;
    }

    /**
     *
     * @param id
     * @return
     */
    public T getById(final int id) {
        return memberMap.get(id);
    }

    /**
     *
     * @param member
     */
    public void add(final T member) {
        members.add(member);
        memberMap.put(((Player) member).getId(), member);
    }

    /**
     *
     * @param newMembers
     */
    public void addAll(final List<T> newMembers) {
        for (T member : newMembers) {
            this.add(member);
        }
    }
    public Map<Integer, T> getMap() {
        return memberMap;
    }
}
