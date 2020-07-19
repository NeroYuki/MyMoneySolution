package controller;

import exception.ProcessExeption;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import model.Balance;
import org.controlsfx.control.CheckComboBox;
import process.ProcessBalance;
import process.ProcessStaticstics;
import scenes.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class statisticsSceneController implements Initializable {
    @FXML
    public ImageView addTransBtn;
    @FXML
    public ImageView planBtn;
    @FXML
    public CheckComboBox<Balance> balancesCheckComboBox;

    @FXML
    public ComboBox<Balance> IncomeComboBox;
    @FXML
    public ComboBox<Balance> expenseComboBox;
    @FXML
    public DatePicker balanceStart;
    @FXML
    public DatePicker balanceFinish;
    @FXML
    public DatePicker incomeStart;
    @FXML
    public DatePicker incomeFinish;
    @FXML
    public DatePicker expenseStart;
    @FXML
    public DatePicker expenseFinish;
    @FXML
    public Label dateLabel1;
    @FXML
    public Label dateLabel2;
    @FXML
    public Label dateLabel3;





    public void transactionBtnClick(ActionEvent e) throws Exception {
        System.out.println("Transaction clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        transactionScene transaction_scene = new transactionScene();
        stage.setScene(transaction_scene.getScene());
    }

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Home clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
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

        // display line chart of balance all time
        // display line chart in tab income and expense


        setBalanceMenu();
        balancesCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Balance>() {
            @Override
            public void onChanged(Change<? extends Balance> c) {
                    ArrayList<Balance> balances = new ArrayList<>( balancesCheckComboBox.getCheckModel().getCheckedItems());
                    displayBalanceChart(balances);
            }
        });
        IncomeComboBox.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                displayIncomeChart(IncomeComboBox.getSelectionModel().getSelectedItem());
            }
        });
        expenseComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayExpenseChart(expenseComboBox.getSelectionModel().getSelectedItem());
            }
        });
        incomeStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayIncomeChart(IncomeComboBox.getSelectionModel().getSelectedItem());
            }
        });
        incomeFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayIncomeChart(IncomeComboBox.getSelectionModel().getSelectedItem());
            }
        });
        expenseStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayExpenseChart(expenseComboBox.getSelectionModel().getSelectedItem());
            }
        });
        expenseFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayExpenseChart(expenseComboBox.getSelectionModel().getSelectedItem());
            }
        });
        balanceStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Balance> balances = new ArrayList<>( balancesCheckComboBox.getCheckModel().getCheckedItems());
                displayBalanceChart(balances);
            }
        });
        balanceFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Balance> balances = new ArrayList<>( balancesCheckComboBox.getCheckModel().getCheckedItems());
                displayBalanceChart(balances);
            }
        });



//        displayIncomeChart();
//        displayExpenseChart();
    }



