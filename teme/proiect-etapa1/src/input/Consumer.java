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

    public void payContract(Contract contract) {
        if(initialBudget >= contract.getPrice()) {
            contract.getDistributor().addToBudget(contract.getPrice());
            initialBudget -= contract.getPrice();
        } else if(inDebt) {
            isBankrupt = true;
        } else {
            inDebt = true;
            previousContract = contract;
        }
    }

    public void pay() {
        if(previousContract != null) {
            payContract(previousContract);
        }
        if(!isBankrupt) {
            payContract(contract);
        }
    }

    @Override
    public void setContract(Contract contract) {
        this.contract = contract;
        remainingSubscription = contract.getLength();
    }

    public void checkContractDate() {
        remainingSubscription--;
        if(remainingSubscription == 0) {
            isSubscripted = false;
        }
    }
}
