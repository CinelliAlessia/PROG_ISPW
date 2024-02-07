package engineering.pattern.observer;

import model.Playlist;

import java.util.ArrayList;
import java.util.List;

/** È l'argomento da osservare, il publisher, se viene aggiunto un elemento in allPlaylist va chiamato notify
 * verso i subscribers
 * Questo model rappresenta la lista di Playlist pubblicate, approvate, quelle che verranno viste dal
 * */
public class PlaylistCollection extends Subject {

    /** Stato del subject */
    private List<Playlist> allPlaylists = new ArrayList<>();

    /** Metodo setState()
     * Utilizzata da AddPlaylistCtrlGrafico se il supervisor carica una playlist
     * Utilizzata da PendingPlaylistCtrlGrafico se il supervisor accetta una playlist
     * */
    public void addPlaylist(Playlist playlist) {
        allPlaylists.add(playlist);
        notifyObservers();
    }

    public void removePlaylist(Playlist playlist) {
        allPlaylists.remove(playlist);
        notifyObservers();
    }

    public void setState(List<Playlist> playlists) {
        allPlaylists = playlists;
        notifyObservers();
    }

    /**
     * Metodo getState() in teoria utilizzato dai subscribers una volta notificati di dover svolgere un update() ?
     *
     * @return tutte le playlist
     */
    public List<Playlist> getState(){
        return allPlaylists;
    }
}

