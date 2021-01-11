package input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * Gets an Object by id
     * @param id
     * @return
     */
    public T getById(final int id) {
        return memberMap.get(id);
    }

    /**
     * Adds a new Object
     * @param member
     */
    public void add(final T member) {
        members.add(member);
        try {
            Method method = member.getClass().getMethod("getId");
            int id = (int) method.invoke(member);
            memberMap.put(id, member);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Added object needs to have getId method that returns integer");
        }
    }

    /**
     * Adds a list of Objects
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
