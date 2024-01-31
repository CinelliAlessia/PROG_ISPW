package controllerApplicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.exceptions.PlaylistNameAlreadyInUse;
import model.Playlist;

import java.sql.SQLException;
import java.util.*;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws SQLException, PlaylistNameAlreadyInUse {
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre());
        //################################################# IMPORTANTE CAPIRE COSA FARE CON QUESTO ID
        System.out.println("AddP applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylistGenre());

        PlaylistDAO playlistDAO = new PlaylistDAO();
        playlistDAO.insertPlaylist(playlist);

    }

    public static List<PlaylistBean> retriveList() throws SQLException {

        List<Playlist> playlists = PlaylistDAO.retrivePlaylistUser();

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }
}
