package model.db;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * This class represents fos_at_universities table
 */
public class FosAtUniversity {

    private Integer id;
    private University university;
    private FieldOfStudy fieldOfStudy;


    public FosAtUniversity(Integer id, University university, FieldOfStudy fieldOfStudy) {
        this.id = id;
        this.university = university;
        this.fieldOfStudy = fieldOfStudy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
