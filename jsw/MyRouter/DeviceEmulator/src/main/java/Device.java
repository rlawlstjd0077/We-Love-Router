import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dsm_025 on 2017-05-07.
 */
public class Device {
    public void doConnect(){
        SocketManager manager = new SocketManager();
        manager.start();
    }
}
