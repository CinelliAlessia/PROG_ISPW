package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.pattern.abstract_factory.DAOFactory;
import model.*;

import java.util.*;
import java.util.logging.Logger;

public class AccountCtrlApplicativo {

    private static final Logger logger = Logger.getLogger(AccountCtrlApplicativo.class.getName());


    /** Recupera tutte le playlist globali by username
     */
    public List<PlaylistBean> retrievePlaylists(ClientBean clientBean) {
        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePlaylistsByEmail(clientBean.getEmail()); //##################### ok che dobbiamo passare una stinga ma non userBean.getEmail

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();
        try {
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());
                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            handleDAOException(e);
        }

        return playlistsBean;
    }

    /** Utilizzata per aggiornare i generi musicali preferiti dell'utente in caso in cui prema il bottone Salva */
    public void updateGenreUser(ClientBean clientBean){
        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Client client;
        if(clientBean.isSupervisor()){
            client = new Supervisor(clientBean.getUsername(),clientBean.getEmail(),clientBean.getPreferences());
        } else {
            client = new User(clientBean.getUsername(),clientBean.getEmail(),clientBean.getPreferences());
        }

        // Invio utente model al DAO
        dao.updateGenreClient(client);

    }


    /** Utilizzata per eliminare le playlist
     * vanno aggiunti dei controlli per capire chi può eliminare */
    public static Boolean deletePlaylist(PlaylistBean pB){

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);

        // Per ora lascio return true
        return true;
    }

    private void handleDAOException(Exception e) {
        logger.severe(e.getMessage());
    }


}
