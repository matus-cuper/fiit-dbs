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
}
