package controllerApplicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.dao.PlaylistDAO_mySQL;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.dao.UserDAO;
import engineering.exceptions.PlaylistNameAlreadyInUse;
import model.Playlist;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws SQLException, IOException {
        //###################### IMPORTANTE CAPIRE COSA FARE CON QUESTO ID ######################

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Crea la Playlist (model)
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre());
        System.out.println("AddPlaylist applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylistGenre());
        // Invio utente model al DAO
        dao.insertPlaylist(playlist);
    }

    public static List<PlaylistBean> retriveList() throws SQLException, IOException {

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrivePlaylistUser();

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }
}
