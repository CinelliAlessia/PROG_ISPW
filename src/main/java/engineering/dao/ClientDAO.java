package engineering.dao;

import engineering.exceptions.*;
import model.*;

public interface ClientDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void insertClient(Login registration) throws EmailAlreadyInUseException, UsernameAlreadyInUseException;

    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Client loadClient(Login login) throws UserDoesNotExistException;

    /** Retrive delle informazioni di un utente dalla persistenza, ottenuta dall'username che abbiamo detto essere unico */
    Client retrieveClientByUsername(String username) throws UserDoesNotExistException;

    /** Ottiene la password associata all'email */
    String getPasswordByEmail(String email) throws UserDoesNotExistException;

    /** Aggiorna i generi musicali preferiti dall'utente, recuperato tramite email*/
    void updateGenreClient(Client client);

    void tryCredentialExisting(Login login) throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
}