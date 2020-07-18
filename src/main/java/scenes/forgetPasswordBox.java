package scenes;

import controller.forgetPasswordBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class forgetPasswordBox {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private Scene scene;
    private forgetPasswordBoxController controller;
    public forgetPasswordBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmForgetPassword.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public forgetPasswordBoxController getController() {
        return controller;
    }
}
