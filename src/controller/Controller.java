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
import model.Utils;
import model.db.Student;
import view.InformationDialog;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle interaction with user on main application window
 * Add handler for double click in main table, windowing buttons,
 * creating, updating and deleting students, checkBox for using
 * materialized view and searching filters
 *
 * Firstly, {@link DatabaseConnection} is created, then default
 * filter for main table and data for it are loaded, other
 * method are only initializers for table and constructors
 * for other views like {@link DetailedView}, {@link NewStudentView}
 * and {@link UpdateStudentView}
 */
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
    @FXML
    private CheckBox useViewCheck;

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
        updateTableData();
        if (databaseConnection.firstWindow())
            previousButton.setDisable(true);
    }

    @FXML
    public void handleNextButton() {
        previousButton.setDisable(false);
        databaseConnection.nextWindow();
        updateTableData();
        if (databaseConnection.lastWindow())
            nextButton.setDisable(true);
    }

    /**
     * If multiple students are selected for deletion,
     * delete them all and create message for {@link InformationDialog}
     */
    @FXML
    public void handleDeleteButton() {
        List<Integer> studentIds = new LinkedList<>();
        List<String> message = new LinkedList<>(Collections.singletonList("Deleted students:"));

        for (Student student : mainTableView.getSelectionModel().getSelectedItems()) {
            studentIds.add(student.getId());
            message.add(student.getName() + " " + student.getSurname() + " with ID " + student.getId());
        }

        new InformationDialog(message);
        databaseConnection.deleteStudentsById(studentIds);
        updateTableData();
    }

    @FXML
    public void handleCreateButton() {
        createNewStudentView();
    }

    @FXML
    public void handleUpdateButton() {
        for (Student student : mainTableView.getSelectionModel().getSelectedItems())
            createUpdateStudentView(databaseConnection.getStudent(student.getId()));
    }

    @FXML
    public void handleUseViewCheck() {
        databaseConnection.setUseView(useViewCheck.isSelected());
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

    /**
     * Thread will initialize data for main table after
     * table is showed up, also table columns are set
     * and filter listeners are added
     */
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

    /**
     * If {@link StudentFilter} will changed, data in main table are updated
     * also windowing buttons are disabled in case of reaching bounds
     * @param observable object
     * @param o unused argument, passed to notifyObservers object from {@link StudentFilter}
     */
    @Override
    public void update(Observable observable, Object o) {
        databaseConnection.setFilter(filter);
        updateTableData();

        nextButton.setDisable(false);
        if (databaseConnection.lastWindow())
            nextButton.setDisable(true);
        previousButton.setDisable(false);
        if (databaseConnection.firstWindow())
            previousButton.setDisable(true);
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

    /**
     * Initialize listeners for searching filters,
     * in case of illegal input, {@link view.ErrorDialog} will pop up
     * and last valid input are set for filter
     */
    private void initializeListeners() {
        nameField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setName(newValue)));
        surnameField.textProperty().addListener(((observableValue, oldValue, newValue) -> filter.setSurname(newValue)));

        birthAtAfterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setBirthAfter(newValue);
            // TODO move to function
            try {
                Utils.parseDate(newValue);
            } catch (ParseException e) {
                if (!newValue.equals(""))
                    birthAtAfterField.setText(oldValue);
            }
        });
        birthAtUntilField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setBirthUntil(newValue);
            // TODO move to function
            try {
                Utils.parseDate(newValue);
            } catch (ParseException e) {
                if (!newValue.equals(""))
                    birthAtUntilField.setText(oldValue);
            }
        });

        marksGreaterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setAverageGreater(newValue);
            // TODO move to function
            try {
                Utils.parseDouble(newValue);
            } catch (NumberFormatException e) {
                if (!newValue.equals(""))
                    marksGreaterField.setText(oldValue);
            }
        });
        marksLowerField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setAverageLower(newValue);
            // TODO move to function
            try {
                Utils.parseDouble(newValue);
            } catch (NumberFormatException e) {
                if (!newValue.equals(""))
                    marksLowerField.setText(oldValue);
            }
        });

        registrationsGreaterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setCountGreater(newValue);
            // TODO move to function
            try {
                Utils.parseInteger(newValue);
            } catch (NumberFormatException e) {
                if (!newValue.equals(""))
                    registrationsGreaterField.setText(oldValue);
            }
        });
        registrationsLowerField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setCountLower(newValue);
            // TODO move to function
            try {
                Utils.parseInteger(newValue);
            } catch (NumberFormatException e) {
                if (!newValue.equals(""))
                    registrationsLowerField.setText(oldValue);
            }
        });
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

    void updateTableData() {
        tableData = FXCollections.observableArrayList(databaseConnection.getStudents());
        mainTableView.setItems(tableData);
    }

    public void handleExit() {
        databaseConnection.close();
        LOG.log(Level.INFO, "Connection was closed");
    }

    DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
}