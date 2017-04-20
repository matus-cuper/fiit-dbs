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
    private String university;


    Registration(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("registration_id");
        this.fosAtUniversity = new FosAtUniversity(resultSet);
        this.status = new Status(resultSet);
        this.changedAt = resultSet.getDate("changed_at");
    }

    public Registration(University university, FieldOfStudy fieldOfStudy, Date changedAt, Status status)
            throws IllegalArgumentException {
        if (university == null || fieldOfStudy == null || changedAt == null || status == null)
            throw new IllegalArgumentException();

        this.changedAt = changedAt;
        this.fosAtUniversity = new FosAtUniversity(university, fieldOfStudy);
        this.status = status;
        this.university = this.fosAtUniversity.getUniversity().getName();
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

    public Status getStatus() {
        return status;
    }

    public Date getChangedAt() {
        return changedAt;
    }

    public String getUniversity() {
        return university;
    }
}
