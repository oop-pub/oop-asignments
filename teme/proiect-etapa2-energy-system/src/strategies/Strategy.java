package strategies;

import java.util.List;
import input.Producer;

public interface Strategy {
    /**
     * Returns the best producers based on chosen strategy
     * @param producers
     * @param requiredEnergy
     * @return
     */
    List<Producer> getProducers(List<Producer> producers, int requiredEnergy);
}
