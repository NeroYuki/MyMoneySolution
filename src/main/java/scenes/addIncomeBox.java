package scenes;

import controller.addIncomeBoxController;
import controller.editTransactionBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addIncomeBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private addIncomeBoxController controller;
    public addIncomeBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAddIncome.fxml"));

        scene = new Scene(root);
        controller = new addIncomeBoxController();
    }

    public Scene getScene() {
        return scene;
    }
}
