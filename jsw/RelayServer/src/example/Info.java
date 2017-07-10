package example;

/**
 * Created by dsm_045 on 2017-07-10.
 */
public class Info {
    private String id;
    private String ip;

    public Info(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
