package controlsystem.viewmodel;

import controlsystem.data.Config;
import controlsystem.data.PortForwardData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class PortForwardDataViewModel {
    public static ObservableList<PortForwardTableRowViewModel> getDatas(){
        ArrayList<PortForwardTableRowViewModel> list = new ArrayList<>();
        for(PortForwardData portForward : Config.configFile.getPortForward().getPortForward_list()){
            PortForwardTableRowViewModel viewModel = new PortForwardTableRowViewModel(new SimpleStringProperty(portForward.getName()),
                    new SimpleStringProperty(portForward.getIp()), new SimpleIntegerProperty(portForward.getExtPort()), new SimpleIntegerProperty(portForward.getIntPort()));
            list.add(viewModel);
        }
        return FXCollections.observableArrayList(list);
    }
}
