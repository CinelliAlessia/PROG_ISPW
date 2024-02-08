package engineering.dao;

import engineering.exceptions.*;
import model.User;

import java.util.List;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    boolean insertUser(User user) throws EmailAlreadyInUse, UsernameAlreadyInUse;

    /** Retrive delle informazioni di un utente dalla persistenza, ottenuta dall'email */
    User loadUser(String userEmail) throws UserDoesNotExist;

    /** Retrive delle informazioni di un utente dalla persistenza, ottenuta dall'username che abbiamo detto essere unico */
    User retrieveUserByUsername(String userName);

    /** Ottiene la password associata all'email */
    String getPasswordByEmail(String email) throws UserDoesNotExist;

    /** Aggiorna i generi musicali preferiti dall'utente, recuperato tramite email*/
    void updateGenreUser(String email, List<String> preferences);
}

