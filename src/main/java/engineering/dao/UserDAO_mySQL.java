package engineering.dao;

import engineering.others.Connect;
import engineering.exceptions.EmailAlreadyInUse;
import engineering.query.QueryLogin;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO_mySQL {

    // Metodo per inserire un User nel database
    public static void insertUser(User user) throws ClassNotFoundException, SQLException, EmailAlreadyInUse {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String username = user.getEmail();
            ResultSet rs = QueryLogin.loginUser(stmt, username);
            if (rs.next()) {
                throw new EmailAlreadyInUse("This username is already in use!");
            }

            QueryLogin.registerUser(stmt, user);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public String getPasswordByEmail(String email) {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            ResultSet rs = QueryLogin.getUserPassword(stmt, email);

            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            // Gestisci l'eccezione
            e.printStackTrace();
        } finally {
            // Chiudi le risorse (ResultSet, Statement, Connection)
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Gestisci l'eccezione
                e.printStackTrace();
            }
        }

        return null; // Se non trovi una corrispondenza
    }
}
