package model;

/**
 * Created by Matus Cuper on 8.4.2017.
 *
 * This class grouping used queries in application
 */
class PreparedQuery {

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

    static String mainTable() {
        return "SELECT s.student_id, s.name, s.surname, s.birth_at,\n" +
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
                "OFFSET ?\n" +
                "LIMIT ?;";
    }
}
