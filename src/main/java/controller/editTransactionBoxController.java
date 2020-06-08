package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Expense;
import model.Income;
import model.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class editTransactionBoxController implements Initializable {
    @FXML
    public TextField valueText;
    public ComboBox unitCombo;
    public DatePicker datepicker;
    public TextArea descriptionTextArea;
    public Button saveBtn;
    public Button resetBtn;
    public ComboBox accountCombo;
    public ComboBox categoryCombo;
    public TextField typeText;

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;

    public void setDialogStage(Stage dialogStage) { // not use this set method but maybe used later
        this.dialogEditStage = dialogStage;
    } // maybe need to use

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

    public void setDefaultValue(Transaction transaction) {
        System.out.println("alo");
        //TODO: set field the same in the selected row
        //this.transaction = transaction;
//        datepicker.setValue(transaction.getTransDate());
//        System.out.println("get date");
//        accountCombo.setValue(transaction.getTransValue());
//        System.out.println("get account");
//        if(transaction.getClass().getName().equals("model.Income")) {
//            categoryCombo.setValue(((Income)transaction).getCategoryName());
//        }
//        else if(transaction.getClass().getName().equals("model.Expense")) {
//            categoryCombo.setValue(((Expense)transaction).getCategoryName());
//        }
//        valueText.setText(Double.toString(transaction.getTransValue()));
//        System.out.println("get value");
        //descriptionTextArea.setText(transaction.getTransDescription());
        //valueText.setText(transaction.getTransDescription());
        //System.out.println("get description" + transaction.getTransDescription());
    }

    public void setTransaction(Transaction trans) {
        this.transaction = trans;
        valueText.setText(Double.toString(this.transaction.getTransValue()));
        datepicker.setValue(transaction.getTransDate());
        if(transaction.getClass().getName().equals("model.Income")) {
            categoryCombo.setValue(((Income)transaction).getCategoryName());
        }
        else if(transaction.getClass().getName().equals("model.Expense")) {
            categoryCombo.setValue(((Expense)transaction).getCategoryName());
        }
        descriptionTextArea.setText(transaction.getTransDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
