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
    public DatePicker datepicker;
    public ComboBox accountCombo;
    public ComboBox categoryCombo;
    public TextField valueText;
    public ComboBox unitCombo;
    public TextArea descriptionTextArea;
    public TextField typeText;
    public Button saveBtn;
    public Button resetBtn;

    public editTransactionBoxController(){
    }

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;

    private Transaction transaction;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setTransaction(Transaction transaction){
        this.transaction = transaction;
        // set date
        datepicker.setValue(transaction.getTransDate());
        // set account, maybe not vital
        accountCombo.setValue(transaction.getApplyingBalance());
        // set category
        if(transaction.getClass().getName().equals("model.Income")) {
            categoryCombo.setValue(((Income)transaction).getCategoryName());
        }
        else if(transaction.getClass().getName().equals("model.Expense")) {
            categoryCombo.setValue(((Expense)transaction).getCategoryName());
        }
        // set type
        if(transaction.getClass().getName().equals("model.Income")){
            typeText.setText("Income");
        }
        else if(transaction.getClass().getName().equals("model.Expense")){
            typeText.setText("Expense");
        }
        valueText.setText(Double.toString(Math.abs(this.transaction.getTransValue())));
        // set description
        descriptionTextArea.setText(this.transaction.getTransDescription());
    }

    public void saveBtnClick(ActionEvent actionEvent) {
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        this.setTransaction(transaction);
    }

}
