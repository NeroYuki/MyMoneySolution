package scenes;

import controller.editLoanBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editLoanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private editLoanBoxController controller;
    public editLoanBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmEditLoan.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editLoanBoxController getController() {
        return controller;
    }
}
