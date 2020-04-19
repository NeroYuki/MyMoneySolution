package scenes;

import controller.loginSceneController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginScene {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private loginSceneController controller;
    public loginScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmLoginNew.fxml"));

        scene = new Scene(root);
        controller = new loginSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
