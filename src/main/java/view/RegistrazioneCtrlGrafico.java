package view;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrationBean;
import engineering.bean.UserBean;
import engineering.others.*;
import org.apache.commons.validator.routines.EmailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.io.IOException;
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
        SceneController.getInstance().goBack(event);
    }

    /** Gestisce l'evento di clic sul pulsante di registrazione.
     * Se la registrazione ha successo viene ottenuto lo UserBean dal controller Applicativo e
     * si imposta la scena sulla Home Page */
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException{
        // L'utente chiede di registrarsi con una determinata mail e un nome utente
        RegistrationBean regBean = getData();

        if (regBean != null) {

            RegistrazioneCtrlApplicativo registrazioneCtrlApplicativo = new RegistrazioneCtrlApplicativo(); //meglio static?
            UserBean userBean = registrazioneCtrlApplicativo.registerUser(regBean);

            if(userBean != null){
                System.out.println("Utente registrato con successo");

                /* --------------- Mostro la home page -------------- */
                SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,userBean);

            } else {
                showError("REGISTRAZIONE NON RIUSCITA");
            }

        }
    }

    /** Ottiene i dati inseriti dall'utente dalla GUI e restituisce un oggetto RegistrationBean. */
    private RegistrationBean getData() {

        String userName = name.getText().trim();
        String userEmail = email.getText().trim();

        String userPassword = password.getText();
        String userConfPw = confPassword.getText();

        // Recupero preferenze aggiornate
        List<String> preferences = GenreManager.retrieveCheckList(checkBoxList);

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConfPw.isEmpty()) {
            showError("CAMPI VUOTI");
        } else if (!verificaPassword(userPassword, userConfPw)) {
            showError("LE PASSWORD NON CORRISPONDONO");
        } else if (!checkMailCorrectness(userEmail)) {
            showError("EMAIL NON VALIDA");
        } else {
            return new RegistrationBean(userName, userEmail,userPassword,preferences,false);
        }

        return null;
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorField.setText(message);
        errorField.setVisible(true);
    }

    // Verifica se le password inserite coincidono.
    private boolean verificaPassword(String password, String confermaPassword) {
        return password.equals(confermaPassword);
    }

    private boolean checkMailCorrectness(String email) {
        return EmailValidator.getInstance().isValid(email);
    }


}
