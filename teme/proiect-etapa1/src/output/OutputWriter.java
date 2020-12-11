package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import input.Consumer;
import input.Distributor;
import input.InputParser;
import input.Players;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OutputWriter {
    private OutputWriter() { }

    /**
     *
     * @param inputParser
     * @param filename
     * @throws IOException
     */
    public static void write(final InputParser inputParser, final String filename)
            throws IOException {
        Players<Distributor> distributors = inputParser.getDistributors();
        Players<Consumer> consumers = inputParser.getConsumers();
        JSONArray distributorsJSON = new JSONArray();
        JSONArray consumersJSON = new JSONArray();
        Map<String, Object> json;
        for (Map.Entry<Integer, Consumer> entry : consumers.getMap().entrySet()) {
            json = new LinkedHashMap<>();
            json.put("id", entry.getKey());
            json.put("isBankrupt", entry.getValue().isBankrupt());
            json.put("budget", entry.getValue().getInitialBudget());
            consumersJSON.add(json);
        }
        Map<String, Object> finalJson = new LinkedHashMap<>();
        finalJson.put("consumers", consumersJSON);
        FileWriter output = new FileWriter(filename);

        Map<String, Object> customer;
        JSONArray customers;

        for (Map.Entry<Integer, Distributor> entry : distributors.getMap().entrySet()) {
            json = new LinkedHashMap<>();
            json.put("id", entry.getKey());
            json.put("budget", entry.getValue().getInitialBudget());
            json.put("isBankrupt", entry.getValue().isBankrupt());
            customers = new JSONArray();
            for (Consumer consumer : entry.getValue().getCustomers()) {
                customer = new LinkedHashMap<>();
                customer.put("consumerId", consumer.getId());
                customer.put("price", consumer.getContract().getPrice());
                customer.put("remainedContractMonths", consumer.getRemainingSubscription() - 1);
                customers.add(customer);
            }
            json.put("contracts", customers);
            distributorsJSON.add(json);
        }
        finalJson.put("distributors", distributorsJSON);
        ObjectMapper objectMapper = new ObjectMapper();
        output.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalJson));
        output.close();
    }
}
