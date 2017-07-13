package controlsystem.controller;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controlsystem.common.UiUtil;
import controlsystem.data.config.Config;
import controlsystem.data.config.PortForward;
import controlsystem.data.config.PortForwardData;
import controlsystem.manager.SocketServerManager;
import controlsystem.viewmodel.PortForwardDataViewModel;
import controlsystem.viewmodel.PortForwardTableRowViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-04-19.
 */
public class PortForwardSettingController extends BorderPane{
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField firstIPField;
    @FXML
    private JFXTextField secondIPField;
    @FXML
    private JFXTextField thirdIPField;
    @FXML
    private JFXTextField fourthIPField;
    @FXML
    private JFXTextField extPortField;
    @FXML
    private JFXTextField intPortField;
    @FXML
    private TableView<PortForwardTableRowViewModel> portForwardTable;
    @FXML
    private TableColumn<PortForwardTableRowViewModel, String> nameTableColumn;
    @FXML
    private TableColumn<PortForwardTableRowViewModel, String> ipTableColumn;
    @FXML
    private TableColumn<PortForwardTableRowViewModel, Integer> extPortTableColumn;
    @FXML
    private TableColumn<PortForwardTableRowViewModel, Integer> intPortTableColumn;

    private boolean saveState;
    private SocketServerManager.Emulator emulator;

    public PortForwardSettingController(SocketServerManager.Emulator emulator){
        try {
            UiUtil.loadFxml(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emulator = emulator;
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ipTableColumn.setCellValueFactory(cellData -> cellData.getValue().ipProperty());
        extPortTableColumn.setCellValueFactory(cellData -> cellData.getValue().intPortProperty().asObject());
        intPortTableColumn.setCellValueFactory(cellData -> cellData.getValue().extPortProperty().asObject());
        portForwardTable.setItems(PortForwardDataViewModel.getDatas(emulator));

        addBtn.setOnMouseClicked(event -> {
            PortForwardTableRowViewModel viewModel = new PortForwardTableRowViewModel(new SimpleStringProperty(nameField.getText()),
                    new SimpleStringProperty(firstIPField.getText() + "." + secondIPField.getText() + "." + thirdIPField.getText() + "." + fourthIPField.getText()),
                    new SimpleIntegerProperty(Integer.parseInt(extPortField.getText())), new SimpleIntegerProperty(Integer.parseInt(intPortField.getText()))
                    );
            portForwardTable.getItems().add(viewModel);
        });
        removeBtn.setOnMouseClicked(event -> {
            portForwardTable.getItems().remove(portForwardTable.getSelectionModel().getSelectedItem());
        });

        saveBtn.setOnMouseClicked(event -> {
            saveState = true;
            ((Stage) getScene().getWindow()).close();
        });
        cancelBtn.setOnMouseClicked(event -> {
            ((Stage) getScene().getWindow()).close();
        });
    }

    public boolean isSaveState() {
        return saveState;
    }

    public String toString(){
        ArrayList<PortForwardData> list = new ArrayList<>();
        for(PortForwardTableRowViewModel viewModel : portForwardTable.getItems()){
            PortForwardData portForward = new PortForwardData(viewModel.nameProperty().getValue(), viewModel.ipProperty().getValue(),
                    viewModel.extPortProperty().getValue(), viewModel.intPortProperty().getValue());
            list.add(portForward);
        }
        PortForward portForwardData = new PortForward(list);
        emulator.getConfig().setPortForward(portForwardData);
        JSONObject object = new JSONObject();
        try {
            object.put("data", new Gson().toJson(portForwardData));
            object.put("type", "portForward");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
