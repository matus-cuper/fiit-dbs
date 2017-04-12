package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.db.Student;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    @FXML
    private TableView<Student> mainTableView;
    @FXML
    private TableColumn<Student, String> nameColumn, surnameColumn;
    @FXML
    private TableColumn<Student, Integer> idColumn, awardsColumn, registrationsColumn, graduationsAllColumn, graduationsSuccessColumn;
    @FXML
    private TableColumn<Student, Double> marksColumn;
    @FXML
    private TableColumn<Student, Date> birthAtColumn;
    @FXML
    private Button previousButton, nextButton;

    private int tableSize;
    private int actualOffset = 0;
    private final int windowSize = 100;
    private DatabaseConnection databaseConnection;

    @FXML
    public void handleTableDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            Student student = mainTableView.getSelectionModel().getSelectedItem();
            createDetailedView(databaseConnection.getStudent(student.getId()));
        }
    }

    @FXML
    public void handlePreviousButton() {
        nextButton.setDisable(false);
        actualOffset = actualOffset - windowSize;
        if (actualOffset == 0)
            previousButton.setDisable(true);
        updateTableData();
    }

    @FXML
    public void handleNextButton() {
        previousButton.setDisable(false);
        actualOffset = actualOffset + windowSize;
        if (actualOffset + windowSize > tableSize)
            nextButton.setDisable(true);
        updateTableData();
    }

    @FXML
    public void handleDeleteButton() {
        List<Integer> studentIds = new LinkedList<>();
        for (Student student : mainTableView.getSelectionModel().getSelectedItems()) {
            studentIds.add(student.getId());
            LOG.log(Level.INFO, "Deleting student " + student.getId());
        }

        databaseConnection.deleteStudentsById(studentIds);
        updateTableData();
    }


    public Controller() {
        databaseConnection = new DatabaseConnection();
        databaseConnection.start();

        while (!databaseConnection.isConnectionReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Error occurred during waiting for initial data load", e);
            }
        }

        tableSize = databaseConnection.countRows("students");
        Initializer initializer = new Initializer();
        initializer.start();
    }

    private class Initializer extends Thread {
        public void run() {
            while (mainTableView == null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, "Error occurred during waiting for mainTableView rendering", e);
                }
            }

            ObservableList<Student> tableData = FXCollections.observableArrayList(databaseConnection.getStudents(actualOffset, windowSize));
            LOG.log(Level.INFO, "Students were read");
            mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            initializeColumns();
            mainTableView.setItems(tableData);
        }
    }

    private void createDetailedView(Student student) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/detailedPane.fxml"));
            Parent parent = fxmlLoader.load();
            DetailedView controller = fxmlLoader.getController();
            controller.setStudent(student);

            Stage detailedStage = new Stage();
            detailedStage.setTitle("Student detail");
            detailedStage.setScene(new Scene(parent, 1000, 615));
            detailedStage.show();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Missing detailedPane.fxml file in view directory", e);
        }
    }

    private void updateTableData() {
        ObservableList<Student> tableData = FXCollections.observableArrayList(databaseConnection.getStudents(actualOffset, windowSize));
        mainTableView.setItems(tableData);
    }

    public void handleExit() {
        databaseConnection.close();
        LOG.log(Level.INFO, "Connection was closed");
    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthAtColumn.setCellValueFactory(new PropertyValueFactory<>("birthAt"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marksAverage"));
        awardsColumn.setCellValueFactory(new PropertyValueFactory<>("awardsCount"));
        registrationsColumn.setCellValueFactory(new PropertyValueFactory<>("registrationsCount"));
        graduationsAllColumn.setCellValueFactory(new PropertyValueFactory<>("graduationsCountAll"));
        graduationsSuccessColumn.setCellValueFactory(new PropertyValueFactory<>("graduationsCountSuccess"));
    }
}