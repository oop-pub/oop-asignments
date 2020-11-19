package container;

import java.util.List;

public interface Container {

    /**
     *
     * @return
     */
    List getAll();

    /**
     *
     * @param obj
     */
    void add(Object obj);

    /**
     *
     */
    void purge();
}
