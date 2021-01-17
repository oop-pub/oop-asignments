package simulate;

import input.Distributor;
import input.InputParser;
import input.Producer;

public final class Updater {

    private Updater() { }
    static void notify(final Change change, final InputParser input, String type) {
        switch (type) {
            case "D" -> {
                Distributor distributor = input.getDistributors().getById(change.getId());
                if (distributor != null) {
                    distributor.update(change);
                }
            }
            case "P" -> {
                Producer producer = input.getProducers().getById(change.getId());
                if (producer != null) {
                    input.getProducers().getById(change.getId()).update(change);
                }
            }

            default -> {

            }
        }

    }
}
