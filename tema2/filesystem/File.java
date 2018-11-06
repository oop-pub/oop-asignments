package filesystem;

import utils.EntityType;
import utils.IDGenerator;
import utils.OutputWriter;

public final class File extends FileSystemEntity {
    private String content;

    /**
     * File constructor.
     *
     * @param name   the name of the file
     * @param parent the parent directory of the file
     */
    public File(String name, Directory parent) {
        this.id = IDGenerator.generateFileID();
        this.type = EntityType.FILE;
        this.parent = parent;
        this.name = name;
        this.content = "";
    }

    /**
     * Tells that any search of an entity reached a dead end.
     *
     * @param path the path
     * @return nothing found
     */
    @Override
    public FileSystemEntity getEntity(String path) {
        return null;
    }

    /**
     * Clones this file.
     * @param newParent clone of the parent directory
     * @return the clone of this file
     */
    @Override
    protected FileSystemEntity clone(Directory newParent) {
        File newFile = new File(this.name, newParent);

        newFile.addContent(this.content);

        return newFile;
    }

    /**
     * Prints the file name.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public void print(String tabs, OutputWriter outputWriter) {
        outputWriter.write(tabs + this.name + "\n");
    }

    /**
     * Tells that any search of an entity reached a dead end.
     * @param name the entity name
     * @return nothing found
     */
    public boolean hasEntity(String name) {
        return false;
    }

    /**
     * Returns the content of the file.
     * @return the content of the file
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Adds content to the file.
     * @param s the content to be added
     */
    public void addContent(String s) {
        this.content += s;
    }
}
