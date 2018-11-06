package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OutputWriter;

public final class FileSystemSnapshot {
    private final Directory root;
    private final OutputWriter outputWriter;
    private Directory currentDir;

    /**
     * File system snapshot constructor used for cloning.
     *
     * @param root         the root directory
     * @param outputWriter the output writer
     */
    public FileSystemSnapshot(Directory root, OutputWriter outputWriter) {
        this.root = root;
        currentDir = root;
        this.outputWriter = outputWriter;
    }

    /**
     * File system constructor.
     *
     * @param outputWriter the output writer
     */
    public FileSystemSnapshot(OutputWriter outputWriter) {
        root = new Directory("root", null);
        currentDir = root;
        this.outputWriter = outputWriter;
    }

    /**
     * Clones the file system snapshot.
     * @return the clone of this file system snapshot
     */
    public FileSystemSnapshot cloneFileSystem() {
        FileSystemSnapshot newFileSystem
                = new FileSystemSnapshot((Directory) this.root.clone(null), this.outputWriter);

        return newFileSystem;
    }

    /**
     * Gets the root directory.
     * @return the root directory
     */
    public Directory getRoot() {
        return root;
    }

    /**
     * Gets the output writer.
     * @return the output writer
     */
    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    public Directory getCurrentDir() {
        return currentDir;
    }

    /**
     * Sets the current dir.
     * @param newDir the dir to be set
     */
    public void setCurrentDir(Directory newDir) {
        this.currentDir = newDir;
    }

    /**
     * Checks if this file system snapshot has an entity.
     * @param path the path to the entity
     * @return whether it has the entity or not
     */
    public boolean hasEntity(String path) {
        if (!path.startsWith("/")) {
            path = currentDir.getPathToHere() + path;
        }
        path = path.replaceFirst("/", "");

        return this.root.hasEntity(path);
    }

    /**
     * Adds an entity to this file system snapshot.
     * @param type the type of the entity
     * @param path the path where to add the entity
     * @param name the name of the entity
     * @return the return code
     */
    public int addEntity(EntityType type, String path, String name) {
        FileSystemEntity e;

        e = this.getEntity(path);
        if (e == null) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }

        if (e.getType().equals(EntityType.DIRECTORY)) {
            FileSystemEntity newFse;
            Directory dir = (Directory) e;

            if (dir.hasEntity(name)) {
                return ErrorCodeManager.SYS_BAD_CMD_CODE;
            }

            switch (type) {
                case FILE:
                    newFse = new File(name, dir);
                    break;
                case DIRECTORY:
                    newFse = new Directory(name, dir);
                    break;
                default:
                    return ErrorCodeManager.SYS_BAD_PATH_CODE;
            }
            dir.addEntity(newFse);

            return ErrorCodeManager.OK;
        }

        return ErrorCodeManager.SYS_BAD_PATH_CODE;
    }


    /**
     * Gets an entity from this file system snapshot.
     * @param path the path to the entity
     * @return the entity or null, if it doesn't exist
     */
    public FileSystemEntity getEntity(String path) {
        FileSystemEntity target = null;

        if (!path.startsWith("/")) {
            path = currentDir.getPathToHere() + path;
        }
        path = path.replaceFirst("/", "");
        target = this.root.getEntity(path);

        return target;
    }
}
