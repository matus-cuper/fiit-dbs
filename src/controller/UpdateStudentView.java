package controller;

import controller.formatters.DatePickerFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Utils;
import model.db.*;
import view.ErrorDialog;
import view.InformationDialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Matus Cuper on 17.4.2017.
 *
 * Handle interaction with user on student updating view
 * Add handlers for updating student's {@link Award},
 * {@link GraduationFromSS}, {@link Registration} and
 * {@link Graduation}, this data will be completely removed
 * and added each time a student is updated, personal
 * information about student are updated by update statement
 *
 * Firstly empty tables are created, then {@link ComboBox} are
 * filled, {@link DatePicker} formats are set and tables
 * columns are initialized, then student is set and
 * tables and field are filled by its values
 */
public class UpdateStudentView {

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
            graduationStartedAtPicker, graduationFinishedAtPicker, birthAtPicker;
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
    private TableColumn<String, Registration> registrationUniversityColumn, registrationFieldOfStudyColumn;
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
    private TableColumn<String, Graduation> graduationUniversityColumn, graduationFieldOfStudyColumn;
    @FXML
    private TableColumn<Date, Graduation> graduationStartedAtColumn, graduationFinishedAtColumn;
    @FXML
    private TableColumn<Boolean, Graduation> graduationGraduatedColumn;
    @FXML
    private TextField nameField, surnameField, phoneField, emailField, addressField, zipCodeField,
            graduationFromSSMarkField;
    @FXML
    private CheckBox graduationGraduatedCheck;

    private ObservableList<GraduationFromSS> graduationsFromSSData;
    private ObservableList<Registration> registrationsData;
    private ObservableList<Award> awardsData;
    private ObservableList<Graduation> graduationsData;

    private Controller ancestor;
    private Student student;

    /**
     * Handler validates input data and pop up
     * {@link ErrorDialog} if some inconsistency exists
     */
    @FXML
    public void handleUpdateStudentButton() {
        try {
            Student student = new Student(this.student.getId(), secondarySchoolCombo.getValue(), nameField.getText(),
                    surnameField.getText(), Utils.convertDate(birthAtPicker.getValue()), addressField.getText(),
                    emailField.getText(), phoneField.getText(), zipCodeField.getText());
            if (secondarySchoolCombo.getValue() == null && !graduationsFromSSData.isEmpty())
                throw new IllegalArgumentException();

            student.setGraduationsFromSS(graduationsFromSSData);
            student.setRegistrations(registrationsData);
            student.setAwards(awardsData);
            student.setGraduations(graduationsData);

            ancestor.getDatabaseConnection().updateStudent(student);
            new InformationDialog("Added student " + student.getName() + " " + student.getSurname() + " with ID " +
                    student.getId());
            ancestor.updateTableData();
        } catch (IllegalArgumentException e) {
            new ErrorDialog("Cannot add student", "Null values are forbidden (except secondary school)\n" +
                    "Email address must contain . after @\n" +
                    "Phone number must start with +\n" +
                    "Phone number and zip code must contain only numbers\n" +
                    "Date of birth must be before any other date\n" +
                    "Maximum length is name(30), surname(30), address(80),\n" +
                    "email(70), phone(15) and zipCode(5)");
        }
    }

    // TODO remove this duplicate of NewStudentView
    @FXML
    public void handleGraduationFromSSAddButton() {
        try {
            GraduationFromSS graduation = new GraduationFromSS(graduationFromSSSubjectCombo.getValue(),
                    graduationFromSSMarkField.getText(), Utils.convertDate(graduationFromSSGraduatedAtPicker.getValue()));
            graduationsFromSSData.add(graduation);
            graduationsFromSSTableView.setItems(graduationsFromSSData);
        } catch (IllegalArgumentException e) {
            new ErrorDialog("Cannot add graduation", "Null values are forbidden\n" +
                    "Mark must be integer between 1 and 4");
        }
    }

    // TODO remove this duplicate of NewStudentView
    @FXML
    public void handleRegistrationAddButton() {
        try {
            Registration registration = new Registration(registrationUniversityCombo.getValue(),
                    registrationFieldOfStudyCombo.getValue(), Utils.convertDate(registrationChangedAtPicker.getValue()),
                    registrationStatusCombo.getValue());
            registrationsData.add(registration);
            registrationsTableView.setItems(registrationsData);
        } catch (IllegalArgumentException e) {
            new ErrorDialog("Cannot add registration", "Null values are forbidden\n");
        }
    }

    // TODO remove this duplicate of NewStudentView
    @FXML
    public void handleAwardAddButton() {
        try {
            Award award = new Award(awardNameCombo.getValue(), awardLevelCombo.getValue(),
                    Utils.convertDate(awardAwardedAtPicker.getValue()));
            awardsData.add(award);
            awardsTableView.setItems(awardsData);
        } catch (IllegalArgumentException e) {
            new ErrorDialog("Cannot add award", "Null values are forbidden\n");
        }
    }

