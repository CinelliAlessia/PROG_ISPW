package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.*;
import model.Playlist;
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
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }
}
