import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by dsm_025 on 2017-05-07.
 */
public class SocketManager extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(SocketManager.class);
    private static final int SERVER_PORT = 8881;
    private static Socket socket;

    public static void closeSocket() throws IOException {
        if (socket != null)
            socket.close();
    }

    @Override
    public void run() {
        SocketListener listener = new SocketListener();
        listener.setOnMessageReceived(msg -> {
                logger.info(msg);
            }
        );
        listener.start();
    }
}
