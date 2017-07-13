package controlsystem.manager;

import controlsystem.data.Operation;
import controlsystem.data.json.Body;
import controlsystem.data.json.Header;
import controlsystem.data.json.Packet;
import controlsystem.data.json.Seq;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class JsonManager {
    public static Packet bindPacket(String src, String dest, String type, int cur, int end, Operation operation, String subValue){
        Header header = new Header(src, dest, type);
        Seq seq = new Seq(cur, end);
        Body body = new Body(operation.getOperation(), subValue);
        return new Packet(header, body, seq);
    }
}
