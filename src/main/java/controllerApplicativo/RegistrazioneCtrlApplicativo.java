package controllerApplicativo;

import engineering.bean.UserBean;
import engineering.dao.UserDAO_JSON;
import engineering.dao.UserDAO_mySQL;
import engineering.exceptions.EmailAlreadyInUse;
import model.User;

import java.sql.SQLException;

public class RegistrazioneCtrlApplicativo {

    public void registerUserAndrea(UserBean bean) {
        User user = new User(bean.getNome(), bean.getEmail(), bean.getPass(), bean.getPreferences());
        UserDAO_JSON dao = new UserDAO_JSON();
        dao.insertUser(user);
    }

    public void registerUserDB(UserBean bean) throws EmailAlreadyInUse, SQLException, ClassNotFoundException {
        User user = new User(bean.getNome(), bean.getEmail(), bean.getPass(), bean.getPreferences());
        UserDAO_mySQL.insertUser(user); // Qui devo chiamare UserDao qaundo farò implements
    }

}