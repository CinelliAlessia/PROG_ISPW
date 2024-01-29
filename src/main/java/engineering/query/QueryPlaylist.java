package engineering.query;

import model.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryPlaylist {
    public static void insertPlaylist(Statement stmt, Playlist playlist) throws SQLException {

        String email = playlist.getEmail();
        String username = playlist.getUsername();
        String link = playlist.getLink();
        String name_playlist = playlist.getPlaylistName();

        String insertPlaylistStatement = String.format(Queries.INSERT_PLAYLIST_QUERY, email, username, name_playlist, link);
        stmt.executeUpdate(insertPlaylistStatement);
    }

    public static ResultSet searchPlaylistLink(Statement stmt, String link) throws SQLException {
        String sql = String.format(Queries.SELECT_LINK_QUERY, link);
        return stmt.executeQuery(sql);
    }
}
