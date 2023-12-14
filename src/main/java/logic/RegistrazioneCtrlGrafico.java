package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrazioneCtrlGrafico {

    public Button back;

    public  void start(Stage stage) throws IOException {
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

}
