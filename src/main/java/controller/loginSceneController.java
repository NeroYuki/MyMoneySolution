package controller;

import database.DatabaseManager;
import database.DatabaseUser;
import exception.DatabaseException;
import exception.ProcessExeption;
import helper.CharacterEncoding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.User;
import process.ProcessUser;
import scenes.homepageScene;

import java.io.IOException;
import java.time.LocalDate;

public class loginSceneController {
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtPassword;

    public void loginBtnClick(ActionEvent e) throws Exception {
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

        try{
            ProcessUser.login(txtUsername.getText(),txtPassword.getText());
        }
        catch (ProcessExeption pe){
            throw pe;
        }

        stage.setScene(homepage_scene.getScene());
    }

    public void registerBtnClick(ActionEvent e) throws Exception {
        System.out.println("Register clicked");
        DatabaseUser.registerUser(new User(this.txtUsername.getText(), this.txtPassword.getText(), "a@b.c", LocalDate.of(2004,11,23), null));
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
