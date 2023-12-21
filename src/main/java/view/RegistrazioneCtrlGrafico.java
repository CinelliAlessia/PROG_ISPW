package view;

import controllerApplicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import start.MainApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneCtrlGrafico {
    // ---------Nodi interfaccia----------
    public Button back;
    public TextField name, email, password, conf_password;
    public Text error_pw;
    private ArrayList <String> preferences;
    //-----------------------------------
    private String user_name,user_email,user_password, user_conf_pw; // Dati

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/registrazione.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Registrazione");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBackClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        LoginCtrlGrafico loginCtrlGrafico = new LoginCtrlGrafico();
        loginCtrlGrafico.start(stage);
    }

    @FXML
    protected void onRegisterClick() throws IOException {
        getData();
        RegistrazioneCtrlApplicativo reg_CtrlApp = new RegistrazioneCtrlApplicativo();

        /*ANDREA DICE CHE CHIAMI A FARE 3 VOLTE SE PUOI FARLO FARE DIRETTAMENTE A LUI*/

        // Questo dovrebbe essere fatto qui, senza chiamare il ctrl applicativo

        if(verificaPassword(user_password,user_conf_pw)){
            // i campi password e conferma password non corrispondono
            error_pw.setText("LE PASSWORD NON CORRISPONDONO");
            error_pw.setVisible(true);
        } else if (verificaEmailCorrect(user_email)) {
            //Controllo se email è corretto
            error_pw.setText("EMAIL NON VALIDA");
            error_pw.setVisible(true);
        } else if(verificaRegistrazioneEsistente(user_password,user_conf_pw)){
            //Controllo se non è già registrato
            error_pw.setText("UTENTE GIA REGISTRATO");
            error_pw.setVisible(true);
        } else{
            //Salvo utente
            UserBean bean = new UserBean(user_name, user_email,user_password, new ArrayList<String>());
            reg_CtrlApp.registerUser(bean); // passaggio al ctrl applicativo

            //Se tutto è stato fatto è possibile impostare la scena
            Stage stage = (Stage) back.getScene().getWindow();
            HomePageCtrlGrafico homePageCtrlGrafico = new HomePageCtrlGrafico();
            homePageCtrlGrafico.start(stage);
        }
    }

    private void getData(){
        //controlla se ha inserito davvero qualcosa
        user_name = name.getText();
        user_email = email.getText();

        /*password in chiaro*/
        user_password = password.getText();
        user_conf_pw = conf_password.getText();
    }

    @FXML
    protected void onRegisterClick2() throws IOException {
        UserBean userBean;
        userBean = getData2();

        if(userBean != null){
            RegistrazioneCtrlApplicativo reg_CtrlApp = new RegistrazioneCtrlApplicativo();
            reg_CtrlApp.registerUser(userBean); // passaggio al ctrl applicativo

            //Se tutto è stato fatto è possibile impostare la scena
            Stage stage = (Stage) back.getScene().getWindow();
            HomePageCtrlGrafico homePageCtrlGrafico = new HomePageCtrlGrafico();
            homePageCtrlGrafico.start(stage);
        }
    }

    private UserBean getData2(){
        //Prendo i dati
        user_name = name.getText();
        user_email = email.getText();

        /*password in chiaro*/
        user_password = password.getText();
        user_conf_pw = conf_password.getText();

        if(user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_conf_pw.isEmpty()) {
            error_pw.setText("CAMPI VUOTI");
            error_pw.setVisible(true);
        } else if(!verificaPassword(user_password,user_conf_pw)){
            // i campi password e conferma password non corrispondono
            error_pw.setText("LE PASSWORD NON CORRISPONDONO");
            error_pw.setVisible(true);
        } else if (!verificaEmailCorrect(user_email)) {
            //Controllo se email è corretto
            error_pw.setText("EMAIL NON VALIDA");
            error_pw.setVisible(true);
        } else if(!verificaRegistrazioneEsistente(user_password,user_conf_pw)){
            //Controllo se non è già registrato
            error_pw.setText("UTENTE GIA REGISTRATO");
            error_pw.setVisible(true);
        } else {
            UserBean userBeanInfo = new UserBean(user_name,user_email,user_password,preferences);
            return userBeanInfo;
        }

        return null;
    }



    private boolean verificaPassword(String password, String confermaPassword) {
        if (password.equals(confermaPassword)) {
            // La password e la conferma password corrispondono
            // Esegui azioni appropriate (visualizza un messaggio, ecc.)
            return true;
        } else {
            // La registrazione può procedere
            // Chiamata al modello o al sistema di persistenza per salvare i dati
            return false;
        }
    }

    //idem grafico
    private boolean verificaEmailCorrect(String email) {
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

    //DA IMPLEMENTARE
    private boolean verificaRegistrazioneEsistente(String password, String confermaPassword) {
        // La password e la conferma password non corrispondono
        // Esegui azioni appropriate (visualizza un messaggio, ecc.)
        // La registrazione può procedere
        // Chiamata al modello o al sistema di persistenza per salvare i dati
        return password.equals(confermaPassword);
    }
}
