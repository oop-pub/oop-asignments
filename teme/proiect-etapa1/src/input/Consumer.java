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
     *
     * @return
     */
    public boolean hasContract() {
        return isSubscripted;
    }

    public int getRemainingSubscription() {
        return remainingSubscription;
    }

    /**
     *
     */
    public void getIncome() {
        addToBudget(monthlyIncome);
    }

    /**
     *
     */
    public void pay() {
        if (inDebt) {
            if (Math.round(previousContract.getPrice() * PENALTY)
                    + contract.getPrice() <= initialBudget) {
                initialBudget -= Math.round(previousContract.getPrice() * PENALTY)
                        + contract.getPrice();
                previousContract.getDistributor().addToBudget(
                        (int) Math.round(previousContract.getPrice() * PENALTY)
                );
                inDebt = false;
                contract.getDistributor().addToBudget(contract.getPrice());
            } else {
                isBankrupt = true;
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
     *
     * @param contract
     */
    @Override
    public void setContract(final Contract contract) {
        this.contract = new Contract(contract);
        remainingSubscription = contract.getLength();
        isSubscripted = true;
    }

    /**
     *
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
     *
     */
    public void removeContract() {
        isSubscripted = false;
    }
}
