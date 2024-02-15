package engineering.dao;

import engineering.exceptions.*;
import engineering.others.Connect;
import engineering.query.QueryLogin;

import view.first.utils.GenreManager;

import model.*;

import java.sql.*;

import java.util.*;
import java.util.logging.Logger;

public class ClientDAOMySQL implements ClientDAO {

    private static final Logger logger = Logger.getLogger(ClientDAOMySQL.class.getName());
    private static final String USERNAME = "username";

    /** Metodo per inserire un User nel database al momento della registrazione
     * viene effettuato il controllo sulla email scelta e sull'username scelto */
    public void insertClient(Login registration) throws EmailAlreadyInUse, UsernameAlreadyInUse{

        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = registration.getEmail();
            rs = QueryLogin.loginUser(stmt, email);
            assert rs != null;
            if (rs.next()) {
                throw new EmailAlreadyInUse();
            }
            rs.close();

            String username = registration.getUsername();
            rs = QueryLogin.loginUserByUsername(stmt, username);
            assert rs != null;
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

    public Client loadClient(Login login) throws UserDoesNotExist{

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

            assert resultSet != null;
            if (resultSet.next()) {
                username = resultSet.getString(USERNAME);
                email = resultSet.getString("email");
                supervisor = resultSet.getBoolean("supervisor");
            } else {
                throw new UserDoesNotExist();
            }

            resultSet = QueryLogin.retrivePrefByEmail(stmt, login.getEmail());

            if (resultSet.next()) {
                preferences = GenreManager.retriveGenre(resultSet);
            }

        } catch(SQLException e){
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,resultSet);
        }

        if(supervisor){
            return new Supervisor(username,email,preferences);

        } else {
            return new User(username,email,preferences);
        }
    }

    public Client retrieveClientByUsername(String username) throws UserDoesNotExist {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        String email = "";
        boolean supervisor = false;
        List<String> preferences = new ArrayList<>();

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            rs = QueryLogin.loginUserByUsername(stmt, username);

            assert rs != null;
            if (rs.next()) {
                username = rs.getString(USERNAME);
                email = rs.getString("email");
                supervisor = rs.getBoolean("supervisor");
            } else {
                throw new UserDoesNotExist();
            }

            rs = QueryLogin.retrivePrefByEmail(stmt, email);

            if (rs.next()) {
                preferences = GenreManager.retriveGenre(rs);
            }

        } catch(SQLException e){
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }

        if(supervisor){
            return new Supervisor(username,email,preferences);
        } else {
            return new User(username,email,preferences);
        }
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

    public void updateGenreClient(Client client) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            QueryLogin.uploadGeneriMusicali(stmt,client.getEmail(),client.getPreferences());

        } catch (SQLException e) {
            // Gestisci l'eccezione
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }
    }

    public void tryCredentialExisting(Login login) throws EmailAlreadyInUse, UsernameAlreadyInUse{

        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();
            rs = QueryLogin.searchEmail(stmt, login.getEmail());

            if (rs.next()) {
                throw new EmailAlreadyInUse();
            }

            rs = QueryLogin.searchUsername(stmt, login.getUsername());

            if (rs.next()) {
                throw new UsernameAlreadyInUse();
            }

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,rs);
        }
    }

    private void handleDAOException(Exception e) {
        logger.severe(e.getMessage());
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


}
