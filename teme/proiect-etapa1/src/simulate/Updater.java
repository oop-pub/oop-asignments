package simulate;

import input.Distributor;
import input.InputParser;

public final class Updater {

    private Updater() { }
    static void update(final CostChange costChange, final InputParser input) {
        Distributor distributor = input.getDistributors()
                .getById(costChange.getId());
        distributor.setInitialInfrastructureCost(
                costChange.getInfrastructureCost()
        );
        distributor.setInitialProductionCost(
                costChange.getProductionCost()
        );
    }
}
