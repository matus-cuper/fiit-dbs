package controller;

import controller.detail.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.db.Student;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Matus Cuper on 8.4.2017.
 *
 * Handle interaction with user on detailed view of student,
 * will pop up after double click on record in main table,
 * user will be able to review all information about given student
 *
 * After calling setter for student, Initializer Thread will be
 * created and tables with student information are filled,
 * other methods are only getters and initializer for these tables
 */
public class DetailedView {

    private static final Logger LOG = Logger.getLogger(DetailedView.class.getName());

    @FXML
    private TableView<StudentTable> studentTableView;
    @FXML
    private TableView<SecondarySchoolTable> graduationsFromSSTableView;
    @FXML
    private TableView<AwardTable> awardsTableView;
    @FXML
    private TableView<GraduationTable> graduationsTableView;
    @FXML
    private TableView<RegistrationTable> registrationsTableView;
    @FXML
    private TableColumn<StudentTable, String> keyColumn, valueColumn;
    @FXML
    private TableColumn<SecondarySchoolTable, String> secondarySchoolNameColumn, secondarySchoolAddressColumn, secondarySchoolSubjectColumn;
    @FXML
    private TableColumn<SecondarySchoolTable, Integer> secondarySchoolMarkColumn;
    @FXML
    private TableColumn<SecondarySchoolTable, Date> secondarySchoolGraduatedAtColumn;
    @FXML
    private TableColumn<AwardTable, String> awardNameColumn, awardLevelColumn;
    @FXML
    private TableColumn<AwardTable, Date> awardAwardedAtColumn;
    @FXML
    private TableColumn<GraduationTable, String> graduationUniversityColumn, graduationAddressColumn, graduationFieldOfStudyColumn;
    @FXML
    private TableColumn<GraduationTable, Date> graduationStartedAtColumn, graduationFinishedAtColumn;
    @FXML
    private TableColumn<GraduationTable, Boolean> graduationGraduatedColumn;
    @FXML
    private TableColumn<RegistrationTable, String> registrationUniversityColumn, registrationAddressColumn,
            registrationFieldOfStudyColumn, registrationStatusColumn;
    @FXML
    private TableColumn<RegistrationTable, Date> registrationChangedAtColumn;

    private Student student;


    public DetailedView() {}

    void setStudent(Student student) {
        this.student = student;
        LOG.log(Level.INFO, "Detailed view of student " + student.getId());
        Initializer initializer = new Initializer();
        initializer.start();
    }

    /**
     * Thread will wait for rendering detailed view and after that
     * will obtain data for given student, initialize columns for
     * tables and fill them with data
     */
    private class Initializer extends Thread {
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Error occurred during waiting for studentTableView rendering", e);
            }

            ObservableList<StudentTable> studentsData = FXCollections.observableArrayList(getStudent());
            ObservableList<SecondarySchoolTable> graduationsFromSSData = FXCollections.observableArrayList(getGraduationsFromSS());
            ObservableList<AwardTable> awardsData = FXCollections.observableArrayList(getAwards());
            ObservableList<GraduationTable> graduationsData = FXCollections.observableArrayList(getGraduations());
            ObservableList<RegistrationTable> registrationsData = FXCollections.observableArrayList(getRegistrations());

            initializeColumns();

            studentTableView.setItems(studentsData);
            graduationsFromSSTableView.setItems(graduationsFromSSData);
            awardsTableView.setItems(awardsData);
            graduationsTableView.setItems(graduationsData);
            registrationsTableView.setItems(registrationsData);
        }
    }

    private List<StudentTable> getStudent() {
        List<StudentTable> student = new LinkedList<>();

        student.add(new StudentTable("Name", this.student.getName()));
        student.add(new StudentTable("Surname", this.student.getSurname()));
        student.add(new StudentTable("Born at", this.student.getBirthAt().toString()));
        student.add(new StudentTable("Address", this.student.getAddress()));
        student.add(new StudentTable("Email", this.student.getEmail()));
        student.add(new StudentTable("Phone", this.student.getPhone()));
        student.add(new StudentTable("Zip code", this.student.getZipCode()));

        return student;
    }

    private List<SecondarySchoolTable> getGraduationsFromSS() {
        return student.getGraduationsFromSS().stream().map(graduation -> new SecondarySchoolTable(student.getSecondarySchool().getName(),
                student.getSecondarySchool().getAddress(), graduation.getSubject().getName(), graduation.getMark(),
                graduation.getGraduatedAt())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<AwardTable> getAwards() {
        return student.getAwards().stream().map(award -> new AwardTable(award.getAwardName().getName(),
                award.getAwardLevel().getName(), award.getAwardedAt())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<GraduationTable> getGraduations() {
        return student.getGraduations().stream().map(graduation -> new GraduationTable(graduation.getFosAtUniversity().getUniversity().getName(),
                graduation.getFosAtUniversity().getUniversity().getAddress(),
                graduation.getFosAtUniversity().getFieldOfStudy().getName(), graduation.getStartedAt(),
                graduation.getFinishedAt(), graduation.isGraduated())).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<RegistrationTable> getRegistrations() {
        return student.getRegistrations().stream().map(registration -> new RegistrationTable(registration.getFosAtUniversity().getUniversity().getName(),
                registration.getFosAtUniversity().getUniversity().getAddress(),
                registration.getFosAtUniversity().getFieldOfStudy().getName(), registration.getChangedAt(),
                registration.getStatus().getName())).collect(Collectors.toCollection(LinkedList::new));
    }

    private void initializeColumns() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        secondarySchoolNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        secondarySchoolAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        secondarySchoolSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        secondarySchoolMarkColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        secondarySchoolGraduatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("graduatedAt"));

        awardNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        awardLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        awardAwardedAtColumn.setCellValueFactory(new PropertyValueFactory<>("awardedAt"));

        graduationUniversityColumn.setCellValueFactory(new PropertyValueFactory<>("university"));
        graduationAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        graduationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<>("fieldOfStudy"));
        graduationStartedAtColumn.setCellValueFactory(new PropertyValueFactory<>("startedAt"));
        graduationFinishedAtColumn.setCellValueFactory(new PropertyValueFactory<>("finishedAt"));
        graduationGraduatedColumn.setCellValueFactory(new PropertyValueFactory<>("graduated"));

        registrationUniversityColumn.setCellValueFactory(new PropertyValueFactory<>("university"));
        registrationAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        registrationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<>("fieldOfStudy"));
        registrationChangedAtColumn.setCellValueFactory(new PropertyValueFactory<>("changedAt"));
        registrationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
