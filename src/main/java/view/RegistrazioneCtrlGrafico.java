package view;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrationBean;
import engineering.bean.UserBean;
import engineering.others.FxmlFileName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneCtrlGrafico {

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

    private ArrayList<String> preferences;

    /**
     * Gestisce l'evento di clic sul pulsante di ritorno (back).
     * Chiude la finestra corrente e avvia la schermata di login.
     *
     */
    @FXML
    protected void onBackClick(ActionEvent event){
        SceneController.getInstance().goBack(event);
    }
    // Gestisce l'evento di clic sul pulsante di registrazione.
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException{
        // L'utente chiede di registrarsi con una determinata mail e un nome utente
        RegistrationBean regBean = getData();
        UserBean userBean = new UserBean();

        if (regBean != null) {

            RegistrazioneCtrlApplicativo registrazioneCtrlApplicativo = new RegistrazioneCtrlApplicativo();
            //uso metodo controller per registrare un utente sul livello di persistenza (non so quale sia e non è importante)
            registrazioneCtrlApplicativo.registerUser(regBean);

            System.out.println("Utente registrato con successo");

            userBean.setEmail(regBean.getEmail());
            userBean.setPreferences(regBean.getPreferences());
            userBean.setUsername(regBean.getUsername());
            System.out.println("Recuperate informazioni per user bean");

            /* --------------- Mostro la home page -------------- */
            SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlFileName.HOME_PAGE_FXML,userBean);

            // Ho eliminato il metodo controlla se email esistente perché lo fa gia la query, ma è importante riuscire
            // a prendere l'esito della query prima di cambiare stage, dobbiamo assicurarci che tutto vada bene.

        }
    }

    // Ottiene i dati inseriti dall'utente dalla GUI e restituisce un oggetto UserBean.
    private RegistrationBean getData() {    // Prendo i dati

        String userName = name.getText().trim();
        String userEmail = email.getText().trim();
        String userPassword = password.getText();

        // Dati
        String userConfPw = confPassword.getText();

        preferences = retriveCheckList();

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConfPw.isEmpty()) {
            showError("CAMPI VUOTI");
        } else if (!verificaPassword(userPassword, userConfPw)) {
            showError("LE PASSWORD NON CORRISPONDONO");
        } else if (!checkMailCorrectness(userEmail)) {
            showError("EMAIL NON VALIDA");
        } else {
            return new RegistrationBean(userName, userEmail,userPassword,preferences,false, true);
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
        errorField.setText(message);
        errorField.setVisible(true);
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
