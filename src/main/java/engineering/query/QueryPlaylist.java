package engineering.query;

import model.Playlist;

import java.sql.*;
import java.util.List;

public class QueryPlaylist {

    private QueryPlaylist(){}

    /** Carica la playlist sul database. */
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String namePlaylist = playlist.getPlaylistName();
        List<String> playlistGenre = playlist.getPlaylistGenre();
        List<Double> emotional = playlist.getEmotional();

        int approved;
        if(playlist.getApproved()){
            approved = 1;
        } else {
            approved = 0;
        }

        try{
            // inserimento nella tabella dell'utente
            String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_USER, namePlaylist, email, username, link, approved, buildEmotionalQueryString(emotional) ,buildGenresQueryString(playlistGenre));
            stmt.executeUpdate(insertPlaylistStatement);
        } catch (SQLException e){
            e.fillInStackTrace();
        }
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
        String sql = String.format(Queries.SELECT_SEARCH_PLAYLIST, word);
        return stmt.executeQuery(sql);
    }

    /** Ritorna una lista di playlist che combaciano con i generi musicali selezionati */
    public static ResultSet searchPlaylistsByFilter(Statement stmt, Playlist playlist) throws SQLException {

            String emotional = buildEmotionalQueryString(playlist.getEmotional());
            String genre = buildGenresQueryString(playlist.getPlaylistGenre());

            String sql = String.format(Queries.SELECT_SEARCH_PLAYLISTS_BY_FILTER,emotional,",",genre);

            System.out.println(sql); //##################################
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
    }

    public static void approvePlaylistByLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.UPDATE_APPROVE_PLAYLIST,1,link);
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




    /** Genera un unica stringa per i quattro valori di slider */
    private static String buildEmotionalQueryString(List<Double> emotional) {
        StringBuilder query = new StringBuilder();

        for (Double slider : emotional) {
            query.append(slider).append(",");
        }

        // Rimuovi l'ultima virgola
        if (!query.isEmpty()) {
            query.setLength(query.length() - 1);
        }

        return query.toString();
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



}
