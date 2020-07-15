package controller;

import exception.DatabaseException;
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
import javafx.util.StringConverter;
import model.*;
import process.ProcessBalance;
import process.ProcessCategories;
import process.ProcessTransaction;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class editTransactionBoxController implements Initializable {
    @FXML
    public DatePicker datepicker;
    public ComboBox<Balance> accountCombo;
    public ComboBox<Category> categoryCombo;
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
        setCategoryCombo();
        setAccountCombo();
        // set date
        datepicker.setValue(transaction.getTransDate());
        // set account, maybe not vital
        accountCombo.setValue(transaction.getApplyingBalance());
        // set category
        if(transaction.getClass().getName().equals("model.Income")) {
            categoryCombo.setValue(((Income)transaction).getCategory());
        }
        else if(transaction.getClass().getName().equals("model.Expense")) {
            categoryCombo.setValue(((Expense)transaction).getCategory());
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
        try{
            ProcessTransaction.revertTransaction(transaction);
            if(transaction.getClass().getName().equals("model.Income")) {
                ProcessTransaction.updateIncome(transaction.getId(),datepicker.getValue(), Double.parseDouble(valueText.getText()), descriptionTextArea.getText(), categoryCombo.getSelectionModel().getSelectedItem(), accountCombo.getSelectionModel().getSelectedItem());
            }
            if(transaction.getClass().getName().equals("model.Expense")){
                ProcessTransaction.updateExpense(transaction.getId(),datepicker.getValue(), Double.parseDouble(valueText.getText()), descriptionTextArea.getText(), categoryCombo.getSelectionModel().getSelectedItem(), accountCombo.getSelectionModel().getSelectedItem());
            }
        }
        catch (ProcessExeption pe)
        {
            System.out.println(pe.getErrorCodeMessage());
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        this.setTransaction(transaction);
    }


    public void setCategoryCombo(){
        ArrayList<Category> categories =new ArrayList<>();
        try{
            if(transaction.getClass().getName().equals("model.Income")) {
                categories = ProcessCategories.getIncomeCategories();
            }
            if(transaction.getClass().getName().equals("model.Expense")){
                categories=ProcessCategories.getExpenseCategories();
            }
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
        ObservableList<Category> categorieslist = FXCollections.observableArrayList(categories);
        categoryCombo.setItems(categorieslist);
        categoryCombo.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category o) {
                if(o==null) return "";
                return o.getName();
            }

            @Override
            public Category fromString(String s) {
                return null;
            }
        });
    }
    public void setAccountCombo(){
        ArrayList<Balance>balances=new ArrayList<>();
        try {
            balances = ProcessBalance.getBalances();
        }
        catch (ProcessExeption pe)
        {
            System.out.println(pe.getErrorCodeMessage());
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
}
