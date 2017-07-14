package emulator.data;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public enum Operation {
    SET_AP_POWER("SET_AP_POWER"),
    GET_AP_POWER("GET_AP_POWER"),
    SET_DHCP_POWER("SET_DHCP_POWER"),
    GET_DHCP_POWER("GET_DHCP_POWER"),
    SET_DHCP_SETTING("SET_DHCP_SETTING"),
    SET_PORTFORWARD("SET_PORTFORWARD"),
    RESET_ROUTER_SETTINGS("RESET_ROUTER_SETTINGS"),
    MODIFY_CONFIG("MODIFY_CONFIG"),
    CONNECTION_LIST("CONNECTION_LIST"),
    SET_AP_SETTING("SET_AP_SETTING");

    private String operation;

    private Operation(String operation){
        this.operation = operation;
    }

    public String getOperation(){
        return operation;
    }

    public static Operation fromString(String value){
        for(Operation operation : values()){
            if(operation.getOperation().equals(value)){
                return operation;
            }
        }
        return null;
    }
}
