package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.observer.Subject;
import model.Playlist;

import java.util.*;
import java.util.logging.Logger;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class HomePageCtrlApplicativo {

    private static final Logger logger = Logger.getLogger(HomePageCtrlApplicativo.class.getName());
    public List<PlaylistBean> retrivePlaylistsApproved() {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();                   // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<Playlist> playlists = dao.retrieveApprovedPlaylists();              // Recupero lista Playlist
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        /* BYPASSIAMO MVC PER PATTERN OBSERVER */
        PlaylistCollection playlistCollection = PlaylistCollection.getInstance();   // Recupero l'istanza del Model Subject
        playlistCollection.setState(playlists);                                     // Imposto lo stato del model Subject SETSTATE NON CHIAMA UPDATE

        // Dato che viene fatto set state, serve fare return di un playlist bean se comunque nel setState verrà fatto update?
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


    /* ############# Dovrebbe essere fuso con il searchPlaylistByName ################ */
    public List<PlaylistBean> searchPlaylistByFilters(PlaylistBean playlistBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();                   // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<PlaylistBean> playlistsBean = new ArrayList<>();           // Creo una lista di playlistBean da restituire al Grafico
        Playlist playlist = new Playlist();                             // Creo la entity da passare al DAO

        /* Popolo la playlist da cercare con solo le informazioni di cui l'utente è interessato */
        playlist.setPlaylistName(playlistBean.getPlaylistName());
        playlist.setPlaylistGenre(playlistBean.getPlaylistGenre());
        playlist.setEmotional(playlistBean.getEmotional());

        List<Playlist> playlists;
        if(emotionalEmpty(playlist.getEmotional()) && genreEmpty(playlist.getPlaylistGenre())){
            playlists = dao.searchPlaylistTitle(playlist);  // Recupero lista Playlist filtrata solo per titolo della playlist
        } else if (!emotionalEmpty(playlist.getEmotional()) && !genreEmpty(playlist.getPlaylistGenre())){
            playlists = dao.searchPlaylistByFilters(playlist);  // Recupero lista Playlist controllando tutti i filtri
        } else if (emotionalEmpty(playlist.getEmotional())) {
            playlists = dao.searchPlaylistByGenre(playlist);  // Recupero lista Playlist controllando Titolo e Generi musicali
        } else {
            playlists = dao.searchPlaylistByEmotional(playlist);  // Recupero lista Playlist controllando Titolo ed Emotional
        }

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            logger.info("HomePage APP: LinkIsNotValid " + e.getMessage());
        }
        return playlistsBean;
    }

    private boolean emotionalEmpty(List<Integer> emotional) {
        if(emotional == null){
            return true;
        }

        for (Integer value : emotional) {
            if (value != 0) {
                return false; // Se anche un solo valore non è 0.0, restituisci false
            }
        }

        return true; // Tutti i valori sono 0.0
    }

    private boolean genreEmpty(List<String> genre){
        return genre == null || genre.isEmpty();
    }



    public void observePlaylistTable(Observer observer){
        Subject playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.attach(observer);
    }

}
