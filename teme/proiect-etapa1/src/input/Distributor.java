package input;
import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Player {

    private int contractLength;
    private int initialInfrastructureCost;
    private static final float PERCENTAGE = (float) 0.2;
    private int initialProductionCost;
    private final List<Consumer> customers;

    public Distributor() {
        customers = new ArrayList<>();
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getInitialProductionCost() {
        return initialProductionCost;
    }

    public void setInitialProductionCost(final int initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }

    /**
     *
     * @param consumer
     */
    public void addCustomer(final Consumer consumer) {
        customers.add(consumer);
    }

    /**
     *
     * @param consumer
     */
    public void removeCustomer(final Consumer consumer) {
        customers.remove(consumer);
    }

    /**
     *
     * @return
     */
    public int getProfit() {
        return (int) Math.round(
                Math.floor(PERCENTAGE * initialProductionCost)
        );
    }

    /**
     *
     */
    public void getIncome() { }

    /**
     *
     */
    public void pay() {
        initialBudget -= (initialInfrastructureCost + initialProductionCost * customers.size());
        if (initialBudget < 0) {
            isBankrupt = true;
        }
    }

    public List<Consumer> getCustomers() {
        return customers;
    }
}
