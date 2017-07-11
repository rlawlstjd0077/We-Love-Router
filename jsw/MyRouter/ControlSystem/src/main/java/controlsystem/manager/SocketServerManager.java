package controlsystem.manager;

import controlsystem.controller.MainController;
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
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public interface OnRPIConnectedListener {
        public void onConnected(ArrayList<Emulator> emulatorList);
    }

    public OnRPIConnectedListener mOnRPIConnected;

    public void setmOnRPIConnectedListener(OnRPIConnectedListener mOnRPIConnected) {
        this.mOnRPIConnected = mOnRPIConnected;
    }

    Selector selector;
    ServerSocketChannel serverSocketChannel;
    ArrayList<Emulator> connections = new ArrayList<>();

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
            byteBuffer.flip();

        } catch (Exception e) {
            try {
                //TODO 통신 안됨 처리
                connections.remove(this);
                socketChannel.close();
            } catch (IOException e2) {
            }
        }
    }

    void accept(SelectionKey selectionKey) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            Emulator client = new Emulator(socketChannel);
            logger.debug("Connected " + socketChannel.getRemoteAddress());
            connections.add(client);
            mOnRPIConnected.onConnected(connections);
            selectionKey.attach(client);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            if (serverSocketChannel.isOpen()) {
                stopServer();
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
                ByteBuffer byteBuffer = charset.encode(client.getSendData());

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
        SocketChannel socketChannel;
        String sendData = null;

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

        public String getSendData() {
            return sendData;
        }

        public void setSendData(String sendData) {
            this.sendData = sendData;
            try {
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                selector.wakeup();
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }
    }

    void stopServer() {
        logger.debug("Stopped server");
        try {
            Iterator<Emulator> iterator = connections.iterator();

            while (iterator.hasNext()) {
                Emulator client = iterator.next();
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
}
