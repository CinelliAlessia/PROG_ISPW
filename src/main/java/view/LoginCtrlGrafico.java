package view;

import controllerApplicativo.LoginCtrlApplicativo;
import engineering.bean.LoginBean;
import engineering.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo(); //Dovrebbe essere static

            if (loginCtrlApp.verificaCredenziali(loginBean)) {
                /* --------------- Credenziali corrette -------------- */
                System.out.println("CREDENZIALI CORRETTE -> Recupero l'istanza di bean ");

                UserBean userBean = loginCtrlApp.loadUser(loginBean);

                if(userBean != null){
                    //userBean.setRegistered(); // Indica che l'utente con cui sto accedendo è registrato
                    System.out.println("Utente registrato, ho recuperato tutto lo user bean");

                    /* --------------- Mostro la home page -------------- */

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage.fxml"));
                    Parent root = loader.load();
                    loader.<HomePageCtrlGrafico>getController().setUserBean(userBean);

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    //stage.setResizable(false); ///##############################################
                    stage.setTitle("Home Page");
                    stage.setScene(scene);
                    stage.show();

                }
            } else { /* --------------- Credenziali non valide --------------*/
                textLogin.isVisible();
                textLogin.setText("Credenziali errate");
            }
        }
    }
    @FXML
    protected void onRegisterClick(ActionEvent event) throws IOException {
        SceneController.getInstance().goToScene(event,"/view/login.fxml");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        //stage.setResizable(false); ///##############################################
        stage.setTitle("Registrazione");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onGuestClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage.fxml"));
        Parent root = loader.load();
        loader.<HomePageCtrlGrafico>getController().setUserBean(null);

        SceneController.getInstance().goToScene(event);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        //stage.setResizable(false); ///##############################################
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
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