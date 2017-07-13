package emulator.manger.network;

import com.google.gson.Gson;
import emulator.data.Operation;
import emulator.data.config.Config;
import emulator.data.json.Packet;
import emulator.manger.JSONManager;
import emulator.manger.handler.PacketHandler;
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
public class SocketManager {
    private static final Logger logger = LoggerFactory.getLogger(ControlSystemSocketManager.class);
    Selector selector;
    ServerSocketChannel serverSocketChannel;
    ArrayList<ControlSystem> connections = new ArrayList<>();

    public void startServer() {
        try {
            selector = Selector.open(); // Selector 생성
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 넌블로킹으로 설정
            serverSocketChannel.bind(new InetSocketAddress(5001));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            if (serverSocketChannel.isOpen()) {
                stopServer();
            }
            return;
        }

        Thread thread = new Thread(() -> {
            logger.debug("Socket Server Started");
            while (true) {
                try {
                    int keyCount = selector.select();
                    if (keyCount == 0) {
                        continue;
                    }
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();

                        if (selectionKey.isAcceptable()) {
                            accept(selectionKey);
                        } else if (selectionKey.isReadable()) {
                            receive(selectionKey);
                        } else if (selectionKey.isWritable()) {
                            send(selectionKey);
                        }
                        iterator.remove();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (serverSocketChannel.isOpen()) {
                        stopServer();
                    }
                    break;
                }
            }
        });
        thread.start();
    }

    void receive(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ControlSystem ControlSystem = getClientFromSocketChannel(socketChannel);
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int byteCount = socketChannel.read(byteBuffer);
            byteBuffer.flip();

            if (byteCount == -1) {
                throw new IOException();
            }
            Charset charset = Charset.forName("UTF-8");
            String data = charset.decode(byteBuffer).toString();

            logger.debug("Receive message : " + data + " from " + socketChannel.getRemoteAddress());
            PacketHandler.handle(ControlSystem, data);
            byteBuffer.flip();

        } catch (Exception e) {
            try {
                //TODO 통신 안됨 처리
                connections.remove(getClientFromSocketChannel(socketChannel));
                logger.debug(socketChannel.getRemoteAddress() + " is disconnected");
                socketChannel.close();
            } catch (IOException e2) {
            }
        }
    }

    void accept(SelectionKey selectionKey) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            ControlSystem controlSystem = new ControlSystem(socketChannel);
            logger.debug("Connected " + socketChannel.getRemoteAddress());
            connections.add(controlSystem);
            selectionKey.attach(controlSystem);
            controlSystem.setSendData(JSONManager.bindPacket(socketChannel.getLocalAddress() + "", socketChannel.getRemoteAddress() +"",
                    "req", 0, 0, Operation.MODIFY_CONFIG, new Gson().toJson(Config.configFile)));
        } catch (Exception e) {
            if (serverSocketChannel.isOpen()) {
                stopServer();
            }
        }
    }

    void send(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ControlSystem client = getClientFromSocketChannel(socketChannel);
        //Connection List에서 Socket 읽어 들이는 과정 필요함.
        if(client.getSendData() != null) {
            try {
                Charset charset = Charset.forName("UTF-8");
                ByteBuffer byteBuffer = charset.encode(new Gson().toJson(client.getSendData()));

                socketChannel.write(byteBuffer);
                client.setSendData(null);
            } catch (Exception e) {
                try {
                    String msg = "[클라이언트 통신 안됨: " + socketChannel.getRemoteAddress() + ": "
                            + Thread.currentThread().getName() + "]";
                    connections.remove(this);
                    socketChannel.close();
                } catch (IOException e2) {

                }
            }
            try {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
            }
            selector.wakeup();
        }
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
            selector.wakeup();
        } catch (ClosedChannelException e) {
        }
    }

    void stopServer() {
        logger.debug("Stopped server");
        try {
            Iterator<ControlSystem> iterator = connections.iterator();

            while (iterator.hasNext()) {
                ControlSystem client = iterator.next();
                client.socketChannel.close();
                iterator.remove();
            }

            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                serverSocketChannel.close();
            }

            if (selector != null && selector.isOpen()) {
                selector.close();
            }
        } catch (Exception e) {
        }
    }

    private ControlSystem getClientFromSocketChannel(SocketChannel socketChannel){
        for(ControlSystem system : connections){
            if(system.getSocketChannel().equals(socketChannel)){
                return system;
            }
        }
        return null;
    }

    public class ControlSystem {
        SocketChannel socketChannel;
        Packet sendData = null;

        ControlSystem(SocketChannel socketChannel) throws IOException{
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
            try{
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                selector.wakeup();
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }
    }
}
