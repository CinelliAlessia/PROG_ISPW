package engineering.dao;

import model.User;

public interface UserDAO {
    void insertUser(User user);

    User loadUser(String userEmail);

    String getPasswordByEmail(String email);
    void deleteUser(User userInstance);
    void retrieveUserByUserName(String userName);
}

    // Devo definire le operazioni che dovranno essere implementate nelle varie interfacce

    // Come interagisco con database degli Utenti?

