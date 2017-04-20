package model;

/**
 * Created by Matus Cuper on 8.4.2017.
 *
 * This class grouping used queries in application
 */
class PreparedQuery {

    // 1 - String: students name
    // 2 - String: students surname
    // 3 - Date: students born after
    // 4 - Date: students born before
    // 5 - Double: students marks average greater than
    // 6 - Double: students marks average lower than
    // 7 - Integer: students registrations count greater than
    // 8 - Integer: students registrations count lower than
    // 9 - Integer: offset
    // 10- Integer: limit
    static final String mainView = "" +
            "SELECT *\n" +
            "FROM main_table_1 m\n" +
            "WHERE m.name LIKE ?\n" +
            "AND m.surname LIKE ?\n" +
            "AND m.birth_at BETWEEN ? AND ?\n" +
            "AND m.gss_avg BETWEEN ? AND ?\n" +
            "AND m.r_count BETWEEN ? AND ?\n" +
            "OFFSET ?\n" +
            "LIMIT ?;";

    static final String mainTable = "" +
            "SELECT *\n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT s.student_id, s.name, s.surname, s.birth_at,\n" +
            "\t\tCOALESCE(gss.avg, 5.00) AS gss_avg,\n" +
            "\t\tCOALESCE(a.count, 0) AS a_count,\n" +
            "\t\tCOALESCE(r.count, 0) AS r_count,\n" +
            "\t\tCOALESCE(g.count, 0) AS g_count_all,\n" +
            "\t\tCOALESCE(g.count_success, 0) AS g_count_success\n" +
            "\tFROM students s\n" +
            "\tLEFT JOIN\n" +
            "\t\t(\n" +
            "\t\tSELECT gss.student_id, ROUND(AVG(gss.mark), 2) AS avg\n" +
            "\t\tFROM graduations_from_ss gss\n" +
            "\t\tGROUP BY gss.student_id\n" +
            "\t\t) gss ON s.student_id = gss.student_id\n" +
            "\tLEFT JOIN\n" +
            "\t\t(\n" +
            "\t\tSELECT a.student_id, COUNT(*)\n" +
            "\t\tFROM awards a\n" +
            "\t\tGROUP BY a.student_id\n" +
            "\t\t) a ON s.student_id = a.student_id\n" +
            "\tLEFT JOIN\n" +
            "\t\t(\n" +
            "\t\tSELECT r.student_id, COUNT(*)\n" +
            "\t\tFROM registrations r\n" +
            "\t\tGROUP BY r.student_id\n" +
            "\t\t) r ON s.student_id = r.student_id\n" +
            "\tLEFT JOIN\n" +
            "\t\t(\n" +
            "\t\tSELECT g.student_id, COUNT(*),\n" +
            "\t\tSUM(CASE WHEN g.graduated = TRUE THEN 1 ELSE 0 END) AS count_success\n" +
            "\t\tFROM graduations g\n" +
            "\t\tGROUP BY g.student_id\n" +
            "\t\t) g ON s.student_id = g.student_id\n" +
            "\tWHERE s.name LIKE ?\n" +
            "\tAND s.surname LIKE ?\n" +
            "\tAND s.birth_at BETWEEN ? AND ?\n" +
            "\t) nt\n" +
            "WHERE nt.gss_avg BETWEEN ? AND ?\n" +
            "AND nt.r_count BETWEEN ? AND ?\n" +
            "OFFSET ?\n" +
            "LIMIT ?;";

    static final String studentById = "SELECT s.*, ss.name AS secondary_school_name, ss.address AS secondary_school_address\n" +
                "FROM students s \n" +
                "LEFT JOIN secondary_schools ss ON s.secondary_school_id = ss.secondary_school_id\n" +
                "WHERE s.student_id = ?;";

    static final String graduationsFromSSByStudent = "SELECT gss.*, s.name\n" +
                "FROM graduations_from_ss gss\n" +
                "JOIN subjects s ON gss.subject_id = s.subject_id\n" +
                "WHERE gss.student_id = ?;";

    static final String awardsByStudent = "SELECT a.*, al.name AS award_level_name, an.name AS award_name_name\n" +
                "FROM awards a\n" +
                "JOIN award_levels al ON a.award_level_id = al.award_level_id\n" +
                "JOIN award_names an ON a.award_name_id = an.award_name_id\n" +
                "WHERE a.student_id = ?;";

