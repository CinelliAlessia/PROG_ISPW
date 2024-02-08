package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.IncorrectPassword;
import engineering.exceptions.UserDoesNotExist;
import model.User;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case

    /** Il metodo accede allo strato di persistenza per verificare se le credenziali per l'accesso sono valide
     * L'email deve essere registrata
     * La password associata deve essere come quella inserita in fate di login
     * Il loginBean contiene il campo mail e il campo password*/
    /** @return un booleano, per verificare la correttezza dell'operazione effettuata
     * Effettua una Query per recuperare la password e confrontarla con quella inserita  */
    public boolean verificaCredenziali(LoginBean bean) throws IncorrectPassword, UserDoesNotExist {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        String password = dao.getPasswordByEmail(bean.getEmail());  // ####### se la mail non esiste da errore, trasformiamo in eccezione

        if (!password.equals(bean.getPassword())){
            throw new IncorrectPassword();
        } else {
            return true;
        }
    }

    /** Recupera l'User dalla persistenza e crea una nuova istanza di UserBean */
    public UserBean loadUser(LoginBean bean) throws UserDoesNotExist {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        User user = dao.loadUser(bean.getEmail());
        System.out.println("Load User recuperato: " + user + " " + user.getEmail() + " " + user.isSupervisor());

        if(user.isSupervisor()){
            return new SupervisorBean(user.getUsername(),user.getEmail(),user.getPref());
        } else {
            return new UserBean(user.getUsername(),user.getEmail(),user.getPref());
        }
    }
}