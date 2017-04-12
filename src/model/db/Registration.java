package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents registrations table
 */
public class Registration {

    private Integer id;
    private FosAtUniversity fosAtUniversity;
    private Status status;
    private Date changedAt;


    public Registration(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("registration_id");
        this.fosAtUniversity = new FosAtUniversity(resultSet);
        this.status = new Status(resultSet);
        this.changedAt = resultSet.getDate("changed_at");
    }

    public Registration(Integer id, FosAtUniversity fosAtUniversity, Status status, Date changedAt) {
        this.id = id;
        this.fosAtUniversity = fosAtUniversity;
        this.status = status;
        this.changedAt = changedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FosAtUniversity getFosAtUniversity() {
        return fosAtUniversity;
    }

    public void setFosAtUniversity(FosAtUniversity fosAtUniversity) {
        this.fosAtUniversity = fosAtUniversity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }
}
