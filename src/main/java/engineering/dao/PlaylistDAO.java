package engineering.dao;

import model.Playlist;

import java.util.List;

public interface PlaylistDAO {

    /** Inserisce una playlist in persistenza*/
    boolean insertPlaylist(Playlist playlist);

    /** Non serve che crea una nuova istanza di Playlist*/
    Playlist approvePlaylist(Playlist playlist);

    /** Elimina la playlist */
    void deletePlaylist(Playlist playlist);

    /** Recupera tutte le playlist dell'utente dall'email */
    List<Playlist> retrievePlaylistsByEmail(String email);

    /** Recupera tutte le playlist globali per genere  */
    List<Playlist> retrievePlaylistsByGenre(List<String> genres);

    List<Playlist> retrievePendingPlaylists();
    List<Playlist> retrieveApprovedPlaylists();

}