    // TODO remove this duplicate of NewStudentView
    @FXML
    public void handleGraduationAddButton() {
        try {
            Graduation graduation = new Graduation(graduationUniversityCombo.getValue(),
                    graduationFieldOfStudyCombo.getValue(), Utils.convertDate(graduationStartedAtPicker.getValue()),
                    Utils.convertDate(graduationFinishedAtPicker.getValue()), graduationGraduatedCheck.isSelected());
            graduationsData.add(graduation);
            graduationsTableView.setItems(graduationsData);
        } catch (IllegalArgumentException e) {
            new ErrorDialog("Cannot add graduation", "Null values are forbidden\n" +
                    "Finished must be after started");
        }
    }

    @FXML
    public void handleGraduationFromSSRemoveButton() {
        graduationsFromSSData.remove(graduationsFromSSTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleRegistrationDeleteButton() {
        registrationsData.remove(registrationsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleAwardDeleteButton() {
        awardsData.remove(awardsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleGraduationDeleteButton() {
        graduationsData.remove(graduationsTableView.getSelectionModel().getSelectedItem());
    }


    public UpdateStudentView() {}

    void setAncestor(Controller ancestor) {
        this.ancestor = ancestor;
        setCombos();
        setFormatter();
        initializeColumns();
    }

    void setStudent(Student student) {
        this.student = student;

        nameField.setText(student.getName());
        surnameField.setText(student.getSurname());
        birthAtPicker.setValue(Instant.ofEpochMilli(student.getBirthAt().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());
        addressField.setText(student.getAddress());
        zipCodeField.setText(student.getZipCode());
        if (student.getSecondarySchool().getName() != null)
            secondarySchoolCombo.setValue(student.getSecondarySchool());

        graduationsFromSSData = FXCollections.observableArrayList(getGraduationsFromSS());
        registrationsData = FXCollections.observableArrayList(getRegistrations());
        awardsData = FXCollections.observableArrayList(getAwards());
        graduationsData = FXCollections.observableArrayList(getGraduations());

        graduationsFromSSTableView.setItems(graduationsFromSSData);
        registrationsTableView.setItems(registrationsData);
        awardsTableView.setItems(awardsData);
        graduationsTableView.setItems(graduationsData);
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

    private List<GraduationFromSS> getGraduationsFromSS() {
        return student.getGraduationsFromSS().stream().map(graduation -> new GraduationFromSS(graduation.getSubject(),
                graduation.getMark().toString(), graduation.getGraduatedAt())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Award> getAwards() {
        return student.getAwards().stream().map(award -> new Award(award.getAwardName(), award.getAwardLevel(),
                award.getAwardedAt())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Graduation> getGraduations() {
        return student.getGraduations().stream().map(graduation -> new Graduation(graduation.getFosAtUniversity().getUniversity(),
                graduation.getFosAtUniversity().getFieldOfStudy(), graduation.getStartedAt(),
                graduation.getFinishedAt(), graduation.isGraduated())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Registration> getRegistrations() {
        return student.getRegistrations().stream().map(registration -> new Registration(registration.getFosAtUniversity().getUniversity(),
                registration.getFosAtUniversity().getFieldOfStudy(), registration.getChangedAt(),
                registration.getStatus())).collect(Collectors.toCollection(LinkedList::new));
    }

    private void setFormatter() {
        graduationFromSSGraduatedAtPicker.setConverter(new DatePickerFormatter());
        registrationChangedAtPicker.setConverter(new DatePickerFormatter());
        awardAwardedAtPicker.setConverter(new DatePickerFormatter());
        graduationStartedAtPicker.setConverter(new DatePickerFormatter());
        graduationFinishedAtPicker.setConverter(new DatePickerFormatter());
        birthAtPicker.setConverter(new DatePickerFormatter());
    }

    private void setCombos() {
        ObservableList<SecondarySchool> secondarySchoolsData = FXCollections.observableArrayList(getSecondarySchools());
        ObservableList<Subject> subjectsData = FXCollections.observableArrayList(getSubjects());
        ObservableList<Status> statusesData = FXCollections.observableArrayList(getStatuses());
        ObservableList<University> universitiesData = FXCollections.observableArrayList(getUniversities());
        ObservableList<FieldOfStudy> fieldsOfStudyData = FXCollections.observableArrayList(getFieldOfStudies());
        ObservableList<AwardName> awardNamesData = FXCollections.observableArrayList(getAwardNames());
        ObservableList<AwardLevel> awardLevelsData = FXCollections.observableArrayList(getAwardLevels());

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
}
