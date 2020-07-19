package controller;

import exception.ProcessExeption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import model.Balance;
import model.Transaction;
import process.ProcessBalance;
import process.ProcessFinancialGoal;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addPlanController implements Initializable {
    @FXML
    public DatePicker startdatepicker;
    public DatePicker expiredatepicker;
    public TextArea descriptionTextArea;
    public Button saveBtn;
    public Button resetBtn;
    public ComboBox unitCombo;
    public ComboBox<Balance> accountCombo;
    public ComboBox typeCombo;
    public Label comparisonLabel;
    public TextField thresholdText;

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;

    public void setDialogStage(Stage dialogStage) { // not use this set method but maybe used later
        this.dialogEditStage = dialogStage;
    }

    public void closeBtnClick(ActionEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
        //dialogEditStage.close();
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

    private Transaction transaction; // not used but maybe later



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeComboSet();
        setAccountCombo();
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        try {
            if (typeCombo.getSelectionModel().getSelectedIndex() == 0) {
                ProcessFinancialGoal.addFinancialGoal(startdatepicker.getValue(),expiredatepicker.getValue(),Double.parseDouble(thresholdText.getText()),1,descriptionTextArea.getText());
            }
            else
            if (typeCombo.getSelectionModel().getSelectedIndex() == 1) {
                ProcessFinancialGoal.addFinancialGoal(startdatepicker.getValue(),expiredatepicker.getValue(),Double.parseDouble(thresholdText.getText()),2,descriptionTextArea.getText());
            }
            else
            if (typeCombo.getSelectionModel().getSelectedIndex() == 2) {
                ProcessFinancialGoal.addFinancialGoal(startdatepicker.getValue(),expiredatepicker.getValue(),accountCombo.getSelectionModel().getSelectedItem(),Double.parseDouble(thresholdText.getText()),3,descriptionTextArea.getText());
            }
            else
            {
                System.out.println("vui long chon type" );
            }
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void resetBtnClick(ActionEvent actionEvent) {
    }

    public void typeComboSet() {
        // type list
        ObservableList<String> typeCategories = FXCollections.observableArrayList("Income", "Expense", "Balance");

        // set data of combo type
        typeCombo.setItems(typeCategories);
    }
    public void setAccountCombo(){
        ArrayList<Balance> balances=new ArrayList<>();
        try {
            balances = ProcessBalance.getBalances();
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for account is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;
        }
        ObservableList<Balance> Balancelist = FXCollections.observableArrayList(balances);
        accountCombo.setItems(Balancelist);
        accountCombo.setConverter(new StringConverter<Balance>() {
            @Override
            public String toString(Balance o) {
                if(o==null) return "";
                return o.getName();
            }

            @Override
            public Balance fromString(String s) {
                return null;
            }
        });
    }


    public void typeTextChange(ActionEvent actionEvent) {
        accountCombo.setEditable(false);
        if(typeCombo.getValue().equals("Balance")){
            accountCombo.setDisable(false);
            this.comparisonLabel.setText("over");
        } else if(typeCombo.getValue().equals("Income")) {
            accountCombo.setDisable(true);
            this.comparisonLabel.setText("over");
        } else{
            accountCombo.setDisable(true);
            this.comparisonLabel.setText("under");
        }
    }
}
