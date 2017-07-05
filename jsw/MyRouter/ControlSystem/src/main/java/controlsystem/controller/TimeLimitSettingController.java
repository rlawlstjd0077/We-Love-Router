package controlsystem.controller;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controlsystem.common.UiUtil;
import controlsystem.data.Config;
import controlsystem.data.TimeLimit;
import controlsystem.data.TimeLimitData;
import controlsystem.viewmodel.PortForwardTableRowViewModel;
import controlsystem.viewmodel.TimeLimitDataViewModel;
import controlsystem.viewmodel.TimeLimitTableRowViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-04-19.
 */
public class TimeLimitSettingController extends BorderPane{
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private TableView<TimeLimitTableRowViewModel> timeLimitTableview;
    @FXML
    private TableColumn<TimeLimitTableRowViewModel, String> nameColumn;
    @FXML
    private TableColumn<TimeLimitTableRowViewModel, String> startColumn;
    @FXML
    private TableColumn<TimeLimitTableRowViewModel, String> endColumn;
    @FXML
    private JFXTextField startHourField;
    @FXML
    private JFXTextField startMinuteField;
    @FXML
    private JFXTextField endHourField;
    @FXML
    private JFXTextField endMinuteField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private JFXTextField nameField;

    private boolean saveState;


    public TimeLimitSettingController(){
        try {
            UiUtil.loadFxml(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        timeLimitTableview.setItems(TimeLimitDataViewModel.getDatas());

        addBtn.setOnMouseClicked(event -> {
            TimeLimitTableRowViewModel viewModel = new TimeLimitTableRowViewModel(new SimpleStringProperty(nameField.getText()),
                    new SimpleStringProperty(startHourField.getText() + ":" + startMinuteField.getText()),
                    new SimpleStringProperty(endHourField.getText() + ":" + endMinuteField.getText())
            );
            timeLimitTableview.getItems().add(viewModel);
        });
        removeBtn.setOnMouseClicked(event -> {
            timeLimitTableview.getItems().remove(timeLimitTableview.getSelectionModel().getSelectedItem());
        });

        saveBtn.setOnMouseClicked(event -> {
            saveState = true;
            ((Stage) getScene().getWindow()).close();
        });
        cancelBtn.setOnMouseClicked(event -> {
            ((Stage) getScene().getWindow()).close();
        });
    }
    public boolean isSaveState(){
        return saveState;
    }

    public String toString(){
        ArrayList<TimeLimitData> list = new ArrayList<>();
        for(TimeLimitTableRowViewModel viewModel : timeLimitTableview.getItems()){
            TimeLimitData timeLimitData = new TimeLimitData(viewModel.nameProperty().getValue(),
                    viewModel.startProperty().getValue(), viewModel.endProperty().getValue());
            list.add(timeLimitData);
        }

        TimeLimit timeLimit = new TimeLimit(list);
        Config.configFile.setTimeLimit(timeLimit);
        JSONObject object = new JSONObject();
        try {
            object.put("data", new Gson().toJson(timeLimit));
            object.put("type", "timeLimit");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}