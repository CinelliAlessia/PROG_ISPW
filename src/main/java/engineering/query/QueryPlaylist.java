package engineering.query;

import model.Playlist;

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

        try{
            // Inserisce i generi musicali nella tabella 'generi_musicali'
            insertGeneriMusicali(stmt, playlistGenre); //l'id viene inserito automaticamente

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            String insertAllPlaylistStatement = String.format(Queries.INSERT_ALL_PLAYLIST_QUERY, namePlaylist, link);
            stmt.executeUpdate(insertAllPlaylistStatement);

            System.out.println("2");

            String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_QUERY, email, username, namePlaylist, link);
            stmt.executeUpdate(insertPlaylistStatement);


        }
    }

    /** Viene utilizzata da insertPlaylist, mantiene l'associazione tra playlist e i suoi generi
     * tramite un id */
    public static void insertGeneriMusicali(Statement stmt, List<String> generiMusicali) throws SQLException {
        // Costruisci la query di inserimento
        StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_PLAYLIST_QUERY, buildGenresQueryString(generiMusicali)));

        // Esegui la query
        stmt.executeUpdate(query.toString());
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

        System.out.println("1   " + query);


        return query.toString();
    }

    /** Utilizzata al momento dell'inserimento per controllare se un link è già all'interno del db
     * SELECT * FROM all_playlist WHERE link = '%s' */
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
    public static ResultSet retriveGenrePlaylist(Statement stmt, int id) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRED_USER_QUERY,id);
        return stmt.executeQuery(sql);
    }
}
