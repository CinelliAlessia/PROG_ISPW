package engineering.pattern.observer;

import java.util.ArrayList;

/** Subject è una classe astratta che fornisce interfacce per registrare o rimuovere dinamicamente gli observer (i subscribers)
 * implementa le seguenti funzioni
 * 1) attach(observer): aggiunge un ConcreteObserver alla lista delle classi da notificare
 * 2) detach(observer): rimuove un ConcreteObserver alla lista delle classi da notificare
 * 3) notify(): notifica un cambiamento alle classi ConcreteObserver
 * */
public abstract class Subject {

    private ArrayList<Observer> observers ;

    protected Subject() {
        observers = new ArrayList<>() ;
    }

    public void attach(Observer newObserver) {
        observers.add(newObserver) ;
    }

    public void detach(Observer removeObserver) {
        observers.remove(removeObserver) ;
    }

    /** Implementato con un loop su tutti i ConcreteObserver, dove ciascuno di essi chiama la funzione update()
     * Viene eseguita dal ConcreteSubject (il publisher) per notificare il suo cambio di stato

     * È BUONA NORMA RENDERLO PRIVATO PERCHÈ SOLO CONCRETEOBSERVER DOVREBBE CHIAMARLO */
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }



}
