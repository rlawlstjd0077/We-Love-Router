package controlsystem.data.config;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class DHCP{
    private boolean powerState;
    private String start;
    private String end;

    public DHCP(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}