package engineering.dao;

import model.Playlist;

import java.sql.SQLException;
import java.util.List;

public class PlaylistDAOJSON implements PlaylistDAO {
    /**
     * @param playlist
     * @throws SQLException
     */
    @Override
    public void insertPlaylist(Playlist playlist) {

    }

    /**
     * @param email
     * @return
     */
    @Override
    public String getPlaylistByUserName(String email) {
        return null;
    }

    /**
     * @param playlistInstance
     */
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

    /**
     * @return
     */
    @Override
    public List<Playlist> retrivePlaylistUser() {
        return null;
    }
}