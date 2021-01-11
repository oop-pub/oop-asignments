package simulate;

public class Change {
    private int id;
    private int newValue;
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public void setEnergyPerDistributor(int newValue) {
        this.newValue = newValue;
    }

    public void setInfrastructureCost(int newValue) {
        this.newValue = newValue;
    }
}
