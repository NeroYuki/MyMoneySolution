package scenes;

import controller.addIncomeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addIncomeBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
private Scene scene;
    private addIncomeBoxController controller;
    public addIncomeBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddIncome.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addIncomeBoxController getController() {
        return controller;
    }
}
