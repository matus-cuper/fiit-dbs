package controller;

import controller.db.SecondarySchoolTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.db.*;
import sun.rmi.runtime.Log;

import javafx.scene.control.TableColumn;
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


        }
    }

    public void setStudent(Student student) {
        this.student = student;
        Initializer initializer = new Initializer();
        initializer.start();
    }
}
