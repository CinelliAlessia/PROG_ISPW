package view;

import controllerApplicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrationBean;
import engineering.bean.UserBean;
import engineering.exceptions.EmailAlreadyInUse;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneCtrlGrafico {

    // ---------Nodi interfaccia----------
    @FXML
    private Button back, registrazione;

    @FXML
    private TextField name, email;

    @FXML
    private PasswordField password, conf_password;

    @FXML
    private Text error_field;

    @FXML
    private CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz, acoustic, reb, country, alternative;

    private ArrayList<String> preferences;

    /**
     * Gestisce l'evento di clic sul pulsante di ritorno (back).
     * Chiude la finestra corrente e avvia la schermata di login.
     *
     * @throws IOException Se si verifica un errore durante il caricamento della schermata di login.
     */
    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goBack(event);
    }
    // Gestisce l'evento di clic sul pulsante di registrazione.
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException{

        RegistrationBean regBean = getData();
        UserBean userBean = new UserBean();

        if (regBean != null) {

            RegistrazioneCtrlApplicativo reg_CtrlApp = new RegistrazioneCtrlApplicativo();
            //############ questo va corretto con una sola chiamata ###############
            reg_CtrlApp.registerUser(regBean); //uso metodo controller per registrare un utente sul livello di persistenza

            System.out.println("Utente registrato con successo");

            userBean.setEmail(regBean.getEmail());
            userBean.setPreferences(regBean.getPreferences());
            userBean.setUsername(regBean.getUsername());
            System.out.println("Recuperate informazioni per user bean");

            /* --------------- Mostro la home page -------------- */
            SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlName.HOME_PAGE_FXML,userBean);

            // Ho eliminato il metodo controlla se email esistente perché lo fa gia la query, ma è importante riuscire
            // a prendere l'esito della query prima di cambiare stage, dobbiamo assicurarci che tutto vada bene.

        }
    }

    // Ottiene i dati inseriti dall'utente dalla GUI e restituisce un oggetto UserBean.
    private RegistrationBean getData() {    // Prendo i dati

        String user_name = name.getText().trim();
        String user_email = email.getText().trim();
        String user_password = password.getText();

        // Dati
        String user_conf_pw = conf_password.getText();

        preferences = retriveCheckList();

        if (user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_conf_pw.isEmpty()) {
            showError("CAMPI VUOTI");
        } else if (!verificaPassword(user_password, user_conf_pw)) {
            showError("LE PASSWORD NON CORRISPONDONO");
        } else if (!checkMailCorrectness(user_email)) {
            showError("EMAIL NON VALIDA");
        } else {
            return new RegistrationBean(user_name, user_email,user_password,preferences,false, true);
        }

        return null;
    }

    private ArrayList<String> retriveCheckList(){
        // Inizializza la lista dei generi musicali selezionati
        preferences = new ArrayList<>();

        // Aggiungi i generi musicali selezionati alla lista
        if (pop.isSelected()) {
            preferences.add("Pop");
        }
        if (indie.isSelected()) {
            preferences.add("Indie");
        }
        if (classic.isSelected()) {
            preferences.add("Classic");
        }
        if (rock.isSelected()) {
            preferences.add("Rock");
        }
        if (electronic.isSelected()) {
            preferences.add("Electronic");
        }
        if (house.isSelected()) {
            preferences.add("House");
        }
        if (hipHop.isSelected()) {
            preferences.add("HipHop");
        }
        if (jazz.isSelected()) {
            preferences.add("Jazz");
        }
        if (acoustic.isSelected()) {
            preferences.add("Acoustic");
        }
        if (reb.isSelected()) {
            preferences.add("REB");
        }
        if (country.isSelected()) {
            preferences.add("Country");
        }
        if (alternative.isSelected()) {
            preferences.add("Alternative");
        }
        return preferences;
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        error_field.setText(message);
        error_field.setVisible(true);
    }

    // Verifica se le password inserite coincidono.
    private boolean verificaPassword(String password, String confermaPassword) {
        return password.equals(confermaPassword);
    }

    // Verifica la correttezza del formato dell'indirizzo email.
    private boolean checkMailCorrectness(String email) {
        // Controllo basico se ha almeno una @ e un punto dopo la @?
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
