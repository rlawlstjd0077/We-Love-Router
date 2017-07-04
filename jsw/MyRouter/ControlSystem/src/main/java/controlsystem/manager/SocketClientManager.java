package controlsystem.manager;

import com.google.gson.Gson;
import controlsystem.data.Config;
import controlsystem.data.Connection;
import controlsystem.data.ConnectionList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class SocketClientManager extends Thread{
    private static boolean connectState;

    public interface OnConnectionChanged{
        void onConnectionChange(boolean state);
    }

    private SocketListener.OnConnectionChanged mOnConnectionChanged;

    public void setOnConnectionChanged(SocketListener.OnConnectionChanged mOnConnectionChanged) {
        this.mOnConnectionChanged = mOnConnectionChanged;
    }

    @Override
    public void run() {
        super.run();
        SocketListener listener = new SocketListener();
        connectState = true;
        listener.setOnMessageReceived(msg -> {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(msg);
                switch (jsonObject.getString("type")){
                    case "config" :
                        Config.configFile = new Gson().fromJson(jsonObject.getString("data"), Config.class);
                        break;
                    case "con_list":
                        Connection.connectionList = new Gson().fromJson(jsonObject.getString("data"), ConnectionList.class).getConList();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        listener.setOnConnectionChanged(state -> {
            mOnConnectionChanged.onConnectionChange(state);
            setConnectState(state);
        });
        listener.start();
    }

    private static void setConnectState(boolean state){
        connectState = state;
    }

    public static boolean isConnectState(){
        return connectState;
    }
}
