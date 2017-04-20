package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents graduations table
 */
public class Graduation {

    private Integer id;
    private FosAtUniversity fosAtUniversity;
    private Date startedAt;
    private Date finishedAt;
    private Boolean graduated;
    private String university;
    private String fieldOfStudy;


    Graduation(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("graduation_id");
        this.fosAtUniversity = new FosAtUniversity(resultSet);
        this.startedAt = resultSet.getDate("started_at");
        this.finishedAt = resultSet.getDate("finished_at");
        this.graduated = resultSet.getBoolean("graduated");
    }

    public Graduation(Integer id, FosAtUniversity fosAtUniversity, Date startedAt, Date finishedAt, Boolean graduated) {
        this.id = id;
        this.fosAtUniversity = fosAtUniversity;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.graduated = graduated;
    }

    public Graduation(University university, FieldOfStudy fieldOfStudy, Date startedAt, Date finishedAt,
                      boolean graduated) throws IllegalArgumentException {
        if (university == null || fieldOfStudy == null || startedAt == null || finishedAt == null)
            throw new IllegalArgumentException();

        if (startedAt.after(finishedAt))
            throw new IllegalArgumentException();

        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.fosAtUniversity = new FosAtUniversity(university, fieldOfStudy);
        this.graduated = graduated;
        this.university = this.fosAtUniversity.getUniversity().getName();
        this.fieldOfStudy = this.fosAtUniversity.getFieldOfStudy().getName();
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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(Boolean graduated) {
        this.graduated = graduated;
    }

    public String getUniversity() {
        return university;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
}
