package controlsystem.data.config;

/**
 * Created by dsm_025 on 2017-05-02.
 */
public class PortForwardData {
    private String name;
    private String ip;
    private int extPort;
    private int intPort;

    public PortForwardData(String name, String ip, int extPort, int intPort) {
        this.name = name;
        this.ip = ip;
        this.extPort = extPort;
        this.intPort = intPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getExtPort() {
        return extPort;
    }

    public void setExtPort(int extPort) {
        this.extPort = extPort;
    }

    public int getIntPort() {
        return intPort;
    }

    public void setIntPort(int intPort) {
        this.intPort = intPort;
    }
}
