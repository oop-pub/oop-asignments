package utils;

import vcs.Vcs;

import java.util.ArrayList;

/**
 * Every operation must extend this class.
 * This is a template for a filesystem operation or a vcs operation.
 *
 */
public abstract class AbstractOperation {
    protected OperationType type;
    protected ArrayList<String> operationArgs;

    /**
     * Abstract operation constructor.
     *
     * @param type          the type of the operation
     * @param operationArgs the arguments of the operation
     */
    public AbstractOperation(OperationType type, ArrayList<String> operationArgs) {
        this.type = type;
        this.operationArgs = operationArgs;
    }

    /**
     * Gets the operation args.
     * @return the arguments of this operation
     */
    public ArrayList<String> getOperationArgs() {
        return this.operationArgs;
    }

    /**
     * Gest the type.
     * @return type of this operation
     */
    public OperationType getType() {
        return this.type;
    }

    /**
     * Accepts the vcs visitor.
     * @param vcs the vcs visitor
     * @return the return code
     */
    public abstract int accept(Vcs vcs);
}
