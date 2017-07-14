package controlsystem.viewmodel;

import controlsystem.data.config.PortForwardData;
import controlsystem.manager.SocketManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class PortForwardDataViewModel {
    public static ObservableList<PortForwardTableRowViewModel> getDatas(SocketManager.Emulator emulator){
        ArrayList<PortForwardTableRowViewModel> list = new ArrayList<>();
        for(PortForwardData portForward : emulator.getConfig().getPortForward().getPortForward_list()){
            PortForwardTableRowViewModel viewModel = new PortForwardTableRowViewModel(new SimpleStringProperty(portForward.getName()),
                    new SimpleStringProperty(portForward.getIp()), new SimpleIntegerProperty(portForward.getExtPort()), new SimpleIntegerProperty(portForward.getIntPort()));
            list.add(viewModel);
        }
        return FXCollections.observableArrayList(list);
    }
}
