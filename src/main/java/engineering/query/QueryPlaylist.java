package engineering.query;

import model.Playlist;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class QueryPlaylist {

    private static final Logger logger = Logger.getLogger(QueryPlaylist.class.getName());

    private QueryPlaylist(){}

    /** Carica la playlist sul database. */
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String namePlaylist = playlist.getPlaylistName();
        List<String> playlistGenre = playlist.getPlaylistGenre();
        List<Integer> emotional = playlist.getEmotional();

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
            handleException(e);
        }
    }


    /** Utilizzata al momento dell'inserimento per controllare se un link è già all'interno del db
     * SELECT * FROM all_playlist WHERE link = '%s' */
    public static ResultSet searchPlaylistLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_LINK_QUERY, link);
        return stmt.executeQuery(sql);
    }

    /** Cerca la parola
     * @param searchTerm passata come argomento */
    public static ResultSet searchPlaylistTitle(Statement stmt, String searchTerm) throws SQLException {
        String word = "%" + searchTerm + "%";
        String sql = String.format(Queries.SELECT_SEARCH_PLAYLIST, word);
        return stmt.executeQuery(sql);
    }

    /** Ritorna una lista di playlist che combaciano con i generi musicali selezionati */
    public static ResultSet searchPlaylistsByFilter(Statement stmt, Playlist playlist) throws SQLException {

        //String emotional = buildEmotionalQueryString(playlist.getEmotional());

        if(playlist.getEmotional() == null){
            playlist.setEmotional(List.of(0,0,0,0));
        }

        String genre = buildGenresQueryString(playlist.getPlaylistGenre());
        genre = genre.replace(" ", "").replace(",", "");


        String sql = String.format(Queries.SELECT_SEARCH_PLAYLISTS_BY_FILTER,
                "%" + playlist.getPlaylistName() + "%",
                playlist.getEmotional().get(0),
                playlist.getEmotional().get(1),
                playlist.getEmotional().get(2),
                playlist.getEmotional().get(3),
                (genre.charAt(0) == '1') ? 1 : 0, // Pop
                (genre.charAt(1) == '1') ? 1 : 0, // Indie
                (genre.charAt(2) == '1') ? 1 : 0, // Classic
                (genre.charAt(3) == '1') ? 1 : 0, // Rock
                (genre.charAt(4) == '1') ? 1 : 0, // Electronic
                (genre.charAt(5) == '1') ? 1 : 0, // House
                (genre.charAt(6) == '1') ? 1 : 0, // HipHop
                (genre.charAt(7) == '1') ? 1 : 0, // Jazz
                (genre.charAt(8) == '1') ? 1 : 0, // Acoustic
                (genre.charAt(9) == '1') ? 1 : 0, // REB
                (genre.charAt(10) == '1') ? 1 : 0, // Country
                (genre.charAt(11) == '1') ? 1 : 0  // Alternative
        );
        return stmt.executeQuery(sql);
    }

    /** Recupera tutta la playlist_utente, va usata con retriveGenrePlaylist per ottenere
     * i generi musicali delle playlist caricate */
    public static ResultSet retrivePlaylistClientByUsername(Statement stmt, String username) throws SQLException {
        String sql = String.format(Queries.SELECT_PLAYLIST_BY_USER,username);
        return stmt.executeQuery(sql);
    }

    /** Recupera tutta la playlist_utente, va usata con retriveGenrePlaylist per ottenere
     * i generi musicali delle playlist caricate */
    public static ResultSet retrievePlaylistClientByEmail(Statement stmt, String email) throws SQLException {
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

    public static ResultSet searchPlaylistsByGenre(Statement stmt, Playlist p) throws SQLException {

        String genre = buildGenresQueryString(p.getPlaylistGenre());
        genre = genre.replace(" ", "").replace(",", "");

        String sql = String.format(Queries.SELECT_SEARCH_BY_GENRE,
                "%" + p.getPlaylistName() + "%",
                (genre.charAt(0) == '1') ? 1 : 0, // Pop
                (genre.charAt(1) == '1') ? 1 : 0, // Indie
                (genre.charAt(2) == '1') ? 1 : 0, // Classic
                (genre.charAt(3) == '1') ? 1 : 0, // Rock
                (genre.charAt(4) == '1') ? 1 : 0, // Electronic
                (genre.charAt(5) == '1') ? 1 : 0, // House
                (genre.charAt(6) == '1') ? 1 : 0, // HipHop
                (genre.charAt(7) == '1') ? 1 : 0, // Jazz
                (genre.charAt(8) == '1') ? 1 : 0, // Acoustic
                (genre.charAt(9) == '1') ? 1 : 0, // REB
                (genre.charAt(10) == '1') ? 1 : 0, // Country
                (genre.charAt(11) == '1') ? 1 : 0  // Alternative
        );

        return stmt.executeQuery(sql);
    }

    public static ResultSet searchPlaylistsByEmotional(Statement stmt, Playlist p) throws SQLException {
        String sql = String.format(Queries.SELECT_SEARCH_BY_EMOTIONAL,
                "%" + p.getPlaylistName() + "%",
                p.getEmotional().get(0),
                p.getEmotional().get(1),
                p.getEmotional().get(2),
                p.getEmotional().get(3));

        return stmt.executeQuery(sql);
    }

    /** Non dovrebbe servire */
    public static ResultSet retrieveIDbyEmail(Statement stmt, String email) throws SQLException {
        String sql = String.format(Queries.SELECT_ID_BY_EMAIL, email);
        return stmt.executeQuery(sql);
    }

    /** Genera un unica stringa per i quattro valori di slider */
    private static String buildEmotionalQueryString(List<Integer> emotional) {
        StringBuilder query = new StringBuilder();

        for (Integer slider : emotional) {
            query.append(slider).append(",");
        }

        // Rimuovi l'ultima virgola
        if (!query.isEmpty()) {
            query.setLength(query.length() - 1);
        }

        return query.toString();
    }

    /**Genera una stringa corretta per effettuare la query, impostando correttamente il true o false dei generi musicali
     * la crea per un insert (0, 1, ...) se bisogna usarla per i search attenzione a spazi e virgole */
    private static String buildGenresQueryString(List<String> generiMusicali) {
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};
        StringBuilder query = new StringBuilder();

        if(generiMusicali == null){
            query.append("0, ".repeat(genres.length));
        } else {
            for (String genere : genres) {
                query.append(generiMusicali.contains(genere) ? "1, " : "0, ");
            }
        }

        // Rimuovi l'ultima virgola
        if (!query.isEmpty()) {
            query.setLength(query.length() - 2);
        }

        return query.toString();
    }

    private static void handleException(Exception e) {
        logger.severe(e.getMessage());
    }


}
