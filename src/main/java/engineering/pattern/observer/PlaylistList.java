package engineering.pattern.observer;

import model.Playlist;

import java.util.ArrayList;
import java.util.List;

/** È l'argomento da osservare, il publisher, se viene aggiunto un elemento in allPlaylist va chiamato notify
 * verso i subscribers */
public class PlaylistList extends Subject {

    /** Stato del subject */
    private List<Playlist> allPlaylists = new ArrayList<>();

    private List<Observer> observers = new ArrayList<>();


    /** Aggiunge un observer alla lista dei subscribers */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /** Rimuove un observer dalla lista dei subscribers */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(); //Parametro allPlaylist
        }
    }

    /** Metodo setState() */
    public void addPlaylist(Playlist playlist) {
        allPlaylists.add(playlist);
        notifyObservers();
    }

    /** Metodo getState() in teoria utilizzato dai subscribers una volta notificati di dover svolgere un update() ? */
    public void getState(){

    }
}

