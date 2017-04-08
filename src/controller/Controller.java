package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.DatabaseConnection;
import model.db.Student;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    @FXML
    private TableView mainTableView;
    @FXML
    private TableColumn idColumn;
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

    private int actualOffset = 0;
    private final int windowSize = 100;
    private DatabaseConnection databaseConnection;


    public Controller() {

        databaseConnection = new DatabaseConnection();
        databaseConnection.start();

        while (!databaseConnection.isConnectionReady()) {
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Error occurred during waiting for initial data load", e);
            }
        }

        Initializer initializer = new Initializer();
        initializer.start();
    }

    private class Initializer extends Thread {
        public void run() {
            while (mainTableView == null) {
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, "Error occurred during waiting for initial data load", e);
                }
            }

            ObservableList<Student> tableData = FXCollections.observableArrayList(databaseConnection.getStudents(actualOffset, windowSize));
            LOG.log(Level.INFO, "Students were read");
            mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            initializeColumns();
            mainTableView.setItems(tableData);
        }
    }

    @FXML
    public void handleTableDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            Student rowData = (Student) mainTableView.getSelectionModel().getSelectedItem();
            System.out.println(rowData.getId() + " " + rowData.getName() + " " + rowData.getSurname());
        }
    }

    public void handleExit() {
        databaseConnection.close();
        LOG.log(Level.INFO, "Connection was closed");
    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        birthAtColumn.setCellValueFactory(new PropertyValueFactory<Student, Date>("birthAt"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("marksAverage"));
        awardsColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("awardsCount"));
        registrationsColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("registrationsCount"));
        graduationsAllColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("graduationsCountAll"));
        graduationsSuccessColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("graduationsCountSuccess"));
    }
}