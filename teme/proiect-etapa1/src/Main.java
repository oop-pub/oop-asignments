import com.fasterxml.jackson.databind.ObjectMapper;
import input.InputParser;
import simulate.Simulator;

public class Main {

    public static void main(String[] args) throws Exception {

        InputParser.getInstance().parse(args[0]);
        Simulator simulator = new Simulator();
        simulator.simulate(InputParser.getInstance());

    }
}
