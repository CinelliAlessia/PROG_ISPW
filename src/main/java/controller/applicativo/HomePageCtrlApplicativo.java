package controller.applicativo;

import engineering.bean.NoticeBean;
import engineering.bean.PlaylistBean;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValidException;
import engineering.others.Printer;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.observer.Subject;
import model.Notice;
import model.Playlist;

import java.util.*;

public class HomePageCtrlApplicativo {

    public List<PlaylistBean> retrivePlaylistsApproved() {

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<Playlist> playlists = dao.retrieveApprovedPlaylists();              // Recupero lista Playlist
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        /* OBSERVER */
        PlaylistCollection playlistCollection = PlaylistCollection.getInstance();   // Recupero l'istanza del Model Subject
        playlistCollection.setState(playlists);                                     // Imposto lo stato del model Subject SETSTATE NON CHIAMA UPDATE

        // Dato che viene fatto set state, serve fare return di un playlist
        // bean se comunque nel setState verrà fatto update?

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValidException e){
            // Non la valuto perché è un retrieve da persistenza, dove è stata caricata correttamente
            Printer.logPrint(String.format("HomePage APP: LinkIsNotValid %s", e.getMessage()));
        }
        return playlistsBean;
    }

    public List<PlaylistBean> searchPlaylistByFilters(PlaylistBean playlistBean) {

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();  // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<PlaylistBean> playlistsBean = new ArrayList<>();           // Creo una lista di playlistBean da restituire al Grafico
        Playlist playlist = new Playlist();                             // Creo la entity da passare al DAO

        /* Popolo la playlist da cercare con solo le informazioni di cui l'utente è interessato */
        playlist.setPlaylistName(playlistBean.getPlaylistName());
        playlist.setPlaylistGenre(playlistBean.getPlaylistGenre());
        playlist.setEmotional(playlistBean.getEmotional());

        List<Playlist> playlists;

        if(emotionalEmpty(playlist.getEmotional()) && genreEmpty(playlist.getPlaylistGenre())){
            playlists = dao.searchPlaylistByTitle(playlist);        // Recupero lista Playlist filtrata solo per titolo della playlist
        } else if (!emotionalEmpty(playlist.getEmotional()) && !genreEmpty(playlist.getPlaylistGenre())){
            playlists = dao.searchPlaylistByFilters(playlist);    // Recupero lista Playlist controllando tutti i filtri
        } else if (emotionalEmpty(playlist.getEmotional())) {
            playlists = dao.searchPlaylistByGenre(playlist);      // Recupero lista Playlist controllando Titolo e Generi musicali
        } else {
            playlists = dao.searchPlaylistByEmotional(playlist);  // Recupero lista Playlist controllando Titolo ed Emotional
        }

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValidException e){
            // Non la valuto perché è un retrieve da persistenza, dove è stata caricata correttamente
            Printer.logPrint(String.format("HomePage APP: LinkIsNotValid %s", e.getMessage()));
        }
        return playlistsBean;
    }

    /** Utilizzata per un corretto filtraggio */
    private boolean emotionalEmpty(List<Integer> emotional) {
        if(emotional == null){
            return true;
        }

        for (Integer value : emotional) {
            if (value != 0) {
                return false; // Se anche un solo valore non è 0, restituisci false
            }
        }

        return true; // Tutti i valori sono 0
    }

    /** Utilizzata per un corretto filtraggio */
    private boolean genreEmpty(List<String> genre){
        return genre == null || genre.isEmpty();
    }

    public void removeNotice(NoticeBean noticeBean) {
        NoticeDAO dao = DAOFactory.getDAOFactory().createNoticeDAO();   // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        Notice notice = new Notice(noticeBean.getTitle(), noticeBean.getBody(), noticeBean.getEmail());
        dao.deleteNotice(notice);
    }

    /** Nel caso in cui non volessimo che la view contattasse il model */
    public void observePlaylistTable(Observer observer){
        Subject playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.attach(observer);
    }
}
