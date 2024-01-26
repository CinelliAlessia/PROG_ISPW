package view;

import controllerApplicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.UserBean;
import engineering.exceptions.EmailAlreadyInUse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneCtrlGrafico {
    // ---------Nodi interfaccia----------
    @FXML
    public Button back, registrazione;

    @FXML
    public TextField name, email;

    @FXML
    public PasswordField password, conf_password;

    @FXML
    public Text error_field;

    @FXML
    public CheckBox pop, indie, classic, rock, electronic, house, hipHop, jazz, acoustic, reb, country, alternative;
    // Aggiungi checkbox per altri generi musicali


    private ArrayList<String> preferences;
    private String user_email, user_password, user_conf_pw; // Dati

    // Inizia la visualizzazione della finestra di registrazione.
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/registrazione.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Registrazione");
        stage.setScene(scene);
        stage.show();
    }

    // Gestisce l'evento di clic sul pulsante di ritorno (back).
    @FXML
    protected void onBackClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        LoginCtrlGrafico loginCtrlGrafico = new LoginCtrlGrafico();
        loginCtrlGrafico.start(stage);
    }

    // Gestisce l'evento di clic sul pulsante di registrazione.
    @FXML
    protected void onRegisterClick() throws IOException, EmailAlreadyInUse, SQLException, ClassNotFoundException {

        UserBean userBean = getData();

        if (userBean != null) {
            RegistrazioneCtrlApplicativo reg_CtrlApp = new RegistrazioneCtrlApplicativo();
            reg_CtrlApp.registerUserDB(userBean); //uso metodo controller per registrare un utente sul DB
            reg_CtrlApp.registerUserFS(userBean); //uso metodo controller per registrare un utente sul FS
            // Se tutto è stato fatto è possibile impostare la scena
            Stage stage = (Stage) registrazione.getScene().getWindow();
            HomePageCtrlGrafico homePageCtrlGrafico = new HomePageCtrlGrafico();
            homePageCtrlGrafico.start(stage);
        }
    }

    // Ottiene i dati inseriti dall'utente dalla GUI e restituisce un oggetto UserBean.
    private UserBean getData() {    // Prendo i dati

        String user_name = name.getText().trim();
        user_email = email.getText().trim();
        user_password = password.getText();
        user_conf_pw = conf_password.getText();

        preferences = retriveCheckList();

        if (user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_conf_pw.isEmpty()) {
            showError("CAMPI VUOTI");
        } else if (!verificaPassword(user_password, user_conf_pw)) {
            showError("LE PASSWORD NON CORRISPONDONO");
        } else if (!verificaEmailCorrect(user_email)) {
            showError("EMAIL NON VALIDA");
        } else if (!verificaRegistrazioneEsistente(user_email)) {
            showError("UTENTE GIA REGISTRATO");
        } else {
            return new UserBean(user_name, user_email, user_password, preferences);
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
    private boolean verificaEmailCorrect(String email) {
        // Controllo basico se ha almeno una @ e un punto dopo la @?
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // DA IMPLEMENTARE
    // Verifica se l'indirizzo email è già registrato nel sistema.
    private boolean verificaRegistrazioneEsistente(String email) {
        // Dobbiamo fare una query?
        return true;
    }
}
