package simulate;

import input.InputParser;

import java.io.IOException;
import java.util.List;

public class Simulator {

    private List<CostChange> costChanges;

    public void simulate(InputParser inputParser) throws IOException {
            for(int i = 0; i <inputParser.getNumberOfTurns(); i++) {
                costChanges = inputParser.getNextUpdates();
                for(CostChange newCost : costChanges) {
                    Updater.update(newCost, inputParser);
                }
            }
    }
}
