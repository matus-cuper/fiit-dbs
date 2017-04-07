package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * Class represents students table
 */
public class Student {

    private Integer id;
    private SecondarySchool secondarySchool;
    private String name;
    private String surname;
    private Date birtAt;
    private String address;
    private String email;
    private String phone;
    private String zipCode;

    private Double graduationsAverage;
    private Integer awardsCount;
    private Integer registrationsCount;
    private Integer graduationsCountAll;
    private Integer graduationsCountSuccess;

    private Set<Award> awards;
    private Set<Graduation> graduations;
    private Set<GraduationFromSS> graduationsFromSS;
    private Set<Registration> registrations;


    public Student(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("student_id");
        this.name = resultSet.getString("name");
        this.surname = resultSet.getString("surname");
        this.birtAt = resultSet.getDate("birth_at");
        this.graduationsAverage = resultSet.getDouble("gss_avg");
        this.awardsCount = resultSet.getInt("a_count");
        this.registrationsCount = resultSet.getInt("r_count");
        this.graduationsCountAll = resultSet.getInt("g_count_all");
        this.graduationsCountSuccess = resultSet.getInt("g_count_success");
    }

    public Student(Integer id, SecondarySchool secondarySchool, String name, String surname,
                   Date birtAt, String address, String email, String phone, String zipCode) {
        this.id = id;
        this.secondarySchool = secondarySchool;
        this.name = name;
        this.surname = surname;
        this.birtAt = birtAt;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SecondarySchool getSecondarySchool() {
        return secondarySchool;
    }

    public void setSecondarySchool(SecondarySchool secondarySchool) {
        this.secondarySchool = secondarySchool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirtAt() {
        return birtAt;
    }

    public void setBirtAt(Date birtAt) {
        this.birtAt = birtAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getGraduationsAverage() {
        return graduationsAverage;
    }

    public void setGraduationsAverage(Double graduationsAverage) {
        this.graduationsAverage = graduationsAverage;
    }

    public Integer getAwardsCount() {
        return awardsCount;
    }

    public void setAwardsCount(Integer awardsCount) {
        this.awardsCount = awardsCount;
    }

    public Integer getRegistrationsCount() {
        return registrationsCount;
    }

    public void setRegistrationsCount(Integer registrationsCount) {
        this.registrationsCount = registrationsCount;
    }

    public Integer getGraduationsCountAll() {
        return graduationsCountAll;
    }

    public void setGraduationsCountAll(Integer graduationsCountAll) {
        this.graduationsCountAll = graduationsCountAll;
    }

    public Integer getGraduationsCountSuccess() {
        return graduationsCountSuccess;
    }

    public void setGraduationsCountSuccess(Integer graduationsCountSuccess) {
        this.graduationsCountSuccess = graduationsCountSuccess;
    }

    public Set<Award> getAwards() {
        return awards;
    }

    public void setAwards(Set<Award> awards) {
        this.awards = awards;
    }

    public Set<Graduation> getGraduations() {
        return graduations;
    }

    public void setGraduations(Set<Graduation> graduations) {
        this.graduations = graduations;
    }

    public Set<GraduationFromSS> getGraduationsFromSS() {
        return graduationsFromSS;
    }

    public void setGraduationsFromSS(Set<GraduationFromSS> graduationsFromSS) {
        this.graduationsFromSS = graduationsFromSS;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }
}
