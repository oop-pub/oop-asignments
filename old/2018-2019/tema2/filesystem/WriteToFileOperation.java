package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class WriteToFileOperation extends FileSystemOperation {
    /**
     * Writetofile operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public WriteToFileOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the writetofile operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        File file;

        if (operationArgs.size() != 2) {
            return ErrorCodeManager.SYS_BAD_CMD_CODE;
        }

        FileSystemEntity fileSystemEntity = fileSystemSnapshot.getEntity(operationArgs.get(0));
        if (fileSystemEntity == null || !fileSystemEntity.getType().equals(EntityType.FILE)) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }

        file = (File) fileSystemEntity;
        file.addContent(operationArgs.get(1));

        return ErrorCodeManager.OK;
    }
}
