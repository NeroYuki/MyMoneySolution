package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scenes.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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

        // display line chart of balance all time
        displayBalanceChart();

        // display line chart in tab income and expense
        displayIncomeChart();
        displayExpenseChart();

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
    LineChart<String,Number> incomeLineChart;
    @FXML
    LineChart<String,Number> expenseLineChart;
    public void displayIncomeChart() {
        // first data
        XYChart.Series<String, Number> seriesA = new XYChart.Series<>();
        XYChart.Data<String, Number> jan1 = new XYChart.Data<>("Jan", 300);
        XYChart.Data<String, Number> feb1 = new XYChart.Data<>("Feb", 500);
        XYChart.Data<String, Number> mar1 = new XYChart.Data<>("Mar", 600);
        seriesA.getData().addAll(jan1, feb1, mar1);
        seriesA.setName("Income item a");
        // next data
        XYChart.Series<String, Number> seriesB = new XYChart.Series<>();
        XYChart.Data<String, Number> jan2 = new XYChart.Data<>("Jan", 400);
        XYChart.Data<String, Number> feb2 = new XYChart.Data<>("Feb", 200);
        XYChart.Data<String, Number> mar2 = new XYChart.Data<>("Mar", 950);
        seriesB.getData().addAll(jan2, feb2, mar2);
        seriesB.setName("Income item b");
        incomeLineChart.getData().addAll(seriesA, seriesB);
    }

    public void displayExpenseChart() {
        // first data
        XYChart.Series<String, Number> seriesA = new XYChart.Series<>();
        XYChart.Data<String, Number> jan1 = new XYChart.Data<>("Jan", 300);
        XYChart.Data<String, Number> feb1 = new XYChart.Data<>("Feb", 500);
        XYChart.Data<String, Number> mar1 = new XYChart.Data<>("Mar", 600);
        seriesA.getData().addAll(jan1, feb1, mar1);
        seriesA.setName("Expense item a");
        // next data
        XYChart.Series<String, Number> seriesB = new XYChart.Series<>();
        XYChart.Data<String, Number> jan2 = new XYChart.Data<>("Jan", 400);
        XYChart.Data<String, Number> feb2 = new XYChart.Data<>("Feb", 200);
        XYChart.Data<String, Number> mar2 = new XYChart.Data<>("Mar", 950);
        seriesB.getData().addAll(jan2, feb2, mar2);
        seriesB.setName("Expense item b");
        expenseLineChart.getData().addAll(seriesA, seriesB);
    }

    @FXML
    LineChart<String,Number> balanceLineChart;
    public void displayBalanceChart() {
        // first data
        XYChart.Series<String, Number> seriesA = new XYChart.Series<>();
        XYChart.Data<String,Number> jan1 = new XYChart.Data<>("Jan",300);
        XYChart.Data<String,Number> feb1 = new XYChart.Data<>("Feb",500);
        XYChart.Data<String,Number> mar1 = new XYChart.Data<>("Mar",600);
        seriesA.getData().addAll(jan1,feb1,mar1);
        seriesA.setName("Balance item a");
        // next data
        XYChart.Series<String, Number> seriesB = new XYChart.Series<>();
        XYChart.Data<String,Number> jan2 = new XYChart.Data<>("Jan",400);
        XYChart.Data<String,Number> feb2 = new XYChart.Data<>("Feb",200);
        XYChart.Data<String,Number> mar2 = new XYChart.Data<>("Mar",950);
        seriesB.getData().addAll(jan2,feb2,mar2);
        seriesB.setName("Balance item b");
        balanceLineChart.getData().addAll(seriesA,seriesB);
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
