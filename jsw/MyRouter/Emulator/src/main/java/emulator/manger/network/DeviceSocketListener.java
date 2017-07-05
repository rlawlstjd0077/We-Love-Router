package emulator.manger.network;

import emulator.data.Connection;

import java.io.*;
import java.net.Socket;

/**
 * Created by dsm_025 on 2017-05-02.
 */
public class DeviceSocketListener extends Thread{
    private Socket socket;
    private BufferedReader br = null;
    private PrintWriter pw = null;
    public interface OnConnectionRefused{
        void onConnectionRefused();
    }

    private OnConnectionRefused mConnectionRefused;

    public void setOnConnectionRefused(OnConnectionRefused mConnectionRefused){
        this.mConnectionRefused = mConnectionRefused;
    }

    public DeviceSocketListener(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            service();
        } catch (IOException e) {
            mConnectionRefused.onConnectionRefused();
        }
    }

    private void service() throws IOException {
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);
        String str = null;
        while(true){
            str = br.readLine();
            if(str == null){

            }
        }
    }

    public void sendMessage(String msg) {
        try {
            System.out.println("Send Message : " + msg);
            OutputStream stream = socket.getOutputStream();
            stream.write(msg.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
