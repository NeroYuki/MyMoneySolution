package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.FinancialGoal;

import java.net.URL;
import java.util.ResourceBundle;

public class editPlanController implements Initializable {
    @FXML
    public DatePicker startdatepicker;
    public DatePicker expiredatepicker;
    public TextArea descriptionTextArea;
    public Button saveBtn;
    public Button resetBtn;
    public ComboBox unitCombo;
    public ComboBox accountCombo;
    public TextField typeText;
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

    private FinancialGoal goal; // not used but maybe later


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(typeText.getText().equals("Balance")){
            this.accountCombo.setDisable(false);
        }
    }

    public void setPlan(FinancialGoal goal) {
        this.goal = goal;
        // set day
        startdatepicker.setValue(goal.getStartDate());
        expiredatepicker.setValue(goal.getExpireDate());
        // set account
        accountCombo.setValue(goal.getCheckBalance());
        // set type
        if(goal.getType()==1) {
            typeText.setText("Income");
        } else if(goal.getType()==2){
            typeText.setText("Expense");
        } else {
            typeText.setText("Balance");
        }
        // set compare label
        if(typeText.getText().equals("Balance")){
            this.comparisonLabel.setText("over");
        } else if(typeText.getText().equals("Income")) {
            this.comparisonLabel.setText("over");
        } else {
            this.comparisonLabel.setText("under");
        }
        thresholdText.setText(Double.toString(goal.getThreshold()));
        descriptionTextArea.setText(goal.getDescription());
    }

    public void saveBtnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        this.setPlan(goal);
    }


}
