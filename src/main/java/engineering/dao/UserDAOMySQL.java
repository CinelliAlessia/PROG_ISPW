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
    public void insertUser(Login registration) throws EmailAlreadyInUse, UsernameAlreadyInUse{

        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = registration.getEmail();
            rs = QueryLogin.loginUser(stmt, email);
            if (rs.next()) {
                throw new EmailAlreadyInUse();
            }
            rs.close();

            String username = registration.getUsername();
            rs = QueryLogin.loginUserBUsername(stmt, username);
            if (rs.next()) {
                throw new UsernameAlreadyInUse();
            }
            rs.close();

            QueryLogin.registerUser(stmt, registration);

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }
    }

    public Client loadUser(Login login) throws UserDoesNotExist{

        Statement stmt = null;
        ResultSet resultSet = null;

        Connection conn;

        String username = "";
        String email = "";
        boolean supervisor = false;

        List<String> preferences = new ArrayList<>();

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            resultSet = QueryLogin.loginUser(stmt, login.getEmail());

            if (resultSet.next()) {
                username = resultSet.getString("username");
                email = resultSet.getString("email");
                supervisor = resultSet.getBoolean("supervisor");
            } else {
                throw new UserDoesNotExist();
            }

            resultSet = QueryLogin.retrivePrefByEmail(stmt, login.getEmail());

            if (resultSet.next()) {
                preferences = GenreManager.retriveGenre(resultSet);
            }
            System.out.println("preferenze in load user " + preferences);

        } catch(SQLException e){
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,resultSet);
        }

        if(supervisor){
            System.out.println("UserDao Supervisore");
            return new Supervisor(username,email,preferences);

        } else {
            System.out.println("UserDao User");
            return new User(username,email,preferences);
        }
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
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }
        return pw; // Se non trovi una corrispondenza
    }

    public void updateGenreUser(Client client) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            System.out.println("CIAOOOO " + client.getEmail());
            QueryLogin.uploadGeneriMusicali(stmt,client.getEmail(),client.getPreferences());

        } catch (SQLException e) {
            // Gestisci l'eccezione
            handleDAOException(e);
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
            handleDAOException(e);
        }
    }

    private void handleDAOException(Exception e) {
        e.printStackTrace();
    }

}
