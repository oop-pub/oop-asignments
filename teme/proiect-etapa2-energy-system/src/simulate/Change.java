package simulate;

public final class Change {
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

    public void setNewValue(int value) {
        newValue = value;
    }

    public void setEnergyPerDistributor(int value) {
        newValue = value;
    }

    public void setInfrastructureCost(int value) {
        newValue = value;
    }
}
