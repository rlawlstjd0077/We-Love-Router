package controlsystem.controller;

import com.jfoenix.controls.JFXButton;
import controlsystem.common.UiUtil;
import controlsystem.viewmodel.ConnectionListDataViewModel;
import controlsystem.viewmodel.ConnectionListTableRowViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by dsm_025 on 2017-05-07.
 */
public class ConnectionListController extends BorderPane{
    @FXML
    private TableView<ConnectionListTableRowViewModel> connectionListTableView;
    @FXML
    private TableColumn<ConnectionListTableRowViewModel, String> assignedColumn;
    @FXML
    private TableColumn<ConnectionListTableRowViewModel, String> addressColumn;
    @FXML
    private JFXButton confirmBtn;
    @FXML
    private JFXButton refreshBtn;

    public ConnectionListController() {
        try {
            UiUtil.loadFxml(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        confirmBtn.setOnMouseClicked(event -> {
            ((Stage) getScene().getWindow()).close();
        });

        refreshBtn.setOnMouseClicked(event -> {
            refresh();
        });

        assignedColumn.setCellValueFactory(cellData -> cellData.getValue().assgiendProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        connectionListTableView.setItems(ConnectionListDataViewModel.getDatas());
    }

    private void refresh(){
        connectionListTableView.setItems(ConnectionListDataViewModel.getDatas());
    }
}
