package simulate;

import input.*;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.io.IOException;
import java.util.*;

public final class Simulator {

    /**
     * Simulates the game
     * @param inputParser
     * @throws IOException
     */
    public void simulate(final InputParser inputParser) throws IOException {
            Players<Distributor> distributors = inputParser.getDistributors();
            Players<Consumer> consumers = inputParser.getConsumers();
            Players<Producer> producers = inputParser.getProducers();
            List<Change> distributorChanges;
            List<Change> producerChanges;
            SortedSet<Contract> contracts = new TreeSet<>();
            for (Distributor distributor : distributors.getAll()) {
                switch (distributor.getProducerStrategy()) {
                    case "GREEN" -> distributor.setProducerFinder(new GreenStrategy());
                    case "PRICE" -> distributor.setProducerFinder(new PriceStrategy());
                    case "QUANTITY" -> distributor.setProducerFinder(new QuantityStrategy());
                }
                distributor.getProducers(producers.getAll());
                contracts.add(new Contract(distributor));
            }
            for (int turn = 0; turn < inputParser.getNumberOfTurns() + 1; turn++) {
                // make monthlyStats
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

                if(turn != inputParser.getNumberOfTurns()) {
                    contracts.remove(bestContract);
                    bestContract.calculatePrice();
                    contracts.add(bestContract);
                }

                if(turn != 0) {
                    //updating producers starting with month 1
                    producerChanges = inputParser.getUpdates(turn - 1, "producerChanges");
                    for(Change change : producerChanges) {
                        Updater.notify(change, inputParser, "P");
                    }

                    //updating the viable distributors
                    for(Distributor distributor : distributors.getAll()) {
                        if(distributor.getRenewProducers()) {
                            distributor.getProducers(producers.getAll());
                        }
                        if(turn != inputParser.getNumberOfTurns()) {
                            contracts.remove(distributor.getContract());
                            distributor.getContract().calculatePrice();
                            contracts.add(distributor.getContract());
                        }
                    }
                    for(Producer producer : producers.getAll()) {
                        producer.addMonthlyStat(turn);
                    }
                }

                // get updates
                if (turn != inputParser.getNumberOfTurns()) {
                    inputParser.addNewCustomers(turn);
                    distributorChanges = inputParser.getUpdates(turn, "distributorChanges");
                    for (Change distributorChange : distributorChanges) {
                        Distributor distributor = distributors.getById(distributorChange.getId());
                        if (!distributor.isBankrupt()) {
                            contracts.remove(distributor.getContract());
                            Updater.notify(distributorChange, inputParser, "D");
                            distributor.getContract().calculatePrice();
                            if(distributor.getContract().getPrice() == 43) {
                                System.out.println("hheere");
                            }
                            contracts.add(distributor.getContract());
                        }
                    }

                }
            }

    }
}
