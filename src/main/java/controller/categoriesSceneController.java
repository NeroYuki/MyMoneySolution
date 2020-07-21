package controller;

import exception.ProcessExeption;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Category;
import process.ProcessCategories;
import scenes.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class categoriesSceneController implements Initializable {
    ListView<Category> incomeListView = new ListView<>();
    ListView<Category> expenseListView = new ListView<>();

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
        // tooltip handle for add item and plan buttons
        Tooltip.install(addTransBtn, new Tooltip("Add new item"));
        Tooltip.install(planBtn, new Tooltip("Add financial goal"));

        // load income categories

        // load expense categories


        // load income and expense
        loadTab();

        //add Listener to filterText
        filterText.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterCategoriesList((String) oldValue, (String) newValue);
            }
        });
    }

    public void loadTab(){
        incomeCategoriesLoad();
        expenseCategoriesLoad();
        incomeWeekLoad();
        expensesWeekLoad();
        incomeMonthLoad();
        expensesMonthLoad();
        incomeDaysLoad();
        expensesDaysLoad();
        incomeYearLoad();
        expensesYearLoad();
        incomeAllLoad();
        expensesAllLoad();
    }
    @FXML
    PieChart incomeWeekPie;
    @FXML
    PieChart expensesWeekPie;
    @FXML
    public Label expensesWeekNote;
    @FXML
    public Label incomeWeekNote;
    @FXML
    public Label incomeWeekTotalLabel;
    @FXML
    public Label expenseWeekTotalLabel;
    @FXML
    public Label incomeWeekSignCompareLabel;
    @FXML
    public Label incomeWeekDifferentCompareLabel;
    @FXML
    public Label expensesWeekSignCompareLabel;
    @FXML
    public Label expensesWeekDifferentCompareLabel;


    public void incomeWeekLoadBtnClick(Event actionEvent) {
        incomeWeekLoad();
    }

    public void expensesWeekLoadBtnClick(Event actionEvent) {
        expensesWeekLoad();
    }

    public void incomeWeekLoad() {
        try{
            ArrayList< ProcessCategories.CatModel > catModels=ProcessCategories.getIncomePineChart(0,7);
            Double sum1=ProcessCategories.getSum(catModels);
            incomeWeekTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            Double sum2=ProcessCategories.getSum(ProcessCategories.getIncomePineChart(7,7));
            if(sum1>sum2) {
                incomeWeekSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                incomeWeekSignCompareLabel.textFillProperty().set(Color.GREEN);
                incomeWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                incomeWeekSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                incomeWeekSignCompareLabel.textFillProperty().set(Color.RED);
                incomeWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                incomeWeekSignCompareLabel.setText("=");
                incomeWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }
            incomeWeekPie.getData().clear();
            incomeWeekPie.getData().addAll(datas);

            for(PieChart.Data data : incomeWeekPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            incomeWeekNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income week is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex){

        }
    }

    public void expensesWeekLoad() {
        try{
            ArrayList<ProcessCategories.CatModel > catModels=ProcessCategories.getExpensePineChart(0,7);
            Double sum1=ProcessCategories.getSum(catModels);
            expenseWeekTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            expensesWeekPie.getData().setAll(datas);

            Double sum2=ProcessCategories.getSum(ProcessCategories.getExpensePineChart(7,7));
            if(sum1>sum2) {
                expensesWeekSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                expensesWeekSignCompareLabel.textFillProperty().set(Color.GREEN);
                expensesWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                expensesWeekSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                expensesWeekSignCompareLabel.textFillProperty().set(Color.RED);
                expensesWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                expensesWeekSignCompareLabel.setText("=");
                expensesWeekDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }

            // display info when click
            for(PieChart.Data data : expensesWeekPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            expensesWeekNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense week is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex) {

        }
    }

    @FXML
    PieChart incomeMonthPie;
    @FXML
    PieChart expensesMonthPie;
    @FXML
    public Label expensesMonthNote;
    @FXML
    public Label incomeMonthNote;
    @FXML
    public Label incomeMonthTotalLabel;
    @FXML
    public Label expenseMonthTotalLabel;
    @FXML
    public Label incomeMonthSignCompareLabel;
    @FXML
    public Label expensesMonthSignCompareLabel;
    @FXML
    public Label incomeMonthDifferentCompareLabel;
    @FXML
    public Label expensesMonthDifferentCompareLabel;
    public void incomeMonthLoad() {
        try{
            ArrayList< ProcessCategories.CatModel > catModels=ProcessCategories.getIncomePineChart(0,30);
            Double sum1=ProcessCategories.getSum(catModels);
            incomeMonthTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            Double sum2=ProcessCategories.getSum(ProcessCategories.getIncomePineChart(30,30));
            if(sum1>sum2) {
                incomeMonthSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                incomeMonthSignCompareLabel.textFillProperty().set(Color.GREEN);
                incomeMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                incomeMonthSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                incomeMonthSignCompareLabel.textFillProperty().set(Color.RED);
                incomeMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                incomeMonthSignCompareLabel.setText("=");
                incomeMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }
            incomeMonthPie.getData().setAll(datas);

            for(PieChart.Data data : incomeMonthPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            incomeMonthNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income month is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex){

        }
    }

    public void expensesMonthLoad() {
        try{
            ArrayList<ProcessCategories.CatModel > catModels=ProcessCategories.getExpensePineChart(0,30);
            Double sum1=ProcessCategories.getSum(catModels);
            expenseMonthTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            expensesMonthPie.getData().setAll(datas);

            Double sum2=ProcessCategories.getSum(ProcessCategories.getExpensePineChart(30,30));
            if(sum1>sum2) {
                expensesMonthSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                expensesMonthSignCompareLabel.textFillProperty().set(Color.GREEN);
                expensesMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                expensesMonthSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                expensesMonthSignCompareLabel.textFillProperty().set(Color.RED);
                expensesMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                expensesMonthSignCompareLabel.setText("=");
                expensesMonthDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }

            // display info when click
            for(PieChart.Data data : expensesMonthPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            expensesMonthNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense month is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex) {

        }
    }
    @FXML
    PieChart incomeDaysPie;
    @FXML
    PieChart expensesDaysPie;
    @FXML
    public Label expensesDaysNote;
    @FXML
    public Label incomeDaysNote;
    @FXML
    public Label incomeDaysTotalLabel;
    @FXML
    public Label expenseDaysTotalLabel;
    @FXML
    public Label incomeDaysSignCompareLabel;
    @FXML
    public Label expensesDaysSignCompareLabel;
    @FXML
    public Label incomeDaysDifferentCompareLabel;
    @FXML
    public Label expensesDaysDifferentCompareLabel;
    public void incomeDaysLoad() {
        try{
            ArrayList< ProcessCategories.CatModel > catModels=ProcessCategories.getIncomePineChart(0,90);
            Double sum1=ProcessCategories.getSum(catModels);
            incomeDaysTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            Double sum2=ProcessCategories.getSum(ProcessCategories.getIncomePineChart(90,90));
            if(sum1>sum2) {
                incomeDaysSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                incomeDaysSignCompareLabel.textFillProperty().set(Color.GREEN);
                incomeDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                incomeDaysSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                incomeDaysSignCompareLabel.textFillProperty().set(Color.RED);
                incomeDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                incomeDaysSignCompareLabel.setText("=");
                incomeDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }
            incomeDaysPie.getData().setAll(datas);

            for(PieChart.Data data : incomeDaysPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            incomeDaysNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income days is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex){

        }
    }

    public void expensesDaysLoad() {
        try{
            ArrayList<ProcessCategories.CatModel > catModels=ProcessCategories.getExpensePineChart(0,90);
            Double sum1=ProcessCategories.getSum(catModels);
            expenseDaysTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            expensesDaysPie.getData().setAll(datas);

            Double sum2=ProcessCategories.getSum(ProcessCategories.getExpensePineChart(90,90));
            if(sum1>sum2) {
                expensesDaysSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                expensesDaysSignCompareLabel.textFillProperty().set(Color.GREEN);
                expensesDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                expensesDaysSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                expensesDaysSignCompareLabel.textFillProperty().set(Color.RED);
                expensesDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                expensesDaysSignCompareLabel.setText("=");
                expensesDaysDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }

            // display info when click
            for(PieChart.Data data : expensesDaysPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            expensesDaysNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense days is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex) {

        }
    }
    @FXML
    PieChart incomeYearPie;
    @FXML
    PieChart expensesYearPie;
    @FXML
    public Label expensesYearNote;
    @FXML
    public Label incomeYearNote;
    @FXML
    public Label incomeYearTotalLabel;
    @FXML
    public Label expenseYearTotalLabel;
    @FXML
    public Label incomeYearSignCompareLabel;
    @FXML
    public Label expensesYearSignCompareLabel;
    @FXML
    public Label incomeYearDifferentCompareLabel;
    @FXML
    public Label expensesYearDifferentCompareLabel;

    public void incomeYearLoad() {
        try{
            ArrayList< ProcessCategories.CatModel > catModels=ProcessCategories.getIncomePineChart(0,365);
            Double sum1=ProcessCategories.getSum(catModels);
            incomeYearTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            Double sum2=ProcessCategories.getSum(ProcessCategories.getIncomePineChart(365,365));
            if(sum1>sum2) {
                incomeYearSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                incomeYearSignCompareLabel.textFillProperty().set(Color.GREEN);
                incomeYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                incomeYearSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                incomeYearSignCompareLabel.textFillProperty().set(Color.RED);
                incomeYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                incomeYearSignCompareLabel.setText("=");
                incomeYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }
            incomeYearPie.getData().setAll(datas);

            for(PieChart.Data data : incomeYearPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            incomeYearNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income year is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex){

        }
    }

    public void expensesYearLoad() {
        try{
            ArrayList<ProcessCategories.CatModel > catModels=ProcessCategories.getExpensePineChart(0,365);
            Double sum1=ProcessCategories.getSum(catModels);
            expenseYearTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            expensesYearPie.getData().setAll(datas);

            Double sum2=ProcessCategories.getSum(ProcessCategories.getExpensePineChart(365,365));
            if(sum1>sum2) {
                expensesYearSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↑"));
                expensesYearSignCompareLabel.textFillProperty().set(Color.GREEN);
                expensesYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum1-sum2));
            }
            else if(sum2>sum1)
            {
                expensesYearSignCompareLabel.setText(helper.CharacterEncoding.NativeEncodingtoUtf8("↓"));
                expensesYearSignCompareLabel.textFillProperty().set(Color.RED);
                expensesYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2-sum1));
            }
            else if (sum1==sum2) {
                expensesYearSignCompareLabel.setText("=");
                expensesYearDifferentCompareLabel.setText(String.format(Locale.US,"%,.0f",sum2 - sum1));
            }

            // display info when click
            for(PieChart.Data data : expensesYearPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            expensesYearNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense year is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex) {

        }
    }

    @FXML
    PieChart incomeAllPie;
    @FXML
    PieChart expensesAllPie;
    @FXML
    public Label expensesAlltimeNote;
    @FXML
    public Label incomeAlltimeNote;
    @FXML
    public Label incomeTotalLabel;
    @FXML
    public Label expenseTotalLabel4;
    public void incomeAllLoad() {
        try{
            ArrayList< ProcessCategories.CatModel > catModels=ProcessCategories.getIncomePineChart();
            Double sum1=ProcessCategories.getSum(catModels);
            incomeTotalLabel.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            incomeAllPie.getData().setAll(datas);

            for(PieChart.Data data : incomeAllPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            incomeAlltimeNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income all is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void expensesAllLoad() {
        try{
            ArrayList<ProcessCategories.CatModel > catModels=ProcessCategories.getExpensePineChart();
            Double sum1=ProcessCategories.getSum(catModels);
            expenseTotalLabel4.setText(String.format(Locale.US,"%,.0f", sum1));
            ArrayList<PieChart.Data> datas=new ArrayList<>();
            for (ProcessCategories.CatModel catModel:catModels) {
                if(catModel.value!=0) {
                    PieChart.Data data = new PieChart.Data(catModel.key, catModel.value);
                    datas.add(data);
                }
            }
            expensesAllPie.getData().setAll(datas);


            // display info when click
            for(PieChart.Data data : expensesAllPie.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Data");
                    alert.setContentText(data.getName()+" : "+data.getPieValue()); // customize info
                    alert.showAndWait();
                });
            }
            expensesAlltimeNote.setVisible(false);
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense all is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            pe.printStackTrace();
            return;
        }
        catch(Exception ex) {

        }
    }


    // load categories for income and expense list view
    @FXML
    VBox listIncomeCategoryBox;
    @FXML
    VBox listExpenseCategoryBox;

    @FXML
    Label incomeGeneralTotalLabel;
    @FXML
    Label expenseGeneralTotalLabel;
    public void incomeCategoriesLoad() {
        //TODO: get right list from database
        File folder = new File("src/main/resources/img/icon/income");
        System.out.println("path income: "+folder.getAbsolutePath());
        File[] files = folder.listFiles();
        //System.out.println(files.length);
        Image[] listOfImages = new Image[files.length];
        String[] fileName = new String[files.length];


        // init array of original categories income
        ArrayList<Category> categories = new ArrayList<Category>();

        try{
            categories =ProcessCategories.getIncomeCategories();
            incomeGeneralTotalLabel.setText(categories.size() + " categories");
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for income is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;
        }
//        for(int i=0;i<files.length;i++){
//            File file = files[i];
//            //TODO: get right list from database
//            listOfImages[i] = new Image("/img/icon/income/"+file.getName());
//            fileName[i] = file.getName();
//
//            // init category for income
//            categories.add(new Category(fileName[i].replace(".png",""), "abc", folder.getAbsolutePath()+"\\"+file.getName(),1));
//            System.out.println(categories.get(i).getIconPath());
//        }



        ObservableList<Category> items = FXCollections.observableArrayList(categories);
//        ObservableList<String> items = FXCollections.observableArrayList(fileName);
        incomeListView.setItems(items);
//        ArrayList<Category> finalCategories = categories;
        ArrayList<Category> finalCategories = categories;
        incomeListView.setCellFactory(param -> new ListCell<Category>() {
            //test
            HBox rowBox = new HBox();
            Label nameLabel = new Label("");
            Pane pane = new Pane();
            Button deleteBtn = new Button("Delete");
            Button editBtn = new Button("Edit");

            private ImageView imageView = new ImageView();

            // initialize block in anonymous class implementation playing role constructor
            {
                // add elements of hbox
                rowBox.getChildren().addAll(imageView, nameLabel, pane, editBtn, deleteBtn);
                HBox.setHgrow(pane, Priority.ALWAYS);
                // style for label
                nameLabel.setTextAlignment(TextAlignment.CENTER);
                nameLabel.setStyle("-fx-font-size: 18");
                nameLabel.setPadding(new Insets(10, 0, 0, 10));
                // style button
                deleteBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                // button delete categories place in every item
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
                            //TODO: delete categories in database there
                            getListView().getItems().remove(getItem());
                            try {
                                ProcessCategories.deleleCategory(finalCategories.get(getIndex()));
                            } catch (ProcessExeption processExeption) {
                                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                                alertWarning.setTitle("Missing something");
                                alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                                alertWarning.setHeaderText("Cannot delete");
                                alertWarning.setContentText("Please check carefully");
                                alertWarning.showAndWait();
                                System.out.println(processExeption.getErrorCodeMessage());
                                return;
                            }
                        }
                    } catch(Exception e){
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setTitle("Missing something");
                        alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertWarning.setHeaderText("Cannot choose item");
                        alertWarning.setContentText("Please check carefully");
                        alertWarning.showAndWait();
                        return;
                    }

                });

                // edit button
                editBtn.setOnAction(event -> {
                    try {
                        // get add income scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        editCategoriesBox editCategories_box = new editCategoriesBox();
                        System.out.println("Edit categories click");

                        // set value of dialog
                        Category select = getListView().getItems().get(getIndex());
                        editCategories_box.getController().setCategory(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Edit categories");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(editCategories_box.getScene());

                        dialogAddStage.showAndWait();
                        // refresh list
                        incomeCategoriesLoad();
                    } catch (Exception e) {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setTitle("Missing something");
                        alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertWarning.setHeaderText("Cannot edit");
                        alertWarning.setContentText("Please check carefully");
                        alertWarning.showAndWait();
                        e.printStackTrace();
                        return;
                    }
                });
            }


            @Override
            public void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    for (int i = 0; i < finalCategories.size(); i++) {
//                            File file = new File(category.getIconPath());
                            Image image = new Image(category.getIconPath(),50,50,false,true);
                            imageView.setImage(image);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                    }

                    //TODO: get right name from database
                    nameLabel.setText(category.getName().toUpperCase().replace(".PNG", "")); // set name display of item
                    setGraphic(rowBox);
                    //setGraphic(imageView);
                }
            }
        });
        listIncomeCategoryBox.getChildren().setAll(incomeListView);
    }

    public void expenseCategoriesLoad() {
        //TODO: get right list from database
        File folder = new File("src/main/resources/img/icon/expense");
        System.out.println("path expense: "+folder.getAbsolutePath());
        File[] files = folder.listFiles();
        //System.out.println(files.length);
        Image[] listOfImages = new Image[files.length];
        String[] fileName = new String[files.length];

        // init array of original categories expense
        ArrayList<Category> categories = new ArrayList<Category>();
        try{
            categories =ProcessCategories.getExpenseCategories();
            expenseGeneralTotalLabel.setText(categories.size() + " categories");
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data for expense is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;
        }
//        for(int i=0;i<files.length;i++){
//            File file = files[i];
//            //TODO: get right list from database
//            listOfImages[i] = new Image("/img/icon/expense/"+file.getName());
//            fileName[i] = file.getName();
//
//            // init category expenses
//            categories.add(new Category(fileName[i].replace(".png",""), "abc", folder.getAbsolutePath()+"\\"+file.getName(),2));
//            System.out.println(categories.get(i).getIconPath());
//        }

        ObservableList<Category> items = FXCollections.observableArrayList(categories);
        expenseListView.setItems(items);

        ArrayList<Category> finalCategories = categories;
        expenseListView.setCellFactory(param -> new ListCell<Category>() {
            //test
            HBox rowBox = new HBox();
            Label nameLabel = new Label("");
            Pane pane = new Pane();
            Button deleteBtn = new Button("Delete");
            Button editBtn = new Button("Edit");

            private ImageView imageView = new ImageView();

            // initialize block in anonymous class implementation playing role constructor
            {
                // add elements of hbox
                rowBox.getChildren().addAll(imageView, nameLabel, pane, editBtn,deleteBtn);
                HBox.setHgrow(pane, Priority.ALWAYS);
                // style for label
                nameLabel.setTextAlignment(TextAlignment.CENTER);
                nameLabel.setStyle("-fx-font-size: 18");
                nameLabel.setPadding(new Insets(10,0,0,10));
                // style button
                deleteBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 15px;\n" + "-fx-background-insets: 10px;");
                // button delete categories place in every item
                deleteBtn.setOnAction(event -> {
                    try{
                        String itemRemove = getListView().getItems().get(getIndex()).getName().replace(".png", "");
                        //confirmation to delete
                        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                                "Delete " + itemRemove + " ?", ButtonType.YES, ButtonType.NO);
                        alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertConfirm.showAndWait();
                        if (alertConfirm.getResult() == ButtonType.YES) {
                            //System.out.println("selectIdx: " + getIndex());
                            //System.out.println("item: " + itemRemove);
                            //TODO: delete categories in database there
                            getListView().getItems().remove(getItem());
                            try {
                                ProcessCategories.deleleCategory(finalCategories.get(getIndex()));
                            } catch (ProcessExeption processExeption) {
                                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                                alertWarning.setTitle("Missing something");
                                alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                                alertWarning.setHeaderText("Cannot delete");
                                alertWarning.setContentText("Please check carefully");
                                alertWarning.showAndWait();
                                System.out.println(processExeption.getErrorCodeMessage());
                                processExeption.printStackTrace();
                                return;
                            }
                        }
                    } catch(Exception e){
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setTitle("Missing something");
                        alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertWarning.setHeaderText("Cannot choose item");
                        alertWarning.setContentText("Please check carefully");
                        alertWarning.showAndWait();
                        e.printStackTrace();
                        return;
                    }

                });
                // edit button
                editBtn.setOnAction(event -> {
                    try {
                        // get add expense scene
                        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // get stage of program, primary stage

                        editCategoriesBox editCategories_box = new editCategoriesBox();
                        //System.out.println("Edit categories click");

                        // set value of dialog
                        Category select = getListView().getItems().get(getIndex());
                        editCategories_box.getController().setCategory(select);

                        // dialog show
                        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
                        dialogAddStage.setTitle("Edit categories");
                        dialogAddStage.initModality(Modality.WINDOW_MODAL);
                        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
                        dialogAddStage.setScene(editCategories_box.getScene());

                        dialogAddStage.showAndWait();
                        // refresh list
                        expenseCategoriesLoad();
                    } catch (Exception e) {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setTitle("Missing something");
                        alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
                        alertWarning.setHeaderText("Cannot edit");
                        alertWarning.setContentText("Please check carefully");
                        alertWarning.showAndWait();
                        e.printStackTrace();
                        return;
                    }
                });
            }
            @Override
            public void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    for (int i = 0; i < finalCategories.size(); i++) {
   //                         File file = new File(category.getIconPath());
                            Image image = new Image(category.getIconPath(),50,50,false,true);
                            imageView.setImage(image);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                    }

                    //TODO: get right name from database
                    nameLabel.setText(category.getName().toUpperCase().replace(".PNG", "")); // set name display of item
                    setGraphic(rowBox);
                }
            }
        });
        listExpenseCategoryBox.getChildren().setAll(expenseListView);
    }

    public void addCategoriesBtnClick(ActionEvent e) throws Exception {
        // get add income scene
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addCategoriesBox addExpenseCategories_box = new addCategoriesBox();
        //System.out.println("Add categories click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add categories");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addExpenseCategories_box.getScene());

        dialogAddStage.showAndWait();
        // refresh categories in listview
        incomeCategoriesLoad();
        expenseCategoriesLoad();
    }

    public void editCategories(ActionEvent e) throws Exception {

    }


    @FXML
    public TextField filterText;
    public void filterCategoriesList(String oldValue, String newValue) {
        //income list
        ObservableList<Category> incomeFilteredList = FXCollections.observableArrayList();
        // get original list
        ArrayList<Category> incomeCategories = new ArrayList<Category>();
        try{
            incomeCategories =ProcessCategories.getIncomeCategories();
        }
        catch (ProcessExeption de){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Cannot get list of income");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            de.printStackTrace();
            return;
        }
        ObservableList<Category> incomeItems = FXCollections.observableArrayList(incomeCategories);

        //expense list
        ObservableList<Category> expenseFilteredList = FXCollections.observableArrayList();
        // get original list
        ArrayList<Category> expenseCategories = new ArrayList<Category>();
        try{
            expenseCategories =ProcessCategories.getExpenseCategories();
        }
        catch (ProcessExeption de){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Cannot get list of expense");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            de.printStackTrace();
            return;
        }
        ObservableList<Category> expenseItems = FXCollections.observableArrayList(expenseCategories);

        // filter by text
        if(filterText == null || (newValue.length() < oldValue.length()) || newValue == null) {
            incomeListView.setItems(incomeItems);
            expenseListView.setItems(expenseItems);
        }
        else {
            newValue = newValue.toUpperCase();
            for(Category category : incomeListView.getItems()) {
                String filterText = category.getName();
                if(filterText.toUpperCase().contains(newValue)) {
                    incomeFilteredList.add(category);
                }
            }
            for(Category category : expenseListView.getItems()) {
                String filterText = category.getName();
                if(filterText.toUpperCase().contains(newValue)) {
                    expenseFilteredList.add(category);
                }
            }
            incomeListView.setItems(incomeFilteredList);
            expenseListView.setItems(expenseFilteredList);
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
        loadTab();


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
        loadTab();
    }
}
