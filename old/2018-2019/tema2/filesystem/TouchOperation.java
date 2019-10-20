package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class TouchOperation extends FileSystemOperation {
    /**
     * Touch operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public TouchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the touch operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        if (operationArgs.size() != 2) {
            return ErrorCodeManager.SYS_BAD_CMD_CODE;
        }

        return fileSystemSnapshot
                .addEntity(EntityType.FILE, operationArgs.get(0), operationArgs.get(1));
    }
}
