package scenes;

import controller.withdrawSavingBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class withdrawSavingBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private withdrawSavingBoxController controller;
    public withdrawSavingBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmWithdrawSaving.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public withdrawSavingBoxController getController() {
        return controller;
    }
}
