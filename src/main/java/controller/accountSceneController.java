package controller;

import exception.ProcessExeption;
import helper.IntervalEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Balance;
import model.Loan;
import model.Saving;
import process.ProcessBalance;
import scenes.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class accountSceneController implements Initializable {
    ListView<Balance> balanceListView = new ListView<>();
    ListView<Saving> savingListView = new ListView<>();
    ListView<Loan> loanListView = new ListView<>();
    @FXML
    private ImageView addTransBtn;
    @FXML
    private ImageView planBtn;

    public void transactionBtnClick(ActionEvent e) throws Exception {
        System.out.println("Transaction clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        transactionScene transaction_scene = new transactionScene();
        stage.setScene(transaction_scene.getScene());
    }

    public void statisticsBtnClick(ActionEvent e) throws Exception {
        System.out.println("Statistics clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        statisticsScene statistics_scene = new statisticsScene();
        stage.setScene(statistics_scene.getScene());
    }

    public void categoriesBtnClick(ActionEvent e) throws Exception {
        System.out.println("Categories clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        categoriesScene categories_scene = new categoriesScene();
        stage.setScene(categories_scene.getScene());
    }

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Home clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
    }

    public void settingBtnClick(ActionEvent e) throws Exception {
        System.out.println("Setting clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        settingsScene settings_scene = new settingsScene();
        stage.setScene(settings_scene.getScene());
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // tooltip handle for add item and plan buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(planBtn, new Tooltip("Add financial goal"));

        loadBalance();
        loadSaving();
        loadLoan();
    }

    @FXML
    VBox listBalanceBox;

    public void loadBalance(){
        //TODO: get right list from database
        //File folder = new File("src/main/resources/img/icon/income");

        // init array of original categories income
        ArrayList<Balance> balances = new ArrayList<Balance>();

        // implement balance process there
        try{
            balances = ProcessBalance.getBalances();
        }
        catch (ProcessExeption pe){
            pe.getMessage();
        }

        balanceListView.setPrefHeight(470);

        ObservableList<Balance> items = FXCollections.observableArrayList(balances);
        balanceListView.setItems(items);

        ArrayList<Balance> finalBalances = balances;
        balanceListView.setCellFactory(param -> new ListCell<Balance>() {
            //test
            HBox rowBox = new HBox();
            Label nameLabel = new Label("");
            Label descriptionLabel = new Label("");
            Label valueLabel = new Label("");
            VBox infoBox = new VBox();
            Label differenceLabel = new Label(""); // compare to yesterday
            VBox compareBox = new VBox();
            Label creationDateLabel = new Label("");
            Pane pane = new Pane();
            Button deleteBtn = new Button("Delete");
            Button editBtn = new Button("Edit");

            private ImageView imageView = new ImageView();

            // initialize block in anonymous class implementation playing role constructor
            {
                // add elements of hbox
                compareBox.getChildren().addAll(valueLabel, differenceLabel);
                infoBox.getChildren().addAll(nameLabel, descriptionLabel);
                rowBox.getChildren().addAll(imageView, infoBox, compareBox, creationDateLabel, pane, editBtn, deleteBtn);
                HBox.setHgrow(pane, Priority.ALWAYS);

                imageView.setTranslateX(10);

                // style for title
                infoBox.setPadding(new Insets(0,0,0,10));
                infoBox.setPrefWidth(190);
                Tooltip.install(infoBox, new Tooltip("Balance info"));

                nameLabel.setTextAlignment(TextAlignment.CENTER);
                nameLabel.setStyle("-fx-font-size: 24");
                nameLabel.setPadding(new Insets(0, 0, 0, 15));
                nameLabel.setWrapText(true);

                descriptionLabel.setTextAlignment(TextAlignment.CENTER);
                descriptionLabel.setStyle("-fx-font-size: 16");
                descriptionLabel.setPadding(new Insets(5, 0, 0, 15));
                descriptionLabel.setWrapText(true);

                // style for value
                compareBox.setPadding(new Insets(0,0,0,10));
                compareBox.setPrefWidth(250);

                valueLabel.setTextAlignment(TextAlignment.CENTER);
                valueLabel.setStyle("-fx-font-size: 24");
                valueLabel.setPadding(new Insets(0, 0, 0, 15));

                differenceLabel.setTextAlignment(TextAlignment.CENTER);
                differenceLabel.setStyle("-fx-font-size: 16");
                differenceLabel.setPadding(new Insets(5, 0, 0, 15));

                Tooltip.install(valueLabel, new Tooltip("Total amount"));
                Tooltip.install(differenceLabel, new Tooltip("Difference compared to yesterday"));

                creationDateLabel.setTextAlignment(TextAlignment.CENTER);
                creationDateLabel.setStyle("-fx-font-size: 16");
                creationDateLabel.setPadding(new Insets(10, 0, 0, 50));

                // style button
                deleteBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                // button delete balance place in every item
                deleteBtn.setOnAction(event -> {
                    try{
                        String itemRemove = getListView().getItems().get(getIndex()).getName().replace(".png", "");
                        //confirmation to delete
                        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                                "Delete " + itemRemove + " ?", ButtonType.YES, ButtonType.NO);
                        alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertConfirm.showAndWait();
                        if (alertConfirm.getResult() == ButtonType.YES) {
                            System.out.println("selectIdx: " + getIndex());
                            System.out.println("item: " + itemRemove);
                            //TODO: delete balance in database there
                            getListView().getItems().remove(getItem());
//                            try {
//                                ProcessCategories.deleleCategory(finalBalances.get(getIndex()));
//                            } catch (ProcessExeption processExeption) {
//                                processExeption.printStackTrace();
//                            }
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                });

                // edit button
                editBtn.setOnAction(event -> {
                    try {
                        // get add income scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        editBalanceBox editBalance_box = new editBalanceBox();
                        // set value of dialog
                        Balance select = getListView().getItems().get(getIndex());
                        editBalance_box.getController().setBalance(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Edit categories");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(editBalance_box.getScene());

                        dialogAddStage.showAndWait();

                        // refresh balance data
                        loadBalance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }


            @Override
            public void updateItem(Balance balance, boolean empty) {
                super.updateItem(balance, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image("/img/self-icon.png",50,50,false,true);
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    nameLabel.setText(balance.getName().toUpperCase()); // set name display of item
                    descriptionLabel.setText(balance.getDescription().toUpperCase()); // set description of item
                    valueLabel.setText(String.valueOf(balance.getValue())); // set value of item
                    //TODO: set difference value yesterday
                    differenceLabel.setText(String.valueOf(balance.getValue())); // set difference value
                    creationDateLabel.setText("Created on: \n" + LocalDate.now().toString());
                    setGraphic(rowBox);
                    //setGraphic(imageView);
                }
            }
        });
        listBalanceBox.getChildren().setAll(balanceListView);
    }

    @FXML
    VBox listSavingBox;
    public void loadSaving(){
        //TODO: get right list from database
        //File folder = new File("src/main/resources/img/icon/income");

        // init array of original categories income
        ArrayList<Saving> savings = new ArrayList<Saving>();

        //TODO: implement saving process there
//        try{
//            balances = ProcessBalance.getBalances();
//        }
//        catch (ProcessExeption pe){
//            pe.getMessage();
//        }

        // init sample to test UI a bit
        savings.add(new Saving("test","this is a test",5.5,LocalDate.now(),90, IntervalEnum.INTERVAL.DAILY,10000,15000));

        savingListView.setPrefHeight(470);

        ObservableList<Saving> items = FXCollections.observableArrayList(savings);
        savingListView.setItems(items);
        ArrayList<Saving> finalBalances = savings;
        savingListView.setCellFactory(param -> new ListCell<Saving>() {
            //test
            HBox rowBox = new HBox();
            Label nameLabel = new Label("");
            Label descriptionLabel = new Label("");
            Label currentValueLabel = new Label("");
            VBox infoBox = new VBox();
            Label baseValueLabel = new Label("");
            VBox valueBox = new VBox();
            Label interestRateLabel = new Label("");
            Label intervalLabel = new Label("");
            VBox interestBox = new VBox();
            Label creationDateLabel = new Label("");
            Label timeSpanLabel = new Label("");
            VBox activeDateBox = new VBox();
            Pane pane = new Pane();
            Button deleteBtn = new Button("Deactivate");
            Button editBtn = new Button("Edit");
            HBox hbox1 = new HBox();
            Button depositBtn = new Button("Deposit");
            Button withdrawBtn = new Button("Withdraw");
            HBox hbox2 = new HBox();
            VBox buttonArea = new VBox();

            private ImageView imageView = new ImageView();

            // initialize block in anonymous class implementation playing role constructor
            {
                // add elements of hbox
                valueBox.getChildren().addAll(currentValueLabel, baseValueLabel);
                infoBox.getChildren().addAll(nameLabel, descriptionLabel);
                interestBox.getChildren().addAll(interestRateLabel,intervalLabel);
                activeDateBox.getChildren().addAll(creationDateLabel,timeSpanLabel);
                hbox1.getChildren().addAll(editBtn,deleteBtn);
                hbox2.getChildren().addAll(depositBtn,withdrawBtn);
                buttonArea.getChildren().addAll(hbox1,hbox2);
                rowBox.getChildren().addAll(infoBox, valueBox, interestBox, activeDateBox, pane, buttonArea);
                HBox.setHgrow(pane, Priority.ALWAYS);

                imageView.setTranslateX(10);

                // style for title
                infoBox.setPadding(new Insets(0,0,0,10));
                infoBox.setPrefWidth(140);
                Tooltip.install(infoBox, new Tooltip("Saving info"));

                nameLabel.setTextAlignment(TextAlignment.CENTER);
                nameLabel.setStyle("-fx-font-size: 24");
                nameLabel.setPadding(new Insets(0, 0, 0, 15));
                nameLabel.setWrapText(true);

                descriptionLabel.setTextAlignment(TextAlignment.CENTER);
                descriptionLabel.setStyle("-fx-font-size: 16");
                descriptionLabel.setPadding(new Insets(5, 0, 0, 15));
                descriptionLabel.setWrapText(true);

                // style for value
                valueBox.setPadding(new Insets(0,0,0,10));
                valueBox.setPrefWidth(190);

                currentValueLabel.setTextAlignment(TextAlignment.CENTER);
                currentValueLabel.setStyle("-fx-font-size: 20");
                currentValueLabel.setPadding(new Insets(0, 0, 0, 15));

                baseValueLabel.setTextAlignment(TextAlignment.CENTER);
                baseValueLabel.setStyle("-fx-font-size: 16");
                baseValueLabel.setPadding(new Insets(5, 0, 0, 35));

                // style for interest value
                interestBox.setPadding(new Insets(0,0,0,5));
                interestBox.setPrefWidth(170);

                interestRateLabel.setTextAlignment(TextAlignment.CENTER);
                interestRateLabel.setStyle("-fx-font-size: 18");
                interestRateLabel.setPadding(new Insets(0, 0, 0, 10));

                intervalLabel.setTextAlignment(TextAlignment.CENTER);
                intervalLabel.setStyle("-fx-font-size: 16");
                intervalLabel.setPadding(new Insets(5, 0, 0, 10));

                // style for active date
                activeDateBox.setPadding(new Insets(0,0,0,10));

                creationDateLabel.setTextAlignment(TextAlignment.CENTER);
                creationDateLabel.setStyle("-fx-font-size: 16");
                creationDateLabel.setPadding(new Insets(10, 0, 0, 10));

                timeSpanLabel.setTextAlignment(TextAlignment.CENTER);
                timeSpanLabel.setStyle("-fx-font-size: 16");
                timeSpanLabel.setPadding(new Insets(10, 0, 0, 10));

                // style button
                editBtn.setPrefWidth(100);
                deleteBtn.setPrefWidth(120);
                withdrawBtn.setPrefWidth(120);
                depositBtn.setPrefWidth(100);
                deleteBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                depositBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                withdrawBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                // button delete balance place in every item
                deleteBtn.setOnAction(event -> {
                    try{
                        String itemRemove = getListView().getItems().get(getIndex()).getName().replace(".png", "");
                        //confirmation to delete
                        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                                "Delete " + itemRemove + " ?", ButtonType.YES, ButtonType.NO);
                        alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertConfirm.showAndWait();
                        if (alertConfirm.getResult() == ButtonType.YES) {
                            System.out.println("selectIdx: " + getIndex());
                            System.out.println("item: " + itemRemove);
                            //TODO: delete saving in database there
                            getListView().getItems().remove(getItem());
//                            try {
//                                ProcessCategories.deleleCategory(finalBalances.get(getIndex()));
//                            } catch (ProcessExeption processExeption) {
//                                processExeption.printStackTrace();
//                            }
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                });

                // edit button

                // deposit button
                editBtn.setOnAction(event -> {
                    try{
                        // get add income scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        editSavingBox editSaving_box = new editSavingBox();
                        // set value of dialog
                        Saving select = getListView().getItems().get(getIndex());
                        editSaving_box.getController().setSaving(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Edit savings");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(editSaving_box.getScene());

                        dialogAddStage.showAndWait();

                        // refresh data saving
                        loadSaving();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                });

                depositBtn.setOnAction(event -> {
                    try {
                        // get deposit scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        depositSavingBox depositSaving_box = new depositSavingBox();
                        // set value of dialog
                        Saving select = getListView().getItems().get(getIndex());
                        depositSaving_box.getController().setSaving(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Deposit saving");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(depositSaving_box.getScene());

                        dialogAddStage.showAndWait();

                        // refresh data saing
                        loadSaving();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                withdrawBtn.setOnAction(event -> {
                    try {
                        // get withdraw scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        withdrawSavingBox withdrawSaving_box = new withdrawSavingBox();
                        // set value of dialog
                        Saving select = getListView().getItems().get(getIndex());
                        withdrawSaving_box.getController().setSaving(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Withdraw saving");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(withdrawSaving_box.getScene());

                        dialogAddStage.showAndWait();
                        // refresh data saing
                        loadSaving();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }


            @Override
            public void updateItem(Saving saving, boolean empty) {
                super.updateItem(saving, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image("/img/saving.png",50,50,false,true);
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    //TODO: get right name from database
                    nameLabel.setText(saving.getName().toUpperCase()); // set name display of item
                    descriptionLabel.setText(saving.getDescription().toUpperCase()); // set description of item
                    currentValueLabel.setText(String.valueOf("Current value: \n" + saving.getCurrentValue())); // set  current value of item
                    baseValueLabel.setText(String.valueOf("Base value: \n" + saving.getBaseValue())); // set base value of item
                    creationDateLabel.setText("Created on: \n" + saving.getCreationDate()); // get creation date of item
                    if(saving.getActiveTimeSpan() > 1)
                        timeSpanLabel.setText("Time span: \n" + saving.getActiveTimeSpan() + " days");
                    else
                        timeSpanLabel.setText("Time span: \n" + saving.getActiveTimeSpan() + " day");
                    interestRateLabel.setText("\nInterest rate: " + saving.getInterestRate()+ "%");
                    intervalLabel.setText("Interval: "+ saving.getInterestInterval());
                    setGraphic(rowBox);
                    //setGraphic(imageView);
                }
            }
        });
        listSavingBox.getChildren().setAll(savingListView);
    }

    @FXML
    VBox listLoanBox;
    public void loadLoan(){
        //TODO: get right list from database
        //File folder = new File("src/main/resources/img/icon/income");

        // init array of original categories income
        ArrayList<Loan> loans = new ArrayList<Loan>();

        //TODO: implement loan process there
//        try{
//            balances = ProcessBalance.getBalances();
//        }
//        catch (ProcessExeption pe){
//            pe.getMessage();
//        }

        // init sample to test UI a bit
        loans.add(new Loan("test","this is a test",5.5,LocalDate.now(),90, IntervalEnum.INTERVAL.DAILY, IntervalEnum.INTERVAL.WEEKLY,10000,15000));

        loanListView.setPrefHeight(470);

        ObservableList<Loan> items = FXCollections.observableArrayList(loans);
        loanListView.setItems(items);
        ArrayList<Loan> finalBalances = loans;
        loanListView.setCellFactory(param -> new ListCell<Loan>() {
            //test
            HBox rowBox = new HBox();
            Label nameLabel = new Label("");
            Label descriptionLabel = new Label("");
            Label currentValueLabel = new Label("");
            VBox infoBox = new VBox();
            Label baseValueLabel = new Label("");
            VBox valueBox = new VBox();
            Label interestRateLabel = new Label("");
            Label interestIntervalLabel = new Label("");
            Label paymentIntervalLabel = new Label("");
            VBox interestBox = new VBox();
            Label creationDateLabel = new Label("");
            Label timeSpanLabel = new Label("");
            VBox activeDateBox = new VBox();
            Pane pane = new Pane();
            Button deleteBtn = new Button("Deactivate");
            Button editBtn = new Button("Edit");
            HBox hbox1 = new HBox();
            Button withdrawBtn = new Button("Withdraw");
            HBox hbox2 = new HBox();
            VBox buttonArea = new VBox();

            private ImageView imageView = new ImageView();

            // initialize block in anonymous class implementation playing role constructor
            {
                // add elements of hbox
                valueBox.getChildren().addAll(currentValueLabel, baseValueLabel);
                infoBox.getChildren().addAll(nameLabel, descriptionLabel);
                interestBox.getChildren().addAll(interestRateLabel, interestIntervalLabel, paymentIntervalLabel);
                activeDateBox.getChildren().addAll(creationDateLabel,timeSpanLabel);
                hbox1.getChildren().addAll(editBtn,deleteBtn);
                hbox2.getChildren().addAll(withdrawBtn);
                buttonArea.getChildren().addAll(hbox1,hbox2);
                rowBox.getChildren().addAll(infoBox, valueBox, interestBox, activeDateBox, pane, buttonArea);
                HBox.setHgrow(pane, Priority.ALWAYS);

                imageView.setTranslateX(10);

                // style for title
                infoBox.setPadding(new Insets(0,0,0,10));
                infoBox.setPrefWidth(140);
                Tooltip.install(infoBox, new Tooltip("Loan info"));

                nameLabel.setTextAlignment(TextAlignment.CENTER);
                nameLabel.setStyle("-fx-font-size: 24");
                nameLabel.setPadding(new Insets(0, 0, 0, 15));
                nameLabel.setWrapText(true);

                descriptionLabel.setTextAlignment(TextAlignment.CENTER);
                descriptionLabel.setStyle("-fx-font-size: 16");
                descriptionLabel.setPadding(new Insets(5, 0, 0, 15));
                descriptionLabel.setWrapText(true);

                // style for value
                valueBox.setPadding(new Insets(0,0,0,10));
                valueBox.setPrefWidth(190);

                currentValueLabel.setTextAlignment(TextAlignment.CENTER);
                currentValueLabel.setStyle("-fx-font-size: 20");
                currentValueLabel.setPadding(new Insets(0, 0, 0, 15));

                baseValueLabel.setTextAlignment(TextAlignment.CENTER);
                baseValueLabel.setStyle("-fx-font-size: 16");
                baseValueLabel.setPadding(new Insets(5, 0, 0, 35));

                // style for interest value
                interestBox.setPadding(new Insets(0,0,0,5));
                interestBox.setPrefWidth(170);

                interestRateLabel.setTextAlignment(TextAlignment.CENTER);
                interestRateLabel.setStyle("-fx-font-size: 18");
                interestRateLabel.setPadding(new Insets(0, 0, 0, 10));

                interestIntervalLabel.setTextAlignment(TextAlignment.CENTER);
                interestIntervalLabel.setStyle("-fx-font-size: 16");
                interestIntervalLabel.setPadding(new Insets(5, 0, 0, 10));

                paymentIntervalLabel.setTextAlignment(TextAlignment.CENTER);
                paymentIntervalLabel.setStyle("-fx-font-size: 16");
                paymentIntervalLabel.setPadding(new Insets(5, 0, 0, 10));

                // style for active date
                activeDateBox.setPadding(new Insets(0,0,0,10));

                creationDateLabel.setTextAlignment(TextAlignment.CENTER);
                creationDateLabel.setStyle("-fx-font-size: 16");
                creationDateLabel.setPadding(new Insets(10, 0, 0, 10));

                timeSpanLabel.setTextAlignment(TextAlignment.CENTER);
                timeSpanLabel.setStyle("-fx-font-size: 16");
                timeSpanLabel.setPadding(new Insets(10, 0, 0, 10));

                // style button
                editBtn.setPrefWidth(100);
                deleteBtn.setPrefWidth(120);
                withdrawBtn.setPrefWidth(120);
                withdrawBtn.setTranslateX(50);
                deleteBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                withdrawBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                // button delete balance place in every item
                deleteBtn.setOnAction(event -> {
                    try{
                        String itemRemove = getListView().getItems().get(getIndex()).getName().replace(".png", "");
                        //confirmation to delete
                        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                                "Delete " + itemRemove + " ?", ButtonType.YES, ButtonType.NO);
                        alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertConfirm.showAndWait();
                        if (alertConfirm.getResult() == ButtonType.YES) {
                            System.out.println("selectIdx: " + getIndex());
                            System.out.println("item: " + itemRemove);
                            //TODO: delete loan in database there
                            getListView().getItems().remove(getItem());
//                            try {
//                                ProcessCategories.deleleCategory(finalBalances.get(getIndex()));
//                            } catch (ProcessExeption processExeption) {
//                                processExeption.printStackTrace();
//                            }
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                });

                // edit button
                editBtn.setOnAction(event -> {
                    try{
                        // get add income scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        editLoanBox editLoan_box = new editLoanBox();
                        // set value of dialog
                        Loan select = getListView().getItems().get(getIndex());
                        editLoan_box.getController().setLoan(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Edit loans");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(editLoan_box.getScene());

                        dialogAddStage.showAndWait();

                        // refresh data loan
                        loadLoan();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                });

                withdrawBtn.setOnAction(event -> {
                    try {
                        // get withdraw scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        withdrawLoanBox withdrawLoan_box = new withdrawLoanBox();
                        // set value of dialog
                        Loan select = getListView().getItems().get(getIndex());
                        withdrawLoan_box.getController().setLoan(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Withdraw loan");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(withdrawLoan_box.getScene());

                        dialogAddStage.showAndWait();
                        // refresh data saing
                        loadLoan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }


            @Override
            public void updateItem(Loan loan, boolean empty) {
                super.updateItem(loan, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image("/img/loan.png",50,50,false,true);
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    //TODO: get right name from database
                    nameLabel.setText(loan.getName().toUpperCase()); // set name display of item
                    descriptionLabel.setText(loan.getDescription().toUpperCase()); // set description of item
                    currentValueLabel.setText(String.valueOf("Current value: \n" + loan.getCurrentValue())); // set  current value of item
                    baseValueLabel.setText(String.valueOf("Base value: \n" + loan.getBaseValue())); // set base value of item
                    creationDateLabel.setText("Created on: \n" + loan.getCreationDate()); // get creation date of item
                    if(loan.getActiveTimeSpan() > 1)
                        timeSpanLabel.setText("Time span: \n" + loan.getActiveTimeSpan() + " days");
                    else
                        timeSpanLabel.setText("Time span: \n" + loan.getActiveTimeSpan() + " day");
                    interestRateLabel.setText("\nInterest rate: " + loan.getInterestRate()+ "%");
                    interestIntervalLabel.setText("Interval: "+ loan.getInterestInterval());
                    paymentIntervalLabel.setText("Payment: " + loan.getPaymentInterval());
                    setGraphic(rowBox);
                    //setGraphic(imageView);
                }
            }
        });
        listLoanBox.getChildren().setAll(loanListView);
    }

    public void addTransClick(MouseEvent e) throws Exception {
        // first appear a dialog to choose the type of transaction for clear handle
        List<String> choices = new ArrayList<>();
        choices.add("Income");
        choices.add("Expenses");
        choices.add("Transfer");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Expenses", choices);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setTitle("Type of transaction dialog");
        dialog.setHeaderText("Pick one type to continue the transaction");
        dialog.setContentText("Type");

        // Traditional way to get the response value
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Option " + result.get());

            // income select event
            if(result.get() == "Income"){
                // get add income scene
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

                addIncomeBox addIncome_box = new addIncomeBox();

                // dialog show
                Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                dialogAddStage.setTitle("Add income");
                dialogAddStage.initModality(Modality.WINDOW_MODAL);
                dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                dialogAddStage.setScene(addIncome_box.getScene());

                dialogAddStage.showAndWait();
            }
            else if(result.get() == "Expenses"){ // expense select option
                // get add income scene
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

                addExpenseBox addExpense_box = new addExpenseBox();

                // dialog show
                Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                dialogAddStage.setTitle("Add expense");
                dialogAddStage.initModality(Modality.WINDOW_MODAL);
                dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                dialogAddStage.setScene(addExpense_box.getScene());

                dialogAddStage.showAndWait();
            }
        }

    }

    public void addPlanClick(MouseEvent e) throws Exception {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addPlanBox addPlan_box = new addPlanBox();

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add financial goal");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addPlan_box.getScene());

        dialogAddStage.showAndWait();

    }

    public void addBalanceBtnClick(ActionEvent e) throws Exception {
        // get add income scene
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addBalanceBox addBalance_box = new addBalanceBox();
        //System.out.println("Add categories click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add balance");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addBalance_box.getScene());

        dialogAddStage.showAndWait();
        // refresh balance in listview
        loadBalance();
    }

    public void addSavingBtnClick(ActionEvent e) throws Exception {
        // get add saving scene
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addSavingBox addSaving_box = new addSavingBox();
        System.out.println("Add savings click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add saving");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addSaving_box.getScene());

        dialogAddStage.showAndWait();
        // refresh saving in listview
        loadSaving();
    }

    public void addLoanBtnClick(ActionEvent actionEvent) throws Exception {
        // get add loan scene
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addLoanBox addLoan_box = new addLoanBox();
        System.out.println("Add savings click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add loan");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addLoan_box.getScene());

        dialogAddStage.showAndWait();
        // refresh saving in listview
        loadLoan();
    }
}
