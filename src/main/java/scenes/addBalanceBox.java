package scenes;

import controller.addBalanceBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addBalanceBox {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private Scene scene;
    private addBalanceBoxController controller;
    public addBalanceBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddBalance.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addBalanceBoxController getController() {
        return controller;
    }
}
