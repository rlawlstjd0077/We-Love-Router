package controlsystem.data;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class Wireless{
    private String ssid;
    private String password;

    public Wireless(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
