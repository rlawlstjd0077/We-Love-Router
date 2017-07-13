package emulator.manger.handler;

import com.google.gson.Gson;
import emulator.data.Operation;
import emulator.data.config.Config;
import emulator.data.config.Wireless;
import emulator.data.json.Packet;
import emulator.manger.JSONManager;
import emulator.manger.network.SocketManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by dsm_045 on 2017-07-12.
 */
public class PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(PacketHandler.class);

    public static void handle(SocketManager.ControlSystem controlSystem, String packetMessage) throws JSONException {
        Packet packet = new Gson().fromJson(packetMessage, Packet.class);
        switch (Operation.fromString(packet.getBody().getOperation())){
            case GET_AP_POWER:

                break;
            case GET_DHCP_POWER:

                break;
            case SET_DHCP_POWER:

                break;
            case SET_AP_POWER:
                break;
            case SET_DHCP_SETTING:

                break;
            case SET_PORTFORWARD:

                break;
            case RESET_ROUTER_SETTINGS:

                break;
            case SET_AP_SETTING:
                JSONObject jsonObject = new JSONObject(packet.getBody().getSubValue());
                Config.configFile.setWireless(new Wireless());
                SocketChannel socketChannel = controlSystem.getSocketChannel();
                try {
                    controlSystem.setSendData(JSONManager.bindPacket(socketChannel.getLocalAddress() + "",
                            socketChannel.getRemoteAddress() + "", "res", 0, 0, Operation.SET_AP_SETTING, "successful receive"));
                } catch (IOException e) {
                }
                break;
        }
    }
}
