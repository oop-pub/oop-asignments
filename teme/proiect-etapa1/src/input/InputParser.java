package input;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulate.CostChange;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class InputParser {

    private Players<Consumer> consumers;
    private Players<Distributor> distributors;
    private int numberOfTurns;
    private static InputParser instance = null;
    private final ObjectMapper objectMapper;
    private final ObjectListFactory objectListFactory;
    private JsonNode jsonNode;

    private InputParser() {
        objectMapper = new ObjectMapper();
        objectListFactory = ObjectListFactory.getInstance();
    }

    public static InputParser getInstance() {
        if(instance == null) {
            instance = new InputParser();
        }
        return instance;
    }

    public void parse() throws IOException {

        numberOfTurns = jsonNode.get("numberOfTurns").asInt();
        JsonNode currentNode = jsonNode.get("initialData");
        consumers.addAll(objectListFactory.getObjectList(currentNode.get("consumers"),
                objectMapper, Consumer.class));
        distributors.addAll(objectListFactory.getObjectList(currentNode.get("distributors"),
                objectMapper, Distributor.class));
    }

    public Players<Consumer> getConsumers() {
        return consumers;
    }

    public Players<Distributor> getDistributors() {
        return distributors;
    }

    public List<CostChange> getNextUpdates(int currentUpdate) throws IOException {

        JsonNode currentNode = jsonNode.get("monthlyUpdates").get(currentUpdate);
        consumers.addAll(objectListFactory.getObjectList(currentNode.get("newConsumers"),
                objectMapper, Consumer.class));

        return objectListFactory.getObjectList(currentNode.get("costsChanges"),
                objectMapper, CostChange.class);
    }
    public void openFile(String filename) throws IOException {
        consumers = new Players<>();
        distributors = new Players<>();
        jsonNode = objectMapper.readTree(new File(filename));
    }
    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
