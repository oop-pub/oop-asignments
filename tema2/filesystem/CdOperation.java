package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class CdOperation extends FileSystemOperation {
    /**
     * Cd operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CdOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the cd operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        FileSystemEntity newDirectory = null;

        if (this.operationArgs.size() == 1) {
            newDirectory = fileSystemSnapshot.getEntity(this.operationArgs.get(0));
            if (newDirectory == null) {
                return ErrorCodeManager.SYS_BAD_PATH_CODE;
            }
            if (newDirectory.getType().equals(EntityType.DIRECTORY)) {
                fileSystemSnapshot.setCurrentDir((Directory) newDirectory);
                return ErrorCodeManager.OK;
            }
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }

        return ErrorCodeManager.SYS_BAD_PATH_CODE; // possible an error
    }
}
