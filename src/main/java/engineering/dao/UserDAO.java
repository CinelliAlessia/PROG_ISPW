package engineering.dao;

import model.User;

public interface UserDAO {
    void insertUser(User user);

    String getPasswordByEmail(String email);

    void saveUser(User userInstance);
    void deleteUser(User userIstance);
    void retreiveUserByUserName (String userName);
    void retreiveUserByUserId (String userId);
}

    // Devo definire le operazioni che dovranno essere implementate nelle varie interfacce

    // Come interagisco con database degli Utenti?
