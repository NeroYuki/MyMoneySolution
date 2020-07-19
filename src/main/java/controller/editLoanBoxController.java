package controller;

import exception.ProcessExeption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Loan;
import process.ProcessLoan;

import java.net.URL;
import java.util.ResourceBundle;

public class editLoanBoxController implements Initializable {
    @FXML
    public Button saveBtn;
    @FXML
    public TextField nameText; // name text field
    public TextArea descriptionTextArea; // info text area
    public TextField interestIntervalText;
    public TextField paymentIntervalText;
    public TextField timeSpanText;
    public TextField baseValueText; // value text field
    public TextField interestRateText;
    public ComboBox unitCombo;

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

    private Loan loan;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLoan(Loan loan){
        this.loan = loan;
        // set name
        nameText.setText(loan.getName());
        // set info
        descriptionTextArea.setText(loan.getDescription());
        // set interval interest
        interestIntervalText.setText("" + loan.getInterestInterval());
        // set interval payment
        paymentIntervalText.setText("" + loan.getPaymentInterval());
        // set active time span
        timeSpanText.setText(Integer.toString(loan.getActiveTimeSpan()));
        // set base value
        baseValueText.setText(String.format("%8d",(int)(loan.getBaseValue())));
        // set interest
        interestRateText.setText(Double.toString(loan.getInterestRate()));
    }

    public void saveBtnClick(ActionEvent actionEvent) throws ProcessExeption {
        //TODO: save add saving to database and show list view
        try {
            ProcessLoan.editLoan(loan,nameText.getText(),descriptionTextArea.getText());
        }
        catch (ProcessExeption pe)
        {
            System.out.println(pe.getErrorCodeMessage());
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void resetBtnClick(ActionEvent actionEvent) {
        this.setLoan(loan);
    }
}
