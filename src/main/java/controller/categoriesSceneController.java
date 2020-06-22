package controller;

import exception.DatabaseException;
import exception.ProcessExeption;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Category;
import process.ProcessCategories;
import scenes.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
        incomeCategoriesLoad();

        // load expense categories
        expenseCategoriesLoad();

        // load income and expense
        incomeWeekLoad();
        expensesWeekLoad();

    }

    @FXML
    PieChart incomeWeekPie;
    @FXML
    PieChart expensesWeekPie;
    @FXML
    public Label expensesWeekNote;
    @FXML
    public Label incomeWeekNote;
    public void incomeWeekLoadBtnClick(Event actionEvent) {
        incomeWeekLoad();
    }

    public void expensesWeekLoadBtnClick(Event actionEvent) {
        expensesWeekLoad();
    }

    public void incomeWeekLoad() {
        try{
            // data to be imported
            //TODO: get suitable values from own database

            int salaryValue = 200;
            int savingValue = 300;
            int businessValue = 600;
            PieChart.Data salary = new PieChart.Data("salary", salaryValue);
            PieChart.Data saving = new PieChart.Data("saving", savingValue);
            PieChart.Data business = new PieChart.Data("business", businessValue);
            incomeWeekPie.getData().clear();
            incomeWeekPie.getData().addAll(salary, saving,business);
            // display info when click
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
        catch(Exception ex){

        }
    }

    public void expensesWeekLoad() {
        try{
            // data to be imported
            int shopValue = 400;
            int billValue = 280;
            int healthValue = 500;
            PieChart.Data shop = new PieChart.Data("shopping", shopValue);
            PieChart.Data billing = new PieChart.Data("bill", billValue);
            PieChart.Data health = new PieChart.Data("health", healthValue);
            expensesWeekPie.getData().clear();
            expensesWeekPie.getData().addAll(shop, billing,health);
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
        catch(Exception ex){

        }
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


        // init array of original categories income
        ArrayList<Category> categories = new ArrayList<Category>();

        try{
            categories =ProcessCategories.getIncomeCategories();
        }
        catch (DatabaseException de){
            de.getErrorCodeMessage();
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
                        "-fx-padding: 10px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 10px;\n" + "-fx-background-insets: 10px;");
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
                                processExeption.printStackTrace();
                            }
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
                    } catch (Exception e) {
                        e.printStackTrace();
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
        listIncomeCategoryBox.getChildren().add(incomeListView);
    }

    public void expenseCategoriesLoad() {
        //TODO: get right list from database
        File folder = new File("src/main/resources/img/icon/expense");
        System.out.println("path expense: "+folder.getAbsolutePath());
        File[] files = folder.listFiles();
        System.out.println(files.length);
        Image[] listOfImages = new Image[files.length];
        String[] fileName = new String[files.length];

        // init array of original categories expense
        ArrayList<Category> categories = new ArrayList<Category>();
        for(int i=0;i<files.length;i++){
            File file = files[i];
            //TODO: get right list from database
            listOfImages[i] = new Image("/img/icon/expense/"+file.getName());
            fileName[i] = file.getName();

            // init category expenses
            categories.add(new Category(fileName[i].replace(".png",""), "abc", folder.getAbsolutePath()+"\\"+file.getName(),2));
            System.out.println(categories.get(i).getIconPath());
        }

        ObservableList<Category> items = FXCollections.observableArrayList(categories);
        expenseListView.setItems(items);

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
                        "-fx-padding: 10px;\n" + "-fx-background-insets: 10px;");
                editBtn.setStyle("-fx-font-size: 18;\n" +
                        "-fx-padding: 10px;\n" + "-fx-background-insets: 10px;");
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
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                });
                // edit button
                editBtn.setOnAction(event -> {
                    try {
                        // get add expense scene
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
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    for (int i = 0; i < categories.size(); i++) {
                        if (category.getName().equals(fileName[i].replace(".png",""))) {
                            File file = new File(category.getIconPath());
                            Image image = new Image(file.toURI().toString(),50,50,false,true);
                            imageView.setImage(image);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                        }
                    }

                    //TODO: get right name from database
                    nameLabel.setText(category.getName().toUpperCase().replace(".PNG", "")); // set name display of item
                    setGraphic(rowBox);
                }
            }
        });
        listExpenseCategoryBox.getChildren().add(expenseListView);
    }

    public void addCategoriesBtnClick(ActionEvent e) throws Exception {
        // get add income scene
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage

        addCategoriesBox addExpenseCategories_box = new addCategoriesBox();
        System.out.println("Add categories click");

        // dialog show
        Stage dialogAddStage = new Stage(StageStyle.TRANSPARENT);
        dialogAddStage.setTitle("Add categories");
        dialogAddStage.initModality(Modality.WINDOW_MODAL);
        dialogAddStage.initOwner(stage); // close this dialog to return to owner window
        dialogAddStage.setScene(addExpenseCategories_box.getScene());

        dialogAddStage.showAndWait();
    }

    public void editCategories(ActionEvent e) throws Exception {

    }

    public void deleteCategories(ActionEvent actionEvent) {
        final int selectedIncome = incomeListView.getSelectionModel().getSelectedIndex();
        String itemIncomeRemove = incomeListView.getSelectionModel().getSelectedItem().getName();
        if (selectedIncome != -1) {

            expenseListView.getSelectionModel().select(-1); // deselect expense item
            itemIncomeRemove = itemIncomeRemove.replace(".png","");
            // confirmation to delete
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + itemIncomeRemove + " ?", ButtonType.YES, ButtonType.NO);
            alertConfirm.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertConfirm.showAndWait();
            if (alertConfirm.getResult() == ButtonType.YES) {
                incomeListView.getItems().remove(selectedIncome);
                System.out.println("selectIdx: " + selectedIncome);
                System.out.println("item: " + itemIncomeRemove);
            }
            // deselect all item
            incomeListView.getSelectionModel().select(-1);

        }


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
