package scenes;

import controller.editBalanceBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editBalanceBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editBalanceBoxController controller;
    public editBalanceBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmEditBalance.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editBalanceBoxController getController() {
        return controller;
    }
}
