package engineering.dao;

import model.User;

import java.sql.SQLException;

public interface UserDAO {
    void insertUser(User user);

    User loadUser(String userEmail);

    String getPasswordByEmail(String email);
    void deleteUser(User userInstance);
    void retrieveUserByUserName(String userName);
    void retrieveUserByUserId(String userId);
}

    // Devo definire le operazioni che dovranno essere implementate nelle varie interfacce

    // Come interagisco con database degli Utenti?

