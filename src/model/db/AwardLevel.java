package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * Representation of award_levels table in database
 */
public class AwardLevel {

    private Integer id;
    private String name;


    public AwardLevel(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("award_level_id");
        this.name = resultSet.getString("name");
    }

    AwardLevel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
