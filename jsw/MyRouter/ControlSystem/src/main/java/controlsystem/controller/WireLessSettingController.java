package controlsystem.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controlsystem.common.UiUtil;
import controlsystem.data.Config;
import controlsystem.data.Wireless;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dsm_025 on 2017-04-19.
 */
public class WireLessSettingController extends BorderPane{
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXPasswordField passwordField;

    private boolean saveState;

    public WireLessSettingController(){
        try {
            UiUtil.loadFxml(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        passwordField.setVisible(true);
        Wireless wireless = Config.configFile.getWireless();
        idField.setText(wireless.getSsid());
        passwordField.setText(wireless.getPassword());

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
        Wireless wireless = new Wireless(idField.getText(), passwordField.getText());
        Config.configFile.setWireless(wireless);

        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("type", "wireless");
            object.put("data", new Gson().toJson(wireless));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String getSSID(){
        return idField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }
}
