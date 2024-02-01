package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.PlaylistDAO;
import engineering.dao.TypesOfPersistenceLayer;
import model.Playlist;
import java.io.IOException;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws IOException {
        //###################### IMPORTANTE CAPIRE COSA FARE CON QUESTO ID ######################

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Crea la Playlist (model)
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre());
        System.out.println("AddPlaylist applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylistGenre());

        // Invio utente model al DAO
        dao.insertPlaylist(playlist);
    }

}
