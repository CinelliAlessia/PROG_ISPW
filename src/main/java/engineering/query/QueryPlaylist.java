package engineering.query;

import model.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryPlaylist {

    private QueryPlaylist(){}

    /** Carica sul Database, sia su all_playlist sia su playlist_utente, la playlist passata
     * come argomento. Aggiungendo anche alla tabella generi_musicali, i generi della playlist
     * mantenendo l'associazione tramite un id */
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String namePlaylist = playlist.getPlaylistName();
        List<String> playlistGenre = playlist.getPlaylistGenre();

        int idPlaylist = playlist.getId();

        // Inserisce i generi musicali nella tabella 'generi_musicali'
        insertGeneriMusicali(stmt, idPlaylist, email, playlistGenre);

        String insertAllPlaylistStatement = String.format(Queries.INSERT_ALL_PLAYLIST_QUERY, namePlaylist, link);
        stmt.executeUpdate(insertAllPlaylistStatement);

        String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_QUERY, email, username, namePlaylist, link, idPlaylist);
        stmt.executeUpdate(insertPlaylistStatement);
    }

    /** Viene utilizzata da insertPlaylist, mantiene l'associazione tra playlist e i suoi generi
     * tramite un id */
    public static void insertGeneriMusicali(Statement stmt, int id, String userEmail, List<String> generiMusicali) throws SQLException {
        // Costruisci la query di inserimento
        StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_PLAYLIST_QUERY, buildGenresQueryString(id, generiMusicali, userEmail)));

        // Esegui la query
        stmt.executeUpdate(query.toString());
    }

    /**Genera una stringa corretta per effettuare la query, impostando correttamente il true o false dei generi musicali */
    private static String buildGenresQueryString(int id, List<String> generiMusicali, String userEmail) {
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};
        StringBuilder query = new StringBuilder();

        //query.append(id).append(", ");  // Aggiungi l'ID alla query
        query.append(String.format("%d, ", id));  // Aggiungi l'ID alla query

        for (String genere : genres) {
            query.append(generiMusicali.contains(genere) ? "1, " : "0, ");
        }

        query.append(String.format("'%s'", userEmail));

        return query.toString();
    }

    /** inutile */
    public static ResultSet searchPlaylistLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_LINK_QUERY, link);
        return stmt.executeQuery(sql);
    }

    /** Recupera tutta la playlist_utente, va usata con retriveGenrePlaylist per ottenere
     * i generi musicali delle playlist caricate */
    public static ResultSet retrivePlaylistUser(Statement stmt) throws SQLException {
        String sql = String.format(Queries.SELECT_PLAYLIST_BY_USER);
        return stmt.executeQuery(sql);
    }

    /** Recupera da generi_musicali, i generi musicali della playlist passata come id */
    public static ResultSet retriveGenrePlaylist(Statement stmt,int id) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRED_USER_QUERY,id);
        return stmt.executeQuery(sql);
    }
}