    static final String graduationsByStudent = "SELECT g.graduation_id, g.started_at, g.finished_at, g.graduated, nt.*\n" +
                "FROM graduations g\n" +
                "JOIN\n" +
                "\t(\n" +
                "\tSELECT fos.*, f.name AS field_of_study_name,\n" +
                "\t\tu.name AS university_name, u.address AS university_address\n" +
                "\tFROM fos_at_universities fos\n" +
                "\tJOIN fields_of_study f ON fos.field_of_study_id = f.field_of_study_id\n" +
                "\tJOIN universities u ON fos.university_id = u.university_id\n" +
                "\t) nt ON g.fos_at_university_id = nt.fos_at_university_id\n" +
                "WHERE g.student_id = ?;\n";

    static final String registrationsByStudent = "SELECT r.*, s.name, nt.*\n" +
                "FROM registrations r\n" +
                "JOIN statuses s ON r.status_id = s.status_id\n" +
                "JOIN\n" +
                "\t(\n" +
                "\tSELECT fos.*, f.name AS field_of_study_name,\n" +
                "\t\tu.name AS university_name, u.address AS university_address\n" +
                "\tFROM fos_at_universities fos\n" +
                "\tJOIN fields_of_study f ON fos.field_of_study_id = f.field_of_study_id\n" +
                "\tJOIN universities u ON fos.university_id = u.university_id\n" +
                "\t) nt ON r.fos_at_university_id = nt.fos_at_university_id\n" +
                "WHERE r.student_id = ?;";

    static final String deleteStudentById = "DELETE FROM students WHERE student_id = ?;";

    static final String deleteGraduationsFromSSByStudentId = "DELETE FROM graduations_from_ss WHERE student_id = ?;";

    static final String deleteAwardsByStudentId = "DELETE FROM awards WHERE student_id = ?;";

    static final String deleteGraduationsByStudentId = "DELETE FROM graduations WHERE student_id = ?;";

    static final String deleteRegistrationsByStudentId = "DELETE FROM registrations WHERE student_id = ?;";

    static final String insertStudent = "INSERT INTO students (secondary_school_id, name, surname, birth_at, " +
            "address, email, phone, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    static final String insertGraduationFromSS = "INSERT INTO graduations_from_ss (student_id, subject_id, " +
            "graduated_at, mark) VALUES (?, ?, ?, ?);";

    static final String insertAward = "INSERT INTO awards (award_level_id, award_name_id, student_id, awarded_at) " +
            "VALUES (?, ?, ?, ?);";

    static final String insertGraduation = "INSERT INTO graduations (fos_at_university_id, student_id, started_at, " +
            "finished_at, graduated) VALUES (?, ?, ?, ?, ?);";

    static final String insertRegistration = "INSERT INTO registrations (fos_at_university_id, student_id, " +
            "status_id, changed_at) VALUES (?, ?, ?, ?);";

    static final String selectFosAtUniversityId = "SELECT fos_at_university_id FROM fos_at_universities WHERE " +
            "university_id = ? AND field_of_study_id = ?;";

    static final String insertFosAtUniversityId = "INSERT INTO fos_at_universities (university_id, " +
            "field_of_study_id) VALUES (?, ?);";

    static final String updateStudent = "UPDATE students SET (secondary_school_id, name, surname, birth_at, address, " +
            "email, phone, zip_code) = (?, ?, ?, ?, ?, ?, ?, ?) WHERE student_id = ?;";

    static String refreshView = "REFRESH MATERIALIZED VIEW main_table_1;";

    private PreparedQuery() {}

    /**
     * It is impossible to create {@link java.sql.PreparedStatement} with tableName as parameter,
     * because of SQL injection, so I alternatively create SQL statement as String, which return
     * row count in given table
     * @param tableName name of table in database to count rows
     * @return SQL query, which counts rows in given table
     */
    static String countRows(String tableName) {
        return "SELECT COUNT(*) FROM " + tableName + ";";
    }

    static String allRecords(String tableName) {
        return "SELECT * FROM " + tableName + ";";
    }
}
