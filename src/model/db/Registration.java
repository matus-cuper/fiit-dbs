package model.db;

import model.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
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

    public Registration(Integer id, FosAtUniversity fosAtUniversity, Status status, Date changedAt) {
        this.id = id;
        this.fosAtUniversity = fosAtUniversity;
        this.status = status;
        this.changedAt = changedAt;
    }

    public Registration(University university, FieldOfStudy fieldOfStudy, LocalDate changedAt, Status status)
            throws IllegalArgumentException {
        if (university == null || fieldOfStudy == null || changedAt == null || status == null)
            throw new IllegalArgumentException();

        try {
            this.changedAt = Utils.parseDate(changedAt.toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }

        this.fosAtUniversity = new FosAtUniversity(1, university, fieldOfStudy);
        // TODO unique constraints on university and field of study in fos_at_universities table
//        this.fosAtUniversity = new FosAtUniversity(university, fieldOfStudy);
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

    public String getUniversity() {
        return university;
    }
}
