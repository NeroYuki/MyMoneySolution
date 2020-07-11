package scenes;

import controller.editSavingBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editSavingBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editSavingBoxController controller;
    public editSavingBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmEditSaving.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editSavingBoxController getController() {
        return controller;
    }
}
