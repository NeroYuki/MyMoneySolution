package scenes;

import controller.addCategoriesBoxController;
import controller.editCategoriesBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editCategoriesBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editCategoriesBoxController controller;
    public editCategoriesBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmEditCategories.fxml"));

        scene = new Scene(root);
        controller = new editCategoriesBoxController();
    }

    public Scene getScene() {
        return scene;
    }
}
