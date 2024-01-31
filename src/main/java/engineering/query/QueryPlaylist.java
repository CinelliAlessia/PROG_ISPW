package engineering.query;

import model.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryPlaylist {
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        System.out.println(email);
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String namePlaylist = playlist.getPlaylistName();
        List<String> playlistGenre = playlist.getPlaylistGenre();

        int idPlaylist = playlist.getId();

        // Poi inserisci i generi musicali nella tabella 'generi_musicali'
        insertGeneriMusicali(stmt, idPlaylist, email, playlistGenre);

        String insertAllPlaylistStatement = String.format(Queries.INSERT_ALL_PLAYLIST_QUERY, namePlaylist, link);
        stmt.executeUpdate(insertAllPlaylistStatement);

        String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_QUERY, email, username, namePlaylist, link, idPlaylist);
        stmt.executeUpdate(insertPlaylistStatement);


    }

    public static void insertGeneriMusicali(Statement stmt, int id, String userEmail, List<String> generiMusicali) throws SQLException {
        // Costruisci la query di inserimento
        StringBuilder query = new StringBuilder(String.format(Queries.INSERT_GENERI_MUSICALI_PLAYLIST_QUERY, buildGenresQueryString(id, generiMusicali, userEmail)));

        // Esegui la query
        stmt.executeUpdate(query.toString());
    }
    private static String buildGenresQueryString(int id, List<String> generiMusicali, String userEmail) {
        String[] genres = {"Pop", "Indie", "Classic", "Rock", "Electronic", "House", "HipHop", "Jazz", "Acoustic", "REB", "Country", "Alternative"};
        StringBuilder query = new StringBuilder();

        //query.append(id).append(", ");  // Aggiungi l'ID alla query
        query.append(String.format("%d, ", id));  // Aggiungi l'ID alla query
        System.out.println(query);

        for (String genere : genres) {
            query.append(generiMusicali.contains(genere) ? "1, " : "0, ");
        }
        System.out.println(query);

        query.append(String.format("'%s'", userEmail));
        System.out.println(query);

        return query.toString();
    }

    public static ResultSet searchPlaylistLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_LINK_QUERY, link);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retrivePlaylistUser(Statement stmt) throws SQLException {
        String sql = String.format(Queries.SELECT_PLAYLIST_BY_USER);
        return stmt.executeQuery(sql);
    }

    public static ResultSet retriveGenrePlaylist(Statement stmt,int id) throws SQLException {
        String sql = String.format(Queries.SELECT_GENRED_USER_QUERY,id);
        return stmt.executeQuery(sql);
    }


}
