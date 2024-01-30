package controllerApplicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.exceptions.PlaylistNameAlreadyInUse;
import model.Playlist;

import java.sql.SQLException;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws SQLException, PlaylistNameAlreadyInUse {
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylist_genre());
        System.out.println("AddP applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylist_genre());

        PlaylistDAO playlistDAO = new PlaylistDAO();
        playlistDAO.insertPlaylist(playlist);

    }
}
