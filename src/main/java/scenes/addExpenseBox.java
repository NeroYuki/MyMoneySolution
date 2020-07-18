package scenes;

import controller.addExpenseBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addExpenseBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
private Scene scene;
    private addExpenseBoxController controller;
    public addExpenseBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddExpense.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addExpenseBoxController getController() {
        return controller;
    }
}
