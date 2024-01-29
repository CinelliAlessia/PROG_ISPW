package controllerApplicativo;

import engineering.bean.RegistrationBean;
import engineering.bean.UserBean;
import engineering.dao.UserDAO_JSON;
import engineering.dao.UserDAO_mySQL;
import engineering.exceptions.EmailAlreadyInUse;
import model.User;

import java.sql.SQLException;

public class RegistrazioneCtrlApplicativo {

    public void registerUserFS(RegistrationBean bean) {
        User user = new User(bean.getUsername(), bean.getEmail(), bean.getPassword(), bean.getPreferences());
        UserDAO_JSON dao = new UserDAO_JSON();
        dao.insertUser(user); // passiamo al DAO
    }

    public void registerUserDB(RegistrationBean bean) throws EmailAlreadyInUse, SQLException, ClassNotFoundException {
        User user = new User(bean.getUsername(), bean.getEmail(), bean.getPassword(), bean.getPreferences());
        UserDAO_mySQL.insertUser(user); // Qui devo chiamare UserDao quando farò implements
    }

}