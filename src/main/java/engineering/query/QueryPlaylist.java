package engineering.query;

import model.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryPlaylist {

    private QueryPlaylist(){}

    /** Carica sul Database, sia su all_playlist sia su playlist_utente, la playlist passata
     * come argomento. Aggiungendo anche alla tabella generi_musicali_user, i generi della playlist
     * mantenendo l'associazione tramite un id */
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String namePlaylist = playlist.getPlaylistName();
        List<String> playlistGenre = playlist.getPlaylistGenre();

        int approved;
        if(playlist.getApproved()){
            approved = 1;
        } else {
            approved = 0;
        }

        /* Gestione corretta dell'approvazione, se un utente non ha l'approvazione verrà caricata solo sulla sua playlist, altrimenti
        * anche nella playlist globale */
        try{
            // inserimento nella tabella dell'utente
            String insertAllPlaylistStatement = String.format(Queries.INSERT_ALL_PLAYLIST_QUERY, namePlaylist, link, approved);
            stmt.executeUpdate(insertAllPlaylistStatement);

            String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_USER, namePlaylist, email, username, link, approved, buildGenresQueryString(playlistGenre));
            stmt.executeUpdate(insertPlaylistStatement);
        } catch (SQLException e){
            // Dobbiamo annullare i caricamenti.
            e.printStackTrace();
        }
    }

    /** Non dovrebbe servire */
    public static ResultSet retrieveIDbyEmail(Statement stmt, String email) throws SQLException {
        String sql = String.format(Queries.SELECT_ID_BY_EMAIL, email);
        return stmt.executeQuery(sql);
    }

    /** Viene utilizzata da insertPlaylist, mantiene l'associazione tra playlist e i suoi generi
     * tramite un id */
    public static void insertGeneriMusicali(Statement stmt, List<String> generiMusicali) throws SQLException {
        try{
            // Costruisci la query di inserimento
            StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_PLAYLIST, buildGenresQueryString(generiMusicali)));

            // Esegui la query
            stmt.executeUpdate(query.toString());
        } catch (SQLException e){
            e.fillInStackTrace();
        } finally {
            // L'utente non dovrebbe registrars
        }
    }

    /**Genera una stringa corretta per effettuare la query, impostando correttamente il true o false dei generi musicali */
    private static String buildGenresQueryString(List<String> generiMusicali) {
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};
        StringBuilder query = new StringBuilder();

        for (String genere : genres) {
            query.append(generiMusicali.contains(genere) ? "1, " : "0, ");
        }

        // Rimuovi l'ultima virgola
        if (!query.isEmpty()) {
            query.setLength(query.length() - 2);
        }

        return query.toString();
    }

    /** Utilizzata al momento dell'inserimento per controllare se un link è già all'interno del db
     * SELECT * FROM all_playlist WHERE link = '%s' */
    public static ResultSet searchPlaylistLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_LINK_QUERY, link);
        return stmt.executeQuery(sql);
    }

    /** Cerca la parola @param searchTerm passata come argomento */
    public static ResultSet searchPlaylistString(Statement stmt, String searchTerm) throws SQLException {
        String word = "%" + searchTerm + "%";
        System.out.println(word);
        String sql = String.format(Queries.SELECT_SHEARCH_PLAYLIST, word);
        return stmt.executeQuery(sql);
    }


    /** Recupera tutta la playlist_utente, va usata con retriveGenrePlaylist per ottenere
     * i generi musicali delle playlist caricate */
    public static ResultSet retrivePlaylistUserByUsername(Statement stmt, String username) throws SQLException {
        String sql = String.format(Queries.SELECT_PLAYLIST_BY_USER,username);
        return stmt.executeQuery(sql);
    }

    /** Recupera tutta la playlist_utente, va usata con retriveGenrePlaylist per ottenere
     * i generi musicali delle playlist caricate */
    public static ResultSet retrievePlaylistUserByEmail(Statement stmt, String email) throws SQLException {
        String sql = String.format(Queries.SELECT_PLAYLIST_BY_EMAIL,email);
        return stmt.executeQuery(sql);
    }

    /** Recupera da generi_musicali, i generi musicali della playlist passata come id */
    public static ResultSet retrieveGenrePlaylistById(Statement stmt, int id) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRE_USER_QUERY,id);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retrieveGenrePlaylist(Statement stmt, String username) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRE_PLAYLIST, username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retrieveGenrePlaylistByLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRE_PLAYLIST_BY_LINK, link);
        return stmt.executeQuery(sql);
    }

    /** Rimuove la playlist su tutte le tabelle, dal link della playlist
     * */
    public static void removePlaylistByLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.DELETE_PLAYLIST_BY_LINK_PLAYLIST_UTENTE,link);
        stmt.executeUpdate(sql);

        sql = String.format(Queries.DELETE_PLAYLIST_BY_LINK_ALL_PLAYLIST,link);
        stmt.executeUpdate(sql);
    }



    public static void approvePlaylistByLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.UPDATE_APPROVE_PLAYLIST,1,link);
        stmt.executeUpdate(sql);

        sql = String.format(Queries.UPDATE_APPROVE_PLAYLIST_IN_ALL,1,link);
        stmt.executeUpdate(sql);
    }

    public static ResultSet retrievePendingPlaylists(Statement stmt) throws SQLException {
        String sql = String.format(Queries.SELECT_PENDING_PLAYLISTS,0);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retrieveApprovedPlaylists(Statement stmt) throws SQLException {
        String sql = String.format(Queries.SELECT_APPROVED_PLAYLISTS,1);
        return stmt.executeQuery(sql);
    }
}
