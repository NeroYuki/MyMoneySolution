package scenes;

import controller.addPlanController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addPlanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private addPlanController controller;
    public addPlanBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAddPlan.fxml"));

        scene = new Scene(root);
        controller = new addPlanController();
    }

    public Scene getScene() {
        return scene;
    }
}
