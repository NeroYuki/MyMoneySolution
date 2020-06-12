import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scenes.*;
import database.*;

public class mainActivity extends Application {

    //Move to a separated config file later

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jfinance?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "SE330.K21";

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Test App");
        loginScene login_scene = new loginScene();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.setScene(login_scene.getScene());
        primaryStage.show();
    }
    public static void main(String[] args) {
        DatabaseManager.initConnection(DB_URL, USER_NAME, PASSWORD);
        if (!DatabaseManager.checkTableDatabase("loggedUser")) {
            DatabaseManager.initDatabase();
        };
        launch(args);
    }
}
