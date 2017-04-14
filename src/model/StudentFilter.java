package model;

import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 12.4.2017.
 *
 * This class represents filters applied on query
 */
public class StudentFilter {

    private static final Logger LOG = Logger.getLogger(StudentFilter.class.getName());

    private String name = null;
    private String surname = null;
    private Date birthAfter = null;
    private Date birthUntil = null;
    private Double averageGreater = null;
    private Double averageLower = null;
    private Integer countGreater = null;
    private Integer countLower = null;


    public StudentFilter() {}

    public String getName() {
        return name != null ? name + "%" : "%";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname != null ? surname + "%" : "%";
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @SuppressWarnings("deprecation")
    Date getBirthAfter() {
        // new Date(0, 1, 1) is 1900-01-01
        return birthAfter != null ? birthAfter : new Date(0, 0, 1);
    }

    public void setBirthAfter(String birthAfter) {
        try {
            this.birthAfter = Utils.parseDate(birthAfter);
        } catch (ParseException e) {
            LOG.log(Level.INFO, "Unparseable date " + birthAfter);
            this.birthAfter = null;
            // TODO show warning
        }
    }

    @SuppressWarnings("deprecation")
    Date getBirthUntil() {
        // new Date(200, 1, 1) is 2100-01-01
        return birthUntil != null ? birthUntil : new Date(200, 0, 1);
    }

    public void setBirthUntil(String birthUntil) {
        try {
            this.birthUntil = Utils.parseDate(birthUntil);
        } catch (ParseException e) {
            LOG.log(Level.INFO, "Unparseable date " + birthUntil);
            this.birthUntil = null;
            // TODO show warning
        }
    }

    Double getAverageGreater() {
        return averageGreater != null ? averageGreater : new Double(1.0);
    }

    public void setAverageGreater(String averageGreater) {
        try {
            this.averageGreater = Double.parseDouble(averageGreater);
        } catch (NumberFormatException e) {
            LOG.log(Level.INFO, "Unparseable double " + averageGreater);
            this.averageGreater = null;
            // TODO show warning
        }
    }

    Double getAverageLower() {
        return averageLower != null ? averageLower : new Double(5.0);
    }

    public void setAverageLower(String averageLower) {
        try {
            this.averageLower = Double.parseDouble(averageLower);
        } catch (NumberFormatException e) {
            LOG.log(Level.INFO, "Unparseable double " + averageLower);
            this.averageLower = null;
            // TODO show warning
        }
    }

    Integer getCountGreater() {
        return countGreater != null ? countGreater : new Integer(0);
    }

    public void setCountGreater(String countGreater) {
        try {
            this.countGreater = Integer.parseInt(countGreater);
        } catch (NumberFormatException e) {
            LOG.log(Level.INFO, "Unparseable integer " + countGreater);
            this.countGreater = null;
            // TODO show warning
        }
    }

    Integer getCountLower() {
        return countLower != null ? countLower : new Integer(Integer.MAX_VALUE);
    }

    public void setCountLower(String countLower) {
        try {
            this.countLower = Integer.parseInt(countLower);
        } catch (NumberFormatException e) {
            LOG.log(Level.INFO, "Unparseable integer " + countLower);
            this.countLower = null;
            // TODO show warning
        }
    }
}
