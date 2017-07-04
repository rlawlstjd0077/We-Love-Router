package emulator.manger.listener;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class DHCPPowerListener {
    private static boolean dhcpPower = true;

    public interface OnPowerChaned {
        public void onItemClicked();
    }
    private static OnPowerChaned mOnPowerChaned;

    public static void setmOnPowerChaned(OnPowerChaned onPowerChaned){
        mOnPowerChaned = onPowerChaned;
    }

    public static boolean isDhcpPower() {
        return dhcpPower;
    }

    public static void setDhcpPower(boolean dhcpPower) {
        DHCPPowerListener.dhcpPower = dhcpPower;
        if(!dhcpPower){
            mOnPowerChaned.onItemClicked();
        }
    }
}
