package view;

import controller.applicativo.LoginCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.IncorrectPassword;
import engineering.exceptions.UserDoesNotExist;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.validator.routines.EmailValidator;
import view.utils.FxmlFileName;
import view.utils.SceneController;

import java.io.IOException;

public class LoginCtrlGrafico {

    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField password;
    @FXML
    private TextField emailField;

    private final SceneController sceneController = new SceneController();

    /** Metodo utilizzato per l'accesso all'applicazione
     * Vengono effettuati i primi controlli sui parametri inseriti
     * Una prima Query per prendere la password associata alla mail e confrontarla con quella inserita
     * Una seconda Query per recuperare tutta l'istanza di userBean

     * Ottimizzazioni: Una sola query, se la password è corretta recupero direttamente lo user Bean senza chiedere
     * */
    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException {

        /* ------ Recupero informazioni dalla schermata di login ------ */
        String email = emailField.getText();
        String pass = password.getText();

        /* ------ Verifica dei parametri inseriti (validità sintattica) ------ */
        if (!checkMailCorrectness(email)){ // NOn va fatto qui il controllo ma in Login Bean
            showError("Email non valida");
        } else {

            /* ------ Creo la bean e imposto i parametri ------ */
            LoginBean loginBean = new LoginBean(email,pass);
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo(); // Creo istanza del Login controller applicativo

            try{
                if (loginCtrlApp.verificaCredenziali(loginBean)) { /* --------------- Credenziali corrette -------------- */

                    System.out.println("CREDENZIALI CORRETTE -> Recupero l'istanza di bean ");

                    UserBean userBean = loginCtrlApp.loadUser(loginBean);

                    /* --------------- Mostro la home page -------------- */
                    sceneController.<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,userBean);
                    System.out.println("Utente acceduto, ho recuperato tutto lo user bean");

                }
            } catch (IncorrectPassword | UserDoesNotExist e){
                showError(e.getMessage());
            }
        }
    }

    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException {
        //Push della scena corrente nello stack delle scene e show() della scena seguente
        sceneController.<RegistrazioneCtrlGrafico>goToScene(event, FxmlFileName.REGISTRATION_FXML,null);
    }

    @FXML
    protected void onGuestClick(ActionEvent event) throws IOException {
        sceneController.<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,null);
    }

    private boolean checkMailCorrectness(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}