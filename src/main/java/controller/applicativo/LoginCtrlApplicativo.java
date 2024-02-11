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
    public ClientBean loadUser(LoginBean bean) throws UserDoesNotExist, EmailIsNotValid {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Login login = new Login(bean.getEmail(), bean.getPassword());           // Creo model Login per comunicare con il dao

        try{
            Client client = dao.loadUser(login);

            if(client instanceof User){
                System.out.println("Login APP: Client " + client +" Supervisor: " + client.isSupervisor());
                return new UserBean(client.getUsername(),client.getEmail(),client.getPreferences());
            } else if(client instanceof Supervisor){
                System.out.println("Login APP: Client " + client +" Supervisor: " + client.isSupervisor());
                return new SupervisorBean(client.getUsername(),client.getEmail(),client.getPreferences());
            }

        } catch (UserDoesNotExist e){
            throw new UserDoesNotExist();
        } catch (EmailIsNotValid e) {
            throw new EmailIsNotValid();
        }
        return null;
    }
}