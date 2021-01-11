package strategies;

import java.util.List;
import input.Producer;

public interface Strategy {
    List<Producer> getProducers(List<Producer> producers, int requiredEnergy);
}
