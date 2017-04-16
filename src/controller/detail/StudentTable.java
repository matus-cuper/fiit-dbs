package controller.detail;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * This class represents {@link model.db.Student} in {@link controller.DetailedView}
 */
public class StudentTable {

    private String key;
    private String value;


    public StudentTable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
