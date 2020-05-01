package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Expense;
import model.Income;
import model.Transaction;
import scenes.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class categoriesSceneController implements Initializable {
    // init a list of transaction to get from database input
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList(); // list of transaction

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

    public void homeBtnClick(ActionEvent e) throws Exception {
        System.out.println("Home clicked");
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        homepageScene homepage_scene = new homepageScene();
        stage.setScene(homepage_scene.getScene());
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
        // display bar chart in tab income and expense
        displayBarChart();

        // load income categories
        incomeCategoriesLoad();

        // load xpense categories
        expenseCategoriesLoad();

    }

    @FXML
    public BarChart<String,Double> incomeCategoriesBarChart;
    public BarChart<String,Double> expenseCategoriesBarChart;
    public CategoryAxis incomeCategoriesAxis;
    public CategoryAxis expenseCategoriesAxis;

    private ObservableList<String> incomeCategoryNames = FXCollections.observableArrayList();
    private ObservableList<String> expenseCategoryNames = FXCollections.observableArrayList();

    public void displayBarChart() {
        // Get an array with the categories income
        String[] incomeCategories = {"Salary","Bonus","Award"};
        //TODO: get right list from own database
        String[] expenseCategories = {"Shopping","Food and Beverage","Movie and show"};
        //TODO: get right list from own database

        // Convert it to a list and add it to our ObservableList of categories
        incomeCategoryNames.addAll(Arrays.asList(incomeCategories));
        expenseCategoryNames.addAll(Arrays.asList(expenseCategories));

        // Assign the categories names as categories for the horizontal axis.
        incomeCategoriesAxis.setCategories(incomeCategoryNames);
        expenseCategoriesAxis.setCategories(expenseCategoryNames);

        //TODO: get right list from own database
        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));

        transactionList.add(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"));
        transactionList.add(new Expense(LocalDate.of(2004,4,15),-20000, "Buy a phone in FPT", "Shopping"));
        transactionList.add(new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"));
        transactionList.add(new Expense(LocalDate.of(2004,11,23),-35000, "Restaurant district 5", "Food and Beverage"));

        // Count the total value of a categories in an account
        Double[] incomeValueTotal = new Double[3];
        Double[] expenseValueTotal = new Double[3];
        //TODO: apply the number of categories from database

        // set initial value of 0 to calculate total then
        for(int i=0;i<incomeValueTotal.length;i++){
            incomeValueTotal[i]= (double) 0;
        }
        for(int i=0;i<expenseValueTotal.length;i++){
            expenseValueTotal[i]= (double) 0;
        }

        // calculate there
        for (Transaction t : transactionList) {
            String incomeSpecifiedCategory = "";
            String expenseSpecifiedCategory = "";
            Double value = Math.abs(t.getTransValue());
            // check if it is equal to Income
            if(t.getClass().getName().equals("model.Income")){
                incomeSpecifiedCategory = ((Income) t).getCategoryName();
            }
            else if(t.getClass().getName().equals("model.Expense")){
                expenseSpecifiedCategory = ((Expense) t).getCategoryName();
            }
            System.out.println(t.getClass().getName());
            for(int i=0;i<incomeCategories.length;i++){
                if(incomeSpecifiedCategory.equals(incomeCategories[i])){
                    incomeValueTotal[i]+=value;
                    System.out.println("income categories " + incomeCategories[i] + incomeValueTotal[i]);
                }
                else if(expenseSpecifiedCategory.equals(expenseCategories[i])){
                    expenseValueTotal[i]+=value;
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

    // load categories for income and expense list view
    @FXML
    VBox listIncomeCategoryBox;
    @FXML
    VBox listExpenseCategoryBox;

    public void incomeCategoriesLoad() {
        //TODO: get right list from database
        File folder = new File("src/main/resources/img/icon/income");
        System.out.println("path income: "+folder.getAbsolutePath());
        File[] files = folder.listFiles();
        System.out.println(files.length);
        Image[] listOfImages = new Image[files.length];
        String[] fileName = new String[files.length];
        for(int i=0;i<files.length;i++){
            File file = files[i];
            //TODO: get right list from database
            listOfImages[i] = new Image("/img/icon/income/"+file.getName());
            fileName[i] = file.getName();
        }


        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                fileName);
        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    for(int i=0;i<listOfImages.length;i++){
                        if(name.equals(fileName[i])){
                            imageView.setImage(listOfImages[i]);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                        }
                    }
                    //TODO: get right name from database
                    setText(name.toUpperCase().replace(".PNG","")); // set name display of item
                    setGraphic(imageView);
                }
            }
        });
        listIncomeCategoryBox.getChildren().add(listView);
        //listIncomeCategoryBox.setAlignment(Pos.CENTER);
    }

    public void expenseCategoriesLoad() {
        File folder = new File("src/main/resources/img/icon/expense");
        System.out.println("path expense: "+folder.getAbsolutePath());
        File[] files = folder.listFiles();
        System.out.println(files.length);
        Image[] listOfImages = new Image[files.length];
        String[] fileName = new String[files.length];
        for(int i=0;i<files.length;i++){
            File file = files[i];
            //TODO: get right list from database
            listOfImages[i] = new Image("/img/icon/expense/"+file.getName());
            fileName[i] = file.getName();
        }


        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                fileName);
        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    for(int i=0;i<listOfImages.length;i++){
                        if(name.equals(fileName[i])){
                            imageView.setImage(listOfImages[i]);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                        }
                    }
                    //TODO: get right name from database
                    setText(name.toUpperCase().replace(".PNG","")); // set name display of item
                    setGraphic(imageView);
                }
            }
        });
        listExpenseCategoryBox.getChildren().add(listView);
    }

    public void editExpenseCategoriesBtnClick(ActionEvent actionEvent) {
    }

    public void deleteExpenseCategoriesBtnClick(ActionEvent actionEvent) {
    }

    public void addExpenseCategoriesBtnClick(ActionEvent e) throws Exception {
        // get add income scene
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addCategoriesBox addExpenseCategories_box = new addCategoriesBox();
        System.out.println("Add expense categories click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add expense categories");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addExpenseCategories_box.getScene());

        dialogAddStage.showAndWait();
    }

    public void editIncomeCategoriesBtnClick(ActionEvent actionEvent) {
    }

    public void deleteIncomeCategoriesBtnClick(ActionEvent actionEvent) {
    }

    public void addIncomeCategoriesBtnClick(ActionEvent actionEvent) {
    }


}
