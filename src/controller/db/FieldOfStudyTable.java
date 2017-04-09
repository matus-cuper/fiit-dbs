package controller.db;

/**
 * Created by Matus Cuper on 9.4.2017.
 *
 * This class represents {@link model.db.FieldOfStudy} in {@link controller.DetailedView}
 */
class FieldOfStudyTable {

    private String university;
    private String address;
    private String fieldOfStudy;


    FieldOfStudyTable(String university, String address, String fieldOfStudy) {
        this.university = university;
        this.address = address;
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
