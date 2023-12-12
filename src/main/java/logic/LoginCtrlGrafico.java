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
    public PasswordField password;
    public TextField username;
    @FXML
    private Label textLogin;

    public  void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1080, 700);
        stage.setTitle("Access Login");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onLoginClick() {
        String user = username.getText();
        String pass = password.getText();
        LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();
        // se credenziali ok
        if (loginCtrlApp.verificaCredenziali(user,pass)) {
            textLogin.setText("Credenziali corrette");
            // ora dovrei permettere "all'utente" di visualizzare la homePage
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage.fxml")); // DA CAMBIARE VIEW
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) login.getScene().getWindow(); // Ottieni la finestra corrente
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            textLogin.setText("Credenziali errate");
        }
    }
    @FXML
    protected void onRegisterClick() throws IOException {
        // questo non dovrebbe essere cosi, ma dovrei creare una istanza del controller grafico del register, e poi utilizzare il metodo start(?)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registrazione.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) login.getScene().getWindow(); // Ottieni la finestra corrente
        stage.setScene(scene);
        stage.show();
    }

}