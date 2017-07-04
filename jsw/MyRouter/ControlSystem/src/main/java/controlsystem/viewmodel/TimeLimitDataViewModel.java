package controlsystem.viewmodel;

import controlsystem.data.Config;
import controlsystem.data.TimeLimit;
import controlsystem.data.TimeLimitData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-05-01.
 */
public class TimeLimitDataViewModel {
    public static ObservableList<TimeLimitTableRowViewModel> getDatas(){
        ArrayList<TimeLimitTableRowViewModel> list = new ArrayList<>();
        for(TimeLimitData timeLimit : Config.configFile.getTimeLimit().getTimeLimit_list()){
            TimeLimitTableRowViewModel viewModel = new TimeLimitTableRowViewModel(new SimpleStringProperty(timeLimit.getName()),
                    new SimpleStringProperty(timeLimit.getStart()), new SimpleStringProperty(timeLimit.getEnd()));
            list.add(viewModel);
        }
        return FXCollections.observableArrayList(list);
    }
}
