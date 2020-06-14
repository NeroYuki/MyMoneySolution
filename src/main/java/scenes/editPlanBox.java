package scenes;

import controller.editPlanController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editPlanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editPlanController controller;
    public editPlanBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAddPlan.fxml"));

        scene = new Scene(root);
        controller = new editPlanController();
    }

    public Scene getScene() {
        return scene;
    }
}
