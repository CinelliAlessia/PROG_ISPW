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
    public void insertPlaylist(Playlist playlist) throws SQLException {
        Statement stmt = null;
        Connection conn;

        try {
            conn = Connect.getInstance().getDBConnection();
            stmt = conn.createStatement();

            String playlistName = playlist.getPlaylistName();
            ResultSet rs = QueryPlaylist.searchPlaylistLink(stmt, playlistName);

            if (rs.next()) {
                throw new PlaylistLinkAlreadyInUse("This link is already in use!");
            }

            QueryPlaylist.insertPlaylist(stmt, playlist);

        } catch (PlaylistLinkAlreadyInUse e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    @Override
    public String getPlaylistByUserName(String email) {
        return null;
    }

    public List<Playlist> retrivePlaylistUser() throws SQLException {
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
                    playlist.setId(resultSet.getString("id"));
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


    @Override
    public void deletePlaylist(Playlist playlistInstance) {

    }

    /**
     * @param mail
     */
    @Override
    public void retrievePlaylistByMail(String mail) {

    }

    /**
     * @param genre
     */
    @Override
    public void retrievePlaylistByGenre(String genre) {

    }


}