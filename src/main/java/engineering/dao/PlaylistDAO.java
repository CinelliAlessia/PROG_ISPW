package engineering.dao;

import model.Playlist;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistDAO {
    void insertPlaylist(Playlist playlist) throws SQLException;
    String getPlaylistByUserName(String email);
    void deletePlaylist(Playlist playlistInstance);
    void retrievePlaylistByMail(String mail);
    void retrievePlaylistByGenre(String genre);
    List<Playlist> retrivePlaylistUser() throws SQLException;
}
