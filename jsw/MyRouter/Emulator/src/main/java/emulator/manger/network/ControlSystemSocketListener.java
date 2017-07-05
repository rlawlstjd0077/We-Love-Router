package emulator.manger.network;

import emulator.manger.JSONManager;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dsm_025 on 2017-04-29.
 */
public class ControlSystemSocketListener extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ControlSystemSocketListener.class);

    private InputStream im;
    private BufferedReader br;

    public interface OnMessageReceived {
        public void onMessageReceive(String msg);
    }

    private OnMessageReceived mOnMessageReceived;

    public void setmOnMessageReceived(OnMessageReceived onMessageReceived) {
        this.mOnMessageReceived = onMessageReceived;
    }

    public ControlSystemSocketListener() {
        logger.info("ControlSystem Socket is On");
        try {
            im = ControlSystemSocketManager.getSocket().getInputStream();
            br = new BufferedReader(new InputStreamReader(im));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                String receiveMsg;
                while ((receiveMsg = br.readLine()) != null) {
                    mOnMessageReceived.onMessageReceive(receiveMsg);
                }
            } catch (IOException e) {
                 try {
                     logger.error("Lose Connection, Try Reconnection");
                     ControlSystemSocketManager.resetSocket();
                     ControlSystemSocketManager.getSocket();
                     logger.info("Reconnection Success");
                     im = ControlSystemSocketManager.getSocket().getInputStream();
                     br = new BufferedReader(new InputStreamReader(im));
                     try {
                         ControlSystemSocketManager.sendMsg(JSONManager.bindConfigMsg(JSONManager.readConfigFile()));
                     } catch (IOException e2) {
                     } catch (JSONException e1) {
                     }
                 } catch (IOException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }
    }
}
