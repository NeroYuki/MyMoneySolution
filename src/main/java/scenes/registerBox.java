package scenes;

import controller.registerBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class registerBox {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private Scene scene;
    private registerBoxController controller;
    public registerBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmRegister.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public registerBoxController getController() {
        return controller;
    }
}
