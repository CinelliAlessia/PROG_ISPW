package view.first;

import controller.applicativo.LoginCtrlApplicativo;
import engineering.bean.*;
import engineering.exceptions.*;

import engineering.others.Printer;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.first.utils.*;

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
    protected void onLoginClick(ActionEvent event) {
        Printer.logPrint("GUI Login: inizio");

        /* ------ Recupero informazioni dalla schermata di login ------ */
        String email = emailField.getText().trim();
        String pass = password.getText().trim();

        /* ------ Verifica dei parametri inseriti (validità sintattica) ------ */
        if (email.isEmpty() || pass.isEmpty()) {
            showError("Ci sono dei campi vuoti!");
        } else {
            /* ------ Creo il bean e imposto i parametri ------ */
            try{
                LoginBean loginBean = new LoginBean(email,pass);
                LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo(); // Creo istanza del Login controller applicativo

                loginCtrlApp.verificaCredenziali(loginBean);

                /* --------------- Credenziali corrette se non è scaturita l'eccezione -------------- */
                ClientBean clientBean = loginCtrlApp.loadUser(loginBean); // Ottengo istanza di clientBean
                Printer.logPrint(String.format("GUI Login: Credenziali corrette, Supervisor: %b", clientBean.isSupervisor()));

                /* --------------- Mostro la home page -------------- */
                sceneController.goToScene(event, FxmlFileName.HOME_PAGE_FXML, clientBean); // Lascio alla homePage GUI la responsabilità di differenziare tra UserBean e SupervisorBean

            } catch (IncorrectPassword | UserDoesNotExist | InvalidEmailException e){
                showError(e.getMessage());
            }
        }
    }

    @FXML
    protected void onRegisterClick(ActionEvent event) {
        //Push della scena corrente nello stack delle scene e show() della scena seguente
        sceneController.goToScene(event, FxmlFileName.REGISTRATION_FXML);
    }

    @FXML
    protected void onGuestClick(ActionEvent event) {
        /* Creiamo un'istanza di HomePageCtrlGrafico in cui T è sostituito con UserBean */
        sceneController.goToScene(event, FxmlFileName.HOME_PAGE_FXML);
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}