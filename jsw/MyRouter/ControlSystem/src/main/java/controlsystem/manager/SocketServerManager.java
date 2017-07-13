package controlsystem.manager;

import com.google.gson.Gson;
import controlsystem.data.config.Config;
import controlsystem.data.config.ConnectionList;
import controlsystem.data.json.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dsm_045 on 2017-07-11.
 */
public class SocketServerManager {
    private static final Logger logger = LoggerFactory.getLogger(SocketServerManager.class);

    public interface OnRPIConnectedListener {
        public void onConnect(Emulator emulator);
        public void onDisConnect(Emulator emulator);
    }

    public OnRPIConnectedListener mOnRPIConnected;

    public void setmOnRPIConnectedListener(OnRPIConnectedListener mOnRPIConnected) {
        this.mOnRPIConnected = mOnRPIConnected;
    }

    public interface OnMessageReceiveListener {
        public void onReceive(Emulator emulator, String packetMessage);
    }

    public OnMessageReceiveListener mOnMessageReceived;

    public void setmOnMessageReceivedListener(OnMessageReceiveListener mOnMessageReceived) {
        this.mOnMessageReceived = mOnMessageReceived;
    }

    Selector selector;
    ArrayList<Emulator> connections = new ArrayList<>();

    public void startSocketManager() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                selector = Selector.open();

                InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5001);
                SocketChannel channel = SocketChannel.open(hostAddress);
                channel.configureBlocking(false);
                Emulator emulator = new Emulator(channel);
                connections.add(emulator);
                mOnRPIConnected.onConnect(emulator);
                channel.register(selector, SelectionKey.OP_READ);
                hostAddress = new InetSocketAddress("localhost", 5002);
                channel = SocketChannel.open(hostAddress);
                channel.configureBlocking(false);
                emulator = new Emulator(channel);
                connections.add(emulator);
                mOnRPIConnected.onConnect(emulator);
                channel.register(selector, SelectionKey.OP_READ);

                while (true) {
                    int keyCount = selector.select();
                    if (keyCount == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();

                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        } else if (selectionKey.isWritable()) {
                            send(selectionKey);
                        }
                        iterator.remove();
                    }
                }
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    void read(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        Emulator emulator = getClientFromSocketChannel(socketChannel);
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int byteCount = socketChannel.read(byteBuffer);
            byteBuffer.flip();

            if (byteCount == -1) {
                return;
            }
            Charset charset = Charset.forName("UTF-8");
            String data = charset.decode(byteBuffer).toString();

            logger.debug("Receive message : " + data + " from " + socketChannel.getRemoteAddress());
            PacketHandler.handle(emulator, data);
            byteBuffer.flip();

        } catch (Exception e) {
            try {
                connections.remove(emulator);
                socketChannel.close();
                mOnRPIConnected.onDisConnect(emulator);
            } catch (IOException e2) {
            }
        }
    }

    void send(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        Emulator client = getClientFromSocketChannel(socketChannel);
        //Connection List에서 Socket 읽어 들이는 과정 필요함.
        if(client.getSendData() != null) {
            try {
                Charset charset = Charset.forName("UTF-8");
                ByteBuffer byteBuffer = charset.encode(new Gson().toJson(client.getSendData()));

                socketChannel.write(byteBuffer);
                client.setSendData(null);
            } catch (Exception e) {
                try {
                    connections.remove(this);
                    socketChannel.close();
                } catch (IOException e2) {

                }
            }
        }
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
            selector.wakeup();
        } catch (ClosedChannelException e) {
        }
    }

    private Emulator getClientFromSocketChannel(SocketChannel socketChannel){
        for(Emulator client : connections){
            if(client.getSocketChannel().equals(socketChannel)){
                return client;
            }
        }
        return null;
    }

    public class Emulator {
        private SocketChannel socketChannel;
        private Packet sendData = null;
        private boolean loginState;
        private Config config;
        private ConnectionList connectionList;

        Emulator(SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);
        }

        public SocketChannel getSocketChannel() {
            return socketChannel;
        }

        public void setSocketChannel(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        public Packet getSendData() {
            return sendData;
        }

        public void setSendData(Packet sendData) {
            this.sendData = sendData;
            try {
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                selector.wakeup();
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }

        public boolean isLoginState() {
            return loginState;
        }

        public void setLoginState(boolean loginState) {
            this.loginState = loginState;
        }

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        public ConnectionList getConnectionList() {
            return connectionList;
        }

        public void setConnectionList(ConnectionList connectionList) {
            this.connectionList = connectionList;
        }
    }
}
