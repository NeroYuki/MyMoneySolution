package scenes;

import controller.homepageSceneController;
import controller.transactionSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class transactionScene {

    private Scene scene;
    private transactionSceneController controller;
    public transactionScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmTransaction.fxml"));
        scene = new Scene(root);


        controller = new transactionSceneController();
    }

    public Scene getScene() {
        return scene;
    }

}
