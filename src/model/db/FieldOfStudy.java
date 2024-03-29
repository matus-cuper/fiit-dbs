package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * Representation of fields_of_study table in database
 */
public class FieldOfStudy {

    private Integer id;
    private String name;


    public FieldOfStudy(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("field_of_study_id");
        this.name = resultSet.getString("name");
    }

    FieldOfStudy(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // TODO remove and replace by duplicity in Graduation and Registration
    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
