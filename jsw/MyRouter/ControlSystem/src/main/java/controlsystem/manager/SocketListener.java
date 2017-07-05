package controlsystem.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class SocketListener extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);
    private InputStream im;
    private BufferedReader br;

    public interface OnConnectionChanged{
        void onConnectionChange(boolean state);
    }

    private OnConnectionChanged mOnConnectionChanged;

    public void setOnConnectionChanged(OnConnectionChanged mOnConnectionChanged) {
        this.mOnConnectionChanged = mOnConnectionChanged;
    }

    public interface OnMessageReceived {
        void onMessageReceive(String msg);
    }

    private OnMessageReceived mOnMessageReceived;

    public void setOnMessageReceived(OnMessageReceived onMessageReceived) {
        this.mOnMessageReceived = onMessageReceived;
    }

    public SocketListener(){
        while(true) {
            try {
                im = SocketConnector.getSocket().getInputStream();
                br = new BufferedReader(new InputStreamReader(im));
            } catch (IOException e) {
                continue;
            }
            break;
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
                    if(receiveMsg.equals("close")){
                        SocketConnector.closeSocket();
                        return;
                    }
                }
            } catch (IOException e) {
                mOnConnectionChanged.onConnectionChange(false);
                try {
                    logger.error("Emulator power Off Try Reconnection");
                    SocketConnector.reconnectToServer();
                    mOnConnectionChanged.onConnectionChange(true);
                    logger.info("Reconnection Success");
                    im = SocketConnector.getSocket().getInputStream();
                    br = new BufferedReader(new InputStreamReader(im));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }
    }
}
