package simulate;

import input.Distributor;

import java.util.Objects;

public final class Contract implements Comparable {

    private int distributorId;
    private int subscriptionCount;
    private final Distributor distributor;
    private int length;
    private int price;

    public Contract(final Distributor distributor) {
        this.distributor = distributor;
        calculatePrice();
    }

    public Contract(final Contract contract) {
        this.distributor = contract.distributor;
        this.price = contract.price;
        this.length = contract.length;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(final int distributorId) {
        this.distributorId = distributorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public boolean calculatePrice() {
        int newPrice;
        if (distributor.getContract() == null) {
            distributor.setContract(this);
            length = distributor.getContractLength();
        }
        if (subscriptionCount == 0) {
            newPrice = distributor.getInitialInfrastructureCost()
                    + distributor.getInitialProductionCost()
                    + distributor.getProfit();
        } else {
            newPrice = (int) Math.round(
                    Math.floor((float) distributor.getInitialInfrastructureCost()
                            / subscriptionCount) + distributor.getInitialProductionCost()
                            + distributor.getProfit()
            );
        }
        if (newPrice != price) {
            price = newPrice;
            return true;
        }
        return false;
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    /**
     *
     * @param number
     */
    public void increaseSubscriptionCount(final int number) {
        subscriptionCount += number;
    }

    /**
     *
     * @param number
     */
    public void decreaseSubscriptionCount(final int number) {
        subscriptionCount -= number;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    @Override
    public int compareTo(final Object o) {
        if (price != ((Contract) o).price) {
            return price - ((Contract) o).price;
        }
        return distributor.getId() - ((Contract) o).distributor.getId();
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contract contract = (Contract) o;
        return distributorId == contract.distributorId
                && subscriptionCount == contract.subscriptionCount
                && length == contract.length
                && price == contract.price
                && Objects.equals(distributor, contract.distributor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distributorId, subscriptionCount, distributor, length, price);
    }
}
