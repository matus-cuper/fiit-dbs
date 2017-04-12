package view;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private Controller controller;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPane.fxml"));
        Parent parent = fxmlLoader.load();
        controller = fxmlLoader.getController();

        primaryStage.setTitle("Students database");
        primaryStage.setScene(new Scene(parent, 1200, 700));
        primaryStage.show();
    }

    @Override
    public void stop() {
        controller.handleExit();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        Platform.exit();
    }
}
