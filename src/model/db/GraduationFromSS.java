package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
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


    GraduationFromSS(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("graduation_from_ss_id");
        this.subject = new Subject(resultSet.getInt("subject_id"), resultSet.getString("name"));
        this.graduatedAt = resultSet.getDate("graduated_at");
        this.mark = resultSet.getInt("mark");
    }

    public GraduationFromSS(Integer id, Subject subject, Date graduatedAt, Integer mark) {
        this.id = id;
        this.subject = subject;
        this.graduatedAt = graduatedAt;
        this.mark = mark;
    }

    public GraduationFromSS(Subject subject, String mark, Date graduatedAt) throws IllegalArgumentException {
        if (subject == null || mark == null || graduatedAt == null)
            throw new IllegalArgumentException();

        try {
            this.graduatedAt = graduatedAt;
            this.mark = Integer.parseInt(mark);
            if (this.mark > 4 || this.mark < 1)
                throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        this.subject = subject;
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
