import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by dsm_025 on 2017-04-29.
 */
public class SocketConnector {
    private static final Logger logger = LoggerFactory.getLogger(SocketConnector.class);
    private static final int SERVER_PORT = 8881;
    private static Socket socket;

    public static Socket getSocket() throws IOException {
        if (socket == null) {
            socket = new Socket();
        }

        while (!socket.isConnected()) {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", SERVER_PORT), 3000);
            logger.info("Connected to Emulator");
        }
        return socket;
    }

    public static void sendMsg(String msg) throws IOException
    {
        getSocket().getOutputStream().write((msg + '\n').getBytes());
    }
}
