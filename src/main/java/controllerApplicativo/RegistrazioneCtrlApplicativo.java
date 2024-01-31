package controllerApplicativo;

import engineering.bean.RegistrationBean;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.dao.UserDAO;
import model.User;

import java.io.IOException;


import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class RegistrazioneCtrlApplicativo {

    public void registerUser(RegistrationBean regBean) throws IOException {
        // Prendo il tipo di persistenza impostato nel file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        UserDAO dao = persistenceType.createUserDAO();

        // Crea l'utente (model)
        User user = new User(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());
        // Invio utente model al DAO
        dao.insertUser(user);
    }


}
