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
import javafx.util.StringConverter;
import model.Balance;
import model.Category;
import model.Transaction;
import process.ProcessBalance;
import process.ProcessCategories;
import process.ProcessSaving;
import process.ProcessTransaction;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addIncomeBoxController implements Initializable {
    @FXML
    public DatePicker datepicker;
    public ComboBox<Balance> accountCombo;
    public ComboBox<Category> categoryCombo;
    public TextField valueText;
    public ComboBox unitCombo;
    public TextArea descriptionTextArea;
    public Button savingLoanBtn;
    public Button saveBtn;
    public Button resetBtn;

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;
    public boolean saved=false;
    public String idSaving="";
    public String idLoan="";
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
        setCategoryCombo();
        setAccountCombo();
    }

    public void setDefaultForSavingLoan(String des){
        descriptionTextArea.setText(des);
    }

    public void saveBtnClick(ActionEvent actionEvent) throws Exception {
        try {
            if(idSaving!="") {
                if(ProcessSaving.getSaving(idSaving).getCurrentValue()>=Double.parseDouble(valueText.getText())) {
                    ProcessTransaction.addIncome(datepicker.getValue(), Double.parseDouble(valueText.getText()), descriptionTextArea.getText(), categoryCombo.getSelectionModel().getSelectedItem(), accountCombo.getSelectionModel().getSelectedItem());
                    ProcessSaving.withdrawSaving(idSaving,Double.parseDouble(valueText.getText()));
                    saved = true;
                }
                else{
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Inform");
                    alert.setTitle("");
                    alert.setContentText("Current Saving can't withdraw that much money");
                    alert.showAndWait();
                }
            }
            else {
                ProcessTransaction.addIncome(datepicker.getValue(), Double.parseDouble(valueText.getText()), descriptionTextArea.getText(), categoryCombo.getSelectionModel().getSelectedItem(), accountCombo.getSelectionModel().getSelectedItem());
                saved = true;
            }
        } catch (ProcessExeption pe) {
            System.out.println(pe.getErrorCodeMessage());
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void resetBtnClick(ActionEvent actionEvent) {
    }

    public void openSavingLoanBtnClick(ActionEvent actionEvent) {

    }

    public void setCategoryCombo(){
        ArrayList<Category> categories =new ArrayList<>();
        try{
            categories= ProcessCategories.getIncomeCategories();
        }
        catch (ProcessExeption de)
        {
            System.out.println("addIccome setcat");
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
