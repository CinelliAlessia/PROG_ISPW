package view;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import org.apache.commons.validator.routines.EmailValidator;

import engineering.bean.*;
import engineering.exceptions.*;

import view.utils.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

import java.net.URL;
import java.util.*;

public class RegistrazioneCtrlGrafico implements Initializable {

    // ---------Nodi interfaccia----------
    @FXML
    private TextField name;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confPassword;

    @FXML
    private Text errorField;

    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;

    private List<CheckBox> checkBoxList;

    private SceneController sceneController;

    public void setAttributes(SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }

    /**
     * Gestisce l'evento di clic sul pulsante di ritorno (back).
     * Chiude la finestra corrente e avvia la schermata di login.
     */
    @FXML
    protected void onBackClick(ActionEvent event){
        sceneController.goBack(event);
    }

    /** Gestisce l'evento di clic sul pulsante di registrazione.
     * Se la registrazione ha successo viene ottenuto lo UserBean dal controller Applicativo e
     * si imposta la scena sulla Home Page */
    @FXML
    protected void onRegisterClick(ActionEvent event){
        LoginBean regBean = new LoginBean();
        getData(regBean);

        if (!regBean.getEmail().isEmpty()) {

            RegistrazioneCtrlApplicativo registrazioneCtrlApplicativo = new RegistrazioneCtrlApplicativo();

            try {
                ClientBean clientBean = new UserBean(regBean.getEmail());
                registrazioneCtrlApplicativo.registerUser(regBean, clientBean);

                System.out.println("GUI Registrazione: Utente registrato con successo");

                /* --------------- Mostro la home page -------------- */
                sceneController.<HomePageCtrlGrafico<ClientBean>>goToScene(event, FxmlFileName.HOME_PAGE_FXML, clientBean);

            } catch (EmailAlreadyInUse | UsernameAlreadyInUse | EmailIsNotValid e) {
                showError(e.getMessage());
            }
        }
    }

    /** Ottiene i dati inseriti dall'utente dalla GUI e restituisce un oggetto RegistrationBean. */
    private void getData(LoginBean loginBean) {

        String username = name.getText().trim(); //.trim() Rimuove gli spazi da inizio e fine stringa
        String userEmail = email.getText().trim();

        String userPassword = password.getText().trim();
        String userConfPw = confPassword.getText().trim();

        // Recupero preferenze aggiornate
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);

        if (username.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConfPw.isEmpty()) {
            showError("CAMPI VUOTI");
        } else if (!verificaPassword(userPassword, userConfPw)) {
            showError("LE PASSWORD NON CORRISPONDONO");
        } else {
            try{
                loginBean.setUsername(username);
                loginBean.setEmail(userEmail);
                loginBean.setPassword(userPassword);
                loginBean.setPreferences(preferences);
            } catch (EmailIsNotValid e){
                showError(e.getMessage());
            }

        }
    }

    /** Mostra un messaggio di errore nell'interfaccia utente */
    private void showError(String message) {
        //
        errorField.setText(message);
        errorField.setVisible(true);
    }

    /** Verifica se le password inserite dall'utente coincidono. */
    private boolean verificaPassword(String password, String confermaPassword) {
        return password.equals(confermaPassword);
    }

    /** Verifica se la email inserita rispetta i canoni per essere una email
     * Dovrebbe essere fatto nel Bean */
    private boolean checkMailCorrectness(String email) {
        return EmailValidator.getInstance().isValid(email);
    }


}
