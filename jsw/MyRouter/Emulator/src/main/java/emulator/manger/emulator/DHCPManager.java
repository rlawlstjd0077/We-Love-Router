package emulator.manger.emulator;

import emulator.data.config.Config;
import emulator.data.config.Connection;
import emulator.data.config.DHCP;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class DHCPManager {
    public static String assignIP(){
        DHCP dhcp = Config.configFile.getDhcp();
        String ip = dhcp.getStart().substring(0, dhcp.getStart().length() - dhcp.getStart().split("\\.")[3].length());
        int start = Integer.parseInt(dhcp.getStart().substring(dhcp.getStart().length() - dhcp.getStart().split("\\.")[3].length()));
        int end = Integer.parseInt(dhcp.getEnd().substring(dhcp.getEnd().length() - dhcp.getEnd().split("\\.")[3].length()));

        while(true){
            int random = randomRange(start, end);
            if(checkIPDistinct(ip + random)){
                ip += random;
                break;
            }
        }
        return ip;
    }

    private static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }
    private static boolean checkIPDistinct(String ip){
        for(Connection connection : Connection.connectionList){
            if((ip).equals(connection.getAssignedAddress())){
                return false;
            }
        }
        return true;
    }
}
