package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.PlaylistLinkAlreadyInUse;
import engineering.pattern.observer.PlaylistCollection;
import model.Playlist;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws PlaylistLinkAlreadyInUse {
        //###################### IMPORTANTE CAPIRE COSA FARE CON QUESTO ID ######################

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea la Playlist (model)
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved());
        System.out.println("AddPlaylist applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylistGenre());

        // Invio Playlist model al DAO
        if(dao.insertPlaylist(playlist)){
            System.out.println("Playlist caricata correttamente");
            PlaylistCollection playlistCollection = PlaylistCollection.getInstance();
            playlistCollection.addPlaylist(playlist);
        } else {
            //################# Se la playlist non viene caricata #################àà
            System.out.println("Playlist non è stata caricata");
        }
    }

}
