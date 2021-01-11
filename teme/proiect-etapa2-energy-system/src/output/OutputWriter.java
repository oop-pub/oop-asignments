package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import input.*;
import org.json.simple.JSONArray;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public final class OutputWriter {
    private OutputWriter() { }

    /**
     * Writes the output file
     * @param inputParser
     * @param filename
     * @throws IOException
     */
    public static void write(final InputParser inputParser, final String filename)
            throws IOException {
        Players<Distributor> distributors = inputParser.getDistributors();
        Players<Consumer> consumers = inputParser.getConsumers();
        Players<Producer> producers = inputParser.getProducers();
        JSONArray distributorsJSON = new JSONArray();
        JSONArray consumersJSON = new JSONArray();
        JSONArray producersJSON = new JSONArray();
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
        Map<String, Object> stat;
        JSONArray customers;

        for (Map.Entry<Integer, Distributor> entry : distributors.getMap().entrySet()) {
            json = new LinkedHashMap<>();
            json.put("id", entry.getKey());
            json.put("energyNeededKW", entry.getValue().getEnergyNeededKW());
            json.put("contractCost", entry.getValue().getContract().getPrice());
            json.put("budget", entry.getValue().getInitialBudget());
            json.put("producerStrategy", entry.getValue().getProducerStrategy());
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
        JSONArray stats;
        for (Map.Entry<Integer, Producer> entry : producers.getMap().entrySet()) {
            json = new LinkedHashMap<>();
            json.put("id", entry.getKey());
            json.put("maxDistributors", entry.getValue().getMaxDistributors());
            json.put("priceKW", entry.getValue().getPriceKW());
            json.put("energyType", entry.getValue().getEnergyType());
            json.put("energyPerDistributor", entry.getValue().getEnergyPerDistributor());
            stats = new JSONArray();
            for (Map.Entry<Integer, List<Integer>> statEntry: entry.getValue().getMonthlyStats().entrySet()) {
                stat = new LinkedHashMap<>();
                stat.put("month", statEntry.getKey());
                Collections.sort(statEntry.getValue());
                stat.put("distributorsIds", statEntry.getValue());
                stats.add(stat);
            }
            json.put("monthlyStats", stats);
            producersJSON.add(json);
        }

        finalJson.put("energyProducers", producersJSON);
        ObjectMapper objectMapper = new ObjectMapper();
        output.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalJson));
        output.close();
    }
}
