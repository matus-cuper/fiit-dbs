package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.DatabaseConnection;
import model.db.Student;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    @FXML
    private TableView mainTableView;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn birthAtColumn;
    @FXML
    private TableColumn marksColumn;
    @FXML
    private TableColumn awardsColumn;
    @FXML
    private TableColumn registrationsColumn;
    @FXML
    private TableColumn graduationsAllColumn;
    @FXML
    private TableColumn graduationsSuccessColumn;

    private DatabaseConnection databaseConnection;


    public Controller() {

        databaseConnection = new DatabaseConnection();
        databaseConnection.start();

        while (!databaseConnection.isInitialLoadReady()) {
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Error occurred during waiting for initial data load", e);
            }
        }

        ObservableList<Student> tableData = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            tableData.add(databaseConnection.students.get(i));
        }

        for (Student s : databaseConnection.students) {
            System.out.println(s.getMarksAverage() + " " + s.getAwardsCount() + " " +
                    s.getRegistrationsCount() + " " + s.getGraduationsCountAll() + " " +
                    s.getGraduationsCountSuccess() + " " + s.getName() + " " + s.getSurname() + " " + s.getBirtAt());
        }


    }
}
