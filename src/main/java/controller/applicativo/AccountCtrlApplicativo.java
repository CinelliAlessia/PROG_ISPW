package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AccountCtrlApplicativo {

    /** Recupera tutte le playlist globali by username
     */
    public List<PlaylistBean> retrivePlaylists(ClientBean clientBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

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
            e.fillInStackTrace();
        }

        return playlistsBean;
    }

    /** Utilizzata per aggiornare i generi musicali preferiti dell'utente in caso in cui prema il bottone Salva */
    public void updateGenreUser(ClientBean clientBean){

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        ClientDAO dao = persistenceType.createUserDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

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

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);

        // Per ora lascio return true
        return true;
    }

}
