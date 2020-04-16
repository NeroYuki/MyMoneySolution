import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import scenes.*;
import controller.*;

public class mainActivity extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Test App");
        loginScene login_scene = new loginScene();
        primaryStage.setScene(login_scene.getScene());
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
