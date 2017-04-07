package model.db;

import java.util.Date;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents graduations_from_ss table
 */
public class GraduationFromSS {

    private Integer id;
    private Subject subject;
    private Date graduatedAt;
    private Integer mark;


    public GraduationFromSS(Integer id, Subject subject, Date graduatedAt, Integer mark) {
        this.id = id;
        this.subject = subject;
        this.graduatedAt = graduatedAt;
        this.mark = mark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getGraduatedAt() {
        return graduatedAt;
    }

    public void setGraduatedAt(Date graduatedAt) {
        this.graduatedAt = graduatedAt;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
