package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import model.*;


import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class RegistrazioneCtrlApplicativo {

    /** Query al dao per registrare un utente */
    public void registerUser(LoginBean regBean, ClientBean clientBean) throws EmailAlreadyInUse, UsernameAlreadyInUse, EmailIsNotValid {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();                           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea l'utente (model) per inviarlo al DAO
        Login registration = new Login(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());

        /*
        try{
            dao.loadUser(registration);
        } catch (UserDoesNotExist _){
            dao.retrieveUserByUsername(registration);
        }*/

        try{
            dao.insertUser(registration);
        } catch (EmailAlreadyInUse e){
            throw new EmailAlreadyInUse();
        } catch (UsernameAlreadyInUse e){
            throw new UsernameAlreadyInUse();
        }

        /* SIAMO SICURI CHE L'UTENTE CHE SI REGISTRA SIA UN UserBean
        * NON CI SI PUO REGISTRARE COME UN SupervisorBean */

        UserBean userBean = (UserBean) clientBean;
        userBean.setUsername(registration.getUsername());
        userBean.setEmail(regBean.getEmail());
        userBean.setPreferences(registration.getPreferences());
    }
}
