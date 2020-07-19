package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Saving;

import java.net.URL;
import java.util.ResourceBundle;

public class depositSavingBoxController implements Initializable {
    @FXML
    public Button saveBtn;
    public Button demoBtn;
    @FXML
    public TextField nameText; // name text field
    public TextField currentValueText;
    public TextField depositValueText;
    public TextField expectedValueText;

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;

    private Saving saving;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setSaving(Saving saving){
        this.saving = saving;
        // set name
        nameText.setText(saving.getName());
        // set current
        currentValueText.setText(String.format("%8d",(int)saving.getCurrentValue()));

    }

    public void confirmBtnClick(ActionEvent actionEvent) {
        //TODO: save add categories to database and show list view

//        try {
//            ProcessCategories.updateCategories(category.getId(),nameText.getText(),iconImage.getImage().getUrl(),infoTextArea.getText(),category.getType(),category.getUsed());
//            System.out.println(iconImage.getImage().getUrl());
//        }
//        catch (ProcessExeption pe)
//        {
//            System.out.println(pe.getErrorCodeMessage());
//        }


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void demoBtnClick(ActionEvent actionEvent) {
        //TODO: set event after click demo button
    }
}
