package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class CatOperation extends FileSystemOperation {
    /**
     * Cat operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CatOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the cat operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        File f;

        if (operationArgs.size() != 1) {
            return ErrorCodeManager.SYS_BAD_CMD_CODE;
        }

        FileSystemEntity fse = fileSystemSnapshot.getEntity(operationArgs.get(0));

        if (fse == null) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }
        if (!fse.getType().equals(EntityType.FILE)) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }

        f = (File) fse;
        fileSystemSnapshot.getOutputWriter().write(f.getContent() + "\n");

        return ErrorCodeManager.OK;
    }
}
