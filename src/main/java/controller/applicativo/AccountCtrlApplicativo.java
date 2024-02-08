package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValid;
import model.Playlist;
import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.*;

public class AccountCtrlApplicativo {

    /** Recupera tutte le playlist globali by username
     */
    public List<PlaylistBean> retrivePlaylists(UserBean userBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePlaylistsByEmail(userBean.getEmail()); //##################### ok che dobbiamo passare una stinga ma non userBean.getEmail

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();
        try {
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }

        return playlistsBean;
    }

    /** Utilizzata per aggiornare i generi musicali preferiti dell'utente in caso in cui prema il bottone Salva */
    public void updateGenreUser(UserBean userBean){

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Invio utente model al DAO
        dao.updateGenreUser(userBean.getEmail(), userBean.getPreferences());

    }

    /** Utilizzata per eliminare le playlist
     * vanno aggiunti dei controlli per capire chi può eliminare */
    public static Boolean deletePlaylist(PlaylistBean pB){

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved());

        dao.deletePlaylist(playlist);

        // Per ora lascio return true
        return true;
    }

}
