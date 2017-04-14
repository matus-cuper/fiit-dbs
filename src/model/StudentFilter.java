package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matus Cuper on 12.4.2017.
 *
 * This class represents filters applied on query
 */
public class StudentFilter {

    private String name;
    private String surname;
    private Date birthAfter;
    private Date birthUntil;
    private Double averageGreater;
    private Double averageLower;
    private Integer countGreater;
    private Integer countLower;

    private static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


    public StudentFilter() {
        averageGreater = 1.;
        averageLower = 5.;
        countGreater = 0;
        countLower = Integer.MAX_VALUE;
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

    public Date getBirthAfter() {
        return birthAfter;
    }

    public void setBirthAfter(String birthAfter) {
        try {
            this.birthAfter = parser.parse(birthAfter);
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO show warning
        }
    }

    public Date getBirthUntil() {
        return birthUntil;
    }

    public void setBirthUntil(String birthUntil) {
        try {
            this.birthUntil = parser.parse(birthUntil);
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO show warning
        }
    }

    public Double getAverageGreater() {
        return averageGreater;
    }

    public void setAverageGreater(String averageGreater) {
        try {
            this.averageGreater = Double.parseDouble(averageGreater);
        } catch (NumberFormatException e) {
            // TODO show warning
        }
    }

    public Double getAverageLower() {
        return averageLower;
    }

    public void setAverageLower(String averageLower) {
        try {
            this.averageLower = Double.parseDouble(averageLower);
        } catch (NumberFormatException e) {
            // TODO show warning
        }
    }

    public Integer getCountGreater() {
        return countGreater;
    }

    public void setCountGreater(String countGreater) {
        try {
            this.countGreater = Integer.parseInt(countGreater);
        } catch (NumberFormatException e) {
            // TODO show warning
        }
    }

    public Integer getCountLower() {
        return countLower;
    }

    public void setCountLower(String countLower) {
        try {
            this.countLower = Integer.parseInt(countLower);
        } catch (NumberFormatException e) {
            // TODO show warning
        }
    }
}
