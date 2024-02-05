package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.Playlist;

import java.util.ArrayList;
import java.util.List;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class PlaylistToApproveCtrlApplicativo {

    public PlaylistToApproveCtrlApplicativo(){

    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retriveAllPlaylist(){

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retriveAllPlaylist();

        List<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }

}
