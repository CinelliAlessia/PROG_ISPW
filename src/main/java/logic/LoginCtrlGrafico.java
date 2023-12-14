package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginCtrlGrafico {
    public Button login;
    public Button register;
    public PasswordField password;
    public TextField username;
    @FXML
    private Label textLogin;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Access Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onLoginClick() throws IOException {
        String user = username.getText();
        String pass = password.getText();
        LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();
        // se credenziali ok
        if (loginCtrlApp.verificaCredenziali(user,pass)) {
            textLogin.setText("Credenziali corrette");

            // ora dovrei permettere "all'utente" di visualizzare la homePage
            Stage stage = (Stage) login.getScene().getWindow();
            HomePageCtrlGrafico homePageCGUI = new HomePageCtrlGrafico();
            homePageCGUI.start(stage);

        } else {
            textLogin.setText("Credenziali errate");
        }
    }
    @FXML
    protected void onRegisterClick() throws IOException {
        // questo non dovrebbe essere cosi, ma dovrei creare una istanza del controller grafico del register, e poi utilizzare il metodo start(?)
        Stage stage = (Stage) register.getScene().getWindow();
        RegistrazioneCtrlGrafico registrazioneCtrlGrafico = new RegistrazioneCtrlGrafico();
        registrazioneCtrlGrafico.start(stage);
    }

    @FXML
    protected void onGuestClick() throws IOException {
        // questo non dovrebbe essere cosi, ma dovrei creare una istanza del controller grafico del register, e poi utilizzare il metodo start(?)
        Stage stage = (Stage) register.getScene().getWindow();
        RegistrazioneCtrlGrafico registrazioneCtrlGrafico = new RegistrazioneCtrlGrafico();
        registrazioneCtrlGrafico.start(stage);
    }
}