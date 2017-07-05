package controlsystem.data;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class Config {
    public static Config configFile;

    private Wireless wireless;
    private PortForward portForward;
    private StartURL startUrl;
    private TimeLimit timeLimit;
    private DHCP dhcp;
    private LoginInform login;

    public static Config getConfigFile() {
        return configFile;
    }

    public static void setConfigFile(Config configFile) {
        Config.configFile = configFile;
    }

    public Wireless getWireless() {
        return wireless;
    }

    public void setWireless(Wireless wireless) {
        this.wireless = wireless;
    }

    public PortForward getPortForward() {
        return portForward;
    }

    public void setPortForward(PortForward portForward) {
        this.portForward = portForward;
    }

    public StartURL getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(StartURL startUrl) {
        this.startUrl = startUrl;
    }

    public TimeLimit getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(TimeLimit timeLimit) {
        this.timeLimit = timeLimit;
    }

    public DHCP getDhcp() {
        return dhcp;
    }

    public void setDhcp(DHCP dhcp) {
        this.dhcp = dhcp;
    }

    public LoginInform getLogin() {
        return login;
    }

    public void setLogin(LoginInform login) {
        this.login = login;
    }
}
