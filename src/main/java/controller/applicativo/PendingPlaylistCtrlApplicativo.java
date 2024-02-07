package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.pattern.observer.PlaylistCollection;
import model.Playlist;
import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class PendingPlaylistCtrlApplicativo {

    public PlaylistBean approvePlaylist(PlaylistBean pB){
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getId());

        Playlist playlistApproved = dao.approvePlaylist(playlist);

        PlaylistCollection playlistCollection = new PlaylistCollection();
        playlistCollection.addPlaylist(playlist);

        pB.setApproved(playlistApproved.getApproved());
        return pB;
    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retrievePlaylists(){

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePendingPlaylists();

        List<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }

    public void rejectPlaylist(PlaylistBean pB) {
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getId());

        dao.deletePlaylist(playlist);
    }
}