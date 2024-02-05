package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.Playlist;

import java.util.ArrayList;
import java.util.List;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class PlaylistToApproveCtrlApplicativo {

    public PlaylistBean approvePlaylist(PlaylistBean pB){
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved());

        Playlist playlistApproved = dao.approvePlaylist(playlist);
        pB.setApproved(playlistApproved.getApproved());

        return pB;
    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retriveAllPlaylist(){

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retriveAllPlaylistToApprove();

        List<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }

}
