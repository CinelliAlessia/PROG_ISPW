package engineering.dao;

import engineering.exceptions.*;
import model.*;

import java.util.List;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void insertUser(Login registration) throws EmailAlreadyInUse, UsernameAlreadyInUse;

    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Client loadUser(Login login) throws UserDoesNotExist;

    /** Retrive delle informazioni di un utente dalla persistenza, ottenuta dall'username che abbiamo detto essere unico */
    Client retrieveUserByUsername(String username) throws UserDoesNotExist;

    /** Ottiene la password associata all'email */
    String getPasswordByEmail(String email) throws UserDoesNotExist;

    /** Aggiorna i generi musicali preferiti dall'utente, recuperato tramite email*/
    void updateGenreUser(Client client);

    void tryCredentialExisting(Login login) throws EmailAlreadyInUse, UsernameAlreadyInUse;

    void addNotice(Notice notice);

    List<Notice> retrieveNotice(User user);
}