package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.observer.Subject;
import model.Playlist;

import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class HomePageCtrlApplicativo {
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

    public List<PlaylistBean> searchPlaylistByName(PlaylistBean playlistBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<PlaylistBean> playlistsBean = new ArrayList<>();           // Creo una lista di playlistBean
        Playlist playlist = new Playlist();                             // Creo la entity da passare al DAO

        // Devo cercare solo il nome, non ho bisogno di impostare altro
        playlist.setPlaylistName(playlistBean.getPlaylistName());
        List<Playlist> playlists = dao.searchPlaylistString(playlist);  // Recupero lista Playlist

        System.out.println("Applicativo home page: playlist trovate " + playlists);

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            System.out.println("Home Page applicativo: " + e.getMessage());
        }
        return playlistsBean;
    }

    /* ############# Dovrebbe essere fuso con il searchPlaylistByName ################ */
    public List<PlaylistBean> searchPlaylistByFilters(PlaylistBean playlistBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<PlaylistBean> playlistsBean = new ArrayList<>();           // Creo una lista di playlistBean da restituire al Grafico
        Playlist playlist = new Playlist();                             // Creo la entity da passare al DAO

        playlist.setPlaylistGenre(playlistBean.getPlaylistGenre());
        playlist.setEmotional(playlistBean.getEmotional());

        List<Playlist> playlists = dao.searchPlaylistByFilters(playlist);  // Recupero lista Playlist

        System.out.println("Applicativo home page: playlist trovate " + playlists);

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            System.out.println("Home Page applicativo: LinkIsNotValid " + e.getMessage());
        }
        return playlistsBean;
    }

    public void observePlaylistTable(Observer observer){
        Subject playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.attach(observer);
    }

}
