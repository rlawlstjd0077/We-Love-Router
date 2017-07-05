package emulator.manger.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dsm_025 on 2017-04-29.
 */
public class ControlSystemSocketManager {
    private static final Logger logger = LoggerFactory.getLogger(ControlSystemSocketManager.class);
    private static final int PORT = 8888;
    private static Socket socket;
    private static ServerSocket serverSocket;
    private InputStream is;
    private BufferedReader br;

    public static Socket getSocket() throws IOException {
        if(socket == null){
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            logger.info("Connected to Control System");
        }
        return socket;
    }

    public static void closeSocket() throws IOException {
        if ( socket != null ) {
            socket.close();
        }
    }

    public static void resetSocket() throws IOException {
        socket = null;
        serverSocket.close();
    }

    public static void sendMsg(String msg) throws IOException
    {
        getSocket().getOutputStream().write((msg + '\n').getBytes());
    }
}
