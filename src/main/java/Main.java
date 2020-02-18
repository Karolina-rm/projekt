
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {

            View view = new View();
            primaryStage = view.getStage();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}