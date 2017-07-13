package emulator.data.json;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class Body {
    private String operation;
    private String subValue;

    public Body(String operation, String subValue) {
        this.operation = operation;
        this.subValue = subValue;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSubValue() {
        return subValue;
    }

    public void setSubValue(String subValue) {
        this.subValue = subValue;
    }
}
