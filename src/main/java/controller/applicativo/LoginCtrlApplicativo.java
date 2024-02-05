package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.User;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    /**
     * Il metodo accede allo strato di persistenza per verificare se le credenziali per l'accesso sono valide
     * L'email deve essere registrata
     * La password associata deve essere come quella inserita in fate di login
     * Il loginBean contiene il campo mail e il campo password*/
    public boolean verificaCredenziali(LoginBean bean) {
        // Prendo il tipo di persistenza impostato nel file di configurazione
     TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        UserDAO dao = persistenceType.createUserDAO();

        String password = dao.getPasswordByEmail(bean.getEmail());

        // Verifica se la password ottenuta dal DAO è null

        // L'utente non è registrato
        if (password == null) {
            return false;
        }
        //Non posso differire, se utente non registrato o password sbagliata
        return password.equals(bean.getPassword());
    }

    /** Recupera l'User dalla persistenza e crea una nuova istanza di UserBean */
    public UserBean loadUser(LoginBean bean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();
        UserDAO dao = persistenceType.createUserDAO();

        User user = dao.loadUser(bean.getEmail());
        return new UserBean(user.getUsername(),user.getEmail(),user.getPref(), user.isSupervisor());
    }
}