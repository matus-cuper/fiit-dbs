package model;

import model.db.*;

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

    private Connection connection = null;
    private Statement statement = null;
    private StudentFilter filter;

    private boolean connectionReady = false;
    private int tableSize;
    private int actualOffset = 0;
    private final int windowSize = 100;

    public void run() {
        initialize();
        tableSize = countRows("students");
        filter = new StudentFilter();
        connectionReady = true;
    }
    // TODO unify table classes setters

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

    private int countRows(String tableName) {
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

    public List<Student> getStudents() {

        List<Student> students = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(PreparedQuery.mainTable);
            preparedStatement.setString(1, filter.getName());
            preparedStatement.setString(2, filter.getSurname());
            preparedStatement.setDate(3, filter.getBirthAfter());
            preparedStatement.setDate(4, filter.getBirthUntil());
            preparedStatement.setDouble(5, filter.getAverageGreater());
            preparedStatement.setDouble(6, filter.getAverageLower());
            preparedStatement.setInt(7, filter.getCountGreater());
            preparedStatement.setInt(8, filter.getCountLower());
            preparedStatement.setInt(9, actualOffset);
            preparedStatement.setInt(10, windowSize);

            ResultSet resultSet = preparedStatement.executeQuery();
            tableSize = 0;
            while (resultSet.next()) {
                students.add(new Student(resultSet));
                tableSize++;
            }

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

            preparedStatements.add(connection.prepareStatement(PreparedQuery.studentById));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.graduationsFromSSByStudent));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.awardsByStudent));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.graduationsByStudent));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.registrationsByStudent));

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

    public void deleteStudentsById(List<Integer> studentIds) {
        try {
            connection.setAutoCommit(false);
            studentIds.forEach(this::deleteStudentById);
            connection.commit();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during deleting student information", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error occurred during setting auto commit back to true", e);
            }
        }
    }

    private void deleteStudentById(Integer studentId) {

        List<PreparedStatement> preparedStatements = new LinkedList<>();
        try {
            preparedStatements.add(connection.prepareStatement(PreparedQuery.deleteRegistrationsByStudentId));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.deleteGraduationsByStudentId));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.deleteAwardsByStudentId));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.deleteGraduationsFromSSByStudentId));
            preparedStatements.add(connection.prepareStatement(PreparedQuery.deleteStudentById));

            for (PreparedStatement p : preparedStatements) {
                p.setInt(1, studentId);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during deleting student information", e);
        }
    }

    public void insertStudent(Student student) {

        int studentId = 0;
        List<PreparedStatement> statements = new LinkedList<>();

        try {
            connection.setAutoCommit(false);
            PreparedStatement studentStatement = connection.prepareStatement(PreparedQuery.insertStudent, Statement.RETURN_GENERATED_KEYS);

            if (student.getSecondarySchool() == null)
                studentStatement.setNull(1, Types.BIGINT);
            else
                studentStatement.setInt(1, student.getSecondarySchool().getId());
            studentStatement.setString(2, student.getName());
            studentStatement.setString(3, student.getSurname());
            studentStatement.setDate(4, Utils.parseDate(student.getBirthAt()));
            studentStatement.setString(5, student.getAddress());
            studentStatement.setString(6, student.getEmail());
            studentStatement.setString(7, student.getPhone());
            studentStatement.setString(8, student.getZipCode());

            int affectedRows = studentStatement.executeUpdate();
            if (affectedRows > 0)
                studentId = getReturnedId(studentStatement.getGeneratedKeys());

            if (studentId > 0) {
                for (GraduationFromSS graduation : student.getGraduationsFromSS()) {
                    PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertGraduationFromSS);
                    statement.setInt(1, studentId);
                    statement.setInt(2, graduation.getSubject().getId());
                    statement.setDate(3, Utils.parseDate(graduation.getGraduatedAt()));
                    statement.setInt(4, graduation.getMark());
                    statements.add(statement);
                }

                for (Award award : student.getAwards()) {
                    PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertAward);
                    statement.setInt(1, award.getAwardLevel().getId());
                    statement.setInt(2, award.getAwardName().getId());
                    statement.setInt(3, studentId);
                    statement.setDate(4, Utils.parseDate(award.getAwardedAt()));
                    statements.add(statement);
                }

                for (Graduation graduation : student.getGraduations()) {
                    graduation.getFosAtUniversity().setId(getFosAtUniversityId(graduation.getFosAtUniversity()));
                    PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertGraduation);
                    statement.setInt(1, graduation.getFosAtUniversity().getId());
                    statement.setInt(2, studentId);
                    statement.setDate(3, Utils.parseDate(graduation.getStartedAt()));
                    statement.setDate(4, Utils.parseDate(graduation.getFinishedAt()));
                    statement.setBoolean(5, graduation.isGraduated());
                    statements.add(statement);
                }

                for (Registration registration : student.getRegistrations()) {
                    registration.getFosAtUniversity().setId(getFosAtUniversityId(registration.getFosAtUniversity()));
                    PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertRegistration);
                    statement.setInt(1, registration.getFosAtUniversity().getId());
                    statement.setInt(2, studentId);
                    statement.setInt(3, registration.getStatus().getId());
                    statement.setDate(4, Utils.parseDate(registration.getChangedAt()));
                    statements.add(statement);
                }
            }

            for (PreparedStatement statement : statements)
                statement.executeUpdate();

            connection.commit();
            LOG.log(Level.INFO, "Inserting student " + studentId);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during inserting student information", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error occurred during setting auto commit back to true", e);
            }
        }
    }

    public void updateStudent(Student student) {
        List<PreparedStatement> statements = new LinkedList<>();

        try {
            connection.setAutoCommit(false);
            PreparedStatement studentStatement = connection.prepareStatement(PreparedQuery.updateStudent);

            studentStatement.setInt(1, student.getSecondarySchool().getId());
            studentStatement.setString(2, student.getName());
            studentStatement.setString(3, student.getSurname());
            studentStatement.setDate(4, Utils.parseDate(student.getBirthAt()));
            studentStatement.setString(5, student.getAddress());
            studentStatement.setString(6, student.getEmail());
            studentStatement.setString(7, student.getPhone());
            studentStatement.setString(8, student.getZipCode());
            studentStatement.setInt(9, student.getId());

            studentStatement.executeUpdate();

            statements.add(connection.prepareStatement(PreparedQuery.deleteRegistrationsByStudentId));
            statements.add(connection.prepareStatement(PreparedQuery.deleteGraduationsByStudentId));
            statements.add(connection.prepareStatement(PreparedQuery.deleteAwardsByStudentId));
            statements.add(connection.prepareStatement(PreparedQuery.deleteGraduationsFromSSByStudentId));

            for (PreparedStatement statement : statements)
                statement.setInt(1, student.getId());

            for (GraduationFromSS graduation : student.getGraduationsFromSS()) {
                PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertGraduationFromSS);
                statement.setInt(1, student.getId());
                statement.setInt(2, graduation.getSubject().getId());
                statement.setDate(3, Utils.parseDate(graduation.getGraduatedAt()));
                statement.setInt(4, graduation.getMark());
                statements.add(statement);
            }

            for (Award award : student.getAwards()) {
                PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertAward);
                statement.setInt(1, award.getAwardLevel().getId());
                statement.setInt(2, award.getAwardName().getId());
                statement.setInt(3, student.getId());
                statement.setDate(4, Utils.parseDate(award.getAwardedAt()));
                statements.add(statement);
            }

            for (Graduation graduation : student.getGraduations()) {
                graduation.getFosAtUniversity().setId(getFosAtUniversityId(graduation.getFosAtUniversity()));
                PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertGraduation);
                statement.setInt(1, graduation.getFosAtUniversity().getId());
                statement.setInt(2, student.getId());
                statement.setDate(3, Utils.parseDate(graduation.getStartedAt()));
                statement.setDate(4, Utils.parseDate(graduation.getFinishedAt()));
                statement.setBoolean(5, graduation.isGraduated());
                statements.add(statement);
            }

            for (Registration registration : student.getRegistrations()) {
                registration.getFosAtUniversity().setId(getFosAtUniversityId(registration.getFosAtUniversity()));
                PreparedStatement statement = connection.prepareStatement(PreparedQuery.insertRegistration);
                statement.setInt(1, registration.getFosAtUniversity().getId());
                statement.setInt(2, student.getId());
                statement.setInt(3, registration.getStatus().getId());
                statement.setDate(4, Utils.parseDate(registration.getChangedAt()));
                statements.add(statement);
            }

            for (PreparedStatement statement : statements)
                statement.executeUpdate();

            connection.commit();
            LOG.log(Level.INFO, "Updating student " + student.getId());
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during inserting student information", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error occurred during setting auto commit back to true", e);
            }
        }
    }

    private Integer getFosAtUniversityId(FosAtUniversity fosAtUniversity) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(PreparedQuery.selectFosAtUniversityId);
        statement.setInt(1, fosAtUniversity.getUniversity().getId());
        statement.setInt(2, fosAtUniversity.getFieldOfStudy().getId());

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            return resultSet.getInt(1);

        statement = connection.prepareStatement(PreparedQuery.insertFosAtUniversityId, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, fosAtUniversity.getUniversity().getId());
        statement.setInt(2, fosAtUniversity.getFieldOfStudy().getId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0)
            return getReturnedId(statement.getGeneratedKeys());

        return null;
    }

    private int getReturnedId(ResultSet resultSet) {
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during generating new student key", e);
        }
        return 0;
    }

    public ResultSet getAllTableData(String tableName) {
        try {
            return statement.executeQuery(PreparedQuery.allRecords(tableName));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error occurred during getting all records from table " + tableName, e);
        }
        return null;
    }

    public void previousWindow() {
        actualOffset = actualOffset - windowSize;
    }

    public void nextWindow() {
        actualOffset = actualOffset + windowSize;
    }

    public boolean firstWindow() {
        return actualOffset == 0;
    }

    public boolean lastWindow() {
        return windowSize > tableSize;
    }

    public boolean isConnectionReady() {
        return connectionReady;
    }

    public int getTableSize() {
        return tableSize;
    }

    public int getActualOffset() {
        return actualOffset;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public StudentFilter getFilter() {
        return filter;
    }

    public void setFilter(StudentFilter filter) {
        actualOffset = 0;
        this.filter = filter;
    }
}
