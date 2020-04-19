package scenes;

import controller.homepageSceneController;
import controller.loginSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class homepageScene {
    private Scene scene;
    private homepageSceneController controller;
    public homepageScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmHomepage.fxml"));
        scene = new Scene(root);


        controller = new homepageSceneController();
    }

    public Scene getScene() {
        return scene;
    }

    public double getWidth() {
        return scene.getWidth();
    }

    public double getHeight() {
        return scene.getHeight();
    }
}
