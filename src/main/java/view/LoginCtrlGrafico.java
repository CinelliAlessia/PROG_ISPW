package view;

import controllerApplicativo.LoginCtrlApplicativo;
import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import engineering.others.FxmlName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginCtrlGrafico {

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Label textLogin;

    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException, SQLException {

        /* ------ Recupero informazioni dalla schermata di login ------ */
        String email = username.getText();
        String pass = password.getText();

        /* ------ Verifica dei parametri inseriti (validità sintattica) ------ */
        if (!checkMailCorrectness(email)){
            textLogin.isVisible();
            textLogin.setText("Email non valida");
        } else {
            /* ------ Creo la bean e imposto i parametri ------ */
            LoginBean loginBean = new LoginBean(email,pass);

            /* ------ Creo istanza del Login controller applicativo e utilizzo i metodi di verifica credenziali ------ */
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo(); //Dovrebbe essere static

            if (loginCtrlApp.verificaCredenziali(loginBean)) {
                /* --------------- Credenziali corrette -------------- */
                System.out.println("CREDENZIALI CORRETTE -> Recupero l'istanza di bean ");

                UserBean userBean = loginCtrlApp.loadUser(loginBean);
                //################### Inserire il caricamento da FS ############

                if(userBean != null){
                    //userBean.setRegistered(); // Indica che l'utente con cui sto accedendo è registrato
                    System.out.println("Utente registrato, ho recuperato tutto lo user bean");

                    /* --------------- Mostro la home page -------------- */
                    SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlName.HOME_PAGE_FXML,userBean);

                }
            } else { /* --------------- Credenziali non valide --------------*/
                textLogin.isVisible();
                textLogin.setText("Credenziali errate");
            }
        }
    }
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException {
        //Push della scena corrente nello stack delle scene e show della scena seguente
        SceneController.getInstance().goToScene(event, FxmlName.REGISTRATION_FXML);
    }
    @FXML
    protected void onGuestClick(ActionEvent event) throws IOException {
        SceneController.getInstance().<HomePageCtrlGrafico>goToScene(event, FxmlName.HOME_PAGE_FXML,null);
    }

    public boolean checkMailCorrectness(String email) {
        /*Controllo basico se ha almeno una @ e un punto dopo la @? */
        // Definisci il pattern per una email valida
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Crea un oggetto Pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Crea un oggetto Matcher con la stringa email da verificare
        Matcher matcher = pattern.matcher(email);

        // Verifica se il formato dell'email è valido
        return matcher.matches();
    }
}