package emulator.data.json;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class Header {
    private String src;
    private String dest;
    private String type;
    private String result;

    public Header(String src, String dest, String type, String result) {
        this.src = src;
        this.dest = dest;
        this.type = type;
        this.result = result;
    }

    public Header(String src, String dest, String type) {
        this(src, dest, type, null);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
