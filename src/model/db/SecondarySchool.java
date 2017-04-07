package model.db;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents secondary_schools table
 */
public class SecondarySchool {

    private Integer id;
    private String name;
    private String address;


    public SecondarySchool(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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
