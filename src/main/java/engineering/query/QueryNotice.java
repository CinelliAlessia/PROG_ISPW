package engineering.query;

import engineering.others.CLIPrinter;
import model.Notice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class QueryNotice {

    private QueryNotice(){}

    public static void addNotice(Statement stmt, Notice notice) throws SQLException {
        String query = String.format(Queries.INSERT_NOTICE_USER, notice.getUsernameAuthor(), notice.getTitle(), notice.getBody());
        stmt.executeUpdate(query);
    }

    public static void removeNotice(Statement stmt, Notice notice) throws SQLException {
        String query = String.format(Queries.REMOVE_NOTICE_CLIENT, notice.getUsernameAuthor(), notice.getBody());
        stmt.executeUpdate(query);
    }

    public static ResultSet retriveNotice(Statement stmt, String username) {
        try{
            String query = String.format(Queries.SELECT_NOTICE_USER, username);
            return stmt.executeQuery(query);
        } catch (SQLException e){
            handleException(e);
            return null;
        }
    }

    /** Solo SQLException */
    private static void handleException(Exception e) {
        CLIPrinter.errorPrint(e.getMessage());
    }

}
