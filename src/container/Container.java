package container;

import java.util.List;

public interface Container {

    /**
     * Gets all the elements in the container
     * @return
     */
    List getAll();

    /**
     * Adds an element
     * @param obj
     */
    void add(Object obj);

    /**
     * Purges the container
     */
    void purge();
}
