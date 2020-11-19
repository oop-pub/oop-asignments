package container;

import java.util.List;

public interface Container {

    List getAll();
    void add(Object obj);
    void purge();
}
