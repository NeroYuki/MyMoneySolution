package controller;

import exception.ProcessExeption;
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
    }

    @FXML
    VBox listBalanceBox;

    public void loadBalance(){
        //TODO: get right list from database
        //File folder = new File("src/main/resources/img/icon/income");

        // init array of original categories income
        ArrayList<Balance> balances = new ArrayList<Balance>();

        try{
            balances = ProcessBalance.getBalances();
        }
        catch (ProcessExeption pe){
            pe.getMessage();
        }

        ObservableList<Balance> items = FXCollections.observableArrayList(balances);
        balanceListView.setItems(items);
//        ArrayList<Category> finalCategories = categories;
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

                descriptionLabel.setTextAlignment(TextAlignment.CENTER);
                descriptionLabel.setStyle("-fx-font-size: 16");
                descriptionLabel.setPadding(new Insets(5, 0, 0, 15));

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
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 15px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 15px;");
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
                    Image image = new Image("/img/icon/income/credit-card.png",50,50,false,true);
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    //TODO: get right name from database
                    nameLabel.setText(balance.getName().toUpperCase()); // set name display of item
                    descriptionLabel.setText(balance.getDescription().toUpperCase()); // set description of item
                    valueLabel.setText(String.valueOf(balance.getValue())); // set value of item
                    differenceLabel.setText(String.valueOf(balance.getValue())); // set value of item
                    creationDateLabel.setText("Created on: \n" + LocalDate.now().toString());
                    setGraphic(rowBox);
                    //setGraphic(imageView);
                }
            }
        });
        listBalanceBox.getChildren().setAll(balanceListView);
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

    public void editBalanceBtnClick(ActionEvent actionEvent) {
    }

    public void deleteBalanceBtnClick(ActionEvent actionEvent) {
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
}
