package emulator;

import com.google.gson.Gson;
import emulator.data.*;
import emulator.manger.JSONManager;
import emulator.manger.listener.DHCPPowerListener;
import emulator.manger.network.ControlSystemSocketListener;
import emulator.manger.network.ControlSystemSocketManager;
import emulator.manger.network.DeviceSocketManager;
import emulator.manger.network.SocketManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class Emulator extends Equipment {
    private static final Logger logger = LoggerFactory.getLogger(Emulator.class);
    private Config settingData;
    private SocketManager socketManager;

    public void startEmulator(){
        logger.info("Router Emulator is on");
        JSONManager.readConfigFile();
        DeviceSocketManager deviceListener = new DeviceSocketManager();
        deviceListener.start();

        refresh();
        socketManager = new SocketManager();
        try {
            socketManager.startSocketManager();
        } catch (IOException e) {
        }
    }

    private void refresh(){
        JSONManager.readConfigFile();
    }

    @Override
    public void setPowerState(boolean state){
        System.out.println("Emulator is " + (state ? "On" : "Off"));
        this.powerState = state;
    }

    private void settingChangeHandler(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        switch (jsonObject.getString("type")){
            case "wireless":
                Config.configFile.setWireless(new Gson().fromJson(jsonObject.getString("data"), Wireless.class));
                logger.info(jsonObject.getString("type") + " Type is Changed");
                break;
            case "portForward":
                Config.configFile.setPortForward(new Gson().fromJson(jsonObject.getString("data"), PortForward.class));
                logger.info(jsonObject.getString("type") + " Type is Changed");
                break;
            case "startUrl":
                Config.configFile.setStartUrl(new Gson().fromJson(jsonObject.getString("data"), StartURL.class));
                logger.info(jsonObject.getString("type") + " Type is Changed");
                break;
            case "timeLimit":
                Config.configFile.setTimeLimit(new Gson().fromJson(jsonObject.getString("data"), TimeLimit.class));
                logger.info(jsonObject.getString("type") + " Type is Changed");
                break;
            case "dhcp":
                Config.configFile.setDHCP(new Gson().fromJson(jsonObject.getString("data"), DHCP.class));
                logger.info(jsonObject.getString("type") + " Type is Changed");
                break;
            case "reset":
                JSONManager.parseAndSave(JSONManager.readDefConfigFile());
                JSONObject config = new JSONObject();
                config.put("type", "config");
                config.put("data", JSONManager.readDefConfigFile());
                try {
                    ControlSystemSocketManager.sendMsg(config.toString());
                } catch (IOException e) {
                }
                logger.info("Config File reset");
                break;
            case "dhcpState":
                DHCPPowerListener.setDhcpPower(jsonObject.getBoolean("state"));
                break;
            case "powerOff":
                logger.info("Emulator Power is Off");
                System.exit(999);
                break;
        }
        JSONManager.bindJsonFile(Config.getConfigFile());
    }
}
