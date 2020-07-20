import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scenes.*;
import database.*;

public class mainActivity extends Application {

    //Move to a separated config file later

    private static final String DB_URL = "jdbc:mysql://db4free.net:3306/jfinance?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";


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
