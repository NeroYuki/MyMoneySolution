package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
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
    public TableColumn<Transaction, String> typeColumn;
    public TableColumn<Transaction, String> categoryColumn;
    public TableColumn<Transaction, Double> valueColumn;

    private ObservableList<Transaction> transactionList; // list of transaction (default by date)

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
        transactionList = FXCollections.observableArrayList(
                new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"),
                new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"),
                new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"),
                new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"),

        new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"),
                new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"),
                new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"),
                new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"),
        new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"),
                new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"),
                new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"),
                new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"),
        new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"),
                new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"),
                new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"),
                new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage"),
        new Income(LocalDate.of(2004,1,5),50000, "School giving scholarship", "Bonus"),
                new Expense(LocalDate.of(2004,4,15),20000, "Buy a phone in FPT", "Shopping"),
                new Income(LocalDate.of(2004,5,6),120000, "Salary May", "Salary"),
                new Expense(LocalDate.of(2004,11,23),35000, "Restaurant district 5", "Food and Beverage")

        );
        dateColumn.setCellValueFactory(new PropertyValueFactory<Transaction,LocalDate>("transDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transDescription"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("categoryName"));
        //typeColumn not set because there is no property in transaction
        valueColumn.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("transValue"));
        transactionTable.setItems(transactionList);
    }
}
