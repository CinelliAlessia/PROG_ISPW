package controller.applicativo;

import engineering.bean.*;
import engineering.others.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.abstract_factory.DAOFactory;

import model.Playlist;

public class AddPlaylistCtrlApplicativo {

    public void insertPlaylist(PlaylistBean pB) throws PlaylistLinkAlreadyInUseException, PlaylistNameAlreadyInUseException {

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea la Playlist (model), id verrà impostato dal dao
        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());

        // Invio Playlist model al DAO
        try{
            dao.insertPlaylist(playlist);

            /* Per pattern Observer !!! */
            if(playlist.getApproved()){ // La notifica all observer solo se la playlist è approvata -> Se è caricata da un supervisore
                PlaylistCollection.getInstance().addPlaylist(playlist);
            }

        } catch (PlaylistLinkAlreadyInUseException e){
            Printer.errorPrint("ADD APP: Playlist non è stata caricata");
            throw new PlaylistLinkAlreadyInUseException();
        } catch (PlaylistNameAlreadyInUseException e) {
            Printer.errorPrint("ADD APP: Playlist non è stata caricata");
            throw new PlaylistNameAlreadyInUseException();
        }
    }

}
