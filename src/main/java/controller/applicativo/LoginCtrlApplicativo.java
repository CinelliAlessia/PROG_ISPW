package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import model.Client;
import model.Login;
import model.Supervisor;
import model.User;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class LoginCtrlApplicativo {

    /** Il metodo accede allo strato di persistenza per verificare se le credenziali per l'accesso sono valide
     * L'email deve essere registrata
     * La password associata deve essere come quella inserita in fate di login
     * Il loginBean contiene il campo mail e il campo password
     * Effettua una Query per recuperare la password e confrontarla con quella inserita  */

    public void verificaCredenziali(LoginBean bean) throws IncorrectPassword, UserDoesNotExist {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        String password = dao.getPasswordByEmail(bean.getEmail());  // ####### se la mail non esiste da errore, trasformiamo in eccezione

        if (!password.equals(bean.getPassword())){
            throw new IncorrectPassword();
        }
    }

    /** Recupera l'User dalla persistenza e crea una nuova istanza di UserBean */
    public ClientBean loadUser(LoginBean bean) throws UserDoesNotExist, InvalidEmailException {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Login login = new Login(bean.getEmail(), bean.getPassword());           // Creo model Login per comunicare con il dao

        try{
            Client client = dao.loadUser(login);

            if(client instanceof User){
                return new UserBean(client.getUsername(),client.getEmail(),client.getPreferences());
            } else if(client instanceof Supervisor){
                return new SupervisorBean(client.getUsername(),client.getEmail(),client.getPreferences());
            }

        } catch (UserDoesNotExist e){
            throw new UserDoesNotExist();
        } catch (InvalidEmailException e) {
            throw new InvalidEmailException();
        }
        return null;
    }
}