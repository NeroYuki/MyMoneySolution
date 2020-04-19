package scenes;

import controller.loginSceneController;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

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
