package scenes;

import controller.addCategoriesBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addCategoriesBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private addCategoriesBoxController controller;
    public addCategoriesBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAddCategories.fxml"));

        scene = new Scene(root);
        controller = new addCategoriesBoxController();
    }

    public Scene getScene() {
        return scene;
    }
}
