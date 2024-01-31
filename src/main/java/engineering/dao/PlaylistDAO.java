package engineering.dao;

import engineering.exceptions.PlaylistNameAlreadyInUse;
import engineering.others.Connect;
import engineering.query.QueryPlaylist;
import model.Playlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    /*L'inserimento di una playlist prima controlla che ci sia già il link all'interno del DB, successivamente inserisce*/
    public void insertPlaylist(Playlist playlist) throws SQLException, PlaylistNameAlreadyInUse {
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

    public static List<Playlist> retrivePlaylistUser() throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            ResultSet rs = QueryPlaylist.retrivePlaylistUser(stmt);
            ArrayList<Integer> idPlaylists = new ArrayList<>();

            while (rs.next()) {
                idPlaylists.add(rs.getInt("id_playlist_genred"));
            }

            rs.close();

            List<Playlist> playlists = new ArrayList<>();

            for (int i : idPlaylists) {
                ResultSet resultSet = QueryPlaylist.retriveGenrePlaylist(stmt, i);

                while (resultSet.next()) {
                    Playlist playlist = new Playlist();
                    playlist.setId(resultSet.getInt("id"));
                    playlist.setUsername(resultSet.getString("name"));
                    playlist.setLink(resultSet.getString("link"));
                    playlist.setEmail(resultSet.getString("userEmail"));

                    // Carica i generi dalla tua query e aggiungili alla lista
                    ArrayList<String> genres = new ArrayList<>();
                    // Popola la lista dei generi

                    playlist.setPlaylistGenre(genres);

                    playlists.add(playlist);
                }

                resultSet.close();
            }

            return playlists;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
