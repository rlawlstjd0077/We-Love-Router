package emulator.data.json;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class Packet {
    private Header header;
    private Body body;
    private Seq seq;

    public Packet(Header header, Body body, Seq seq) {
        this.header = header;
        this.body = body;
        this.seq = seq;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
