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
        calculatePrice();
    }

    public Contract(Contract contract) {
        this.distributor = contract.distributor;
        this.price = contract.price;
        this.length = contract.length;
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

    public boolean calculatePrice() {
        int newPrice;
        if (distributor.getContract() == null) {
            distributor.setContract(this);
            length = distributor.getContractLength();
        }
        if (subscriptionCount == 0) {
            newPrice = distributor.getInitialInfrastructureCost() +
                    distributor.getInitialProductionCost() +
                    distributor.getProfit();
        } else {
            newPrice = (int)Math.round(Math.floor((float)distributor.getInitialInfrastructureCost() /
                    subscriptionCount) + distributor.getInitialProductionCost() +
                    distributor.getProfit());
        }
        if(newPrice != price) {
            price = newPrice;
            return true;
        }
        return false;
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void increaseSubscriptionCount(int number) {
        subscriptionCount += number;
    }
    public void decreaseSubscriptionCount(int number) {
        subscriptionCount -= number;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    @Override
    public int compareTo(Object o) {
        if(price != ((Contract)o).price) {
            return price - ((Contract)o).price;
        }
        return distributor.getId() - ((Contract)o).distributor.getId();
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return distributorId == contract.distributorId &&
                subscriptionCount == contract.subscriptionCount &&
                length == contract.length &&
                price == contract.price &&
                Objects.equals(distributor, contract.distributor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distributorId, subscriptionCount, distributor, length, price);
    }
}
