package controlsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controlsystem.common.UiUtil;
import controlsystem.data.Config;
import controlsystem.data.LoginInform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Created by dsm_025 on 2017-04-19.
 */
public class LoginController extends BorderPane{
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginBtn;

    private boolean loginState;

    public LoginController(){
        try {
            UiUtil.loadFxml(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginState = false;

        loginBtn.setOnMouseClicked(event -> {
            if(isLoginSuccess(idField.getText(), passwordField.getText())){
                loginState = true;
                Window window = getScene().getWindow();
                ((Stage) window).close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!");
                alert.showAndWait();
            }
        });
    }

    public boolean getLoginState(){
        return loginState;
    }

    private boolean isLoginSuccess(String id, String psw){
        LoginInform info = Config.configFile.getLogin();
        return id.equals(info.getId()) && psw.equals(info.getPassword());
    }
}