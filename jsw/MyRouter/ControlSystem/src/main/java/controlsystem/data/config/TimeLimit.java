package controlsystem.data.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class TimeLimit {
    private List<TimeLimitData> timeLimit_list = new ArrayList<>();

    public TimeLimit(List<TimeLimitData> timeLimit_list) {
        this.timeLimit_list = timeLimit_list;
    }

    public List<TimeLimitData> getTimeLimit_list() {
        return timeLimit_list;
    }

    public void setTimeLimit_list(List<TimeLimitData> timeLimit_list) {
        this.timeLimit_list = timeLimit_list;
    }
}
