package model.db;

import model.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * Representation of students table in database
 * Some aggregated attributes are used only for
 * main view, getters are necessary because of
 * {@link javafx.scene.control.cell.PropertyValueFactory}
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

    public Student(SecondarySchool secondarySchool, String name, String surname, Date birthAt, String address,
                   String email, String phone, String zipCode) throws IllegalArgumentException {

        name = Utils.removeWhitespaces(name);
        surname = Utils.removeWhitespaces(surname);
        email = Utils.removeWhitespaces(email);
        phone = Utils.removeWhitespaces(phone);
        zipCode = Utils.removeWhitespaces(zipCode);

        if (name == null || surname == null || birthAt == null || address == null ||
                email == null || phone == null || zipCode == null)
            throw new IllegalArgumentException();

        if (name.length() > 30 || surname.length() > 30 || address.length() > 80 || email.length() > 70)
            throw new IllegalArgumentException();

        if (Utils.countMatches(email, "@") != 1 ||
                Utils.countMatches(email.substring(email.lastIndexOf("@") + 1), ".") != 1)
            throw new IllegalArgumentException();

        if (!phone.startsWith("+") ||
                !Utils.containOnlyNumbers(phone.substring(1)) ||
                phone.length() > 15)
            throw new IllegalArgumentException();

        if (!Utils.containOnlyNumbers(zipCode) || zipCode.length() != 5)
            throw new IllegalArgumentException();

        this.name = name;
        this.surname = surname;
        this.birthAt = birthAt;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.zipCode = zipCode;

        this.secondarySchool = secondarySchool;
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

    public String getZipCode() {
        return zipCode;
    }

    public Double getMarksAverage() {
        return marksAverage;
    }

    public Integer getAwardsCount() {
        return awardsCount;
    }

    public Integer getRegistrationsCount() {
        return registrationsCount;
    }

    public Integer getGraduationsCountAll() {
        return graduationsCountAll;
    }

    public Integer getGraduationsCountSuccess() {
        return graduationsCountSuccess;
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
