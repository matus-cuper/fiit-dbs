package controller.detail;

import java.util.Date;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * Represent {@link model.db.SecondarySchool} and {@link model.db.GraduationFromSS}
 * in {@link controller.DetailedView}
 * In contrast with database model, this table contains duplicate values of
 * SecondarySchool information, caused by personal inner feeling, that it is right
 * Getters have to be listed because of {@link javafx.scene.control.cell.PropertyValueFactory}
 */
public class SecondarySchoolTable {

    private String name;
    private String address;
    private String subject;
    private Integer mark;
    private Date graduatedAt;


    public SecondarySchoolTable(String name, String address, String subject, Integer mark, Date graduatedAt) {
        this.name = name;
        this.address = address;
        this.subject = subject;
        this.mark = mark;
        this.graduatedAt = graduatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Date getGraduatedAt() {
        return graduatedAt;
    }
}
