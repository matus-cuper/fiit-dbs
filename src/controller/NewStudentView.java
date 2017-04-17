package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.db.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 15.4.2017.
 *
 * This class represent view for adding
 * new student into database
 */
public class NewStudentView {

    private static final Logger LOG = Logger.getLogger(NewStudentView.class.getName());

    @FXML
    private ComboBox<SecondarySchool> secondarySchoolCombo;
    @FXML
    private ComboBox<Subject> graduationFromSSSubjectCombo;
    @FXML
    private ComboBox<University> registrationUniversityCombo, graduationUniversityCombo;
    @FXML
    private ComboBox<FieldOfStudy> registrationFieldOfStudyCombo, graduationFieldOfStudyCombo;
    @FXML
    private ComboBox<Status> registrationStatusCombo;
    @FXML
    private ComboBox<AwardName> awardNameCombo;
    @FXML
    private ComboBox<AwardLevel> awardLevelCombo;
    @FXML
    private DatePicker graduationFromSSGraduatedAtPicker, registrationChangedAtPicker, awardAwardedAtPicker,
            graduationStartedAtPicker, graduationFinishedAtPicker;
    @FXML
    private Button addStudentButton, graduationFromSSAddButton, graduationFromSSRemoveButton, registrationAddButton,
            registrationRemoveButton, awardAddButton, awardRemoveButton, graduationAddButton, graduationRemoveButton;
    @FXML
    private TableView<GraduationFromSS> graduationsFromSSTableView;
    @FXML
    private TableView<Registration> registrationsTableView;
    @FXML
    private TableView<Award> awardsTableView;
    @FXML
    private TableView<Graduation> graduationsTableView;
    @FXML
    private TableColumn<Subject, GraduationFromSS> secondarySchoolSubjectColumn;
    @FXML
    private TableColumn<Integer, GraduationFromSS> secondarySchoolMarkColumn;
    @FXML
    private TableColumn<Date, GraduationFromSS> secondarySchoolGraduatedAtColumn;
    @FXML
    private TableColumn<FosAtUniversity, Registration> registrationUniversityColumn, registrationFieldOfStudyColumn;
    @FXML
    private TableColumn<Date, Registration> registrationChangedAtColumn;
    @FXML
    private TableColumn<Status, Registration> registrationStatusColumn;
    @FXML
    private TableColumn<AwardName, Award> awardNameColumn;
    @FXML
    private TableColumn<AwardLevel, Award> awardLevelColumn;
    @FXML
    private TableColumn<Date, Award> awardAwardedAtColumn;
    @FXML
    private TableColumn<FosAtUniversity, Graduation> graduationUniversityColumn, graduationFieldOfStudyColumn;
    @FXML
    private TableColumn<Date, Graduation> graduationStartedAtColumn, graduationFinishedAtColumn;
    @FXML
    private TableColumn<Boolean, Graduation> graduationGraduatedColumn;
    @FXML
    private TextField nameField, surnameField, birthAtField, phoneField, emailField, addressField, zipCodeField,
            graduationFromSSMarkField;
    @FXML
    private CheckBox graduationGraduatedCheck;

    private ObservableList<SecondarySchool> secondarySchoolsData;
    private ObservableList<Subject> subjectsData;
    private ObservableList<Status> statusesData;
    private ObservableList<University> universitiesData;
    private ObservableList<FieldOfStudy> fieldsOfStudyData;
    private ObservableList<AwardName> awardNamesData;
    private ObservableList<AwardLevel> awardLevelsData;
    private ObservableList<GraduationFromSS> graduationsFromSSData;
    private ObservableList<Registration> registrationsData;
    private ObservableList<Award> awardsData;
    private ObservableList<Graduation> graduationsData;

    private Controller ancestor;

