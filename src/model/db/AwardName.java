package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents award_names table
 */
public class AwardName {

    private Integer id;
    private String name;


    public AwardName(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("award_name_id");
        this.name = resultSet.getString("name");
    }

    public AwardName(Integer id, String name) {
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
