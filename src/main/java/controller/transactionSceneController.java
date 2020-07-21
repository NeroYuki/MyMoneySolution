package controller;

import exception.ProcessExeption;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Balance;
import model.Expense;
import model.Income;
import model.Transaction;
import process.ProcessLoan;
import process.ProcessTransaction;
import scenes.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class transactionSceneController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML
    public ImageView addTransBtn;
    @FXML
    public ImageView planBtn;
    public TableView<Transaction> transactionWeekTable;
    public TableColumn<Transaction, String> idWeekColumn;
    public TableColumn<Transaction, LocalDate> dateWeekColumn;
    public TableColumn<Transaction, String> descriptionWeekColumn;
    public TableColumn<Transaction, Balance> accountWeekColumn; // yet to be used because not in constructor of income or saving??
    public TableColumn<Transaction, String> categoryWeekColumn;
    public TableColumn<Transaction, Double> valueWeekColumn;

    public TextField filterWeekText;
    @FXML
    public Button viewBtn;
    @FXML
    public CheckBox incomeWeekCbox;
    @FXML
    public CheckBox expenseWeekCbox;
    @FXML
    public Label incomeWeekTotalLabel;
    @FXML
    public Label expenseWeekTotalLabel;

    private ObservableList<Transaction> transactionWeekList = FXCollections.observableArrayList(); // list of transaction (default by date)


    public void incomeWeekCheckBox(ActionEvent e)throws Exception{
        displapWeakView();
        filterData();
    }
    public void expenseWeekCheckBox(ActionEvent e)throws Exception{
        displapWeakView();
        filterData();
    }

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
        // tooltip handle for add item and plan buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(planBtn, new Tooltip("Add financial goal"));

        // load data to table
        displayTableView();


    }

    public void displayTableView(){
        displapWeakView();
        displayTableViewMonthly();
        displayTableViewCustomly();
        // filter data when search table
        filterData();
    }

    public void displapWeakView() {
        // table view handle
        //TODO: get right list from own database
        ArrayList<Transaction> transactions=new ArrayList<>();
        try{
            if(incomeWeekCbox.isSelected()==true) {
                ArrayList<Income> incomes=ProcessTransaction.getAllIncome(LocalDate.now().minusDays(6), LocalDate.now().plusDays(1));
                transactions.addAll(incomes);
                double sum= 0;
                for(Transaction transaction:incomes) {
                    sum+=transaction.getTransValue();
                }
                incomeWeekTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                incomeWeekTotalLabel.setText("0");
            }

            if(expenseWeekCbox.isSelected()==true) {
                ArrayList<Expense> expenses=ProcessTransaction.getAllExpense(LocalDate.now().minusDays(6), LocalDate.now().plusDays(1));
                transactions.addAll(expenses);
                double sum= 0;
                for(Transaction expense:expenses) {
                    sum+=expense.getTransValue();
                }
                expenseWeekTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                expenseWeekTotalLabel.setText("0");
            }
        }
        catch (ProcessExeption pe)
        {
            pe.printStackTrace();
        }

        transactionWeekList.setAll(transactions);
//        transactionList.add(
//                new Income(UUIDHelper.newUUIDString(),LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
//
//        transactionList.add(
//                new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
//
//        transactionList.add(
//                new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
//
//        transactionList.add(
//                new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
//
//        transactionList.add(
//                new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(UUIDHelper.newUUIDString(), LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(UUIDHelper.newUUIDString(), LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));

        // add data to suitable columns
        //idWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("id"));
        dateWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transDate"));
        descriptionWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transDescription"));
        categoryWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryName"));
        //typeColumn not set because there is no property in transaction
        //TODO: should add the account column here but no property now to use
        accountWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Balance>("applyingBalance"));
        valueWeekColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("transValue"));

        transactionWeekTable.setRowFactory(table -> new TableRow<>(){
            @Override
            protected void updateItem(Transaction transItem, boolean empty) {
                super.updateItem(transItem, empty);

                if (transItem == null || empty) {
                    setStyle("");
                } else {
                    valueWeekColumn.setCellFactory(column -> new TableCell<Transaction, Double>() {
                        @Override
                        protected void updateItem(Double valueItem, boolean empty) {
                            super.updateItem(valueItem, empty);

                            if (valueItem == null || empty) {
                                setText("");
                                setStyle("");
                            } else {
                                setText(String.format(Locale.US,"%,.0f", valueItem));
//                                setText(String.format("%,8d",(int)((double)(valueItem))).replace("-",""));
                                if (transItem.getType().equals("Expense")) {
                                    //setTextFill(Color.DARKRED);
                                    setStyle("-fx-background-color: red;\n" +  "-fx-alignment: CENTER;");
                                } else if(transItem.getType().equals("Income")) {
                                    setStyle("-fx-background-color: #009383;\n" +  "-fx-alignment: CENTER;");
                                }
                            }
                        }
                    });

                }
            }
        });

        // bring data to the table
        transactionWeekTable.setItems(transactionWeekList);
    }

    public TableView<Transaction> transactionMonthTable;
    public TableColumn<Transaction, String> idColumn1;
    public TableColumn<Transaction, LocalDate> dateMonthColumn;
    public TableColumn<Transaction, String> descriptionMonthColumn;
    public TableColumn<Transaction, Balance> accountMonthColumn; // yet to be used because not in constructor of income or saving??
    public TableColumn<Transaction, String> categoryMonthColumn;
    public TableColumn<Transaction, Double> valueMonthColumn;

    public TextField filterMonthText;
    @FXML
    public CheckBox incomeMonthCbox;
    @FXML
    public CheckBox expenseMonthCbox;
    @FXML
    public Label incomeMonthTotalLabel;
    @FXML
    public Label expenseMonthTotalLabel;

    private ObservableList<Transaction> transactionMonthList = FXCollections.observableArrayList(); // list of transaction (default by date)


    public void incomeMonthCheckBox(ActionEvent e)throws Exception{
        displayTableViewMonthly();
        filterData();
    }
    public void expenseMonthCheckBox(ActionEvent e)throws Exception{
        displayTableViewMonthly();
        filterData();
    }
    public void displayTableViewMonthly() {
        // table view handle
        //TODO: get right list from own database
        ArrayList<Transaction> transactions=new ArrayList<>();
        try{
            if(incomeMonthCbox.isSelected()==true) {
                ArrayList<Income> incomes=ProcessTransaction.getAllIncome(LocalDate.now().minusDays(30), LocalDate.now().plusDays(1));
                transactions.addAll(incomes);
                double sum= 0;
                for(Transaction transaction:incomes) {
                    sum+=transaction.getTransValue();
                }
                incomeMonthTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                incomeMonthTotalLabel.setText("0");
            }

            if(expenseMonthCbox.isSelected()==true) {
                ArrayList<Expense> expenses=ProcessTransaction.getAllExpense(LocalDate.now().minusDays(30), LocalDate.now().plusDays(1));
                transactions.addAll(expenses);
                double sum= 0;
                for(Transaction expense:expenses) {
                    sum+=expense.getTransValue();
                }
                expenseMonthTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                expenseMonthTotalLabel.setText("0");
            }
        }
        catch (ProcessExeption pe)
        {
            pe.printStackTrace();

        }

        transactionMonthList.setAll(transactions);
//
        // add data to suitable columns
        //idMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("id"));
        dateMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transDate"));
        descriptionMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transDescription"));
        categoryMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryName"));
        //typeColumn not set because there is no property in transaction
        //TODO: should add the account column here but no property now to use
        accountMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Balance>("applyingBalance"));
        valueMonthColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("transValue"));

        transactionMonthTable.setRowFactory(table -> new TableRow<>(){
            @Override
            protected void updateItem(Transaction transItem, boolean empty) {
                super.updateItem(transItem, empty);

                if (transItem == null || empty) {
                    setStyle("");
                } else {
                    valueMonthColumn.setCellFactory(column -> new TableCell<Transaction, Double>() {
                        @Override
                        protected void updateItem(Double valueItem, boolean empty) {
                            super.updateItem(valueItem, empty);

                            if (valueItem == null || empty) {
                                setText("");
                                setStyle("");
                            } else {
                                setText(String.format(Locale.US,"%,.0f", valueItem));
//                                setText(String.format("%,8d",(int)((double)(valueItem))).replace("-",""));
                                if (transItem.getType().equals("Expense")) {
                                    //setTextFill(Color.DARKRED);
                                    setStyle("-fx-background-color: red;\n" +  "-fx-alignment: CENTER;");
                                } else if(transItem.getType().equals("Income")) {
                                    setStyle("-fx-background-color: #009383;\n" +  "-fx-alignment: CENTER;");
                                }
                            }
                        }
                    });

                }
            }
        });

        // bring data to the table
        transactionMonthTable.setItems(transactionMonthList);
    }

    public TableView<Transaction> transactionCustomTable;
    public TableColumn<Transaction, String> idColumn11;
    public TableColumn<Transaction, LocalDate> dateCustomColumn;
    public TableColumn<Transaction, String> descriptionCustomColumn;
    public TableColumn<Transaction, Balance> accountCustomColumn; // yet to be used because not in constructor of income or saving??
    public TableColumn<Transaction, String> categoryCustomColumn;
    public TableColumn<Transaction, Double> valueCustomColumn;

    public TextField filterCustomText;
    @FXML
    public CheckBox incomeCustomCbox;
    @FXML
    public CheckBox expenseCustomCbox;
    @FXML
    public Label incomeCustomTotalLabel;
    @FXML
    public Label expenseCustomTotalLabel;
    @FXML
    public DatePicker startdate;
    @FXML
    public DatePicker finishdate;


    private ObservableList<Transaction> transactionCustomList = FXCollections.observableArrayList(); // list of transaction (default by date)


    public void incomeCustomWeekCheckbox(ActionEvent e)throws Exception{
        displayTableViewCustomly();
        filterData();
    }
    public void expenseCustomCheckBox(ActionEvent e)throws Exception{
        displayTableViewCustomly();
        filterData();
    }
    public void startDateChange(ActionEvent e)throws Exception{
        displayTableViewCustomly();
        filterData();
    }
    public void finishDateChange(ActionEvent e)throws Exception{
        displayTableViewCustomly();
        filterData();

    }
    public void displayTableViewCustomly() {
        // table view handle
        //TODO: get right list from own database
        ArrayList<Transaction> transactions=new ArrayList<>();
        try{
            LocalDate temDate=LocalDate.of(1970,1,2);
            LocalDate start=LocalDate.now();
            LocalDate end=start;
            if(startdate.getValue()==null){
                start=temDate;
            }
            else {
                start=startdate.getValue();
            }
            if(finishdate.getValue()==null){
                end=LocalDate.now();
            }
            else {
                end=finishdate.getValue().plusDays(1);
            }


            if(incomeCustomCbox.isSelected()==true) {
                ArrayList<Income> incomes=ProcessTransaction.getAllIncome(start, end);
                transactions.addAll(incomes);
                double sum= 0;
                for(Transaction transaction:incomes) {
                    sum+=transaction.getTransValue();
                }
                incomeCustomTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                incomeCustomTotalLabel.setText("0");
            }

            if(expenseCustomCbox.isSelected()==true) {
                ArrayList<Expense> expenses=ProcessTransaction.getAllExpense(start,end);
                transactions.addAll(expenses);
                double sum= 0;
                for(Transaction expense:expenses) {
                    sum+=expense.getTransValue();
                }
                expenseCustomTotalLabel.setText(String.format(Locale.US,"%,.0f", sum));
            }else {
                expenseCustomTotalLabel.setText("0");
            }
        }
        catch (ProcessExeption pe)
        {
            pe.printStackTrace();

        }

        transactionCustomList.setAll(transactions);
//
        // add data to suitable columns
        //idCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("id"));
        dateCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transDate"));
        descriptionCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transDescription"));
        categoryCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryName"));
        //typeColumn not set because there is no property in transaction
        //TODO: should add the account column here but no property now to use
        accountCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Balance>("applyingBalance"));
        valueCustomColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("transValue"));

        transactionCustomTable.setRowFactory(table -> new TableRow<>(){
            @Override
            protected void updateItem(Transaction transItem, boolean empty) {
                super.updateItem(transItem, empty);

                if (transItem == null || empty) {
                    setStyle("");
                } else {
                    valueCustomColumn.setCellFactory(column -> new TableCell<Transaction, Double>() {
                        @Override
                        protected void updateItem(Double valueItem, boolean empty) {
                            super.updateItem(valueItem, empty);

                            if (valueItem == null || empty) {
                                setText("");
                                setStyle("");
                            } else {
                                setText(String.format(Locale.US,"%,.0f", valueItem));
//                                setText(String.format("%,8d",(int)((double)(valueItem))).replace("-",""));
                                if (transItem.getType().equals("Expense")) {
                                    //setTextFill(Color.DARKRED);
                                    setStyle("-fx-background-color: red;\n" +  "-fx-alignment: CENTER;");
                                } else if(transItem.getType().equals("Income")) {
                                    setStyle("-fx-background-color: #009383;\n" +  "-fx-alignment: CENTER;");
                                }
                            }
                        }
                    });

                }
            }
        });

        // bring data to the table
        transactionCustomTable.setItems(transactionCustomList);
    }

    public void filterData() {
        // use to filter data based on text
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Transaction> filteredWeekData = new FilteredList<>(transactionWeekList, p -> true);
        FilteredList<Transaction> filteredMonthData = new FilteredList<>(transactionMonthList, p -> true);
        FilteredList<Transaction> filteredCustomData = new FilteredList<>(transactionCustomList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterWeekText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredWeekData.setPredicate(transaction -> {
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
        filterMonthText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMonthData.setPredicate(transaction -> {
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
        filterCustomText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomData.setPredicate(transaction -> {
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
        SortedList<Transaction> sortedData = new SortedList<>(filteredWeekData);
        SortedList<Transaction> sortedData1 = new SortedList<>(filteredMonthData);
        SortedList<Transaction> sortedData2 = new SortedList<>(filteredCustomData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(transactionWeekTable.comparatorProperty());
        sortedData1.comparatorProperty().bind(transactionMonthTable.comparatorProperty());
        sortedData2.comparatorProperty().bind(transactionCustomTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        transactionWeekTable.setItems(sortedData);
        transactionMonthTable.setItems(sortedData1);
        transactionCustomTable.setItems(sortedData2);
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    public void deleteWeekBtnClick(ActionEvent e) {
        Transaction select = transactionWeekTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + select.getTransDescription() +" on " + DateUtil.format(select.getTransDate()) + " ?",ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                try{
                    ProcessTransaction.deleteTransaction(select);
                }
                catch (ProcessExeption pe){
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setTitle("Missing something");
                    alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                    alertWarning.setHeaderText("Cannot delete week item");
                    alertWarning.setContentText("Please check carefully");
                    alertWarning.showAndWait();
                    System.out.println(pe.getErrorCodeMessage());
                    return;
                }
                transactionWeekList.remove(select); // delete call
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
    public void editWeekBtnClick(ActionEvent e) throws Exception {
        Transaction select = transactionWeekTable.getSelectionModel().getSelectedItem(); // select an item
        // really need a check type condition here to determine the select is income or expense by using instanceof
        // or getClass() to return the runtime of transaction object
        // ex: if(select instanceof Income) { ... } else we know type income or expense to serve purpose in edit form
        if (select != null) {
            // get edit transaction scene
            System.out.println("Edit clicked");
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

            //TODO: generate id from process (arraylist)
            editTransactionBox editTransaction_box = new editTransactionBox();

            // set value of dialog
            editTransaction_box.getController().setTransaction(select);

            // dialog show
            Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
            dialogEditStage.setTitle("Edit Transaction");
            dialogEditStage.initModality(Modality.WINDOW_MODAL);
            dialogEditStage.initOwner(stage); // close this dialog to return to owner window
            dialogEditStage.setScene(editTransaction_box.getScene());

            dialogEditStage.showAndWait();
            System.out.println("go back");
            // refresh if in the transaction page
            displayTableView();
            // filter data when search table
            filterData();
            System.out.println(transactionWeekTable.getItems().get(0).getTransDescription());



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

    public void addTransClick(MouseEvent e) throws Exception {
        // first appear a dialog to choose the type of transaction for clear handle
        List<String> choices = new ArrayList<>();
        choices.add("Income");
        choices.add("Expenses");

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
                // refresh if in the transaction page
                displayTableView();
                // filter data when search table
                filterData();
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
                // refresh if in the transaction page
                displayTableView();
                displayTableViewCustomly();
                displayTableViewMonthly();
                // filter data when search table
                filterData();
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

    public void editMonthBtnClick(ActionEvent e) throws Exception {
        Transaction select = transactionMonthTable.getSelectionModel().getSelectedItem(); // select an item
        // really need a check type condition here to determine the select is income or expense by using instanceof
        // or getClass() to return the runtime of transaction object
        // ex: if(select instanceof Income) { ... } else we know type income or expense to serve purpose in edit form
        if (select != null) {
            // get edit transaction scene
            System.out.println("Edit clicked");
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

            //TODO: generate id from process (arraylist)
            editTransactionBox editTransaction_box = new editTransactionBox();

            // set value of dialog
            editTransaction_box.getController().setTransaction(select);

            // dialog show
            Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
            dialogEditStage.setTitle("Edit Transaction");
            dialogEditStage.initModality(Modality.WINDOW_MODAL);
            dialogEditStage.initOwner(stage); // close this dialog to return to owner window
            dialogEditStage.setScene(editTransaction_box.getScene());

            dialogEditStage.showAndWait();
            System.out.println("go back");
            // refresh if in the transaction page
            displayTableView();
            // filter data when search table
            filterData();
            System.out.println(transactionCustomTable.getItems().get(0).getTransDescription());



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

    public void deleteMonthBtnClick(ActionEvent actionEvent) {
        Transaction select = transactionMonthTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + select.getTransDescription() +" on " + DateUtil.format(select.getTransDate()) + " ?",ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                try{
                    ProcessTransaction.deleteTransaction(select);
                }
                catch (ProcessExeption pe){
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setTitle("Missing something");
                    alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                    alertWarning.setHeaderText("Cannot delete Month item");
                    alertWarning.setContentText("Please check carefully");
                    alertWarning.showAndWait();
                    System.out.println(pe.getErrorCodeMessage());
                    return;
                }
                transactionMonthList.remove(select); // delete call
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


    public void editCustomBtnClick(ActionEvent e) throws Exception {
        Transaction select = transactionCustomTable.getSelectionModel().getSelectedItem(); // select an item
        // really need a check type condition here to determine the select is income or expense by using instanceof
        // or getClass() to return the runtime of transaction object
        // ex: if(select instanceof Income) { ... } else we know type income or expense to serve purpose in edit form
        if (select != null) {
            // get edit transaction scene
            System.out.println("Edit clicked");
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

            //TODO: generate id from process (arraylist)
            editTransactionBox editTransaction_box = new editTransactionBox();

            // set value of dialog
            editTransaction_box.getController().setTransaction(select);

            // dialog show
            Stage dialogEditStage = new Stage(StageStyle.TRANSPARENT);
            dialogEditStage.setTitle("Edit Transaction");
            dialogEditStage.initModality(Modality.WINDOW_MODAL);
            dialogEditStage.initOwner(stage); // close this dialog to return to owner window
            dialogEditStage.setScene(editTransaction_box.getScene());

            dialogEditStage.showAndWait();
            System.out.println("go back");
            // refresh if in the transaction page
            displayTableView();
            // filter data when search table
            filterData();
            System.out.println(transactionCustomTable.getItems().get(0).getTransDescription());



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

    public void deleteCustomBtnClick(ActionEvent actionEvent) {
        Transaction select = transactionCustomTable.getSelectionModel().getSelectedItem(); // select an item
        if (select != null) {
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + select.getTransDescription() +" on " + DateUtil.format(select.getTransDate()) + " ?",ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                try{
                    ProcessTransaction.deleteTransaction(select);
                }
                catch (ProcessExeption pe){
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setTitle("Missing something");
                    alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                    alertWarning.setHeaderText("Cannot delete Custom item");
                    alertWarning.setContentText("Please check carefully");
                    alertWarning.showAndWait();
                    System.out.println(pe.getErrorCodeMessage());
                    return;
                }
                transactionCustomList.remove(select); // delete call
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
}
