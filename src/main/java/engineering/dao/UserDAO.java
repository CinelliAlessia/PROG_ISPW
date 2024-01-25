package engineering.dao;

import model.User;

public interface UserDAO {
    void insertUser(User user);

    String getPasswordByEmail(String email);
}

