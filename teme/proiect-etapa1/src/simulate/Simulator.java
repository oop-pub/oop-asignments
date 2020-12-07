package simulate;

import input.Consumer;
import input.Distributor;
import input.InputParser;
import input.Players;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Simulator {

    public void simulate(InputParser inputParser) throws IOException {
            Players<Distributor> distributors = inputParser.getDistributors();
            Players<Consumer> consumers = inputParser.getConsumers();
            Contract bestContract;

            for (int i = 0; i <inputParser.getNumberOfTurns(); i++) {
                bestContract = null;
                for (Distributor distributor : distributors.getAll()) {
                    if (distributor.getContract() == null) {
                        new Contract(distributor.getId()).calulatePrice();
                    } else {
                        distributor.getContract().calulatePrice();
                    }
                    if (bestContract == null || distributor.getContract().getPrice()
                            < bestContract.getPrice()) {
                        bestContract = distributor.getContract();
                    }
                    distributor.addToBudget(distributor.getProfit());
                    distributor.pay(distributor.getInitialInfrastructureCost());
                    if (distributor.getContract().getSubscriptionCount() != 0) {
                        distributor.pay(distributor.getInitialProductionCost());
                    }
                }
                if (bestContract != null) {
                    for (Consumer consumer : consumers.getAll()) {
                        if (!consumer.hasContract()) {
                            bestContract.increaseSubscriptionCount();
                            consumer.setContract(bestContract);
                            consumer.setRemainingSubscription();
                        }
                    }
                }
                List<CostChange> costChanges = inputParser.getNextUpdates();
                for (CostChange newCost : costChanges) {
                    Updater.update(newCost, inputParser);
                }

            }
    }
}
