package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.StudentFilter;
import model.db.Student;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Observer {

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
    @FXML
    private TextField nameField, surnameField, birthAtAfterField, birthAtUntilField, marksGreaterField, marksLowerField,
            registrationsGreaterField, registrationsLowerField;

    private DatabaseConnection databaseConnection;
    private ObservableList<Student> tableData;
    private StudentFilter filter;

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
        databaseConnection.previousWindow();
        if (databaseConnection.firstWindow())
            previousButton.setDisable(true);
        updateTableData();
    }

    @FXML
    public void handleNextButton() {
        // TODO handle next button, bug after second window
        previousButton.setDisable(false);
        databaseConnection.nextWindow();
        if (databaseConnection.lastWindow())
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

    @FXML
    public void handleCreateButton() {
        createNewStudentView();
    }

    @FXML
    public void handleUpdateButton() {
        for (Student student : mainTableView.getSelectionModel().getSelectedItems()) {
            createUpdateStudentView(databaseConnection.getStudent(student.getId()));
            LOG.log(Level.INFO, "Updating student " + student.getId());
        }
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

        filter = new StudentFilter();
        filter.addObserver(this);
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

            tableData = FXCollections.observableArrayList(databaseConnection.getStudents());
            LOG.log(Level.INFO, "Students were read");
            mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            initializeListeners();
            initializeColumns();
            mainTableView.setItems(tableData);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        databaseConnection.setFilter(filter);
        LOG.log(Level.INFO, "Students were filtered");
        updateTableData();

        nextButton.setDisable(false);
        if (databaseConnection.lastWindow())
            nextButton.setDisable(true);
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

    private void createNewStudentView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/newStudentPane.fxml"));
            Parent parent = fxmlLoader.load();
            NewStudentView controller = fxmlLoader.getController();
            controller.setAncestor(this);

            Stage detailedStage = new Stage();
            detailedStage.setTitle("New student");
            detailedStage.setScene(new Scene(parent, 965, 770));
            detailedStage.show();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Missing newStudentPane.fxml file in view directory", e);
        }
    }

    private void createUpdateStudentView(Student student) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/updateStudentPane.fxml"));
            Parent parent = fxmlLoader.load();
            UpdateStudentView controller = fxmlLoader.getController();
            controller.setStudent(student);
            controller.setAncestor(this);

            Stage updatedStage = new Stage();
            updatedStage.setTitle("Update student");
            updatedStage.setScene(new Scene(parent, 965, 770));
            updatedStage.show();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Missing updateStudentPane.fxml file in view directory", e);
        }
    }

    private void updateTableData() {
        tableData = FXCollections.observableArrayList(databaseConnection.getStudents());
        mainTableView.setItems(tableData);
    }

    public void handleExit() {
        databaseConnection.close();
        LOG.log(Level.INFO, "Connection was closed");
    }

    private void initializeListeners() {
        nameField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setName(newValue)));
        surnameField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setSurname(newValue)));
        birthAtAfterField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setBirthAfter(newValue)));
        birthAtUntilField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setBirthUntil(newValue)));
        marksGreaterField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setAverageGreater(newValue)));
        marksLowerField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setAverageLower(newValue)));
        registrationsGreaterField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setCountGreater(newValue)));
        registrationsLowerField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setCountLower(newValue)));
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

    DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
}