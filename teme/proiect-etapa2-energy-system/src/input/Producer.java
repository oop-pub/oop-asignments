package input;

import simulate.Change;
import strategies.EnergyChoiceStrategyType;

import java.util.HashMap;
import java.util.List;

import java.util.LinkedList;
import java.util.Map;

public class Producer {
    private int id;
    private String energyType;
    private int maxDistributors;
    private float priceKW;
    private int energyPerDistributor;
    private List<Distributor> currentDistributors;
    private final Map<Integer, List<Integer>> monthlyStats;

    public Producer() {
        currentDistributors = new LinkedList<>();
        monthlyStats = new HashMap<>();
    }

    public List<Distributor> getCurrentDistributors() {
        return currentDistributors;
    }

    public void setCurrentDistributors(List<Distributor> currentDistributors) {
        this.currentDistributors = currentDistributors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public void update(Change change) {
        energyPerDistributor = change.getNewValue();
        for(Distributor distributor : currentDistributors) {
            distributor.setRenewProducers(true);
        }
    }

    public boolean hasRoom() {
        return currentDistributors.size() < maxDistributors;
    }

    public void addDistributor(Distributor distributor) {
        currentDistributors.add(distributor);
    }

    public void removeDistributor(Distributor distributor) {
        currentDistributors.remove(distributor);
    }

    public Map<Integer, List<Integer>> getMonthlyStats() {
        return monthlyStats;
    }

    public void addMonthlyStat(int month) {
        List<Integer> stat = new LinkedList<>();
        for(Distributor distributor : currentDistributors) {
            stat.add(distributor.getId());
        }
        monthlyStats.put(month, stat);
    }
}
