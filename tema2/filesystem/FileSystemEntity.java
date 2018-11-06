package filesystem;

import utils.AbstractEntity;
import utils.OutputWriter;

public abstract class FileSystemEntity extends AbstractEntity {
    protected String name;
    protected Directory parent;

    /**
     * @return the name of the entity
     */
    public String getName() {
        return this.name;
    }

    /**
     * Prints the entity.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public abstract void print(String tabs, OutputWriter outputWriter);

    /**
     * Gets the entity specified by the path.
     *
     * @param path the path to the entity
     * @return the entity or null if it doesn't exist
     */
    public abstract FileSystemEntity getEntity(String path);

    /**
     * Clones the entity.
     *
     * @param newParent the clone of the parent
     * @return the clone of this entity
     */
    protected abstract FileSystemEntity clone(Directory newParent);
}
