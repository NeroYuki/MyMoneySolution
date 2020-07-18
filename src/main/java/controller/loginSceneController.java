package controller;

import exception.ProcessExeption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import process.ProcessUser;
import scenes.homepageScene;
import scenes.registerBox;

public class loginSceneController {
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtPassword;

    public void loginBtnClick(ActionEvent e) throws Exception {
        try{
            ProcessUser.login(txtUsername.getText(),txtPassword.getText());
        }
        catch (ProcessExeption de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
        System.out.println("Login clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage
        //stage.setFullScreen(true);

        homepageScene homepage_scene = new homepageScene();

        // set center on the screen
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - homepage_scene.getWidth()) * 0.1;
        double y = bounds.getMinY() + (bounds.getHeight() - homepage_scene.getHeight()) * 0.1;
        stage.setX(x);
        stage.setY(y);



        stage.setScene(homepage_scene.getScene());
    }

    public void registerBtnClick(ActionEvent e) throws Exception {
        System.out.println("Register clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage
        //TODO: generate id from process (arraylist)
        registerBox register_box = new registerBox();

        // dialog show
        Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
        dialogEditStage.setTitle("Register users");
        dialogEditStage.initModality(Modality.WINDOW_MODAL);
        dialogEditStage.initOwner(stage); // close this dialog to return to owner window
        dialogEditStage.setScene(register_box.getScene());

        dialogEditStage.showAndWait();
        //DatabaseUser.registerUser(new User(this.txtUsername.getText(), this.txtPassword.getText(), "a@b.c", LocalDate.of(2004,11,23), null));
    }

    public void closeBtnClick(ActionEvent e){
        System.exit(0);
    }

    // position to move screen around easily
    private double xOffset = 0;
    private double yOffset = 0;
    // move scene around
    public void drag(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void press(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
}
