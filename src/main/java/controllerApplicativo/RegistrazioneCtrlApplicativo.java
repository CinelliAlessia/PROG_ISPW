package controllerApplicativo;

import java.util.ArrayList;

import engineering.bean.UserBean;
import engineering.dao.UserDAO;
import engineering.dao.UserDAO_MONGO;
import engineering.dao.UserDAO_JSON;
import model.User;

public class RegistrazioneCtrlApplicativo {

    public void registerUser(UserBean bean) {
        User user = new User(bean.getNome(), bean.getEmail(), bean.getPass(), new ArrayList<>());
        UserDAO daoJson = new UserDAO_JSON();
        UserDAO daoMongoDB = new UserDAO_MONGO();
        daoJson.saveUser(user);
        daoMongoDB.saveUser(user);
    }

}