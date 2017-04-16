package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.db.SecondarySchool;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private ObservableList<SecondarySchool> secondarySchoolData;
    private Controller ancestor;


    public NewStudentView() {}

    private void setCombos() {
        secondarySchoolData = FXCollections.observableArrayList(getSecondarySchools());
        secondarySchoolCombo.setItems(secondarySchoolData);
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

    void setAncestor(Controller ancestor) {
        this.ancestor = ancestor;
        setCombos();
    }
}
