package controllerApplicativo;

import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import engineering.dao.UserDAO_JSON;
import engineering.dao.UserDAO_mySQL;
import model.User;

import java.sql.SQLException;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    public LoginCtrlApplicativo() {
    }

    public boolean verificaCredenziali(LoginBean bean) {
        // Ottieni la password dal database usando l'email
        UserDAO_mySQL userDAO = new UserDAO_mySQL();
        String passwordFromDB = userDAO.getPasswordByEmail(bean.getEmail());

        // Stessa cosa anche per il file system, ma qui(?)
        UserDAO_JSON userDAOFS = new UserDAO_JSON();
        String passwordFromFS = userDAOFS.getPasswordByEmail(bean.getEmail());

        // Verifica se le credenziali sono corrette ########## ad Alessia non piace ###########
        return (passwordFromDB != null && passwordFromDB.equals(bean.getPassword()) && passwordFromFS.equals(bean.getPassword()));
    }

    public UserBean loadUser(LoginBean bean) throws SQLException {
        User user = UserDAO_mySQL.loadUser(bean.getEmail());
        return new UserBean(user.getUsername(),user.getEmail(),user.getPref(), user.isSupervisor(),true)    ;
    }

    public UserBean loadUserByEmailFromFS(String email) {
        return null;
    }
}