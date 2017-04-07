package model.db;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents awards_level table
 */
public class AwardLevel {

    private Integer id;
    private String name;


    public AwardLevel(Integer id, String name) {
        this.id = id;
        this.name = name;
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
