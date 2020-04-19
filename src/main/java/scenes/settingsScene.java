package scenes;

import controller.homepageSceneController;
import controller.settingSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class settingsScene {
    private Scene scene;
    private settingSceneController controller;
    public settingsScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmSettings.fxml"));
        scene = new Scene(root);


        controller = new settingSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
