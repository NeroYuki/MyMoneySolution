package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scenes.homepageScene;
import scenes.loginScene;

import java.io.IOException;

public class loginSceneController {
    @FXML
    public void loginBtnClick(ActionEvent e) throws Exception {
        System.out.println("Login clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
    }

    public void registerBtnClick(ActionEvent e) throws IOException {
        System.out.println("Register clicked");

    }

    public void closeBtnClick(ActionEvent e){
        System.exit(0);
    }
}
