package controlsystem.controller;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controlsystem.common.UiUtil;
import controlsystem.data.config.StartURL;
import controlsystem.manager.SocketManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dsm_025 on 2017-04-19.
 */
public class StartURLSettingController extends BorderPane{
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField urlField;

    private boolean saveState;
    private SocketManager.Emulator emulator;

    public StartURLSettingController(SocketManager.Emulator emulator){
        try {
            UiUtil.loadFxml(    this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.emulator = emulator;
        StartURL data = emulator.getConfig().getStartUrl();
        urlField.setText(data.getUrl());
        saveBtn.setOnMouseClicked(event -> {
            ((Stage) getScene().getWindow()).close();
            saveState = true;
        });
        cancelBtn.setOnMouseClicked(event -> ((Stage) getScene().getWindow()).close());
    }

    public boolean isSaveState() {
        return saveState;
    }

    public String toString(){
        StartURL startURL = new StartURL(urlField.getText());
        emulator.getConfig().setStartUrl(startURL);
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("type", "startUrl");
            object.put("data", new Gson().toJson(startURL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
