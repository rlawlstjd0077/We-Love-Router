package emulator;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class Equipment {
    private String model;
    private String description;
    public boolean powerState;

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isPowerState() {
        return powerState;
    }
    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }
}
