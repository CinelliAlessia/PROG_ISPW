package engineering.dao;

import engineering.others.Printer;
import engineering.others.Connect;
import engineering.query.QueryNotice;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAOMySQL implements NoticeDAO{


    public void addNotice(Notice notice) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            QueryNotice.addNotice(stmt, notice);

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }

    }

    public void deleteNotice(Notice notice) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            QueryNotice.removeNotice(stmt, notice);

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }
    }


    public List<Notice> retrieveNotice(Client user) {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        List<Notice> noticeList = new ArrayList<>();


        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            rs = QueryNotice.retriveNotice(stmt, user.getUsername());

            while (true) {
                assert rs != null;
                if (!rs.next()) break;

                String title = rs.getString("title");
                String body = rs.getString("body");
                String author = rs.getString("username");

                Notice notice = new Notice(title,body,author);
                noticeList.add(notice);
            }
            rs.close();

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }
        return noticeList;
    }

    /** Metodo utilizzato per chiudere le risorse utilizzate */
    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }

    /** Metodo utilizzato per notificare SQLException */
    private void handleDAOException(Exception e) {
        Printer.errorPrint(String.format("NoticeDAOMySQL: %s", e.getMessage()));
    }

}
