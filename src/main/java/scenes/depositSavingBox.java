package scenes;

import controller.depositSavingBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class depositSavingBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private depositSavingBoxController controller;
    public depositSavingBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmDepositSaving.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public depositSavingBoxController getController() {
        return controller;
    }
}
