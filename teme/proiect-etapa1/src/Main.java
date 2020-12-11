import input.InputParser;
import output.OutputWriter;
import simulate.Simulator;

public final class Main {

    private Main() { }

    /**
     *
     * @param args
     * @throws Exception
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
