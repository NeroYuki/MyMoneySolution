package scenes;

import controller.homepageSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class homepageScene {
    private Scene scene;
    private homepageSceneController controller;
    public homepageScene() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmHomepage.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();

    }

    public homepageSceneController getController() {
        return controller;
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
