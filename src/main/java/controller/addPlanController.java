package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class addPlanController implements Initializable {
    @FXML
    public DatePicker startdatepicker;
    public DatePicker expiredatepicker;
    public TextArea descriptionTextArea;
    public Button saveBtn;
    public Button resetBtn;
    public ComboBox unitCombo;
    public ComboBox accountCombo;
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
    }

    public void saveBtnClick(ActionEvent actionEvent) {


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

    public void typeTextChange(ActionEvent actionEvent) {
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
