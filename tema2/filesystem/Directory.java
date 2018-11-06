package filesystem;

import utils.EntityType;
import utils.IDGenerator;
import utils.OutputWriter;

import java.util.ArrayList;

public final class Directory extends FileSystemEntity {
    private static final int PARENT = -1;
    private static final int THIS = 1;
    private static final int NOTHING = 0;
    private String pathToHere;
    private ArrayList<FileSystemEntity> childrenEntities;

    /**
     * Directory constructor.
     *
     * @param name   of the directory
     * @param parent parent of the directory
     */
    public Directory(String name, Directory parent) {
        this.id = IDGenerator.generateFileID();
        this.type = EntityType.DIRECTORY;
        this.name = name;
        this.parent = parent;
        this.childrenEntities = new ArrayList<>();
        if (parent != null) {
            pathToHere = parent.getPathToHere();
        } else {
            pathToHere = "/";
        }
    }

    /**
     * Prints the directory content recursively.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public void print(String tabs, OutputWriter outputWriter) {
        outputWriter.write(tabs + this.name + "\n");
        tabs += "\t";
        for (FileSystemEntity e : childrenEntities) {
            e.print(tabs, outputWriter);
        }
    }

    /**
     * Adds a child entity.
     *
     * @param entity the child entity
     */
    public void addEntity(FileSystemEntity entity) {
        this.childrenEntities.add(entity);
    }

    /**
     * Gets all the children entities of this directory.
     * @return the children entities
     */
    public ArrayList<FileSystemEntity> getContent() {
        return this.childrenEntities;
    }

    /**
     * Composes the path to this directory.
     * @return the path to this directory
     */
    public String getPathToHere() {
        return pathToHere + this.name + "/";
    }

    /**
     * Checks recursively if the path given leads to any entity inside of this directory.
     * @param path path to follow
     * @return whether the directory holds the entity specified by the path or not
     */
    public boolean hasEntity(String path) {
        String[] parts = path.split("/", 2);
        FileSystemEntity currentEntity = getEntity(parts);

        if (parts.length == 1) {
            return currentEntity != null;
        } else {
            if (currentEntity == null) {
                return false;
            }
            return currentEntity.hasEntity(parts[1]);
        }
    }

    /**
     * Gets the entity specified by the given path.
     * @param path that specifies the entity
     * @return specified entity or null if it doesn't exist
     */
    public FileSystemEntity getEntity(String path) {
        String[] parts = path.split("/", 2);
        FileSystemEntity currentEntity = getEntity(parts);

        if (currentEntity == null) {
            return null;
        }

        if (parts.length == 1 || parts[1].isEmpty()) {
            return currentEntity;
        } else {
            return currentEntity.getEntity(parts[1]);
        }
    }

    /**
     * Looks for the next entity from the given path in this directory.
     * @param parts next entity and the rest of the path
     * @return the next entity from the given path or null if it doesn't exist
     */
    private FileSystemEntity getEntity(String[] parts) {
        FileSystemEntity result = null;

        switch (checkForSpecialFolder(parts[0])) {
            case PARENT:
                result = this.parent;
                break;
            case THIS:
                result = this;
                break;
            case NOTHING:
                if (this.name.equals("root") && this.name.equals(parts[0])) {
                    result = this;
                } else {
                    for (FileSystemEntity fileSystemEntity : this.childrenEntities) {
                        if (fileSystemEntity.getName().equals(parts[0])) {
                            result = fileSystemEntity;
                            break;
                        }
                    }
                }
            default:
                break;
        }

        return result;
    }

    /**
     * Helper to check for special folders in the path.
     * @param currentPart the path
     * @return the special folder code
     */
    private int checkForSpecialFolder(String currentPart) {
        if (currentPart.equals(".")) {
            return THIS;
        }
        if (currentPart.equals("..")) {
            return PARENT;
        }

        return NOTHING;
    }

    /**
     * Removes an entity from the children entities.
     * @param name the name of the entity to be removed
     */
    public void remove(String name) {
        for (int i = 0; i < this.childrenEntities.size(); i++) {
            if (childrenEntities.get(i).name.equals(name)) {
                this.childrenEntities.remove(i);
                break;
            }
        }
    }

    /**
     * Clones the current directory and all of its children.
     * @param newParent the clone of the parent directory
     * @return the clone of this directory
     */
    public FileSystemEntity clone(Directory newParent) {
        Directory clonedDir = new Directory(this.name, newParent);

        for (FileSystemEntity fileSystemEntity : this.childrenEntities) {
            clonedDir.addEntity(fileSystemEntity.clone(this));
        }

        return clonedDir;
    }
}
