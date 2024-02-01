package engineering.dao;

import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.others.Connect;
import engineering.query.QueryPlaylist;
import model.Playlist;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOMySQL implements PlaylistDAO {

    /** Inserimento di una playlist in db. Viene prima controllato che non ci sia già il link all'interno del DB, successivamente inserisce */
    public void insertPlaylist(Playlist playlist) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlist.getLink();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink);

            if (rs.next()) {
                throw new PlaylistLinkAlreadyInUse("This link is already in use!");
            }

            QueryPlaylist.insertPlaylist(stmt, playlist);

        } catch (PlaylistLinkAlreadyInUse | SQLException e) {
            e.fillInStackTrace();
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
    }

    public String getPlaylistByUserName(String email) {
        return null;
    }

    public List<Playlist> retrivePlaylistByUsername(String username) {
        Statement stmt = null;
        Connection conn;

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
                ResultSet resultSet = QueryPlaylist.retriveGenrePlaylist(stmt, i); //ATTENZIONE ALL'ID CHE PASSIAMO, 0 SE VOGLIO I GENERI DEGLI USER

                while (resultSet.next()) {
                    Playlist playlist = new Playlist();
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

        }
        catch (SQLException e){
            e.fillInStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }
        }
        return null;
    }

    public void deletePlaylist(Playlist playlistInstance) {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistLink = playlistInstance.getLink();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistLink); // Cerca in all_playlist

            if (rs.next()) {
                QueryPlaylist.removePlaylistByLink(stmt,playlistInstance.getLink()); // Rimuove in playlist_utente E in all_playlist
            }

        } catch (SQLException e){
            e.fillInStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.fillInStackTrace();
            }

        }
    }

    public void retrievePlaylistByMail(String mail) {
        //TODO
    }

    public void retrievePlaylistByGenre(String genre) {
        //TODO
    }


}