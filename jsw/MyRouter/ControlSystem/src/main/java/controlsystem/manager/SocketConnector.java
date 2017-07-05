package controlsystem.manager;

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
    private static final int SERVER_PORT = 8888;
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

    public void receiveMsg() {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeSocket() throws IOException {
        if (socket != null)
            socket.close();
    }

    public void sendMessage(String msg) {
        try {
            System.out.println("Send Message : " + msg);
            OutputStream stream = socket.getOutputStream();
            stream.write(msg.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reconnectToServer() {
        while(true) {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", SERVER_PORT), 3000);
            } catch (IOException e) {
                continue;
            }
            break;
        }
    }

    public static void sendMsg(String msg) throws IOException {
            getSocket().getOutputStream().write((msg + '\n').getBytes());
    }
}
