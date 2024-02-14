package engineering.query;

import model.Notice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class QueryNotice {
    private static final Logger logger = Logger.getLogger(QueryNotice.class.getName());

    public static void addNotice(Statement stmt, Notice notice) throws SQLException {
        String query = String.format(Queries.INSERT_NOTICE_USER, notice.getUsernameAuthor(), notice.getTitle(), notice.getBody());
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

    private static void handleException(Exception e) {
        logger.severe(e.getMessage());
    }

}
