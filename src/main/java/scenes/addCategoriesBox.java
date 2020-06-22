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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddCategories.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addCategoriesBoxController getController() {
        return controller;
    }
}
