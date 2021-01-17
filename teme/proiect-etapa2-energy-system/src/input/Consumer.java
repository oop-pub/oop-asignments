package input;

import simulate.Contract;

public final class Consumer extends Player {

    private int monthlyIncome;
    private static final float PENALTY = (float) 1.2;

    private boolean isSubscripted;

    private int remainingSubscription;

    private boolean inDebt;

    private Contract previousContract;

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * Returns true if the consumer has an active contract
     * @return
     */
    public boolean hasContract() {
        return isSubscripted;
    }

    public int getRemainingSubscription() {
        return remainingSubscription;
    }

    /**
     * Raises the budget by the monthly income
     */
    public void getIncome() {
        addToBudget(monthlyIncome);
    }

    /**
     * Trying to pay the distributor of the current contract
     */
    public void pay() {
        if (inDebt) {
            if (Math.round(previousContract.getPrice() * PENALTY)
                    + contract.getPrice() <= initialBudget) {
                initialBudget -= Math.round(previousContract.getPrice() * PENALTY)
                        + contract.getPrice();
                previousContract.getDistributor().addToBudget(
                         Math.round(previousContract.getPrice() * PENALTY)
                );
                inDebt = false;
                contract.getDistributor().addToBudget(contract.getPrice());
            } else {
                if (previousContract != contract
                        && Math.round(previousContract.getPrice() * PENALTY) <= initialBudget) {
                    initialBudget -= Math.round(previousContract.getPrice() * PENALTY);
                    previousContract.getDistributor().addToBudget(
                            Math.round(previousContract.getPrice() * PENALTY)
                    );
                   previousContract = contract;
                } else {
                    isBankrupt = true;
                }
            }
        } else {
            if (contract.getPrice() <= initialBudget) {
                initialBudget -= contract.getPrice();
                contract.getDistributor().addToBudget(contract.getPrice());
            } else {
                previousContract = contract;
                inDebt = true;
            }
        }
    }

    /**
     * Sets a new contract for the consumer
     * @param contract
     */
    @Override
    public void setContract(final Contract contract) {
        this.contract = new Contract(contract);
        remainingSubscription = contract.getLength();
        isSubscripted = true;
    }

    /**
     * Checks if the contract expired
     */
    public void checkContractDate() {
        remainingSubscription--;
        if (remainingSubscription == 0) {
            isSubscripted = false;
            contract.getDistributor().getContract().decreaseSubscriptionCount(1);
            contract.getDistributor().removeCustomer(this);
        }
    }

    /**
     * Removes the current contract
     */
    public void removeContract() {
        isSubscripted = false;
    }
}
