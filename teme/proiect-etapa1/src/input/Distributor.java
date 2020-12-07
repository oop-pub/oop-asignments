package input;

import simulate.Contract;

public class Distributor extends Player {

    private int contractLength;
    private int initialInfrastructureCost;
    private int initialProductionCost;

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

    public int getProfit() {
        return (int)Math.round(Math.floor(0.2 * initialProductionCost));
    }
}
