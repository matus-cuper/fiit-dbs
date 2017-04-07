package model.db;

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


    public Graduation(Integer id, FosAtUniversity fosAtUniversity, Date startedAt, Date finishedAt, Boolean graduated) {
        this.id = id;
        this.fosAtUniversity = fosAtUniversity;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.graduated = graduated;
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

    public Boolean getGraduated() {
        return graduated;
    }

    public void setGraduated(Boolean graduated) {
        this.graduated = graduated;
    }
}
