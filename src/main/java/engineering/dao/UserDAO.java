package engineering.dao;

import model.User;

import java.util.List;

public interface UserDAO {
    void insertUser(User user);

    User loadUser(String userEmail);

    String getPasswordByEmail(String email);
    void deleteUser(User userInstance);
    void retrieveUserByUserName(String userName);

    /** Aggiorna i generi musicali preferiti dall'utente, recuperato tramite email*/
    void updateGenreUser(String email, List<String> preferences);
}

    // Devo definire le operazioni che dovranno essere implementate nelle varie interfacce

    // Come interagisco con database degli Utenti?

