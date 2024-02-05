package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import model.User;


import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class RegistrazioneCtrlApplicativo {

    public UserBean registerUser(RegistrationBean regBean){
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        UserDAO dao = persistenceType.createUserDAO();

        // TODO Verifica che non esista già un utente con stessa mail e nome utente
        // Alessia fatto

        // Crea l'utente (model)
        User user = new User(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());

        UserBean userBean = null;
        // Invio utente model al DAO
        if(!dao.insertUser(user)){
            userBean = new UserBean(user.getUsername(),user.getEmail(),user.getPref());
        }
        return userBean;
    }


}
