package controlsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResourceAsStream("commons/fonts/Roboto-Medium.ttf"),
                14
        );
        Font.loadFont(getClass().getResourceAsStream("/commons/fonts/Roboto-Bold.ttf"),
                14
        );
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("My Router");
        primaryStage.setScene(new Scene(root, 1200, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
