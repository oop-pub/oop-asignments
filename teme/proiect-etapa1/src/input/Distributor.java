package input;

import simulate.Contract;

import java.util.ArrayList;
import java.util.List;

public class Distributor extends Player {

    private int contractLength;
    private int initialInfrastructureCost;
    private int initialProductionCost;
    private List<Consumer> customers;

    public Distributor() {
        customers = new ArrayList<>();
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getInitialProductionCost() {
        return initialProductionCost;
    }

    public void setInitialProductionCost(int initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }

    public void addCustomer(Consumer consumer) {
        customers.add(consumer);
    }

    public void removeCustomer(Consumer consumer) {
        customers.remove(consumer);
    }

    public int getProfit() {
        return (int)Math.round(Math.floor(0.2 * initialProductionCost));
    }

    public void getIncome() {}

    public void pay() {
        initialBudget -= (initialInfrastructureCost + initialProductionCost * customers.size());
        if(initialBudget < 0) {
            isBankrupt = true;
        }
    }

    public List<Consumer> getCustomers() {
        return customers;
    }
}
