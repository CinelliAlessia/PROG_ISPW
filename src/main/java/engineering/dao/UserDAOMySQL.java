package engineering.dao;

import engineering.exceptions.*;
import engineering.others.Connect;
import engineering.query.QueryLogin;

import view.utils.GenreManager;

import model.*;

import java.sql.*;

import java.util.*;

public class UserDAOMySQL implements UserDAO {

    /** Metodo per inserire un User nel database al momento della registrazione
    * viene effettuato il controllo sulla email scelta e sull'username scelto */
    public boolean insertUser(User user) throws EmailAlreadyInUse, UsernameAlreadyInUse{
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;
        boolean result = true;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = user.getEmail();
            rs = QueryLogin.loginUser(stmt, email);
            if (rs.next()) {
                throw new EmailAlreadyInUse();
            }
            rs.close();

            String username = user.getUsername();
            rs = QueryLogin.loginUserBUsername(stmt, username);
            if (rs.next()) {
                throw new UsernameAlreadyInUse();
            }
            rs.close();

            QueryLogin.registerUser(stmt, user);

        } catch (SQLException e) {
            // Gestisci l'eccezione
            e.fillInStackTrace();
            result = false;

        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }
        return result;
    }

    public User loadUser(String userEmail) throws UserDoesNotExist {
        Statement stmt = null;
        ResultSet resultSet = null;

        Connection conn;
        User user;

        String username = "";
        String email = "";
        String password = "";
        boolean supervisor = false;

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
            } else {
                throw new UserDoesNotExist();
            }

            resultSet = QueryLogin.retrivePrefByEmail(stmt, userEmail);

            if (resultSet.next()) {
                preferences = GenreManager.retriveGenre(resultSet);
            }
            System.out.println("preferenze in load user " + preferences);

        } catch(SQLException e){
            e.fillInStackTrace();
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,resultSet);

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

    public User retrieveUserByUsername(String userName) {
        // TODO -> LA FACCIO QUANDO SERVE
        return null;
    }

    public String getPasswordByEmail(String email) throws UserDoesNotExist{
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
            } else {
                throw new UserDoesNotExist();
            }
        } catch (SQLException e) {
            // Gestisci l'eccezione
            e.fillInStackTrace();
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
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
            // Chiusura delle risorse
            closeResources(stmt,null);
        }
    }

    /** Metodo utilizzato per chiudere le risorse utilizzate */
    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
}
