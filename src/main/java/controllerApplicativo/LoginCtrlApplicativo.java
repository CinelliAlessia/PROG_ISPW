package controllerApplicativo;

import engineering.bean.LoginBean;
import engineering.dao.UserDAO; // Assumendo che tu abbia una classe UserDAO per gestire l'accesso al database.
import engineering.dao.UserDAO_mySQL;
import model.User;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    /* public boolean verificaCredenziali(LoginBean bean) {
        // Esempio di verifica delle credenziali rigida
        // Credenziali errate
        return bean.getEmail().equals("admin@gmail.com") && bean.getPassword().equals("1234"); // Credenziali corrette
    }*/

    /*
    private final UserDAO userDAO; // Inizializzato nel costruttore o mediante injection.

    public LoginCtrlApplicativo(UserDAO userDAO) {
        this.userDAO = userDAO;
    }*/


    public LoginCtrlApplicativo() {
    }


    // Implementa la logica dello use case
    public boolean verificaCredenziali(LoginBean bean) {
        UserDAO_mySQL userDAO = new UserDAO_mySQL();
        // Ottieni la password dal database usando l'email
        String passwordFromDB = userDAO.getPasswordByEmail(bean.getEmail());

        // Verifica se le credenziali sono corrette
        return passwordFromDB != null && passwordFromDB.equals(bean.getPassword());
    }
}