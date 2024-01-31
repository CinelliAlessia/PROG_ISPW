package engineering.query;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryLogin {
    private QueryLogin() {
    }

    /** Carica nel database un nuovo utente e i suoi generi musicali preferiti */
    public static void registerUser(Statement stmt, User user) throws SQLException {
        String name = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        // Esegui prima l'inserimento nella tabella 'user'
        String insertUserStatement = String.format(Queries.INSERT_USER_QUERY, email, name, password);
        stmt.executeUpdate(insertUserStatement);

        // Poi inserisci i generi musicali nella tabella 'generi_musicali'
        insertGeneriMusicali(stmt, email, user.getPref());
    }

    /** Inserisce i generi musicali preferiti dall'utente, utilizzata al momento della registrazione dell'utente*/
    public static void insertGeneriMusicali(Statement stmt, String userEmail, List<String> generiMusicali) throws SQLException {
        // Costruisci la query di inserimento
        StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_QUERY, buildGenresQueryString(generiMusicali, userEmail)));

        // Esegui la query
        stmt.executeUpdate(query.toString());
    }

    /**  */
    public static void uploadGeneriMusicali(Statement stmt, String userEmail, List<String> generiMusicali) throws SQLException {
        /* String updateQuery = Queries.UPDATE_GENERI_MUSICALI_QUERY;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Imposta i valori per i generi musicali
            for (int i = 1; i <= generiMusicali.size(); i++) {
                preparedStatement.setInt(i, generiMusicali.get(i - 1).equals("1") ? 1 : 0);
            }
            preparedStatement.setString(14, userEmail); // email

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
*/
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

    // Query per prendere la email, utilizzata nella registrazione per vedere se l'email è già registrata
    public static ResultSet loginUser(Statement stmt, String email) throws SQLException {
        String sql = String.format(Queries.SELECT_EMAIL_USER_QUERY, email);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retrivePrefByEmail(Statement stmt, String email) throws SQLException{
        String sql = String.format(Queries.SELECT_GENRED_USER_QUERY, email);
        return stmt.executeQuery(sql);
    }

    // Query per prendere la password della email passata come argomento
    public static ResultSet getUserPassword(Statement stmt, String email) throws SQLException {
        String query = String.format(Queries.SELECT_PASSWORD_QUERY, email);
        return stmt.executeQuery(query);
    }

}
