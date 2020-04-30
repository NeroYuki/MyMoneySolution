package scenes;

import controller.addExpenseBoxController;
import controller.addIncomeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addExpenseBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private addExpenseBoxController controller;
    public addExpenseBox() throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/frmAddExpense.fxml"));

        scene = new Scene(root);
        controller = new addExpenseBoxController();
    }

    public Scene getScene() {
        return scene;
    }
}
