package controller.detail;

import java.util.Date;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * This class represents {@link model.db.Graduation} in {@link controller.DetailedView}
 */
public class GraduationTable extends FieldOfStudyTable {

    private Date startedAt;
    private Date finishedAt;
    private Boolean graduated;


    public GraduationTable(String university, String address, String fieldOfStudy, Date startedAt,
                           Date finishedAt, Boolean graduated) {
        super(university, address, fieldOfStudy);
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.graduated = graduated;
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
