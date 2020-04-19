package scenes;

import controller.categoriesSceneController;
import controller.homepageSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class categoriesScene {
    private Scene scene;
    private categoriesSceneController controller;
    public categoriesScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmCategories.fxml"));
        scene = new Scene(root);


        controller = new categoriesSceneController();
    }

    public Scene getScene() {
        return scene;
    }
}
