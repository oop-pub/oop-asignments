package simulate;

import input.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Simulator {

    public void simulate(InputParser inputParser) throws IOException {
            Players<Distributor> distributors = inputParser.getDistributors();
            Players<Consumer> consumers = inputParser.getConsumers();
            List<CostChange> costChanges;
            SortedSet<Contract> contracts = new TreeSet<>();
            for(Distributor distributor : distributors.getAll()) {
                contracts.add(new Contract(distributor));
            }
            for(int turn = 0; turn < inputParser.getNumberOfTurns(); turn++) {
                // get the salary
                Visa.getIncome(consumers.getAll());

                Contract bestContract = contracts.first();
                Distributor luckyDistributor = bestContract.getDistributor();
                bestContract.increaseSubscriptionCount(consumers.getAll().size());

                // new contracts
                for(Consumer consumer : consumers.getAll()) {
                    consumer.checkContractDate();
                    if(!consumer.hasContract()) {
                        consumer.setContract(bestContract);
                        luckyDistributor.addCustomer(consumer);
                        bestContract.increaseSubscriptionCount(1);
                    }
                }

                // pay the fees
                Visa.payFees(consumers.getAll());
                Visa.payFees(distributors.getAll());

                // remove the bankrupt consumers
                consumers.getAll().removeIf(Player::isBankrupt);

                // remove the bankrupt distributors

                distributors.getAll().removeIf(Distributor::isBankrupt);

                // get updates
                costChanges = inputParser.getNextUpdates();
                for (CostChange newCost : costChanges) {
                    Updater.update(newCost, inputParser);
                }
            }

    }
}
