package scenes;

import controller.addLoanBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class addLoanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;

    private Scene scene;
    private addLoanBoxController controller;
    public addLoanBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmAddLoan.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public addLoanBoxController getController() {
        return controller;
    }
}
