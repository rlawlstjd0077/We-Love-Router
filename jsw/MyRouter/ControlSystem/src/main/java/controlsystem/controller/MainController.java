package controlsystem.controller;

import com.jfoenix.controls.JFXButton;
import controlsystem.manager.JsonManager;
import controlsystem.manager.SocketClientManager;
import controlsystem.manager.SocketConnector;
import controlsystem.manager.SocketServerManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.util.ArrayList;
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
    @FXML
    private ComboBox<String> connectedListComboBox;
    private ArrayList<SocketServerManager.Emulator> EmulatorList;
    private SocketServerManager socketServerManager;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private void menuHandle(MouseEvent event) {
        Stage newStage;
        switch (((Button) event.getSource()).getId()) {
            case "wireLessBtn":
                WireLessSettingController wireLessSettingController = new WireLessSettingController();
                newStage = settingNewState(new Scene(wireLessSettingController), "WireLessSetting");
                newStage.setOnHidden(event1 -> {
                    if(wireLessSettingController.isSaveState()){
                        if(connectedListComboBox.getValue() != null) {
                            findClientFromAddress(connectedListComboBox.getValue()).setSendData(wireLessSettingController.toString());
                            logger.info("Wireless Setting Changed");
                        } else {
                            makeErrorAlert("Error", "Emulator not selected", "Try after select emulator").show();
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
                        logger.info("PortForward Setting Changed");
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
        if(powerBtn.isSelected()) {      // Power On
            setButtonsDisable(false);
        }
//            if(!SocketClientManager.isConnectState()){
//                Alert alert = makeErrorAlert("Error Dialog","Emulator power is Off", "Try again" );
//                alert.showAndWait();
//                powerBtn.setSelected(false);
//                return;
//            }
//            LoginController controller = new LoginController();
//            Stage newStage = settingNewState(new Scene(controller), "Login");
//            newStage.setOnHidden(event -> {
//                if(controller.getLoginState()){
//                    setButtonsDisable(false);
//                }else{
//                    powerBtn.setSelected(false);
//                }
//            });
//            newStage.showAndWait();
//        }else{                          //Power Off
//            logger.info("Emulator Power is Off");
//            setButtonsDisable(true);
//            systemPowerOff();
//            try {
//                SocketConnector.sendMsg(new JSONObject().put("type", "powerOff").toString());
//            } catch (JSONException e) {
//            } catch (IOException e) {
//            }
//        }
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
        EmulatorList = new ArrayList<>();

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
        socketServerManager = new SocketServerManager();
        socketServerManager.setmOnRPIConnectedListener(RPIList -> {
            this.EmulatorList = RPIList;
            setConnectedListComboBox();
        });
        socketServerManager.startServer();
        JsonManager.readConfigFile();
    }

    private void setConnectedListComboBox(){
        //현재 선택된 Val을 가져와 비교를 해서 연결이 끊기면
        try {
            String selectedVal = connectedListComboBox.getValue();
            String settingVal = EmulatorList.get(0).getSocketChannel().getRemoteAddress().toString();

            for(SocketServerManager.Emulator client : EmulatorList) {
                String tempVal = client.getSocketChannel().getRemoteAddress().toString();
                if(tempVal.equals(selectedVal)){
                    settingVal = tempVal;
                }
            }

            connectedListComboBox.getItems().clear();
            for (SocketServerManager.Emulator client : EmulatorList) {
                connectedListComboBox.getItems().add(client.getSocketChannel().getRemoteAddress().toString());
            }
            String finalSettingVal = settingVal;
            Platform.runLater(() -> {
                connectedListComboBox.setValue(finalSettingVal);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketServerManager.Emulator findClientFromAddress(String remoteAddress){
        try {
            for (SocketServerManager.Emulator client : EmulatorList) {
                if (client.getSocketChannel().getRemoteAddress().toString().equals(remoteAddress)){
                    return client;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
