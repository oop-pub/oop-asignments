package input;

public class Consumer extends Player {

    private int monthlyIncome;

    private boolean isSubscripted;

    private int remainingSubscription;

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
}
