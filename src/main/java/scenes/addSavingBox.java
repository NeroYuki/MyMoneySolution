package scenes;

import controller.addSavingBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addSavingBox {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private Scene scene;
    private addSavingBoxController controller;
    public addSavingBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddSaving.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addSavingBoxController getController() {
        return controller;
    }
}
