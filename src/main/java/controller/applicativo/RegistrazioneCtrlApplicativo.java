package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.pattern.abstract_factory.DAOFactory;
import model.*;

public class RegistrazioneCtrlApplicativo {

    // CLI PROBLEMA: Verificare email e username prima di creare realmente l'utente

    /** Query al dao per registrare un utente */
    public void registerUser(LoginBean regBean, ClientBean clientBean) throws EmailAlreadyInUseException, UsernameAlreadyInUseException, InvalidEmailException {

        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        // Crea l'utente (model) per inviarlo al DAO
        Login registration = new Login(regBean.getUsername(), regBean.getEmail(), regBean.getPassword(), regBean.getPreferences());

        try{
            dao.insertClient(registration);
        } catch (EmailAlreadyInUseException e){
            throw new EmailAlreadyInUseException();
        } catch (UsernameAlreadyInUseException e){
            throw new UsernameAlreadyInUseException();
        }

        /* SIAMO SICURI CHE L'UTENTE CHE SI REGISTRA SIA UN UserBean
        * NON CI SI PUO REGISTRARE COME UN Supervisore */

        UserBean userBean = (UserBean) clientBean;
        userBean.setUsername(registration.getUsername());
        userBean.setEmail(regBean.getEmail());
        userBean.setPreferences(registration.getPreferences());
    }

    public void tryCredentialExisting(LoginBean regBean) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {
        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Login login = new Login();
        login.setEmail(regBean.getEmail());
        login.setUsername(regBean.getUsername());
        dao.tryCredentialExisting(login);
    }

}
