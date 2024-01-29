package engineering.dao;

import engineering.exceptions.PlaylistNameAlreadyInUse;
import engineering.others.Connect;
import engineering.query.QueryPlaylist;
import model.Playlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlaylistDAO {
    /*L'inserimento di una playlist prima controlla che ci sia già il link all'interno del DB, successivamente inserisce*/
    public static void insertPlaylist(Playlist playlist) throws SQLException, PlaylistNameAlreadyInUse {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistName = playlist.getPlaylistName();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistName);
            if (rs.next()) {
                throw new PlaylistNameAlreadyInUse("This list is already in use!");
            }

            QueryPlaylist.insertPlaylist(stmt, playlist);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
