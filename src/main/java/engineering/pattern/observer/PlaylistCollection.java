package engineering.pattern.observer;

import model.Playlist;

import java.util.*;

/** È l'argomento da osservare (il publisher, il ConcreteSubject)
 * se viene modificata la PlaylistCollection tramite i metodi addPlaylist o removePlaylist, vengono successivamente
 * informati tutti gli observers(subscribers) utilizzando il metodo notifyObservers.

 * Questo model rappresenta la lista di Playlist approvate.

 * Viene utilizzato per aggiornare le istanze di HomePageControllerGrafico a ogni nuova aggiunta di una playlist.
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

    /** Costruttore privato affinché nessuno possa crearne una nuova istanza */
    private PlaylistCollection(){

    }

    /** Metodo setState()
     * Utilizzata da AddPlaylistCtrlGrafico se il supervisor carica una playlist direttamente
     * Utilizzata da PendingPlaylistCtrlGrafico se il supervisor accetta una playlist di un utente
     * */
    public void addPlaylist(Playlist playlist) {
        allPlaylists.add(playlist);
        notifyObservers();
    }

    /** Non implementato l'eliminazione di una playlist accettata */
    public void removePlaylist(Playlist playlist) {
        allPlaylists.remove(playlist);
        notifyObservers();
    }

    public void setState(List<Playlist> playlists) {
        allPlaylists = playlists;

        // In questo caso non viene fatto notifyObservers() volontariamente
        // perché nessuna istanza di ConcreteObserver modificherà mai lo stato
        // e quindi non andrebbero notificati gli altri Observer

        // setState viene effettuato esclusivamente a ogni ingresso nella applicazione
        // (in initialize nella HomePageCtrlGrafico) dopo aver fatto richiesta in persistenza (modello Pull)
        // e dato che in add e remove Playlist viene fatto notify observer, tutte le altre istanze iscritte sono aggiornate
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

