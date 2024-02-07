package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.*;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.observer.Subject;
import model.Playlist;
import view.HomePageCtrlGrafico;

import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class HomePageCtrlApplicativo {
    public List<PlaylistBean> retrivePlaylistsApproved() {

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrieveApprovedPlaylists();
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }

    public List<PlaylistBean> searchNamePlaylist(PlaylistBean playlistBean) {

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Creo una lista di playlistBean
        List<PlaylistBean> playlistsBean = new ArrayList<>();
        // Creo la entity da passare al DAO
        Playlist playlist = new Playlist();

        playlist.setPlaylistName(playlistBean.getPlaylistName());

        // Recupero lista Playlist
        List<Playlist> playlists = dao.searchPlaylistString(playlist);

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
            playlistsBean.add(pB);
        }

        return playlistsBean;

    }

    public void observePlaylistTable(Observer observer){
        Subject playlistCollection = new PlaylistCollection();
        playlistCollection.attach(observer);
    }

}
