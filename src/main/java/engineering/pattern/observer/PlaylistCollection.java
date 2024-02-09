package engineering.pattern.observer;

import model.Playlist;

import java.util.*;

/** È l'argomento da osservare (il publisher)
 * se viene modificata la PlaylistCollection tramite i metodi addPlaylist o removePlaylist, vengono successivamente
 * informati tutti gli observers(subscribers) utilizzando il metodo notifyObservers.

 * Questo model rappresenta la lista di Playlist approvate.
 * */
public class PlaylistCollection extends Subject {
    private static PlaylistCollection playlistCollection = null;

    /** Stato del subject */
    private List<Playlist> allPlaylists = new ArrayList<>();

    /** Singleton poiché tutti gli utenti hanno la stessa vista dello strato di persistenza */
    public static PlaylistCollection getInstance() { //Pattern Singleton
        if (playlistCollection == null) {
            playlistCollection = new PlaylistCollection();
        }
        return playlistCollection;
    }

    private PlaylistCollection(){

    }

    /** Metodo setState()
     * Utilizzata da AddPlaylistCtrlGrafico se il supervisor carica una playlist
     * Utilizzata da PendingPlaylistCtrlGrafico se il supervisor accetta una playlist
     * */
    public void addPlaylist(Playlist playlist) {
        System.out.println(playlist.getLink() + " " + playlist.getId() + " " +  playlist.getEmotional());
        allPlaylists.add(playlist);
        notifyObservers();
    }

    public void removePlaylist(Playlist playlist) {
        allPlaylists.remove(playlist);
        notifyObservers();
    }

    public void setState(List<Playlist> playlists) {
        for(Playlist p: playlists){
            System.out.println(p.getLink() + " " + p.getId() + " " +  p.getEmotional());
        }

        allPlaylists = playlists;
        //notifyObservers();
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

