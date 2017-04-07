package model;

import model.db.FosAtUniversity;
import model.db.Status;

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

        List<Status> statuses = new LinkedList<>();
        ResultSet rs;

        try {
            rs = statement.executeQuery("" +
                    "SELECT fos.*, \n" +
                    "\tf.name AS field_of_study_name,\n" +
                    "\tu.name AS university_name, u.address AS university_address\n" +
                    "FROM fos_at_universities fos\n" +
                    "JOIN fields_of_study f ON fos.field_of_study_id = f.field_of_study_id\n" +
                    "JOIN universities u ON fos.university_id = u.university_id\n" +
                    "LIMIT 100");

            while (rs.next()) {
                FosAtUniversity s = new FosAtUniversity(rs);
                System.out.println(s.getId() + " " + s.getUniversity().getName() + " " + s.getUniversity().getAddress() + " " + s.getFieldOfStudy().getName());
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
