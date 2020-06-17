package scenes;

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmEditCategories.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editCategoriesBoxController getController() {
        return controller;
    }
}
