package controller.applicativo;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.pattern.observer.PlaylistCollection;
import model.Playlist;

import java.util.ArrayList;
import java.util.List;



public class PendingPlaylistCtrlApplicativo {

    public void approvePlaylist(PlaylistBean pB){
        PlaylistDAO dao = getDAO();
        System.out.println("ApprovePlaylist il bean contiene:"+ pB.getPlaylistName() + pB.getId() );

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        System.out.println("ApprovePlaylist la playlist contiene:"+ playlist.getPlaylistName() + playlist.getId() );
        // Istanza di playlist ha ancora il parametro approved a false
        Playlist playlistApproved = dao.approvePlaylist(playlist);

        PlaylistCollection playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.addPlaylist(playlist); // Lancio metodo con approved = false ######################

        // ############ Imposto approved a true sul bean (?) ############
        pB.setApproved(playlistApproved.getApproved());
    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retrievePlaylists(){

        PlaylistDAO dao = getDAO();

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
        PlaylistDAO dao = getDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);
    }

    private PlaylistDAO getDAO(){
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        return persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
    }
}
