package input;

import simulate.Contract;

public class Consumer extends Player {

    private int monthlyIncome;

    private boolean isSubscripted;

    private int remainingSubscription;

    private boolean inDebt;

    private Contract previousContract;

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public boolean hasContract() {
        return isSubscripted;
    }

    public int getRemainingSubscription() {
        return remainingSubscription;
    }

    public void setRemainingSubscription(int remainingSubscription) {
        this.remainingSubscription = remainingSubscription;
    }

    public void getIncome() {
        addToBudget(monthlyIncome);
    }

    public void pay() {
        if(inDebt) {
            if(Math.round(previousContract.getPrice() * 1.2) + contract.getPrice() <= initialBudget) {
                initialBudget -= Math.round(previousContract.getPrice() * 1.2) + contract.getPrice();
                previousContract.getDistributor().addToBudget((int)Math.round(previousContract.getPrice() * 1.2));
                inDebt = false;
                contract.getDistributor().addToBudget(contract.getPrice());
            } else {
                isBankrupt = true;
            }
        } else {
            if(contract.getPrice() <= initialBudget) {
                initialBudget -= contract.getPrice();
                contract.getDistributor().addToBudget(contract.getPrice());
            } else {
                previousContract = contract;
                inDebt = true;
            }
        }
    }

    @Override
    public void setContract(Contract contract) {
        this.contract = new Contract(contract);
        remainingSubscription = contract.getLength();
        isSubscripted = true;
    }

    public void checkContractDate() {
        remainingSubscription--;
        if(remainingSubscription == 0) {
            isSubscripted = false;
            contract.getDistributor().getContract().decreaseSubscriptionCount(1);
            contract.getDistributor().removeCustomer(this);
        }
    }

    public Contract getPreviousContract() {
        return previousContract;
    }

    public void removeContract() {
        isSubscripted = false;
    }
}
