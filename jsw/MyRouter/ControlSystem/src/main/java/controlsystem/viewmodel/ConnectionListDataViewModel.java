package controlsystem.viewmodel;

import controlsystem.data.config.Connection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class ConnectionListDataViewModel {
    public static ObservableList<ConnectionListTableRowViewModel > getDatas(){
        ArrayList<ConnectionListTableRowViewModel > list = new ArrayList<>();
        for(Connection connection : Connection.connectionList){
            ConnectionListTableRowViewModel viewModel = new ConnectionListTableRowViewModel(new SimpleStringProperty(connection.getAssignedAddress()),
                    new SimpleStringProperty(connection.getAdreess()));
            list.add(viewModel);
        }
        return FXCollections.observableArrayList(list);
    }
}
