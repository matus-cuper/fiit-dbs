package controller.detail;

import java.util.Date;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * Represent {@link model.db.Registration} in {@link controller.DetailedView}
 * Getters have to be listed because of {@link javafx.scene.control.cell.PropertyValueFactory}
 */
public class RegistrationTable extends FieldOfStudyTable {

    private Date changedAt;
    private String status;


    public RegistrationTable(String university, String address, String fieldOfStudy, Date changedAt, String status) {
        super(university, address, fieldOfStudy);
        this.changedAt = changedAt;
        this.status = status;
    }

    public Date getChangedAt() {
        return changedAt;
    }

    public String getStatus() {
        return status;
    }
}
