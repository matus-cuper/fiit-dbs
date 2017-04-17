package model.db;

import model.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private Date birthAt;
    private String address;
    private String email;
    private String phone;
    private String zipCode;

    private Double marksAverage;
    private Integer awardsCount;
    private Integer registrationsCount;
    private Integer graduationsCountAll;
    private Integer graduationsCountSuccess;

    private List<Award> awards;
    private List<Graduation> graduations;
    private List<GraduationFromSS> graduationsFromSS;
    private List<Registration> registrations;


    public Student(List<ResultSet> resultSets) throws SQLException {
        ResultSet studentRS = resultSets.get(0);
        studentRS.next();

        this.id = studentRS.getInt("student_id");
        this.secondarySchool = new SecondarySchool(studentRS.getInt("secondary_school_id"),
                studentRS.getString("secondary_school_name"), studentRS.getString("secondary_school_address"));
        this.name = studentRS.getString("name");
        this.surname = studentRS.getString("surname");
        this.birthAt = studentRS.getDate("birth_at");
        this.address = studentRS.getString("address");
        this.email = studentRS.getString("email");
        this.phone = studentRS.getString("phone");
        this.zipCode = studentRS.getString("zip_code");

        this.graduationsFromSS = new LinkedList<>();
        while (resultSets.get(1).next())
            this.graduationsFromSS.add(new GraduationFromSS(resultSets.get(1)));

        this.awards = new LinkedList<>();
        while (resultSets.get(2).next())
            this.awards.add(new Award(resultSets.get(2)));

        this.graduations = new LinkedList<>();
        while (resultSets.get(3).next())
            this.graduations.add(new Graduation(resultSets.get(3)));

        this.registrations = new LinkedList<>();
        while (resultSets.get(4).next())
            this.registrations.add(new Registration(resultSets.get(4)));
    }

    public Student(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("student_id");
        this.name = resultSet.getString("name");
        this.surname = resultSet.getString("surname");
        this.birthAt = resultSet.getDate("birth_at");
        this.marksAverage = resultSet.getDouble("gss_avg");
        this.awardsCount = resultSet.getInt("a_count");
        this.registrationsCount = resultSet.getInt("r_count");
        this.graduationsCountAll = resultSet.getInt("g_count_all");
        this.graduationsCountSuccess = resultSet.getInt("g_count_success");
    }

    public Student(Integer id, SecondarySchool secondarySchool, String name, String surname,
                   Date birthAt, String address, String email, String phone, String zipCode) {
        this.id = id;
        this.secondarySchool = secondarySchool;
        this.name = name;
        this.surname = surname;
        this.birthAt = birthAt;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;
    }

    public Student(SecondarySchool secondarySchool, String name, String surname, LocalDate birthAt, String address,
                   String email, String phone, String zipCode) throws IllegalArgumentException {
        if (name == null || surname == null || birthAt == null || address == null || email == null || phone == null || zipCode == null)
            throw new IllegalArgumentException();

        try {
            this.birthAt = Utils.parseDate(birthAt.toString());
            // TODO for email - contains @ only once, contains . once after @
            // TODO for phone - starts with + and others are numbers, max size 15 chars with +
            if (!phone.startsWith("+"))
                throw new IllegalArgumentException();
            // TODO for zipCode - contains only numbers, max 5 after removing spaces

            // /*
            this.email = email;
            this.phone = phone;
            this.zipCode = zipCode;
            // */

        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }

        this.secondarySchool = secondarySchool;
        this.name = name;
        this.surname = surname;
        this.address = address;
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

    public Date getBirthAt() {
        return birthAt;
    }

    public void setBirthAt(Date birthAt) {
        this.birthAt = birthAt;
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

    public Double getMarksAverage() {
        return marksAverage;
    }

    public void setMarksAverage(Double marksAverage) {
        this.marksAverage = marksAverage;
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

    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) throws IllegalArgumentException {
        for (Award award : awards)
            if (award.getAwardedAt().before(this.birthAt))
                throw new IllegalArgumentException();

        this.awards = awards;
    }

    public List<Graduation> getGraduations() {
        return graduations;
    }

    public void setGraduations(List<Graduation> graduations) throws IllegalArgumentException {
        for (Graduation graduation : graduations)
            if (graduation.getStartedAt().before(this.birthAt) || graduation.getFinishedAt().before(this.birthAt))
                throw new IllegalArgumentException();

        this.graduations = graduations;
    }

    public List<GraduationFromSS> getGraduationsFromSS() {
        return graduationsFromSS;
    }

    public void setGraduationsFromSS(List<GraduationFromSS> graduationsFromSS) throws IllegalArgumentException {
        for (GraduationFromSS graduation : graduationsFromSS)
            if (graduation.getGraduatedAt().before(this.birthAt))
                throw new IllegalArgumentException();

        this.graduationsFromSS = graduationsFromSS;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) throws IllegalArgumentException {
        for (Registration registration : registrations)
            if (registration.getChangedAt().before(this.birthAt))
                throw new IllegalArgumentException();

        this.registrations = registrations;
    }
}
