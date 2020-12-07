package simulate;

import input.Distributor;
import input.InputParser;

public final class Updater {

    static void update(CostChange costChange, InputParser input) {
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
