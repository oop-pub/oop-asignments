package strategies;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import input.Producer;

public final class GreenStrategy implements Strategy {
    @Override
    public List<Producer> getProducers(List<Producer> producers, int requiredEnergy) {
        List<Producer> arrangedProducers = producers.stream()
                .filter(Producer::hasRoom)
                .sorted(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                if (GreenDetector.check(o1.getEnergyType())
                        && !GreenDetector.check(o2.getEnergyType())) {
                    return -1;
                } else if (!GreenDetector.check(o1.getEnergyType())
                        && GreenDetector.check(o2.getEnergyType())) {
                    return 1;
                } else if (o1.getPriceKW() != o2.getPriceKW()) {
                    return Float.compare(o1.getPriceKW(), o2.getPriceKW());
                } else if (o1.getEnergyPerDistributor() != o2.getEnergyPerDistributor()) {
                    return -o1.getEnergyPerDistributor() + o2.getEnergyPerDistributor();
                } else {
                    return o1.getId() - o2.getId();
                }
            }
        }).collect(Collectors.toList());
        List<Producer> newProducers = new LinkedList<>();
        for (Producer producer : arrangedProducers) {
            if (requiredEnergy > 0) {
                requiredEnergy -= producer.getEnergyPerDistributor();
                newProducers.add(producer);
            } else {
                break;
            }
        }
        return newProducers;

    }
}
