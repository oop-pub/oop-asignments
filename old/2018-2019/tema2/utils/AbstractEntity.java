package utils;

public abstract class AbstractEntity {
    protected int id;
    protected EntityType type;

    /**
     * Gets the id.
     * @return the id of this entity
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the type.
     * @return the type of the entity
     */
    public EntityType getType() {
        return this.type;
    }

    /**
     * Checks if it has a certain child entity.
     *
     * @param entityPath path to the child entity
     * @return whether it has the child entity or not
     */
    public abstract boolean hasEntity(String entityPath);
}
