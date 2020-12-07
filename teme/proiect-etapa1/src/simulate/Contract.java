package simulate;

import input.Distributor;
import input.InputParser;

public class Contract {

    private int distributorId;
    private int subscriptionCount;
    private int length;
    private float price;

    public Contract(int distributorId) {
        this.distributorId = distributorId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void calulatePrice() {
        Distributor distributor = InputParser.getInstance()
                                .getDistributors().getById(distributorId);
        if (distributor.getContract() == null) {
            distributor.setContract(this);
            length = distributor.getContractLength();
        }
        if (subscriptionCount == 0) {
            price = distributor.getInitialInfrastructureCost() +
                    distributor.getInitialProductionCost() +
                    distributor.getProfit();
        } else {
            price = Math.round(Math.floor((float)distributor.getInitialInfrastructureCost() /
                    subscriptionCount) + distributor.getInitialProductionCost() +
                    distributor.getProfit());
        }
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void increaseSubscriptionCount() {
        subscriptionCount += 1;
    }
}
