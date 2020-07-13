package controller;

import exception.ProcessExeption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.net.URL;
import java.util.ResourceBundle;

public class addLoanBoxController implements Initializable {
    @FXML
    public Button saveBtn;
    @FXML
    public TextField nameText; // name text field
    public TextArea descriptionTextArea; // info text area
    public ComboBox interestIntervalCombo;
    public ComboBox paymentIntervalCombo;
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
        // init interval combo
        setIntervalCombo();
    }

    public void saveBtnClick(ActionEvent actionEvent) throws ProcessExeption {
        //TODO: save add loan to database and show list view
//        try {
//            ProcessCategories.saveCategories(nameText.getText(),iconImage.getImage().getUrl(),infoTextArea.getText(),typeCombo.getSelectionModel().getSelectedItem().toString());
//        }
//        catch (ProcessExeption pe)
//        {
//            System.out.println(pe.getErrorCodeMessage());
//        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void setIntervalCombo(){
        // type list
        ObservableList<String> typeInterestInterval = FXCollections.observableArrayList("Daily", "Weekly","Monthly","Quarterly","Yearly");
        ObservableList<String> typePaymentInterval = FXCollections.observableArrayList("Daily", "Weekly","Monthly","Quarterly","Yearly");

        // set data of combo interval
        interestIntervalCombo.setItems(typeInterestInterval);
        paymentIntervalCombo.setItems(typePaymentInterval);
    }

    public void resetBtnClick(ActionEvent actionEvent) {
    }
}
