package input;

import simulate.Contract;

public abstract class Player {
    protected int id;
    protected int initialBudget;
    protected boolean isBankrupt;
    protected Contract contract;

    /**
     * Returns the id of the player
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a new Id
     * @param id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returns the budget
     * @return
     */
    public int getInitialBudget() {
        return initialBudget;
    }

    /**
     * Sets the inital budget
     * @param initialBudget
     */
    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    /**
     * Adds a given amount to the budget
     * @param sum
     */
    public void addToBudget(final int sum) {
        initialBudget += sum;
    }

    /**
     * Method for paying the fees
     */
    public abstract void pay();

    /**
     * Sets a new Contract
     * @param theContract
     */
    public void setContract(final Contract theContract) {
        contract = theContract;
    }

    /**
     * Returns the current contract
     * @return
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * Adds monthly income if it is the case
     */

    /**
     * Returns true if player is bankrupt
     * @return
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }
}