////    public BarChart<String,Double> incomeCategoriesBarChart;
////    public BarChart<String,Double> expenseCategoriesBarChart;
//    public CategoryAxis incomeCategoriesAxis;
//    public CategoryAxis expenseCategoriesAxis;
//
//    private ObservableList<String> incomeCategoryNames = FXCollections.observableArrayList();
//    private ObservableList<String> expenseCategoryNames = FXCollections.observableArrayList();
//
//    // init a list of transaction to get from database input
//    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList(); // list of transaction
//
//    public void displayBarChart() {
//        // Get an array with the categories income
////        String[] incomeCategories = {"Salary","Bonus","Award"};
//        try {
//            String[] incomeCategories = ProcessCategories.getIncomeCategoriesName();
//            //TODO: get right list from own database
////        String[] expenseCategories = {"Shopping","Food and Beverage","Movie and show"};
//            String[] expenseCategories = ProcessCategories.getExpenseCategoriesName();
//            //TODO: get right list from own database
//
//            // Convert it to a list and add it to our ObservableList of categories
//            incomeCategoryNames.addAll(Arrays.asList(incomeCategories));
//            expenseCategoryNames.addAll(Arrays.asList(expenseCategories));
//
//            // Assign the categories names as categories for the horizontal axis.
//            incomeCategoriesAxis.setCategories(incomeCategoryNames);
//            expenseCategoriesAxis.setCategories(expenseCategoryNames);
//
//            // Count the total value of a categories in an account
//            Double[] incomeValueTotal = new Double[incomeCategories.length];
//            Double[] expenseValueTotal = new Double[expenseCategories.length];
//            //TODO: apply the number of categories from database
//
//            // set initial value of 0 to calculate total then
//            for (int i = 0; i < incomeValueTotal.length; i++) {
//                incomeValueTotal[i] = (double) 0;
//            }
//            for (int i = 0; i < expenseValueTotal.length; i++) {
//                expenseValueTotal[i] = (double) 0;
//            }
//
//            // calculate there
//            for (Transaction t : transactionList) {
//                String incomeSpecifiedCategory = "";
//                String expenseSpecifiedCategory = "";
//                Double value = Math.abs(t.getTransValue());
//                // check if it is equal to Income
//                if (t.getClass().getName().equals("model.Income")) {
//                    incomeSpecifiedCategory = ((Income) t).getCategoryName();
//                } else if (t.getClass().getName().equals("model.Expense")) {
//                    expenseSpecifiedCategory = ((Expense) t).getCategoryName();
//                }
//                System.out.println(t.getClass().getName());
//                for (int i = 0; i < incomeCategories.length; i++) {
//                    if (incomeSpecifiedCategory.equals(incomeCategories[i])) {
//                        incomeValueTotal[i] += value;
//                        System.out.println("income categories " + incomeCategories[i] + incomeValueTotal[i]);
//                    } else if (expenseSpecifiedCategory.equals(expenseCategories[i])) {
//                        expenseValueTotal[i] += value;
//                        System.out.println("expense categories " + expenseCategories[i] + expenseValueTotal[i]);
//                    }
//                }
//            }
//
//            XYChart.Series<String, Double> incomeCategorySeries = new XYChart.Series<>();
//            XYChart.Series<String, Double> expenseCategorySeries = new XYChart.Series<>();
//
//            // Create a XYChart.Data object for every category, then add it to the series.
//            for (int i = 0; i < incomeValueTotal.length; i++) {
//                incomeCategorySeries.getData().add(new XYChart.Data<>(incomeCategoryNames.get(i), incomeValueTotal[i]));
//            }
//            for (int i = 0; i < expenseValueTotal.length; i++) {
//                expenseCategorySeries.getData().add(new XYChart.Data<>(expenseCategoryNames.get(i), expenseValueTotal[i]));
//            }
//
//            incomeCategoriesBarChart.getData().add(incomeCategorySeries);
//            expenseCategoriesBarChart.getData().add(expenseCategorySeries);
//        }
//        catch (DatabaseException de) {
//            System.out.println(de.getErrorCodeMessage());
//        }
//    }

    @FXML
    BarChart<String,Number> incomeBarChart;
    @FXML
    BarChart<String,Number> expenseBarChart;

    public void displayIncomeChart(Balance balance) {
        if(balance == null )return;
        if(incomeStart.getValue()==null){dateLabel2.setText("");return;}
        if(incomeFinish.getValue()==null){dateLabel2.setText("");return;}
        if(incomeStart.getValue().isAfter(incomeFinish.getValue())){dateLabel2.setText("Start day cannot be after finnish day");}
        if(incomeStart.getValue().isAfter(LocalDate.now())){dateLabel2.setText("Start day cannot be after today");}

        XYChart.Series<String, Number> seriesA = new XYChart.Series<>();
        ProcessStaticstics.ChartModel chartModel= null;
        try {
            chartModel = ProcessStaticstics.getIncomeChart(balance, incomeStart.getValue(), incomeFinish.getValue());
            if(chartModel==null) return;
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(balance.getName());
            for(int i=0;i<chartModel.localDates.size();i++){
                XYChart.Data<String, Number> d = new XYChart.Data<>(chartModel.localDates.get(i).toString(), chartModel.numbers.get(i));
                series.getData().add(d);
            }
            incomeBarChart.getData().setAll(series);
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
    }

    public void displayExpenseChart(Balance balance) {
        if(balance == null )return;
        if(expenseFinish.getValue()==null){dateLabel3.setText("");return;}
        if(expenseStart.getValue()==null){dateLabel3.setText("");return;}
        if(expenseStart.getValue().isAfter(expenseFinish.getValue())){dateLabel3.setText("Start day cannot be after finnish day");}
        if(expenseStart.getValue().isAfter(LocalDate.now())){dateLabel3.setText("Start day cannot be after today");}
        XYChart.Series<String, Number> seriesA = new XYChart.Series<>();
        ProcessStaticstics.ChartModel chartModel= null;
        try {
            chartModel = ProcessStaticstics.getExpenseChart(balance, expenseStart.getValue(), expenseFinish.getValue());
            if(chartModel==null) return;
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(balance.getName());
            for(int i=0;i<chartModel.localDates.size();i++){
                XYChart.Data<String, Number> d = new XYChart.Data<>(chartModel.localDates.get(i).toString(), chartModel.numbers.get(i));
                series.getData().add(d);
            }
                expenseBarChart.getData().setAll(series);
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            pe.printStackTrace();
            return;
        }
    }

    @FXML
    LineChart<String,Number> balanceLineChart;
    public void displayBalanceChart(ArrayList<Balance> balances) {
        if(balances.size() == 0 )return;

        if(balanceFinish.getValue()==null){dateLabel1.setText("");return;}
        if(balanceStart.getValue()==null){dateLabel1.setText("");return;}
        if(balanceStart.getValue().isAfter(balanceFinish.getValue())){dateLabel1.setText("Start day cannot be after finnish day");}
        if(balanceStart.getValue().isAfter(LocalDate.now())){dateLabel1.setText("Start day cannot be after today");}
        ArrayList<XYChart.Series<String,Number>> seriesA=new ArrayList<>();
        try {
            for (Balance balance:balances){
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(balance.getName());
                ProcessStaticstics.ChartModel chartModel = ProcessStaticstics.getTransactionInTime(balance, balanceStart.getValue(), balanceFinish.getValue());
                if(chartModel==null) return;
                for(int i=0;i<chartModel.localDates.size();i++){
                    XYChart.Data<String, Number> d = new XYChart.Data<>(chartModel.localDates.get(i).toString(), chartModel.numbers.get(i));

                    series.getData().add(d);
                }
                seriesA.add(series);
            }
        }
        catch (ProcessExeption processExeption){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data balance is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(processExeption.getErrorCodeMessage());
            processExeption.printStackTrace();
            return;
        }
        balanceLineChart.getData().setAll(seriesA);

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
    public void setBalanceMenu(){
        ArrayList<Balance> balances=new ArrayList<>();
        Balance all= null;
        try {
            balances = ProcessBalance.getBalances();
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Cannot get balance list combo");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;
        }
        final ObservableList<Balance> balancelist = FXCollections.observableArrayList(balances);
        balancesCheckComboBox.getItems().setAll(balancelist);
        balancesCheckComboBox.setConverter(new StringConverter<Balance>() {
            @Override
            public String toString(Balance o) {
                if(o==null) return "";
                return o.getName();
            }
            @Override
            public Balance fromString(String s) {
                return null;
            }
        });

        IncomeComboBox.getItems().setAll(balancelist);
        IncomeComboBox.setConverter(new StringConverter<Balance>() {
            @Override
            public String toString(Balance o) {
                if(o==null) return "";
                return o.getName();
            }
            @Override
            public Balance fromString(String string) {
                return null;
            }
        });
        expenseComboBox.getItems().setAll(balancelist);
        expenseComboBox.setConverter(new StringConverter<Balance>() {
            @Override
            public String toString(Balance o) {
                if(o==null) return "";
                return o.getName();
            }
            @Override
            public Balance fromString(String string) {
                return null;
            }
        });
    }



}
