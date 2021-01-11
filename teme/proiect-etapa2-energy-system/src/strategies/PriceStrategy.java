package strategies;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import input.Producer;

public class PriceStrategy implements Strategy {
    @Override
    public List<Producer> getProducers(List<Producer> producers, int requiredEnergy) {
        List<Producer> arrangedProducers = producers.stream()
                .filter(Producer::hasRoom)
                .sorted(Comparator.comparing(Producer::getPriceKW).thenComparing(Producer::getEnergyPerDistributor
                , Comparator.reverseOrder())
                .thenComparing(Producer::getId))
                .collect(Collectors.toList());
        List<Producer> newProducers = new LinkedList<>();
        for(Producer producer : arrangedProducers) {
            if(requiredEnergy > 0) {
                requiredEnergy -= producer.getEnergyPerDistributor();
                newProducers.add(producer);
            } else {
                break;
            }
        }
        return newProducers;
    }
}
