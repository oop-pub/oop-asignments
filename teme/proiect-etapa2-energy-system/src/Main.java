import input.InputParser;
import output.OutputWriter;
import simulate.Simulator;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        InputParser inputParser = InputParser.getInstance();
        inputParser.openFile(args[0]);
        inputParser.parse();
        Simulator simulator = new Simulator();
        simulator.simulate(inputParser);
        OutputWriter.write(inputParser, args[1]);
    }
}
