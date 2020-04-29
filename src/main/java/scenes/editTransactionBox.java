package scenes;

import controller.editTransactionBoxController;
import controller.loginSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class editTransactionBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editTransactionBoxController controller;
    public editTransactionBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmEditTransaction.fxml"));

        scene = new Scene(root);
        controller = new editTransactionBoxController();
    }

    public Scene getScene() {
        return scene;
    }
}
