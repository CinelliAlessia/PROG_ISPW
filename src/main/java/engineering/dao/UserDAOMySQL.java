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

public class UserDAOMySQL implements UserDAO{

    // Metodo per inserire un User nel database al momento della registrazione
    public void insertUser(User user) {
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;
        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String email = user.getEmail();
            rs = QueryLogin.loginUser(stmt, email);
            if (rs.next()) {
                throw new EmailAlreadyInUse("This email is already in use!");
            }
            rs.close();
            QueryLogin.registerUser(stmt, user);

        } catch (SQLException | EmailAlreadyInUse e) {
            // Gestisci l'eccezione
            e.printStackTrace();

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
                e.printStackTrace();
            }
        }
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
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        return pw; // Se non trovi una corrispondenza
    }

    public User loadUser(String userEmail){
        Statement stmt;
        Connection conn;
        User user;

        String username = "";
        String email = "";
        String password = "";
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        ArrayList<String> preferences = new ArrayList<>();

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

            if (resultSet2.next()) {

                // Aggiungi i generi musicali alla List solo se sono impostati a true
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

                ///preferences = genrePlaylist(resultSet2);
                //System.out.println("AAAAAAAAAAAAAAAAAAAAAA " + preferences);

            }
            resultSet.close();

            System.out.println("preferenze in load user " + preferences);

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(resultSet2 != null){
                    resultSet2.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }

            user = new User(username, email, password, preferences);

        }
        // Chiudi gli Statement e la connessione
        return user;
    }

    public ArrayList<String> genrePlaylist(ResultSet rs) throws SQLException {
        ArrayList<String> preferences = new ArrayList<>();

        // Aggiungi i generi musicali alla List solo se sono impostati a true
        if (rs.getBoolean("Pop")) preferences.add("Pop");
        if (rs.getBoolean("Indie")) preferences.add("Indie");
        if (rs.getBoolean("Classic")) preferences.add("Classic");
        if (rs.getBoolean("Rock")) preferences.add("Rock");
        if (rs.getBoolean("Electronic")) preferences.add("Electronic");
        if (rs.getBoolean("House")) preferences.add("House");
        if (rs.getBoolean("HipHop")) preferences.add("Hip Hop");
        if (rs.getBoolean("Jazz")) preferences.add("Jazz");
        if (rs.getBoolean("Acoustic")) preferences.add("Acoustic");
        if (rs.getBoolean("REB")) preferences.add("REB");
        if (rs.getBoolean("Country")) preferences.add("Country");
        if (rs.getBoolean("Alternative")) preferences.add("Alternative");

        System.out.println("preferenze in genrePlaylist " + preferences);
        return preferences;
    }

    @Override
    public void deleteUser(User userInstance) {
        // TODO document why this method is empty
    }

    @Override
    public void retrieveUserByUserName(String userName) {

    }


}
