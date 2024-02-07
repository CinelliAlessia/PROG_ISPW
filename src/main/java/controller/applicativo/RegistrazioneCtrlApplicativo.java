package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.User;


import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class RegistrazioneCtrlApplicativo {

    public UserBean registerUser(RegistrationBean regBean){
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        UserDAO dao = persistenceType.createUserDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea l'utente (model) per inviarlo al DAO
        User user = new User(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());
        // Creo bean per la risposta da inviare al ctrl Grafico
        UserBean userBean = null;

        // Invio utente (model) al DAO e verifico se l'operazione di aggiunta utente è andata a buon fine
        if(dao.insertUser(user)){
            userBean = new UserBean(user.getUsername(),user.getEmail(),user.getPref());
        }
        return userBean;
    }
}
