import com.fasterxml.jackson.databind.ObjectMapper;
import input.InputParser;
import output.OutputWriter;
import simulate.Simulator;

public class Main {

    public static void main(String[] args) throws Exception {

        InputParser inputParser = InputParser.getInstance();
        inputParser.openFile(args[0]);
        inputParser.parse();
        Simulator simulator = new Simulator();
        simulator.simulate(inputParser);
        OutputWriter.write(inputParser, args[1]);

    }
}
