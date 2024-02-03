package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.Playlist;
import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.*;

public class AccountCtrlApplicativo {

    /** Recupera tutte le playlist globali by username
     * DA CORREGGERE, COME CONOSCO L'USERNAME ? */
    public static List<PlaylistBean> retriveList(){

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist

        String username = "ciao";
        List<Playlist> playlists = dao.retrivePlaylistByUsername(username);

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }

    /** Utilizzata per aggiornare i generi musicali preferiti dell'utente in caso in cui prema il bottone Salva */
    public void updateGenreUser(UserBean userBean){
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        UserDAO dao = persistenceType.createUserDAO();

        // Invio utente model al DAO
        dao.updateGenreUser(userBean.getEmail(), userBean.getPreferences());

    }

    /** Utilizzata per eliminare le playlist
     * vanno aggiunti dei controlli per capire chi può eliminare */
    public static Boolean deletePlaylist(PlaylistBean pB){

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre());


        dao.deletePlaylist(playlist);

        // Per ora lascio return true
        return true;
    }

}
