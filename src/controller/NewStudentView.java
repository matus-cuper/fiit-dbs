package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableColumn<String, Subject> secondarySchoolSubjectColumn;
    @FXML
    private TableColumn<Integer, GraduationFromSS> secondarySchoolMarkColumn;
    @FXML
    private TableColumn<Date, GraduationFromSS> secondarySchoolGraduatedAtColumn;
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

    private Controller ancestor;

    @FXML
    public void handleGraduationFromSSAddButton() {
        System.out.println(graduationFromSSSubjectCombo.getValue());
        System.out.println(graduationFromSSMarkField.getText());
        System.out.println(graduationFromSSGraduatedAtPicker.getValue());
    }

    @FXML
    public void handleRegistrationAddButton() {
        System.out.println(registrationUniversityCombo.getValue());
        System.out.println(registrationFieldOfStudyCombo.getValue());
        System.out.println(registrationChangedAtPicker.getValue());
        System.out.println(registrationStatusCombo.getValue());
    }

    @FXML
    public void handleAwardAddButton() {
        System.out.println(awardNameCombo.getValue());
        System.out.println(awardLevelCombo.getValue());
        System.out.println(awardAwardedAtPicker.getValue());
    }

    @FXML
    public void handleGraduationAddButton() {
        System.out.println(graduationUniversityCombo.getValue());
        System.out.println(graduationFieldOfStudyCombo.getValue());
        System.out.println(graduationStartedAtPicker.getValue());
        System.out.println(graduationFinishedAtPicker.getValue());
        System.out.println(graduationGraduatedCheck.isSelected());
    }

    public NewStudentView() {}

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
    }
}
