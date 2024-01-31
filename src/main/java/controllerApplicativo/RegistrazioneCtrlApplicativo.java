package controllerApplicativo;

import engineering.bean.RegistrationBean;
import engineering.dao.TypesOfPersistenceLayer;
import engineering.dao.UserDAO;
import model.User;

import java.io.IOException;


import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class RegistrazioneCtrlApplicativo {

    public void registerUser(RegistrationBean regBean) throws IOException {
        // Leggi le preferenze dal file di configurazione
        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType();

        // Crea l'istanza corretta del DAO
        UserDAO dao = persistenceType.createDAO();

        // Crea l'utente e passa al DAO
        User user = new User(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());
        dao.insertUser(user);
    }


}
