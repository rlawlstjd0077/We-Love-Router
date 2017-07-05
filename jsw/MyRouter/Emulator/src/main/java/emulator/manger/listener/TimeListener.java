package emulator.manger.listener;

import emulator.data.Config;
import emulator.data.TimeLimit;
import emulator.data.TimeLimitData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class TimeListener extends TimerTask {
    public interface OnTimeChanged {
        public void onItemClicked(boolean state);
    }
    private OnTimeChanged mOnTimeChanged;

    public void setmOnTimeChanged(OnTimeChanged onTimeChanged){
        this.mOnTimeChanged = onTimeChanged;
    }

    @Override
    public void run() {
        boolean tmp = isValidUseTime();
        if(tmp != isValidUseTime()) {
            mOnTimeChanged.onItemClicked(isValidUseTime());
        }
    }

    private boolean isValidUseTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm", Locale.KOREA);
        TimeLimit limit = Config.getConfigFile().getTimeLimit();
        for(TimeLimitData data : limit.getTimeLimit_list()) {
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();
            LocalTime currentTime = LocalTime.now();
            LocalTime startTime = null;
            LocalTime endTime = null;
            try {
                startCal.setTime(format.parse(data.getStart()));
                endCal.setTime(format.parse(data.getEnd()));
                startTime = LocalTime.of(startCal.get(Calendar.HOUR_OF_DAY), startCal.get(Calendar.MINUTE));
                endTime = LocalTime.of(endCal.get(Calendar.HOUR_OF_DAY), endCal.get(Calendar.MINUTE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(currentTime.isAfter(startTime) && currentTime.isBefore(endTime)){
                return false;
            }
        }
        return true;
    }
}
