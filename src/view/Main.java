package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DatabaseConnection;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        launch(args);
    }
}
