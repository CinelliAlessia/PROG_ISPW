package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.dao.TypesOfPersistenceLayer;
import model.Playlist;

import java.io.IOException;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AccountCtrlApplicativo {

    /** Recupera tutte le playlist globali by username*/

    /*
    public static List<PlaylistBean> retriveList() throws IOException {

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrivePlaylistByUsername(username);

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();

        for (Playlist p : playlists){
            PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre());
            playlistsBean.add(pB);
        }

        return playlistsBean;
    }*/

    public static Boolean deletePlaylist(PlaylistBean pB) throws IOException {

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
