package model;

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
public class DatabaseConnection {

    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());

    private static final String DB_URL = "jdbc:postgresql://" + PropertyReader.readProperty("database.host")
            + ":" + PropertyReader.readProperty("database.port") + "/" + PropertyReader.readProperty("database.name");
    private static final String DB_USER = PropertyReader.readProperty("database.user");
    private static final String DB_PASSWORD = PropertyReader.readProperty("database.password");

    private static Connection connection = null;
    private static Statement statement = null;


    public DatabaseConnection() {
        initialize();

        List<Status> statuses = new LinkedList<>();
        ResultSet rs;

        try {
            rs = statement.executeQuery("SELECT * FROM statuses");

            while (rs.next()) {
                Status s = new Status(rs);
                System.out.println(s.getId() + " " + s.getName());
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Failed during select from statuses", e);
        }

        stop();
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
    private void stop() {
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
