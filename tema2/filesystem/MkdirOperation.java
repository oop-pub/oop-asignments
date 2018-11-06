package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class MkdirOperation extends FileSystemOperation {
    /**
     * Mkdir operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public MkdirOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the mkdir operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        int result = ErrorCodeManager.OK;

        if (operationArgs == null) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }

        result = fileSystemSnapshot
                .addEntity(EntityType.DIRECTORY, operationArgs.get(0), operationArgs.get(1));

        return result;
    }
}
