package engineering.query;

import engineering.others.Printer;
import model.Login;

import java.sql.*;
import java.util.List;

public class QueryLogin {

    private QueryLogin() {
    }

    /** Carica nel database un nuovo utente e i suoi generi musicali preferiti */
    public static void registerUser(Statement stmt, Login user) {

        String name = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        boolean supervisor = user.isSupervisor();

        int bool = 0;

        if(supervisor){
            bool = 1;
        }

        try {
            // Esegui prima l'inserimento nella tabella 'user'
            String insertUserStatement = String.format(Queries.INSERT_USER, email, name, password, bool);
            stmt.executeUpdate(insertUserStatement);

            // Poi inserisci i generi musicali nella tabella 'generi_musicali'
            insertGeneriMusicali(stmt, email, user.getPreferences());
        } catch (SQLException e){
            handleException(e);
        }

    }

    /** Inserisce i generi musicali preferiti dall'utente, utilizzata al momento della registrazione dell'utente */
    public static void insertGeneriMusicali(Statement stmt, String userEmail, List<String> generiMusicali) {
        try{
            StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_USER, buildGenresQueryString(generiMusicali, userEmail)));
            stmt.executeUpdate(query.toString());
        } catch (SQLException e){
            handleException(e);
        }
    }

    /** Aggiorna i generi musicali preferiti dell'utente */
    public static void uploadGeneriMusicali(Statement stmt, String userEmail, List<String> generiMusicali) {
        try {
            // Costruisci la query di aggiornamento
            String query = String.format(Queries.UPDATE_GENERI_MUSICALI_USER,
                    generiMusicali.contains("Pop") ? 1 : 0,
                    generiMusicali.contains("Indie") ? 1 : 0,
                    generiMusicali.contains("Classic") ? 1 : 0,
                    generiMusicali.contains("Rock") ? 1 : 0,
                    generiMusicali.contains("Electronic") ? 1 : 0,
                    generiMusicali.contains("House") ? 1 : 0,
                    generiMusicali.contains("HipHop") ? 1 : 0,
                    generiMusicali.contains("Jazz") ? 1 : 0,
                    generiMusicali.contains("Acoustic") ? 1 : 0,
                    generiMusicali.contains("REB") ? 1 : 0,
                    generiMusicali.contains("Country") ? 1 : 0,
                    generiMusicali.contains("Alternative") ? 1 : 0,
                    userEmail);

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            handleException(e);
        }
    }

    /** Ritorna un ResultSet contenente tutti i campi di client recuperati tramite la email */
    public static ResultSet loginUser(Statement stmt, String email) {
        try{
            String sql = String.format(Queries.SELECT_USER_BY_EMAIL, email);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public static ResultSet loginUserByUsername(Statement stmt, String username){
        try{
            String sql = String.format(Queries.SELECT_USER_BY_USERNAME, username);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    public static ResultSet retrivePrefByEmail(Statement stmt, String email) throws SQLException{
        String sql = String.format(Queries.SELECT_GENRE_USER_QUERY, email);
        return stmt.executeQuery(sql);
    }

    // Query per prendere la password della email passata come argomento
    public static ResultSet getUserPassword(Statement stmt, String email) throws SQLException {
        String query = String.format(Queries.SELECT_PASSWORD_BY_EMAIL, email);
        return stmt.executeQuery(query);
    }

    public static ResultSet searchEmail(Statement stmt, String email) throws SQLException {
        String query = String.format(Queries.SEARCH_EMAIL, email);
        return stmt.executeQuery(query);
    }

    public static ResultSet searchUsername(Statement stmt, String username) throws SQLException {
        String query = String.format(Queries.SEARCH_USERNAME, username);
        return stmt.executeQuery(query);
    }

    private static String buildGenresQueryString(List<String> generiMusicali, String userEmail) {
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};
        StringBuilder query = new StringBuilder();

        // Aggiungi i valori booleani alla query
        for (String genere : genres) {
            query.append(generiMusicali.contains(genere) ? "1, " : "0, ");
        }

        query.append(String.format("'%s'", userEmail));

        return query.toString();
    }

    /** Solo SQLException */
    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }

}
