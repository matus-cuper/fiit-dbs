package model.db;

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

    private Set<Award> awards;
    private Set<Graduation> graduations;
    private Set<GraduationFromSS> graduationsFromSS;
    private Set<Registration> registrations;


    public Student(Integer id, SecondarySchool secondarySchool, String name, String surname, Date birtAt, String address, String email, String phone, String zipCode) {
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
