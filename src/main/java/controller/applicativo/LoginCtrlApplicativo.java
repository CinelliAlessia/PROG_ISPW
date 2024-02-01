package controller.applicativo;

import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.dao.UserDAO;
import engineering.dao.UserDAOMySQL;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    public boolean verificaCredenziali(LoginBean bean) throws IOException {
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        UserDAO dao = persistenceType.createUserDAO();

        String password = dao.getPasswordByEmail(bean.getEmail());

        return password.equals(bean.getPassword());
    }

    public UserBean loadUser(LoginBean bean) throws IOException {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        UserDAO dao = persistenceType.createUserDAO();

        User user = dao.loadUser(bean.getEmail());
        return new UserBean(user.getUsername(),user.getEmail(),user.getPref(), user.isSupervisor());
    }

    public UserBean loadUserByEmailFromFS(String email) {
        return null;
    }
}