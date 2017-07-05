package controlsystem.controller;

import com.jfoenix.controls.JFXButton;
import controlsystem.manager.SocketClientManager;
import controlsystem.manager.SocketConnector;
import controlsystem.manager.SocketListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dsm_025 on 2017-04-18.
 */
public class MainController implements Initializable {
    @FXML
    private JFXButton wireLessBtn;
    @FXML
    private JFXButton portForwardBtn;
    @FXML
    private JFXButton startURLBtn;
    @FXML
    private JFXButton timeLimitBtn;
    @FXML
    private JFXButton DHCPBtn;
    @FXML
    private RadioButton powerBtn;
    @FXML
    private JFXButton connectionListBtn;
    @FXML
    private JFXButton refreshBtn;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    private void menuHandle(MouseEvent event) {
        Stage newStage;
        switch (((Button) event.getSource()).getId()) {
            case "wireLessBtn":
                WireLessSettingController wireLessSettingController = new WireLessSettingController();
                newStage = settingNewState(new Scene(wireLessSettingController), "WireLessSetting");
                newStage.setOnHidden(event1 -> {
                    if(wireLessSettingController.isSaveState()){
                        try {
                            SocketConnector.sendMsg(wireLessSettingController.toString());
                            logger.info("Wireless Setting Changed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newStage.showAndWait();
                break;
            case "portForwardBtn":
                PortForwardSettingController portForwardSettingController = new PortForwardSettingController();
                newStage = settingNewState(new Scene(portForwardSettingController), "PortForwardSetting");
                newStage.setOnHidden(event1 -> {
                    if(portForwardSettingController.isSaveState()){
                        try {
                            SocketConnector.sendMsg(portForwardSettingController.toString());
                            logger.info("PortForward Setting Changed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newStage.showAndWait();
                break;
            case "startURLBtn":
                StartURLSettingController startURLSettingController = new StartURLSettingController();
                newStage = settingNewState(new Scene(startURLSettingController), "StartURLSetting");
                newStage.setOnHidden(event1 -> {
                    if(startURLSettingController.isSaveState()){
                        try {
                            SocketConnector.sendMsg(startURLSettingController.toString());
                            logger.info("StartURL Setting Changed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newStage.showAndWait();
                break;
            case "timeLimitBtn":
                TimeLimitSettingController timeLimitSettingController = new TimeLimitSettingController();
                newStage = settingNewState(new Scene(timeLimitSettingController), "TimeLimitSetting");
                newStage.setOnHidden(event1 -> {
                    if(timeLimitSettingController.isSaveState()){
                        try {
                            SocketConnector.sendMsg(timeLimitSettingController.toString());
                            logger.info("TimeLimit Setting Changed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newStage.showAndWait();
                break;
            case "DHCPBtn":
                DHCPSettingController dhcpSettingController = new DHCPSettingController();
                newStage = settingNewState(new Scene(dhcpSettingController), "DHCPSetting");
                newStage.setOnHidden(event1 -> {
                    if(dhcpSettingController.isSaveState()){
                        try {
                            SocketConnector.sendMsg(dhcpSettingController.toString());
                            logger.info("DHCP Setting Changed");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newStage.showAndWait();
                break;
            case "connectionListBtn" :
                ConnectionListController connectionListController = new ConnectionListController();
                newStage = settingNewState(new Scene(connectionListController), "ConnectionList");
                newStage.showAndWait();
                break;
            case "refreshBtn":
                try {
                    logger.info("Config Refresh");
                    SocketConnector.sendMsg(new JSONObject().put("type", "reset").toString());
                } catch (IOException e) {
                } catch (JSONException e) {
                }
        }
    }

    private void powerHandle(){
        if(powerBtn.isSelected()){      // Power On
            if(!SocketClientManager.isConnectState()){
                Alert alert = makeErrorAlert("Error Dialog","Emulator power is Off", "Try again" );
                alert.showAndWait();
                powerBtn.setSelected(false);
                return;
            }
            LoginController controller = new LoginController();
            Stage newStage = settingNewState(new Scene(controller), "Login");
            newStage.setOnHidden(event -> {
                if(controller.getLoginState()){
                    setButtonsDisable(false);
//                    emulator.setPowerState(true);
                }else{
                    powerBtn.setSelected(false);
                }
            });
            newStage.showAndWait();
        }else{                          //Power Off
            logger.info("Emulator Power is Off");
            setButtonsDisable(true);
            systemPowerOff();
            try {
                SocketConnector.sendMsg(new JSONObject().put("type", "powerOff").toString());
            } catch (JSONException e) {
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wireLessBtn.setOnMouseClicked(event -> menuHandle(event));
        portForwardBtn.setOnMouseClicked(event -> menuHandle(event));
        startURLBtn.setOnMouseClicked(event -> menuHandle(event));
        timeLimitBtn.setOnMouseClicked(event -> menuHandle(event));
        DHCPBtn.setOnMouseClicked(event -> menuHandle(event));
        connectionListBtn.setOnMouseClicked(event -> menuHandle(event));
        refreshBtn.setOnMouseClicked(event -> menuHandle(event));
        powerBtn.setOnMouseClicked(event -> powerHandle());
        setButtonsDisable(true);

        SocketClientManager manager = new SocketClientManager();
        manager.start();
        manager.setOnConnectionChanged(state -> {
            if(!state && powerBtn.isSelected()){
                Platform.runLater(() -> {
                    systemPowerOff();
                    Alert alert = makeErrorAlert("Error Dialog","Emulator power is Off", "Try again" );
                    alert.showAndWait();
                });
            }
        });
    }

    private void systemPowerOff(){
        setButtonsDisable(true);
        powerBtn.setSelected(false);
    }

    private Alert makeErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    private Stage settingNewState(Scene scene, String title){
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(title);
        return newStage;
    }

    private void setButtonsDisable(boolean state){
        wireLessBtn.setDisable(state);
        portForwardBtn.setDisable(state);
        startURLBtn.setDisable(state);
        timeLimitBtn.setDisable(state);
        DHCPBtn.setDisable(state);
        connectionListBtn.setDisable(state);
        refreshBtn.setDisable(state);
    }
}
