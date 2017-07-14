package controlsystem.manager;

import com.google.gson.Gson;
import controlsystem.data.Operation;
import controlsystem.data.config.Config;
import controlsystem.data.config.ConnectionList;
import controlsystem.data.json.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(PacketHandler.class);

    public static void handle(SocketManager.Emulator emulator, String packetMessage){
        Packet packet = new Gson().fromJson(packetMessage, Packet.class);

        switch (Operation.fromString(packet.getBody().getOperation())){
            case MODIFY_CONFIG:
                try {
                    emulator.setConfig(new Gson().fromJson(packet.getBody().getSubValue(), Config.class));
                    logger.debug("Config file setting at " + emulator.getSocketChannel().getRemoteAddress());
                } catch (IOException e) {
                }
                break;
            case CONNECTION_LIST:
                try {
                    emulator.setConnectionList(new Gson().fromJson(packet.getBody().getSubValue(), ConnectionList.class));
                    logger.debug("ConnectionList changed at " + emulator.getSocketChannel().getRemoteAddress());
                } catch (IOException e) {
                }
                break;
        }
    }
}
