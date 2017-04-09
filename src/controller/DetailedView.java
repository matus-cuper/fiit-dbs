package controller;

import controller.db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.db.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 8.4.2017.
 *
 * This class represents detailed view of students,
 * window pop up after double click on record
 */
public class DetailedView {

    private static final Logger LOG = Logger.getLogger(DetailedView.class.getName());

    @FXML
    private TableView studentTableView, graduationsFromSSTableView, awardsTableView, graduationsTableView, registrationsTableView;
    @FXML
    private TableColumn keyColumn, valueColumn;
    @FXML
    private TableColumn secondarySchoolNameColumn, secondarySchoolAddressColumn, secondarySchoolSubjectColumn,
            secondarySchoolMarkColumn, secondarySchoolGraduatedAtColumn;
    @FXML
    private TableColumn awardNameColumn, awardLevelColumn, awardAwardedAtColumn;
    @FXML
    private TableColumn graduationUniversityColumn, graduationAddressColumn, graduationFieldOfStudyColumn,
            graduationStartedAtColumn, graduationFinishedAtColumn, graduationGraduatedColumn;
    @FXML
    private TableColumn registrationUniversityColumn, registrationAddressColumn, registrationFieldOfStudyColumn,
            registrationChangedAtColumn, registrationStatusColumn;

    private Student student;


    public DetailedView() {}

    private class Initializer extends Thread {
        public void run() {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Error occurred during waiting for studentTableView rendering", e);
            }

            ObservableList<StudentTable> studentsData = FXCollections.observableArrayList(getStudents());
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

    private List<StudentTable> getStudents() {
        List<StudentTable> students = new LinkedList<>();

        students.add(new StudentTable("Name", student.getName()));
        students.add(new StudentTable("Surname", student.getSurname()));
        students.add(new StudentTable("Born at", student.getBirthAt().toString()));
        students.add(new StudentTable("Address", student.getAddress()));
        students.add(new StudentTable("Email", student.getEmail()));
        students.add(new StudentTable("Phone", student.getPhone()));
        students.add(new StudentTable("Zip code", student.getZipCode()));

        return students;
    }

    private List<SecondarySchoolTable> getGraduationsFromSS() {
        List<SecondarySchoolTable> secondarySchools = new LinkedList<>();
        for (GraduationFromSS graduation : student.getGraduationsFromSS())
            secondarySchools.add(new SecondarySchoolTable(student.getSecondarySchool().getName(),
                    student.getSecondarySchool().getAddress(), graduation.getSubject().getName(), graduation.getMark(), graduation.getGraduatedAt()));

        return secondarySchools;
    }

    private List<AwardTable> getAwards() {
        List<AwardTable> awards = new LinkedList<>();
        for (Award award : student.getAwards())
            awards.add(new AwardTable(award.getAwardName().getName(), award.getAwardLevel().getName(), award.getAwardedAt()));

        return awards;
    }

    private List<GraduationTable> getGraduations() {
        List<GraduationTable> graduations = new LinkedList<>();
        for (Graduation graduation : student.getGraduations())
            graduations.add(new GraduationTable(graduation.getFosAtUniversity().getUniversity().getName(),
                    graduation.getFosAtUniversity().getUniversity().getAddress(),
                    graduation.getFosAtUniversity().getFieldOfStudy().getName(),
                    graduation.getStartedAt(), graduation.getFinishedAt(), graduation.isGraduated()));

        return graduations;
    }

    private List<RegistrationTable> getRegistrations() {
        List<RegistrationTable> registrations = new LinkedList<>();
        for (Registration registration : student.getRegistrations())
            registrations.add(new RegistrationTable(registration.getFosAtUniversity().getUniversity().getName(),
                    registration.getFosAtUniversity().getUniversity().getAddress(),
                    registration.getFosAtUniversity().getFieldOfStudy().getName(), registration.getChangedAt(),
                    registration.getStatus().getName()));

        return registrations;
    }

    private void initializeColumns() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("value"));

        secondarySchoolNameColumn.setCellValueFactory(new PropertyValueFactory<SecondarySchoolTable, String>("name"));
        secondarySchoolAddressColumn.setCellValueFactory(new PropertyValueFactory<SecondarySchoolTable, String>("address"));
        secondarySchoolSubjectColumn.setCellValueFactory(new PropertyValueFactory<SecondarySchoolTable, String>("subject"));
        secondarySchoolMarkColumn.setCellValueFactory(new PropertyValueFactory<SecondarySchoolTable, Integer>("mark"));
        secondarySchoolGraduatedAtColumn.setCellValueFactory(new PropertyValueFactory<SecondarySchoolTable, Date>("graduatedAt"));

        awardNameColumn.setCellValueFactory(new PropertyValueFactory<AwardTable, String>("name"));
        awardLevelColumn.setCellValueFactory(new PropertyValueFactory<AwardTable, String>("level"));
        awardAwardedAtColumn.setCellValueFactory(new PropertyValueFactory<AwardTable, Date>("awardedAt"));

        graduationUniversityColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, String>("university"));
        graduationAddressColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, String>("address"));
        graduationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, String>("fieldOfStudy"));
        graduationStartedAtColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, Date>("startedAt"));
        graduationFinishedAtColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, Date>("finishedAt"));
        graduationGraduatedColumn.setCellValueFactory(new PropertyValueFactory<GraduationTable, Boolean>("graduated"));

        registrationUniversityColumn.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("university"));
        registrationAddressColumn.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("address"));
        registrationFieldOfStudyColumn.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("fieldOfStudy"));
        registrationChangedAtColumn.setCellValueFactory(new PropertyValueFactory<RegistrationTable, Date>("changedAt"));
        registrationStatusColumn.setCellValueFactory(new PropertyValueFactory<RegistrationTable, String>("status"));
    }

    void setStudent(Student student) {
        this.student = student;
        Initializer initializer = new Initializer();
        initializer.start();
    }
}
