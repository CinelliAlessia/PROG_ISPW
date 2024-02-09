package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.PlaylistCollection;
import model.Playlist;
import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class PendingPlaylistCtrlApplicativo {

    public PlaylistBean approvePlaylist(PlaylistBean pB){
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        System.out.println("ApprovePlaylist il bean contiene:"+ pB.getPlaylistName() + pB.getId() );

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        System.out.println("ApprovePlaylist la playlist contiene:"+ playlist.getPlaylistName() + playlist.getId() );
        // Istanza di playlist ha ancora il parametro approved a false
        Playlist playlistApproved = dao.approvePlaylist(playlist);

        PlaylistCollection playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.addPlaylist(playlist); // Lancio metodo con approved = false

        // ############ Imposto approved a true sul bean (?) ############
        pB.setApproved(playlistApproved.getApproved());
        return pB;
    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retrievePlaylists(){

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePendingPlaylists();
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }

        return playlistsBean;
    }

    public void rejectPlaylist(PlaylistBean pB) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);
    }
}
