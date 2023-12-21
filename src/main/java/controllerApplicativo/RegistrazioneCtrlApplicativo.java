package controllerApplicativo;

import java.util.ArrayList;

import engineering.bean.UserBean;
import engineering.dao.UserDAOmongo;
import engineering.dao.UserDAO_JSON;
import model.User;

public class RegistrazioneCtrlApplicativo {

    public void registerUserAndrea(UserBean bean) {
        String nome = bean.getNome(), mail = bean.getEmail(), pass = bean.getPass();
        ArrayList<String> pref = bean.getPreferences();
        User user = new User(nome,mail,pass,pref);
        UserDAO_JSON dao = new UserDAO_JSON();
        dao.registerUserAndrea(user);
    }

    public void registerUserDB(UserBean bean) {
        User user = new User(bean.getNome(), bean.getEmail(), bean.getPass(), new ArrayList<>());
        UserDAOmongo userDAOmongo = new UserDAOmongo();
        userDAOmongo.insertUser(user);
    }

}