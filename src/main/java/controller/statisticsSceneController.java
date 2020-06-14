package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import process.ProcessStaticstics;
import scenes.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class statisticsSceneController implements Initializable {
    @FXML
    public Label expensesNote;
    @FXML
    public Label incomeNote;
    @FXML
    public ImageView addTransBtn;
    @FXML
    public ImageView planBtn;

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
        // tooltip handle for add item and plan buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(planBtn, new Tooltip("Add plan"));

    }

    public void incomeLoad(ActionEvent actionEvent) {
        try{
            // data to be imported
            //TODO: get suitable values from own database
            ProcessStaticstics.getTimelyIncome(4);

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

    public void addTransClick(MouseEvent e) throws Exception {
        // first appear a dialog to choose the type of transaction for clear handle
        List<String> choices = new ArrayList<>();
        choices.add("Income");
        choices.add("Expenses");
        choices.add("Transfer");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Expenses", choices);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setTitle("Type of transaction dialog");
        dialog.setHeaderText("Pick one type to continue the transaction");
        dialog.setContentText("Type");

        // Traditional way to get the response value
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Option " + result.get());

            // income select event
            if(result.get() == "Income"){
                // get add income scene
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

                addIncomeBox addIncome_box = new addIncomeBox();

                // dialog show
                Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                dialogAddStage.setTitle("Add income");
                dialogAddStage.initModality(Modality.WINDOW_MODAL);
                dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                dialogAddStage.setScene(addIncome_box.getScene());

                dialogAddStage.showAndWait();
            }
            else if(result.get() == "Expenses"){ // expense select option
                // get add income scene
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

                addExpenseBox addExpense_box = new addExpenseBox();

                // dialog show
                Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                dialogAddStage.setTitle("Add expense");
                dialogAddStage.initModality(Modality.WINDOW_MODAL);
                dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                dialogAddStage.setScene(addExpense_box.getScene());

                dialogAddStage.showAndWait();
            }
        }

    }

    public void addPlanClick(MouseEvent event) {
    }
}
