package engineering.pattern.observer;


/** Definisce l'interfaccia per tutti i subscribers per ricevere le notifiche dai publisher
 * Verrà usata come classe astratta implementata dai ConcreteObserver che realizzeranno update */
public interface Observer {

    void update();
}
