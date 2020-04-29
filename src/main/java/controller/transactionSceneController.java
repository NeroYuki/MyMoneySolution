package controller;

import helper.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Expense;
import model.Income;
import model.Transaction;
import scenes.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class transactionSceneController implements Initializable {
    @FXML
    public ImageView addTransBtn;
    public ImageView memoBtn;
    public TableView<Transaction> transactionTable;
    public TableColumn<Transaction, LocalDate> dateColumn;
    public TableColumn<Transaction, String> descriptionColumn;
    public TableColumn<Transaction, String> accountColumn; // yet to be used because not in constructor of income or saving??
    public TableColumn<Transaction, String> categoryColumn;
    public TableColumn<Transaction, Double> valueColumn;

    public TextField filterText;
    @FXML
    public Button viewBtn;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList(); // list of transaction (default by date)

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Home clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
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
        // tooltip handle for add item and memo buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(memoBtn, new Tooltip("Memo record"));


        // table view handle
        // data initialization, we will use own database later
        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"));

        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"));

        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"));

        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"));

        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"));

        // add data to suitable columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transDescription"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryName"));
        //typeColumn not set because there is no property in transaction
        valueColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("transValue"));
        transactionTable.setItems(transactionList);


        // filter data when search table
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Transaction> filteredData = new FilteredList<>(transactionList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transaction -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare description and value of transaction data with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (transaction.getTransDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the description
                } else if (transaction.getTransValue().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches value
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(transactionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        transactionTable.setItems(sortedData);


    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    public void deleteBtnClick(ActionEvent e) {
        Transaction select = transactionTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + select.getTransDescription() +" on " + DateUtil.format(select.getTransDate()) + " ?",ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                transactionList.remove(select); // delete call
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

    @FXML
    public void editBtnClick(ActionEvent e) throws Exception {
        Transaction select = transactionTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // get edit transaction scene
            System.out.println("Edit clicked");
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

            editTransactionBox editTransaction_box = new editTransactionBox();

            // dialog show
            Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
            dialogEditStage.setTitle("Edit Transaction");
            dialogEditStage.initModality(Modality.WINDOW_MODAL);
            dialogEditStage.initOwner(stage); // close this dialog to return to owner window
            dialogEditStage.setScene(editTransaction_box.getScene());
            dialogEditStage.showAndWait();

            //stage.setScene(editTransaction_box.getScene());
        } else {
            // Nothing select
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("No Selection");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("No data selected");
            alertWarning.setContentText("Please select a row in the table to edit");
            alertWarning.showAndWait();
        }

    }
}
