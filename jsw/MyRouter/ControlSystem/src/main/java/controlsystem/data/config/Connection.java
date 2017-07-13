package controlsystem.data.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2017-05-07.
 */
public class Connection {
    public static List<Connection> connectionList = new ArrayList<>();
    private String adreess;
    private String assignedAddress;

    public Connection(String adreess, String assignedAddress) {
        this.adreess = adreess;
        this.assignedAddress = assignedAddress;
    }


    public String getAdreess() {
        return adreess;
    }

    public void setAdreess(String adreess) {
        this.adreess = adreess;
    }

    public String getAssignedAddress() {
        return assignedAddress;
    }

    public void setAssignedAddress(String assignedAddress) {
        this.assignedAddress = assignedAddress;
    }

    public boolean isEuqalAssigned(String assignedAddress){
        for(Connection con : connectionList){
            if(con.getAssignedAddress().equals(assignedAddress)){
                return true;
            }
        }
        return false;
    }

    public Connection getEuqalAssigned(String assignedAddress){
        for(Connection con : connectionList){
            if(con.getAssignedAddress().equals(assignedAddress)){
                return con;
            }
        }
        return null;
    }
}
