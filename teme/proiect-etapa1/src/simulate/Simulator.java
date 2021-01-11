package simulate;

import input.Consumer;
import input.Distributor;
import input.InputParser;
import input.Players;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Simulator {

    /**
     * Simulates the game
     * @param inputParser
     * @throws IOException
     */
    public void simulate(final InputParser inputParser) throws IOException {
            Players<Distributor> distributors = inputParser.getDistributors();
            Players<Consumer> consumers = inputParser.getConsumers();
            List<CostChange> costChanges;
            SortedSet<Contract> contracts = new TreeSet<>();
            for (Distributor distributor : distributors.getAll()) {
                contracts.add(new Contract(distributor));
            }
            for (int turn = 0; turn < inputParser.getNumberOfTurns() + 1; turn++) {
                // get the salary
                Visa.getIncome(consumers.getAll());

                Contract bestContract = contracts.first();
                Distributor luckyDistributor = bestContract.getDistributor();

                // new contracts
                for (Consumer consumer : consumers.getAll()) {
                    consumer.checkContractDate();
                    if (!consumer.hasContract()) {
                        consumer.setContract(bestContract);
                        luckyDistributor.addCustomer(consumer);
                        bestContract.increaseSubscriptionCount(1);
                    }
                }
                // pay the fees
                Visa.payFees(consumers.getAll());
                Visa.payFees(distributors.getAll());

                // remove the bankrupt consumers
                Iterator<Consumer> it = consumers.getAll().listIterator();
                while (it.hasNext()) {
                    Consumer consumer = it.next();
                    if (consumer.isBankrupt()) {
                        consumer.getContract().getDistributor().getContract()
                                .decreaseSubscriptionCount(1);
                        consumer.getContract().getDistributor().removeCustomer(consumer);
                        it.remove();
                    }
                }

                // remove the bankrupt distributors
                Iterator<Distributor> it2 = distributors.getAll().listIterator();
                while (it2.hasNext()) {
                    Distributor distributor = it2.next();
                    if (distributor.isBankrupt()) {
                        for (Consumer consumer : distributor.getCustomers()) {
                            consumer.removeContract();
                            distributor.removeCustomer(consumer);
                        }
                        contracts.remove(distributor.getContract());
                        it2.remove();
                    }
                }

                if (distributors.getAll().size() == 0) {
                    break;
                }

                contracts.remove(bestContract);
                bestContract.calculatePrice();
                contracts.add(bestContract);

                // get updates
                if (turn != inputParser.getNumberOfTurns()) {
                    costChanges = inputParser.getNextUpdates(turn);
                    for (CostChange newCost : costChanges) {
                        Distributor distributor = distributors.getById(newCost.getId());
                        if (!distributor.isBankrupt()) {
                            contracts.remove(distributor.getContract());
                            Updater.update(newCost, inputParser);
                            distributor.getContract().calculatePrice();
                            contracts.add(distributor.getContract());
                        }
                    }
                }
            }

    }
}
