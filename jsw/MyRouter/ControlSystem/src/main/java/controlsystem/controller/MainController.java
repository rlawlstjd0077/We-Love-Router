package controlsystem.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXButton;
import controlsystem.data.Operation;
import controlsystem.data.config.Config;
import controlsystem.data.config.ConnectionList;
import controlsystem.data.json.Packet;
import controlsystem.manager.JsonManager;
import controlsystem.manager.SocketManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private RadioButton apPowerBtn;
    @FXML
    private RadioButton dhcpPowerBtn;
    @FXML
    private JFXButton connectionListBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private ComboBox<String> connectedListComboBox;
    private ArrayList<SocketManager.Emulator> emulatorList;
    private SocketManager socketManager;
    private SocketManager.Emulator emulator;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private void menuHandle(MouseEvent event) {
        Stage newStage;
        switch (((Button) event.getSource()).getId()) {
            case "wireLessBtn":
                WireLessSettingController wireLessSettingController = new WireLessSettingController(emulator);
                newStage = settingNewState(new Scene(wireLessSettingController), "WireLessSetting");
                newStage.setOnHidden(event1 -> {
                    if (wireLessSettingController.isSaveState()) {
//                            SocketManager.Emulator emulator = findEmulatorFromAddress(connectedListComboBox.getValue());
                        emulator.setSendData(sendData(Operation.SET_AP_SETTING, wireLessSettingController.toString()));
                        logger.info("Wireless Setting Changed");
                    }
                });
                newStage.showAndWait();
                break;
            case "portForwardBtn":
                PortForwardSettingController portForwardSettingController = new PortForwardSettingController(emulator);
                newStage = settingNewState(new Scene(portForwardSettingController), "PortForwardSetting");
                newStage.setOnHidden(event1 -> {
                    if (portForwardSettingController.isSaveState()) {
                        emulator.setSendData(sendData(Operation.SET_PORTFORWARD, portForwardSettingController.toString()));
                        logger.info("PortForward Setting Changed");
                    }
                });
                newStage.showAndWait();
                break;
            case "startURLBtn":
                StartURLSettingController startURLSettingController = new StartURLSettingController(emulator);
                newStage = settingNewState(new Scene(startURLSettingController), "StartURLSetting");
                newStage.setOnHidden(event1 -> {
                    if (startURLSettingController.isSaveState()) {
                        emulator.setSendData(sendData(Operation.SET_STARTPAGE, startURLSettingController.toString()));
                        logger.info("StartURL Setting Changed");
                    }
                });
                newStage.showAndWait();
                break;
            case "timeLimitBtn":
                TimeLimitSettingController timeLimitSettingController = new TimeLimitSettingController(emulator);
                newStage = settingNewState(new Scene(timeLimitSettingController), "TimeLimitSetting");
                newStage.setOnHidden(event1 -> {
                    if (timeLimitSettingController.isSaveState()) {
//                        emulator.setSendData(sendData(Operation.SET_TIME_LIMIT, timeLimitSettingController.toString()));
                        logger.info("TimeLimit Setting Changed");
                    }
                });
                newStage.showAndWait();
                break;
            case "DHCPBtn":
                DHCPSettingController dhcpSettingController = new DHCPSettingController(emulator);
                newStage = settingNewState(new Scene(dhcpSettingController), "DHCPSetting");
                newStage.setOnHidden(event1 -> {
                    if (dhcpSettingController.isSaveState()) {
                        emulator.setSendData(sendData(Operation.SET_DHCP_SETTING, dhcpSettingController.toString()));
                        logger.info("DHCP Setting Changed");
                    }
                });
                newStage.showAndWait();
                break;
            case "connectionListBtn":
                ConnectionListController connectionListController = new ConnectionListController();
                newStage = settingNewState(new Scene(connectionListController), "ConnectionList");
                newStage.showAndWait();
                break;
            case "refreshBtn":
                logger.info("Config Refresh");
                try {
                    emulator.setSendData(JsonManager.bindPacket(emulator.getSocketChannel().getLocalAddress() + "",
                            emulator.getSocketChannel().getRemoteAddress() + "", "req", 0, 0, Operation.RESET_ROUTER_SETTINGS, ""));
                } catch (IOException e) {
                }
        }
    }

    private Packet sendData(Operation operation, String message) {
        try {
            return JsonManager.bindPacket(emulator.getSocketChannel().getLocalAddress() + "", emulator.getSocketChannel().getLocalAddress() + "",
                    "req", 0, 0, operation, message);
        } catch (IOException e) {
        }
        return null;
    }

    private void powerHandle() {

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
        apPowerBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // TODO Power 바뀜 서버에 요청
            try {
                emulator.setSendData(JsonManager.bindPacket(emulator.getSocketChannel().getLocalAddress() + "", emulator.getSocketChannel().getRemoteAddress() + "",
                        "req", 0, 0, Operation.SET_AP_POWER, new JSONObject().put("state", newValue).toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        dhcpPowerBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // TODO Power 바뀜 서버에 요청
            try{
                emulator.setSendData(JsonManager.bindPacket(emulator.getSocketChannel().getLocalAddress() + "", emulator.getSocketChannel().getRemoteAddress() + "",
                        "req", 0, 0, Operation.SET_DHCP_POWER, new JSONObject().put("state", newValue).toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectedListComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (connectedListComboBox.getValue() != null) {
                SocketManager.Emulator emulator = findEmulatorFromAddress(newValue);
                this.emulator = emulator;
                if (!emulator.isLoginState()) {
                    LoginController loginController = new LoginController(emulator);
                    Stage newStage = settingNewState(new Scene(loginController), "Login");
                    newStage.setOnHidden(event -> {
                        if (loginController.getLoginState()) {
                            setButtonsDisable(false);
                            emulator.setLoginState(true);
                            refreshAPDHCPPowerState();
                        } else {
                            connectedListComboBox.setValue(null);
                            setButtonsDisable(true);
                        }
                    });
                    newStage.show();
                } else {
                    setButtonsDisable(false);
                }
            }
        });

        setButtonsDisable(true);
        emulatorList = new ArrayList<>();

        socketManager = new SocketManager();
        socketManager.setmOnRPIConnectedListener(new SocketManager.OnRPIConnectedListener() {
            @Override
            public void onConnect(SocketManager.Emulator emulator) {
                addEmulator(emulator);
            }

            @Override
            public void onDisConnect(SocketManager.Emulator emulator) {
                removeEmulator(emulator);
            }
        });

        socketManager.setmOnMessageReceivedListener((emulator, message) -> {
            handle(emulator, message);
        });

        try {
            socketManager.startSocketManager();
        } catch (IOException e) {
        }
    }

    private void refreshAPDHCPPowerState() {
        apPowerBtn.setSelected(emulator.getConfig().getPower().isApPower());
        dhcpPowerBtn.setSelected(emulator.getConfig().getPower().isDhcpPower());
    }

    private void addEmulator(SocketManager.Emulator emulator) {
        emulatorList.add(emulator);
        try {
            connectedListComboBox.getItems().add(emulator.getSocketChannel().getRemoteAddress() + "");
        } catch (IOException e) {
        }
    }

    private void removeEmulator(SocketManager.Emulator emulator) {
        try {
            if (this.emulator == emulator) {
                setButtonsDisable(true);
                Platform.runLater(() -> {
                    makeErrorAlert("Connect refuse", "Connect refuse", connectedListComboBox.getValue() + " is disconnected").show();
                    connectedListComboBox.setValue("");
                });
            }
            Platform.runLater(() -> connectedListComboBox.getItems().clear());
            emulatorList.remove(emulator);
            for (SocketManager.Emulator temp : emulatorList) {
                connectedListComboBox.getItems().add(temp.getSocketChannel().getRemoteAddress() + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleClose() {
        makeErrorAlert("Connection refuse", "Connection refuse", "Try connect another").show();
        setButtonsDisable(true);
    }

    private SocketManager.Emulator findEmulatorFromAddress(String remoteAddress) {
        try {
            for (SocketManager.Emulator client : emulatorList) {
                if (client.getSocketChannel().getRemoteAddress().toString().equals(remoteAddress)) {
                    return client;
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    private Alert makeErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    private Stage settingNewState(Scene scene, String title) {
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(title);
        return newStage;
    }

    private void setButtonsDisable(boolean state) {
        wireLessBtn.setDisable(state);
        portForwardBtn.setDisable(state);
        startURLBtn.setDisable(state);
        timeLimitBtn.setDisable(state);
        DHCPBtn.setDisable(state);
        connectionListBtn.setDisable(state);
        refreshBtn.setDisable(state);
        apPowerBtn.setDisable(state);
        dhcpPowerBtn.setDisable(state);
    }

    public void handle(SocketManager.Emulator emulator, String packetMessage) {
        Packet packet = new Gson().fromJson(packetMessage, Packet.class);

        switch (Operation.fromString(packet.getBody().getOperation())) {
            case MODIFY_CONFIG:
                try {
                    emulator.setConfig(new Gson().fromJson(packet.getBody().getSubValue(), Config.class));
                    logger.debug("Config file setting at " + emulator.getSocketChannel().getRemoteAddress());
                } catch (IOException e) {
                }
                break;
            case CONNECTION_LIST:
                try {
                    emulator.setConnectionList(new Gson().fromJson(packet.getBody().getSubValue(), ConnectionList.class));
                    logger.debug("ConnectionList changed at " + emulator.getSocketChannel().getRemoteAddress());
                } catch (IOException e) {
                }
                break;
        }
    }
}
