package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scenes.homepageScene;
import scenes.loginScene;

import java.io.IOException;

public class loginSceneController {
    @FXML
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

        stage.setScene(homepage_scene.getScene());
    }

    public void registerBtnClick(ActionEvent e) throws IOException {
        System.out.println("Register clicked");

    }

    public void closeBtnClick(ActionEvent e){
        System.exit(0);
    }
}
