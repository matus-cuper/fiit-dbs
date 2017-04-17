package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents fos_at_universities table
 */
public class FosAtUniversity {

    private Integer id;
    private University university;
    private FieldOfStudy fieldOfStudy;


    FosAtUniversity(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("field_of_study_id");
        this.university = new University(resultSet.getInt("university_id"), resultSet.getString("university_name"), resultSet.getString("university_address"));
        this.fieldOfStudy = new FieldOfStudy(resultSet.getInt("field_of_study_id"), resultSet.getString("field_of_study_name"));
    }

    FosAtUniversity(Integer id, University university, FieldOfStudy fieldOfStudy) {
        this.id = id;
        this.university = university;
        this.fieldOfStudy = fieldOfStudy;
    }

    @Override
    public String toString() {
        return fieldOfStudy.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
