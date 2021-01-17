package input;
import simulate.Change;
import strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Player {

    private int contractLength;
    private int initialInfrastructureCost;
    private int energyNeededKW;
    private static final float PERCENTAGE = (float) 0.2;
    private static final int DIVIDER = 10;
    private int initialProductionCost;
    private String producerStrategy;
    private final List<Consumer> customers;
    private Strategy producerFinder;
    private List<Producer> currentProducers;
    private boolean renewProducers;

    public void setProducerFinder(Strategy producerFinder) {
        this.producerFinder = producerFinder;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public Distributor() {
        customers = new ArrayList<>();
        initialProductionCost = 0;
        renewProducers = true;
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
     * Adds a new customer
     * @param consumer
     */
    public void addCustomer(final Consumer consumer) {
        customers.add(consumer);
    }

    /**
     * Removes a customer
     * @param consumer
     */
    public void removeCustomer(final Consumer consumer) {
        customers.remove(consumer);
    }

    /**
     * Returns the profit
     * @return
     */
    public int getProfit() {
        return (int) Math.round(
                Math.floor(PERCENTAGE * initialProductionCost)
        );
    }

    /**
     * Removes the payed amount
     */
    public void pay() {
        initialBudget -= (initialInfrastructureCost + initialProductionCost * customers.size());
        if (initialBudget < 0) {
            isBankrupt = true;
        }
    }

    /**
     * Gets new producers for the distributor
     * @param producers
     */
    public void getProducers(List<Producer> producers) {
            if (currentProducers != null) {
                for (Producer producer : currentProducers) {
                    producer.removeDistributor(this);
                }
            }
            currentProducers = producerFinder.getProducers(producers, energyNeededKW);
            float totalCost = 0;
            for (Producer producer : currentProducers) {
                totalCost += (producer.getEnergyPerDistributor() * producer.getPriceKW());
                producer.addDistributor(this);
            }
            initialProductionCost = (int) Math.round(Math.floor(totalCost / DIVIDER));
            renewProducers = false;
    }

    /**
     * returns a list of customers
     * @return
     */
    public List<Consumer> getCustomers() {
        return customers;
    }

    /**
     * updates when a change occurs
     * @param distributorChange
     */
    public void update(Change distributorChange) {
        initialInfrastructureCost = distributorChange.getNewValue();
    }


    public void setRenewProducers(boolean renewProducers) {
        this.renewProducers = renewProducers;
    }
    public boolean getRenewProducers() {
        return renewProducers;
    }
}