    @FXML
    public void handleGraduationFromSSAddButton() {
        try {
            GraduationFromSS graduation = new GraduationFromSS(graduationFromSSSubjectCombo.getValue(),
                    graduationFromSSMarkField.getText(), graduationFromSSGraduatedAtPicker.getValue());
            graduationsFromSSData.add(graduation);
            graduationsFromSSTableView.setItems(graduationsFromSSData);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "Show window", e);
            // TODO throw warning
        }
    }

    @FXML
    public void handleRegistrationAddButton() {
        try {
            Registration registration = new Registration(registrationUniversityCombo.getValue(),
                    registrationFieldOfStudyCombo.getValue(), registrationChangedAtPicker.getValue(),
                    registrationStatusCombo.getValue());
            registrationsData.add(registration);
            registrationsTableView.setItems(registrationsData);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "Show window", e);
            // TODO throw warning
        }
    }

    @FXML
    public void handleAwardAddButton() {
        try {
            Award award = new Award(awardNameCombo.getValue(), awardLevelCombo.getValue(),
                    awardAwardedAtPicker.getValue());
            awardsData.add(award);
            awardsTableView.setItems(awardsData);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "Show window", e);
            // TODO throw warning
        }
    }

    @FXML
    public void handleGraduationAddButton() {
        try {
            Graduation graduation = new Graduation(graduationUniversityCombo.getValue(),
                    graduationFieldOfStudyCombo.getValue(), graduationStartedAtPicker.getValue(),
                    graduationFinishedAtPicker.getValue(), graduationGraduatedCheck.isSelected());
            graduationsData.add(graduation);
            graduationsTableView.setItems(graduationsData);
        } catch (IllegalArgumentException e) {
            LOG.log(Level.INFO, "Show window", e);
            // TODO throw warning
        }
    }

    public NewStudentView() {
        graduationsFromSSData = FXCollections.observableArrayList();
        registrationsData = FXCollections.observableArrayList();
        awardsData = FXCollections.observableArrayList();
        graduationsData = FXCollections.observableArrayList();
    }

    private void setFormatter() {
        graduationFromSSGraduatedAtPicker.setConverter(new MyConverter());
        registrationChangedAtPicker.setConverter(new MyConverter());
        awardAwardedAtPicker.setConverter(new MyConverter());
        graduationStartedAtPicker.setConverter(new MyConverter());
        graduationFinishedAtPicker.setConverter(new MyConverter());
    }

    private void setCombos() {
        secondarySchoolsData = FXCollections.observableArrayList(getSecondarySchools());
        subjectsData = FXCollections.observableArrayList(getSubjects());
        statusesData = FXCollections.observableArrayList(getStatuses());
        universitiesData = FXCollections.observableArrayList(getUniversities());
        fieldsOfStudyData = FXCollections.observableArrayList(getFieldOfStudies());
        awardNamesData = FXCollections.observableArrayList(getAwardNames());
        awardLevelsData = FXCollections.observableArrayList(getAwardLevels());

        secondarySchoolCombo.setItems(secondarySchoolsData);
        graduationFromSSSubjectCombo.setItems(subjectsData);
        registrationUniversityCombo.setItems(universitiesData);
        graduationUniversityCombo.setItems(universitiesData);
        registrationFieldOfStudyCombo.setItems(fieldsOfStudyData);
        graduationFieldOfStudyCombo.setItems(fieldsOfStudyData);
        registrationStatusCombo.setItems(statusesData);
        awardNameCombo.setItems(awardNamesData);
        awardLevelCombo.setItems(awardLevelsData);
    }

    private void initializeColumns() {
        secondarySchoolSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        secondarySchoolMarkColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        secondarySchoolGraduatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("graduatedAt"));

        registrationUniversityColumn.setCellValueFactory(new PropertyValueFactory<>("university"));
        registrationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<>("fieldOfStudy"));
        registrationChangedAtColumn.setCellValueFactory(new PropertyValueFactory<>("changedAt"));
        registrationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        awardNameColumn.setCellValueFactory(new PropertyValueFactory<>("awardName"));
        awardLevelColumn.setCellValueFactory(new PropertyValueFactory<>("awardLevel"));
        awardAwardedAtColumn.setCellValueFactory(new PropertyValueFactory<>("awardedAt"));

        graduationUniversityColumn.setCellValueFactory(new PropertyValueFactory<>("university"));
        graduationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<>("fieldOfStudy"));
        graduationStartedAtColumn.setCellValueFactory(new PropertyValueFactory<>("startedAt"));
        graduationFinishedAtColumn.setCellValueFactory(new PropertyValueFactory<>("finishedAt"));
        graduationGraduatedColumn.setCellValueFactory(new PropertyValueFactory<>("graduated"));
    }

    private List<SecondarySchool> getSecondarySchools() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("secondary_schools");
        List<SecondarySchool> secondarySchools = new LinkedList<>();

        try {
            while (resultSet.next())
                secondarySchools.add(new SecondarySchool(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of secondary schools", e);
        }
        return secondarySchools;
    }

    private List<Subject> getSubjects() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("subjects");
        List<Subject> subjects = new LinkedList<>();

        try {
            while (resultSet.next())
                subjects.add(new Subject(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of subjects", e);
        }
        return subjects;
    }

    private List<Status> getStatuses() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("statuses");
        List<Status> statuses = new LinkedList<>();

        try {
            while (resultSet.next())
                statuses.add(new Status(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of statuses", e);
        }
        return statuses;
    }

    private List<University> getUniversities() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("universities");
        List<University> universities = new LinkedList<>();

        try {
            while (resultSet.next())
                universities.add(new University(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of universities", e);
        }
        return universities;
    }

    private List<FieldOfStudy> getFieldOfStudies() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("fields_of_study");
        List<FieldOfStudy> fieldsOfStudy = new LinkedList<>();

        try {
            while (resultSet.next())
                fieldsOfStudy.add(new FieldOfStudy(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of field of studies", e);
        }
        return fieldsOfStudy;
    }

    private List<AwardName> getAwardNames() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("award_names");
        List<AwardName> awardNames = new LinkedList<>();

        try {
            while (resultSet.next())
                awardNames.add(new AwardName(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of award names", e);
        }
        return awardNames;
    }

    private List<AwardLevel> getAwardLevels() {
        ResultSet resultSet = ancestor.getDatabaseConnection().getAllTableData("award_levels");
        List<AwardLevel> awardLevels = new LinkedList<>();

        try {
            while (resultSet.next())
                awardLevels.add(new AwardLevel(resultSet));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during reading result set of award levels", e);
        }
        return awardLevels;
    }

    void setAncestor(Controller ancestor) {
        this.ancestor = ancestor;
        setCombos();
        setFormatter();
        initializeColumns();
    }
}
