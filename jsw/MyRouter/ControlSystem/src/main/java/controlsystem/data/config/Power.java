package controlsystem.data.config;

/**
 * Created by dsm_045 on 2017-07-13.
 */
public class Power {
    private boolean apPower;
    private boolean dhcpPower;

    public boolean isApPower() {
        return apPower;
    }

    public void setApPower(boolean apPower) {
        this.apPower = apPower;
    }

    public boolean isDhcpPower() {
        return dhcpPower;
    }

    public void setDhcpPower(boolean dhcpPower) {
        this.dhcpPower = dhcpPower;
    }
}
