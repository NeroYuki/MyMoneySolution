package controller;

import exception.DatabaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Expense;
import model.Income;
import model.Transaction;
import process.ProcessCategories;
import scenes.*;

import java.net.URL;
import java.util.*;

public class statisticsSceneController implements Initializable {
    @FXML
    public ImageView addTransBtn;
    @FXML
    public ImageView planBtn;

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

        // display bar chart in tab income and expense
        displayBarChart();

    }



    @FXML
    public BarChart<String,Double> incomeCategoriesBarChart;
    public BarChart<String,Double> expenseCategoriesBarChart;
    public CategoryAxis incomeCategoriesAxis;
    public CategoryAxis expenseCategoriesAxis;

    private ObservableList<String> incomeCategoryNames = FXCollections.observableArrayList();
    private ObservableList<String> expenseCategoryNames = FXCollections.observableArrayList();

    // init a list of transaction to get from database input
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList(); // list of transaction

    public void displayBarChart() {
        // Get an array with the categories income
//        String[] incomeCategories = {"Salary","Bonus","Award"};
        try {
            String[] incomeCategories = ProcessCategories.getIncomeCategoriesName();
            //TODO: get right list from own database
//        String[] expenseCategories = {"Shopping","Food and Beverage","Movie and show"};
            String[] expenseCategories = ProcessCategories.getExpenseCategoriesName();
            //TODO: get right list from own database

            // Convert it to a list and add it to our ObservableList of categories
            incomeCategoryNames.addAll(Arrays.asList(incomeCategories));
            expenseCategoryNames.addAll(Arrays.asList(expenseCategories));

            // Assign the categories names as categories for the horizontal axis.
            incomeCategoriesAxis.setCategories(incomeCategoryNames);
            expenseCategoriesAxis.setCategories(expenseCategoryNames);

            //TODO: get right list from own database
//        transactionList.add(
//                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
//
//        transactionList.add(
//                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
//        transactionList.add(new Expense(LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
//        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
//        transactionList.add(new Expense(LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));
            //           transactionList.addAll(ProcessTransactionScene.getTransactionsInfo());
            // Count the total value of a categories in an account
            Double[] incomeValueTotal = new Double[incomeCategories.length];
            Double[] expenseValueTotal = new Double[expenseCategories.length];
            //TODO: apply the number of categories from database

            // set initial value of 0 to calculate total then
            for (int i = 0; i < incomeValueTotal.length; i++) {
                incomeValueTotal[i] = (double) 0;
            }
            for (int i = 0; i < expenseValueTotal.length; i++) {
                expenseValueTotal[i] = (double) 0;
            }

            // calculate there
            for (Transaction t : transactionList) {
                String incomeSpecifiedCategory = "";
                String expenseSpecifiedCategory = "";
                Double value = Math.abs(t.getTransValue());
                // check if it is equal to Income
                if (t.getClass().getName().equals("model.Income")) {
                    incomeSpecifiedCategory = ((Income) t).getCategoryName();
                } else if (t.getClass().getName().equals("model.Expense")) {
                    expenseSpecifiedCategory = ((Expense) t).getCategoryName();
                }
                System.out.println(t.getClass().getName());
                for (int i = 0; i < incomeCategories.length; i++) {
                    if (incomeSpecifiedCategory.equals(incomeCategories[i])) {
                        incomeValueTotal[i] += value;
                        System.out.println("income categories " + incomeCategories[i] + incomeValueTotal[i]);
                    } else if (expenseSpecifiedCategory.equals(expenseCategories[i])) {
                        expenseValueTotal[i] += value;
                        System.out.println("expense categories " + expenseCategories[i] + expenseValueTotal[i]);
                    }
                }
            }

            XYChart.Series<String, Double> incomeCategorySeries = new XYChart.Series<>();
            XYChart.Series<String, Double> expenseCategorySeries = new XYChart.Series<>();

            // Create a XYChart.Data object for every category, then add it to the series.
            for (int i = 0; i < incomeValueTotal.length; i++) {
                incomeCategorySeries.getData().add(new XYChart.Data<>(incomeCategoryNames.get(i), incomeValueTotal[i]));
            }
            for (int i = 0; i < expenseValueTotal.length; i++) {
                expenseCategorySeries.getData().add(new XYChart.Data<>(expenseCategoryNames.get(i), expenseValueTotal[i]));
            }

            incomeCategoriesBarChart.getData().add(incomeCategorySeries);
            expenseCategoriesBarChart.getData().add(expenseCategorySeries);
        }
        catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
        }
//        catch (ProcessExeption pe){
//            System.out.println(pe.getErrorCodeMessage());
//        }
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

}
