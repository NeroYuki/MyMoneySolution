package controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import scenes.*;

public class settingSceneController {
    public void transactionBtnClick(ActionEvent e) throws Exception {
        System.out.println("Transaction clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        transactionScene transaction_scene = new transactionScene();
        stage.setScene(transaction_scene.getScene());
    }

    public void statisticsBtnClick(ActionEvent e) throws Exception {
        System.out.println("Statistics clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        statisticsScene statistics_scene = new statisticsScene();
        stage.setScene(statistics_scene.getScene());
    }

    public void categoriesBtnClick(ActionEvent e) throws Exception {
        System.out.println("Categories clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        categoriesScene categories_scene = new categoriesScene();
        stage.setScene(categories_scene.getScene());
    }

    public void accountBtnClick(ActionEvent e) throws Exception {
        System.out.println("Account clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        accountScene account_scene = new accountScene();
        stage.setScene(account_scene.getScene());
    }

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Account clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
    }
}
