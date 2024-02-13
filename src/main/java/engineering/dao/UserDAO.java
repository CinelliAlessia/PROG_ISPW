package engineering.dao;

import engineering.exceptions.*;
import model.*;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void insertUser(Login registration) throws EmailAlreadyInUse, UsernameAlreadyInUse;

    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Client loadUser(Login login) throws UserDoesNotExist;

    /** Retrive delle informazioni di un utente dalla persistenza, ottenuta dall'username che abbiamo detto essere unico */
    Client retrieveUserByUsername(String username);

    /** Ottiene la password associata all'email */
    String getPasswordByEmail(String email) throws UserDoesNotExist;

    /** Aggiorna i generi musicali preferiti dall'utente, recuperato tramite email*/
    void updateGenreUser(Client client);
}

