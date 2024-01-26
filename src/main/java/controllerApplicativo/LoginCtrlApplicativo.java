package controllerApplicativo;

import engineering.bean.LoginBean;
import engineering.dao.UserDAO; // Assumendo che tu abbia una classe UserDAO per gestire l'accesso al database.
import engineering.dao.UserDAO_JSON;
import engineering.dao.UserDAO_mySQL;
import model.User;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    public LoginCtrlApplicativo() {
    }


    // Implementa la logica dello use case
    public boolean verificaCredenziali(LoginBean bean) {
        UserDAO_mySQL userDAO = new UserDAO_mySQL();
        UserDAO_JSON userDAOFS = new UserDAO_JSON();
        // Ottieni la password dal database usando l'email
        String passwordFromDB = userDAO.getPasswordByEmail(bean.getEmail());
        String passwordFromFS = userDAOFS.getPasswordByEmail(bean.getEmail());

        // Verifica se le credenziali sono corrette
        return passwordFromDB != null && passwordFromDB.equals(bean.getPassword()) && passwordFromFS.equals(bean.getPassword());
    }
}