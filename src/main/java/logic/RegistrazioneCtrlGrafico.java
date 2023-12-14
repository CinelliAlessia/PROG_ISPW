package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegistrazioneCtrlGrafico {

    public Button back;
    public TextField name, email, password, conf_password;
    public Text error_pw;

    private String user_name,user_email,user_password, user_conf_pw;


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
            //Se tutti i controlli sono validi è possibile impostare la scena
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

}
