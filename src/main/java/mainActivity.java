import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import scenes.*;
import database.*;
import controller.*;

public class mainActivity extends Application {

    //Move to a separated config file later

    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "leesown123";

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Test App");
        loginScene login_scene = new loginScene();
        primaryStage.setScene(login_scene.getScene());
        primaryStage.show();
    }
    public static void main(String[] args) {
        PersonalDatabase.initConnection(DB_URL, USER_NAME, PASSWORD);
        launch(args);
    }
}
