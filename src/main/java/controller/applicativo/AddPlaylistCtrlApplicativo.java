package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.Playlist;
import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB){
        //###################### IMPORTANTE CAPIRE COSA FARE CON QUESTO ID ######################

        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        PlaylistDAO dao = persistenceType.createPlaylistDAO();

        // Crea la Playlist (model)
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved());
        System.out.println("AddPlaylist applicativo: "+playlist.getEmail()+ " " + playlist.getUsername()+ " " + playlist.getPlaylistName() + " " + playlist.getLink() + " " + playlist.getPlaylistGenre());

        // Invio Playlist model al DAO
        if(dao.insertPlaylist(playlist)){
            System.out.println("Playlist caricata correttamente");
        } else {
            //################# Se la playlist non viene caricata #################àà
        }
    }

}
