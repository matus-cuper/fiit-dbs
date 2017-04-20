package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents secondary_schools table
 */
public class SecondarySchool {

    private Integer id;
    private String name;
    private String address;


    public SecondarySchool(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("secondary_school_id");
        this.name = resultSet.getString("name");
        this.address = resultSet.getString("address");
    }

    SecondarySchool(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
