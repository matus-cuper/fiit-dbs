package model;

import model.db.Student;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 7.4.2017.
 *
 * Class represent connection to database
 */
public class DatabaseConnection extends Thread {

    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());

    private static final String DB_URL = "jdbc:postgresql://" + PropertyReader.readProperty("database.host")
            + ":" + PropertyReader.readProperty("database.port") + "/" + PropertyReader.readProperty("database.name");
    private static final String DB_USER = PropertyReader.readProperty("database.user");
    private static final String DB_PASSWORD = PropertyReader.readProperty("database.password");

    private static Connection connection = null;
    private static Statement statement = null;


    public void run() {
        initialize();

        List<Student> students = new LinkedList<>();
        ResultSet rs;

        try {
            rs = statement.executeQuery("" +
                    "SELECT s.student_id, s.name, s.surname, s.birth_at,\n" +
                    "\tCOALESCE(gss.avg, 5.00) AS gss_avg,\n" +
                    "\tCOALESCE(a.count, 0) AS a_count,\n" +
                    "\tCOALESCE(r.count, 0) AS r_count,\n" +
                    "\tCOALESCE(g.count, 0) AS g_count_all,\n" +
                    "\tCOALESCE(g.count_success, 0) AS g_count_success\n" +
                    "FROM students s\n" +
                    "LEFT JOIN\n" +
                    "\t(\n" +
                    "\tSELECT gss.student_id, ROUND(AVG(gss.mark), 2) AS avg\n" +
                    "\tFROM graduations_from_ss gss\n" +
                    "\tGROUP BY gss.student_id\n" +
                    "\t) gss ON s.student_id = gss.student_id\n" +
                    "LEFT JOIN\n" +
                    "\t(\n" +
                    "\tSELECT a.student_id, COUNT(*)\n" +
                    "\tFROM awards a\n" +
                    "\tGROUP BY a.student_id\n" +
                    "\t) a ON s.student_id = a.student_id\n" +
                    "LEFT JOIN\n" +
                    "\t(\n" +
                    "\tSELECT r.student_id, COUNT(*)\n" +
                    "\tFROM registrations r\n" +
                    "\tGROUP BY r.student_id\n" +
                    "\t) r ON s.student_id = r.student_id\n" +
                    "LEFT JOIN\n" +
                    "\t(\n" +
                    "\tSELECT g.student_id, COUNT(*),\n" +
                    "\t\tSUM(CASE WHEN g.graduated = TRUE THEN 1 ELSE 0 END) AS count_success\n" +
                    "\tFROM graduations g\n" +
                    "\tGROUP BY g.student_id\n" +
                    "\t) g ON s.student_id = g.student_id\n" +
                    "LIMIT 100");

            while (rs.next()) {
                students.add(new Student(rs));
            }

            for (Student s : students) {
                System.out.println(s.getMarksAverage() + " " + s.getAwardsCount() + " " +
                        s.getRegistrationsCount() + " " + s.getGraduationsCountAll() + " " +
                        s.getGraduationsCountSuccess() + " " + s.getName() + " " + s.getSurname() + " " + s.getBirtAt());
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Failed during select from statuses", e);
        }

        close();
    }


    /**
     * Created connection to database given by connect String
     */
    private void initialize() {
        try {
            if (connection == null)
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            LOG.severe("Database access error occurred.");
        }

        try {
            if (statement == null)
                statement = connection.createStatement();
        } catch (SQLException e) {
            LOG.severe("Database access error occurred or method called on closed statement.");
        }
    }

    /**
     * Close connection to database after all work
     */
    private void close() {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            LOG.severe("Database access denied. Cannot close statement.");
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            LOG.severe("Database access denied. Cannot close connection.");
        }
    }
}
