package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.pattern.observer.PlaylistCollection;
import model.Playlist;

import java.util.logging.Logger;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AddPlaylistCtrlApplicativo {

    private static final Logger logger = Logger.getLogger(AddPlaylistCtrlApplicativo.class.getName());

    public void insertPlaylist(PlaylistBean pB) throws PlaylistLinkAlreadyInUse {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea la Playlist (model), id verrà impostato dal dao
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());

        // Invio Playlist model al DAO
        if(dao.insertPlaylist(playlist)){
            /* Per pattern Observer */
            PlaylistCollection playlistCollection = PlaylistCollection.getInstance();
            if(playlist.getApproved()){ // La notifica all observer solo se la playlist è approvata
                playlistCollection.addPlaylist(playlist);
            }
        } else {
            //################# Se la playlist non viene caricata Dovrei restituire un eccezione #################àà
            logger.info("ADD APP: Playlist non è stata caricata");
        }
    }

}
