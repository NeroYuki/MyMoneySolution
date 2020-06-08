package scenes;

import controller.editTransactionBoxController;
import controller.loginSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Transaction;

public class editTransactionBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editTransactionBoxController controller;

    public editTransactionBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmEditTransaction.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editTransactionBoxController getController() {
        return controller;
    }

}
