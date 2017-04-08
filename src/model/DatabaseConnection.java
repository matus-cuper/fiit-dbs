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

    private boolean connectionReady = false;

    public void run() {
        initialize();
        connectionReady = true;
    }


    /**
     * Created connection to database given by connect String
     */
    private void initialize() {
        try {
            if (connection == null)
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during getting connection", e);
        }

        try {
            if (statement == null)
                statement = connection.createStatement();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during creating statement", e);
        }
    }

    /**
     * Close connection to database after all work
     */
    public void close() {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during closing statement", e);
        }

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during closing connection", e);
        }
    }

    public int countRows(String tableName) {
        Integer result = null;
        try {
            ResultSet resultSet = statement.executeQuery(PreparedQuery.countRows(tableName));
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during counting rows of table " + tableName, e);
        }

        return (result != null) ? result : 0;
    }

    public List<Student> getStudents(int offset, int limit) {

        List<Student> students = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(PreparedQuery.mainTable());
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                students.add(new Student(resultSet));

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during selecting students into main table", e);
        }

        return students;
    }

    public Student getStudent(int id) {
        Student student = null;
        List<PreparedStatement> preparedStatements = new LinkedList<>();
        List<ResultSet> resultSets = new LinkedList<>();

        try {
            connection.setAutoCommit(false);

            preparedStatements.add(connection.prepareStatement(PreparedQuery.studentById()));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.graduationsFromSSByStudent()));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.awardsByStudent()));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.graduationsByStudent()));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.registrationsByStudent()));

            for (PreparedStatement p : preparedStatements) {
                p.setInt(1, id);
                resultSets.add(p.executeQuery());
            }
            connection.commit();
            student = new Student(resultSets);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during selecting details about student", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error occurred during setting auto commit back to true", e);
            }
        }

        return student;
    }

    public boolean isConnectionReady() {
        return connectionReady;
    }
}
