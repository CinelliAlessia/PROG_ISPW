package view;

import controller.applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrationBean;
import engineering.bean.UserBean;
import engineering.others.FxmlFileName;
import engineering.others.GenreMenager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.validator.routines.EmailValidator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(pop);
        checkBoxList.add(indie);
        checkBoxList.add(classic);
        checkBoxList.add(rock);
        checkBoxList.add(electronic);
        checkBoxList.add(house);
        checkBoxList.add(hipHop);
        checkBoxList.add(jazz);
        checkBoxList.add(acoustic);
        checkBoxList.add(reb);
        checkBoxList.add(country);
        checkBoxList.add(alternative);

        // Recupero preferenze aggiornate
        ArrayList<String> preferences = GenreMenager.retrieveCheckList(checkBoxList);

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
