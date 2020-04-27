package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import scenes.*;

import java.net.URL;
import java.util.ResourceBundle;

public class statisticsSceneController implements Initializable {
    @FXML
    public Label expensesNote;
    public Label incomeNote;

    public void transactionBtnClick(ActionEvent e) throws Exception {
        System.out.println("Transaction clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        transactionScene transaction_scene = new transactionScene();
        stage.setScene(transaction_scene.getScene());
    }

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Home clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
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

    public void settingBtnClick(ActionEvent e) throws Exception {
        System.out.println("Setting clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        settingsScene settings_scene = new settingsScene();
        stage.setScene(settings_scene.getScene());
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

    @FXML
    PieChart incomePie;
    @FXML
    PieChart expensesPie;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void incomeLoad(ActionEvent actionEvent) {
        try{
            // data to be imported
            int salaryValue = 200;
            int savingValue = 300;
            int businessValue = 600;
            PieChart.Data salary = new PieChart.Data("salary", salaryValue);
            PieChart.Data saving = new PieChart.Data("saving", savingValue);
            PieChart.Data business = new PieChart.Data("business", businessValue);
            incomePie.getData().clear();
            incomePie.getData().addAll(salary, saving,business);
            // display info when click
            for(PieChart.Data data : incomePie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }

            incomeNote.setVisible(false);
        }
        catch(Exception ex){

        }
    }

    public void expensesLoad(ActionEvent actionEvent) {
        try{
            // data to be imported
            int shopValue = 400;
            int billValue = 280;
            int healthValue = 500;
            PieChart.Data shop = new PieChart.Data("shopping", shopValue);
            PieChart.Data billing = new PieChart.Data("bill", billValue);
            PieChart.Data health = new PieChart.Data("health", healthValue);
            expensesPie.getData().clear();
            expensesPie.getData().addAll(shop, billing,health);
            // display info when click
            for(PieChart.Data data : expensesPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }

            expensesNote.setVisible(false);
        }
        catch(Exception ex){

        }
    }
}
