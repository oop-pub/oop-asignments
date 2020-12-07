package input;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulate.CostChange;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class InputParser {

    private final Players<Consumer> consumers;
    private final Players<Distributor> distributors;
    private int numberOfTurns;
    private static InputParser instance = null;
    private JsonNode jsonNode;
    private final ObjectMapper objectMapper;
    private final int currentUpdate;

    private InputParser() {
        objectMapper = new ObjectMapper();
        consumers = new Players<>();
        distributors = new Players<>();
        currentUpdate = 0;
    }

    public static InputParser getInstance() {
        if(instance == null) {
            instance = new InputParser();
        }
        return instance;
    }

    public void parse(String filename) throws IOException {

        jsonNode = objectMapper.readTree(new File(filename));
        numberOfTurns = jsonNode.get("numberOfTurns").asInt();
        consumers.addAll(getData(jsonNode.get("initialData").get("consumers"), Consumer.class));
        distributors.addAll(getData(jsonNode.get("initialData").get("distributors"), Distributor.class));
    }

    private <T> List<T> getData(JsonNode location, Class<T> cls) throws IOException {

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, cls);
        return objectMapper.readValue(
                location.toString(),
                javaType
        );
    }

    public Players<Consumer> getConsumers() {
        return consumers;
    }

    public Players<Distributor> getDistributors() {
        return distributors;
    }

    public List<CostChange> getNextUpdates() throws IOException {
        JsonNode currentNode = jsonNode.get("monthlyUpdates").get(currentUpdate);
        consumers.addAll(getData(
                currentNode.get("newConsumers"),
                Consumer.class));

        return getData(
                currentNode.get("costsChanges"),
                CostChange.class);
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
