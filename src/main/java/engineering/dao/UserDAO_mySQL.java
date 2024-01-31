package engineering.dao;

import engineering.others.Connect;
import engineering.exceptions.EmailAlreadyInUse;
import engineering.query.QueryLogin;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO_mySQL implements UserDAO{

    // Metodo per inserire un User nel database al momento della registrazione
    public void insertUser(User user) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = user.getEmail();
            ResultSet rs = QueryLogin.loginUser(stmt, email);
            if (rs.next()) {
                throw new EmailAlreadyInUse("This email is already in use!");
            }
            rs.close();
            QueryLogin.registerUser(stmt, user);
        } catch (SQLException | EmailAlreadyInUse e) {
            // Gestisci l'eccezione
            e.fillInStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // Gestisci l'eccezione
                e.fillInStackTrace();
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
                String pw = rs.getString("password");
                rs.close();
                return pw;
            }

        } catch (SQLException e) {
            // Gestisci l'eccezione
            e.fillInStackTrace();
        } finally {
            // Chiudi le risorse (ResultSet, Statement, Connection)
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                // Gestisci l'eccezione
                e.fillInStackTrace();
            }
        }

        return null; // Se non trovi una corrispondenza
    }

    /**
     * @param userInstance
     */
    @Override
    public void deleteUser(User userInstance) {
        // TODO document why this method is empty
    }

    /**
     * @param userName
     */
    @Override
    public void retrieveUserByUserName(String userName) {
        // TODO document why this method is empty
    }

    /**
     * @param userId
     */
    @Override
    public void retrieveUserByUserId(String userId) {
        // TODO document why this method is empty
    }

    public static User loadUser(String userEmail) throws SQLException {
        Statement stmt = null;
        Connection conn;
        User user;

        String username = "", email="", password="";
        ResultSet resultSet = null, resultSet2 = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            resultSet = QueryLogin.loginUser(stmt, userEmail);

            if (resultSet.next()) {
               username = resultSet.getString("username");
               email = resultSet.getString("email");
               password = resultSet.getString("password");
            }
            resultSet.close();

            resultSet2 = QueryLogin.retrivePrefByEmail(stmt, userEmail);
            ArrayList<String> preferences = new ArrayList<>();

            if (resultSet2.next()) {
                // Aggiungi i generi musicali all'ArrayList solo se sono impostati a true
                if (resultSet2.getBoolean("Pop")) preferences.add("Pop");
                if (resultSet2.getBoolean("Indie")) preferences.add("Indie");
                if (resultSet2.getBoolean("Classic")) preferences.add("Classic");
                if (resultSet2.getBoolean("Rock")) preferences.add("Rock");
                if (resultSet2.getBoolean("Electronic")) preferences.add("Electronic");
                if (resultSet2.getBoolean("House")) preferences.add("House");
                if (resultSet2.getBoolean("HipHop")) preferences.add("Hip Hop");
                if (resultSet2.getBoolean("Jazz")) preferences.add("Jazz");
                if (resultSet2.getBoolean("Acoustic")) preferences.add("Acoustic");
                if (resultSet2.getBoolean("REB")) preferences.add("REB");
                if (resultSet2.getBoolean("Country")) preferences.add("Country");
                if (resultSet2.getBoolean("Alternative")) preferences.add("Alternative");
            }
            resultSet2.close();

            user = new User(username, email, password, preferences);

        } finally {
            assert resultSet != null;
            resultSet.close();
            assert resultSet2 != null;
            resultSet2.close();
        }
        // Chiudi gli Statement e la connessione
        return user;
    }
}
