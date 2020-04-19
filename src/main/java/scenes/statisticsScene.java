package scenes;

import controller.homepageSceneController;
import controller.statisticsSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class statisticsScene {
    private Scene scene;
    private statisticsSceneController controller;
    public statisticsScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmStatistics.fxml"));
        scene = new Scene(root);


        controller = new statisticsSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
