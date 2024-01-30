package view;

import controllerApplicativo.LoginCtrlApplicativo;
import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginCtrlGrafico {
    @FXML
    private Button login, register,guest;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    @FXML
    private Label textLogin;


    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Access Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

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
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();

            if (loginCtrlApp.verificaCredenziali(loginBean)) {
                /* --------------- Credenziali corrette -------------- */
                System.out.println("CREDENZIALI CORRETTE -> Recupero l'istanza di bean ");

                UserBean userBean = loginCtrlApp.loadUser(loginBean);

                if(userBean != null){
                    //userBean.setRegistered(); // Indica che l'utente con cui sto accedendo è registrato
                    System.out.println("Utente registrato, ho recuperato tutto lo user bean");

                    /* --------------- Mostro la home page -------------- */
                    Stage stage = (Stage) login.getScene().getWindow();
                    HomePageCtrlGrafico homePageCGUI = new HomePageCtrlGrafico();
                    homePageCGUI.start(stage, userBean);
                }
            } else { /* --------------- Credenziali non valide --------------*/
                textLogin.isVisible();
                textLogin.setText("Credenziali errate");
            }
        }
    }
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException { // Non devo fa controlli

        Stage stage = (Stage) register.getScene().getWindow();
        RegistrazioneCtrlGrafico registrazioneCtrlGrafico = new RegistrazioneCtrlGrafico();
        registrazioneCtrlGrafico.start(stage);
    }

    @FXML
    protected void onGuestClick(ActionEvent event) throws IOException {

        // Devo aprire direttamente la home page, ma devo propagare l'informazione dell'accesso Guest
        Stage stage = (Stage) guest.getScene().getWindow();
        HomePageCtrlGrafico homePageCGUI = new HomePageCtrlGrafico();
        homePageCGUI.start(stage,null);
    }
    /*Questo va qua? non c'è riuso di codice*/
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