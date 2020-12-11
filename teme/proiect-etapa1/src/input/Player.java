package input;

import simulate.Contract;

public abstract class Player {
    protected int id;
    protected int initialBudget;
    protected boolean isBankrupt;
    protected Contract contract;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getInitialBudget() {
        return initialBudget;
    }

    /**
     *
     * @param initialBudget
     */
    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    /**
     *
     * @param sum
     */
    public void addToBudget(final int sum) {
        initialBudget += sum;
    }

    /**
     *
     */
    public abstract void pay();

    /**
     *
     * @param theContract
     */
    public void setContract(final Contract theContract) {
        contract = theContract;
    }

    /**
     *
     * @return
     */
    public Contract getContract() {
        return contract;
    }

    /**
     *
     */
    public abstract void getIncome();

    /**
     *
     * @return
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }
}
