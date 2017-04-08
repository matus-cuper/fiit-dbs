package controller;

import model.db.Student;

/**
 * Created by Matus Cuper on 8.4.2017.
 *
 * This class represents detailed view of students,
 * window pop up after double click on record
 */
class DetailedView {


    DetailedView(Student student) {
        System.out.println(student.getId());
    }
}
