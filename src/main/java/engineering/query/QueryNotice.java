package engineering.query;

import engineering.others.Printer;
import model.Notice;

import java.sql.*;

public class QueryNotice {

    private QueryNotice(){}

    public static void addNotice(Statement stmt, Notice notice) throws SQLException {
        String query = String.format(Queries.INSERT_NOTICE_USER, notice.getEmail(), notice.getTitle(), notice.getBody());
        stmt.executeUpdate(query);
    }

    public static void removeNotice(Statement stmt, Notice notice) throws SQLException {
        String query = String.format(Queries.REMOVE_NOTICE_CLIENT, notice.getEmail(), notice.getBody());
        stmt.executeUpdate(query);
    }

    public static ResultSet retriveNotice(Statement stmt, String email) {
        try{
            String query = String.format(Queries.SELECT_NOTICE_USER, email);
            return stmt.executeQuery(query);
        } catch (SQLException e){
            handleException(e);
            return null;
        }
    }

    /** Solo SQLException */
    private static void handleException(Exception e) {
        Printer.errorPrint(e.getMessage());
    }

}
