package filesystem;

import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class LsOperation extends FileSystemOperation {
    /**
     * Ls operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LsOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the ls operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(FileSystemSnapshot fileSystemSnapshot) {
        Directory dir;
        FileSystemEntity e;

        if (operationArgs.size() == 0) {
            e = fileSystemSnapshot.getCurrentDir();
        } else {
            e = fileSystemSnapshot.getEntity(operationArgs.get(0));
        }

        if (e == null) {
            return ErrorCodeManager.SYS_BAD_PATH_CODE;
        }
        if (!e.getType().equals(EntityType.DIRECTORY)) {
            return ErrorCodeManager.SYS_BAD_CMD_CODE;
        }

        dir = (Directory) e;
        for (FileSystemEntity fse : dir.getContent()) {
            fileSystemSnapshot.getOutputWriter().write(fse.getName() + "\n");
        }

        return ErrorCodeManager.OK;
    }
}
