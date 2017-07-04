package emulator.manger.network;

import com.google.gson.Gson;
import emulator.data.Connection;
import emulator.data.ConnectionList;
import emulator.manger.listener.TimeListener;
import emulator.manger.emulator.DHCPManager;
import emulator.manger.listener.DHCPPowerListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by dsm_025 on 2017-05-02.
 */
public class DeviceSocketManager extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(DeviceSocketManager.class);
    private static ArrayList<Socket> connectionList = new ArrayList<>();
    private final int PORT = 8881;
    ServerSocket ss;
    Socket s;

    @Override
    public void run() {
        logger.info("Device Socket is On");
        try {
            DHCPPowerListener.setmOnPowerChaned(() -> {
                for(Socket socket : connectionList){
                    sendMsg("DHCP Power is " + (DHCPPowerListener.isDhcpPower() ? "On" : "Off"), socket);
                }
            });
            TimeListener timeListener = new TimeListener();
            new Timer().schedule(timeListener, 1000, 60000);
            timeListener.setmOnTimeChanged((state) -> {
                for(Socket socket : connectionList){
                    if(!state) {
                        sendMsg("Not Time to Use", socket);
                    }
                }
            });

            ss = new ServerSocket(PORT);
            while (true) {
                s = ss.accept();
                Connection connection = new Connection(s.getInetAddress().toString(), DHCPManager.assignIP());
                DeviceSocketListener st = new DeviceSocketListener(s);
                connectionList.add(s);
                st.setOnConnectionRefused(() -> {
                    Connection.connectionList.remove(connection);
                    connectionList.remove(s);
                    notifyConnectionListChanged();
                });
                st.start();
                Connection.connectionList.add(connection);
                sendMsg("Yout Device assigned " + connection.getAssignedAddress(), s);
                sendMsg("DHCP Power is " + (DHCPPowerListener.isDhcpPower() ? "On" : "Off"), s);
                notifyConnectionListChanged();
                logger.info(s.getInetAddress() + " Device Connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyConnectionListChanged() {
        logger.info("Connection List Changed");
        JSONObject jsonObject = new JSONObject();
        ConnectionList connectionList = new ConnectionList(Connection.connectionList);
        try {
            jsonObject.put("type", "con_list");
            jsonObject.put("data", new Gson().toJson(connectionList));
            ControlSystemSocketManager.sendMsg(jsonObject.toString());
        } catch (JSONException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendConList() {
        JSONObject jsonObject = new JSONObject();
        ConnectionList connectionList = new ConnectionList(Connection.connectionList);
        try {
            jsonObject.put("type", "con_list");
            jsonObject.put("data", new Gson().toJson(connectionList));
            ControlSystemSocketManager.sendMsg(jsonObject.toString());
        } catch (JSONException e) {
        } catch (IOException e) {
        }
    }

    public static void sendMsg(String msg, Socket s) {
        try {
            s.getOutputStream().write((msg + '\n').getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
