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

        if(!reg_CtrlApp.verificaPassword(user_password,user_conf_pw)){
            // i campi password e conferma password non corrispondono
            error_pw.setText("LE PASSWORD NON CORRISPONDONO");
            error_pw.setVisible(true);
        } else if (!reg_CtrlApp.verificaEmailCorrect(user_email)) {
            //Controllo se email è corretto
            error_pw.setText("EMAIL NON VALIDA");
            error_pw.setVisible(true);
        } else if(!reg_CtrlApp.verificaRegistrazioneEsistente(user_password,user_conf_pw)){
            //Controllo se non è già registrato
            error_pw.setText("UTENTE GIA REGISTRATO");
            error_pw.setVisible(true);
        } else{
            //Salvo utente
            UserBean bean = new UserBean(user_name, user_email,user_password, new ArrayList<String>());
            reg_CtrlApp.registerUserAndrea(bean); // passaggio al ctrl applicativo

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

    private UserBean getData2(){
        //controlla se ha inserito davvero qualcosa
        user_name = name.getText();
        user_email = email.getText();

        /*password in chiaro*/
        user_password = password.getText();
        user_conf_pw = conf_password.getText();

        if(user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_conf_pw.isEmpty()) {
            error_pw.setText("CAMPI VUOTI");
            error_pw.setVisible(true);
            return null;
        } else {
            UserBean userBeanInfo = new UserBean(user_name,user_email,user_password,preferences);
            return userBeanInfo;
        }

    }
}
