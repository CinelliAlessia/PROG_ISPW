package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.pattern.abstract_factory.DAOFactory;
import model.*;

import java.util.*;

public class LoginCtrlApplicativo {

    /** Il metodo accede allo strato di persistenza per verificare se le credenziali per l'accesso sono valide
     * L'email deve essere registrata
     * La password associata deve essere come quella inserita in fate di login
     * Il loginBean contiene il campo mail e il campo password
     * Effettua una Query per recuperare la password e confrontarla con quella inserita  */

    public void verificaCredenziali(LoginBean bean) throws IncorrectPassword, UserDoesNotExist {

        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        String password = dao.getPasswordByEmail(bean.getEmail());  // ####### se la mail non esiste da errore, trasformiamo in eccezione

        if (!password.equals(bean.getPassword())){
            throw new IncorrectPassword();
        }
    }

    /** Recupera l'User dalla persistenza e crea una nuova istanza di UserBean */
    public ClientBean loadUser(LoginBean bean) throws UserDoesNotExist, InvalidEmailException {

        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();
        Login login = new Login(bean.getEmail(), bean.getPassword());           // Creo model Login per comunicare con il dao

        try{
            Client client = dao.loadClient(login);

            if(!client.isSupervisor()){
                UserBean userBean = new UserBean(client.getUsername(),client.getEmail(),client.getPreferences());

                List<NoticeBean> noticeBeanList = new ArrayList<>();
                List<Notice> noticeList = retriveNotice(client);

                for(Notice notice: noticeList){
                    NoticeBean noticeBean = new NoticeBean(notice.getTitle(),notice.getBody(),notice.getUsernameAuthor());
                    noticeBeanList.add(noticeBean);
                }
                userBean.setNotices(noticeBeanList);
                return userBean;

            } else {
                return new SupervisorBean(client.getUsername(),client.getEmail(),client.getPreferences());
            }

        } catch (UserDoesNotExist e){
            throw new UserDoesNotExist();
        } catch (InvalidEmailException e) {
            throw new InvalidEmailException();
        }
    }

    public List<Notice> retriveNotice(Client user){
        NoticeDAO dao = DAOFactory.getDAOFactory().createNoticeDAO();
        return dao.retrieveNotice(user);
    }
}