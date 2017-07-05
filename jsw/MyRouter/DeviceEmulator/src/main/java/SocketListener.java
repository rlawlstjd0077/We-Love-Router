import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dsm_025 on 2017-05-07.
 */
public class SocketListener extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);
    private InputStream im;
    private BufferedReader br;

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
        while (true) {
            try {
                im = SocketConnector.getSocket().getInputStream();
                br = new BufferedReader(new InputStreamReader(im));
                String receiveMsg;
                while ((receiveMsg = br.readLine()) != null) {
                    mOnMessageReceived.onMessageReceive(receiveMsg);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }
}
