package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Players<T> {
    private List<T> members;
    private Map<Integer, T> memberMap;

    public Players() {
        members = new ArrayList<>();
        memberMap = new HashMap<>();
    }

    public List getAll() {
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
}
