package model;

import view.ErrorDialog;

import java.sql.Date;
import java.text.ParseException;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 12.4.2017.
 *
 * This class represents filters applied on query
 */
public class StudentFilter extends Observable {

    private static final Logger LOG = Logger.getLogger(StudentFilter.class.getName());

    private String name = null;
    private String surname = null;
    private Date birthAfter = null;
    private Date birthUntil = null;
    private Double averageGreater = null;
    private Double averageLower = null;
    private Integer countGreater = null;
    private Integer countLower = null;
    private boolean changed = false;


    public StudentFilter() {}

    private void change() {
        if (this.changed) {
            setChanged();
            notifyObservers();
        }
    }

    private void change(boolean changed) {
        if (changed) {
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + " " + getBirthAfter() + " " + getBirthUntil() + " " +
                getAverageGreater() + " " + getAverageLower() + " " + getCountGreater() + " " + getCountLower();
    }

    public String getName() {
        return name != null ? name + "%" : "%";
    }

    public void setName(String name) {
        this.name = name;
        change(true);
    }

    public String getSurname() {
        return surname != null ? surname + "%" : "%";
    }

    public void setSurname(String surname) {
        this.surname = surname;
        change(true);
    }

    @SuppressWarnings("deprecation")
    Date getBirthAfter() {
        // new Date(0, 1, 1) is 1900-01-01
        return birthAfter != null ? birthAfter : new Date(0, 0, 1);
    }

    public void setBirthAfter(String birthAfter) {
        changed = false;
        try {
            this.birthAfter = Utils.parseDate(birthAfter);
            changed = true;
        } catch (ParseException e) {
            new ErrorDialog("Unparseable date", "Date must be in yyyy-MM-dd format");
        }
        change();
    }

    @SuppressWarnings("deprecation")
    Date getBirthUntil() {
        // new Date(200, 1, 1) is 2100-01-01
        return birthUntil != null ? birthUntil : new Date(200, 0, 1);
    }

    public void setBirthUntil(String birthUntil) {
        changed = false;
        try {
            this.birthUntil = Utils.parseDate(birthUntil);
            changed = true;
        } catch (ParseException e) {
            new ErrorDialog("Unparseable date", "Date must be in yyyy-MM-dd format");
        }
        change();
    }

    Double getAverageGreater() {
        return averageGreater != null ? averageGreater : new Double(1.0);
    }

    public void setAverageGreater(String averageGreater) {
        changed = false;
        try {
            this.averageGreater = Utils.parseDouble(averageGreater);
            changed = true;
        } catch (NumberFormatException e) {
            new ErrorDialog("Unparseable double", "Average must be double between 1 and 5");
        }
        change();
    }

    Double getAverageLower() {
        return averageLower != null ? averageLower : new Double(5.0);
    }

    public void setAverageLower(String averageLower) {
        changed = false;
        try {
            this.averageLower = Utils.parseDouble(averageLower);
            changed = true;
        } catch (NumberFormatException e) {
            new ErrorDialog("Unparseable double", "Average must be double between 1 and 5");
        }
        change();
    }

    Integer getCountGreater() {
        return countGreater != null ? countGreater : new Integer(0);
    }

    public void setCountGreater(String countGreater) {
        changed = false;
        try {
            this.countGreater = Utils.parseInteger(countGreater);
            changed = true;
        } catch (NumberFormatException e) {
            new ErrorDialog("Unparseable integer", "Count must be integer");
        }
        change();
    }

    Integer getCountLower() {
        return countLower != null ? countLower : new Integer(Integer.MAX_VALUE);
    }

    public void setCountLower(String countLower) {
        changed = false;
        try {
            this.countLower = Utils.parseInteger(countLower);
            changed = true;
        } catch (NumberFormatException e) {
            new ErrorDialog("Unparseable integer", "Count must be integer");
        }
        change();
    }
}
