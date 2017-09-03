package controller.detail;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * Represent attributes of {@link model.db.Student} in {@link controller.DetailedView}
 * Student information are displayed as key-value table (both Strings)
 * where key stands for attribute name and value for student specific value
 * Getters have to be listed because of {@link javafx.scene.control.cell.PropertyValueFactory}
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

    public String getValue() {
        return value;
    }
}
