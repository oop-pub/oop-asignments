package simulate;

import input.Distributor;
import input.InputParser;

import java.util.Objects;

public class Contract implements Comparable {

    private int distributorId;
    private int subscriptionCount;
    private Distributor distributor;
    private int length;
    private int price;

    public Contract(Distributor distributor) {
        this.distributor = distributor;
        calulatePrice();
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void calulatePrice() {

        if (distributor.getContract() == null) {
            distributor.setContract(this);
            length = distributor.getContractLength();
        }
        if (subscriptionCount == 0) {
            price = distributor.getInitialInfrastructureCost() +
                    distributor.getInitialProductionCost() +
                    distributor.getProfit();
        } else {
            price = (int)Math.round(Math.floor((float)distributor.getInitialInfrastructureCost() /
                    subscriptionCount) + distributor.getInitialProductionCost() +
                    distributor.getProfit());
        }
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void increaseSubscriptionCount(int number) {
        subscriptionCount += number;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    @Override
    public int compareTo(Object o) {
        return price - ((Contract)o).price;
    }

    public int getLength() {
        return length;
    }
}
