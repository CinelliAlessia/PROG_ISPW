package engineering.dao;

import engineering.exceptions.UsernameAlreadyInUse;
import engineering.others.Connect;
import engineering.exceptions.EmailAlreadyInUse;
import engineering.others.GenreManager;
import engineering.query.QueryLogin;
import model.Supervisor;
import model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOMySQL implements UserDAO {

    /** Metodo per inserire un User nel database al momento della registrazione
    * viene effettuato il controllo sulla email scelta e sull'username scelto*/
    public boolean insertUser(User user) {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;
        boolean result;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = user.getEmail();
            rs = QueryLogin.loginUser(stmt, email);
            if (rs.next()) {
                throw new EmailAlreadyInUse("This email is already in use!");
            }
            rs.close();

            String username = user.getUsername();
            rs = QueryLogin.loginUserBUsername(stmt, username);
            if (rs.next()) {
                throw new UsernameAlreadyInUse("This username is already in use!");
            }
            rs.close();

            QueryLogin.registerUser(stmt, user);

            result = true;

        } catch (SQLException | EmailAlreadyInUse | UsernameAlreadyInUse e) {
            // Gestisci l'eccezione
            e.fillInStackTrace();
            result = false;

        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                // Gestisci l'eccezione
                e.fillInStackTrace();
            }
        }
        return result;
    }

    public User loadUser(String userEmail){
        Statement stmt;
        Connection conn;
        User user;

        String username = "";
        String email = "";
        String password = "";
        boolean supervisor = false;

        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        List<String> preferences = new ArrayList<>();

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            resultSet = QueryLogin.loginUser(stmt, userEmail);

            if (resultSet.next()) {
                username = resultSet.getString("username");
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                supervisor = resultSet.getBoolean("supervisor");
            }
            resultSet.close();

            resultSet2 = QueryLogin.retrivePrefByEmail(stmt, userEmail);

            if (resultSet2.next()) {
                preferences = GenreManager.retriveGenre(resultSet2);
            }
            System.out.println("preferenze in load user " + preferences);

        } catch(SQLException e){
            e.fillInStackTrace();
        } finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(resultSet2 != null){
                    resultSet2.close();
                }
            } catch(SQLException e){
                e.fillInStackTrace();
            }

            System.out.println("Supervisore: " + supervisor);

            if(supervisor){
                user = new Supervisor(username, email, password, preferences);
                System.out.println("UserDao Supervisore");
            } else {
                user = new User(username, email, password, preferences);
            }
        }
        return user;
    }

    public void retrieveUserByUsername(String userName) {
        // TODO -> LA FACCIO QUANDO SERVE
    }

    public String getPasswordByEmail(String email) {
        Statement stmt = null;
        Connection conn;
        String pw = null;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            rs = QueryLogin.getUserPassword(stmt, email);

            if (rs.next()) {
                pw = rs.getString("password");
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
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                // Gestisci l'eccezione
                e.fillInStackTrace();
            }
        }

        return pw; // Se non trovi una corrispondenza
    }

    public void updateGenreUser(String email, List<String> preferences) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            QueryLogin.uploadGeneriMusicali(stmt,email,preferences);

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
    }
}
