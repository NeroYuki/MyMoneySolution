package controller;

import helper.ComponentUI.RingProgressIndicator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Balance;
import model.FinancialGoal;
import scenes.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class homepageSceneController  implements Initializable {
    @FXML
    public AnchorPane progressArea;
    @FXML
    BorderPane homeBorderPane;
    @FXML
    public ImageView addTransBtn;
    @FXML
    public ImageView planBtn;

    @FXML
    public TableView<FinancialGoal> goalTable;
    public TableColumn<FinancialGoal, String> statusColumn;
    public TableColumn<FinancialGoal, String> descriptionColumn;
    public TableColumn<FinancialGoal, Integer> daysColumn;

    private ObservableList<FinancialGoal> goalList = FXCollections.observableArrayList(); // list of goal (default by date)

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

    public void accountBtnClick(ActionEvent e) throws Exception {
        System.out.println("Account clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        accountScene account_scene = new accountScene();
        stage.setScene(account_scene.getScene());
    }

    public void settingBtnClick(ActionEvent e) throws Exception {
        System.out.println("Setting clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        settingsScene settings_scene = new settingsScene();
        stage.setScene(settings_scene.getScene());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // tooltip handle for add item and plan buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(planBtn, new Tooltip("Add financial goal"));

        // add progress indicator
        RingProgressIndicator indicator = new RingProgressIndicator();
        //TODO: get first data in table view
        Slider slider = new Slider(0, 100, 50);
        indicator.setStyle("-fx-background:  #E6E6FA;");

        slider.valueProperty().addListener((o, oldVal, newVal) -> indicator.setProgress(newVal.intValue()));
        VBox main = new VBox(1, indicator);
        indicator.setProgress(Double.valueOf(slider.getValue()).intValue());

        slider.setValue(90); // set value of progress using this
        main.setLayoutX(progressArea.getPrefWidth()/3.8);
        main.setLayoutY(progressArea.getPrefWidth()/3.5);

        progressArea.getChildren().add(main);

        // change progress goal show color base on type of goal
        indicator.setColor("red");
        indicator.setColor("green");

        // display table
        displayTableView();

    }

    private void displayTableView() {
        // table view handle
        //TODO: get right list from own database
        ArrayList<FinancialGoal> goals =new ArrayList<>();
//        try{
//            goals = ProcessTransactionScene.getTransactionsInfo();
//        }
//        catch (ProcessExeption pe)
//        {
//            System.out.println(pe.getErrorCodeMessage());
//        }

//        goalList.setAll(goals);

        // example for display test UI
        goalList.setAll(
                new FinancialGoal("test1",1,50000,LocalDate.of(2004,1,5),LocalDate.of(2004,2,6),new Balance("balance1","yes",500)),
                new FinancialGoal("test2",1,6000,LocalDate.of(2006,5,16),LocalDate.of(2009,3,5),new Balance("balancego","yesss",300)));

        // add data to suitable columns
        //statusColumn.setCellValueFactory(new PropertyValueFactory<FinancialGoal,String>("status"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<FinancialGoal, String>("description"));
        //daysColumn.setCellValueFactory(new PropertyValueFactory<FinancialGoal,Integer>("days"));

        goalTable.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(FinancialGoal goalItem, boolean empty) {
                super.updateItem(goalItem, empty);

                if (goalItem == null || empty) {

                } else {
                    statusColumn.setCellFactory(column -> new TableCell<FinancialGoal, String>() {
                        @Override
                        protected void updateItem(String statusItem, boolean empty) {
                            super.updateItem(statusItem, empty);

                            if (goalItem == null || empty) {
                                setText("");
                                //TODO: set case of status
                            } else {
                                setText("90%");
                            }
                        }
                    });
                    daysColumn.setCellFactory(column -> new TableCell<FinancialGoal, Integer>() {
                        @Override
                        protected void updateItem(Integer dayItem, boolean empty) {
                            super.updateItem(dayItem, empty);

                            if (goalItem == null || empty) {
                                setText("");
                                //TODO: set number of days for plan
                            } else {
                                setText("110");
                            }
                        }
                    });
                }
            }
        });

        // bring data to the table
        goalTable.setItems(goalList);
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

    public void deleteBtnClick(ActionEvent actionEvent) {
        FinancialGoal select = goalTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + select.getDescription() + " ?",ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                //TODO: process to remove there
//                try{
//                    ProcessTransactionScene.deleteTransaction(select);
//                }
//                catch (ProcessExeption pe){
//                    pe.getErrorCodeMessage();
//                }
                goalList.remove(select); // delete call
            }
        } else {
            // Nothing select
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setTitle("No Selection");
            alertWarning.setHeaderText("No data selected");
            alertWarning.setContentText("Please select a row in the table to delete");
            alertWarning.showAndWait();
        }
    }

    public void editBtnClick(ActionEvent e) throws Exception {
        FinancialGoal select = goalTable.getSelectionModel().getSelectedItem(); // select an item
        // really need a check type condition here to determine the select is income or expense by using instanceof
        // or getClass() to return the runtime of transaction object
        // ex: if(select instanceof Income) { ... } else we know type income or expense to serve purpose in edit form
        if (select != null) {
            // get edit plan scene
            System.out.println("Edit clicked");
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

            //TODO: generate id from process (arraylist)
            editPlanBox editPlan_box = new editPlanBox();

            // set value of dialog
            editPlan_box.getController().setPlan(select);

            // dialog show
            Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
            dialogEditStage.setTitle("Edit Transaction");
            dialogEditStage.initModality(Modality.WINDOW_MODAL);
            dialogEditStage.initOwner(stage); // close this dialog to return to owner window
            dialogEditStage.setScene(editPlan_box.getScene());

            dialogEditStage.showAndWait();
            System.out.println("go back");
            // refresh data
            displayTableView();
            System.out.println(goalTable.getItems().get(0).getDescription());



        } else {
            // Nothing select
            System.out.println("no selection");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("No Selection");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("No data selected");
            alertWarning.setContentText("Please select a row in the table to edit");
            alertWarning.showAndWait();
        }
    }
}
