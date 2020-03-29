package Scenes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class loginScene {
    private Scene scene;
    private loginSceneController controller;
    public loginScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
        scene = new Scene(root, 300, 180);
        controller = new loginSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
