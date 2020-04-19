package scenes;

import controller.accountSceneController;
import controller.homepageSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class accountScene {
    private Scene scene;
    private accountSceneController controller;
    public accountScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAccount.fxml"));
        scene = new Scene(root);


        controller = new accountSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
