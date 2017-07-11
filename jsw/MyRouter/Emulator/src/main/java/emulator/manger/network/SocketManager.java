package emulator.manger.network;

import com.google.gson.Gson;
import emulator.Emulator;
import emulator.data.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dsm_045 on 2017-07-11.
 */
public class SocketManager {
    private static final Logger logger = LoggerFactory.getLogger(ControlSystemSocketManager.class);
    private Selector selector;
    private ArrayList<ControlSystem> connections = new ArrayList<>();

    public void startSocketManager() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                selector = Selector.open();

                InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5001);
                SocketChannel channel = SocketChannel.open(hostAddress);
                channel.configureBlocking(false);
                ControlSystem controlSystem = new ControlSystem(channel);
                controlSystem.setSendData(new Gson().toJson(Config.configFile));
                connections.add(controlSystem);


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
    private void read(SelectionKey key) throws IOException {
        String data;
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int byteCount = channel.read(buffer);
        buffer.flip();

//        if (byteCount == -1) {
//            channel.register(selector, SelectionKey.OP_READ);
//        }

        Charset charset = Charset.forName("UTF-8");
        data = charset.decode(buffer).toString();
        System.out.println("Got: " + new String(data));
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void send(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            Charset charset = Charset.forName("UTF-8");
            ByteBuffer byteBuffer = charset.encode(getClientFromSocketChannel(channel).sendData);

            channel.write(byteBuffer);
        } catch (IOException e) {
        }
        try {
            channel.register(selector, SelectionKey.OP_READ);
            selector.wakeup();
        } catch (ClosedChannelException e) {
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
        String sendData = null;

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

        public String getSendData() {
            return sendData;
        }

        public void setSendData(String sendData) {
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
