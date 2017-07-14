package controlsystem.data.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class ConnectionList {
    private List<Connection> conList = new ArrayList<>();

    public ConnectionList(List<Connection> connectionList) {
        this.conList = connectionList;
    }

    public List<Connection> getConList() {
        return conList;
    }

    public void setConList(List<Connection> conList) {
        this.conList = conList;
    }
}
