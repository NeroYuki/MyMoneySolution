package scenes;

import controller.withdrawLoanBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class withdrawLoanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
    private Scene scene;
    private withdrawLoanBoxController controller;
    public withdrawLoanBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/frmWithdrawLoan.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public withdrawLoanBoxController getController() {
        return controller;
    }
}
